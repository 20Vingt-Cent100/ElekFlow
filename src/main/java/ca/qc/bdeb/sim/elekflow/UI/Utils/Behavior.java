package ca.qc.bdeb.sim.elekflow.UI.Utils;

import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;
import javafx.event.Event;

public interface Behavior {
    void asCurrent(Event event, VueComposantElectrique vue);
    void asNoCurrent(Event event, VueComposantElectrique vue);
}
