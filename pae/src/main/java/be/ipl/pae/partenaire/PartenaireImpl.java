package be.ipl.pae.partenaire;

import com.owlike.genson.Genson;
import util.Util;

public class PartenaireImpl implements Partenaire {

  private String nomLegal;
  private String nomAffaire;
  private String nomComplet;
  private String typeOrganisation;
  private String departement;
  private int nbEmploye;
  private String adresse;
  private String pays;
  private String region;
  private String codePostal;
  private String ville;
  private String email;
  private String siteWeb;
  private String tel;
  private String code;
  private int numVersion;

  /**
   * Constructeur.
   * 
   * @param nomLegal introduit
   * @param nomAffaire introduit
   * @param nomComplet introduit
   * @param departement introduit
   * @param typeOrganisation introduit
   * @param nbEmploye introduit
   * @param adresse introduit
   * @param pays introduit
   * @param region introduit
   * @param codePostal introduit
   * @param ville introduit
   * @param email introduit
   * @param siteWeb introduit
   * @param tel introduit
   * @param code introduit
   * 
   *        Parametre de l'etudiant
   * 
   */
  public PartenaireImpl(String nomLegal, String nomAffaire, String nomComplet, String departement,
      String typeOrganisation, int nbEmploye, String adresse, String pays, String region,
      String codePostal, String ville, String email, String siteWeb, String tel, String code,
      int numVersion) {
    super();
    this.nomLegal = nomLegal;
    this.nomAffaire = nomAffaire;
    this.nomComplet = nomComplet;
    this.departement = departement;
    this.typeOrganisation = typeOrganisation;
    this.nbEmploye = nbEmploye;
    this.adresse = adresse;
    this.pays = pays;
    this.region = region;
    this.codePostal = codePostal;
    this.ville = ville;
    this.email = email;
    this.siteWeb = siteWeb;
    this.tel = tel;
    this.code = code;
    this.numVersion = numVersion;
  }

  public PartenaireImpl() {
    super();
  }

  /**
   * Test.
   * 
   * @return false si vide, null ou mauvais format
   */
  public boolean validateFormPartenaire() {
    if (!Util.validateString(nomLegal) || !Util.validateString(nomAffaire)
        || !Util.validateString(nomComplet) || !Util.validateString(typeOrganisation)
        || !Util.validateString(adresse) || !Util.validateString(region)
        || !Util.validateString(codePostal) || !Util.validateString(ville)
        || !Util.validateEmail(email) || !Util.validateString(siteWeb) || !Util.validateString(tel)
        || !Util.validateString(code) || !Util.validateString(departement)) {
      return false;
    }

    if (!Util.checkPositive(nbEmploye)) {
      return false;
    }
    return true;
  }

  public int getNumVersion() {
    return this.numVersion;
  }

  public void setNumVersion(int numVersion) {
    this.numVersion = numVersion;
  }

  public PartenaireImpl deserialiseur(String json) {
    return new Genson().deserialize(json, PartenaireImpl.class);
  }

  public String getNomLegal() {
    return nomLegal;
  }


  public void setNomLegal(String nomLegal) {
    this.nomLegal = nomLegal;
  }


  public String getNomAffaire() {
    return nomAffaire;
  }


  public void setNomAffaire(String nomAffaire) {
    this.nomAffaire = nomAffaire;
  }

  public String getNomComplet() {
    return nomComplet;
  }


  public void setNomComplet(String nomComplet) {
    this.nomComplet = nomComplet;
  }


  public String getTypeOrganisation() {
    return typeOrganisation;
  }


  public void setTypeOrganisation(String typeOrganisation) {
    this.typeOrganisation = typeOrganisation;
  }


  public int getNbEmploye() {
    return nbEmploye;
  }

  public void setNbEmploye(int nbEmploye) {
    this.nbEmploye = nbEmploye;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }


  public String getPays() {
    return pays;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }


  public String getRegion() {
    return region;
  }


  public void setRegion(String region) {
    this.region = region;
  }


  public String getCodePostal() {
    return codePostal;
  }


  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }


  public String getVille() {
    return ville;
  }


  public void setVille(String ville) {
    this.ville = ville;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getSiteWeb() {
    return siteWeb;
  }


  public void setSiteWeb(String siteWeb) {
    this.siteWeb = siteWeb;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }


  public String getCode() {
    return code;
  }


  public void setCode(String code) {
    this.code = code;
  }

  public String getDepartement() {
    return departement;
  }

  public void setDepartement(String departement) {
    this.departement = departement;
  }

  /**
   * HashCode.
   */
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((tel == null) ? 0 : tel.hashCode());
    return result;
  }

  /**
   * Equals.
   * 
   * @param obj introduit
   * @return true si c'est egal à l'obj
   */
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PartenaireImpl other = (PartenaireImpl) obj;
    if (adresse == null) {
      if (other.adresse != null) {
        return false;
      }
    } else if (!adresse.equals(other.adresse)) {
      return false;
    }
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (tel == null) {
      if (other.tel != null) {
        return false;
      }
    } else if (!tel.equals(other.tel)) {
      return false;
    }
    return true;
  }

  /**
   * toString.
   * 
   * @return toString
   */
  public String toString() {
    return "PartenaireImpl [nomLegal=" + nomLegal + ", nomAffaire=" + nomAffaire + ", nomComplet="
        + nomComplet + ", departement=" + ", typeOrganisation=" + typeOrganisation + ", nbEmploye="
        + nbEmploye + ", adresse=" + adresse + ", pays=" + pays + ", region=" + region
        + ", codePostal=" + codePostal + ", ville=" + ville + ", email=" + email + ", siteWeb="
        + siteWeb + ", tel=" + tel + ", code=" + code + "]";
  }
}
