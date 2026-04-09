package ca.qc.bdeb.sim.elekflow.UI;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.File;

public class ElekflowScene extends javafx.scene.Scene {
    private WindowMode mode;

    public static ElekflowScene creerSceneSimulation(File fileProject, double width, double height, WindowMode mode){
        var root = new Pane();

        return new ElekflowScene(root, width, height, mode);
    }

    public ElekflowScene(Parent root, double width, double height, WindowMode mode) {
        super(root, width, height);

        this.mode = mode;
    }

    public WindowMode getMode(){
        return mode;
    }
}
