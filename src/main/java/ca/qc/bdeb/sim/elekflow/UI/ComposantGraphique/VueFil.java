package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.Vec2;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;


public class VueFil extends Polyline{
    private final int nodeIndex;

    private final ArrayList<VueBorne> listBorne;

    private int elbowOrientation;

    public VueFil(double startX, double startY, int nodeIndex, VueBorne borne){
        this.getStyleClass().addAll("fil", "cursor");
        listBorne = new ArrayList<>();
        listBorne.addFirst(borne);

        borne.getParent().localToParentTransformProperty().addListener((o, oldV, newV) ->{updateInitialPosition();});

        this.setFocusTraversable(true);

        this.nodeIndex = nodeIndex;

        this.getPoints().addAll(startX, startY, startX, startY, startX, startY);

        setHandles();
    }

    public void changeEndPoint(double endX, double endY) {
        Point2D newEndPoint = getParent().sceneToLocal(endX, endY);

        double startX = this.getPoints().get(0);
        double startY = this.getPoints().get(1);
        double curEndX = newEndPoint.getX();
        double curEndY = newEndPoint.getY();

        double absDeltaX = Math.abs(curEndX - startX);
        double absDeltaY = Math.abs(curEndY - startY);

        if (absDeltaX < 50 && absDeltaY < 50){
            elbowOrientation = absDeltaX > absDeltaY ? 0 : 1;
        }

        // 2. Apply deadzone to final points (preventing micro-jitters)
        double targetEndX = (absDeltaX > 20) ? curEndX : startX;
        double targetEndY = (absDeltaY > 20) ? curEndY : startY;

        // 3. Set Points based on the Sticky Priority
        if (elbowOrientation == 0) {
            // Path: Start -> Horizontal Elbow -> End
            this.getPoints().set(2, targetEndX);
            this.getPoints().set(3, startY);
        } else {
            // Path: Start -> Vertical Elbow -> End
            this.getPoints().set(2, startX);
            this.getPoints().set(3, targetEndY);
        }

        this.getPoints().set(4, targetEndX);
        this.getPoints().set(5, targetEndY);
    }

    private void updateInitialPosition(){
        var borne = listBorne.getFirst();

        double centerX = borne.getCenterX();
        double centerY = borne.getCenterY();

        Point2D posGlobal = borne.getParent().localToParent(borne.localToParent(centerX, centerY));

        this.getPoints().set(0, posGlobal.getX());
        this.getPoints().set(1, posGlobal.getY());

        if (elbowOrientation != 0){
            this.getPoints().set(2, posGlobal.getX());
        }
        else {
            this.getPoints().set(3, posGlobal.getY());
        }
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
        switch (event.getCode()){
            case DELETE -> fireEvent(new WireEvent(WireEvent.DELETE_WIRE, this, null));
        }
    }

    private void handleOnMouseDragExited(MouseDragEvent event) {
    }

    private void handleOnMouseDragReleased(MouseDragEvent event) {
    }

    private void handleOnMouseExited(MouseEvent event) {
        listBorne.forEach((borne) -> {borne.hide();});
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
        this.requestFocus();
        event.consume();
    }

    private void handleOnMouseEntered(MouseEvent event) {
        listBorne.forEach((borne) -> {borne.show();});
    }

    private void handleOnMouseDragged(MouseEvent event) {
    }
}
