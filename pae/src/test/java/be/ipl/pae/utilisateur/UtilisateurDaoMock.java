package be.ipl.pae.utilisateur;

import java.util.ArrayList;
import be.ipl.pae.dal.UtilisateurDao;

public class UtilisateurDaoMock implements UtilisateurDao {



  /**
   * Constructeur.
   * 
   * @param dalService introduit
   * @param utilisateurFactory introduit
   * @param mobiliteFactory introduit
   */
  public UtilisateurDaoMock() {

  }

  /**
   * Cherche un utilisateur par rapport à un login.
   * 
   * @param mail d'un utilisateur
   * @return renvoie un user rempli avec les informations d'un inscrit dont le mail correspond
   */
  public ArrayList<String> recupererNomPays() {

    ArrayList<String> array = new ArrayList<String>();

    return array;
  }

  /**
   * FindByMail.
   * 
   * @param mail introduit
   * @return l'utilisateur ayant le mail passe en parametre
   */
  public UtilisateurDto findByMail(String mail) {
    if (mail == null) {
      return null;
    }
    UtilisateurDto user = new UtilisateurImplMock(mail);

    return user;
  }

  /**
   * Cherche un utilisateur par rapport à un login.
   * 
   * @param mail d'un utilisateur
   * @return renvoie un user rempli avec les informations d'un inscrit dont le mail correspond
   */
  public UtilisateurDto insertUser(UtilisateurDto utilisateur) {
    if (utilisateur.getMail() == null || utilisateur.getMdp() == null
        || utilisateur.getNom() == null || utilisateur.getMdp() == null
        || utilisateur.getPseudo() == null) {
      return null;
    }
    UtilisateurDto user = new UtilisateurImplMock(utilisateur.getMail(), utilisateur.getMdp(),
        utilisateur.getNom(), utilisateur.getMdp(), utilisateur.getPseudo());

    return user;

  }

  /**
   * Liste.
   * 
   * @return une arrayList des etudiants
   */
  public ArrayList<UtilisateurDto> selectEtudiantsSignature() {
    ArrayList<UtilisateurDto> array = new ArrayList<UtilisateurDto>();


    return array;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des inscrits
   */
  public ArrayList<UtilisateurDto> selectInscrits() {

    ArrayList<UtilisateurDto> list = new ArrayList<UtilisateurDto>();


    return list;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des etudiants
   */
  public ArrayList<UtilisateurDto> SelectStudents() {

    ArrayList<UtilisateurDto> list = new ArrayList<UtilisateurDto>();

    return list;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des raisons
   */
  public ArrayList<String> getRaisons() {


    return null;
  }
}


