package be.ipl.pae.partenaire;

import java.util.ArrayList;
import be.ipl.pae.dal.DalService;
import be.ipl.pae.dal.PartenaireDao;

public class PartenaireUccImpl implements PartenaireUcc {

  private PartenaireDao partenaireDao;
  private DalService dalService;

  /**
   * Constructeur.
   * 
   * @param partenaireDao introduit
   * 
   */
  public PartenaireUccImpl(PartenaireDao partenaireDao, DalService dalService) {
    this.partenaireDao = partenaireDao;
    this.dalService = dalService;
  }

  /**
   * Methode pour recuperer les infos de l'etudiant.
   * 
   * @param email introduit
   * @return l'etudiant ayant le mail passe en parametre
   */
  public PartenaireDto recupererInfo(String email) {
    try {
      this.dalService.startTransa();
      if (email == null) {
        return null;
      }
      PartenaireDto partenaire = (PartenaireDto) partenaireDao.findByEmail(email);
      return partenaire;
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
   * @param jsonRecherche introduit
   * 
   *        rechercherPartenaire
   * @return une ArrayList
   */
  /*
   * public ArrayList<PartenaireDto> recherchePartenaire(String jsonRecherche) { try {
   * this.dalService.startTransa(); PartenaireDto part = new PartenaireImpl(); Genson genson = new
   * Genson();
   * 
   * part = genson.deserialize(jsonRecherche, PartenaireImpl.class); ArrayList<PartenaireDto> array
   * = partenaireDao.recherchePartenaires(part); return array;
   * 
   * } catch (Exception exc) { this.dalService.rollBackTransa(); throw exc; } finally {
   * this.dalService.commitTransa(); }
   * 
   * 
   * }
   */

  /**
   * Methode.
   * 
   * @return le partenaire en mettant à jour ces infos
   * @throws Exception
   */
  public boolean updateInfo(PartenaireDto partenaire) {
    try {
      this.dalService.startTransa();
      partenaire.setNumVersion(partenaireDao.getNumversion(partenaire.getNomLegal()));
      System.out.println("numVersion partenaire:" + partenaire.getNumVersion());
      return partenaireDao.updateInfoPartenaire(partenaire);

    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  /**
   * Liste des partenaires.
   * 
   * @return ArrayList des partenaires
   */
  public ArrayList<PartenaireDto> getPartenaires() {
    try {
      this.dalService.startTransa();
      ArrayList<PartenaireDto> partenaires = partenaireDao.selectPartenaires();
      return partenaires;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }
  }

  /**
   * Liste des noms des partenaires.
   * 
   * @return ArrayList des partenaires
   */
  public ArrayList<String> getNomsPartenaires() {
    try {
      this.dalService.startTransa();
      ArrayList<String> partenaires = partenaireDao.selectNomsPartenaires();
      return partenaires;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  public ArrayList<PartenaireDto> recherchePartenaire(PartenaireDto partenaire) {
    try {
      this.dalService.startTransa();
      ArrayList<PartenaireDto> listeRecherche = partenaireDao.recherchePartenaire(partenaire);
      return listeRecherche;
    } catch (Exception exc) {
      this.dalService.rollBackTransa();
      throw exc;
    } finally {
      this.dalService.commitTransa();
    }

  }

  /*
   * public int getNumVersion(String nomLegal) { try { this.dalService.startTransa(); return
   * partenaireDao.getNumversion(nomLegal); } catch (Exception exc) {
   * this.dalService.rollBackTransa(); throw exc; } finally { this.dalService.commitTransa(); } }
   */

}

