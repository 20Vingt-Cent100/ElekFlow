package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.*;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ConsoleEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ExportEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;

import java.io.File;

public class SimulationScene extends ElekflowScene {
    private final Console console = new Console();
    private ZoneSimulation zoneSimulation;

    private final File file;

    public SimulationScene(double width, double height, WindowMode mode, File file) {
        super(width, height, mode);
        addStyleSheet(App.atlas.getStylesheet("simulationStyle"));

        this.file = file;

        loadSimulation();

        this.addEventHandler(ComponentEvent.CREATE_NEW_COMPONENT, this::handleCreateNewComponent);
        this.addEventHandler(ComponentEvent.BUTTON_DRAGGED, this::handleMoveComponent);
        this.addEventHandler(ComponentEvent.PLACED, this::handlePlacedComponent);
        this.addEventHandler(ComponentEvent.DELETE_GHOST_COMPONENT, this::handleDeleteComponent);
        this.addEventHandler(ShowInfoEvent.SHOW_INFO, this::handleShowInfoEvent);
        this.addEventHandler(ConsoleEvent.OPEN_CONSOLE, this::handleOpenConsole);
        this.addEventHandler(ConsoleEvent.HIDE_CONSOLE, this::handleHideConsole);
        this.addEventHandler(ExportEvent.EXPORT_SVG, this::handleExportSVG);
    }

    private void handleExportSVG(ExportEvent event){
        zoneSimulation.exportToSVG(event.getFile());
    }

    private void handleCreateNewComponent(ComponentEvent e) {
        VueComposantElectrique vue = e.getComposantElectrique();

        OVERLAY_PANE.getChildren().add(vue);
        Loggeur.logConsole(
                "New component: " + vue.getComposantNom() + " was created at ("
                        + (e.getMouseEvent().getSceneX() - e.getMouseEvent().getX())
                        + ", " + (e.getMouseEvent().getSceneY() - e.getMouseEvent().getY()) + ")",
                NiveauLog.TOTAL);
    }

    private void handlePlacedComponent(ComponentEvent e) {
        OVERLAY_PANE.getChildren().remove(e.getComposantElectrique());
        zoneSimulation.addComponent(e.getComposantElectrique(), e.getMouseEvent());
    }

    private void handleMoveComponent(ComponentEvent e) {
        ((VueComposantElectrique) OVERLAY_PANE.getChildren().get(OVERLAY_PANE.getChildren().indexOf(e.getComposantElectrique())))
                .moveOnDrag(e.getMouseEvent());
    }

    private void handleDeleteComponent(ComponentEvent e) {
        OVERLAY_PANE.getChildren().remove(e.getComposantElectrique());
    }

    private void handleShowInfoEvent(ShowInfoEvent e) {
        var infoMenu = new InfoMenu(e.compElecGraph.getNOM());

        OVERLAY_PANE.setRight(infoMenu);
    }

    private void handleOpenConsole(ConsoleEvent e) {
        ROOT.setBottom(console);
    }

    private void handleHideConsole(ConsoleEvent e) {
        ROOT.setBottom(null);
    }

    @Override
    public void populateScene() {
        ROOT.setLeft(new MenuComposant());
        ROOT.setTop(new TopBar());

        zoneSimulation = new ZoneSimulation();
        ROOT.setCenter(zoneSimulation);
    }

    private void loadSimulation(){

    }
}