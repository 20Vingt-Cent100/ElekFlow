package ca.qc.bdeb.sim.elekflow.UI.Events;

import ca.qc.bdeb.sim.elekflow.Logique.ComposantElectrique;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.ComposantElectriqueGraphique;
import javafx.event.Event;
import javafx.event.EventType;

public class ShowInfoEvent extends Event {
    public static final EventType<ShowInfoEvent> SHOW_INFO = new EventType<>(Event.ANY, "SHOW_INFO");
    public static final EventType<ShowInfoEvent> HIDE_INFO = new EventType<>(Event.ANY, "HIDE_INFO");

    public final ComposantElectriqueGraphique compElecGraph;
    public final ComposantElectrique compElec;

    public ShowInfoEvent(EventType<? extends Event> eventType, ComposantElectrique compElec, ComposantElectriqueGraphique compElecGraph) {
        super(eventType);
        this.compElecGraph = compElecGraph;
        this.compElec = compElec;
    }
}
