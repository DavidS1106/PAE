package be.ipl.pae.main;

import java.io.InputStream;
import java.util.Properties;
import be.ipl.pae.backend.EtudiantServlet;
import be.ipl.pae.backend.MainServlet;
import be.ipl.pae.backend.MobiliteServlet;
import be.ipl.pae.backend.PartenaireServlet;
import be.ipl.pae.backend.Serveur;
import be.ipl.pae.backend.SessionServlet;
import be.ipl.pae.backend.StatutMobiliteServlet;
import be.ipl.pae.backend.UtilServlet;
import be.ipl.pae.dal.DalService;
import be.ipl.pae.dal.DalServiceImpl;
import be.ipl.pae.dal.EtudiantDao;
import be.ipl.pae.dal.EtudiantDaoImpl;
import be.ipl.pae.dal.MobiliteDao;
import be.ipl.pae.dal.MobiliteDaoImpl;
import be.ipl.pae.dal.PartenaireDao;
import be.ipl.pae.dal.PartenaireDaoImpl;
import be.ipl.pae.dal.StatutMobiliteDao;
import be.ipl.pae.dal.StatutMobiliteDaoImpl;
import be.ipl.pae.dal.UtilisateurDao;
import be.ipl.pae.dal.UtilisateurDaoImpl;
import be.ipl.pae.etudiant.EtudiantUcc;
import be.ipl.pae.etudiant.EtudiantUccImpl;
import be.ipl.pae.etudiant.FactoryEtudiant;
import be.ipl.pae.etudiant.FactoryEtudiantImpl;
import be.ipl.pae.mobilite.FactoryMobilite;
import be.ipl.pae.mobilite.FactoryMobiliteImpl;
import be.ipl.pae.mobilite.MobiliteUcc;
import be.ipl.pae.mobilite.MobiliteUccImpl;
import be.ipl.pae.partenaire.FactoryPartenaire;
import be.ipl.pae.partenaire.FactoryPartenaireImpl;
import be.ipl.pae.partenaire.PartenaireUcc;
import be.ipl.pae.partenaire.PartenaireUccImpl;
import be.ipl.pae.statutmobilite.FactoryStatutMobilite;
import be.ipl.pae.statutmobilite.FactoryStatutMobiliteImpl;
import be.ipl.pae.statutmobilite.StatutMobiliteUcc;
import be.ipl.pae.statutmobilite.StatutMobiliteUccImpl;
import be.ipl.pae.utilisateur.FactoryUtilisateur;
import be.ipl.pae.utilisateur.FactoryUtilisateurImpl;
import be.ipl.pae.utilisateur.UtilisateurUcc;
import be.ipl.pae.utilisateur.UtilisateurUccImpl;
import log.MyLogger;
import util.Util;

public class Main {

  /**
   * Main de l'application.
   * 
   * @param args tableau de String
   * @throws Exception à lancer lors d'erreur
   */
  public static void main(String[] args) throws Exception {

    // Creation Logger

    MyLogger.setup();

    // Chargement properties

    Properties prop = new Properties();
    InputStream input = null;
    Util.loadProperties(prop, input);
    DalService dalService = new DalServiceImpl();
    Class<?> classe;

    // DEBUT INJECTION DEPENDANCES

    // UtilisateurFactory
    Class<?> classeFactoryUtilisateur =
        Class.forName(prop.getProperty("be.ipl.pae.utilisateur.factoryUtilisateur"));

    // EtudiantFactory
    Class<?> classeFactoryEtudiant =
        Class.forName(prop.getProperty("be.ipl.pae.etudiant.factoryEtudiant"));


    // MobiliteFactory
    Class<?> classeFactoryMobilite =
        Class.forName(prop.getProperty("be.ipl.pae.mobilite.factoryMobilite"));


    // StatutMobiliteFactory
    Class<?> classeFactoryStatutMobilite =
        Class.forName(prop.getProperty("be.ipl.pae.statutmobilite.factoryStatutMobilite"));


    // PartenaireFactory
    Class<?> classeFactoryPartenaire =
        Class.forName(prop.getProperty("be.ipl.pae.partenaire.factoryPartenaire"));


    // EtudiantDao
    Class<?> classeEtudiantDao = Class.forName(prop.getProperty("be.ipl.pae.dal.etudiantDao"));


    // UtilisateurDao
    Class<?> classeUtilisateurDao =
        Class.forName(prop.getProperty("be.ipl.pae.dal.utilisateurDao"));


    // StatutMobiliteDao
    Class<?> classeStatutMobiliteDao =
        Class.forName(prop.getProperty("be.ipl.pae.dal.statutMobiliteDao"));


    // PartenaireDao
    Class<?> classePartenaireDao = Class.forName(prop.getProperty("be.ipl.pae.dal.partenaireDao"));


    // MobiliteDao
    Class<?> classeMobiliteDao = Class.forName(prop.getProperty("be.ipl.pae.dal.mobiliteDao"));


    // EtudiantUcc
    Class<?> classeEtudiantUcc = Class.forName(prop.getProperty("be.ipl.pae.etudiant.etudiantUcc"));


    // MobiliteUcc
    Class<?> classeMobiliteUcc = Class.forName(prop.getProperty("be.ipl.pae.mobilite.mobiliteUcc"));


    // UtilisateurUcc
    Class<?> classeUtilisateurUcc =
        Class.forName(prop.getProperty("be.ipl.pae.utilisateur.utilisateurUcc"));


    // SessionServlet
    Class<?> classeSessionServlet =
        Class.forName(prop.getProperty("be.ipl.pae.backend.sessionServlet"));


    // StatutMobiliteUcc
    Class<?> classeStatutMobiliteUcc =
        Class.forName(prop.getProperty("be.ipl.pae.statutmobilite.statutMobiliteUcc"));


    // PartenaireUcc
    Class<?> classePartenaireUcc =
        Class.forName(prop.getProperty("be.ipl.pae.partenaire.partenaireUcc"));


    // MobiliteServlet
    Class<?> classeMobiliteServlet =
        Class.forName(prop.getProperty("be.ipl.pae.backend.mobiliteServlet"));


    // EtudiantServlet
    Class<?> classeEtudiantServlet =
        Class.forName(prop.getProperty("be.ipl.pae.backend.etudiantServlet"));


    // PartenaireServlet
    Class<?> classePartenaireServlet =
        Class.forName(prop.getProperty("be.ipl.pae.backend.partenaireServlet"));


    // StatutMobiliteServlet
    Class<?> classeStatutMobiliteServlet =
        Class.forName(prop.getProperty("be.ipl.pae.backend.statutMobiliteServlet"));


    // Servlet

    // classe = Class.forName(prop.getProperty("be.ipl.pae.backend.servlet"));
    // Servlet servlet = (Servlet) Util.newObject(classe, factoryUtilisateur, factoryEtudiant,
    // factoryPartenaire, factoryMobilite, mobiliteUcc, statutMobiliteUcc, utilisateurUcc,
    // etudiantUcc, partenaireUcc);
    //
    // Serveur.demarrerServeur(Integer.parseInt(prop.getProperty("portServeur")), servlet);

    // pour demarrer le serveur avec le MainServlet

    Class<?> classeMainServlet = Class.forName(prop.getProperty("be.ipl.pae.backend.mainServlet"));

    Class<?> classeUtilServlet = Class.forName(prop.getProperty("be.ipl.pae.backend.utilServlet"));


    UtilServlet utilServlet = (UtilServlet) Util.newObject(classeUtilServlet);

    FactoryUtilisateur factoryUtilisateur =
        (FactoryUtilisateurImpl) Util.newObject(classeFactoryUtilisateur);

    FactoryEtudiant factoryEtudiant = (FactoryEtudiantImpl) Util.newObject(classeFactoryEtudiant);

    FactoryMobilite factoryMobilite = (FactoryMobiliteImpl) Util.newObject(classeFactoryMobilite);

    FactoryStatutMobilite factoryStatutMobilite =
        (FactoryStatutMobiliteImpl) Util.newObject(classeFactoryStatutMobilite);

    FactoryPartenaire factoryPartenaire =
        (FactoryPartenaireImpl) Util.newObject(classeFactoryPartenaire);


    EtudiantDao etudiantDao = (EtudiantDaoImpl) Util.newObject(classeEtudiantDao, factoryEtudiant,
        dalService, factoryUtilisateur);

    UtilisateurDao utilisateurDao = (UtilisateurDaoImpl) Util.newObject(classeUtilisateurDao,
        dalService, factoryUtilisateur, factoryMobilite);

    StatutMobiliteDao statutMobiliteDao = (StatutMobiliteDaoImpl) Util
        .newObject(classeStatutMobiliteDao, dalService, factoryStatutMobilite);

    PartenaireDao partenaireDao =
        (PartenaireDaoImpl) Util.newObject(classePartenaireDao, dalService, factoryPartenaire);


    MobiliteDao mobiliteDao =
        (MobiliteDaoImpl) Util.newObject(classeMobiliteDao, dalService, factoryMobilite);

    EtudiantUcc etudiantUcc =
        (EtudiantUccImpl) Util.newObject(classeEtudiantUcc, etudiantDao, dalService);

    MobiliteUcc mobiliteUcc =
        (MobiliteUccImpl) Util.newObject(classeMobiliteUcc, mobiliteDao, dalService);

    UtilisateurUcc utilisateurUcc =
        (UtilisateurUccImpl) Util.newObject(classeUtilisateurUcc, utilisateurDao, dalService);

    SessionServlet sessionServlet = (SessionServlet) Util.newObject(classeSessionServlet,
        utilisateurUcc, utilServlet, factoryUtilisateur);


    StatutMobiliteUcc statutMobiliteUcc = (StatutMobiliteUccImpl) Util
        .newObject(classeStatutMobiliteUcc, statutMobiliteDao, dalService);


    PartenaireUcc partenaireUcc =
        (PartenaireUccImpl) Util.newObject(classePartenaireUcc, partenaireDao, dalService);

    MobiliteServlet mobiliteServlet =
        (MobiliteServlet) Util.newObject(classeMobiliteServlet, factoryMobilite, mobiliteUcc,
            utilisateurUcc, statutMobiliteUcc, sessionServlet, utilServlet);

    EtudiantServlet etudiantServlet = (EtudiantServlet) Util.newObject(classeEtudiantServlet,
        factoryEtudiant, utilisateurUcc, etudiantUcc, sessionServlet, utilServlet);


    PartenaireServlet partenaireServlet =
        (PartenaireServlet) Util.newObject(classePartenaireServlet, factoryPartenaire,
            partenaireUcc, sessionServlet, utilServlet);

    StatutMobiliteServlet statutMobiliteServlet =
        (StatutMobiliteServlet) Util.newObject(classeStatutMobiliteServlet, factoryStatutMobilite,
            statutMobiliteUcc, sessionServlet, utilServlet);

    MainServlet main = (MainServlet) Util.newObject(classeMainServlet, mobiliteServlet,
        sessionServlet, etudiantServlet, partenaireServlet, statutMobiliteServlet, utilServlet);


    Serveur.demarrerServeur(Integer.parseInt(prop.getProperty("portServeur")), main);



    /*
     * // UtilisateurFactory classe =
     * Class.forName(prop.getProperty("be.ipl.pae.utilisateur.factoryUtilisateur"));
     * FactoryUtilisateur factoryUtilisateur = (FactoryUtilisateurImpl) Util.newObject(classe);
     * 
     * // EtudiantFactory classe =
     * Class.forName(prop.getProperty("be.ipl.pae.etudiant.factoryEtudiant")); FactoryEtudiant
     * factoryEtudiant = (FactoryEtudiantImpl) Util.newObject(classe);
     * 
     * // MobiliteFactory classe =
     * Class.forName(prop.getProperty("be.ipl.pae.mobilite.factoryMobilite")); FactoryMobilite
     * factoryMobilite = (FactoryMobiliteImpl) Util.newObject(classe);
     * 
     * // StatutMobiliteFactory classe =
     * Class.forName(prop.getProperty("be.ipl.pae.statutmobilite.factoryStatutMobilite"));
     * FactoryStatutMobilite factoryStatutMobilite = (FactoryStatutMobiliteImpl)
     * Util.newObject(classe);
     * 
     * // PartenaireFactory classe =
     * Class.forName(prop.getProperty("be.ipl.pae.partenaire.factoryPartenaire")); FactoryPartenaire
     * factoryPartenaire = (FactoryPartenaireImpl) Util.newObject(classe);
     * 
     * // EtudiantDao classe = Class.forName(prop.getProperty("be.ipl.pae.dal.etudiantDao"));
     * EtudiantDao etudiantDao = (EtudiantDaoImpl) Util.newObject(classe, factoryEtudiant,
     * dalService, factoryUtilisateur);
     * 
     * // UtilisateurDao classe = Class.forName(prop.getProperty("be.ipl.pae.dal.utilisateurDao"));
     * UtilisateurDao utilisateurDao = (UtilisateurDaoImpl) Util.newObject(classe, dalService,
     * factoryUtilisateur, factoryMobilite);
     * 
     * // StatutMobiliteDao classe =
     * Class.forName(prop.getProperty("be.ipl.pae.dal.statutMobiliteDao")); StatutMobiliteDao
     * statutMobiliteDao = (StatutMobiliteDaoImpl) Util.newObject(classe, dalService,
     * factoryStatutMobilite);
     * 
     * // PartenaireDao classe = Class.forName(prop.getProperty("be.ipl.pae.dal.partenaireDao"));
     * PartenaireDao partenaireDao = (PartenaireDaoImpl) Util.newObject(classe, dalService,
     * factoryPartenaire);
     * 
     * // MobiliteDao classe = Class.forName(prop.getProperty("be.ipl.pae.dal.mobiliteDao"));
     * MobiliteDao mobiliteDao = (MobiliteDaoImpl) Util.newObject(classe, dalService,
     * factoryMobilite);
     * 
     * // EtudiantUcc classe = Class.forName(prop.getProperty("be.ipl.pae.etudiant.etudiantUcc"));
     * EtudiantUcc etudiantUcc = (EtudiantUccImpl) Util.newObject(classe, etudiantDao);
     * 
     * // MobiliteUcc classe = Class.forName(prop.getProperty("be.ipl.pae.mobilite.mobiliteUcc"));
     * MobiliteUcc mobiliteUcc = (MobiliteUccImpl) Util.newObject(classe, mobiliteDao);
     * 
     * // UtilisateurUcc classe =
     * Class.forName(prop.getProperty("be.ipl.pae.utilisateur.utilisateurUcc")); UtilisateurUcc
     * utilisateurUcc = (UtilisateurUccImpl) Util.newObject(classe, utilisateurDao, mobiliteDao,
     * partenaireDao);
     * 
     * // SessionServlet classe =
     * Class.forName(prop.getProperty("be.ipl.pae.backend.sessionServlet")); SessionServlet
     * sessionServlet = (SessionServlet) Util.newObject(classe, utilisateurUcc);
     * 
     * // StatutMobiliteUcc classe =
     * Class.forName(prop.getProperty("be.ipl.pae.statutmobilite.statutMobiliteUcc"));
     * StatutMobiliteUcc statutMobiliteUcc = (StatutMobiliteUccImpl) Util.newObject(classe,
     * statutMobiliteDao);
     * 
     * // PartenaireUcc classe =
     * Class.forName(prop.getProperty("be.ipl.pae.partenaire.partenaireUcc")); PartenaireUcc
     * partenaireUcc = (PartenaireUccImpl) Util.newObject(classe, partenaireDao);
     * 
     * 
     * // MobiliteServlet classe =
     * Class.forName(prop.getProperty("be.ipl.pae.backend.mobiliteServlet")); MobiliteServlet
     * mobiliteServlet = (MobiliteServlet) Util.newObject(classe, mobiliteUcc, utilisateurUcc,
     * statutMobiliteUcc, sessionServlet);
     * 
     * 
     * // EtudiantServlet classe =
     * Class.forName(prop.getProperty("be.ipl.pae.backend.etudiantServlet")); EtudiantServlet
     * etudiantServlet = (EtudiantServlet) Util.newObject(classe, factoryEtudiant, utilisateurUcc,
     * etudiantUcc, sessionServlet);
     * 
     * // PartenaireServlet classe =
     * Class.forName(prop.getProperty("be.ipl.pae.backend.partenaireServlet")); PartenaireServlet
     * partenaireServlet = (PartenaireServlet) Util.newObject(classe, partenaireUcc);
     * 
     * // StatutMobiliteServlet classe =
     * Class.forName(prop.getProperty("be.ipl.pae.backend.statutMobiliteServlet"));
     * StatutMobiliteServlet statutMobiliteServlet = (StatutMobiliteServlet) Util.newObject(classe,
     * statutMobiliteUcc, sessionServlet);
     * 
     * // Servlet
     * 
     * // classe = Class.forName(prop.getProperty("be.ipl.pae.backend.servlet")); // Servlet servlet
     * = (Servlet) Util.newObject(classe, factoryUtilisateur, factoryEtudiant, // factoryPartenaire,
     * factoryMobilite, mobiliteUcc, statutMobiliteUcc, utilisateurUcc, // etudiantUcc,
     * partenaireUcc); // //
     * Serveur.demarrerServeur(Integer.parseInt(prop.getProperty("portServeur")), servlet);
     * 
     * // pour demarrer le serveur avec le MainServlet
     * 
     * classe = Class.forName(prop.getProperty("be.ipl.pae.backend.mainServlet")); MainServlet main
     * = (MainServlet) Util.newObject(classe, mobiliteServlet, sessionServlet, etudiantServlet,
     * partenaireServlet, statutMobiliteServlet);
     * 
     * Serveur.demarrerServeur(Integer.parseInt(prop.getProperty("portServeur")), main);
     */
  }
}


