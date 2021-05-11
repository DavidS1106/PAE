package be.ipl.pae.mobilite;

import java.time.LocalDate;

public interface MobiliteDto {

  MobiliteImpl deserialiseur(String json);

  String getMail();

  int getId_mobilite();

  void setId_mobilite(int id_mobilite);

  int getNumOrdre();

  String getTypeDeMobilite();

  String getCode();

  String getNomPartenaire();

  int getQuadri();

  void setMail(String mail);

  String getNom();

  String getPrenom();

  void setNom(String nom);

  void setPrenom(String prenom);


  void setNumOrdre(int numOrdre);

  void setTypeDeMobilite(String typeDeMobilite);

  void setCode(String code);

  void setNomPartenaire(String nomPartenaire);

  void setQuadri(int quadri);

  void setPays(String pays);

  String getPays();

  LocalDate getDateIntro();

  void setDateIntro(LocalDate dateIntro);

  void setAnneeAcademique(String anneeAcademique);

  String getAnneeAcademique();

  public int getVersion();

  public void setVersion(int version);
}
