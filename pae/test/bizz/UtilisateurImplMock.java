
package bizz;

import be.ipl.pae.utilisateur.UtilisateurDto;
import be.ipl.pae.utilisateur.UtilisateurImpl;
import util.Util;

public class UtilisateurImplMock implements UtilisateurDto {

  private String mail;
  private String mdp;
  private String nom;
  private String prenom;
  private String pseudo;
  private Fonction fonction;

  public UtilisateurImplMock(String mail, String mdp, String nom, String prenom, String pseudo) {
    this.mail = mail;
    this.mdp = mdp;
    this.nom = nom;
    this.prenom = prenom;
    this.pseudo = pseudo;
  }

  public UtilisateurImplMock() {
    super();
  }

  public boolean checkPwd(String password) {
    if (mdp.equals(password)) {
      return true;
    }
    return false;

  }

  public Fonction getFonction() {
    return this.fonction;
  }

  public void setFonction(Fonction fonction) {
    this.fonction = fonction;
  }

  public boolean isEmpty() {
    return this.equals(new UtilisateurImplMock());
  }

  public boolean validateUser() {
    if (this.mail == null || this.mail.isEmpty() || !Util.validateEmail(mail)) {
      return false;
    }
    if (this.nom == null || this.nom.isEmpty() || this.nom.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    if (this.prenom == null || this.prenom.isEmpty() || this.prenom.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    if (this.pseudo == null || this.pseudo.isEmpty() || this.pseudo.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    if (this.mdp == null || this.mdp.isEmpty() || this.mdp.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    return true;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getMdp() {
    return mdp;
  }

  public void setMdp(String mdp) {
    this.mdp = mdp;
  }

  public String getNom() {
    return nom;
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

  public String getPseudo() {
    return pseudo;
  }

  public void setPseudo(String pseudo) {
    this.pseudo = pseudo;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fonction == null) ? 0 : fonction.hashCode());
    result = prime * result + ((mail == null) ? 0 : mail.hashCode());
    result = prime * result + ((mdp == null) ? 0 : mdp.hashCode());
    result = prime * result + ((nom == null) ? 0 : nom.hashCode());
    result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
    result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
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
    UtilisateurImplMock other = (UtilisateurImplMock) obj;
    if (fonction != other.fonction) {
      return false;
    }
    if (mail == null) {
      if (other.mail != null) {
        return false;
      }
    } else if (!mail.equals(other.mail)) {
      return false;
    }
    if (mdp == null) {
      if (other.mdp != null) {
        return false;
      }
    } else if (!mdp.equals(other.mdp)) {
      return false;
    }
    if (nom == null) {
      if (other.nom != null) {
        return false;
      }
    } else if (!nom.equals(other.nom)) {
      return false;
    }
    if (prenom == null) {
      if (other.prenom != null) {
        return false;
      }
    } else if (!prenom.equals(other.prenom)) {
      return false;
    }
    if (pseudo == null) {
      if (other.pseudo != null) {
        return false;
      }
    } else if (!pseudo.equals(other.pseudo)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "UtilisateurImpl [mail=" + mail + ", mdp=" + mdp + ", nom=" + nom + ", prenom=" + prenom
        + ", pseudo=" + pseudo + ", fonction=" + fonction + "]";
  }

  @Override
  public UtilisateurImpl deserialiseur(String json) {
    // TODO Auto-generated method stub
    return null;
  }
}
