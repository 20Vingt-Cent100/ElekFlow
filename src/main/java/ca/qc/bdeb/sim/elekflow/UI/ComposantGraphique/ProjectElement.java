package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import ca.qc.bdeb.sim.elekflow.UI.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.util.HashMap;


public class ProjectElement extends HBox {
    public ProjectElement(String name, Path path){
        this.getStyleClass().addAll("hbox-projet", "cursor", "center");

        Label nomProjet = new Label(name);
        nomProjet.getStyleClass().addAll("text-white", "medium-text", "center-left", "medium-text");

        Button optionProjet = new Button();
        optionProjet.getStyleClass().addAll("no-style-btn", "grow-animation", "accent-hover", "gray-light");

        ContextMenu menu = new ContextMenu();
        menu.getStyleClass().addAll("menu-context");

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

        optionProjet.setAlignment(Pos.CENTER);

        Label nomPath = new Label(path.toAbsolutePath().toString());
        nomPath.getStyleClass().addAll("gray", "medium-text");
        nomPath.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);

        Region spacerLeft = new Region();
        Region spacerRight = new Region();

        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        this.getChildren().addAll(nomProjet, spacerLeft, optionProjet, spacerRight, nomPath);
    }

    public static void loadElements(VBox parent){
        HashMap<String, Path> projects = ElekFlowFile.loadRecentProjetsList();

        projects.forEach((name, path) ->{
            ProjectElement newElement = new ProjectElement(name, path);
            newElement.setPrefWidth(parent.getPrefWidth());
            parent.getChildren().add(newElement);
        });
    }


}
