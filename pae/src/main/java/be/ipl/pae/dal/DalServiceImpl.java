package be.ipl.pae.dal;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.commons.dbcp2.BasicDataSource;
import exceptions.FatalException;
import log.MyLogger;
import util.Util;


public class DalServiceImpl implements DalService {
  // private Connection conn = null;
  Properties prop = new Properties();
  InputStream input = null;

  private static ThreadLocal<Connection> threadConnection = new ThreadLocal<Connection>();

  private static BasicDataSource pool;

  // Enlever les paramètres.
  public DalServiceImpl() {
    Util.loadProperties(prop, input);
    String driver = prop.getProperty("driverDB");
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException exception) {
      MyLogger.getLogger().log(Level.SEVERE, "Driver PostgreSQL manquant !");
      System.exit(1);
    }
    pool = new BasicDataSource();
    pool.setDriverClassName(driver);
    pool.setUsername(prop.getProperty("loginDB"));
    pool.setPassword(prop.getProperty("passwordDB"));
    pool.setUrl(prop.getProperty("urlDB"));
    pool.setMaxTotal(-1);
    pool.setMaxOpenPreparedStatements(-1);
    pool.setMaxIdle(-1);
  }



  /**
   * Initialisation du PreparedStatement.
   * 
   * @param request la requête
   * @return renvoie un PreparedStatement avec la requête
   * @throws SQLException
   */
  public PreparedStatement getPreparedStatement(String request) throws SQLException {

    return threadConnection.get().prepareStatement(request);
  }

  public void startTransa() {
    try {
      if (threadConnection.get() == null) {
        threadConnection.set(pool.getConnection());
      }
      threadConnection.get().setAutoCommit(false);
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException();
    }
  }

  public void commitTransa() {
    try {
      if (threadConnection.get() != null) {
        threadConnection.get().commit();
        threadConnection.get().setAutoCommit(true);
        threadConnection.get().close();
        threadConnection.set(null);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException();
    }
  }

  public synchronized void rollBackTransa() {
    try {
      if (threadConnection.get() != null) {
        threadConnection.get().rollback();
        threadConnection.get().setAutoCommit(true);
        threadConnection.get().close();
        threadConnection.set(null);
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      throw new FatalException();
    }
  }

}

