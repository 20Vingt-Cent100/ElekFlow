package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ComposantCategorieGrille extends VBox {

    private FlowPane grille = new FlowPane();

    public ComposantCategorieGrille(String nomCategorie, List<ComposantElectriqueGraphique> liste){
        this.getStyleClass().add("grille-composants");

        var titreCategorie = new Label(nomCategorie);
        titreCategorie.getStyleClass().addAll("medium-text", "light-color");

        var titreBox = new VBox();
        titreBox.getStyleClass().add("titre-box");
        titreBox.getChildren().addAll(titreCategorie, new Separator());

        grille.getStyleClass().add("grille-pane");

        if(liste != null) {
            for (ComposantElectriqueGraphique comp : liste) {
                ajouterElement(new BouttonComposant(comp));
            }
        }
        else{
            grille.setManaged(false);
        }

        this.getChildren().addAll(titreBox, grille);
    }

    public void clear(){
        grille.getChildren().clear();
        grille.setManaged(false);
    }

    public void ajouterElement(BouttonComposant compBtn){
        if(!grille.isManaged())
            grille.setManaged(true);

        grille.getChildren().add(compBtn);
    }
}
