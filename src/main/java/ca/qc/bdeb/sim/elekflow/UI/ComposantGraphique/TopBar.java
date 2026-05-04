package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Events.ConsoleEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ExportEvent;
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
        var saveAs = new MenuItem("Save as");
        var importF = new MenuItem("Import");
        var export = new MenuItem("Export");
        var exportAsSvg = new MenuItem("Export as SVG");
        exportAsSvg.setOnAction(this::handleExportAsSvg);

        fichier.getItems().addAll(save, saveAs, importF, export, exportAsSvg);

        this.getChildren().add(menuBar);
    }

    private void handleConsoleEvent(ConsoleEvent e){
        if(e.getEventType() == ConsoleEvent.OPEN_CONSOLE){
            consoleActivated = true;
        }else{
            consoleActivated = false;
        }
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
