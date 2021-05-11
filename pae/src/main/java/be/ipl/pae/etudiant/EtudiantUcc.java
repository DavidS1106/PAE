package be.ipl.pae.etudiant;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EtudiantUcc {

  EtudiantDto recupererInfo(String email);

  boolean updateInfo(EtudiantDto etud) throws SQLException;

  EtudiantDto recupererInfoViaNom(String nom);

  ArrayList<EtudiantDto> getEtudiants();

  // int getNumVersion(String email);

  ArrayList<EtudiantDto> rechercheEtudiant(EtudiantDto etud);
}
