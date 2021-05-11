package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DalService {


  PreparedStatement getPreparedStatement(String request) throws SQLException;

  void rollBackTransa();

  void commitTransa();

  void startTransa();

}
