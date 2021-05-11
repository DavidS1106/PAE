package be.ipl.pae.backend;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.servlet.DefaultServlet;
import com.owlike.genson.Genson;
import be.ipl.pae.partenaire.FactoryPartenaire;
import be.ipl.pae.partenaire.PartenaireDto;
import be.ipl.pae.partenaire.PartenaireUcc;

public class PartenaireServlet extends DefaultServlet {
  private PartenaireUcc partenaireUcc;
  private UtilServlet utilServlet;
  private SessionServlet sessionServlet;
  private FactoryPartenaire partenaireFactory;

  public PartenaireServlet(FactoryPartenaire partenaireFactory, PartenaireUcc partenaireUcc,
      SessionServlet sessionServlet, UtilServlet utilServlet) {
    super();
    this.partenaireFactory = partenaireFactory;
    this.partenaireUcc = partenaireUcc;
    this.utilServlet = utilServlet;
    this.sessionServlet = sessionServlet;
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

    if (partenaireRenvoye != null) {
      Genson genson = new Genson();
      String json = genson.serialize(partenaireRenvoye);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }
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
    utilServlet.response(resp, json, "text/html", 200);
  }

  /**
   * Sauvegarde les informations d'un partenaire.
   * 
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   */
  public void saveInfosPartenaire(HttpServletRequest req, HttpServletResponse resp) {

    String json = req.getParameter("json");
    PartenaireDto partenaire = partenaireFactory.creerPartenaire();
    partenaire = partenaire.deserialiseur(json);

    if (this.partenaireUcc.updateInfo(partenaire)) {
      utilServlet.response(resp, "ok", "text/html", 200);
    } else {
      utilServlet.response(resp, "ko", "text/html", 400);
    }
  }

  /**
   * Récupère la liste des partenaires.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recupererListePartenaires(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    ArrayList<String> partenaires = partenaireUcc.getNomsPartenaires();
    if (!partenaires.isEmpty()) {
      Genson gen = new Genson();
      String json = gen.serialize(partenaires);
      utilServlet.response(resp, json, "text/html", 200);
    } else {
      utilServlet.response(resp, "no_result", "text/html", 400);
    }

  }

  /**
   * Rechercher les partenaires.
   * 
   * @param req reçu du front-end
   * @param resp renvoie au front-end
   * @throws IOException input ouput exception
   */
  public void recherchePartenaire(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    String json = req.getParameter("json");
    PartenaireDto partenaire = partenaireFactory.creerPartenaire();
    partenaire = partenaire.deserialiseur(json);

    if (partenaire.getNomLegal().isEmpty() && partenaire.getPays().isEmpty()
        && partenaire.getVille().isEmpty()) {
      utilServlet.response(resp, "empty_input", "text/html", 200);
      return;
    }

    ArrayList<PartenaireDto> recherchePartenaire = partenaireUcc.recherchePartenaire(partenaire);

    if (!recherchePartenaire.isEmpty()) {
      Genson genson = new Genson();
      json = genson.serialize(recherchePartenaire);
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
   * 
   * String nomLegal = req.getParameter("mail"); numVersion = partenaireUcc.getNumVersion(nomLegal);
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
