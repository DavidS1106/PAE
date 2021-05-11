package be.ipl.pae.backend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.DefaultServlet;
import com.owlike.genson.Genson;
import be.ipl.pae.etudiant.EtudiantDto;
import be.ipl.pae.etudiant.EtudiantUcc;
import be.ipl.pae.etudiant.FactoryEtudiant;
import be.ipl.pae.utilisateur.UtilisateurDto;
import be.ipl.pae.utilisateur.UtilisateurDto.Fonction;
import be.ipl.pae.utilisateur.UtilisateurUcc;

public class EtudiantServlet extends DefaultServlet {
  private UtilisateurUcc utilisateurUcc;
  private FactoryEtudiant etudiantFactory;
  private EtudiantUcc etudiantUcc;
  private SessionServlet sessionServlet;
  private UtilServlet utilServlet;

  public EtudiantServlet(FactoryEtudiant etudiantFactory, UtilisateurUcc utilisateurUcc,
      EtudiantUcc etudiantUcc, SessionServlet sessionServlet, UtilServlet utilServlet) {
    super();
    this.utilisateurUcc = utilisateurUcc;
    this.etudiantFactory = etudiantFactory;
    this.etudiantUcc = etudiantUcc;
    this.sessionServlet = sessionServlet;
    this.utilServlet = utilServlet;
  }

  /**
   * Récupère les étudiants.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getEtudiant(HttpServletRequest req, HttpServletResponse resp) {
    String email = sessionServlet.getUserMailByCookies(req.getCookies());
    System.out.println(email);
    if (email != null) {
      EtudiantDto etudRenvoye = etudiantUcc.recupererInfo(email);

      String textDate = String.valueOf(etudRenvoye.getDateNaissance());
      etudRenvoye.setDateNaissanceaRenvoyer(textDate);
      Genson genson = new Genson();
      String json = genson.serialize(etudRenvoye);
      utilServlet.response(resp, json, "text/html", 200);

    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }

  }


  /**
   * Sauvegarde les informations de l'étudiant
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws FileNotFoundException file not found exception
   * @throws SQLException sql exception
   */
  public void saveInfos(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    String json = req.getParameter("json");
    LocalDate dateNaissance = null;
    EtudiantDto etud = etudiantFactory.creerEtudiant();

    etud = etud.deserialiseur(json);

    if (!req.getParameter("date_naissance").equals("")) {
      dateNaissance = LocalDate.parse(req.getParameter("date_naissance"));
      etud.setDateNaissance(dateNaissance);
    }
    etud.setEmail(sessionServlet.getUserMailByCookies(req.getCookies()));

    if (this.etudiantUcc.updateInfo(etud)) {
      System.out.println("ok");
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      System.out.println("ko");
      utilServlet.response(resp, "ko_save_etud", "text/html", 400);
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
  public void saveInfosProf(HttpServletRequest req, HttpServletResponse resp) throws SQLException {

    String json = req.getParameter("json");
    LocalDate dateNaissance = null;
    EtudiantDto etud = etudiantFactory.creerEtudiant();

    etud = etud.deserialiseur(json);


    if (!req.getParameter("date_naissance").equals("")) {
      dateNaissance = LocalDate.parse(req.getParameter("date_naissance"));
      etud.setDateNaissance(dateNaissance);
    }

    if (this.etudiantUcc.updateInfo(etud)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Récupérer les données d'un étudiant.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupererDonneesEtudiantSelectionne(HttpServletRequest req,
      HttpServletResponse resp) {

    String email = req.getParameter("email");
    EtudiantDto etudRenvoye = etudiantUcc.recupererInfo(email);
    String textDate = String.valueOf(etudRenvoye.getDateNaissance());
    etudRenvoye.setDateNaissanceaRenvoyer(textDate);

    if (etudRenvoye != null) {
      Genson genson = new Genson();
      String json = genson.serialize(etudRenvoye);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Recherche un étudiant.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void rechercheEtudiant(HttpServletRequest req, HttpServletResponse resp) {

    String json = req.getParameter("json");
    EtudiantDto etud = etudiantFactory.creerEtudiant();
    etud = etud.deserialiseur(json);

    if (etud.getNom().isEmpty() && etud.getPrenom().isEmpty()) {
      utilServlet.response(resp, "empty_input", "text/html", 400);
      return;
    }

    ArrayList<EtudiantDto> rechercheEtudiant = etudiantUcc.rechercheEtudiant(etud);
    System.out.println("re : " + rechercheEtudiant);
    if (!rechercheEtudiant.isEmpty()) {
      Genson genson = new Genson();
      json = genson.serialize(rechercheEtudiant);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      System.out.println("no_result");
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
  }

  /**
   * Récupère les étudiants.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getStudentsProf(HttpServletRequest req, HttpServletResponse resp) {
    ArrayList<EtudiantDto> etudiants = etudiantUcc.getEtudiants();
    Genson genson = new Genson();
    String json = genson.serialize(etudiants);
    utilServlet.response(resp, json, "text/html", 200);
  }


  /**
   * Récupère la fonction d'un utilisateur.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void recupFonction(HttpServletRequest req, HttpServletResponse resp) {
    String mail = sessionServlet.getUserMailByCookies(req.getCookies());
    UtilisateurDto utilisateur = utilisateurUcc.getUser(mail);
    if (utilisateur.getFonction() == Fonction.E) {
      utilServlet.response(resp, "choixMobilite", "text/html", 200);
    } else {
      utilServlet.response(resp, "confirmerMobiliteProf", "text/html", 200);
    }
  }

  /**
   * Récupères les inscrits.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getInscrits(HttpServletRequest req, HttpServletResponse resp) {
    ArrayList<UtilisateurDto> utilisateurs = utilisateurUcc.getEtudiants();
    Genson genson = new Genson();
    String json = genson.serialize(utilisateurs);
    utilServlet.response(resp, json, "text/html", 200);
  }

  /**
   * Récupères les raisons d'annulation.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void getRaisons(HttpServletRequest req, HttpServletResponse resp) {
    ArrayList<String> raisons = utilisateurUcc.raisonsAnnulation();
    Genson genson = new Genson();
    String json = genson.serialize(raisons);
    utilServlet.response(resp, json, "text/html", 200);
  }

  /**
   * Recupere les numeros de version pour l'optimistic lock.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupNumVersion(HttpServletRequest req, HttpServletResponse resp) {
    String choix = req.getParameter("choix");
    String email = sessionServlet.getUserMailByCookies(req.getCookies());
    int numVersion = 0;

    if (choix.equals("etudiant")) {

      /* numVersion = etudiantUcc.getNumVersion(email); */
    }
    if (choix.equals("etudiant_prof")) {

      /* numVersion = etudiantUcc.getNumVersion(req.getParameter("mail")); */

    }
    try {

      if (numVersion > 0) {
        String version = String.valueOf(numVersion);
        String tab[] = {version, choix};

        Genson gen = new Genson();
        String json = gen.serialize(tab);

        resp.getOutputStream().print(json);
      } else {
        resp.getOutputStream().print("erreur numVersion " + choix);
      }

    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
}
