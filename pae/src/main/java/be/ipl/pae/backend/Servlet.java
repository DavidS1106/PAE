package be.ipl.pae.backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.DefaultServlet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Genson;
import be.ipl.pae.etudiant.EtudiantDto;
import be.ipl.pae.etudiant.EtudiantUcc;
import be.ipl.pae.etudiant.FactoryEtudiant;
import be.ipl.pae.mobilite.FactoryMobilite;
import be.ipl.pae.mobilite.MobiliteDto;
import be.ipl.pae.mobilite.MobiliteUcc;
import be.ipl.pae.partenaire.FactoryPartenaire;
import be.ipl.pae.partenaire.PartenaireDto;
import be.ipl.pae.partenaire.PartenaireUcc;
import be.ipl.pae.statutmobilite.StatutMobiliteDto;
import be.ipl.pae.statutmobilite.StatutMobiliteUcc;
import be.ipl.pae.utilisateur.FactoryUtilisateur;
import be.ipl.pae.utilisateur.UtilisateurDto;
import be.ipl.pae.utilisateur.UtilisateurDto.Fonction;
import be.ipl.pae.utilisateur.UtilisateurUcc;
import exceptions.FatalException;
import log.MyLogger;


public class Servlet extends DefaultServlet {

  private static final long serialVersionUID = 1L;


  private static final String JWTSECRET = "djeqpFHEHUISPF";
  private Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);
  private Builder builder = JWT.create();
  private JWTVerifier verif = JWT.require(algorithm).build();

  /*
   * JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Base64.getDecoder().decode(SECRET)))
   * .withIssuer("auth0") .acceptLeeway(1) .acceptExpiresAt(5 * 60) .build();
   */

  // mettre l'id de l'etudiant dans un token le token sera notre badge pour
  // chaque requete pour qu'il sache qui c'est.
  private FactoryUtilisateur utilisateurFactory;
  private FactoryEtudiant etudiantFactory;
  private FactoryPartenaire partenaireFactory;
  private FactoryMobilite mobiliteFactory;
  private MobiliteUcc mobiliteUcc;
  private EtudiantUcc etudiantUcc;
  private UtilisateurUcc utilisateurUcc;
  private StatutMobiliteUcc statutMobiliteUcc;
  private PartenaireUcc partenaireUcc;

  /**
   * Initialisation.
   * 
   * @param utilisateurFactory factory
   * @param etudiantFactory factory
   * @param partenaireFactory factory
   * @param mobiliteFactory factory
   * @param mobiliteUcc use case
   * @param statutMobiliteUcc use case
   * @param utilisateurUcc use case
   * @param etudiantUcc use case
   * @param partenaireUcc use case
   */
  public Servlet(FactoryUtilisateur utilisateurFactory, FactoryEtudiant etudiantFactory,
      FactoryPartenaire partenaireFactory, FactoryMobilite mobiliteFactory, MobiliteUcc mobiliteUcc,
      StatutMobiliteUcc statutMobiliteUcc, UtilisateurUcc utilisateurUcc, EtudiantUcc etudiantUcc,
      PartenaireUcc partenaireUcc) {
    super();
    this.utilisateurFactory = utilisateurFactory;
    this.etudiantFactory = etudiantFactory;
    this.mobiliteFactory = mobiliteFactory;
    this.mobiliteUcc = mobiliteUcc;
    this.etudiantUcc = etudiantUcc;
    this.partenaireFactory = partenaireFactory;
    this.utilisateurUcc = utilisateurUcc;
    this.statutMobiliteUcc = statutMobiliteUcc;
    this.partenaireUcc = partenaireUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String action = req.getParameter("action");
    System.out.println("act : " + action);

    switch (action) {
      case "login":
        login(req, resp);
        break;
      case "logout":
        logout(req, resp);
        break;
      case "refresh":
        refresh(req, resp);
        break;
      case "inscription":
        inscription(req, resp);
        break;

      case "get_etudiant":
        getEtudiant(req, resp);
        break;
      case "recupererDonneesPartenaireSelectionne":
        recupererDonneesPartenaireSelectionne(req, resp);
        break;
      case "get_partenaires_list":
        getPartenaires(req, resp);
        break;
      case "save_info_part":
        saveInfosPartenaire(req, resp);
        break;
      case "save_info":
        try {
          saveInfos(req, resp);
        } catch (SQLException ex) {
          MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
          ex.printStackTrace();
          throw new FatalException();
        }
        break;
      case "save_infos_prof":
        try {
          saveInfosProf(req, resp);
        } catch (SQLException ex) {
          MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
          ex.printStackTrace();
          throw new FatalException();
        }
        break;
      case "annulationMobilite":
        annulationMobilite(req, resp);
        break;
      case "etatDocuments":
        etatDocuments(req, resp);
        break;
      case "updateEtatDocuments":
        updateEtatDocuments(req, resp);
        break;
      case "recupererEtudiantSelectionne":
        recupererDonneesEtudiantSelectionne(req, resp);
        break;
      case "ajouterMobilite":
        insererMobilite(req, resp);
        break;
      case "ajouterMobiliteComplet":
        insererMobiliteComplet(req, resp);
        break;
      case "modifierMobilite":
        modifierMobilite(req, resp);
        break;
      case "recupererListePays":
        recupererListePays(req, resp);
        break;
      case "recupererListePartenaires":
        recupererListePartenaires(req, resp);
        break;
      case "recupFonction":
        recupFonction(req, resp);
        break;
      case "confirmerMobilite":
        confirmerMobilite(req, resp);
        break;
      case "chargerMobiliteExistante":
        chargerMobiliteExistante(req, resp);
        break;
      case "chargerMobiliteNonExistante":
        chargerMobiliteNonExistante(req, resp);
        break;
      case "getNumVersion":
        /* recupNumversion(req, resp); */
      case "rechercheEtudiant":
        rechercheEtudiant(req, resp);
        break;
      case "recherchePartenaire":
        recherchePartenaire(req, resp);
        break;
      case "rechercheMobilite":
        rechercheMobilite(req, resp);
        break;
      case "modifierMob":
        modifierMob(req, resp);
        break;
      case "getRaisons":
        getRaisons(req, resp);
        break;
      case "getStudentsProf":
        getStudentsProf(req, resp);
        break;
      case "getInscrits":
        getInscrits(req, resp);
        break;
      case "recupEtudiantMobilite":
        recupEtudiantMobilite(req, resp);
        break;
      default:
        break;
    }
  }


  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {

    String page = " ";

    page += ajouterPage("header.html");
    page += ajouterPage("authentification.html");
    page += ajouterPage("choixMobilite.html");
    page += ajouterPage("ConfirmerMobilite.html");
    page += ajouterPage("EtatDesDocumentsEtudiants.html");
    page += ajouterPage("EtatDesDocumentsProfs.html");
    page += ajouterPage("InfosEtudiants.html");
    page += ajouterPage("InfosEtudiantsProfs.html");
    page += ajouterPage("InfosPartenaires.html");
    page += ajouterPage("recherche.html");
    page += ajouterPage("Annulation-Etudiant.html");
    page += ajouterPage("Annulation-Professeur.html");
    page += ajouterPage("DemandeMobiliteCsv.html");
    page += ajouterPage("retourMobilite.html");
    page += ajouterPage("voirInscrit.html");
    page += ajouterPage("footer.html");

    if (!req.getRequestURI().equals("/")) {
      try {
        super.doGet(req, resp);
      } catch (ServletException | IOException ex) {
        ex.printStackTrace();
        throw new FatalException();
      }
    } else {
      fichierPage(resp, page);
    }

  }
  // resp.setStatus(status);
  // resp.setContentType(contentType);
  // byte[] msgBytes;
  // try {
  // msgBytes = msg.getBytes("UTF-8");
  // resp.setContentLength(msgBytes.length);
  // resp.setCharacterEncoding("UTF-8");
  // resp.getOutputStream().write(msgBytes);
  // } catch (UnsupportedEncodingException exception) {
  // MyLogger.getLogger().log(Level.SEVERE, exception.getMessage());
  // exception.printStackTrace();
  // throw new FatalException();
  // } catch (IOException ex) {
  // MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
  // ex.printStackTrace();
  // throw new FatalException();
  // }
  // response(resp, json, "text/html", 200);

  private void fichierPage(HttpServletResponse resp, String page) {
    response(resp, page, "text/html", 200);
  }

  private String ajouterPage(String nomPage) throws IOException {
    String pageARenvoye = "";
    Path path = Paths.get("www/html/" + nomPage);
    try {
      BufferedReader buffer = Files.newBufferedReader(path);
      Scanner sc = new Scanner(buffer);
      while ((pageARenvoye += sc.nextLine() + "\n") != null) {
        sc.toString();
      }
    } catch (IOException ex) {
      ex.printStackTrace();
      throw new FatalException();
    } catch (NoSuchElementException ex2) {
      return pageARenvoye;
    }
    return null;
  }

  /**
   * modifier une mobilite
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void modifierMob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int id = Integer.parseInt(req.getParameter("idMobilite"));
    String email = getUserMailByCookies(req.getCookies());
    int numOrdre = Integer.parseInt(req.getParameter("numOrdre"));
    String nomPartenaire = req.getParameter("nomPartenaire");
    String pays = req.getParameter("pays");
    String typeDeMobilite = req.getParameter("typeDeMobilite");
    String code = req.getParameter("code");
    int quadri = 0;
    if (req.getParameter("quadri") == "") {
      quadri = 3;
    } else {
      quadri = Integer.parseInt(req.getParameter("quadri"));
    }
    // if (!mobiliteUcc.updateMob(id, email, numOrdre, nomPartenaire, pays, typeDeMobilite, code,
    // quadri)) {
    // response(resp, "ko", "text/html", 200);
    //
    // }
    // response(resp, "ok", "text/html", 200);
  }

  /**
   * recuperer les mobilites
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void chargerMobiliteExistante(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String mail = getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mobilites = mobiliteUcc.recupStudMob(mail);
    for (MobiliteDto m : mobilites) {
      m.setMail(mail);
    }
    Genson g = new Genson();
    String json = g.serialize(mobilites);
    response(resp, json, "text/html", 200);

    /*
     * String email = getUserMailByCookies(req.getCookies()); ArrayList<MobiliteDto> mRenvoye =
     * mobiliteUcc.recupererMob(email);
     * 
     * Genson g = new Genson(); String json = g.serialize(mRenvoye);
     * resp.setContentType("text/html"); resp.setStatus(200);
     * resp.getOutputStream().write(json.getBytes());
     */
  }

  /**
   * recuperer les mobilites
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void chargerMobiliteNonExistante(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String email = getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mRenvoye = mobiliteUcc.recupererMobNonExistante(email);

    Genson g = new Genson();
    String json = g.serialize(mRenvoye);
    response(resp, json, "text/html", 200);
  }

  /**
   * confirmer une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void confirmerMobilite(HttpServletRequest req, HttpServletResponse resp) {
    String email = getUserMailByCookies(req.getCookies());
    int idMobilite = Integer.parseInt(req.getParameter("idMobilite"));
    if (statutMobiliteUcc.confirmerMobilite(idMobilite, email)) {
      response(resp, "ok", "text/html", 200);
    }
    response(resp, "ko", "text/html", 200);
  }

  /**
   * Envoie la page.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void insererMobilite(HttpServletRequest req, HttpServletResponse resp) {
    int preference = Integer.parseInt(req.getParameter("preference"));
    String partenaire = req.getParameter("partenaire");
    String pays = req.getParameter("pays");
    int quadrimestre = Integer.parseInt(req.getParameter("quadrimestre"));
    LocalDate dateIntroduction = LocalDate.now();
    String anneAcad = String.valueOf(LocalDate.now());// A MODIFIER !!
    String email = getUserMailByCookies(req.getCookies());

    // mobiliteUcc.insererMob(email, anneAcad, preference, pays, partenaire, dateIntroduction,
    // quadrimestre);

  }

  /**
   * Insère une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void insererMobiliteComplet(HttpServletRequest req, HttpServletResponse resp) {

    int preference = Integer.parseInt(req.getParameter("preference"));
    String partenaire = req.getParameter("partenaire");
    String programme = req.getParameter("programme");
    String code = req.getParameter("code");
    String pays = req.getParameter("pays");
    int quadrimestre = Integer.parseInt(req.getParameter("quadrimestre"));
    LocalDate dateIntroduction = LocalDate.now();
    String anneAcad = String.valueOf(LocalDate.now());// A MODIFIER !!
    String email = getUserMailByCookies(req.getCookies());

    // mobiliteUcc.insererMobComplet(email, anneAcad, preference, pays, partenaire,
    // dateIntroduction,
    // quadrimestre, programme, code);

  }

  /**
   * Modifie une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input output exception
   */
  public void modifierMobilite(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String email = getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mobRenvoye = mobiliteUcc.recupererMob(email);


    Genson genson = new Genson();
    String json = genson.serialize(mobRenvoye);
    resp.setStatus(200);
    resp.getOutputStream().print(json);
  }

  /**
   * Sauvegarde les informations de l'étudiant.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws FileNotFoundException file not found exception
   * @throws SQLException sql exception
   */
  public void saveInfos(HttpServletRequest req, HttpServletResponse resp)
      throws FileNotFoundException, SQLException {


    String json = req.getParameter("json");
    LocalDate dateNaissance = null;
    EtudiantDto etud = etudiantFactory.creerEtudiant();

    etud = etud.deserialiseur(json);

    if (!req.getParameter("date_naissance").equals("")) {
      dateNaissance = LocalDate.parse(req.getParameter("date_naissance"));
      etud.setDateNaissance(dateNaissance);
    }
    etud.setEmail(getUserMailByCookies(req.getCookies()));

    try {
      if (this.etudiantUcc.updateInfo(etud)) {
        response(resp, "not ok", "text/html", 400);
      } else {
        resp.getOutputStream().print("ok");
      }
    } catch (IOException ex) {
      MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
      ex.printStackTrace();
      throw new FatalException();
    }
  }

  /**
   * Sauvegarde les informations de l'étudiant par un professeur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   * @throws SQLException sql exception
   */
  public void saveInfosProf(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, SQLException {

    String json = req.getParameter("json");
    LocalDate dateNaissance = null;
    EtudiantDto etud = etudiantFactory.creerEtudiant();

    etud = etud.deserialiseur(json);

    if (!req.getParameter("date_naissance").equals("")) {
      dateNaissance = LocalDate.parse(req.getParameter("date_naissance"));
      etud.setDateNaissance(dateNaissance);
    }

    try {
      if (this.etudiantUcc.updateInfo(etud)) {
        response(resp, "not ok", "text/html", 400);
      } else {
        resp.getOutputStream().print("ok");
      }
    } catch (IOException e) {
      MyLogger.getLogger().log(Level.SEVERE, e.getMessage());
      e.printStackTrace();

      throw new FatalException();
    }
  }

  /**
   * Sauvegarde les informations d'un partenaire.
   * 
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void saveInfosPartenaire(HttpServletRequest req, HttpServletResponse resp) {

    String adresse = req.getParameter("adresse");
    String code = req.getParameter("code");
    String codePostal = req.getParameter("code_postal");
    int nbrEmploye = Integer.parseInt(req.getParameter("nbr_employe"));
    String nomAffaire = req.getParameter("nom_affaire");
    String nomComplet = req.getParameter("nom_complet");
    String nomLegal = req.getParameter("nom_legal");
    String pays = req.getParameter("pays");
    String region = req.getParameter("region");
    String siteWeb = req.getParameter("site_web");
    String tel = req.getParameter("tel");
    String departement = req.getParameter("departement");
    String typeOrganisation = req.getParameter("type_organisation");
    String ville = req.getParameter("ville");
    String email = req.getParameter("email");

    // this.partenaireUcc.updateInfo(nomLegal, nomAffaire, nomComplet, departement,
    // typeOrganisation,
    // nbrEmploye, adresse, pays, region, codePostal, ville, email, siteWeb, tel, code,
    // Integer.parseInt(req.getParameter("numVersion")));
  }

  /**
   * Récupère les données d'un partenaire.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupererDonneesPartenaireSelectionne(HttpServletRequest req,
      HttpServletResponse resp) throws IOException {

    String email = req.getParameter("email");

    PartenaireDto partenaireRenvoye = partenaireUcc.recupererInfo(email);
    Genson genson = new Genson();
    String data = genson.serialize(partenaireRenvoye);
    System.out.println(partenaireRenvoye);
    resp.getOutputStream().write(data.getBytes());
  }

  /**
   * Récupérer les données d'un étudiant.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupererDonneesEtudiantSelectionne(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String email = req.getParameter("email");
    EtudiantDto etudRenvoye = etudiantUcc.recupererInfo(email);
    String textDate = String.valueOf(etudRenvoye.getDateNaissance());
    etudRenvoye.setDateNaissanceaRenvoyer(textDate);
    Genson genson = new Genson();
    String data = genson.serialize(etudRenvoye);

    resp.getOutputStream().write(data.getBytes());
  }

  /**
   * Récupère les étudiants.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getEtudiant(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String email = getUserMailByCookies(req.getCookies());

    EtudiantDto etudRenvoye = etudiantUcc.recupererInfo(email);

    String textDate = String.valueOf(etudRenvoye.getDateNaissance());
    etudRenvoye.setDateNaissanceaRenvoyer(textDate);
    Genson genson = new Genson();
    String data = genson.serialize(etudRenvoye);
    resp.getOutputStream().write(data.getBytes());

  }

  /**
   * Récupère les mobilités.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */


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
        System.out.println("refresh " + email);
        response(resp, "confirmerMobilite", "text/html", 200);
      } else {
        response(resp, "authentification", "text/html", 400);
      }
    } else {
      response(resp, "authentification", "text/html", 400);
    }
  }



  /**
   * Connecte un utilisateur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException lance une exception relatif aux input et outputs
   */
  public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String email = req.getParameter("email");
    String mdp = req.getParameter("mdp");
    UtilisateurDto userFacticePourLeMerge = null;
    UtilisateurDto userRenvoye = utilisateurUcc.seConnecter(userFacticePourLeMerge);

    if (userRenvoye != null) {
      Map<String, Object> claims = new HashMap<String, Object>();
      claims.put("email", email);
      String token = builder.withClaim("email", email)
          .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
          .sign(algorithm);

      Cookie cookie = new Cookie("email", token);
      cookie.setPath("/");
      cookie.setMaxAge(60 * 60 * 24);
      cookie.setHttpOnly(true);
      resp.addCookie(cookie);

      response(resp, userRenvoye.getPrenom() + " " + userRenvoye.getNom(), "text/html", 200);
    } else {
      response(resp, "not ok", "text/html", 400);
    }
  }

  /**
   * Récupère l'email.
   * 
   * @param cookies d'un utilisateur
   * @return email
   */
  private String getUserMailByCookies(Cookie[] cookies) {
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
    response(resp, "ok", "text/html", 200);
  }

  /**
   * Inscrit un utilisateur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException lance une exception relatif aux input et outputs
   */
  public void inscription(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // String email = req.getParameter("email");
    // String lastName = req.getParameter("nom");
    // String firstName = req.getParameter("prenom");
    // String pseudo = req.getParameter("pseudo");
    // String pswd = req.getParameter("mdp");
    //
    // UtilisateurDto userRenvoye =
    // utilisateurUcc.inscription(email, lastName, firstName, pseudo, pswd);
    // if (userRenvoye != null) {
    // Map<String, Object> claims = new HashMap<String, Object>();
    // claims.put("email", email);
    // String token = null;
    // try {
    // Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);
    // token = JWT.create().withIssuer("auth0").sign(algorithm);
    // } catch (JWTCreationException exception) {
    // MyLogger.getLogger().log(Level.SEVERE, exception.getMessage());
    // exception.printStackTrace();
    // throw new FatalException();
    // }
    // Cookie cookie = new Cookie("email", token);
    // cookie.setPath("/");
    // cookie.setMaxAge(60 * 60 * 24 * 365);
    // resp.addCookie(cookie);
    //
    // response(resp, "ok", "text/html", 200);
    // } else {
    // response(resp, "ko", "text/html", 400);
    // }
  }

  /**
   * Annule une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void annulationMobilite(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String raison = req.getParameter("raison");
    String mail = req.getParameter("etudiant");
    String idRaison = req.getParameter("id_raison");
    int version = Integer.parseInt(req.getParameter("version"));

    if (mail == null) {
      mail = getUserMailByCookies(req.getCookies());
    }

    boolean succeed = statutMobiliteUcc.annuler(mail, raison, idRaison);

    if (succeed) {
      response(resp, "ok", "text/html", 200);
    } else {
      response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Récupère la fonction d'un utilisateur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void recupFonction(HttpServletRequest req, HttpServletResponse resp) {
    String mail = getUserMailByCookies(req.getCookies());
    UtilisateurDto utilisateur = utilisateurUcc.getUser(mail);
    if (utilisateur.getFonction() == Fonction.E) {
      response(resp, "E", "text/html", 200);
    } else {
      response(resp, "NOT E", "text/html", 200);
    }
  }

  /**
   * Récupère l'état des documents d'une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void etatDocuments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String mail = req.getParameter("etudiant");
    if (mail == null) {
      mail = getUserMailByCookies(req.getCookies());
    }
    StatutMobiliteDto etatDocuments = statutMobiliteUcc.getEtatDocuments(mail);
    Genson genson = new Genson();
    String json = genson.serialize(etatDocuments);
    resp.getOutputStream().print(json);
  }

  /**
   * Met à jour l'état des documents d'une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void updateEtatDocuments(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String mail = req.getParameter("etudiant");
    boolean contratBourse = Boolean.parseBoolean(req.getParameter("contratBourse"));
    boolean conventionEtude = Boolean.parseBoolean(req.getParameter("conventionEtude"));
    boolean conventionStage = Boolean.parseBoolean(req.getParameter("conventionStage"));
    boolean charteEtudiant = Boolean.parseBoolean(req.getParameter("charteEtudiant"));
    boolean documentEngagement = Boolean.parseBoolean(req.getParameter("documentEngagement"));
    boolean demandePremierPaiement =
        Boolean.parseBoolean(req.getParameter("demandePremierPaiement"));
    boolean premierPaiement = Boolean.parseBoolean(req.getParameter("premierPaiement"));
    boolean infoEtudiant = Boolean.parseBoolean(req.getParameter("infoEtudiant"));
    boolean infoPartenaire = Boolean.parseBoolean(req.getParameter("infoPartenaire"));
    boolean attestationSejour = Boolean.parseBoolean(req.getParameter("attestationSejour"));
    boolean releveNotes = Boolean.parseBoolean(req.getParameter("releveNotes"));
    boolean certificatStage = Boolean.parseBoolean(req.getParameter("certificatStage"));
    boolean rapportFinal = Boolean.parseBoolean(req.getParameter("rapportFinal"));
    boolean demandeSecondPaiement = Boolean.parseBoolean(req.getParameter("demandeSecondPaiement"));
    boolean secondPaiement = Boolean.parseBoolean(req.getParameter("secondPaiement"));
    int version = Integer.parseInt(req.getParameter("version"));
    // statutMobiliteUcc.updateEtatDocuments(mail, contratBourse, conventionEtude, conventionStage,
    // charteEtudiant, documentEngagement, demandePremierPaiement, premierPaiement, infoEtudiant,
    // infoPartenaire, attestationSejour, releveNotes, certificatStage, rapportFinal,
    // demandeSecondPaiement, secondPaiement, version);
  }

  /**
   * Récupère les partenaires.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getPartenaires(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ArrayList<PartenaireDto> partenaires = partenaireUcc.getPartenaires();
    Genson genson = new Genson();
    String json = genson.serialize(partenaires);
    response(resp, json, "text/html", 200);
  }

  /**
   * Récupères les inscrits.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getInscrits(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ArrayList<UtilisateurDto> utilisateurs = utilisateurUcc.getEtudiants();
    Genson genson = new Genson();
    String json = genson.serialize(utilisateurs);
    response(resp, json, "text/html", 200);
  }

  /**
   * Récupère les mobilite.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  private void recupEtudiantMobilite(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String mail = getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mobilites = mobiliteUcc.recupStudMob(mail);
    Genson g = new Genson();
    String json = g.serialize(mobilites);
    response(resp, json, "text/html", 200);
  }

  /**
   * Récupère les étudiants.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getStudentsProf(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ArrayList<EtudiantDto> etudiants = etudiantUcc.getEtudiants();
    Genson genson = new Genson();
    String json = genson.serialize(etudiants);
    response(resp, json, "text/html", 200);
  }

  /**
   * Récupères les raisons d'annulation.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getRaisons(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    ArrayList<String> raisons = utilisateurUcc.raisonsAnnulation();
    Genson genson = new Genson();
    String json = genson.serialize(raisons);
    response(resp, json, "text/html", 200);
  }

  /**
   * Récupère les pays.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupererListePays(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    ArrayList<String> pays = utilisateurUcc.getNomPays();
    if (pays != null) {
      Genson gen = new Genson();
      String json = gen.serialize(pays);
      response(resp, json, "text/html", 200);
    } else {
      response(resp, "no_result", "text/html", 200);
    }
  }

  /**
   * Recherche un étudiant.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void rechercheEtudiant(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    // String nom = req.getParameter("nom");
    // String prenom = req.getParameter("prenom");
    //
    // if (nom.isEmpty() && prenom.isEmpty()) {
    // response(resp, "empty_input", "text/html", 200);
    // return;
    // }
    //
    // ArrayList<EtudiantDto> rechercheEtudiant = etudiantUcc.rechercheEtudiant(etud);
    // if (!rechercheEtudiant.isEmpty()) {
    // Genson genson = new Genson();
    // String json = genson.serialize(rechercheEtudiant);
    // response(resp, json, "text/html", 200);
    // } else {
    // response(resp, "no_result", "text/html", 200);
    // }
  }

  /**
   * Récupère les partenaires.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recherchePartenaire(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String nom = req.getParameter("nom");
    String pays = req.getParameter("pays");
    String ville = req.getParameter("ville");

    if (nom.isEmpty() && pays.isEmpty() && ville.isEmpty()) {
      response(resp, "empty_input", "text/html", 200);
      return;
    }

    // ArrayList<PartenaireDto> recherchePartenaire =
    // partenaireUcc.recherchePartenaire(nom, pays, ville);

    // if (!recherchePartenaire.isEmpty()) {
    // Genson genson = new Genson();
    // String json = genson.serialize(recherchePartenaire);
    // response(resp, json, "text/html", 200);
    // } else {
    // response(resp, "no_result", "text/html", 200);
    // }
  }

  /**
   * Récupère les mobilite.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void rechercheMobilite(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String annee = req.getParameter("annee");
    String etat = req.getParameter("etat");

    if (annee.isEmpty() && etat.isEmpty()) {
      response(resp, "empty_input", "text/html", 200);
      return;
    }

    ArrayList<MobiliteDto> rechercheMobilite = mobiliteUcc.rechercheMobilite(annee, etat);
    if (!rechercheMobilite.isEmpty()) {
      Genson genson = new Genson();
      String json = genson.serialize(rechercheMobilite);
      response(resp, json, "text/html", 200);
    } else {
      response(resp, "no_result", "text/html", 200);
    }
  }

  /**
   * Récupère les partenaires.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupererListePartenaires(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    ArrayList<String> partenaires = partenaireUcc.getNomsPartenaires();
    if (partenaires != null) {
      Genson gen = new Genson();
      String json = gen.serialize(partenaires);
      byte[] msgBytes;
      try {
        msgBytes = json.getBytes("UTF-8");
        resp.setContentLength(msgBytes.length);
        resp.setCharacterEncoding("UTF-8");
        resp.getOutputStream().write(msgBytes);
      } catch (UnsupportedEncodingException exception) {
        exception.printStackTrace();
      }
    }
  }

  /**
   * Recupere les numeros de version pour l'optimistic lock.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  /*
   * public void recupNumversion(HttpServletRequest req, HttpServletResponse resp) throws
   * IOException { String choix = req.getParameter("choix"); String email =
   * getUserMailByCookies(req.getCookies()); int numVersion = 0; if (choix.equals("etatDocuments"))
   * { numVersion = statutMobiliteUcc.getNumVersion(req.getParameter("mail")); } if
   * (choix.equals("annulationMobilite")) { req.getParameter("mail"); if (req.getParameter("mail")
   * == "") { numVersion = statutMobiliteUcc.getNumVersion(email); } else { numVersion =
   * statutMobiliteUcc.getNumVersion(req.getParameter("mail")); }
   * 
   * } if (choix.equals("partenaire")) { String nomLegal = req.getParameter("mail"); numVersion =
   * partenaireUcc.getNumVersion(nomLegal); }
   * 
   * if (choix.equals("etudiant")) { numVersion = etudiantUcc.getNumVersion(email); } if
   * (choix.equals("etudiant_prof")) {
   * 
   * numVersion = etudiantUcc.getNumVersion(req.getParameter("mail"));
   * 
   * } try {
   * 
   * if (numVersion > 0) { String version = String.valueOf(numVersion); String tab[] = {version,
   * choix};
   * 
   * Genson gen = new Genson(); String json = gen.serialize(tab);
   * 
   * resp.getOutputStream().print(json); } else { resp.getOutputStream().print("erreur numVersion "
   * + choix); }
   * 
   * } catch (IOException exception) { exception.printStackTrace(); }
   * 
   * }
   */

  /**
   * Donne les informations à renvoyer au front-end.
   * 
   * @param resp renvoie au back-end
   * @param msg message ok ou non ok
   * @param contentType format
   * @param status statut code
   */
  public void response(HttpServletResponse resp, String msg, String contentType, int status) {
    resp.setStatus(status);
    resp.setContentType(contentType);
    byte[] msgBytes;
    try {
      msgBytes = msg.getBytes("UTF-8");
      resp.setContentLength(msgBytes.length);
      resp.setCharacterEncoding("UTF-8");
      resp.getOutputStream().write(msgBytes);
    } catch (UnsupportedEncodingException exception) {
      MyLogger.getLogger().log(Level.SEVERE, exception.getMessage());
      exception.printStackTrace();
      throw new FatalException();
    } catch (IOException ex) {
      MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
      ex.printStackTrace();
      throw new FatalException();
    }
  }
}
