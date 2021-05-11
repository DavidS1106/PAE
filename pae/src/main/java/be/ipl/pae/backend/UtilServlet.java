package be.ipl.pae.backend;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import javax.servlet.http.HttpServletResponse;
import exceptions.FatalException;
import log.MyLogger;

public class UtilServlet {

  public UtilServlet() {
    super();
  }

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
