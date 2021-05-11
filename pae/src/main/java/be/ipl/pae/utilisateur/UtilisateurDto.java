package be.ipl.pae.utilisateur;

public interface UtilisateurDto {
  enum Fonction {
    E, P, PR
  }

  UtilisateurImpl deserialiseur(String json);

  String getMail();

  void setMail(String mail);

  String getMdp();

  void setMdp(String mdp);

  String getNom();

  void setNom(String nom);

  String getPrenom();

  void setPrenom(String prenom);

  String getPseudo();

  void setPseudo(String pseudo);

  Fonction getFonction();

  void setFonction(Fonction fonction);
}
