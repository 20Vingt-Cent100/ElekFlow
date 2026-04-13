package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.File;
import java.util.Objects;

public class StartupScene extends ElekflowScene {
    public StartupScene(double width, double height, WindowMode mode) {
        super(width, height, WindowMode.WINDOWED);

        addStyleSheet(App.atlas.getStylesheet("menuDepart"));

        populateScene();
    }

    protected void populateScene(){
        AnchorPane sceneLayout = new AnchorPane();
        HBox options = new HBox();

        AnchorPane.setTopAnchor(options, 450.0); //Y
        AnchorPane.setLeftAnchor(options, 50.0); //X

        options.setSpacing(10);

        var createNewBtn = new Button("Créer projet");
        var importBtn = new Button("Importer projet");

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

        Label texteProjetRecent = new Label("Projets recents");
        texteProjetRecent.setId("texteProjetsRecents");

        AnchorPane.setTopAnchor(texteProjetRecent, 80.0);
        AnchorPane.setLeftAnchor(texteProjetRecent, 650.0);

        VBox content = new VBox();

        ScrollPane scroller = new ScrollPane();
        scroller.setId("scroller");

        for(int i = 0; i < 100; i++){
            Button btn = new Button("Projet" + i);

            btn.setOnMouseEntered(e -> {
                btn.setId("btnProjets");

            });

            btn.setOnMouseExited(e -> {
                btn.setId("btnProjetsBleu");
            });

            content.getChildren().addAll(btn);

        }

        scroller.setContent(content);
        AnchorPane.setTopAnchor(scroller, 155.0);
        AnchorPane.setLeftAnchor(scroller, 550.0);

        sceneLayout.getChildren().addAll(options, image, textLogo, scroller, texteProjetRecent);
        ROOT.getChildren().addAll(sceneLayout);
    }

    private void importNewProject(ActionEvent actionEvent){
        var file = new FileChooser();
        file.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichiers ElekFlow", "*.elk"));
        file.setTitle("Importer un projet");
        file.setInitialDirectory(new File("./projets"));

        var ioFile = file.showOpenDialog(ROOT.getScene().getWindow());
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
