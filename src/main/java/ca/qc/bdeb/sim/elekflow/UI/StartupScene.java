package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.util.Objects;

public class StartupScene extends ElekflowScene {

    public static StartupScene createStartupScene(){
        Pane root = new Pane();
        root.setId("mainVBox");


        return new StartupScene(root, 1200, 700, Color.rgb(3, 25, 39));
    }

    private final Pane root;

    private StartupScene(Parent root, double width, double height, Paint fill) {
        super(root, width, height, WindowMode.WINDOWED);

        this.root = (Pane) root;

        this.getStylesheets().add(
                Objects.requireNonNull(getClass()
                                .getResource("/ca/qc/bdeb/sim/elekflow/menuDepart.css"))
                        .toString()
        );

        populateScene();
    }

    private void populateScene(){
        AnchorPane sceneLayout = new AnchorPane();
        HBox options = new HBox();

        AnchorPane.setTopAnchor(options, 600.0); //Y
        AnchorPane.setLeftAnchor(options, 50.0); //X

        options.setSpacing(10);

        var createNewBtn = new Button("Créer nouveau projet");
        var importBtn = new Button("Importer projet existant");

        importBtn.setId("bouttons");
        createNewBtn.setId("bouttons");

        importBtn.setOnAction(this::importNewProject);

        createNewBtn.setOnAction(this::createNewProject);

        ImageView image = new ImageView(App.atlas.getIMG("logo"));
        image.setFitHeight(1228.0/4);
        image.setFitWidth(786.0/4);

        AnchorPane.setTopAnchor(image, 60.0); //Y
        AnchorPane.setLeftAnchor(image, 50.0); //X

        Label textLogo = new Label("ElekFlow");
        textLogo.setId("textLogo");

        AnchorPane.setTopAnchor(textLogo, 165.0); //Y
        AnchorPane.setLeftAnchor(textLogo, 180.0); //X

        options.getChildren().addAll(createNewBtn, importBtn);

        createNewBtn.setOnMouseEntered(e -> {
            createNewBtn.setId("bouttonsBleu");

        });

        createNewBtn.setOnMouseExited(e -> {
            createNewBtn.setId("bouttons");
        });

        importBtn.setOnMouseEntered(e -> {
            importBtn.setId("bouttonsBleu");

        });

        importBtn.setOnMouseExited(e -> {
            importBtn.setId("bouttons");
        });

        sceneLayout.getChildren().addAll(options, image, textLogo);
        root.getChildren().addAll(sceneLayout);
    }

    private void importNewProject(ActionEvent actionEvent){
        var file = new FileChooser();
        file.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichiers ElekFlow", "*.elk"));
        file.setTitle("Importer un projet");
        file.setInitialDirectory(new File("./projets"));

        var ioFile = file.showOpenDialog(root.getScene().getWindow());
        //if (ioFile != null)
            //changeScene(ioFile);
    }

    private void createNewProject(ActionEvent actionEvent){
        var secondaryStage = new Stage();

        VBox root = new VBox();

        //root.setBackground(new Background(new BackgroundFill(Color.web("031927"), null, null)));

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
            //changeScene(
                    //new File(locationText.getText())
            //);
            secondaryStage.close();
        });

        root.getChildren().add(createBtn);

        Scene createNewProjectScene = new Scene(root, 300, 500);

        secondaryStage.getIcons().add(App.atlas.getIMG("iconDark"));
        secondaryStage.setScene(createNewProjectScene);
        secondaryStage.setResizable(false);
        secondaryStage.centerOnScreen();
        secondaryStage.setTitle("Crée un nouveau projet");
        secondaryStage.show();
    }


}
