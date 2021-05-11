package be.ipl.pae.statutmobilite;

import com.owlike.genson.Genson;

public class StatutMobiliteImpl implements StatutMobilite {

  private Etat etat;
  private boolean envoiEtudiant;
  private boolean envoiDemandePremierPaiement;
  private boolean annule;
  private boolean documentsSignes;
  private boolean envoiDemandeSecondPaiement;
  private int mobilite;
  private String raisonAnnulationEtudiant;
  private String raisonAnnulationProfesseur;
  private boolean contratBourse;
  private boolean conventionStage;
  private boolean conventionEtude;
  private boolean charteEtudiant;
  private boolean documentEngagement;
  private boolean demandePremierPaiement;
  private boolean premierPaiement;
  private boolean infoEtudiant;
  private boolean infoPartenaire;
  private boolean attestationSejour;
  private boolean releveNotes;
  private boolean certificatStage;
  private boolean rapportFinal;
  private boolean demandeSecondPaiement;
  private boolean secondPaiement;
  private boolean partiEnMobilite;
  private int numVersion;

  /**
   * Constructeur du statut de la mobilité.
   * 
   * @param etat de la mobilité
   * @param envoiEtudiant true si la demande a été envoyé
   * @param envoiDemandePremierPaiement true si la première demande de paiement a été effectué
   * @param annule true si annulé
   * @param documentsSignes true si les documents ont été signés
   * @param envoiDemandeSecondPaiement true si la seconde demande de paiement a été effectué
   * @param mobilite de l'étudiant
   * @param raisonAnnulationEtudiant raison de l'élève pour annuler
   * @param raisonAnnulationProfesseur raison du professeur pour annuler
   * @param contratBourse true si signé
   * @param conventionStage true si signé
   * @param conventionEtude true si signé
   * @param charteEtudiant true si signé
   * @param documentEngagement true si signé
   * @param demandePremierPaiement true si demandé
   * @param premierPaiement true si effectué
   * @param infoEtudiant true si complété
   * @param infopartenaire true si complété
   * @param attestationSejour true si reçue
   * @param releveNotes true si reçu
   * @param certificateStage true si reçu
   * @param rapportFinal true si reçu
   * @param demandeSecondPaiement true si demandé
   * @param secondPaiement true si effectué
   * @param partiEnMobilite true si parti
   */
  public StatutMobiliteImpl(Etat etat, boolean envoiEtudiant, boolean envoiDemandePremierPaiement,
      boolean annule, boolean documentsSignes, boolean envoiDemandeSecondPaiement, int mobilite,
      String raisonAnnulationEtudiant, String raisonAnnulationProfesseur, boolean contratBourse,
      boolean conventionStage, boolean conventionEtude, boolean charteEtudiant,
      boolean documentEngagement, boolean demandePremierPaiement, boolean premierPaiement,
      boolean infoEtudiant, boolean infoPartenaire, boolean attestationSejour, boolean releveNotes,
      boolean certificatStage, boolean rapportFinal, boolean demandeSecondPaiement,
      boolean secondPaiement, boolean partiEnMobilite, int numVersion) {
    this.etat = etat;
    this.envoiEtudiant = envoiEtudiant;
    this.envoiDemandePremierPaiement = envoiDemandePremierPaiement;
    this.annule = annule;
    this.documentsSignes = documentsSignes;
    this.envoiDemandeSecondPaiement = envoiDemandeSecondPaiement;
    this.mobilite = mobilite;
    this.raisonAnnulationEtudiant = raisonAnnulationEtudiant;
    this.raisonAnnulationProfesseur = raisonAnnulationProfesseur;
    this.contratBourse = contratBourse;
    this.conventionStage = conventionStage;
    this.conventionEtude = conventionEtude;
    this.charteEtudiant = charteEtudiant;
    this.documentEngagement = documentEngagement;
    this.demandePremierPaiement = demandePremierPaiement;
    this.premierPaiement = premierPaiement;
    this.infoEtudiant = infoEtudiant;
    this.infoPartenaire = infoPartenaire;
    this.attestationSejour = attestationSejour;
    this.releveNotes = releveNotes;
    this.certificatStage = certificatStage;
    this.rapportFinal = rapportFinal;
    this.demandeSecondPaiement = demandeSecondPaiement;
    this.secondPaiement = secondPaiement;
    this.partiEnMobilite = partiEnMobilite;
    this.numVersion = numVersion;
  }

  public StatutMobiliteImpl() {

  }

  public StatutMobiliteImpl deserialiseur(String json) {
    return new Genson().deserialize(json, StatutMobiliteImpl.class);
  }

  public Etat getEtat() {
    return etat;
  }

  public void setEtat(Etat etat) {
    this.etat = etat;
  }

  public boolean isEnvoiEtudiant() {
    return envoiEtudiant;
  }

  public void setEnvoiEtudiant(boolean envoiEtudiant) {
    this.envoiEtudiant = envoiEtudiant;
  }

  public boolean isEnvoiDemandePremierPaiement() {
    return envoiDemandePremierPaiement;
  }

  public void setEnvoiDemandePremierPaiement(boolean envoiDemandePremierPaiement) {
    this.envoiDemandePremierPaiement = envoiDemandePremierPaiement;
  }

  public boolean isAnnule() {
    return annule;
  }

  public void setAnnule(boolean annule) {
    this.annule = annule;
  }

  public boolean isDocumentsSignes() {
    return documentsSignes;
  }

  public void setDocumentsSignes(boolean documentsSignes) {
    this.documentsSignes = documentsSignes;
  }

  public boolean isEnvoiDemandeSecondPaiement() {
    return envoiDemandeSecondPaiement;
  }

  public void setEnvoiDemandeSecondPaiement(boolean envoiDemandeSecondPaiement) {
    this.envoiDemandeSecondPaiement = envoiDemandeSecondPaiement;
  }

  public int getMobilite() {
    return mobilite;
  }

  public void setMobilite(int mobilite) {
    this.mobilite = mobilite;
  }

  public String getRaisonAnnulationEtudiant() {
    return raisonAnnulationEtudiant;
  }

  public void setRaisonAnnulationEtudiant(String raisonAnnulationEtudiant) {
    this.raisonAnnulationEtudiant = raisonAnnulationEtudiant;
  }

  public String getRaisonAnnulationProfesseur() {
    return raisonAnnulationProfesseur;
  }

  public void setRaisonAnnulationProfesseur(String raisonAnnulationProfesseur) {
    this.raisonAnnulationProfesseur = raisonAnnulationProfesseur;
  }

  public boolean isContratBourse() {
    return contratBourse;
  }

  public void setContratBourse(boolean contratBourse) {
    this.contratBourse = contratBourse;
  }

  public boolean isConventionStage() {
    return conventionStage;
  }

  public void setConventionStage(boolean conventionStage) {
    this.conventionStage = conventionStage;
  }

  public boolean isConventionEtude() {
    return conventionEtude;
  }

  public void setConventionEtude(boolean conventionEtude) {
    this.conventionEtude = conventionEtude;
  }

  public boolean isCharteEtudiant() {
    return charteEtudiant;
  }

  public void setCharteEtudiant(boolean charteEtudiant) {
    this.charteEtudiant = charteEtudiant;
  }

  public boolean isDocumentEngagement() {
    return documentEngagement;
  }

  public void setDocumentEngagement(boolean documentEngagement) {
    this.documentEngagement = documentEngagement;
  }

  public boolean isDemandePremierPaiement() {
    return demandePremierPaiement;
  }

  public void setDemandePremierPaiement(boolean demandePremierPaiement) {
    this.demandePremierPaiement = demandePremierPaiement;
  }

  public boolean isPremierPaiement() {
    return premierPaiement;
  }

  public void setPremierPaiement(boolean premierPaiement) {
    this.premierPaiement = premierPaiement;
  }

  public boolean isInfoEtudiant() {
    return infoEtudiant;
  }

  public void setInfoEtudiant(boolean infoEtudiant) {
    this.infoEtudiant = infoEtudiant;
  }

  public boolean isInfoPartenaire() {
    return infoPartenaire;
  }

  public void setInfoPartenaire(boolean infoPartenaire) {
    this.infoPartenaire = infoPartenaire;
  }

  public boolean isAttestationSejour() {
    return attestationSejour;
  }

  public void setAttestationSejour(boolean attestationSejour) {
    this.attestationSejour = attestationSejour;
  }

  public boolean isReleveNotes() {
    return releveNotes;
  }

  public void setReleveNotes(boolean releveNotes) {
    this.releveNotes = releveNotes;
  }

  public boolean isCertificatStage() {
    return certificatStage;
  }

  public void setCertificatStage(boolean certificatStage) {
    this.certificatStage = certificatStage;
  }

  public boolean isRapportFinal() {
    return rapportFinal;
  }

  public void setRapportFinal(boolean rapportFinal) {
    this.rapportFinal = rapportFinal;
  }

  public boolean isDemandeSecondPaiement() {
    return demandeSecondPaiement;
  }

  public void setDemandeSecondPaiement(boolean demandeSecondPaiement) {
    this.demandeSecondPaiement = demandeSecondPaiement;
  }

  public boolean isSecondPaiement() {
    return secondPaiement;
  }

  public void setSecondPaiement(boolean secondPaiement) {
    this.secondPaiement = secondPaiement;
  }

  public boolean isPartiEnMobilite() {
    return this.partiEnMobilite;
  }

  public void setPartiEnMobilite(boolean partiEnMobilite) {
    this.partiEnMobilite = partiEnMobilite;
  }
}
