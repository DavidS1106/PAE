package be.ipl.pae.backend;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class Serveur {

  /**
   * Lance le serveur.
   * 
   * @param port le port sur lequelle se lance le serveur
   * @param statutMobiliteUcc introduit
   * @param etudiantUcc introduit
   * @param utilisateurUcc introduit
   * @throws Exception à lancer lors d'erreur
   */
  public static void demarrerServeur(int port, MainServlet servlet) throws Exception {
    WebAppContext context = new WebAppContext();
    final Server server = new Server(port);
    // context.addServlet(new ServletHolder(servlet), "/Authentification");

    // String[] tab = new String[1];
    // tab[0] = "SinglePage.html";
    // context.setWelcomeFiles(tab);
    context.setInitParameter("cacheControl", "no-store, no-cache,must-revalidate");
    context.setResourceBase("www");
    context.addServlet(new ServletHolder(servlet), "/");
    server.setHandler(context);
    server.start();
  }
}


