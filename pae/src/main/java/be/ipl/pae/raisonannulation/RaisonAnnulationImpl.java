package be.ipl.pae.raisonannulation;

import java.util.HashSet;
import java.util.Set;

public class RaisonAnnulationImpl implements RaisonAnnulation {

  private Set<String> raisons = new HashSet<String>();

  public RaisonAnnulationImpl(Set<String> raisons) {
    this.raisons = raisons;
  }

  public Set<String> getRaisons() {
    return this.raisons;
  }

  public void setRaisons(Set<String> raisons) {
    this.raisons = raisons;
  }

  public boolean addRaison(String raison) { // ou insert db?
    return this.raisons.add(raison);
  }

  public boolean removeRaison(String raison) { // ou delete db?
    return this.raisons.remove(raison);
  }
}
