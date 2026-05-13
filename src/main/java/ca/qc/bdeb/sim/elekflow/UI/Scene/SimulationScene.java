package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.*;
import ca.qc.bdeb.sim.elekflow.UI.Events.*;
import ca.qc.bdeb.sim.elekflow.UI.Utils.BehaviorList;
import ca.qc.bdeb.sim.elekflow.UI.Utils.InteractionListe;
import ca.qc.bdeb.sim.elekflow.UI.Utils.OnStageClose;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;
import ca.qc.bdeb.sim.elekflow.proto.Component;
import ca.qc.bdeb.sim.elekflow.proto.Content;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;

import java.io.File;

public class SimulationScene extends ElekflowScene {
    private final Console console = new Console();
    private ZoneSimulation zoneSimulation;
    private MenuComposant menuComposant;

    private final ElekFlowFile file;

    public SimulationScene(double width, double height, WindowMode mode, ElekFlowFile file) {
        super(width, height, mode);
        addStyleSheet(App.atlas.getStylesheet("simulationStyle"));

        this.file = file;

        InteractionListe.loadListe();
        BehaviorList.loadBehaviors();
        loadSimulation();

        this.addEventHandler(ComponentEvent.CREATE_NEW_COMPONENT, this::handleCreateNewComponent);
        this.addEventHandler(ComponentEvent.BUTTON_DRAGGED, this::handleMoveComponent);
        this.addEventHandler(ComponentEvent.PLACED, this::handlePlacedComponent);
        this.addEventHandler(ComponentEvent.DELETE_GHOST_COMPONENT, this::handleDeleteComponent);
        this.addEventHandler(ShowInfoEvent.SHOW_INFO, this::handleShowInfoEvent);
        this.addEventHandler(ShowInfoEvent.HIDE_INFO, this::handleHideInfoEvent);

        this.addEventHandler(ConsoleEvent.OPEN_CONSOLE, this::handleOpenConsole);
        this.addEventHandler(ConsoleEvent.HIDE_CONSOLE, this::handleHideConsole);
        this.addEventHandler(ExportEvent.EXPORT_SVG, this::handleExportSVG);
        this.addEventHandler(FileEvent.SAVE_EVENT, this::handleSaveFile);

        this.setOnKeyPressed((e) ->{
            if(e.getCode() == KeyCode.S && e.isControlDown()){
                saveFile(null);
            }
        });

        this.setOnStageClose(new OnStageClose() {
            @Override
            public void execute() {
                saveFile(null);
            }
        });
    }

    private void handleSaveFile(FileEvent fileEvent) {
        saveFile(fileEvent.file);
    }


    private void saveFile(File file){
        Content content = Content.newBuilder()
                .setCircuit(zoneSimulation.getCircuit())
                .build();

        if(!this.file.saveFile(file, content))
            Loggeur.logConsole("File could not be saved", NiveauLog.ERREUR);
        else
            Loggeur.logConsole("File is being saved", NiveauLog.TOTAL);
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
        e.getComposantElectrique().addBornes(zoneSimulation);
    }

    private void handleMoveComponent(ComponentEvent e) {
        e.getComposantElectrique().moveOnDrag(e.getMouseEvent());
    }

    private void handleDeleteComponent(ComponentEvent e) {
        OVERLAY_PANE.getChildren().remove(e.getComposantElectrique());
    }

    private void handleShowInfoEvent(ShowInfoEvent e) {
        var infoMenu = new InfoMenu(e.compElecGraph);

        OVERLAY_PANE.setRight(infoMenu);
    }

    private void handleHideInfoEvent(ShowInfoEvent e) {
        OVERLAY_PANE.setRight(null);
    }

    private void handleOpenConsole(ConsoleEvent e) {
        ROOT.setBottom(console);
    }

    private void handleHideConsole(ConsoleEvent e) {
        ROOT.setBottom(null);
    }

    @Override
    public void populateScene() {
        menuComposant = new MenuComposant();
        ROOT.setLeft(menuComposant);
        ROOT.setTop(new TopBar());

        zoneSimulation = new ZoneSimulation();
        ROOT.setCenter(zoneSimulation);
    }

    private void loadSimulation(){
        zoneSimulation.setCircuit(file.getCircuit().toBuilder());
    }

    public ComposantJSON getComposantJson(String key){
        return menuComposant.getComposantJson(key);
    }
}