package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Resistance;

public class VueResistance extends VueComposantElectrique{

    public VueResistance() {
        super(App.atlas.getSVG("Resistance"), new Resistance());
    }
}
