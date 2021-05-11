package be.ipl.pae.dal;

import java.util.ArrayList;
import be.ipl.pae.partenaire.PartenaireDto;


public interface PartenaireDao {

  PartenaireDto findByEmail(String email);

  ArrayList<PartenaireDto> recherchePartenaires(PartenaireDto part);

  boolean updateInfoPartenaire(PartenaireDto partenaire);

  ArrayList<PartenaireDto> selectPartenaires();

  ArrayList<String> selectNomsPartenaires();

  ArrayList<PartenaireDto> recherchePartenaire(PartenaireDto partenaire);

  int getNumversion(String nomLegal);
}
