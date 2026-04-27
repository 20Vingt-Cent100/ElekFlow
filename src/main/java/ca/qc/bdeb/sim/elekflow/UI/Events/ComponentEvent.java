package ca.qc.bdeb.sim.elekflow.UI.Events;

import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.ComposantElectrique;
import javafx.event.Event;
import javafx.event.EventType;

public class ComponentEvent extends Event {
    public static final EventType<ComponentEvent> CREATE_NEW_COMPONENT = new EventType<>(Event.ANY, "CREATE_NEW_COMPONENT");
    public static final EventType<ComponentEvent> DELETE_COMPONENT = new EventType<>(Event.ANY, "DELETE_COMPONENT");

    private final ComposantElectrique composantElectrique;
    private final double sceneX;
    private final double sceneY;
    private final double dx;
    private final double dy;

    public ComponentEvent(EventType<? extends Event> eventType,
                          ComposantElectrique composantElectrique,
                          double sceneX, double sceneY, double dx, double dy)
    {
        super(eventType);

        this.composantElectrique = composantElectrique;
        this.sceneX = sceneX;
        this.sceneY = sceneY;
        this.dx = dx;
        this.dy = dy;
    }

    public ComposantElectrique getComposantElectrique() {
        return composantElectrique;
    }

    public double getSceneX() {
        return sceneX;
    }

    public double getSceneY() {
        return sceneY;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}
