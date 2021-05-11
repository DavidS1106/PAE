package be.ipl.pae.utilisateur;

public class UtilisateurImplMock implements Utilisateur {

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

  public UtilisateurImplMock(String mail) {
    this.mail = mail;
  }

  public UtilisateurImplMock() {
    super();
  }

  public boolean checkPwd(String password) {
    if (password == null) {
      return false;
    }
    return true;

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
    return false;
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

    return 0;
  }

  @Override
  public boolean equals(Object obj) {

    return false;
  }

  @Override
  public String toString() {
    return "UtilisateurImpl [mail=" + mail + ", mdp=" + mdp + ", nom=" + nom + ", prenom=" + prenom
        + ", pseudo=" + pseudo + ", fonction=" + fonction + "]";
  }

  @Override
  public UtilisateurImpl deserialiseur(String json) {

    return null;
  }

    @Override
  public boolean validateUserForConnection() {
    if (mail == null) {
      return false;
    }
    return true;

  }
}
