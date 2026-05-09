package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.file.Path;
import java.util.HashMap;


public class ProjectElement extends HBox {
    public ProjectElement(){

    }

    public static void loadElements(VBox parent){
        HashMap<String, Path> projects = ElekFlowFile.loadRecentProjetsList();

        projects.forEach((name, path) ->{
            parent.getChildren().add(new ProjectElement());
        });
    }


}
