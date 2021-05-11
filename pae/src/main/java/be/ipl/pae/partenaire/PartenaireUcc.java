package be.ipl.pae.partenaire;

import java.util.ArrayList;

public interface PartenaireUcc {

  PartenaireDto recupererInfo(String email);

  boolean updateInfo(PartenaireDto partenaire);

  ArrayList<PartenaireDto> getPartenaires();

  ArrayList<String> getNomsPartenaires();

  ArrayList<PartenaireDto> recherchePartenaire(PartenaireDto partenaire);



}
