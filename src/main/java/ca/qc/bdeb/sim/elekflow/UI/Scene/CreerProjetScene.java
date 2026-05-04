package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class CreerProjetScene extends ElekflowScene{
    public CreerProjetScene(double width, double height, WindowMode mode) {
        super(width, height, mode);
    }

    @Override
    protected void populateScene() {
        VBox conteneur = new VBox();

        conteneur.setPadding(new Insets(10));
        conteneur.setSpacing(10);
        conteneur.setAlignment(Pos.CENTER);

        conteneur.setFocusTraversable(true);
        conteneur.requestFocus();

        addStyleSheet(App.atlas.getStylesheet("menuCreerProjet"));

        Label nomProjet = new Label("Nom du projet");
        nomProjet.setId("texte");
        Label emplacement = new Label("Emplacement");
        emplacement.setId("texte");
        Label creation = new Label("Créer maintenant?");
        creation.setId("texte");

        var dir = new DirectoryChooser();
        dir.setInitialDirectory(new File("./projets"));

        TextField textDebut = new TextField();
        textDebut.setPromptText("Nom du projet");

        // Sélectionner tout le texte après affichage
        //Platform.runLater(textDebut::selectAll);

        var saveLocation = new HBox();

        saveLocation.setSpacing(5);

        var locationText = new TextField(dir.getInitialDirectory().getAbsolutePath());
        locationText.setMinWidth(100);

        var locationBtn = new Button("Parcourir...");
        locationBtn.setId("bouttons");

        locationBtn.setOnMouseEntered(e -> {
            locationBtn.setId("bouttonsBleu");

        });

        locationBtn.setOnMouseExited(e -> {
            locationBtn.setId("bouttons");
        });


        saveLocation.getChildren().addAll(locationText, locationBtn);

        locationBtn.setOnAction((e) ->{
            try {
                locationText.setText((dir.showDialog(App.getStage("createProjectStage").getOwner())).getAbsolutePath());
            }catch (NullPointerException ex){
                Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            }

        });

        var createBtn = new Button("Créer le projet");
        createBtn.setId("bouttons");

        createBtn.setOnMouseEntered(e -> {
            createBtn.setId("bouttonsBleu");

        });

        createBtn.setOnMouseExited(e -> {
            createBtn.setId("bouttons");
        });



        conteneur.getChildren().addAll(nomProjet, textDebut, emplacement, saveLocation, creation, createBtn);
        ROOT.getChildren().add(conteneur);

        App.getStage("createProjectStage").setX(750);
        App.getStage("createProjectStage").setY(350);
        App.getStage("createProjectStage").setShow(true);
    }
}
