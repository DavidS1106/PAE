package be.ipl.pae.mobilite;

import java.time.LocalDate;
import com.owlike.genson.Genson;

public class MobiliteImpl implements Mobilite {
  private String nom;
  private String prenom;
  private String mail;
  private int numOrdre;
  private String anneeAcademique;
  private String typeDeMobilite;
  private String code;
  private String nomPartenaire;
  private String pays;
  private int quadri;
  private LocalDate dateIntro;
  private int id_mobilite;
  private int version;

  public MobiliteImpl() {}

  public MobiliteImpl(String mail, int numOrdre, String typeDeMobilite, String code,
      String nomPartenaire, int quadri, String nom, String prenom, int id_mobilite, int version) {
    super();
    this.mail = mail;
    this.numOrdre = numOrdre;
    this.typeDeMobilite = typeDeMobilite;
    this.code = code;
    this.nomPartenaire = nomPartenaire;
    this.quadri = quadri;
    this.id_mobilite = id_mobilite;
    this.version = version;
  }

  public MobiliteImpl deserialiseur(String json) {
    return new Genson().deserialize(json, MobiliteImpl.class);
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public int getId_mobilite() {
    return id_mobilite;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public void setId_mobilite(int id_mobilite) {
    this.id_mobilite = id_mobilite;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public void setNumOrdre(int numOrdre) {
    this.numOrdre = numOrdre;
  }

  public void setTypeDeMobilite(String typeDeMobilite) {
    this.typeDeMobilite = typeDeMobilite;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setNomPartenaire(String nomPartenaire) {
    this.nomPartenaire = nomPartenaire;
  }

  public void setQuadri(int quadri) {
    this.quadri = quadri;
  }



  public void setPays(String pays) {
    this.pays = pays;
  }

  public String getPays() {
    return pays;
  }

  public String getMail() {
    return mail;
  }

  public LocalDate getDateIntro() {
    return dateIntro;
  }

  public void setDateIntro(LocalDate dateIntro) {
    this.dateIntro = dateIntro;
  }

  public int getNumOrdre() {
    return numOrdre;
  }

  public String getTypeDeMobilite() {
    return typeDeMobilite;
  }

  public String getCode() {
    return code;
  }

  public String getNomPartenaire() {
    return nomPartenaire;
  }

  public int getQuadri() {
    return quadri;
  }

  public String getAnneeAcademique() {
    return anneeAcademique;
  }

  public void setAnneeAcademique(String anneeAcademique) {
    this.anneeAcademique = anneeAcademique;
  }

  @Override
  public String toString() {
    return "MobiliteImpl [nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", numOrdre="
        + numOrdre + ", anneeAcademique=" + anneeAcademique + ", typeDeMobilite=" + typeDeMobilite
        + ", code=" + code + ", nomPartenaire=" + nomPartenaire + ", pays=" + pays + ", quadri="
        + quadri + ", dateIntro=" + dateIntro + "]";
  }
}
