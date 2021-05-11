package be.ipl.pae.partenaire;

public interface PartenaireDto {

  PartenaireImpl deserialiseur(String json);

  int getNumVersion();

  void setNumVersion(int numVersion);

  String getNomLegal();

  void setNomLegal(String nomLegal);

  String getNomAffaire();

  void setNomAffaire(String nomAffaire);

  String getNomComplet();

  void setNomComplet(String nomComplet);

  String getDepartement();

  void setDepartement(String departement);

  String getTypeOrganisation();

  void setTypeOrganisation(String typeOrganisation);

  int getNbEmploye();

  void setNbEmploye(int nbEmploye);

  String getAdresse();

  void setAdresse(String adresse);

  String getPays();

  void setPays(String pays);

  String getRegion();

  void setRegion(String region);

  String getCodePostal();

  void setCodePostal(String codePostal);

  String getVille();

  void setVille(String ville);

  String getEmail();

  void setEmail(String email);

  String getSiteWeb();

  void setSiteWeb(String siteWeb);

  String getTel();

  void setTel(String tel);

  String getCode();

  void setCode(String code);

  int hashCode();

  boolean equals(Object obj);

  String toString();

}
