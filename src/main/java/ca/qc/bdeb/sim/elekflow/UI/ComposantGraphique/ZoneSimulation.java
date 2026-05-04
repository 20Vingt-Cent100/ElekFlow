package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ExportEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.girod.javafx.svgimage.tosvg.SVGConverter;

import java.io.File;
import java.io.IOException;

public class ZoneSimulation extends Pane {
    private Group simultionGroup = new Group();
    private final Pane pane = new Pane();
    private final SVGConverter converter = new SVGConverter();

    private Point2D lastClick;

    public ZoneSimulation(){
        this.getChildren().add(pane);
        pane.getChildren().add(simultionGroup);

        simultionGroup.setManaged(false);
        simultionGroup.setAutoSizeChildren(false);

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
        addEventHandler(ComponentEvent.DELETE_COMPONENT, this::handleDeleteComponent);
        addEventHandler(WireEvent.CREATE_WIRE, this::handleCreateWire);
        addEventHandler(WireEvent.MOVE_END_POINT, this::handleWireMoveEndPoint);
    }

    public void exportToSVG(File file){
        try {
            converter.convert(pane, file.toURI().toURL());
            Loggeur.logConsole("Svg exporter", NiveauLog.TOTAL);
        }catch (IOException e){
            Loggeur.logConsole(e.getMessage(), NiveauLog.ERREUR);
        }

    }

    private void handleWireMoveEndPoint(WireEvent event){
        var fil = new Line();
        fil.startXProperty().bind(event.getStartXproperty());
        fil.startYProperty().bind(event.getStartYproperty());

        fil.setEndX(event.getSceneX());
        fil.setEndY(event.getSceneY());

        simultionGroup.getChildren().add(new Line());
    }

    private void handleCreateWire(WireEvent event){
        var fil = new Line();
        fil.startXProperty().bind(event.getStartXproperty());
        fil.startYProperty().bind(event.getStartYproperty());

        simultionGroup.getChildren().add(new Line());
    }

    private void handleDeleteComponent(ComponentEvent event){
        this.simultionGroup.getChildren().remove(event.getComposantElectrique());
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

    //TODO: Zoomer vers le curseur
    private void handleOnScroll(ScrollEvent event) {
        if (event.isControlDown()){

            pane.setTranslateX(this.getTranslateX());
            pane.setTranslateY(this.getTranslateY());

            pane.setScaleX(Math.max(pane.getScaleX() + event.getDeltaY()/100., 0.2));
            pane.setScaleY(Math.max(pane.getScaleY() + event.getDeltaY()/100., 0.2));
        }
    }

    private void handleOnMouseClicked(MouseEvent mouseEvent) {
    }

    private void handleOnMouseEntered(MouseEvent mouseEvent) {
    }

    private void handleOnMouseDragged(MouseEvent mouseEvent) {
        simultionGroup.setTranslateX(simultionGroup.getTranslateX() + (mouseEvent.getX() - lastClick.getX()) / pane.getScaleX());
        simultionGroup.setTranslateY(simultionGroup.getTranslateY() + (mouseEvent.getY() - lastClick.getY()) / pane.getScaleY());

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
