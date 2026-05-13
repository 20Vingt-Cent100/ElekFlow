package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.girod.javafx.svgimage.tosvg.ConverterParameters;
import org.girod.javafx.svgimage.tosvg.SVGConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class ZoneSimulation extends Pane {

    private final Group simultionGroup = new Group();

    private final Pane pane = new Pane();

    private final SVGConverter converter = new SVGConverter();

    private Point2D lastClick = new Point2D(0,0);

    private ArrayList<VueBorne> listeBorne = new ArrayList<>();

    /**
     * Extension de JavaFX pane qui affiche les composants électriques affichés à l'écran
     */

    public ZoneSimulation() {
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

    /**
     * Ajoute des gestionnaires d'évènements pour divers évènements
     */

    private void setHandles() {
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
        addEventHandler(WireEvent.DELETE_WIRE, this::handleDeleteWire);
        addEventHandler(WireEvent.SHOW_NODE, this::handleShowNode);
        addEventHandler(WireEvent.HIDE_NODE, this::handleHideNode);
    }


    /**
     * Export l'ensemble des éléments constituants la zone de simulation vers une fichier svg
     * @param file destination et non du fichier ou enregistrer le svg
     */

    public void exportToSVG(File file) {
        try {
            converter.convert(this, file.toURI().toURL());
            Loggeur.logConsole("Svg exporter", NiveauLog.TOTAL);
        } catch (IOException e) {
            Loggeur.logConsole(e.getMessage(), NiveauLog.ERREUR);
        }

    }

    private void handleShowNode(WireEvent wireEvent) {
        listeBorne.forEach((b) -> b.show(true));
    }

    private void handleHideNode(WireEvent wireEvent) {
        listeBorne.forEach((b) -> b.hide(true));
    }

    private void handleCreateWire(WireEvent event) {
        simultionGroup.getChildren().add(event.getFil());
    }

    private void handleDeleteComponent(ComponentEvent event) {
        this.simultionGroup.getChildren().remove(event.getComposantElectrique());
    }

    private void handleDeleteWire(WireEvent event) {
        this.simultionGroup.getChildren().remove(event.getFil());
    }

    private void handleOnKeyPressed(KeyEvent keyEvent) {}

    private void handleOnMouseDragExited(MouseDragEvent mouseDragEvent) {}

    private void handleOnMouseDragReleased(MouseDragEvent mouseDragEvent) {}

    private void handleOnMouseExited(MouseEvent mouseEvent) {}

    private void handleOnMouseReleased(MouseEvent mouseEvent) {}

    private void handleOnMousePressed(MouseEvent mouseEvent) {
        lastClick = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    private void handleOnZoom(ZoomEvent zoomEvent) {}

    //TODO: Zoomer vers le curseur
    private void handleOnScroll(ScrollEvent event) {
        if (event.isControlDown()) {

            pane.setTranslateX(this.getTranslateX());
            pane.setTranslateY(this.getTranslateY());

            pane.setScaleX(Math.max(pane.getScaleX() + event.getDeltaY() / 100., 0.2));
            pane.setScaleY(Math.max(pane.getScaleY() + event.getDeltaY() / 100., 0.2));
        }
    }

    private void handleOnMouseClicked(MouseEvent mouseEvent) {
        fireEvent(new ShowInfoEvent(ShowInfoEvent.HIDE_INFO, null, null));
    }

    private void handleOnMouseEntered(MouseEvent mouseEvent) {}

    private void handleOnMouseDragged(MouseEvent mouseEvent) {
        simultionGroup.setTranslateX(simultionGroup.getTranslateX() + (mouseEvent.getX() - lastClick.getX()) / pane.getScaleX());
        simultionGroup.setTranslateY(simultionGroup.getTranslateY() + (mouseEvent.getY() - lastClick.getY()) / pane.getScaleY());

        lastClick = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    public void addComponent(VueComposantElectrique composantElectrique, MouseEvent event) {
        composantElectrique.setTranslateX(0);
        composantElectrique.setTranslateY(0);

        Point2D mousePosition = simultionGroup.sceneToLocal(event.getSceneX(), event.getSceneY());

        simultionGroup.getChildren().add(composantElectrique);
        composantElectrique.setLayoutX(mousePosition.getX() - composantElectrique.getCenterX());
        composantElectrique.setLayoutY(mousePosition.getY() - composantElectrique.getCenterY());
    }

    public VueBorne findHoveredNode(double sceneX, double sceneY) {
        for (VueBorne node : listeBorne) {
            // Convert the node's bounds to Scene coordinates for a fair comparison
            Bounds bounds = node.localToScene(node.getBoundsInLocal());

            if (bounds.contains(sceneX, sceneY)) {
                return node; // We found the node the mouse is over
            }
        }
        return null;
    }

    public void addBorne(VueBorne bornes){
        listeBorne.add(bornes);
    }
}