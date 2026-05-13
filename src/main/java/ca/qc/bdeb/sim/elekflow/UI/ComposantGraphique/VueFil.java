package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.Vec2;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;


public class VueFil extends Region{
    private final ArrayList<VueBorne> listBorne;
    Polyline fil = new Polyline();

    private int elbowOrientation;

    public VueFil(double startX, double startY, VueBorne borne){
        this.getStyleClass().add("vue-fil");
        fil.getStyleClass().addAll("fil", "cursor");
        listBorne = new ArrayList<>();
        listBorne.addFirst(borne);

        borne.getParent().localToParentTransformProperty().addListener((o, oldV, newV) ->{updateInitialPosition();});

        this.setFocusTraversable(true);
        this.setPickOnBounds(false);

        fil.getPoints().addAll(startX, startY, startX, startY, startX, startY);

        fil.setFocusTraversable(true);
        fil.setPickOnBounds(false);

        setHandles();
        this.getChildren().add(fil);
    }

    public void changeEndPoint(double endX, double endY) {
        Point2D newEndPoint = getParent().sceneToLocal(endX, endY);

        double startX = fil.getPoints().get(0);
        double startY = fil.getPoints().get(1);
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
            fil.getPoints().set(2, targetEndX);
            fil.getPoints().set(3, startY);
        } else {
            // Path: Start -> Vertical Elbow -> End
            fil.getPoints().set(2, startX);
            fil.getPoints().set(3, targetEndY);
        }

        fil.getPoints().set(4, targetEndX);
        fil.getPoints().set(5, targetEndY);
    }

    private void updateInitialPosition() {
        Point2D startPos = getGlobalBornePos(listBorne.getFirst());
        fil.getPoints().set(0, startPos.getX());
        fil.getPoints().set(1, startPos.getY());

        syncElbow(); // Call a shared method to fix the middle point
    }

    public void updateEndPosition() {
        Point2D endPos = getGlobalBornePos(listBorne.getLast());
        fil.getPoints().set(4, endPos.getX());
        fil.getPoints().set(5, endPos.getY());

        syncElbow(); // Call a shared method to fix the middle point
    }

    private void syncElbow() {
        // We need both points to define the elbow
        double startX = fil.getPoints().get(0);
        double startY = fil.getPoints().get(1);
        double endX = fil.getPoints().get(4);
        double endY = fil.getPoints().get(5);

        if (elbowOrientation == 0) {
            // Mode: Horizontal then Vertical
            // Elbow X comes from End, Elbow Y comes from Start
            fil.getPoints().set(2, endX);
            fil.getPoints().set(3, startY);
        } else {
            // Mode: Vertical then Horizontal
            // Elbow X comes from Start, Elbow Y comes from End
            fil.getPoints().set(2, startX);
            fil.getPoints().set(3, endY);
        }
    }

    // Helper to keep code clean
    private Point2D getGlobalBornePos(VueBorne b) {
        return b.getParent().localToParent(b.localToParent(b.getCenterX(), b.getCenterY()));
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
            case DELETE -> fireEvent(new WireEvent(WireEvent.DELETE_WIRE, this, 0, 0));
        }
    }

    private void handleOnMouseDragExited(MouseDragEvent event) {
    }

    private void handleOnMouseDragReleased(MouseDragEvent event) {
    }

    private void handleOnMouseExited(MouseEvent event) {
        listBorne.forEach((b) -> b.hide(false));
    }

    private void handleOnMouseReleased(MouseEvent event) {
    }

    private void handleOnMousePressed(MouseEvent event) {
        this.requestFocus();
        event.consume();
    }

    private void handleOnZoom(ZoomEvent event) {
    }

    private void handleOnScroll(ScrollEvent event) {
    }

    private void handleOnMouseClicked(MouseEvent event) {
        this.requestFocus();
        event.consume();

        Point2D generalCoord = this.getParent().sceneToLocal(event.getSceneX(), event.getSceneY());

        listBorne.addLast(new VueBorne(generalCoord.getX(), generalCoord.getY()));

        this.getChildren().add(listBorne.getLast());
        listBorne.getLast().toFront();
    }

    private void handleOnMouseEntered(MouseEvent event) {
        listBorne.forEach((b) -> b.show(false));
    }

    private void handleOnMouseDragged(MouseEvent event) {
        event.consume();
    }

    public void addEndNode(VueBorne borne, boolean isLinked){
        listBorne.addLast(borne);

        if (isLinked) borne.getParent().localToParentTransformProperty().addListener((o, oldV, newV) -> updateEndPosition());
        else{
            this.getChildren().add(borne);
            borne.addToAll();
            borne.toFront();
        }
    }
}
