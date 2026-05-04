package ca.qc.bdeb.sim.elekflow.UI;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class VueBorne extends Circle {
    public VueBorne(){
        setRadius(3);
    }

    public void hide(){
        this.setVisible(false);
        this.setManaged(false);
    }

    public void show(){
        this.setVisible(true);
        this.setManaged(true);
    }
}
