package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TopBar extends HBox {
    public TopBar(String projectName){


        MenuBar menuBar = new MenuBar();
        var fichier = new Menu("Fichier");
        var parametres = new Menu("Paramètres");
        var vue = new Menu("Vue");

        menuBar.getMenus().addAll(fichier, parametres, vue);

        var console = new MenuItem("Console");
        vue.getItems().add(console);

        var save = new MenuItem("Save");
        var saveAs = new MenuItem("Save as");
        var importF = new MenuItem("Import");
        var export = new MenuItem("Export");

        fichier.getItems().addAll(save, saveAs, importF, export);

        this.getChildren().add(menuBar);
    }
}
