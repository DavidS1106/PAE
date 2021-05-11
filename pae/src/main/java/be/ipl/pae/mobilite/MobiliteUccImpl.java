package be.ipl.pae.mobilite;

import java.util.ArrayList;
import be.ipl.pae.dal.DalService;
import be.ipl.pae.dal.MobiliteDao;

public class MobiliteUccImpl implements MobiliteUcc {
  private MobiliteDao mobiliteDao;
  private DalService dalService;

  public MobiliteUccImpl(MobiliteDao mobiliteDao, DalService dalService) {
    this.mobiliteDao = mobiliteDao;
    this.dalService = dalService;
  }

  /**
   * Methode inserer mobilite
   * 
   * @param email introduit
   * @param anneeAcad introduit
   * @param numOrdrePreference introduit
   * @param pays introduit
   * @param nomPartenaire introduit
   * @param dateIntroduction introduit
   * @param quadri introduit
   * 
   *        inserer une mobilite
   * 
   * @return la mobilite inseree
   */
  public boolean insererMob(MobiliteDto mobilite) {
    try {
      this.dalService.startTransa();
      return this.mobiliteDao.insererMobilite(mobilite);
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  /**
   * ArrayList. recupererMobilites
   * 
   * @return une ArrayList
   */
  public ArrayList<MobiliteDto> recupererMobilites() {
    try {
      this.dalService.startTransa();
      ArrayList<MobiliteDto> array;
      array = mobiliteDao.findAllStudentMobilities();
      return array;

    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  /**
   * Methode inserer mobilite via le deuxième formulaire (Complet)
   * 
   * @param email introduit
   * @param anneeAcad introduit
   * @param numOrdrePreference introduit
   * @param pays introduit
   * @param nomPartenaire introduit
   * @param dateIntroduction introduit
   * @param quadri introduit
   * @param programme introduit
   * @param programme code
   * 
   *        inserer une mobilite
   * 
   * @return la mobilite inseree
   */
  public boolean insererMobComplet(MobiliteDto mobilite, String programme) {
    try {
      this.dalService.startTransa();
      return this.mobiliteDao.insererMobiliteComplet(mobilite, programme);
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  /**
   * ArrayList.
   * 
   * @param email introduit
   * @return une ArrayList
   */
  public ArrayList<MobiliteDto> recupererMob(String email) {
    try {
      this.dalService.startTransa();
      ArrayList<MobiliteDto> e = this.mobiliteDao.recupererMobilite(email);
      return e;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }


  public ArrayList<MobiliteDto> recupererMobNonExistante(String email) {
    try {
      this.dalService.startTransa();
      ArrayList<MobiliteDto> e = this.mobiliteDao.recupererMobiliteNonExistante(email);
      return e;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  public ArrayList<MobiliteDto> recupStudMob(String email) {
    try {
      this.dalService.startTransa();
      ArrayList<MobiliteDto> e = this.mobiliteDao.verifPartenaireExistant(email);
      return e;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  public boolean updateMob(MobiliteDto mobilite, int idMobilite) {
    try {
      this.dalService.startTransa();
      int version = mobiliteDao.getNumVersion(idMobilite);
      return this.mobiliteDao.updateMobilite(mobilite, idMobilite, version);
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  public ArrayList<MobiliteDto> rechercheMobilite(String annee, String etat) {
    try {
      this.dalService.startTransa();
      ArrayList<MobiliteDto> listeRecherche = mobiliteDao.rechercheMobilite(annee, etat);
      return listeRecherche;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

}
