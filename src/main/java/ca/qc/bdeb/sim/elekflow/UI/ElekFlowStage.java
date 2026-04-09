package ca.qc.bdeb.sim.elekflow.UI;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ElekFlowStage extends Stage {
    private ElekFlowStage(){

    }

    public ElekFlowStage(Stage stage){
        createStage(stage.getTitle(), stage.getIcons().getFirst(), false);
    }

    public static ElekFlowStage createStage(String title, Image icon, boolean closeAllOnExit){
        var stage = new ElekFlowStage();
        stage.setFullScreenExitHint("");
        stage.getIcons().add(icon);

        stage.setTitle(title);

        if(closeAllOnExit)
            stage.setOnCloseRequest(stage::handleOnCloseRequest);

        return stage;
    }

    protected void handleOnCloseRequest(WindowEvent event){
        Platform.exit();
    }

    public void setShow(boolean show){
        if (show) {
            this.show();
        } else {
            this.hide();
        }
    }
}