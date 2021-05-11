package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import be.ipl.pae.mobilite.FactoryMobilite;
import be.ipl.pae.utilisateur.FactoryUtilisateur;
import be.ipl.pae.utilisateur.UtilisateurDto;
import be.ipl.pae.utilisateur.UtilisateurDto.Fonction;
import exceptions.FatalException;
import log.MyLogger;

public class UtilisateurDaoImpl implements UtilisateurDao {

  private DalService dalService;
  private FactoryUtilisateur utilisateurFactory;
  private FactoryMobilite mobiliteFactory;

  /**
   * Constructeur.
   * 
   * @param dalService introduit
   * @param utilisateurFactory introduit
   * @param mobiliteFactory introduit
   */
  public UtilisateurDaoImpl(DalService dalService, FactoryUtilisateur utilisateurFactory,
      FactoryMobilite mobiliteFactory) {
    this.dalService = dalService;
    this.utilisateurFactory = utilisateurFactory;
    this.mobiliteFactory = mobiliteFactory;
  }

  /**
   * Cherche un utilisateur par rapport à un login.
   * 
   * @param mail d'un utilisateur
   * @return renvoie un user rempli avec les informations d'un inscrit dont le mail correspond
   */
  public ArrayList<String> recupererNomPays() {
    String request = "SELECT nom FROM pae.pays ";
    ArrayList<String> array = new ArrayList<String>();
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            array.add(rs.getString("nom"));
          }
        }

      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } ;

    return array;
  }

  /**
   * FindByMail.
   * 
   * @param mail introduit
   * @return l'utilisateur ayant le mail passe en parametre
   */
  public UtilisateurDto findByMail(String mail) {
    UtilisateurDto user = this.utilisateurFactory.creerUtilisateur();
    String request = "SELECT * FROM pae.inscrits i WHERE i.mail = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {

        ps.setString(1, mail);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            user.setMail(rs.getString("mail"));
            user.setPseudo(rs.getString("pseudo"));
            user.setMdp(rs.getString("mdp"));
            user.setPrenom(rs.getString("prenom"));
            user.setNom(rs.getString("nom"));
            Fonction fonction = Fonction.valueOf(rs.getString("fonction"));
            user.setFonction(fonction);
          }
        }

      } catch (SQLException exception) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête FindByLogin n'a pas réussi !");
        exception.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;
  }

  /**
   * Cherche un utilisateur par rapport à un login.
   * 
   * @param mail d'un utilisateur
   * @return renvoie un user rempli avec les informations d'un inscrit dont le mail correspond
   */
  public UtilisateurDto insertUser(UtilisateurDto utilisateur) {
    UtilisateurDto user = utilisateurFactory.creerUtilisateur();
    int id_inscrit = 0;
    String request =
        "INSERT INTO pae.inscrits (id_inscrit, date_inscription, nom,prenom,pseudo,mdp,mail,fonction) values (DEFAULT,DEFAULT,?,?,?,?,?,?) ";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, utilisateur.getNom());
        ps.setString(2, utilisateur.getPrenom());
        ps.setString(3, utilisateur.getPseudo());
        ps.setString(4, utilisateur.getMdp());
        ps.setString(5, utilisateur.getMail());
        ps.setString(6, "E");
        ps.executeUpdate();
      } catch (SQLException exception) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insertUser1 n'a pas réussi !");
        exception.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e2) {
      e2.printStackTrace();
    }
    user.setMail(utilisateur.getMail());
    user.setPseudo(utilisateur.getPseudo());
    user.setMdp(utilisateur.getMdp());
    user.setPrenom(utilisateur.getPrenom());
    user.setNom(utilisateur.getNom());
    user.setFonction(UtilisateurDto.Fonction.E);

    String request2 = "SELECT i.id_inscrit FROM pae.inscrits i WHERE i.mail = ? ";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request2)) {
      try {
        ps.setString(1, utilisateur.getMail());
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            id_inscrit = rs.getInt("id_inscrit");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insertUser2 n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    String request3 =
        "INSERT INTO pae.etudiants (id_inscrit, nationalite, date_naissance, adresse, tel, sexe_etudiant, statut_etudiant, nbr_annee_etudes, num_compte_bancaire, titulaire_compte, "
            + "nom_banque, code_bic, departement, numero, code_postal, email, version) VALUES (?, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,"
            + "NULL, NULL, ?, DEFAULT) ";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request3)) {
      try {
        ps.setInt(1, id_inscrit);
        ps.setString(2, utilisateur.getMail());
        ps.executeUpdate();
      } catch (SQLException exception) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insertUser3 n'a pas réussi !");
        exception.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user;

  }

  /**
   * Liste.
   * 
   * @return une arrayList des etudiants
   */
  public ArrayList<UtilisateurDto> selectEtudiantsSignature() {
    ArrayList<UtilisateurDto> array = new ArrayList<UtilisateurDto>();
    String request = "";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des inscrits
   */
  public ArrayList<UtilisateurDto> selectInscrits() {
    String request = "SELECT * FROM pae.inscrits ORDER BY nom,prenom";
    ArrayList<UtilisateurDto> list = new ArrayList<UtilisateurDto>();

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            UtilisateurDto user = utilisateurFactory.creerUtilisateur();
            user.setMail(rs.getString("mail"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setPseudo(rs.getString("pseudo"));
            list.add(user);
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête selectInscrits n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des etudiants
   */
  public ArrayList<UtilisateurDto> SelectStudents() {
    String request = "SELECT * FROM pae.inscrits WHERE fonction = 'E'";
    ArrayList<UtilisateurDto> list = new ArrayList<UtilisateurDto>();

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            UtilisateurDto user = utilisateurFactory.creerUtilisateur();
            user.setMail(rs.getString("mail"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            list.add(user);
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête selectStudents n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des raisons
   */
  public ArrayList<String> getRaisons() {

    String request = "SELECT * FROM pae.raisons_annulation";
    ArrayList<String> list = new ArrayList<String>();
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            list.add(rs.getString("raison"));
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête getRaisons n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return list;
  }
}
