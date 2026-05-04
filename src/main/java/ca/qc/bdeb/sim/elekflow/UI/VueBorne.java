package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.math.BigDecimal;

public class VueBorne extends Circle {

    public VueBorne(BigDecimal[] coordinate, double[] parentSize){
        setRadius(5);
        this.setLayoutX(coordinate[0].doubleValue() * parentSize[0]);
        this.setLayoutY(coordinate[1].doubleValue() * parentSize[1]);
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
    }

    private void handleOnMousePressed(MouseEvent event) {
        event.consume();

        fireEvent(new WireEvent(WireEvent.CREATE_WIRE, this.centerXProperty(), this.centerYProperty()));
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
    }
}
