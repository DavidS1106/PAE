package be.ipl.pae.statutmobilite;

public interface StatutMobiliteUcc {

  boolean annuler(String mail, String raison, String idRaison);

  StatutMobiliteDto getEtatDocuments(String mail);

  boolean updateEtatDocuments(String mail, StatutMobiliteDto statutMobilite);

  boolean confirmerMobilite(int idMobilite, String email);
}
