package be.ipl.pae.dal;

import java.util.ArrayList;
import be.ipl.pae.utilisateur.UtilisateurDto;

public interface UtilisateurDao { // <T>

  // abstract <T> ...
  UtilisateurDto findByMail(String mail);

  UtilisateurDto insertUser(UtilisateurDto utilisateur);

  ArrayList<UtilisateurDto> selectInscrits();

  ArrayList<UtilisateurDto> selectEtudiantsSignature();

  ArrayList<UtilisateurDto> SelectStudents();

  ArrayList<String> getRaisons();

  ArrayList<String> recupererNomPays();

}
