package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.Vec2;
import ca.qc.bdeb.sim.elekflow.proto.Point;
import ca.qc.bdeb.sim.elekflow.proto.Wire;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;


public class VueFil extends Region{
    private final ArrayList<VueBorne> listBorne;
    Polyline fil = new Polyline();

    private int elbowOrientation;

    private Wire.Builder wire;

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
        fil.setFill(null);

        setHandles();
        this.getChildren().add(fil);


        this.wire = Wire.newBuilder();
        this.wire.addPoints(Point.newBuilder().setX(startX).setY(startY).build());
        this.wire.addPoints(Point.newBuilder().setX(startX).setY(startY).build());
        this.wire.addPoints(Point.newBuilder().setX(startX).setY(startY).build());
    }

    public VueFil(Wire.Builder wire){
        this.wire = wire;
        listBorne = new ArrayList<>();
        this.getStyleClass().add("vue-fil");
        fil.getStyleClass().addAll("fil", "cursor");

        //vueBornes.getFirst().getParent().localToParentTransformProperty().addListener((o, oldV, newV) ->{updateInitialPosition();});
        this.setFocusTraversable(true);
        this.setPickOnBounds(false);

        fil.getPoints().setAll(this.wire.getPoints(0).getX(), this.wire.getPoints(0).getY(),
                this.wire.getPoints(1).getX(), this.wire.getPoints(1).getY(),
                this.wire.getPoints(2).getX(), this.wire.getPoints(2).getY()
                );

        fil.setFocusTraversable(true);
        fil.setPickOnBounds(false);
        fil.setFill(null);

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

        wire.setPoints(1, Point.newBuilder().setX(fil.getPoints().get(2)).setY(fil.getPoints().get(3)).build());
        wire.setPoints(2, Point.newBuilder().setX(fil.getPoints().get(4)).setY(fil.getPoints().get(5)).build());
    }

    private void updateInitialPosition() {
        Point2D startPos = getGlobalBornePos(listBorne.getFirst());
        fil.getPoints().set(0, startPos.getX());
        fil.getPoints().set(1, startPos.getY());

        wire.setPoints(0, Point.newBuilder().setX(fil.getPoints().get(0)).setY(fil.getPoints().get(1)).build());


        syncElbow(); // Call a shared method to fix the middle point

        wire.setPoints(1, Point.newBuilder().setX(fil.getPoints().get(2)).setY(fil.getPoints().get(3)).build());
    }

    public void updateEndPosition() {
        Point2D endPos = getGlobalBornePos(listBorne.getLast());
        fil.getPoints().set(4, endPos.getX());
        fil.getPoints().set(5, endPos.getY());

        wire.setPoints(2, Point.newBuilder().setX(fil.getPoints().get(4)).setY(fil.getPoints().get(5)).build());

        syncElbow(); // Call a shared method to fix the middle point

        wire.setPoints(1, Point.newBuilder().setX(fil.getPoints().get(2)).setY(fil.getPoints().get(3)).build());
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
            borne.addToAll((ZoneSimulation) this.getParent().getParent().getParent());
            borne.toFront();
        }
    }

    public Wire getWire() {
        return wire.build();
    }
}
