package be.ipl.pae.statutmobilite;

import be.ipl.pae.dal.DalService;
import be.ipl.pae.dal.StatutMobiliteDao;
import util.Util;

public class StatutMobiliteUccImpl implements StatutMobiliteUcc {

  private StatutMobiliteDao statutMobiliteDao;
  private DalService dalService;

  public StatutMobiliteUccImpl(StatutMobiliteDao statutMobiliteDao, DalService dalService) {
    this.statutMobiliteDao = statutMobiliteDao;
    this.dalService = dalService;
  }

  /**
   * Annule la demande de mobilité d'un étudiant.
   * 
   * @param mail de l'étudiant
   * @param raison not null si c'est la raison de l'étudiant
   * @param idRaison not null si c'est la raison du professeur
   * @return true si la demande de mobilité a été annulée
   */
  public boolean annuler(String mail, String raison, String idRaison) {
    try {
      this.dalService.startTransa();
      int version = statutMobiliteDao.getNumVersion(mail);
      if (!Util.validateEmail(mail)) {
        return false;
      }

      if (raison == null && idRaison == null) {
        return false;
      }

      if (raison == null) {
        this.statutMobiliteDao.annulerMobiliteParProfesseur(mail, idRaison, version);
        statutMobiliteDao.annulerMobiliteParProfesseur(mail, idRaison, version);
      } else {
        this.statutMobiliteDao.annulerMobiliteParEtudiant(mail, raison, version);
        statutMobiliteDao.annulerMobiliteParEtudiant(mail, raison, version);
      }

      return true;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }


  }

  /**
   * Récupère l'état des documents d'un étudiant.
   * 
   * @param mail de l'étudiant
   */
  public StatutMobiliteDto getEtatDocuments(String mail) {
    try {
      this.dalService.startTransa();
      if (mail == null || mail.isEmpty() || !Util.validateEmail(mail)) {
        return null;
      }

      StatutMobilite etatDocuments = (StatutMobilite) this.statutMobiliteDao.getEtatDocuments(mail);
      return etatDocuments;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }


  }

  /**
   * Met à jour les documents.
   * 
   * @param mail de l'étudiant
   * @param contratBourse vrai si signé
   * @param conventionEtude vrai si signé
   * @param conventionStage vrai si signé
   * @param charteEtudiant vrai si signé
   * @param documentEngagement vrai si signé
   * @param demandePremierPaiement vrai si envoyé
   * @param premierPaiement vrai si effectué
   * @param infoEtudiant vrai si complétées
   * @param infoPartenaire vrai si complétées
   * @param attestationSejour vrai si signé
   * @param releveNotes vrai si reçu
   * @param certificatStage vrai si reçu
   * @param rapportFinal vrai si reçu
   * @param demandeSecondPaiement vrai si envoyé
   * @param secondPaiement vrai si payé
   */
  public boolean updateEtatDocuments(String mail, StatutMobiliteDto statutMobilite) {

    try {
      this.dalService.startTransa();
      int version = statutMobiliteDao.getNumVersion(mail);
      if (!Util.validateEmail(mail)) {
        return false;
      }

      return this.statutMobiliteDao.updateEtatDocuments(mail, statutMobilite, version);

    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }



  public boolean confirmerMobilite(int idMobilite, String email) {
    // return this.statutMobiliteDao.confirmerMobilite(nomMobilite);
    return this.statutMobiliteDao.confirmerMobilite(idMobilite, email);
  }

  /*
   * public int getNumVersion(String mail) { try { this.dalService.startTransa(); return
   * statutMobiliteDao.getNumVersion(mail);
   * 
   * } catch (Exception exc) { this.dalService.rollBackTransa(); throw exc; } finally {
   * this.dalService.commitTransa(); } }
   */
}
