package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.*;

import java.io.File;

public class StartupScene extends Scene {
    public static StartupScene createStartupScene(){
        Pane root = new Pane();
        root.setBackground(new Background(new BackgroundFill(Color.web("031927"), null, null)));

        return new StartupScene(root, 900, 700, Color.rgb(3, 25, 39));
    }

    private final Pane root;

    private StartupScene(Parent root, double width, double height, Paint fill) {
        super(root, width, height);

        this.root = (Pane) root;

        populateScene();
    }

    private void populateScene(){
        VBox sceneLayout = new VBox();
        HBox options = new HBox();

        sceneLayout.getChildren().addAll(options);

        root.getChildren().addAll(sceneLayout);

        var importBtn = new Button("Importer un projet");
        var createNewBtn = new Button("Créer un nouveau projet");

        importBtn.setOnAction(this::importNewProject);
        createNewBtn.setOnAction(this::createNewProject);

        options.getChildren().add(importBtn);
        options.getChildren().add(createNewBtn);
    }

    private void importNewProject(ActionEvent actionEvent){
        var file = new FileChooser();
        file.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichiers ElekFlow", "*.elk"));
        file.setTitle("Importer un projet");
        file.setInitialDirectory(new File("./projets"));

        var ioFile = file.showOpenDialog(root.getScene().getWindow());
        if (ioFile != null)
            changeScene(ioFile);
    }

    private void createNewProject(ActionEvent actionEvent){
        var secondaryStage = new Stage();

        VBox root = new VBox();
        root.setBackground(new Background(new BackgroundFill(Color.web("031927"), null, null)));

        var dir = new DirectoryChooser();
        dir.setInitialDirectory(new File("./projets"));

        var saveLocation = new HBox();

        var locationText = new TextField(dir.getInitialDirectory().getAbsolutePath());
        locationText.setMinWidth(250);

        var locationBtn = new Button("choisir...");

        saveLocation.getChildren().addAll(locationText, locationBtn);

        locationBtn.setOnAction((e) ->{
            try {
                locationText.setText((dir.showDialog(secondaryStage.getOwner())).getAbsolutePath());
            }catch (NullPointerException ex){
                Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            }

        });

        root.getChildren().add(new TextField("nom_projet"));
        root.getChildren().add(saveLocation);

        var createBtn = new Button("Créer projet");
        createBtn.setOnAction((e) ->{
            changeScene(
                    new File(locationText.getText())
            );
            secondaryStage.close();
        });

        root.getChildren().add(createBtn);

        Scene createNewProjectScene = new Scene(root, 300, 500);

        secondaryStage.getIcons().add(App.atlas.getIMG("iconDark"));
        secondaryStage.setScene(createNewProjectScene);
        secondaryStage.setResizable(false);
        secondaryStage.centerOnScreen();
        secondaryStage.setTitle("Créer un nouveau projet");
        secondaryStage.show();
    }

    private void changeScene(File fileToOpen){
        App.setScene(
                SimulationScene.
                        creerSceneDeSimulation(
                                fileToOpen
                        ),
                WindowMode.MAXIMISED);
    }
}
