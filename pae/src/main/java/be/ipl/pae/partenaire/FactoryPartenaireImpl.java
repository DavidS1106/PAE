package be.ipl.pae.partenaire;

public class FactoryPartenaireImpl implements FactoryPartenaire {

  public PartenaireDto creerPartenaire() {
    PartenaireDto partenaire = new PartenaireImpl();
    return partenaire;
  }
}
