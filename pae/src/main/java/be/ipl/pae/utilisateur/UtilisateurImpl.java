package be.ipl.pae.utilisateur;

import org.mindrot.bcrypt.BCrypt;
import com.owlike.genson.Genson;
import util.Util;

// test 2 a,bfwsvnjk
public class UtilisateurImpl implements Utilisateur {

  private String mail;
  private String mdp;
  private String nom;
  private String prenom;
  private String pseudo;
  private Fonction fonction;

  /**
   * Constructeur.
   *
   * @param mail de l'utilisateur
   * @param mdp de l'utilisateur
   * @param nom de l'utilisateur
   * @param prenom de l'utilisateur
   * @param pseudo de l'utilisateur
   */
  public UtilisateurImpl(String mail, String mdp, String nom, String prenom, String pseudo) {
    this.mail = mail;
    this.mdp = mdp;
    this.nom = nom;
    this.prenom = prenom;
    this.pseudo = pseudo;
  }

  public UtilisateurImpl() {
    super();
  }

  public boolean checkPwd(String password) {
    mdp = BCrypt.hashpw(mdp, BCrypt.gensalt());
    return BCrypt.checkpw(password, mdp);
  }

  public UtilisateurImpl deserialiseur(String json) {
    return new Genson().deserialize(json, UtilisateurImpl.class);
  }

  /**
   * Vérifie que le mail et le mot de passe sont corrects.
   * 
   * @return renvoie vrai si c'est le cas
   */
  public boolean validateUserForConnection() {
    if (this.mail == null || this.mail.equals("") || !Util.validateEmail(mail)) {
      if (this.mdp == null || this.mdp.equals("") || !Util.validateString(mdp)) {
        return false;
      }
      return false;
    }
    return true;
  }

  public boolean isEmpty() {
    return this.equals("");
  }

  /**
   * Vérifie que les attributes de l'utilisateur sont corrects.
   * 
   * @return vrai si tout est bons
   */
  public boolean validateUser() {
    if (this.mail == null || this.mail.isEmpty() || !Util.validateEmail(mail)) {
      return false;
    }
    if (this.pseudo == null || this.pseudo.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    if (this.nom == null || this.nom.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    if (this.prenom == null || this.prenom.matches(Util.REGEX_EMPTY)) {
      return false;
    }
    if (this.mdp == null || this.mdp.matches(Util.REGEX_EMPTY)) {
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

  public Fonction getFonction() {
    return this.fonction;
  }

  public void setFonction(Fonction fonction) {
    this.fonction = fonction;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((fonction == null) ? 0 : fonction.hashCode());
    result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
    result = prime * result + ((mail == null) ? 0 : mail.hashCode());
    result = prime * result + ((mdp == null) ? 0 : mdp.hashCode());
    result = prime * result + ((nom == null) ? 0 : nom.hashCode());
    result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
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
    UtilisateurImpl other = (UtilisateurImpl) obj;
    if (fonction != other.fonction) {
      return false;
    }
    if (pseudo == null) {
      if (other.pseudo != null) {
        return false;
      }
    } else if (!pseudo.equals(other.pseudo)) {
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
    return true;
  }

  @Override
  public String toString() {
    return "UtilisateurImpl [mail=" + mail + ", mdp=" + mdp + ", nom=" + nom + ", prenom=" + prenom
        + ", pseudo=" + pseudo + ", fonction=" + fonction + "]";
  }


}
