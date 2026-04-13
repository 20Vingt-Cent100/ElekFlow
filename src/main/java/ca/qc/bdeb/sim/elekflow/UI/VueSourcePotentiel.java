package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Resistance;

public class VueSourcePotentiel extends VueComposantElectrique{
    public VueSourcePotentiel() {
        super(App.atlas.getSVG("SourceCourantContinu"), new Resistance());
    }
}
