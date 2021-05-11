package be.ipl.pae.backend;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.statutmobilite.FactoryStatutMobilite;
import be.ipl.pae.statutmobilite.StatutMobiliteDto;
import be.ipl.pae.statutmobilite.StatutMobiliteUcc;

public class StatutMobiliteServlet {
  private StatutMobiliteUcc statutMobiliteUcc;
  private SessionServlet sessionServlet;
  private UtilServlet utilServlet;
  private FactoryStatutMobilite statutMobiliteFactory;

  public StatutMobiliteServlet(FactoryStatutMobilite statutMobiliteFactory,
      StatutMobiliteUcc statutMobiliteUcc, SessionServlet sessionServlet, UtilServlet utilServlet) {
    super();
    this.statutMobiliteFactory = statutMobiliteFactory;
    this.statutMobiliteUcc = statutMobiliteUcc;
    this.sessionServlet = sessionServlet;
    this.utilServlet = utilServlet;
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
      mail = sessionServlet.getUserMailByCookies(req.getCookies());
    }
    StatutMobiliteDto etatDocuments = statutMobiliteUcc.getEtatDocuments(mail);
    if (etatDocuments != null) {
      Genson genson = new Genson();
      String json = genson.serialize(etatDocuments);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
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
    String json = req.getParameter("json");
    StatutMobiliteDto statutMobilite = statutMobiliteFactory.creerStatutMobilite();
    statutMobilite = statutMobilite.deserialiseur(json);

    if (statutMobiliteUcc.updateEtatDocuments(mail, statutMobilite)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /*
   * public void recupNumVersion(HttpServletRequest req, HttpServletResponse resp) throws
   * IOException { String choix = req.getParameter("choix"); String email =
   * sessionServlet.getUserMailByCookies(req.getCookies()); int numVersion = 0;
   * 
   * numVersion = statutMobiliteUcc.getNumVersion(req.getParameter("mail"));
   * 
   * 
   * try {
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
