package be.ipl.pae.raisonannulation;

public interface RaisonAnnulation extends RaisonAnnulationDto {

  boolean addRaison(String raison);

  boolean removeRaison(String raison);
}
