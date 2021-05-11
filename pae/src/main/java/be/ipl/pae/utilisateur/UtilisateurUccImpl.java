

package be.ipl.pae.utilisateur;

import java.util.ArrayList;
import be.ipl.pae.dal.DalService;
import be.ipl.pae.dal.UtilisateurDao;

public class UtilisateurUccImpl implements UtilisateurUcc {

  private UtilisateurDao utilisateurDao;
  private DalService dalService;

  /**
   * Constructeur.
   * 
   * @param utilisateurDao introduit
   * 
   * @param mobiliteDao introduit
   * @param partDao introduit
   * 
   */
  public UtilisateurUccImpl(UtilisateurDao utilisateurDao, DalService dalService) {
    this.utilisateurDao = utilisateurDao;
    this.dalService = dalService;
  }

  /**
   * Vérifie si la connection peut se faire.
   * 
   * @param mail introduit
   * 
   * @return vrai si l'utilisateur qui veut se connecter est présent dans la base de données
   */
  public UtilisateurDto seConnecter(UtilisateurDto util) {

    try {
      this.dalService.startTransa();
      Utilisateur utilisateur = (Utilisateur) this.utilisateurDao.findByMail(util.getMail());

      if (utilisateur == null) {
        return null;
      }

      if (utilisateur.validateUserForConnection() && utilisateur.checkPwd(util.getMdp())) {
        return utilisateur;
      }
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
    return null;
  }

  public UtilisateurDto getUser(String mail) {
    try {
      this.dalService.startTransa();
      return utilisateurDao.findByMail(mail);
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }



  /**
   * Inscription.
   * 
   * @param mail introduit
   * @param nom introduit
   * @param prenom introduit
   * @param pseudo introduit
   * @param pswd introduit
   * 
   *        s'inscrire sur le site
   * @return l'utilisateur
   */
  public UtilisateurDto inscription(UtilisateurDto utilisateur) {
    try {
      ArrayList<UtilisateurDto> listeInscrits = getInscrits();

      Utilisateur user = new UtilisateurImpl(utilisateur.getMail(), utilisateur.getMdp(),
          utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getPseudo());

      for (UtilisateurDto util : listeInscrits) {
        if (util.getMail().equals(utilisateur.getMail())) {
          System.out.println("Email déjà utilisé");
          return null;
        }
        if (util.getNom().equals(utilisateur.getNom())
            && util.getPrenom().equals(utilisateur.getPrenom())) {
          System.out.println("Nom et prenom déjà utilisé ensemble");
          return null;
        }
        if (util.getPseudo().equals(utilisateur.getPrenom())) {
          System.out.println("Pseudo déjà utilise");
          return null;
        }
      }

      if (user.validateUser()) {
        this.dalService.startTransa();
        user = (Utilisateur) this.utilisateurDao.insertUser(utilisateur);
        this.dalService.commitTransa();
        return user;
      }
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    }
    return null;
  }

  /**
   * ArrayList. recupererEtudiantsSignature
   * 
   * @return une ArrayList
   */
  public ArrayList<UtilisateurDto> recupererEtudiantsSignature() {

    return null;
  }



  /**
   * ArrayList. getInscrits
   * 
   * @return une ArrayList
   */
  public ArrayList<UtilisateurDto> getInscrits() {
    try {
      this.dalService.startTransa();
      ArrayList<UtilisateurDto> users = utilisateurDao.selectInscrits();
      return users;

    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  /**
   * ArrayList. getEtudiants
   * 
   * @return une ArrayList
   */
  public ArrayList<UtilisateurDto> getEtudiants() {
    try {
      this.dalService.startTransa();
      ArrayList<UtilisateurDto> etudiants = utilisateurDao.SelectStudents();
      return etudiants;

    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  /**
   * ArrayList. getNomPays
   * 
   * @return une ArrayList
   */
  public ArrayList<String> getNomPays() {
    try {
      this.dalService.startTransa();
      return utilisateurDao.recupererNomPays();

    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  /**
   * ArrayList. raisonsAnnulation
   * 
   * @return une ArrayList
   */
  public ArrayList<String> raisonsAnnulation() {
    try {
      this.dalService.startTransa();
      ArrayList<String> raisons = utilisateurDao.getRaisons();
      return raisons;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }
}
