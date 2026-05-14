package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.WireEvent;
import ca.qc.bdeb.sim.elekflow.UI.Scene.SimulationScene;
import ca.qc.bdeb.sim.elekflow.proto.Circuit;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.girod.javafx.svgimage.SVGContent;
import org.girod.javafx.svgimage.tosvg.SVGConverter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;


public class ZoneSimulation extends Pane {

    private Circuit.Builder circuit;

    private final Group simultionGroup = new Group();

    private final Pane pane = new Pane();

    private final SVGConverter converter = new SVGConverter();

    private Point2D lastClick = new Point2D(0,0);

    private final HashMap<Long, VueBorne> listeBorne = new HashMap<>();

    /**
     * Extension de JavaFX pane qui affiche les composants électriques affichés à l'écran
     */

    public ZoneSimulation() {
        circuit = Circuit.newBuilder();
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
        listeBorne.forEach((k, b) -> b.show(true));
    }

    private void handleHideNode(WireEvent wireEvent) {
        listeBorne.forEach((k, b) -> b.hide(true));
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
        AtomicReference<VueBorne> hoveredBorne = new AtomicReference<>();

        listeBorne.forEach((k, node) ->{
            // Convert the node's bounds to Scene coordinates for a fair comparison
            Bounds bounds = node.localToScene(node.getBoundsInLocal());

            if (bounds.contains(sceneX, sceneY)) {
                hoveredBorne.set(node); // We found the node the mouse is over
            }
        });


        return hoveredBorne.get();
    }

    public void addBorne(VueBorne bornes, long index){
        listeBorne.put(index , bornes);
    }

    public VueBorne getBorne(long key){
        if (key == 0)
            return null;
        return listeBorne.get(key);
    }

    public Circuit getCircuit(){
        circuit.setZoomFactor(pane.getScaleX());
        circuit.setTranslateX(simultionGroup.getTranslateX());
        circuit.setTranslateY(simultionGroup.getTranslateY());

        circuit.clearComponents();
        circuit.clearWires();

        simultionGroup.getChildren().forEach((child)->{
            if(child instanceof VueComposantElectrique vue){
                circuit.addComponents(vue.getComponent());
            }


            if(child instanceof VueFil wire){
                circuit.addWires(wire.getWire());
            }


        });

        return circuit.build();
    }

    public void setCircuit(Circuit.Builder circuit) {
        if (!circuit.getComponentsList().isEmpty()) {
            this.circuit = circuit;
            pane.setScaleX(circuit.getZoomFactor());
            pane.setScaleY(circuit.getZoomFactor());

            simultionGroup.setTranslateX(circuit.getTranslateX());
            simultionGroup.setTranslateY(circuit.getTranslateY());

            circuit.getComponentsList().forEach(
                    (comp) ->
                    {
                        var vue = new VueComposantElectrique(
                                ((SimulationScene) this.getScene()).getComposantJson(comp.getKey()),
                                comp.getLayoutX(),
                                comp.getLayoutY(),
                                comp.getRotation(),
                                comp.toBuilder()
                        );

                        vue.addBornes(this, true);

                        simultionGroup.getChildren().add(vue);
                    }
            );

            circuit.getWiresList().forEach((wire) ->{
                var fil = new VueFil(
                        wire.toBuilder(), this
                );



                simultionGroup.getChildren().add(fil);
            });

            handleHideNode(null);
        }
    }
}