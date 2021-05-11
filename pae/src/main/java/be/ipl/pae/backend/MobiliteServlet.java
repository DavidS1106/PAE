package be.ipl.pae.backend;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.DefaultServlet;
import com.owlike.genson.Genson;
import be.ipl.pae.mobilite.FactoryMobilite;
import be.ipl.pae.mobilite.MobiliteDto;
import be.ipl.pae.mobilite.MobiliteUcc;
import be.ipl.pae.statutmobilite.StatutMobiliteUcc;
import be.ipl.pae.utilisateur.UtilisateurUcc;

public class MobiliteServlet extends DefaultServlet {
  private MobiliteUcc mobiliteUcc;
  private UtilisateurUcc utilisateurUcc;
  private StatutMobiliteUcc statutMobiliteUcc;
  private SessionServlet sessionServlet;
  private MainServlet mainServlet;
  private UtilServlet utilServlet;
  private FactoryMobilite mobiliteFactory;

  public MobiliteServlet(FactoryMobilite mobiliteFactory, MobiliteUcc mobiliteUcc,
      UtilisateurUcc utilisateurUcc, StatutMobiliteUcc statutMobiliteUcc,
      SessionServlet sessionServlet, UtilServlet utilServlet) {
    super();
    this.mobiliteUcc = mobiliteUcc;
    this.utilisateurUcc = utilisateurUcc;
    this.statutMobiliteUcc = statutMobiliteUcc;
    this.sessionServlet = sessionServlet;
    this.utilServlet = utilServlet;
    this.mobiliteFactory = mobiliteFactory;
  }

  /**
   * modifier une mobilite
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void modifierMob(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    int idMobilite = Integer.parseInt(req.getParameter("id"));


    String email = sessionServlet.getUserMailByCookies(req.getCookies());
    String json = req.getParameter("json");
    MobiliteDto mobilite = mobiliteFactory.creerMobilite();
    mobilite = mobilite.deserialiseur(json);
    mobilite.setMail(email);
    mobilite.setId_mobilite(idMobilite);
    System.out.println("m : " + mobilite);

    if (mobiliteUcc.updateMob(mobilite, idMobilite)) {
      System.out.println("ok");
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      System.out.println("ko");
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }



  /**
   * Récupère les mobilités.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void avoirMobilites(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    ArrayList<MobiliteDto> array;
    array = mobiliteUcc.recupererMobilites();
    Genson genson = new Genson();

    /*
     * .useDateAsTimestamp(false) .useDateFormat(new
     * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")) .create()
     */
    // System.out.println(array.get(0));
    String json = genson.serialize(array);
    utilServlet.response(resp, json, "text/html", 200);
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

    if (mail == null) {
      mail = sessionServlet.getUserMailByCookies(req.getCookies());
    }

    if (statutMobiliteUcc.annuler(mail, raison, idRaison)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Insere une mobilite.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void insererMobilite(HttpServletRequest req, HttpServletResponse resp) {
    // int preference = Integer.parseInt(req.getParameter("preference"));
    // String partenaire = req.getParameter("partenaire");
    // String pays = req.getParameter("pays");
    // int quadrimestre = Integer.parseInt(req.getParameter("quadrimestre"));
    // LocalDate dateIntroduction = LocalDate.now();
    // String anneAcad = String.valueOf(LocalDate.now());// A MODIFIER !!
    // String email = sessionServlet.getUserMailByCookies(req.getCookies());

    String json = req.getParameter("json");
    MobiliteDto mobilite = mobiliteFactory.creerMobilite();
    mobilite = mobilite.deserialiseur(json);
    mobilite.setAnneeAcademique(String.valueOf(LocalDate.now()));
    mobilite.setMail(sessionServlet.getUserMailByCookies(req.getCookies()));
    mobilite.setDateIntro(LocalDate.now());

    if (mobiliteUcc.insererMob(mobilite)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * confirmer une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void confirmerMobilite(HttpServletRequest req, HttpServletResponse resp) {
    String email = sessionServlet.getUserMailByCookies(req.getCookies());
    int idMobilite = Integer.parseInt(req.getParameter("idMobilite"));
    if (statutMobiliteUcc.confirmerMobilite(idMobilite, email)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  public void confirmerMobiliteExistant(HttpServletRequest req, HttpServletResponse resp) {
    String email = req.getParameter("email");
    int idMobilite = Integer.parseInt(req.getParameter("idMobilite"));
    System.out.println("confirmerMobiliteExistante  mail :" + email);
    if (statutMobiliteUcc.confirmerMobilite(idMobilite, email)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      System.out.println("confiEx failed");
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Insère une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void insererMobiliteComplet(HttpServletRequest req, HttpServletResponse resp) {

    // int preference = Integer.parseInt(req.getParameter("preference"));
    // String partenaire = req.getParameter("partenaire");
    String programme = req.getParameter("programme");
    // String code = req.getParameter("code");
    // String pays = req.getParameter("pays");
    // int quadrimestre = Integer.parseInt(req.getParameter("quadrimestre"));
    // LocalDate dateIntroduction = LocalDate.now();
    // String anneAcad = String.valueOf(LocalDate.now());// A MODIFIER !!
    // String email = sessionServlet.getUserMailByCookies(req.getCookies());

    String json = req.getParameter("json");
    MobiliteDto mobilite = mobiliteFactory.creerMobilite();
    mobilite = mobilite.deserialiseur(json);
    mobilite.setAnneeAcademique(String.valueOf(LocalDate.now()));
    mobilite.setMail(sessionServlet.getUserMailByCookies(req.getCookies()));
    mobilite.setDateIntro(LocalDate.now());

    // CHECK PAYS ET PROGRAMME

    if (mobiliteUcc.insererMobComplet(mobilite, programme)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Modifie une mobilité.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input output exception
   */
  public void recupererMobilite(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String email = sessionServlet.getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mobRenvoye = mobiliteUcc.recupererMob(email);

    if (!mobRenvoye.isEmpty()) {
      Genson genson = new Genson();
      String json = genson.serialize(mobRenvoye);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
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
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
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
      utilServlet.response(resp, "empty_input", "text/html", 400);
      return;
    }

    ArrayList<MobiliteDto> rechercheMobilite = mobiliteUcc.rechercheMobilite(annee, etat);
    if (!rechercheMobilite.isEmpty()) {
      Genson genson = new Genson();
      String json = genson.serialize(rechercheMobilite);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
  }

  /**
   * Récupère les mobilite.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  void recupEtudiantMobilite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String mail = sessionServlet.getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mobilites = mobiliteUcc.recupStudMob(mail);
    Genson g = new Genson();
    String json = g.serialize(mobilites);
    utilServlet.response(resp, json, "text/html", 200);
  }

  /**
   * recuperer les mobilites
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void chargerMobiliteExistante(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String mail = sessionServlet.getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mobilites = mobiliteUcc.recupStudMob(mail);
    for (MobiliteDto m : mobilites) {
      m.setMail(mail);
    }
    if (!mobilites.isEmpty()) {
      Genson genson = new Genson();
      String json = genson.serialize(mobilites);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
  }

  /**
   * recuperer les mobilites
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void chargerMobiliteNonExistante(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    String email = sessionServlet.getUserMailByCookies(req.getCookies());
    ArrayList<MobiliteDto> mRenvoye = mobiliteUcc.recupererMobNonExistante(email);

    if (!mRenvoye.isEmpty()) {
      Genson g = new Genson();
      String json = g.serialize(mRenvoye);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
  }

  /*
   * public void recupNumVersion(HttpServletRequest req, HttpServletResponse resp) throws
   * IOException { String choix = req.getParameter("choix"); String email =
   * sessionServlet.getUserMailByCookies(req.getCookies()); int numVersion = 0;
   * 
   * if (choix.equals("modifierMob")) { int idMob = Integer.parseInt(req.getParameter("mail"));
   * numVersion = mobiliteUcc.getNumVersion(idMob); } if (choix.equals("annulationMobilite")) { if
   * (req.getParameter("mail").equals("")) { System.out.println("email anulation " + email);
   * numVersion = statutMobiliteUcc.getNumVersion(email); } else { numVersion =
   * statutMobiliteUcc.getNumVersion(req.getParameter("mail")); } } try {
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


}
