package be.ipl.pae.etudiant;

import java.time.LocalDate;

public interface EtudiantDto {


  EtudiantImpl deserialiseur(String json);

  String getStatut();

  void setStatut(String statut);

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setNumVersion(int numVersion);

  int getNumVersion();

  void setPrenom(String prenom);

  LocalDate getDateNaissance();

  void setDateNaissance(LocalDate date);

  String getNationalite();

  void setNationalite(String nationalite);

  String getAdresse();

  void setAdresse(String adresse);

  int getNumero();

  void setNumero(int numero);

  int getCodePostal();

  void setCodePostal(int codePostal);

  String getTel();

  void setTel(String tel);

  String getEmail();

  void setEmail(String email);

  String getSexe();

  void setSexe(String sexe);

  int getNbrAnneesEtudes();

  void setNbrAnneesEtudes(int nbrAnneeEtudes);

  String getDepartement();

  void setDepartement(String departement);

  String getNumCompteBancaire();

  void setNumCompteBancaire(String numCompteBancaire);

  String getCodeBic();

  void setCodeBic(String codeBic);

  String getTitulaireCompte();

  void setTitulaireCompte(String titulaireCompte);

  String getNomBanque();

  void setNomBanque(String nomBanque);

  int hashCode();

  boolean equals(Object obj);

  String toString();

  void setDateNaissanceaRenvoyer(String dateNaissanceaRenvoyer);

  String getDateNaissanceaRenvoyer();
}
