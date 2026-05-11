package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ConsoleEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ExportEvent;
import ca.qc.bdeb.sim.elekflow.UI.Scene.ElekFlowStage;
import ca.qc.bdeb.sim.elekflow.UI.Scene.StartupScene;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

public class TopBar extends HBox {
    private boolean consoleActivated = false;

    public TopBar(){
        this.getStyleClass().add("top-hbox");

        MenuBar menuBar = new MenuBar();
        var fichier = new Menu("Fichier");
        var parametres = new Menu("Paramètres");
        var vue = new Menu("Vue");

        menuBar.getMenus().addAll(fichier, parametres, vue);

        var console = new MenuItem("Console");
        vue.getItems().add(console);

        console.setOnAction((e) -> {
            fireEvent(consoleActivated ? new ConsoleEvent(ConsoleEvent.HIDE_CONSOLE) : new ConsoleEvent(ConsoleEvent.OPEN_CONSOLE));
        });

        addEventHandler(ConsoleEvent.OPEN_CONSOLE, this::handleConsoleEvent);
        addEventHandler(ConsoleEvent.HIDE_CONSOLE, this::handleConsoleEvent);

        var save = new MenuItem("Save");
        save.setOnAction(this::handleSave);
        var saveAs = new MenuItem("Save as");
        saveAs.setOnAction(this::handleSaveAs);
        var importF = new MenuItem("Import");
        importF.setOnAction(this::handleImport);
        var export = new MenuItem("Export");
        export.setOnAction(this::handleExport);
        var exportAsSvg = new MenuItem("Export as SVG");
        exportAsSvg.setOnAction(this::handleExportAsSvg);

        var close = new MenuItem("Close");
        close.setOnAction(this::handleClose);

        fichier.getItems().addAll(save, saveAs, importF, export, exportAsSvg, close);

        this.getChildren().add(menuBar);
    }

    private void handleConsoleEvent(ConsoleEvent e){
        if(e.getEventType() == ConsoleEvent.OPEN_CONSOLE){
            consoleActivated = true;
        }else{
            consoleActivated = false;
        }
    }

    private void handleSave(ActionEvent event){

    }

    private void handleSaveAs(ActionEvent event){

    }

    private void handleImport(ActionEvent event){

    }

    private void handleExport(ActionEvent event){

    }

    private void handleClose(ActionEvent event){
        App.addStage(ElekFlowStage.createStage("Liste projets", App.atlas.getIMG("LogoDark"), true, false), ElekFlowStage.STARTUP_SCREEN, false);
        App.changeScene(new StartupScene(), ElekFlowStage.STARTUP_SCREEN);

        App.removeStage(ElekFlowStage.SIMULATION);

        App.getStage(ElekFlowStage.STARTUP_SCREEN).setShow(true);
    }

    private void handleExportAsSvg(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer en format SVG");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Scalable vector graphics", "svg"));
        File file = fileChooser.showSaveDialog(this.getScene().getWindow());

        if (file != null){
            String path = file.getAbsolutePath();
            path = path.endsWith(".svg") ? path : path + ".svg";

            Loggeur.logConsole("export to svg started", NiveauLog.TOTAL);
            fireEvent(new ExportEvent(ExportEvent.EXPORT_SVG, new File(path)));
        }


    }
}
