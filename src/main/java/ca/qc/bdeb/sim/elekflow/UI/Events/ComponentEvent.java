package ca.qc.bdeb.sim.elekflow.UI.Events;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public class ComponentEvent extends Event {
    public static final EventType<ComponentEvent> CREATE_NEW_COMPONENT = new EventType<>(Event.ANY, "CREATE_NEW_COMPONENT");
    public static final EventType<ComponentEvent> BUTTON_DRAGGED = new EventType<>(Event.ANY, "BUTTON_DRAGGED");
    public static final EventType<ComponentEvent> PLACED = new EventType<>(Event.ANY, "PLACED");
    public static final EventType<ComponentEvent> DELETE_GHOST_COMPONENT = new EventType<>(Event.ANY, "DELETE_GHOST_COMPONENT");
    public static final EventType<ComponentEvent> DELETE_COMPONENT = new EventType<>(Event.ANY, "DELETE_COMPONENT");
    public static final EventType<ComponentEvent> SHOW_DESCRIPTION = new EventType<>(Event.ANY, "SHOW_DESCRIPTION");


    private final VueComposantElectrique composantElectrique;
    private final MouseEvent mouseEvent;

    public ComponentEvent(EventType<? extends Event> eventType,
                          VueComposantElectrique composantElectrique,
                          MouseEvent mouseEvent)
    {
        super(eventType);

        this.composantElectrique = composantElectrique;
        this.mouseEvent = mouseEvent;
    }

    public VueComposantElectrique getComposantElectrique() {
        return composantElectrique;
    }

    public MouseEvent getMouseEvent(){
        return mouseEvent;
    }
}
