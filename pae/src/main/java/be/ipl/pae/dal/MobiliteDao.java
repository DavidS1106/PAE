package be.ipl.pae.dal;

import java.util.ArrayList;
import be.ipl.pae.mobilite.MobiliteDto;


public interface MobiliteDao {

  boolean insererMobilite(MobiliteDto mobilite);

  boolean insererMobiliteComplet(MobiliteDto mobilite, String programme);

  ArrayList<MobiliteDto> recupererMobilite(String nomUtilisateur);

  ArrayList<MobiliteDto> recupererMobiliteNonExistante(String nomUtilisateur);

  ArrayList<MobiliteDto> findAllStudentMobilities();

  ArrayList<MobiliteDto> findMobilitesForStudent(String mail);

  boolean updateMobilite(MobiliteDto mobilite, int idMobilite, int version);

  ArrayList<MobiliteDto> rechercheMobilite(String annee, String etat);

  ArrayList<MobiliteDto> verifPartenaireExistant(String email);

  int getNumVersion(int id);
}
