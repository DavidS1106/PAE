package be.ipl.pae.utilisateur;

import java.util.ArrayList;

public interface UtilisateurUcc {

  UtilisateurDto seConnecter(UtilisateurDto utilisateur);

  UtilisateurDto inscription(UtilisateurDto utilisateur);

  UtilisateurDto getUser(String mail);

  ArrayList<UtilisateurDto> recupererEtudiantsSignature();

  ArrayList<UtilisateurDto> getEtudiants();

  ArrayList<UtilisateurDto> getInscrits();

  ArrayList<String> raisonsAnnulation();

  ArrayList<String> getNomPays();

}
