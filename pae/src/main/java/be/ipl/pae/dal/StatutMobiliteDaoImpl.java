package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import be.ipl.pae.statutmobilite.FactoryStatutMobilite;
import be.ipl.pae.statutmobilite.StatutMobiliteDto;
import be.ipl.pae.statutmobilite.StatutMobiliteImpl;
import exceptions.FatalException;
import log.MyLogger;

public class StatutMobiliteDaoImpl implements StatutMobiliteDao {

  private int idInscrit;
  private int idStatutMobilite;
  private List<Integer> idMobilites = new ArrayList<Integer>();
  private int idRaison;
  private String request;
  private DalService dalService;
  @SuppressWarnings("unused")
  private FactoryStatutMobilite statutMobiliteFactory;
  private int version;

  public StatutMobiliteDaoImpl(DalService dalService, FactoryStatutMobilite statutMobiliteFactory) {
    this.dalService = dalService;
    this.statutMobiliteFactory = statutMobiliteFactory;
  }

  /**
   * Initialise les informations.
   * 
   * @param mail de l'étudiant
   */
  private void initialiserInfos(String mail) {
    getIdInscrit(mail);
    getIdsMobilite();
    getIdStatutMobilite();
    getVersion();
  }

  /**
   * Récupère l'id d'un étudiant.
   * 
   * @param mail d'un étudiant
   */
  private void getIdInscrit(String mail) {

    this.request = "SELECT id_inscrit FROM pae.inscrits I WHERE I.mail = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, mail);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            this.idInscrit = rs.getInt("id_inscrit");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête getIdInscrit n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  /**
   * Récupère l'id d'une mobilité.
   * 
   * 
   */
  private void getIdsMobilite() {

    this.request = "SELECT id_mobilite FROM pae.mobilites M WHERE M.id_inscrit = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, this.idInscrit);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idMobilites.add(rs.getInt("id_mobilite"));
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête getIdMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Récupère l'id du statut d'une mobilité.
   * 
   * 
   */
  private void getIdStatutMobilite() {

    this.request = "SELECT SM.mobilite FROM pae.statut_mobilite SM, pae.mobilites M "
        + "WHERE SM.mobilite = M.id_mobilite AND M.id_inscrit = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, this.idInscrit);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idStatutMobilite = rs.getInt("mobilite");
          }
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Récupère la Pk d'une raison d'annulation.
   * 
   * @param raison d'annulation
   */
  private void getIdRaisonAnnulation(String raison) {

    this.request = "SELECT id_raison FROM pae.raisons_annulation RA WHERE RA.raison = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, raison);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            this.idRaison = rs.getInt("id_raison");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête getPkRaisonAnulation n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Annule sa propre mobilité.
   * 
   * @param mail de l'étudiant
   * @param raison de l'annulation
   */
  public void annulerMobiliteParEtudiant(String mail, String raison, int version) {

    initialiserInfos(mail);
    this.request =
        "UPDATE pae.statut_mobilite SET annule = true, raison_annulation_etudiant = ?,version=? "
            + "WHERE mobilite = ? AND version = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, raison);
        ps.setInt(2, version + 1);
        ps.setInt(3, idStatutMobilite);
        ps.setInt(4, version);
        ps.executeUpdate();
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La requête annulerMobiliteParEtudiant n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Annule la mobilité d'un étudiant par un professeur.
   * 
   * @param mail de l'étudiant
   * @param raison de l'annulation
   */
  public void annulerMobiliteParProfesseur(String mail, String raison, int version) {

    initialiserInfos(mail);
    getIdRaisonAnnulation(raison);
    this.request =
        "UPDATE pae.statut_mobilite SET annule = true, raison_annulation_prof = ?,version=? "
            + "WHERE mobilite = ? AND version = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, raison);
        ps.setInt(2, version + 1);
        ps.setInt(3, idStatutMobilite);
        ps.setInt(4, version);
        ps.executeUpdate();
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La requête annulerMobiliteParProfesseur n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Récupère l'état des documents d'un étudiant.
   * 
   * @param mail de l'étudiant
   * @return statut d'une mobilité
   */
  public StatutMobiliteDto getEtatDocuments(String mail) {

    initialiserInfos(mail);
    StatutMobiliteDto etatDocuments = new StatutMobiliteImpl();
    this.request = "SELECT SM.* FROM pae.statut_mobilite SM WHERE SM.mobilite = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idStatutMobilite);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            etatDocuments.setAnnule(rs.getBoolean("annule"));
            etatDocuments.setAttestationSejour(rs.getBoolean("attestation_sejour"));
            etatDocuments.setCertificatStage(rs.getBoolean("certificat_stage"));
            etatDocuments.setCharteEtudiant(rs.getBoolean("charte_etudiant"));
            etatDocuments.setContratBourse(rs.getBoolean("contrat_bourse"));
            etatDocuments.setConventionEtude(rs.getBoolean("convention_etude"));
            etatDocuments.setConventionStage(rs.getBoolean("convention_stage"));
            etatDocuments.setDemandePremierPaiement(rs.getBoolean("demande_premier_paiement"));
            etatDocuments.setDemandeSecondPaiement(rs.getBoolean("demande_second_paiement"));
            etatDocuments.setDocumentEngagement(rs.getBoolean("document_engagement"));
            etatDocuments.setDocumentsSignes(rs.getBoolean("documents_signes"));
            etatDocuments.setEnvoiDemandePremierPaiement(rs.getBoolean("demande_premier_paiement"));
            etatDocuments.setEnvoiDemandeSecondPaiement(rs.getBoolean("demande_second_paiement"));
            etatDocuments.setEnvoiEtudiant(rs.getBoolean("envoi_etudiant"));
            etatDocuments.setInfoEtudiant(rs.getBoolean("info_etudiant"));
            etatDocuments.setInfoPartenaire(rs.getBoolean("info_partenaire"));
            etatDocuments.setMobilite(rs.getInt("mobilite"));
            etatDocuments.setPremierPaiement(rs.getBoolean("premier_paiement"));
            etatDocuments.setRapportFinal(rs.getBoolean("rapport_final"));
            etatDocuments.setReleveNotes(rs.getBoolean("releve_notes"));
            etatDocuments.setSecondPaiement(rs.getBoolean("second_paiement"));
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête getEtatDocuments n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return etatDocuments;
  }

  /**
   * Met à jour le statut de la mobilité.
   * 
   * @param mail de l'étudiant
   * @param contratBourse true si signé
   * @param conventionStage true si signé
   * @param conventionEtude true si signé
   * @param charteEtudiant true si signé
   * @param documentEngagement true si signé
   * @param demandePremierPaiement true si demandé
   * @param premierPaiement true si effectué
   * @param infoEtudiant true si complété
   * @param infoPartenaire true si complété
   * @param attestationSejour true si reçue
   * @param releveNotes true si reçu
   * @param certificatStage true si reçu
   * @param rapportFinal true si reçu
   * @param demandeSecondPaiement true si demandé
   * @param secondPaiement true si effectué
   */
  public boolean updateEtatDocuments(String mail, StatutMobiliteDto statutMobilite, int version) {


    initialiserInfos(mail);
    this.request = "UPDATE pae.statut_mobilite SET contrat_bourse = ?, convention_etude = ?, "
        + "convention_stage = ?, charte_etudiant = ?, document_engagement = ?, "
        + "demande_premier_paiement = ?, premier_paiement = ?, info_etudiant = ?, "
        + "info_partenaire = ?, attestation_sejour = ?, releve_notes = ?, certificat_stage = ?, "
        + "rapport_final = ?, demande_second_paiement = ?, second_paiement = ?, version = ? "
        + "WHERE mobilite = ? AND version = ?";
    int result = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setBoolean(1, statutMobilite.isContratBourse());
        ps.setBoolean(2, statutMobilite.isConventionEtude());
        ps.setBoolean(3, statutMobilite.isConventionStage());
        ps.setBoolean(4, statutMobilite.isCharteEtudiant());
        ps.setBoolean(5, statutMobilite.isDocumentEngagement());
        ps.setBoolean(6, statutMobilite.isDemandePremierPaiement());
        ps.setBoolean(7, statutMobilite.isPremierPaiement());
        ps.setBoolean(8, statutMobilite.isInfoEtudiant());
        ps.setBoolean(9, statutMobilite.isInfoPartenaire());
        ps.setBoolean(10, statutMobilite.isAttestationSejour());
        ps.setBoolean(11, statutMobilite.isReleveNotes());
        ps.setBoolean(12, statutMobilite.isCertificatStage());
        ps.setBoolean(13, statutMobilite.isRapportFinal());
        ps.setBoolean(14, statutMobilite.isDemandeSecondPaiement());
        ps.setBoolean(15, statutMobilite.isSecondPaiement());
        ps.setInt(16, version + 1);
        ps.setInt(17, idStatutMobilite);
        ps.setInt(18, version);
        result = ps.executeUpdate();
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête updateEtatDocuments n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result > 0;
  }

  /**
   * Récupère la version du statut de la mobilité.
   *
   *
   */
  private void getVersion() {


    this.request = "SELECT SM.version FROM pae.statut_mobilite SM WHERE SM.mobilite = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, this.idStatutMobilite);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            this.version = rs.getInt("version");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La récupération de la version n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * /** Select l'id du partenaire.
   * 
   * @param nomPartenaire introduit
   * @param idPays introduit
   * @return l'id du partenaire
   */
  private int selectIdPartenaire(String nomPartenaire) {

    int idPartenaire = 0;
    this.request = "SELECT p.id_partenaire FROM pae.partenaires p WHERE p.nom_legal = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, nomPartenaire);
        try (ResultSet rs = ps.executeQuery()) {
          // System.out.println(rs);
          while (rs.next()) {
            idPartenaire = rs.getInt(1);
          }
        }
      } catch (SQLException e) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête selectIdPartenaire n'a pas réussi");
        e.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    return idPartenaire;
  }

  private int selectSiteWebPartenaire(String nomPartenaire) {

    this.request = "SELECT p.site_web FROM pae.partenaires p WHERE p.nom_legal = ?";
    int idPartenaire = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, nomPartenaire);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idPartenaire = rs.getInt("id_partenaire");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La récupération de l'idPartenaire n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return idPartenaire;
  }

  public boolean confirmerMobilite(int idMobilite, String email) {

    // int idPartenaire = selectIdPartenaire(nomMobilite);
    // int idInscrit = selectIdInscrit(email);
    // System.out.println(nomMobilite);
    // int idMobilite = selectIdMobilite(idPartenaire, idInscrit);
    // System.out.println("confimerMob : idMobilite :" + idMobilite);
    this.request =
        "INSERT INTO pae.statut_mobilite values(DEFAULT,'C',?,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT)";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idMobilite);
        ps.executeUpdate();

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insererPartenaire n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();

      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }

  /**
   * Select l'id de l'inscrit.
   * 
   * @param email introduit
   * @return l'id de l'inscrit
   */
  private int selectIdInscrit(String email) {

    this.request = "SELECT i.id_inscrit FROM pae.inscrits i WHERE i.mail = ?";
    int idUtilisateur = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idUtilisateur = rs.getInt("id_inscrit");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La récupération de l'idUtilisateur n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return idUtilisateur;
  }

  /**
   * Select l'id de l'inscrit.
   * 
   * @param email introduit
   * @return l'id de l'inscrit
   */
  private int selectIdMobilite(int idPartenaire, int idInscrit) {
    int idMobilite = 0;
    String request =
        "SELECT m.id_mobilite FROM pae.mobilites m WHERE m.partenaire=? AND m.id_inscrit = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idPartenaire);
        ps.setInt(2, idInscrit);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idMobilite = rs.getInt("id_mobilite");
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La récupération de l'idUtilisateur n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return idMobilite;
  }

  public int getNumVersion(String mail) {

    int version = 0;

    String requestVersion =
        "SELECT sm.version FROM pae.statut_mobilite sm,pae.mobilites m,pae.inscrits i WHERE sm.mobilite=m.id_mobilite AND m.id_inscrit=i.id_inscrit AND i.mail=?";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestVersion)) {

      try {

        ps.setString(1, mail);
        try (ResultSet rs = ps.executeQuery()) {
          System.out.println("rs dao " + rs);

          while (rs.next()) {
            version = rs.getInt("version");
          }
        }
      } catch (SQLException e1) {
        MyLogger.getLogger().log(Level.SEVERE, "La récupération de la version a échouée");
        e1.printStackTrace();
        throw new FatalException();
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NullPointerException ne) {
      ne.printStackTrace();
      version = 0;
    }
    System.out.println("anul numversion" + version);
    return version;
  }
}
