package be.ipl.pae.etudiant;

public interface Etudiant extends EtudiantDto {

  boolean validateFormEtudiant();


  EtudiantImpl deserialiseur(String json);


}
