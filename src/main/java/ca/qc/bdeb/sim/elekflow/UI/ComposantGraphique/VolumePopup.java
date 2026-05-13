package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class VolumePopup extends VBox {
    public VolumePopup(){
        this.getChildren().addAll(new Label("Volume"), new Separator(), new Slider(0.0, 1.0 ,0.3));
    }
}
