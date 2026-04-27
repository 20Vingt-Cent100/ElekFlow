package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class InfoMenu extends VBox {
    public InfoMenu(String composantName){
        this.getChildren().add(new Label(composantName));
    }
}
