package be.ipl.pae.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.DefaultServlet;
import exceptions.FatalException;
import log.MyLogger;


public class MainServlet extends DefaultServlet {

  private static final long serialVersionUID = 1L;

  /*
   * JWTVerifier verifier = JWT.require(Algorithm.HMAC256(Base64.getDecoder().decode(SECRET)))
   * .withIssuer("auth0") .acceptLeeway(1) .acceptExpiresAt(5 * 60) .build();
   */

  // mettre l'id de l'etudiant dans un token le token sera notre badge pour
  // chaque requete pour qu'il sache qui c'est.


  private MobiliteServlet mobiliteServlet;
  private SessionServlet sessionServlet;
  private EtudiantServlet etudiantServlet;
  private PartenaireServlet partenaireServlet;
  private StatutMobiliteServlet statutMobiliteServlet;
  private UtilServlet utilServlet;
  private Logger logger;

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
  /*
   * public MainServlet(FactoryUtilisateur utilisateurFactory, FactoryEtudiant etudiantFactory,
   * FactoryPartenaire partenaireFactory, FactoryMobilite mobiliteFactory, MobiliteUcc mobiliteUcc,
   * StatutMobiliteUcc statutMobiliteUcc, UtilisateurUcc utilisateurUcc, EtudiantUcc etudiantUcc,
   * PartenaireUcc partenaireUcc) { super(); }
   */

  public MainServlet(MobiliteServlet mobiliteServlet, SessionServlet sessionServlet,
      EtudiantServlet etudiantServlet, PartenaireServlet partenaireServlet,
      StatutMobiliteServlet statutMobiliteServlet, UtilServlet utilServlet) {
    super();
    this.mobiliteServlet = mobiliteServlet;
    this.sessionServlet = sessionServlet;
    this.etudiantServlet = etudiantServlet;
    this.partenaireServlet = partenaireServlet;
    this.statutMobiliteServlet = statutMobiliteServlet;
    this.utilServlet = utilServlet;
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String action = req.getParameter("action");

    switch (action) {
      case "login":
        sessionServlet.login(req, resp);
        break;
      case "logout":
        sessionServlet.logout(req, resp);
        break;
      case "refresh":
        sessionServlet.refresh(req, resp);
        break;
      case "inscription":
        sessionServlet.inscription(req, resp);
        break;
      case "avoirMobilites":
        mobiliteServlet.avoirMobilites(req, resp);
        break;
      case "get_etudiant":
        etudiantServlet.getEtudiant(req, resp);
        break;
      case "recupererDonneesPartenaireSelectionne":
        partenaireServlet.recupererDonneesPartenaireSelectionne(req, resp);
        break;
      case "get_partenaires_list":
        partenaireServlet.getPartenaires(req, resp);
        break;
      case "save_info_part":
        partenaireServlet.saveInfosPartenaire(req, resp);
        break;
      case "save_info":
        try {
          etudiantServlet.saveInfos(req, resp);
        } catch (SQLException ex) {
          MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
          ex.printStackTrace();
          throw new FatalException();
        }
        break;
      case "save_infos_prof":
        try {
          etudiantServlet.saveInfosProf(req, resp);
        } catch (SQLException ex) {
          MyLogger.getLogger().log(Level.SEVERE, ex.getMessage());
          ex.printStackTrace();
          throw new FatalException();
        }
        break;
      case "annulationMobilite":
        mobiliteServlet.annulationMobilite(req, resp);
        break;
      case "etatDocuments":
        statutMobiliteServlet.etatDocuments(req, resp);
        break;
      case "updateEtatDocuments":
        statutMobiliteServlet.updateEtatDocuments(req, resp);
        break;
      case "recupererEtudiantSelectionne":
        etudiantServlet.recupererDonneesEtudiantSelectionne(req, resp);
        break;
      case "ajouterMobilite":
        mobiliteServlet.insererMobilite(req, resp);
        break;
      case "ajouterMobiliteComplet":
        mobiliteServlet.insererMobiliteComplet(req, resp);
        break;
      case "modifierMobilite":
        mobiliteServlet.recupererMobilite(req, resp);
        break;
      case "recupererListePays":
        mobiliteServlet.recupererListePays(req, resp);
        break;
      case "recupererListePartenaires":
        partenaireServlet.recupererListePartenaires(req, resp);
        break;
      case "recupFonction":
        etudiantServlet.recupFonction(req, resp);
        break;
      case "confirmerMobilite":
        mobiliteServlet.confirmerMobilite(req, resp);
        break;
      case "chargerMobiliteExistante":
        mobiliteServlet.chargerMobiliteExistante(req, resp);
        break;
      case "chargerMobiliteNonExistante":
        mobiliteServlet.chargerMobiliteNonExistante(req, resp);
        break;
      case "rechercheEtudiant":
        etudiantServlet.rechercheEtudiant(req, resp);
        break;
      case "recherchePartenaire":
        partenaireServlet.recherchePartenaire(req, resp);
        break;
      case "rechercheMobilite":
        mobiliteServlet.rechercheMobilite(req, resp);
        break;
      case "modifierMob":
        mobiliteServlet.modifierMob(req, resp);
        break;
      case "getRaisons":
        etudiantServlet.getRaisons(req, resp);
        break;
      case "getStudentsProf":
        etudiantServlet.getStudentsProf(req, resp);
        break;
      case "getInscrits":
        etudiantServlet.getInscrits(req, resp);
        break;
      case "recupEtudiantMobilite":
        mobiliteServlet.recupEtudiantMobilite(req, resp);
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
    page += ajouterPage("ConfirmerMobiliteProf.html");
    page += ajouterPage("EtatDesDocumentsEtudiants.html");
    page += ajouterPage("EtatDesDocumentsProfs.html");
    page += ajouterPage("InfosEtudiants.html");
    page += ajouterPage("InfosEtudiantsProfs.html");
    page += ajouterPage("InfosPartenaires.html");
    page += ajouterPage("recherche.html");
    page += ajouterPage("rechercheProf.html");
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

  private void fichierPage(HttpServletResponse resp, String page) {
    utilServlet.response(resp, page, "text/html", 200);
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

}

