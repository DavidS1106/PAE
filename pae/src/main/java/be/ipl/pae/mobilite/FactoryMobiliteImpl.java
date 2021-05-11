package be.ipl.pae.mobilite;

public class FactoryMobiliteImpl implements FactoryMobilite {

  public MobiliteDto creerMobilite() {
    MobiliteDto mobilite = new MobiliteImpl();
    return mobilite;
  }
}
