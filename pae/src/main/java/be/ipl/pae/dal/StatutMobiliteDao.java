package be.ipl.pae.dal;

import be.ipl.pae.statutmobilite.StatutMobiliteDto;

public interface StatutMobiliteDao {

  void annulerMobiliteParEtudiant(String mail, String raison, int version);

  void annulerMobiliteParProfesseur(String mail, String idRaison, int version);

  StatutMobiliteDto getEtatDocuments(String mail);

  boolean updateEtatDocuments(String mail, StatutMobiliteDto statutMobilite, int version);

  boolean confirmerMobilite(int idMobilite, String email);

  public int getNumVersion(String mail);
}
