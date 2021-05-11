package be.ipl.pae.etudiant;

import java.time.LocalDate;
import com.owlike.genson.Genson;
import util.Util;

public class EtudiantImpl implements Etudiant {

  private String statut;
  private String nom;
  private String prenom;
  private LocalDate dateNaissance;
  private String nationalite;
  private String adresse;
  private int numero;
  private int codePostal;
  private String tel;
  private String email;
  private String sexe;
  private int nbrAnneesEtudes;
  private String departement;
  private String numCompteBancaire;
  private String codeBic;
  private String titulaireCompte;
  private String nomBanque;
  private String dateNaissanceaRenvoyer;
  private int numVersion;

  /**
   * Constructeur.
   *
   * @param statut de l'etudiant
   * @param dateNaissance de l'etudiant
   * @param nationalite de l'etudiant
   * @param adresse de l'etudiant
   * @param numero de l'etudiant
   * @param codePostal de l'etudiant
   * @param tel de l'etudiant
   * @param email de l'etudiant
   * @param sexe de l'etudiant
   * @param nbrAnneesEtudes de l'etudiant
   * @param departement de l'etudiant
   * @param numCompteBancaire de l'etudiant
   * @param codeBic de l'etudiant
   * @param titulaireCompte de l'etudiant
   * @param nomBanque de l'etudiant
   * 
   *        Parametres de l'etudiant
   */

  public EtudiantImpl(String statut, LocalDate dateNaissance, String nationalite, String adresse,
      int numero, int codePostal, String tel, String email, String sexe, int nbrAnneesEtudes,
      String departement, String numCompteBancaire, String codeBic, String titulaireCompte,
      String nomBanque) {
    super();
    this.statut = statut;
    this.nom = nom;
    this.prenom = prenom;
    this.dateNaissance = dateNaissance;
    this.nationalite = nationalite;
    this.adresse = adresse;
    this.numero = numero;
    this.codePostal = codePostal;
    this.tel = tel;
    this.email = email;
    this.sexe = sexe;
    this.nbrAnneesEtudes = nbrAnneesEtudes;
    this.departement = departement;
    this.numCompteBancaire = numCompteBancaire;
    this.codeBic = codeBic;
    this.titulaireCompte = titulaireCompte;
    this.nomBanque = nomBanque;
  }

  public EtudiantImpl() {
    super();
  }

  /**
   * Test.
   * 
   * @return false si vide, null ou mauvais format
   */
  public boolean validateFormEtudiant() {
    if (this.statut == null) {
      System.out.println("1");
      return false;
    }

    if (this.dateNaissance == null) {
      System.out.println("2");
      return false;
    }
    if (!Util.validateString(nationalite)) {
      System.out.println("3");

      return false;
    }
    if (!Util.validateString(adresse)) {
      System.out.println("4");

      return false;
    }

    if (!Util.checkPositive(numero)) {
      System.out.println("5");
      return false;
    }
    if (!Util.checkPositive(codePostal)) {
      System.out.println("6");
      return false;
    }
    if (!Util.validateTel(tel) && !Util.validateString(String.valueOf(tel))) {
      System.out.println("7");
      return false;
    }
    if (!Util.validateEmail(email)) {
      System.out.println("8");
      return false;
    }
    if (!Util.validateString(email)) {
      System.out.println("9");
      return false;
    }
    if (sexe == null) {
      System.out.println("10");
      return false;
    }
    if (!Util.checkPositive(nbrAnneesEtudes)
        && !Util.validateString(String.valueOf(nbrAnneesEtudes))) {
      System.out.println("11");

      return false;
    }
    // if (!Util.validateString(departement)) {
    // System.out.println("12");
    //
    // return false;
    // }
    if (!Util.validateCompteBancaire(numCompteBancaire)) {
      System.out.println("13");

      return false;
    }
    if (!Util.validateString(numCompteBancaire)
        && !Util.validateString(String.valueOf(numCompteBancaire))) {
      System.out.println("14");

      return false;
    }
    if (!Util.validateCodeBic(codeBic)) {
      System.out.println("15");

      return false;
    }
    if (!Util.validateString(codeBic)) {
      System.out.println("16");

      return false;
    }
    if (!Util.validateString(titulaireCompte)) {
      System.out.println("17");

      return false;
    }
    if (!Util.validateString(nomBanque)) {
      System.out.println("18");

      return false;
    }
    return true;
  }


  public void setNumVersion(int numVersion) {
    this.numVersion = numVersion;
  }

  public int getNumVersion() {
    return numVersion;
  }

  public String getStatut() {
    return statut;
  }


  public void setStatut(String statut) {
    this.statut = statut;
  }


  public String getNom() {
    return nom;
  }


  public void setDateNaissanceaRenvoyer(String dateNaissanceaRenvoyer) {
    this.dateNaissanceaRenvoyer = dateNaissanceaRenvoyer;
  }

  public String getDateNaissanceaRenvoyer() {
    return dateNaissanceaRenvoyer;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }


  public String getPrenom() {
    return prenom;
  }


  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }


  public LocalDate getDateNaissance() {
    return dateNaissance;
  }


  public void setDateNaissance(LocalDate dateNaissance) {
    this.dateNaissance = dateNaissance;
  }


  public String getNationalite() {
    return nationalite;
  }


  public void setNationalite(String nationalite) {
    this.nationalite = nationalite;
  }


  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }


  public int getNumero() {
    return numero;
  }


  public void setNumero(int numero) {
    this.numero = numero;
  }


  public int getCodePostal() {
    return codePostal;
  }


  public void setCodePostal(int codePostal) {
    this.codePostal = codePostal;
  }


  public String getTel() {
    return tel;
  }


  public void setTel(String tel) {
    this.tel = tel;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getSexe() {
    return sexe;
  }


  public void setSexe(String sexe) {
    this.sexe = sexe;
  }


  public int getNbrAnneesEtudes() {
    return nbrAnneesEtudes;
  }


  public void setNbrAnneesEtudes(int nbrAnneesEtudes) {
    this.nbrAnneesEtudes = nbrAnneesEtudes;
  }


  public String getDepartement() {
    return departement;
  }


  public void setDepartement(String departement) {
    this.departement = departement;
  }


  public String getNumCompteBancaire() {
    return numCompteBancaire;
  }


  public void setNumCompteBancaire(String numCompteBancaire) {
    this.numCompteBancaire = numCompteBancaire;
  }


  public String getCodeBic() {
    return codeBic;
  }


  public void setCodeBic(String codeBic) {
    this.codeBic = codeBic;
  }


  public String getTitulaireCompte() {
    return titulaireCompte;
  }


  public void setTitulaireCompte(String titulaireCompte) {
    this.titulaireCompte = titulaireCompte;
  }


  public String getNomBanque() {
    return nomBanque;
  }

  public EtudiantImpl deserialiseur(String json) {
    return new Genson().deserialize(json, EtudiantImpl.class);
  }

  public void setNomBanque(String nomBanque) {
    this.nomBanque = nomBanque;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((nom == null) ? 0 : nom.hashCode());
    result = prime * result + ((numCompteBancaire == null) ? 0 : numCompteBancaire.hashCode());
    result = prime * result + ((tel == null) ? 0 : tel.hashCode());
    result = prime * result + ((titulaireCompte == null) ? 0 : titulaireCompte.hashCode());
    return result;
  }

  @Override
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
    EtudiantImpl other = (EtudiantImpl) obj;
    if (email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!email.equals(other.email)) {
      return false;
    }
    if (nom == null) {
      if (other.nom != null) {
        return false;
      }
    } else if (!nom.equals(other.nom)) {
      return false;
    }
    if (numCompteBancaire == null) {
      if (other.numCompteBancaire != null) {
        return false;
      }
    } else if (!numCompteBancaire.equals(other.numCompteBancaire)) {
      return false;
    }
    if (tel == null) {
      if (other.tel != null) {
        return false;
      }
    } else if (!tel.equals(other.tel)) {
      return false;
    }
    if (titulaireCompte == null) {
      if (other.titulaireCompte != null) {
        return false;
      }
    } else if (!titulaireCompte.equals(other.titulaireCompte)) {
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
    return "Etudiant [statut=" + statut + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
        + dateNaissance + ", nationalite=" + nationalite + ", adresse=" + adresse + ", numero="
        + numero + ", codePostal=" + codePostal + ", tel=" + tel + ", email=" + email + ", sexe="
        + sexe + ", nbrAnneesEtudes=" + nbrAnneesEtudes + ", departement=" + departement
        + ", numCompteBancaire=" + numCompteBancaire + ", codeBic=" + codeBic + ", titulaireCompte="
        + titulaireCompte + ", nomBanque=" + nomBanque + "]";
  }
}
