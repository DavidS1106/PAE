package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import be.ipl.pae.dal.DalService;

public class DalServiceImplMock implements DalService {


  // Enlever les paramètres.
  public DalServiceImplMock() {

  }



  /**
   * Initialisation du PreparedStatement.
   * 
   * @param request la requête
   * @return renvoie un PreparedStatement avec la requête
   * @throws SQLException
   */
  public PreparedStatement getPreparedStatement(String request) throws SQLException {
    return null;
  }

  public void startTransa() {

  }

  public void commitTransa() {

  }

  public synchronized void rollBackTransa() {

  }
}
