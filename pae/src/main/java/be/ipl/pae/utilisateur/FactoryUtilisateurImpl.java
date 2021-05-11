package be.ipl.pae.utilisateur;

public class FactoryUtilisateurImpl implements FactoryUtilisateur {

  public UtilisateurDto creerUtilisateur() {
    UtilisateurDto utilisateur = new UtilisateurImpl();
    return utilisateur;
  }
}
