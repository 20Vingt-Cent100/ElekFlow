package ca.qc.bdeb.sim.elekflow.UI;

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

        this.getStyleClass().add("fill");
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
    }

    private void handleOnZoom(ZoomEvent event) {
    }

    private void handleOnScroll(ScrollEvent event) {
    }

    private void handleOnMouseClicked(MouseEvent event) {
    }

    private void handleOnMouseEntered(MouseEvent event) {
    }

    private void handleOnMouseDragged(MouseEvent event) {
    }
}
