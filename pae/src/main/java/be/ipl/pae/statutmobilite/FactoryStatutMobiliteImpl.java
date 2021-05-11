package be.ipl.pae.statutmobilite;

public class FactoryStatutMobiliteImpl implements FactoryStatutMobilite {


  public StatutMobiliteDto creerStatutMobilite() {
    StatutMobiliteDto statutMobilite = new StatutMobiliteImpl();
    return statutMobilite;
  }
}
