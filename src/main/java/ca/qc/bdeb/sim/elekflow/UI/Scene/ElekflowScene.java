package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Utils.OnStageClose;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class ElekflowScene extends javafx.scene.Scene {

    private OnStageClose onStageClose;
    private final WindowMode mode;
    protected final StackPane STACK_PANE;
    protected final BorderPane ROOT = new BorderPane();
    protected final BorderPane OVERLAY_PANE = new BorderPane();

    public ElekflowScene(double width, double height, WindowMode mode) {
        super(new StackPane(), width, height);

        STACK_PANE = (StackPane) getRoot();

        OVERLAY_PANE.setMouseTransparent(false);
        OVERLAY_PANE.setFocusTraversable(true);
        OVERLAY_PANE.setPickOnBounds(false);

        STACK_PANE.getChildren().addAll(ROOT, OVERLAY_PANE);

        ROOT.setPrefWidth(width);
        ROOT.setPrefHeight(height);

        this.mode = mode;

        ROOT.setFocusTraversable(true);
        ROOT.setOnMouseClicked((e)->{ROOT.requestFocus();});

        addStyleSheet(App.atlas.getStylesheet("styleSystem"));

        populateScene();


    }


    protected void addStyleSheet(String style){
        getStylesheets().add(style);
    }

    protected abstract void populateScene();

    public WindowMode getMode(){
        return mode;
    }

    protected void setOnStageClose(OnStageClose onStageClose){
        this.onStageClose = onStageClose;
    }

    public void executeOnStageClose(){
        if (onStageClose != null)
            onStageClose.execute();
    }
}
