package be.ipl.pae.utilisateur;

interface Utilisateur extends UtilisateurDto {

  boolean checkPwd(String password);

  boolean validateUser();

  boolean validateUserForConnection();
}
