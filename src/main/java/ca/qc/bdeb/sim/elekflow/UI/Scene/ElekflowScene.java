package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class ElekflowScene extends javafx.scene.Scene {
    private WindowMode mode;
    protected final StackPane STACK_PANE;
    protected final BorderPane ROOT = new BorderPane();
    protected final BorderPane OVERLAY_PANE = new BorderPane();

    public ElekflowScene(double width, double height, WindowMode mode) {
        super(new StackPane(), width, height);

        STACK_PANE = (StackPane) getRoot();

        OVERLAY_PANE.setMouseTransparent(true);
        OVERLAY_PANE.setPickOnBounds(false);

        STACK_PANE.getChildren().addAll(ROOT, OVERLAY_PANE);

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

    protected abstract void populateScene();

    public WindowMode getMode(){
        return mode;
    }
}
