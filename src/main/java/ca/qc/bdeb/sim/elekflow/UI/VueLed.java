package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Led;

public class VueLed extends VueComposantElectrique{

    public VueLed() {
        super(App.atlas.getSVG("LED"), new Led(5));
        this.getStyleClass().add("composant-fill");
    }
}
