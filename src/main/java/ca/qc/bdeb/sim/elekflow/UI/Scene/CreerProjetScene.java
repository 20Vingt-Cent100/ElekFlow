package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.nio.file.Path;

public class CreerProjetScene extends ElekflowScene{
    private TextField projectName;
    private TextField projectPath;

    public CreerProjetScene() {
        super(260, 250, WindowMode.WINDOWED);
        addStyleSheet(App.atlas.getStylesheet("menuCreerProjet"));
    }

    @Override
    public void populateScene() {
        VBox conteneur = new VBox();

        conteneur.setPadding(new Insets(10));
        conteneur.setSpacing(10);
        conteneur.setAlignment(Pos.CENTER);

        conteneur.setFocusTraversable(true);
        conteneur.requestFocus();

        Label nomProjet = new Label("Nom du projet");
        nomProjet.setId("texte");
        Label emplacement = new Label("Emplacement");
        emplacement.setId("texte");

        var dir = new DirectoryChooser();
        dir.setInitialDirectory(new File("./projets"));

        projectName = new TextField();
        projectName.setPromptText("Nom du projet");

        projectName.setOnKeyPressed((e) ->{
            if(e.getCode() == KeyCode.ENTER){
                projectName.commitValue();
                createNew();
            }
        });

        var saveLocation = new HBox();

        saveLocation.setSpacing(5);

        projectPath = new TextField(dir.getInitialDirectory().getAbsolutePath());
        projectPath.setMinWidth(100);

        var locationBtn = new Button("Parcourir...");
        locationBtn.getStyleClass().addAll("no-style-btn", "border-accent", "btn", "cursor");


        saveLocation.getChildren().addAll(projectPath, locationBtn);

        locationBtn.setOnAction((e) ->{
            try {
                projectPath.setText((dir.showDialog(App.getStage(ElekFlowStage.CREATE_NEW_PROJECT).getOwner())).getAbsolutePath());
            }catch (NullPointerException ex){
                Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            }

        });

        var createBtn = new Button("Créer le projet");
        createBtn.getStyleClass().addAll("accent-style-btn", "btn", "cursor");

        createBtn.setOnAction((e) ->{
            createNew();
        });


        conteneur.getChildren().addAll(nomProjet, projectName, emplacement, saveLocation, createBtn);
        ROOT.setCenter(conteneur);

        App.getStage(ElekFlowStage.CREATE_NEW_PROJECT).setX(750);
        App.getStage(ElekFlowStage.CREATE_NEW_PROJECT).setY(350);
        App.getStage(ElekFlowStage.CREATE_NEW_PROJECT).setShow(true);
    }

    private void createNew(){
        if(!projectName.getText().isEmpty() && !projectPath.getText().isEmpty()) {
            if (ElekFlowFile.createNewFile(projectName.getText(), Path.of(projectPath.getText()))) {


                App.addStage(
                        ElekFlowStage.createStage("Elekflow: " + projectName.getText(), App.atlas.getIMG("LogoDark"), true, true),
                        ElekFlowStage.SIMULATION,
                        false
                );

                App.changeScene(new SimulationScene(1920, 1080, WindowMode.MAXIMISED, new File(projectPath.getText())), ElekFlowStage.SIMULATION);
                App.getStage(ElekFlowStage.SIMULATION).setShow(true);

                App.removeStage(ElekFlowStage.CREATE_NEW_PROJECT);
                App.removeStage(ElekFlowStage.STARTUP_SCREEN);
            }
        }
    }
}
