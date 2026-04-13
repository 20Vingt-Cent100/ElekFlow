package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.scene.layout.Pane;

public abstract class ElekflowScene extends javafx.scene.Scene {
    private WindowMode mode;
    protected static final Pane ROOT = new Pane();

    public ElekflowScene(double width, double height, WindowMode mode) {
        super(ROOT, width, height);
        ROOT.setPrefWidth(width);
        ROOT.setPrefHeight(height);

        this.mode = mode;

        ROOT.setFocusTraversable(true);
        ROOT.setOnMouseClicked((e)->{ROOT.requestFocus();});

        populateScene();
    }


    protected void addStyleSheet(String style){
        getStylesheets().add(style);
    }

    public abstract void populateScene();

    public WindowMode getMode(){
        return mode;
    }
}
