package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuOptions extends MenuBar {
    public MenuOptions(){
        var fichier = new Menu("Fichier");
        var parametre = new Menu("Paramètres");
        var vue = new Menu("Vue");

        fichier.getItems().add(new MenuItem("Save"));
        fichier.getItems().add(new MenuItem("Save as"));
        fichier.getItems().add(new MenuItem("Import"));
        fichier.getItems().add(new MenuItem("Export"));

        getMenus().addAll(fichier, parametre, vue);

        requestLayout();
    }
}
