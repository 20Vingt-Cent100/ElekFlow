package ca.qc.bdeb.sim.elekflow.UI.Utils;

import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;
import javafx.event.Event;

public interface Behavior {
    void execute(Event event, VueComposantElectrique vue);
}
