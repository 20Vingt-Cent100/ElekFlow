package ca.qc.bdeb.sim.elekflow.UI.Scene;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.ProjectElement;
import ca.qc.bdeb.sim.elekflow.UI.Utils.WindowMode;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.File;
import java.util.ArrayList;

public class StartupScene extends ElekflowScene {
    public StartupScene() {
        super(1090, 550, WindowMode.WINDOWED);
        addStyleSheet(App.atlas.getStylesheet("menuDepart"));

        populateScene();
    }

    public void populateScene(){
        var leftVBox = new VBox();
        leftVBox.getStyleClass().addAll("left-vbox");
        var rightVBox = new VBox();
        rightVBox.getStyleClass().addAll("right-vbox", "center-top");

        fillLeftVbox(leftVBox);
        fillRightVbox(rightVBox);

        ROOT.setLeft(leftVBox);
        ROOT.setCenter(rightVBox);
    }

    private void fillLeftVbox(VBox box){
        HBox logoArea = new HBox();
        logoArea.getStyleClass().addAll("center");

        ImageView logo = new ImageView(App.atlas.getIMG("logo"));
        logo.setPreserveRatio(true);
        logo.setFitWidth(196.5);
        logo.setFitHeight(307);

        Label logoTitle = new Label("ElekFlow");
        logoTitle.getStyleClass().addAll("text-white", "text-big");

        logoArea.getChildren().addAll(logo, logoTitle);

        HBox btnBox = new HBox();
        btnBox.getStyleClass().addAll("btn-box");

        var createNewBtn = new Button("  Créer projet  ");
        var importBtn = new Button("Importer projet");

        importBtn.getStyleClass().addAll("no-style-btn", "border-accent", "btn", "cursor", "font-20");
        createNewBtn.getStyleClass().addAll("accent-style-btn", "btn", "cursor", "font-20");

        importBtn.setOnAction(this::importNewProject);

        createNewBtn.setOnAction(this::createNewProject);

        btnBox.getChildren().addAll(createNewBtn, importBtn);

        box.getChildren().addAll(logoArea, btnBox);
    }

    private void fillRightVbox(VBox box){
        Label recentProjectLabel = new Label("Projets récents");
        recentProjectLabel.getStyleClass().addAll("text-white", "h2");

        VBox recentProjectList = new VBox();
        recentProjectList.getStyleClass().addAll("liste-projet");

        ProjectElement.loadElements(recentProjectList);

        ScrollPane recentProjectScroll = new ScrollPane(recentProjectList);
        recentProjectScroll.getStyleClass().addAll("project-scroll");

        box.getChildren().addAll(recentProjectLabel, recentProjectScroll);
    }

    private void importNewProject(ActionEvent actionEvent){
        var file = new FileChooser();
        file.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Fichiers ElekFlow", "*.elk"));
        file.setTitle("Importer projet");
        file.setInitialDirectory(new File("./projets"));

        var ioFile = file.showOpenDialog(ROOT.getScene().getWindow());

    }

    private void createNewProject(ActionEvent actionEvent){
        App.addStage(ElekFlowStage.createStage("Créer projet",App.atlas.getIMG("logoAdapte"), false, false), "createProjectStage", false);
        App.changeScene(new CreerProjetScene(), "createProjectStage");
    }

}
