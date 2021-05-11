package be.ipl.pae.statutmobilite;

public interface StatutMobiliteDto {

  enum Etat {
    C, EP, AP, APS
  }

  StatutMobiliteImpl deserialiseur(String json);

  Etat getEtat();

  void setEtat(Etat etat);

  boolean isEnvoiEtudiant();

  void setEnvoiEtudiant(boolean envoiEtudiant);

  boolean isEnvoiDemandePremierPaiement();

  void setEnvoiDemandePremierPaiement(boolean envoiDemandePremierPaiement);

  boolean isAnnule();

  void setAnnule(boolean annule);

  boolean isDocumentsSignes();

  void setDocumentsSignes(boolean documentsSignes);

  int getMobilite();

  void setMobilite(int mobilite);

  String getRaisonAnnulationEtudiant();

  void setRaisonAnnulationEtudiant(String raisonAnnulationEtudiant);

  String getRaisonAnnulationProfesseur();

  void setRaisonAnnulationProfesseur(String raisonAnnulationProfesseur);

  boolean isEnvoiDemandeSecondPaiement();

  void setEnvoiDemandeSecondPaiement(boolean envoiDemandeSecondPaiement);

  boolean isContratBourse();

  void setContratBourse(boolean contratBourse);

  boolean isConventionStage();

  void setConventionStage(boolean conventionStage);

  boolean isConventionEtude();

  void setConventionEtude(boolean conventionEtude);

  boolean isCharteEtudiant();

  void setCharteEtudiant(boolean charteEtudiant);

  boolean isDocumentEngagement();

  void setDocumentEngagement(boolean documentEngagement);

  boolean isDemandePremierPaiement();

  void setDemandePremierPaiement(boolean demandePremierPaiement);

  boolean isPremierPaiement();

  void setPremierPaiement(boolean premierPaiement);

  boolean isInfoEtudiant();

  void setInfoEtudiant(boolean infoEtudiant);

  boolean isInfoPartenaire();

  void setInfoPartenaire(boolean infoPartenaire);

  boolean isAttestationSejour();

  void setAttestationSejour(boolean attestationSejour);

  boolean isReleveNotes();

  void setReleveNotes(boolean releveNotes);

  boolean isCertificatStage();

  void setCertificatStage(boolean certificatStage);

  boolean isRapportFinal();

  void setRapportFinal(boolean rapportFinal);

  boolean isDemandeSecondPaiement();

  void setDemandeSecondPaiement(boolean demandeSecondPaiement);

  boolean isSecondPaiement();

  void setSecondPaiement(boolean secondPaiement);
}
