package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.ComposantElectrique;
import ca.qc.bdeb.sim.elekflow.Logique.Resistance;
import org.girod.javafx.svgimage.SVGImage;

public class VueResistance extends VueComposantElectrique{

    public VueResistance() {
        super(App.atlas.getSVG("Resistance"), new Resistance());
    }
}
