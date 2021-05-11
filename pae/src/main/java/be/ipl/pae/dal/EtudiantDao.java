package be.ipl.pae.dal;

import java.sql.SQLException;
import java.util.ArrayList;
import be.ipl.pae.etudiant.EtudiantDto;

public interface EtudiantDao {

  EtudiantDto findByEmail(String email);

  boolean updateInfoEtudiant(EtudiantDto etud) throws SQLException;

  EtudiantDto findByName(String name);

  ArrayList<EtudiantDto> selectEtudiants();

  int getNumversion(String email);

  ArrayList<EtudiantDto> rechercheEtudiant(EtudiantDto etud);
}
