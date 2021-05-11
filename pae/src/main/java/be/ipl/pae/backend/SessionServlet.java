package be.ipl.pae.backend;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.DefaultServlet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import be.ipl.pae.utilisateur.FactoryUtilisateur;
import be.ipl.pae.utilisateur.UtilisateurDto;
import be.ipl.pae.utilisateur.UtilisateurUcc;
import exceptions.FatalException;
import log.MyLogger;
import util.Util;

public class SessionServlet extends DefaultServlet {

  private UtilisateurUcc utilisateurUcc;
  private UtilServlet utilServlet;
  private FactoryUtilisateur utilisateurFactory;
  private static final String JWTSECRET = "djeqpFHEHUISPF";
  private Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);
  private Builder builder = JWT.create();
  private JWTVerifier verif = JWT.require(algorithm).build();

  public SessionServlet(UtilisateurUcc utilisateurUcc, UtilServlet utilServlet,
      FactoryUtilisateur utilisateurFactory) {
    super();
    this.utilisateurUcc = utilisateurUcc;
    this.utilServlet = utilServlet;
    this.utilisateurFactory = utilisateurFactory;
  }

  /**
   * Connecte un utilisateur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException lance une exception relatif aux input et outputs
   */
  public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String json = req.getParameter("json");
    UtilisateurDto utilisateur = utilisateurFactory.creerUtilisateur();
    utilisateur = utilisateur.deserialiseur(json);

    UtilisateurDto userRenvoye = utilisateurUcc.seConnecter(utilisateur);

    if (userRenvoye != null) {

      Map<String, Object> claims = new HashMap<String, Object>();
      claims.put("email", userRenvoye.getMail());
      String token = builder.withClaim("email", userRenvoye.getMail())
          .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
          .sign(algorithm);

      Cookie cookie = new Cookie("email", token);
      // System.out.println(cookie.getName());
      cookie.setPath("/");
      cookie.setMaxAge(60 * 60 * 24);
      cookie.setHttpOnly(true);
      resp.addCookie(cookie);
      utilServlet.response(resp, userRenvoye.getPrenom() + " " + userRenvoye.getNom(), "text/html",
          200);
    } else {
      utilServlet.response(resp, "error_login", "text/html", 400);
    }
  }

  /**
   * Déconnexion.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    req.getSession().invalidate();
    for (Cookie cookie : req.getCookies()) {
      if (cookie.getName().equals("email")) {
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
      }
    }

    // HttpSession session = req.getSession();
    // session.setAttribute("email", null);
    // session.invalidate();
    utilServlet.response(resp, "ok", "text/html", 200);
  }

  /**
   * refresh la page.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException lance une exception relatif aux input et outputs
   */
  public void refresh(HttpServletRequest req, HttpServletResponse resp) throws IOException {


    // String token = null;
    // try {
    // Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);
    // token = JWT.create().withIssuer("auth0").sign(algorithm);
    //
    // } catch (JWTCreationException exception) {
    // MyLogger.getLogger().log(Level.SEVERE, exception.getMessage());
    // exception.printStackTrace();
    // throw new FatalException();
    // }

    String email = "";
    Cookie[] cookies = req.getCookies();
    if (cookies != null) {
      for (Cookie c : cookies) {
        if ("email".equals(c.getName())) {
          try {
            DecodedJWT decodedJwt = this.verif.verify(c.getValue());
            email = decodedJwt.getClaim("email").asString();
          } catch (JWTVerificationException ex) {
            ex.printStackTrace();
          }
        }
      }
      if (email != "") {
        UtilisateurDto mailUser = utilisateurUcc.getUser(email);
        if (mailUser.getFonction().equals(UtilisateurDto.Fonction.E)) {
          utilServlet.response(resp, "confirmerMobilite", "text/html", 200);
        } else {
          utilServlet.response(resp, "confirmerMobiliteProf", "text/html", 200);
        }
      } else {
        utilServlet.response(resp, "authentification", "text/html", 400);
      }
    } else {
      utilServlet.response(resp, "authentification", "text/html", 400);
    }
  }

  /**
   * Inscrit un utilisateur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException lance une exception relatif aux input et outputs
   */
  public void inscription(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    String json = req.getParameter("json");
    UtilisateurDto utilisateur = utilisateurFactory.creerUtilisateur();
    utilisateur = utilisateur.deserialiseur(json);

    if (!Util.validateEmail(utilisateur.getMail())) {
      utilServlet.response(resp, "error_email", "text/html", 400);
      return;
    }

    UtilisateurDto userRenvoye = utilisateurUcc.inscription(utilisateur);
    System.out.println("u : " + userRenvoye);
    if (userRenvoye != null) {
      Map<String, Object> claims = new HashMap<String, Object>();
      claims.put("email", userRenvoye.getMail());
      String token = null;
      try {
        Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);
        token = JWT.create().withIssuer("auth0").sign(algorithm);
      } catch (JWTCreationException exception) {
        MyLogger.getLogger().log(Level.SEVERE, exception.getMessage());
        exception.printStackTrace();
        throw new FatalException();
      }
      Cookie cookie = new Cookie("email", token);
      cookie.setPath("/");
      cookie.setMaxAge(60 * 60 * 24 * 365);
      resp.addCookie(cookie);
      utilServlet.response(resp, "ok_inscription", "text/html", 200);
    } else {
      utilServlet.response(resp, "error_inscription", "text/html", 400);
    }

  }

  /**
   * Récupère l'email.
   * 
   * @param cookies d'un utilisateur
   * @return email
   */
  public String getUserMailByCookies(Cookie[] cookies) {
    if (cookies != null) {
      for (Cookie c : cookies) {
        if ("email".equals(c.getName())) {
          try {
            DecodedJWT decodedJwt = this.verif.verify(c.getValue());
            return decodedJwt.getClaim("email").asString();
          } catch (JWTVerificationException ex) {
            ex.printStackTrace();
          }
        }
      }
    }
    return null;
  }
}
