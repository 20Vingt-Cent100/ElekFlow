package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ComposantCategorieGrille extends VBox {
    ComposantCategorieGrille(String nomCategorie){
        this.getStyleClass().add("grille-composants");

        var titreCategorie = new Label(nomCategorie);
        titreCategorie.getStyleClass().addAll("medium-text", "light-color");

        var titreBox = new VBox();
        titreBox.getStyleClass().add("titre-box");
        titreBox.getChildren().addAll(titreCategorie, new Separator());

        this.getChildren().addAll(titreBox);
    }
}
