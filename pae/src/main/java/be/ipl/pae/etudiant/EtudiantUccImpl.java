package be.ipl.pae.etudiant;

import java.sql.SQLException;
import java.util.ArrayList;
import be.ipl.pae.dal.DalService;
import be.ipl.pae.dal.EtudiantDao;

public class EtudiantUccImpl implements EtudiantUcc {

  private EtudiantDao etudiantDao;
  private DalService dalService;

  /**
   * Constructeur.
   * 
   * @param etudiantDao
   * 
   *        constructeur
   */
  public EtudiantUccImpl(EtudiantDao etudiantDao, DalService dalService) {
    this.etudiantDao = etudiantDao;
    this.dalService = dalService;
  }

  /**
   * Methode. Methode pour recuperer les infos de l'etudiant
   * 
   * @return l'etudiant ayant le mail passe en parametre
   */
  public EtudiantDto recupererInfo(String email) {
    try {
      this.dalService.startTransa();
      if (email == null) {
        return null;
      }
      EtudiantDto etud = (EtudiantDto) this.etudiantDao.findByEmail(email);
      return etud;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }


  /**
   * Methode.
   * 
   * @return l'etudiant ayant le nom passe en parametre
   */
  public EtudiantDto recupererInfoViaNom(String nom) {
    try {
      this.dalService.startTransa();
      if (nom == null) {
        return null;
      }
      EtudiantDto etud = (EtudiantDto) this.etudiantDao.findByName(nom);
      return etud;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  /**
   * Methode.
   * 
   * @return l'etudiant en mettant à jour ces infos
   */
  public boolean updateInfo(EtudiantDto etud) throws SQLException {
    try {
      this.dalService.startTransa();
      // Etudiant etudiant = (Etudiant) this.etudiantDao.findByEmail(etud.getEmail());
      etud.setNumVersion(etudiantDao.getNumversion(etud.getEmail()));
      // if (etudiant.validateFormEtudiant()) {
      System.out.println("numVersion" + etud.getNumVersion());
      boolean update = this.etudiantDao.updateInfoEtudiant(etud);
      // this.dalService.commitTransa();
      return update;
      // } else {
      // return false;
      // }
      // return this.etudiantDao.updateInfoEtudiant(etud);
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }


  /*
   * public int getNumVersion(String email) { try { this.dalService.startTransa(); int version =
   * etudiantDao.getNumversion(email); return version; } catch (Exception exc) {
   * this.dalService.rollBackTransa(); throw exc; } finally { this.dalService.commitTransa(); }
   * 
   * }
   */

  /**
   * Liste.
   * 
   * @return ArrayList
   */
  public ArrayList<EtudiantDto> getEtudiants() {
    try {
      this.dalService.startTransa();
      ArrayList<EtudiantDto> etudiants = etudiantDao.selectEtudiants();
      return etudiants;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  public ArrayList<EtudiantDto> rechercheEtudiant(EtudiantDto etud) {
    try {
      this.dalService.startTransa();
      ArrayList<EtudiantDto> listeRecherche = etudiantDao.rechercheEtudiant(etud);
      return listeRecherche;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }
}
