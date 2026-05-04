package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class ZoneSimulation extends Pane {
    private Group simultionGroup = new Group();
    private Point2D lastClick;

    public ZoneSimulation(){
        this.getChildren().add(simultionGroup);
        simultionGroup.setManaged(false);

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(this.widthProperty());
        clip.heightProperty().bind(this.heightProperty());

        this.setClip(clip);

        setHandles();
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

    private void handleOnKeyPressed(KeyEvent keyEvent) {
    }

    private void handleOnMouseDragExited(MouseDragEvent mouseDragEvent) {
    }

    private void handleOnMouseDragReleased(MouseDragEvent mouseDragEvent) {
    }

    private void handleOnMouseExited(MouseEvent mouseEvent) {
    }

    private void handleOnMouseReleased(MouseEvent mouseEvent) {
    }

    private void handleOnMousePressed(MouseEvent mouseEvent) {
        lastClick = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    private void handleOnZoom(ZoomEvent zoomEvent) {
    }

    private void handleOnScroll(ScrollEvent event) {
        if (event.isControlDown()){
            simultionGroup.setScaleX(Math.abs(simultionGroup.getScaleX() + event.getDeltaY()/200.));
            simultionGroup.setScaleY(Math.abs(simultionGroup.getScaleY() + event.getDeltaY()/200.));
        }
    }

    private void handleOnMouseClicked(MouseEvent mouseEvent) {
    }

    private void handleOnMouseEntered(MouseEvent mouseEvent) {
    }

    private void handleOnMouseDragged(MouseEvent mouseEvent) {
        simultionGroup.setTranslateX(simultionGroup.getTranslateX() + mouseEvent.getX() - lastClick.getX());
        simultionGroup.setTranslateY(simultionGroup.getTranslateY() + mouseEvent.getY() - lastClick.getY());

        lastClick = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    public void addComponent(VueComposantElectrique composantElectrique, MouseEvent event){
        composantElectrique.setTranslateX(0);
        composantElectrique.setTranslateY(0);

        Point2D mousePosition = simultionGroup.sceneToLocal(event.getSceneX(), event.getSceneY());

        simultionGroup.getChildren().add(composantElectrique);
        composantElectrique.setLayoutX(mousePosition.getX() - composantElectrique.getCenterX());
        composantElectrique.setLayoutY(mousePosition.getY() - composantElectrique.getCenterY());
    }

    public void moveComponent(VueComposantElectrique composantElectrique, MouseEvent event){

    }

    public Point2D getGroupScale(){
        return new Point2D(simultionGroup.getScaleX(), simultionGroup.getScaleY());
    }
}
