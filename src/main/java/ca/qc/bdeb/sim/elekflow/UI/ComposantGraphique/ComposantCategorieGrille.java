package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ComposantCategorieGrille extends VBox {

    public ComposantCategorieGrille(String nomCategorie, List<ComposantElectrique> liste){
        this.getStyleClass().add("grille-composants");

        var titreCategorie = new Label(nomCategorie);
        titreCategorie.getStyleClass().addAll("medium-text", "light-color");

        var titreBox = new VBox();
        titreBox.getStyleClass().add("titre-box");
        titreBox.getChildren().addAll(titreCategorie, new Separator());

        var grille = new FlowPane();
        grille.getStyleClass().add("grille-pane");

        if(liste != null) {
            for (ComposantElectrique comp : liste) {
                grille.getChildren().add(new BouttonComposant(comp));
            }
        }
        else{
            grille.setManaged(false);
        }

        this.getChildren().addAll(titreBox, grille);
    }
}
