package be.ipl.pae.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import be.ipl.pae.mobilite.FactoryMobilite;
import be.ipl.pae.mobilite.MobiliteDto;
import be.ipl.pae.mobilite.MobiliteImpl;
import exceptions.FatalException;
import log.MyLogger;


public class MobiliteDaoImpl implements MobiliteDao {
  private DalService dalService;
  private FactoryMobilite mobiliteFactory;

  /**
   * Constructeur.
   * 
   * @param dalService introduit
   * @param mobiliteFactory introduit
   */
  public MobiliteDaoImpl(DalService dalService, FactoryMobilite mobiliteFactory) {
    this.dalService = dalService;
    this.mobiliteFactory = mobiliteFactory;
  }

  /**
   * Inserer mob.
   * 
   * @param email introduit
   * @param anneeAcad introduit
   * @param numOrdrePreference introduit
   * @param pays introduit
   * @param nomPartenaire introduit
   * @param dateIntroduction introduit
   * @param quadri introduit
   * 
   * @return la mobilite ajoute
   */
  public boolean insererMobilite(MobiliteDto mobilite) {

    String nomPartenaire = mobilite.getNomPartenaire();
    String pays = mobilite.getPays();
    String anneeAcad = mobilite.getAnneeAcademique();
    int numOrdrePreference = mobilite.getNumOrdre();
    int quadri = mobilite.getQuadri();
    LocalDate dateIntroduction = mobilite.getDateIntro();


    String programme = "";
    int idPays = selectIdPays(pays);
    int idPartenaire = selectIdPartenaire(nomPartenaire);
    int idInscrit = selectIdInscrit(mobilite.getMail());
    String requestProgramme = "SELECT p.programme FROM pae.pays p WHERE p.id_pays = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestProgramme)) {
      try {
        ps.setInt(1, idPays);
        ps.executeQuery();
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            programme = rs.getString("programme");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insererMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }

    } catch (SQLException e2) {
      e2.printStackTrace();
    }


    // récuper le code du pays
    String code = "";
    String requestCode = "SELECT par.code FROM pae.partenaires par WHERE par.id_partenaire = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestCode)) {
      try {
        ps.setInt(1, idPartenaire);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            code = rs.getString("code");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requestCode n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }

    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    String request =
        "INSERT INTO pae.mobilites values(DEFAULT,?,?,?,?,?,?,?,false,?,DEFAULT, ?) RETURNING id_mobilite";
    int idMobilite = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idInscrit);
        ps.setString(2, anneeAcad);
        ps.setInt(3, numOrdrePreference);
        ps.setInt(4, idPays);
        ps.setInt(5, idPartenaire);
        ps.setDate(6, java.sql.Date.valueOf(dateIntroduction));
        ps.setInt(7, quadri);
        ps.setString(8, programme);
        ps.setString(9, code);
        try (ResultSet rs = ps.executeQuery()) {
          rs.next();
          idMobilite = rs.getInt(1);
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insererMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return (idMobilite != 0);
  }

  /**
   * Inserer mobilité via le deuxième formulaire (Complet).
   * 
   * @param email
   * @param anneeAcad introduit
   * @param numOrdrePreference introduit
   * @param pays introduit
   * @param nomPartenaire introduit
   * @param dateIntroduction introduit
   * @param quadri introduit
   * @param programme introduit
   * @param code introduit
   * @return la mobilite ajoute
   */
  public boolean insererMobiliteComplet(MobiliteDto mobilite, String programme) {

    int idInscrit = selectIdInscrit(mobilite.getMail());
    String nomPartenaire = mobilite.getNomPartenaire();
    String code = mobilite.getCode();
    String pays = mobilite.getPays();
    String typeDeMobilite = mobilite.getTypeDeMobilite();
    String anneeAcad = mobilite.getAnneeAcademique();
    int numOrdrePreference = mobilite.getNumOrdre();
    int quadri = mobilite.getQuadri();
    LocalDate dateIntroduction = mobilite.getDateIntro();

    // Si partenaire
    if (nomPartenaire != null) {
      String requestPartenaire =
          "insert into pae.partenaires values (DEFAULT, ?, null, null, null, null, null, null, null, null, null, null, null, null, null, ?, DEFAULT)";
      try (PreparedStatement ps = this.dalService.getPreparedStatement(requestPartenaire)) {
        try {
          ps.setString(1, nomPartenaire);
          ps.setString(2, code);
          ps.executeUpdate();
        } catch (SQLException e) {
          MyLogger.getLogger().log(Level.SEVERE, "La requestPartenaire n'a pas réussi");
          e.printStackTrace();
          throw new FatalException();
        }
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
    }



    String requestMobi =
        "INSERT INTO pae.mobilites VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, false, ?, DEFAULT, ?) RETURNING id_mobilite";
    int idMobilite = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestMobi)) {
      try {
        ps.setInt(1, idInscrit);
        ps.setString(2, anneeAcad);
        ps.setInt(3, numOrdrePreference);
        if (pays.equals("Non-defini")) {
          ps.setNull(4, java.sql.Types.NULL);
        } else {
          int idPays = selectIdPays(pays);
          ps.setInt(4, idPays);
        }

        if (nomPartenaire == null) {
          ps.setNull(5, java.sql.Types.NULL);
        } else {
          int idPartenaire = selectIdPartenaire(nomPartenaire);
          ps.setInt(5, idPartenaire);
        }

        ps.setDate(6, java.sql.Date.valueOf(dateIntroduction));
        ps.setInt(7, quadri);
        ps.setString(8, programme);
        ps.setString(9, code);
        try (ResultSet rs = ps.executeQuery()) {
          rs.next();
          idMobilite = rs.getInt(1);
        }

      } catch (SQLException e) {
        MyLogger.getLogger().log(Level.SEVERE, "La requestMobi n'a pas réussi");
        e.printStackTrace();
        throw new FatalException();
      }

    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    return (idMobilite != 0);
  }

  /**
   * /** Select l'id du pays.
   *
   * @param pays introduit
   * @return l'id du pays
   */
  private int selectIdPays(String pays) {

    String request = "SELECT p.id_pays FROM pae.pays p WHERE p.nom = ?";
    int idPays = 0;

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, pays);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idPays = rs.getInt("id_pays");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insererMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return idPays;
  }

  /**
   * Insert partenaire.
   * 
   * @param nomPartenaire introduit
   * @param idPays introduit
   * @return l'id du partenaire
   */
  private int insertPartenaire(String nomPartenaire, int idPays) {

    int idPartenaire = 0;
    String request = "INSERT INTO pae.partenaires values(DEFAULT,?,null,null,null,null,null,null,?,"
        + "null,null,null,null,null,null,null,DEFAULT) RETURNING id_partenaire";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, nomPartenaire);
        ps.setInt(2, idPays);
        try (ResultSet rs = ps.executeQuery()) {
          rs.next();
          idPartenaire = rs.getInt(1);
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête insererPartenaire n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return idPartenaire;
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
    String request = "SELECT p.id_partenaire FROM pae.partenaires p WHERE p.nom_legal = ?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setString(1, nomPartenaire);
        try (ResultSet rs = ps.executeQuery()) {
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
      e1.printStackTrace();
    }


    return idPartenaire;
  }

  /**
   * Select l'id de l'inscrit.
   * 
   * @param email introduit
   * @return l'id de l'inscrit
   */
  private int selectIdInscrit(String email) {

    String request = "SELECT i.id_inscrit FROM pae.inscrits i WHERE i.mail = ?";
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
      e.printStackTrace();
    }

    return idUtilisateur;
  }

  /**
   * Liste.
   * 
   * @return une arrayList des mobilites de tous les etudiants
   */
  public ArrayList<MobiliteDto> findAllStudentMobilities() {
    ArrayList<MobiliteDto> array = new ArrayList<MobiliteDto>();
    String request =
        "SELECT m.id_mobilite,i.nom,i.prenom,i.mail,m.num_ordre_preference, p.nom as nom_pays,p.programme,"
            + " par.code,par.nom_complet,m.quadri,m.date_introduction "
            + "FROM pae.mobilites m LEFT JOIN pae.inscrits i ON  m.id_inscrit=i.id_inscrit "
            + "LEFT JOIN pae.partenaires par ON  par.id_partenaire=m.partenaire "
            + "LEFT JOIN pae.pays p ON p.id_pays=par.pays ORDER BY i.nom,m.num_ordre_preference";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {

        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            MobiliteDto demande = new MobiliteImpl();
            demande.setNom(rs.getString("nom"));
            demande.setPrenom(rs.getString("prenom"));
            demande.setMail(rs.getString("mail"));
            demande.setNumOrdre(rs.getInt("num_ordre_preference"));
            demande.setPays(rs.getString("nom_pays"));
            demande.setTypeDeMobilite(rs.getString("programme"));
            demande.setCode(rs.getString("code"));
            demande.setNomPartenaire(rs.getString("nom_complet"));
            demande.setQuadri(rs.getInt("quadri"));
            demande.setDateIntro(rs.getDate("date_introduction").toLocalDate());
            array.add(demande);
          }
        }

      } catch (SQLException exception) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La requête findAllStudentMobilities n'a pas réussi !");
        exception.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return array;
  }

  /**
   * Liste.
   * 
   * @param mail introduit
   * @return une arrayList des mobilites de l'etudiant dont le mail est passee en parametre
   */
  public ArrayList<MobiliteDto> findMobilitesForStudent(String mail) {
    ArrayList<MobiliteDto> array = new ArrayList<MobiliteDto>();
    int idInscrit = selectIdInscrit(mail);

    String request =
        "SELECT m.*,pa.programme as pays_programme FROM pae.mobilites m,pae.pays pa WHERE m.id_inscrit = ? AND m.pays=pa.id_pays ORDER BY m.num_ordre_preference";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idInscrit);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            MobiliteDto demande = new MobiliteImpl();
            demande.setNumOrdre(rs.getInt("num_ordre_preference"));
            int idPays = rs.getInt("pays");
            String requestPays = "SELECT p.nom from pae.pays p WHERE p.id_pays = ?";
            String pays = "";
            try (PreparedStatement psPays = this.dalService.getPreparedStatement(requestPays)) {
              try {
                psPays.setInt(1, idPays);
                try (ResultSet rs2 = psPays.executeQuery()) {

                  while (rs2.next()) {
                    pays = rs2.getString("nom");
                  }
                } catch (SQLException exception) {
                  MyLogger.getLogger().log(Level.SEVERE,
                      "La requête findAllStudentMobilities n'a pas réussi !");
                  exception.printStackTrace();
                  throw new FatalException();
                }



                demande.setPays(pays);
                demande.setTypeDeMobilite(rs.getString("pays_programme"));
                demande.setCode(rs.getString("code"));
                String partenaire = "";
                int idPartenaire = rs.getInt("partenaire");
                String requestPartenaire =
                    "SELECT par.nom_legal from pae.partenaires par WHERE par.id_partenaire = ?";
                try (PreparedStatement psPartenaire =
                    this.dalService.getPreparedStatement(requestPartenaire)) {
                  try {
                    psPartenaire.setInt(1, idPartenaire);
                    ResultSet rsPartenaire = psPartenaire.executeQuery();

                    while (rsPartenaire.next()) {
                      partenaire = rsPartenaire.getString("nom_legal");
                    }
                  } catch (SQLException exception) {
                    MyLogger.getLogger().log(Level.SEVERE,
                        "La requête findAllStudentMobilities n'a pas réussi !");
                    exception.printStackTrace();
                    throw new FatalException();
                  }

                }

                demande.setId_mobilite(rs.getInt("id_mobilite"));
                demande.setNomPartenaire(partenaire);
                demande.setCode(rs.getString("code"));
                demande.setTypeDeMobilite(rs.getString("pays_programme"));
                demande.setQuadri(rs.getInt("quadri"));
                demande.setDateIntro(rs.getDate("date_introduction").toLocalDate());
                array.add(demande);
                System.out.println(demande.getTypeDeMobilite());

              } catch (SQLException e) {
                e.printStackTrace();
              }
            }
          }
        } catch (SQLException exception) {
          MyLogger.getLogger().log(Level.SEVERE,
              "La requête findAllStudentMobilities n'a pas réussi !");
          exception.printStackTrace();
          throw new FatalException();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }

      ArrayList<Integer> idMobDansStatut = selectIdMobiliteDansStatutMobilite();
      for (Iterator<MobiliteDto> iterator = array.iterator(); iterator.hasNext();) {
        MobiliteDto m = iterator.next();
        if (idMobDansStatut.contains(m.getId_mobilite())) {
          iterator.remove();
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return array;
  }

  public ArrayList<MobiliteDto> verifPartenaireExistant(String email) {
    ArrayList<MobiliteDto> mobPartenaireNonExistant = recupererMobiliteNonExistante(email);
    ArrayList<MobiliteDto> mobPartenaire = findMobilitesForStudent(email);
    for (Iterator<MobiliteDto> iterator = mobPartenaire.iterator(); iterator.hasNext();) {
      MobiliteDto m = iterator.next();
      for (Iterator<MobiliteDto> iterator2 = mobPartenaireNonExistant.iterator(); iterator2
          .hasNext();) {
        MobiliteDto m2 = iterator2.next();

        if (m.getId_mobilite() == m2.getId_mobilite()) {
          iterator.remove();
        }
      }
    }
    return mobPartenaire;
  }

  public ArrayList<Integer> selectIdMobiliteDansStatutMobilite() {
    ArrayList<MobiliteDto> array = new ArrayList<MobiliteDto>();
    ArrayList<Integer> idMob = new ArrayList<Integer>();
    String request = "SELECT DISTINCT s.mobilite FROM pae.statut_mobilite s";
    int i = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            i = rs.getInt("mobilite");
            idMob.add(i);
          }
        }

      } catch (SQLException exception) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La requ�te selectIdMobiliteDansStatutMobilite n'a pas reussi !");
        exception.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return idMob;
  }

  @Override
  public ArrayList<MobiliteDto> recupererMobilite(String nomUtilisateur) {
    ArrayList<MobiliteDto> array = new ArrayList<MobiliteDto>();

    String requestInscrit = "SELECT i.id_inscrit FROM pae.inscrits i WHERE i.mail = ?";

    int idUtilisateur = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestInscrit)) {
      try {
        ps.setString(1, nomUtilisateur);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            idUtilisateur = rs.getInt("id_inscrit");
          }
        }

      } catch (SQLException e) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La récupération de idUtilisateur n'a pas réussi");
        e.printStackTrace();
        throw new FatalException();
      }

    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    String request = "SELECT m.num_ordre_preference, m.programme, p.code , p.nom_legal , m.pays , "
        + "m.quadri FROM pae.mobilites m, "
        + "pae.inscrits i, pae.partenaires p WHERE  m.id_inscrit=i.id_inscrit AND m.id_inscrit = ? ";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idUtilisateur);
        try (ResultSet rs2 = ps.executeQuery()) {
          while (rs2.next()) {
            MobiliteDto demande = mobiliteFactory.creerMobilite();
            demande.setNom(nomUtilisateur);
            demande.setNumOrdre(rs2.getInt("num_ordre_preference"));
            demande.setTypeDeMobilite(rs2.getString("programme"));
            demande.setCode(rs2.getString("code"));
            demande.setNomPartenaire(rs2.getString("nom_legal"));
            demande.setPays(rs2.getString("pays"));
            demande.setQuadri(rs2.getInt("quadri"));
            array.add(demande);
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête recupererMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return array;
  }

  /**
   * Liste.
   * 
   * @param nomUtilisateur introduit
   * @return une arrayList des mobilites
   */
  public ArrayList<MobiliteDto> recupererMobiliteNonExistante(String nomUtilisateur) {
    ArrayList<MobiliteDto> array = new ArrayList<MobiliteDto>();
    String requestInscrit = "SELECT i.id_inscrit FROM pae.inscrits i WHERE i.mail = ?";
    int idUtilisateur = 0;
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestInscrit)) {
      try {
        ps.setString(1, nomUtilisateur);
        try (ResultSet rsInscrit = ps.executeQuery()) {
          while (rsInscrit.next()) {
            idUtilisateur = rsInscrit.getInt("id_inscrit");
          }
        }

      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE,
            "La récupération de idUtilisateur n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }


    String request =
        "SELECT m.id_mobilite ,pa.programme, m.num_ordre_preference, m.partenaire, p.code , p.nom_legal , m.pays , "
            + "m.quadri FROM pae.mobilites m, pae.inscrits i, pae.partenaires p,pae.pays pa "
            + "WHERE m.id_inscrit=i.id_inscrit AND m.partenaire=p.id_partenaire AND m.pays=pa.id_pays"
            + " AND p.site_web IS NULL AND i.id_inscrit = ? ";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, idUtilisateur);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            MobiliteDto demande = mobiliteFactory.creerMobilite();
            demande.setId_mobilite(rs.getInt("id_mobilite"));
            demande.setMail(nomUtilisateur);
            demande.setTypeDeMobilite(rs.getString("programme"));
            System.out.println("non existantte :" + demande.getTypeDeMobilite());
            String partenaire = "";
            int idPartenaire = rs.getInt("partenaire");
            String requestPartenaire =
                "SELECT par.nom_legal from pae.partenaires par WHERE par.id_partenaire = ?";
            try (PreparedStatement psPartenaire =
                this.dalService.getPreparedStatement(requestPartenaire)) {
              try {
                psPartenaire.setInt(1, idPartenaire);
                try (ResultSet rsPartenaire = psPartenaire.executeQuery()) {
                  while (rsPartenaire.next()) {
                    partenaire = rsPartenaire.getString("nom_legal");
                  }
                }
              } catch (SQLException exception) {
                MyLogger.getLogger().log(Level.SEVERE,
                    "La requ�te findAllStudentMobilities n'a pas reussi !");
                exception.printStackTrace();
                throw new FatalException();
              }

            }
            demande.setNomPartenaire(partenaire);
            demande.setNumOrdre(rs.getInt("num_ordre_preference"));
            demande.setTypeDeMobilite(rs.getString("programme"));
            demande.setCode(rs.getString("code"));
            demande.setNomPartenaire(rs.getString("nom_legal"));
            demande.setPays(rs.getString("pays"));
            demande.setQuadri(rs.getInt("quadri"));
            array.add(demande);
          }
        }


      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête recupererMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return array;
  }

  public boolean updateMobilite(MobiliteDto mobilite, int idMobilite, int version) {

    String pays = mobilite.getPays();
    String nomPartenaire = mobilite.getNomPartenaire();
    String typeDeMobilite = mobilite.getTypeDeMobilite();
    String code = mobilite.getCode();
    int numOrdre = mobilite.getNumOrdre();
    int quadri = mobilite.getQuadri();
    // int idMobilite = mobilite.getId_mobilite();
    // int version = mobilite.getVersion();
    version = 11;
    System.out.println("id + version" + idMobilite + " " + version);

    if (pays != "") {
      int idPays = selectIdPays(pays);
      if (!checkPaysTypeMobilite(idPays, typeDeMobilite)) {
        System.out.println("ko1");
        return false;
      }
    }
    // if (nomPartenaire != "") {
    // int idPartenaire = selectIdPartenaire(nomPartenaire);
    // System.out.println("ko2");
    // if (!checkCodePartenaire(idPartenaire, code)) {
    // return false;
    // }
    // }
    int result = 0;

    String request =
        "UPDATE pae.mobilites SET num_ordre_preference=? , pays=?, partenaire=? , programme=?, code=? , quadri=?, version=? WHERE id_mobilite=? AND version=?";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        ps.setInt(1, numOrdre);
        if (pays != "") {
          int idPays = selectIdPays(pays);
          ps.setInt(2, idPays);
        } else {
          ps.setNull(2, java.sql.Types.NULL);
        }
        if (nomPartenaire != "") {
          int idPartenaire = selectIdPartenaire(nomPartenaire);
          ps.setInt(3, idPartenaire);
        } else {
          ps.setNull(3, java.sql.Types.NULL);
        }

        ps.setString(4, typeDeMobilite);
        ps.setString(5, code);
        if (quadri == 0) {
          ps.setNull(6, java.sql.Types.NULL);
        } else {
          ps.setInt(6, quadri);
        }
        ps.setInt(7, version + 1);
        ps.setInt(8, idMobilite);
        ps.setInt(9, version);
        result = ps.executeUpdate();
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête updateMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result != 0;
  }

  private boolean checkCodePartenaire(int idPartenaire, String code) {
    String requestPartenaire = "SELECT p.code FROM pae.partenaires p WHERE p.id_partenaire=?";
    String BonCode = "";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestPartenaire)) {
      try {
        ps.setInt(1, idPartenaire);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            BonCode = rs.getString("code");
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête checkCodePartenaire n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (!code.equals(BonCode)) {
      return false;
    }
    return true;
  }

  private boolean checkPaysTypeMobilite(int idPays, String typeDeMobilite) {
    String requestPartenaire = "SELECT p.programme FROM pae.pays p WHERE p.id_pays=?";
    String BonId = "";

    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestPartenaire)) {
      try {
        ps.setInt(1, idPays);
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            BonId = rs.getString("programme");
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête checkPaysTypeMobilite n'a pas réussi");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {

      e.printStackTrace();
    }

    if (!typeDeMobilite.equals(BonId)) {
      return false;
    }
    return true;
  }

  public ArrayList<MobiliteDto> rechercheMobilite(String annee, String etat) {

    String requestAnnee = "";
    String requestEtat = "";
    String request =
        "SELECT m.*, pa.nom AS nom_pays, p.nom_legal AS nom_legal, i.mail AS mail, i.nom AS nom, i.prenom AS prenom FROM pae.inscrits i, pae.statut_mobilite sm, pae.mobilites m "
            + "LEFT JOIN pae.partenaires p ON p.id_partenaire = m.partenaire "
            + "LEFT JOIN pae.pays pa ON pa.id_pays = m.pays WHERE i.id_inscrit = m.id_inscrit AND m.id_mobilite = sm.mobilite AND ";
    int ordre = 0;

    if (!annee.isEmpty()) {
      requestAnnee = "m.annee_acad = ?";
    }
    if (!etat.isEmpty()) {
      requestEtat = "lower(sm.etat) LIKE lower(?)";
    }

    if (!annee.isEmpty() && !etat.isEmpty()) {
      requestEtat = " AND " + requestEtat;
      ordre = 1;
    } else if (!annee.isEmpty() && etat.isEmpty()) {
      ordre = 2;
    } else if (annee.isEmpty() && !etat.isEmpty()) {
      ordre = 3;
    } else {
      ordre = 4;
    }
    request += requestAnnee + requestEtat;

    etat += '%';
    ArrayList<MobiliteDto> liste = new ArrayList<MobiliteDto>();

    try (PreparedStatement ps = this.dalService.getPreparedStatement(request)) {
      try {
        switch (ordre) {
          case 1:
            ps.setString(1, annee);
            ps.setString(2, etat);
            break;
          case 2:
            ps.setString(1, annee);
            break;
          case 3:
            ps.setString(1, etat);
            break;
          case 4:
            return liste;
          default:
            break;
        }
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            MobiliteDto rechercheMob = mobiliteFactory.creerMobilite();
            rechercheMob.setAnneeAcademique(rs.getString("annee_acad"));
            rechercheMob.setCode(rs.getString("code"));
            if (rs.getDate("date_introduction") != null) {
              rechercheMob.setDateIntro(rs.getDate("date_introduction").toLocalDate());
            }
            rechercheMob.setMail(rs.getString("mail"));
            rechercheMob.setMail(rs.getString("nom"));
            rechercheMob.setMail(rs.getString("prenom"));
            rechercheMob.setNomPartenaire(rs.getString("nom_legal"));
            rechercheMob.setNumOrdre(rs.getInt("num_ordre_preference"));
            rechercheMob.setPays(rs.getString("nom_pays"));
            rechercheMob.setQuadri(rs.getInt("quadri"));
            rechercheMob.setTypeDeMobilite(rs.getString("programme"));
            liste.add(rechercheMob);
          }
        }
      } catch (SQLException ex) {
        MyLogger.getLogger().log(Level.SEVERE, "La requête rechercheMobilite n'a pas réussi !");
        ex.printStackTrace();
        throw new FatalException();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return liste;
  }

  public int getNumVersion(int id) {

    int version = 0;
    String requestVersion = "SELECT m.version FROM pae.mobilites m WHERE m.id_mobilite=?";
    try (PreparedStatement ps = this.dalService.getPreparedStatement(requestVersion)) {
      try {
        ps.setInt(1, id);
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NullPointerException ne) {
      version = 0;
      ne.printStackTrace();
    }
    return version;
  }
}
