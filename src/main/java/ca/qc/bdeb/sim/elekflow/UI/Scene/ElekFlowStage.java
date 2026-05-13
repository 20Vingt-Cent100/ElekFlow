package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ExportEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class ElekFlowStage extends Stage {
    public static String SIMULATION = "Simulation";
    public static String STARTUP_SCREEN = "StartupStage";
    public static String CREATE_NEW_PROJECT = "CreateProjectStage";

    private ElekFlowStage(){}

    public static ElekFlowStage createStage(String title, Image icon, boolean closeAllOnExit, boolean isRezisable){
        var stage = new ElekFlowStage();
        stage.setFullScreenExitHint("");
        stage.getIcons().add(icon);
        stage.centerOnScreen();
        stage.setResizable(isRezisable);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle(title);

        if(closeAllOnExit)
            stage.setOnCloseRequest(stage::handleOnCloseRequest);

        Loggeur.logConsole("The stage was created", NiveauLog.TOTAL);
        return stage;
    }

    protected void handleOnCloseRequest(WindowEvent event){
        handleExit();
        Platform.exit();
        System.exit(0);
    }

    public void handleExit(){
        ((ElekflowScene)this.getScene()).executeOnStageClose();
        Loggeur.logConsole("ElekFlow requested EXIT", NiveauLog.TOTAL);
    }

    public void setShow(boolean show){
        if (show) {
            this.show();
        } else {
            this.hide();
        }
    }

    public void changeMode(WindowMode mode){
        switch (mode){
            case WINDOWED -> {setFullScreen(false);
                setMaximized(false);}

            case FULLSCREEN -> setFullScreen(true);
            case MAXIMISED -> setMaximized(true);
        }
    }
}
