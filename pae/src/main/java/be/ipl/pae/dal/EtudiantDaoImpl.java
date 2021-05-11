package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import be.ipl.pae.etudiant.EtudiantDto;
import be.ipl.pae.etudiant.FactoryEtudiant;
import be.ipl.pae.utilisateur.FactoryUtilisateur;
import exceptions.FatalException;
import log.MyLogger;

public class EtudiantDaoImpl implements EtudiantDao {

  private FactoryEtudiant etudiantFactory;
  private FactoryUtilisateur utilisateurFactory;
  private DalService dalService;

  /**
   * Constructeur.
   * 
   * @param etudiantFactory introduit
   * @param dalService introduit
   * @param utilisateurFactory introduit
   * 
   *        constructeur
   */

  public EtudiantDaoImpl(FactoryEtudiant etudiantFactory, DalService dalService,
      FactoryUtilisateur utilisateurFactory) {
    this.etudiantFactory = etudiantFactory;
    this.dalService = dalService;
    this.utilisateurFactory = utilisateurFactory;
  }

  /**
   * FindByName.
   * 
   * @param name introduit
   * @return l'etudiant ayant le nom passe en parametre
   */
  public EtudiantDto findByName(String name) {
    EtudiantDto etudiant = etudiantFactory.creerEtudiant();
    String request = "SELECT e.*, pa.nom AS nom_pays, i.nom , i.prenom  FROM pae.etudiants e, "
        + "pae.inscrits i, pae.pays pa WHERE e.email = i.mail AND i.nom = ? ";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, name);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            etudiant.setNom(rs.getString("nom"));
            etudiant.setPrenom(rs.getString("prenom"));
            etudiant.setNationalite(rs.getString("nom_pays"));
            etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            etudiant.setAdresse(rs.getString("adresse"));
            etudiant.setTel(rs.getString("tel"));
            etudiant.setSexe(rs.getString("sexe_etudiant"));
            etudiant.setStatut(rs.getString("statut_etudiant"));
            etudiant.setNbrAnneesEtudes(rs.getInt("nbr_annee_etudes"));
            etudiant.setNumCompteBancaire(rs.getString("num_compte_bancaire"));
            etudiant.setTitulaireCompte(rs.getString("titulaire_compte"));
            etudiant.setNomBanque(rs.getString("nom_banque"));
            etudiant.setCodeBic(rs.getString("code_bic"));
            etudiant.setDepartement(rs.getString("departement"));
            etudiant.setNumero(rs.getInt("numero"));
            etudiant.setCodePostal(rs.getInt("code_postal"));
            etudiant.setEmail(rs.getString("email"));
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête findByName a échoué");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return etudiant;
  }

  /**
   * FindByEmail.
   * 
   * @param email introduit
   * @return l'etudiant ayant l'email passe en parametre
   */
  public EtudiantDto findByEmail(String email) {



    EtudiantDto etudiant = this.etudiantFactory.creerEtudiant();
    String request = "SELECT e.date_naissance,e.adresse,e.tel,e.sexe_etudiant,e.statut_etudiant,"
        + "e.nbr_annee_etudes,e.num_compte_bancaire,e.titulaire_compte,e.nom_banque,"
        + "e.code_bic,e.departement,e.numero,e.code_postal,e.email, pa.nom AS nom_pays, "
        + "i.nom AS nom_etudiant , i.prenom AS prenom_etudiant FROM pae.inscrits i "
        + "LEFT JOIN pae.etudiants e ON e.id_inscrit=i.id_inscrit LEFT JOIN pae.pays pa ON "
        + "pa.id_pays=e.nationalite WHERE e.email = i.mail AND e.email = ?";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, email);

        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {

            etudiant.setNom(rs.getString("nom_etudiant"));
            etudiant.setPrenom(rs.getString("prenom_etudiant"));
            etudiant.setNationalite(rs.getString("nom_pays"));

            if (rs.getDate("date_naissance") != null) {
              etudiant.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            }
            etudiant.setAdresse(rs.getString("adresse"));
            etudiant.setTel(rs.getString("tel"));
            etudiant.setSexe(rs.getString("sexe_etudiant"));
            etudiant.setStatut(rs.getString("statut_etudiant"));
            etudiant.setNbrAnneesEtudes(rs.getInt("nbr_annee_etudes"));
            etudiant.setNumCompteBancaire(rs.getString("num_compte_bancaire"));
            etudiant.setTitulaireCompte(rs.getString("titulaire_compte"));
            etudiant.setNomBanque(rs.getString("nom_banque"));
            etudiant.setCodeBic(rs.getString("code_bic"));
            etudiant.setDepartement(rs.getString("departement"));
            etudiant.setNumero(rs.getInt("numero"));
            etudiant.setCodePostal(rs.getInt("code_postal"));
            etudiant.setEmail(rs.getString("email"));
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête findByEmail n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return etudiant;
  }

  /**
   * UpdateInfoEtudiants.
   * 
   * @param statut introduit
   * @param dateNaissance introduit
   * @param nationalite introduit
   * @param adresse introduit
   * @param numero introduit
   * @param codePostal introduit
   * @param tel introduit
   * @param email introduit
   * @param sexe introduit
   * @param nbrAnneesEtudes introduit
   * @param departement introduit
   * @param numCompteBancaire introduit
   * @param codeBic introduit
   * @param titulaireCompte introduit
   * @param nomBanque introduit
   * 
   * @return l'etudiant mettant à jour ces données
   */
  public boolean updateInfoEtudiant(EtudiantDto etud) throws SQLException {



    int fkPays = 0;

    String requestBefore = "SELECT pa.id_pays FROM pae.pays pa WHERE pa.nom = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestBefore)) {
      try {
        ps.setString(1, etud.getNationalite());
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            fkPays = rs.getInt("id_pays");
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête updateInfoEtudiant n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();

      }

    }


    int result;
    String request = "UPDATE pae.etudiants e SET nationalite = ?, date_naissance = ? "
        + ",adresse = ?, tel = ?, sexe_etudiant = ?, statut_etudiant = ? "
        + ",nbr_annee_etudes = ?,num_compte_bancaire = ? ,titulaire_compte = ? "
        + ",nom_banque = ? ,code_bic = ? ,departement = ?, numero = ? ,code_postal = ?,version=?"
        + "WHERE e.email = ? AND e.version = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, fkPays);
        if (etud.getDateNaissance() != null) {
          ps.setDate(2, java.sql.Date.valueOf(etud.getDateNaissance()));
        } else {
          ps.setDate(2, null);
        }
        ps.setString(3, etud.getAdresse());
        ps.setString(4, etud.getTel());
        ps.setString(5, etud.getSexe());
        ps.setString(6, etud.getStatut());
        ps.setInt(7, etud.getNbrAnneesEtudes());
        ps.setString(8, etud.getNumCompteBancaire());
        ps.setString(9, etud.getTitulaireCompte());
        ps.setString(10, etud.getNomBanque());
        ps.setString(11, etud.getCodeBic());
        ps.setString(12, etud.getDepartement());
        ps.setInt(13, etud.getNumero());
        ps.setInt(14, etud.getCodePostal());
        System.out.println("version : " + etud.getNumVersion());
        ps.setInt(15, etud.getNumVersion() + 1);
        ps.setString(16, etud.getEmail());
        ps.setInt(17, etud.getNumVersion());
        result = ps.executeUpdate();
      } catch (SQLException ex) {
        etud = null;
        MyLogger.getLogger().log(Level.SEVERE, "La requête updateInfoEtudiant n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    }
    System.out.println("result : " + result);
    return result != 0;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des etudiants
   */
  public ArrayList<EtudiantDto> selectEtudiants() {
    String request = "SELECT e.email, i.nom AS nom, i.prenom AS prenom FROM pae.etudiants e, "
        + "pae.inscrits i WHERE e.id_inscrit = i.id_inscrit";
    ArrayList<EtudiantDto> rechercheEtud = new ArrayList<EtudiantDto>();

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            EtudiantDto etud = etudiantFactory.creerEtudiant();
            etud.setEmail(rs.getString("email"));
            etud.setNom(rs.getString("nom"));
            etud.setPrenom(rs.getString("prenom"));
            rechercheEtud.add(etud);
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête selectEtudiants n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return rechercheEtud;
  }

  /**
   * Recherche un étudiant.
   * 
   * @param nom de l'étudiant
   * @param prenom de l'étudiant
   * @return l'étudiant recherché
   */
  public ArrayList<EtudiantDto> rechercheEtudiant(EtudiantDto etud) {

    String nom = etud.getNom();
    String prenom = etud.getPrenom();
    String requestNom = "";
    String requestPrenom = "";
    String request = "SELECT e.*, pa.nom AS nom_pays, i.nom , i.prenom  FROM pae.inscrits i "
        + "LEFT JOIN pae.etudiants e ON e.id_inscrit=i.id_inscrit "
        + "LEFT JOIN pae.pays pa ON pa.id_pays = e.nationalite WHERE e.email = i.mail AND ";
    int ordre = 0;

    if (!nom.isEmpty()) {
      requestNom = "lower(i.nom) LIKE lower(?)";
    }
    if (!prenom.isEmpty()) {
      requestPrenom = "lower(i.prenom) LIKE lower(?)";
    }

    if (!nom.isEmpty() && !prenom.isEmpty()) {
      requestPrenom = " AND " + requestPrenom;
      ordre = 1;
    } else if (!nom.isEmpty() && prenom.isEmpty()) {
      ordre = 2;
    } else if (nom.isEmpty() && !prenom.isEmpty()) {
      ordre = 3;
    } else {
      ordre = 4;
    }
    request += requestNom + requestPrenom;
    nom += '%';
    prenom += '%';
    ArrayList<EtudiantDto> liste = new ArrayList<EtudiantDto>();


    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        switch (ordre) {
          case 1:
            ps.setString(1, nom);
            ps.setString(2, prenom);
            break;
          case 2:
            ps.setString(1, nom);
            break;
          case 3:
            ps.setString(1, prenom);
            break;
          case 4:
            return liste;
          default:
            break;
        }
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            EtudiantDto rechercheEtud = etudiantFactory.creerEtudiant();
            rechercheEtud.setNom(rs.getString("nom"));
            rechercheEtud.setPrenom(rs.getString("prenom"));
            rechercheEtud.setNationalite(rs.getString("nom_pays"));
            if (rs.getDate("date_naissance") != null) {
              rechercheEtud.setDateNaissance(rs.getDate("date_naissance").toLocalDate());
            }
            rechercheEtud.setAdresse(rs.getString("adresse"));
            rechercheEtud.setTel(rs.getString("tel"));
            rechercheEtud.setSexe(rs.getString("sexe_etudiant"));
            rechercheEtud.setStatut(rs.getString("statut_etudiant"));
            rechercheEtud.setNbrAnneesEtudes(rs.getInt("nbr_annee_etudes"));
            rechercheEtud.setNumCompteBancaire(rs.getString("num_compte_bancaire"));
            rechercheEtud.setTitulaireCompte(rs.getString("titulaire_compte"));
            rechercheEtud.setNomBanque(rs.getString("nom_banque"));
            rechercheEtud.setCodeBic(rs.getString("code_bic"));
            rechercheEtud.setDepartement(rs.getString("departement"));
            rechercheEtud.setNumero(rs.getInt("numero"));
            rechercheEtud.setCodePostal(rs.getInt("code_postal"));
            rechercheEtud.setEmail(rs.getString("email"));
            liste.add(rechercheEtud);
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête rechercheEtudiant n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return liste;
  }

  public int getNumversion(String email) {

    int version = 0;
    String requestVersion = "SELECT e.version FROM pae.etudiants e where e.email = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestVersion)) {
      try {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
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
      e.printStackTrace();
    } catch (NullPointerException ne) {
      version = 0;
      ne.printStackTrace();
    }

    return version;
  }
}
