package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.ElekFlowStage;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class StartupScene extends ElekflowScene {
    public StartupScene(double width, double height, WindowMode mode) {
        super(width, height, WindowMode.WINDOWED);

        addStyleSheet(App.atlas.getStylesheet("menuDepart"));

        populateScene();
    }

    public void populateScene(){
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
        AnchorPane.setLeftAnchor(texteProjetRecent, 653.0);

        VBox content = new VBox();
        content.setSpacing(10);

        ScrollPane scroller = new ScrollPane();
        scroller.setId("scrollPane");

        HBox projet1 = new HBox();
        HBox projet2 = new HBox();
        HBox projet3 = new HBox();
        HBox projet4 = new HBox();
        HBox projet5 = new HBox();
        HBox projet6 = new HBox();
        HBox projet7 = new HBox();
        HBox projet8 = new HBox();
        HBox projet9 = new HBox();
        HBox projet10 = new HBox();

        ArrayList<HBox> listeProjet = new ArrayList<>();

        listeProjet.add(projet1);
        listeProjet.add(projet2);
        listeProjet.add(projet3);
        listeProjet.add(projet4);
        listeProjet.add(projet5);
        listeProjet.add(projet6);
        listeProjet.add(projet7);
        listeProjet.add(projet8);
        listeProjet.add(projet9);
        listeProjet.add(projet10);

        for(HBox projet : listeProjet){

            projet.setId("HBoxProjet");
            projet.setPadding(new Insets(10));
            projet.setSpacing(15);

            Label nomProjet = new Label("Nom du projet");
            nomProjet.setId("textProjetListe");

            Button optionProjet = new Button();
            ContextMenu menu = new ContextMenu();
            menu.setId("menuFenetre");

            MenuItem option1 = new MenuItem("Ouvrir");
            MenuItem option2 = new MenuItem("Renommer");
            MenuItem option3 = new MenuItem("Supprimer");

            menu.getItems().addAll(option1, option2, option3);

            optionProjet.setOnAction((e -> {

                menu.show(optionProjet,
                        optionProjet.localToScreen(0, optionProjet.getHeight()).getX(),
                        optionProjet.localToScreen(0, optionProjet.getHeight()).getY());
            }));

            optionProjet.setGraphic(App.atlas.getSVG("Menu"));

            optionProjet.setOnMouseEntered(e -> {
                optionProjet.setGraphic(App.atlas.getSVG("MenuBleu"));

            });

            optionProjet.setOnMouseExited(e -> {
                optionProjet.setGraphic(App.atlas.getSVG("Menu"));
            });

            optionProjet.setAlignment(Pos.CENTER);
            optionProjet.setId("bouttonMenu");

            Label nomPath = new Label("E:\\ElekFlow\\projets\\ProjectPaths");
            nomPath.setId("nomPath");
            nomPath.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
            nomPath.setPadding(new Insets(5));

            // deux spacers pour centrer le bouton
            Region spacerLeft = new Region();
            Region spacerRight = new Region();

            HBox.setHgrow(spacerLeft, Priority.ALWAYS);
            HBox.setHgrow(spacerRight, Priority.ALWAYS);

            nomPath.setMaxWidth(200);

            projet.getChildren().addAll(nomProjet, spacerLeft, optionProjet, spacerRight, nomPath);

            content.getChildren().add(projet);

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
        file.setTitle("Importer projet");
        file.setInitialDirectory(new File("./projets"));

        var ioFile = file.showOpenDialog(ROOT.getScene().getWindow());
        //if (ioFile != null)
            //changeScene(ioFile);
    }

    private void createNewProject(ActionEvent actionEvent){
        App.addStage(ElekFlowStage.createStage("Créer projet",App.atlas.getIMG("logoAdapte"), false, false), "createProjectStage", false);
        App.changeScene(new CreerProjetScene(260, 250, WindowMode.WINDOWED), "createProjectStage");
    }

}
