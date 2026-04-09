package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ElekFlowStage extends Stage {
    public static ElekFlowStage createStage(String title, Image icon, boolean closeAllOnExit){
        var stage = new ElekFlowStage();
        stage.setFullScreenExitHint("");
        stage.getIcons().add(icon);
        stage.centerOnScreen();
        stage.setResizable(false);

        stage.setTitle(title);

        if(closeAllOnExit)
            stage.setOnCloseRequest(stage::handleOnCloseRequest);

        Loggeur.logConsole("The stage was created", NiveauLog.TOTAL);
        return stage;
    }

    protected void handleOnCloseRequest(WindowEvent event){
        Loggeur.logConsole("ElekFlow requested EXIT", NiveauLog.TOTAL);
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
