package be.ipl.pae.mobilite;

import java.util.ArrayList;


public interface MobiliteUcc {

  boolean insererMob(MobiliteDto mobilite);

  boolean insererMobComplet(MobiliteDto mobilite, String programme);

  ArrayList<MobiliteDto> recupererMob(String email);

  ArrayList<MobiliteDto> recupStudMob(String email);

  ArrayList<MobiliteDto> recupererMobNonExistante(String email);

  boolean updateMob(MobiliteDto mobilite, int idMobilite);

  ArrayList<MobiliteDto> rechercheMobilite(String annee, String etat);

  public ArrayList<MobiliteDto> recupererMobilites();
}
