package be.ipl.pae.backend;

public interface MessageError {

  public static final byte[] ERROR_MDP = "Erreur mdp".getBytes();
  public static final byte[] ERROR_LOGIN = "Login n'existe pas".getBytes();
  public static final byte[] ERROR_EMAIL = "Erreur email".getBytes();
  public static final byte[] ERROR_INVALID_DATA = "Données invalide".getBytes();

}
