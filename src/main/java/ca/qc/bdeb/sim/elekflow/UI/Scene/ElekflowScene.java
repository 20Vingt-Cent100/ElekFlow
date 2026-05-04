package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.scene.layout.Pane;

public abstract class ElekflowScene extends javafx.scene.Scene {
    private WindowMode mode;
    protected final Pane ROOT;

    public ElekflowScene(double width, double height, WindowMode mode) {
        super(new Pane(), width, height);
        ROOT = (Pane) this.getRoot();
        ROOT.setPrefWidth(width);
        ROOT.setPrefHeight(height);

        this.mode = mode;

        ROOT.setFocusTraversable(true);
        ROOT.setOnMouseClicked((e)->{ROOT.requestFocus();});

        ROOT.getStyleClass().add("root");

        populateScene();
    }


    protected void addStyleSheet(String style){
        getStylesheets().add(style);
    }

    protected abstract void populateScene();

    public WindowMode getMode(){
        return mode;
    }
}
