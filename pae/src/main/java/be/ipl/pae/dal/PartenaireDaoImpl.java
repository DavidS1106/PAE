package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import be.ipl.pae.partenaire.FactoryPartenaire;
import be.ipl.pae.partenaire.PartenaireDto;
import exceptions.FatalException;
import log.MyLogger;

public class PartenaireDaoImpl implements PartenaireDao {

  private DalService dalService;
  private FactoryPartenaire partenaireFactory;

  /**
   * Constructeur.
   * 
   * @param dalService introduit
   * @param partenaireFactory introduit
   */
  public PartenaireDaoImpl(DalService dalService, FactoryPartenaire partenaireFactory) {
    this.dalService = dalService;
    this.partenaireFactory = partenaireFactory;
  }

  /**
   * FindByEmail.
   * 
   * @param email introduit
   * @return le partenaire ayant l'email passe en parametre
   */
  public PartenaireDto findByEmail(String email) {
    PartenaireDto part = partenaireFactory.creerPartenaire();
    String request = "SELECT p.*, pa.nom AS nom_pays FROM pae.partenaires p, pae.pays pa "
        + "WHERE p.pays = pa.id_pays AND p.nom_legal = ? ";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            part.setAdresse(rs.getString("adresse"));
            part.setCode(rs.getString("code"));
            part.setCodePostal(rs.getString("code_postal"));
            part.setEmail(rs.getString("email"));
            part.setNbEmploye(rs.getInt("nbr_employe"));
            part.setNomAffaire(rs.getString("nom_affaire"));
            part.setNomComplet(rs.getString("nom_complet"));
            part.setNomLegal(rs.getString("nom_legal"));
            part.setPays(rs.getString("nom_pays"));
            part.setRegion(rs.getString("region"));
            part.setSiteWeb(rs.getString("site_web")); // Changer en type URL
            part.setTel(rs.getString("tel"));
            part.setDepartement(rs.getString("departement"));
            part.setTypeOrganisation(rs.getString("type_organisation"));
            part.setVille(rs.getString("ville"));
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
    return part;
  }

  /**
   * UpdateInfoPartenaire.
   * 
   * @param nomLegal introduit
   * @param nomAffaire introduit
   * @param nomComplet introduit
   * @param departement introduit
   * @param typeOrganisation introduit
   * @param nbEmploye introduit
   * @param adresse introduit
   * @param pays introduit
   * @param region introduit
   * @param codePostal introduit
   * @param ville introduit
   * @param siteWeb introduit
   * @param tel introduit
   * @param code introduit
   * 
   * @return l'etudiant mettant à jour ces données
   * @throws SQLException
   */
  public boolean updateInfoPartenaire(PartenaireDto partenaire) {
    int fkPays = 0;
    PartenaireDto part = partenaireFactory.creerPartenaire();
    String requestBefore = "SELECT pa.id_pays FROM pae.pays pa WHERE pa.nom = ?";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestBefore)) {
      try {
        ps.setString(1, partenaire.getPays());
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            fkPays = rs.getInt("id_pays");
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La récupération du pays n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    String request = "UPDATE pae.partenaires p SET nom_affaire = ?, nom_complet = ?, "
        + "departement = ?, type_organisation = ?, nbr_employe = ?, adresse = ?, "
        + "pays = ?, region = ?, code_postal = ?, ville = ?, email = ?,"
        + "site_web = ?, tel = ?, code = ?, version = ? "
        + "WHERE p.nom_legal = ? AND p.version = ?";

    int result = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, partenaire.getNomAffaire());
        ps.setString(2, partenaire.getNomComplet());
        ps.setString(3, partenaire.getDepartement());
        ps.setString(4, partenaire.getTypeOrganisation());
        ps.setInt(5, partenaire.getNbEmploye());
        ps.setString(6, partenaire.getAdresse());
        ps.setInt(7, fkPays);
        ps.setString(8, partenaire.getRegion());
        ps.setString(9, partenaire.getCodePostal());
        ps.setString(10, partenaire.getVille());
        ps.setString(11, partenaire.getEmail());
        ps.setString(12, partenaire.getSiteWeb());
        ps.setString(13, partenaire.getTel());
        ps.setString(14, partenaire.getCode());
        ps.setInt(15, partenaire.getNumVersion() + 1);
        ps.setString(16, partenaire.getNomLegal());
        ps.setInt(17, partenaire.getNumVersion());
        result = ps.executeUpdate();

        System.out.println("ps : " + ps.toString());
      } catch (SQLException ex) {
        part = null;
        MyLogger.getLogger().log(Level.SEVERE, "La requête updateInfoPartenaire n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result != 0;
  }


  /**
   * Liste des partenaires.
   * 
   * @return une arrayList des partenaires
   */
  public ArrayList<PartenaireDto> selectPartenaires() {
    String request = "SELECT * FROM pae.partenaires";
    ArrayList<PartenaireDto> list = new ArrayList<PartenaireDto>();
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            PartenaireDto part = partenaireFactory.creerPartenaire();
            part.setNomLegal(rs.getString("nom_legal"));
            part.setEmail(rs.getString("email"));
            list.add(part);
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête selectPartenaires n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return list;
  }

  /**
   * Liste des noms des partenaires.
   * 
   * @return une arrayList des partenaires
   */
  public ArrayList<String> selectNomsPartenaires() {
    String request = "SELECT p.nom_legal FROM pae.partenaires p";
    ArrayList<String> list = new ArrayList<String>();
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            String nom = rs.getString("nom_legal");
            list.add(nom);
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête selectNomsPartenaires n'a pas réussi");
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
   * @param part introduit
   * @return ArrayList des partenaires recherche
   */
  public ArrayList<PartenaireDto> recherchePartenaires(PartenaireDto part) {



    return null;
  }

  public ArrayList<PartenaireDto> recherchePartenaire(PartenaireDto partenaire) {

    String nom = partenaire.getNomLegal();
    String pays = partenaire.getPays();
    String ville = partenaire.getVille();

    String requestNom = "";
    String requestPays = "";
    String requestVille = "";
    String request = "SELECT p.*, pa.nom AS nom_pays FROM pae.partenaires p "
        + "LEFT JOIN pae.pays pa ON pa.id_pays = p.pays WHERE p.pays = pa.id_pays AND ";
    int ordre = 0;

    if (!nom.isEmpty()) {
      requestNom = "(lower(p.nom_legal) LIKE lower(?) OR lower(p.nom_affaire) LIKE lower(?) OR "
          + "lower(p.nom_complet) LIKE lower(?))";
    }
    if (!pays.isEmpty()) {
      requestPays = "lower(pa.nom) LIKE lower(?)";
    }
    if (!ville.isEmpty()) {
      requestVille = "lower(p.ville) LIKE lower(?)";
    }

    if (!nom.isEmpty() && !pays.isEmpty() && !ville.isEmpty()) {
      requestPays = " AND " + requestPays;
      requestVille = " AND " + requestVille;
      ordre = 1;
    } else if (!nom.isEmpty() && pays.isEmpty() && ville.isEmpty()) {
      ordre = 2;
    } else if (!nom.isEmpty() && !pays.isEmpty() && ville.isEmpty()) {
      requestPays = " AND " + requestPays;
      ordre = 3;
    } else if (!nom.isEmpty() && pays.isEmpty() && !ville.isEmpty()) {
      requestVille = " AND " + requestVille;
      ordre = 4;
    } else if (nom.isEmpty() && !pays.isEmpty() && ville.isEmpty()) {
      ordre = 5;
    } else if (nom.isEmpty() && !pays.isEmpty() && !ville.isEmpty()) {
      requestVille = " AND " + requestVille;
      ordre = 6;
    } else if (nom.isEmpty() && pays.isEmpty() && !ville.isEmpty()) {
      ordre = 7;
    } else {
      ordre = 8;
    }
    request += requestNom + requestPays + requestVille;

    nom += '%';
    pays += '%';
    ville += '%';
    ArrayList<PartenaireDto> liste = new ArrayList<PartenaireDto>();

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        switch (ordre) {
          case 1:
            ps.setString(1, nom);
            ps.setString(2, nom);
            ps.setString(3, nom);
            ps.setString(4, pays);
            ps.setString(5, ville);
            break;
          case 2:
            ps.setString(1, nom);
            ps.setString(2, nom);
            ps.setString(3, nom);
            break;
          case 3:
            ps.setString(1, nom);
            ps.setString(2, nom);
            ps.setString(3, nom);
            ps.setString(4, pays);
            break;
          case 4:
            ps.setString(1, nom);
            ps.setString(2, nom);
            ps.setString(3, nom);
            ps.setString(4, ville);
            break;
          case 5:
            ps.setString(1, pays);
            break;
          case 6:
            ps.setString(1, pays);
            ps.setString(2, ville);
            break;
          case 7:
            ps.setString(1, ville);
            break;
          case 8:
            return liste;
          default:
            break;
        }
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            PartenaireDto recherchePart = partenaireFactory.creerPartenaire();
            recherchePart.setNomLegal(rs.getString("nom_legal"));
            recherchePart.setNomAffaire(rs.getString("nom_affaire"));
            recherchePart.setNomComplet(rs.getString("nom_complet"));
            recherchePart.setPays(rs.getString("nom_pays"));
            recherchePart.setRegion(rs.getString("region"));
            recherchePart.setVille(rs.getString("ville"));
            recherchePart.setAdresse(rs.getString("adresse"));
            recherchePart.setCodePostal(rs.getString("code_postal"));
            recherchePart.setCode(rs.getString("code"));
            recherchePart.setTypeOrganisation(rs.getString("type_organisation"));
            recherchePart.setDepartement(rs.getString("departement"));
            recherchePart.setNbEmploye(rs.getInt("nbr_employe"));
            recherchePart.setEmail(rs.getString("email"));
            recherchePart.setSiteWeb(rs.getString("site_web"));
            recherchePart.setTel(rs.getString("tel"));

            liste.add(recherchePart);
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête recherchePartenaire n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return liste;
  }

  public int getNumversion(String nomLegal) {

    int version = 0;
    String requestVersion = "SELECT e.version FROM pae.partenaires e where e.nom_legal = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestVersion)) {
      try {
        ps.setString(1, nomLegal);
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
