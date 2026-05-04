package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.Events.ConsoleEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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

        fichier.getItems().addAll(save, saveAs, importF, export);

        this.getChildren().add(menuBar);
    }

    private void handleConsoleEvent(ConsoleEvent e){
        if(e.getEventType() == ConsoleEvent.OPEN_CONSOLE){
            consoleActivated = true;
        }else{
            consoleActivated = false;
        }
    }
}
