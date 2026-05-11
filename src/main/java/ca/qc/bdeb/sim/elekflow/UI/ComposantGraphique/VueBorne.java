package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.shape.Circle;

import java.math.BigDecimal;

public class VueBorne extends Circle {
    private static int nodeIndex = 0;

    private VueFil vueFil;
    private final int index;

    public VueBorne(BigDecimal[] coordinate, double[] parentSize){
        index = nodeIndex ++;
        setRadius(4);
        this.setLayoutX(coordinate[0].doubleValue() * parentSize[0]);
        this.setLayoutY(coordinate[1].doubleValue() * parentSize[1]);
        this.setFocusTraversable(true);
        this.setPickOnBounds(true);

        this.getStyleClass().add("fill");
        setHandles();
    }

    public VueBorne(double layoutX, double layoutY){
        index = nodeIndex ++;

        setRadius(4);
        this.setLayoutX(layoutX);
        this.setLayoutY(layoutY);
        this.setFocusTraversable(true);
        this.setPickOnBounds(true);

        this.getStyleClass().add("fill");
        setHandles();
    }

    public void hide(){
        this.setVisible(false);
        this.setManaged(false);
    }

    public void show(){
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
        addEventFilter(WireEvent.SHOW_NODE, (e) ->{show();});
        addEventFilter(WireEvent.HIDE_NODE, (e) ->{hide();});
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
        vueFil = null;
    }

    private void handleOnMousePressed(MouseEvent event) {
        event.consume();

        if (vueFil == null){
            Point2D center = this.getParent().localToParent(this.localToParent(getCenterX(), getCenterY()));

            vueFil = new VueFil(center.getX(), center.getY(), index, this);

            fireEvent(new WireEvent(WireEvent.CREATE_WIRE, vueFil, null));
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
        vueFil.changeEndPoint(event.getSceneX(), event.getSceneY());
    }
}
