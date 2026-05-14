package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Scene.ElekflowScene;
import ca.qc.bdeb.sim.elekflow.UI.Scene.StartupScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;


public class ProjectElement extends HBox {
    private final File file;

    private final Label nomProjet;
    private final Label nomPath;

    public ProjectElement(String name, Path path){
        file = new File(path.toAbsolutePath().toUri());
        Loggeur.logConsole("file is named " + file.getName(), NiveauLog.TOTAL);

        this.getStyleClass().addAll("hbox-projet", "cursor", "center");
        nomProjet = new Label(name);
        nomProjet.getStyleClass().addAll("text-white", "medium-text", "center-left", "medium-text");
        nomProjet.setPrefWidth(nomProjet.getWidth());
        nomProjet.setMinWidth(200);

        MenuButton optionProjet = new MenuButton();
        optionProjet.getStyleClass().addAll("no-style-btn", "grow-animation", "accent-hover", "gray-light", "project-menu");

        MenuItem option1 = new MenuItem("Ouvrir");
        option1.getStyleClass().addAll("menu-item");
        option1.setOnAction((e) ->{openFile();});

        MenuItem option2 = new MenuItem("Déplacer");
        option2.getStyleClass().addAll("menu-item");
        option2.setOnAction((e) ->{renameFile();});

        MenuItem option3 = new MenuItem("Supprimer");
        option3.getStyleClass().addAll("menu-item");
        option3.setOnAction((e) ->{deleteFile();});

        optionProjet.getItems().addAll(option1, option2, option3);

        optionProjet.setGraphic(App.atlas.getSVG("Menu"));

        optionProjet.setAlignment(Pos.CENTER);

        nomPath = new Label(path.toAbsolutePath().toString());
        nomPath.getStyleClass().addAll("gray", "medium-text");
        nomPath.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
        nomPath.setPrefWidth(nomPath.getWidth());
        nomPath.setMinWidth(250);

        Region spacerLeft = new Region();
        Region spacerRight = new Region();

        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        HBox.setHgrow(spacerRight, Priority.ALWAYS);

        this.getChildren().addAll(nomProjet, spacerLeft, optionProjet, spacerRight, nomPath);
        this.setOnMouseClicked(this::handleOnMouseClicked);
    }

    public static void loadElements(VBox parent){
        HashMap<String, Path> projects = ElekFlowFile.loadRecentProjetsList();

        projects.forEach((name, path) ->{
            ProjectElement newElement = new ProjectElement(name.replace(".elk", ""), path);
            newElement.setPrefWidth(parent.getPrefWidth());
            parent.getChildren().add(newElement);
        });

        if(projects.isEmpty()){
            loadIfEmpty(parent);
        }
    }

    private void handleOnMouseClicked(MouseEvent event){
        if (event.getClickCount() == 2){
            openFile();
        }
    }

    private void openFile(){
        ((StartupScene)this.getScene()).openFile(file);
    }

    private void renameFile(){
        File newFile;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file.getParentFile());
        newFile =  fileChooser.showSaveDialog(this.getScene().getWindow());
        newFile = new File(newFile.getPath()+ ".elk");

        if(file.renameTo(newFile)){
            Loggeur.logConsole("file was renamed to " + newFile.getName(), NiveauLog.TOTAL);
            nomProjet.setText(newFile.getName().replace(".elk", ""));
            nomPath.setText(newFile.getAbsolutePath());
        }else {
            Loggeur.logConsole("failed renaming the file" , NiveauLog.TOTAL);
        }
    }

    private void deleteFile() {
        try {
            Files.delete(file.toPath());

            Loggeur.logConsole("File was deleted successfully", NiveauLog.TOTAL);
            if (this.getParent() instanceof VBox parent) {
                parent.getChildren().remove(this);

                if(parent.getChildren().isEmpty()){
                    loadIfEmpty(parent);
                }
            }

        } catch (IOException e) {
            // Ça va te dire précisément si c'est "Access Denied" ou "File in use"
            Loggeur.logConsole("Failed to delete file: " + e.getMessage(), NiveauLog.TOTAL);
            e.printStackTrace();
        }
    }


    private static void loadIfEmpty(VBox parent){
        Label emptyRegionTitle = new Label("Aucun projet récent");
        emptyRegionTitle.getStyleClass().addAll("font-20", "text-white");
        Label emptyRegionDescription = new Label("Créez un nouveau projet ou importez en un pour commencer");
        emptyRegionDescription.getStyleClass().addAll("text-white");
        parent.getChildren().addAll(emptyRegionTitle, emptyRegionDescription);
    }
}
