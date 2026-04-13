package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.ElekFlowFile;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class ProjectElement extends Region{
    public static void loadElements(VBox parent){
        ElekFlowFile.loadProjetsList();
    }


}
