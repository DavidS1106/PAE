package be.ipl.pae.etudiant;

public class FactoryEtudiantImpl implements FactoryEtudiant {

  public EtudiantDto creerEtudiant() {
    EtudiantDto etudiant = new EtudiantImpl();
    return etudiant;
  }

}
