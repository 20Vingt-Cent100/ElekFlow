package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ComposantCategorieGrille extends VBox {

    //Grille de composants
    private final FlowPane grille = new FlowPane();

    /**
     * Créer une grille qui affiche les composants présents dans un catégorie donnée
     * @param nomCategorie Nom affiché dans le menu
     * @param liste Liste de composants qui sont présent dans la catégorie
     * @cssValue: grille-composants, enfant: grille-pane
     */
    public ComposantCategorieGrille(String nomCategorie, List<ComposantJSON> liste){
        this.getStyleClass().add("grille-composants");

        var titreCategorie = new Label(nomCategorie);
        titreCategorie.getStyleClass().addAll("medium-text", "text-light");

        var titreBox = new VBox();
        titreBox.getStyleClass().add("titre-box");
        titreBox.getChildren().addAll(titreCategorie, new Separator());

        grille.getStyleClass().add("grille-pane");

        if(liste != null) {
            for (ComposantJSON comp : liste) {
                ajouterElement(new BoutonComposant(comp));
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

    public void ajouterElement(BoutonComposant compBtn){
        if(!grille.isManaged())
            grille.setManaged(true);

        grille.getChildren().add(compBtn);
    }

    public void setShow(boolean show){
        this.setManaged(show);
        this.setVisible(show);
    }
}
