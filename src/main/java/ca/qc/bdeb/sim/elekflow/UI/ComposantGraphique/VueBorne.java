package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import ca.qc.bdeb.sim.elekflow.proto.Borne;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.shape.Circle;

import java.math.BigDecimal;

public class VueBorne extends Circle {
    private static int OverallIndex = 1;

    private final int index;
    private final int onLoadIndex;

    private VueFil vueFil;
    private VueBorne hovered;

    private boolean priority = false;

    public VueBorne(BigDecimal[] coordinate, double[] parentSize, int onLoadIndex){
        index = OverallIndex++;
        this.onLoadIndex = onLoadIndex;

        setRadius(4);
        this.setLayoutX(coordinate[0].doubleValue() * parentSize[0]);
        this.setLayoutY(coordinate[1].doubleValue() * parentSize[1]);
        this.setFocusTraversable(true);
        this.setPickOnBounds(true);

        this.getStyleClass().addAll("fill", "cursor");
        setHandles();
    }

    public VueBorne(double layoutX, double layoutY){
        index = OverallIndex++;

        this.onLoadIndex = 0;

        setRadius(4);
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
        this.setFocusTraversable(true);
        this.setPickOnBounds(true);

        this.getStyleClass().addAll("fill", "cursor");
        setHandles();
    }

    public void addToAll(ZoneSimulation simulation, boolean isLoaded){
        if(isLoaded)
            simulation.addBorne(this, getOnLoadIndex());
        else
            simulation.addBorne(this, getIndex());
    }

    public void hide(boolean isPriority){
        if(isPriority)
            priority = false;

        if(!priority) {
            this.setVisible(false);
            this.setManaged(false);
        }
    }

    public void show(boolean isPriority){
        if (isPriority)
            priority = true;

        this.setVisible(true);
        this.setManaged(true);
    }

    private void setHandles(){
        setOnMouseDragged(this::handleOnMouseDragged);
        setOnMouseEntered(this::handleOnMouseEntered);
        setOnMouseClicked(this::handleOnMouseClicked);
        setOnScroll(this::handleOnScroll);
        setOnZoom(this::handleOnZoom);
        setOnMousePressed(this::handleOnMousePressed);
        setOnMouseReleased(this::handleOnMouseReleased);
        setOnMouseExited(this::handleOnMouseExited);
        setOnMouseDragReleased(this::handleOnMouseDragReleased);
        setOnMouseDragExited(this::handleOnMouseDragExited);
        setOnKeyPressed(this::handleOnKeyPressed);
        addEventFilter(WireEvent.SHOW_NODE, (e) ->{show(true);});
        addEventFilter(WireEvent.HIDE_NODE, (e) ->{hide(true);});
    }

    private void handleOnKeyPressed(KeyEvent event) {
    }

    private void handleOnMouseDragExited(MouseDragEvent event) {
    }

    private void handleOnMouseDragReleased(MouseDragEvent event) {
    }

    private void handleOnMouseExited(MouseEvent event) {
    }

    private void handleOnMouseReleased(MouseEvent event) {
        if(hovered != null && hovered != this){
            vueFil.addEndNode(hovered, true);
        }else {
            Point2D pos = vueFil.getParent().sceneToLocal(event.getSceneX(), event.getSceneY());
            vueFil.addEndNode(new VueBorne(pos.getX(), pos.getY()), false);
        }

        vueFil = null;
        fireEvent(new WireEvent(WireEvent.HIDE_NODE, null, 0 ,0));
    }

    private void handleOnMousePressed(MouseEvent event) {
        event.consume();

        if (vueFil == null){
            Point2D center = this.getParent().localToParent(this.localToParent(getCenterX(), getCenterY()));

            vueFil = new VueFil(center.getX(), center.getY(), this);

            fireEvent(new WireEvent(WireEvent.CREATE_WIRE, vueFil, 0, 0));
            fireEvent(new WireEvent(WireEvent.SHOW_NODE, null, 0, 0));
        }
    }

    private void handleOnZoom(ZoomEvent event) {
    }

    private void handleOnScroll(ScrollEvent event) {
    }

    private void handleOnMouseClicked(MouseEvent event) {
        event.consume();
    }

    private void handleOnMouseEntered(MouseEvent event) {
    }

    private void handleOnMouseDragged(MouseEvent event) {
        event.consume();
        //vueFil.changeEndPoint(event.getSceneX(), event.getSceneY());

        double mx = event.getSceneX();
        double my = event.getSceneY();

        hovered = ((ZoneSimulation)vueFil.getParent().getParent().getParent()).findHoveredNode(mx, my);

        if (hovered != null) {
            // Snap the visual wire to the EXACT center of the hovered node
            Point2D snapPt = hovered.localToScene(hovered.getCenterX(), hovered.getCenterY());

            vueFil.changeEndPoint(snapPt.getX(), snapPt.getY());
        } else {
            vueFil.changeEndPoint(event.getSceneX(), event.getSceneY());
        }
    }

    public Borne getBorne(){
        return Borne.newBuilder().setIndex(index).build();
    }

    public int getIndex() {
        return index;
    }

    public long getOnLoadIndex() {
        return onLoadIndex;
    }
}
