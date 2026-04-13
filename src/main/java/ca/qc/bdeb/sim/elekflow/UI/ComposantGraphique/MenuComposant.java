package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Events.SearchEvent;
import com.github.cliftonlabs.json_simple.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

import static ca.qc.bdeb.sim.elekflow.UI.Utils.JsonCles.*;

public class MenuComposant extends VBox {
    private HashMap<String, ComposantElectrique> COMPOSANTS_ELECTRIQUES = new HashMap<>();
    private LinkedList<String> listeCategorie = new LinkedList<>();
    private final Label label = new Label();

    public MenuComposant(){
        loadAllElectricalComponents();

        this.setFocusTraversable(true);
        this.requestFocus();

        this.setId("menu-composant");

        this.setLayoutX(0);
        this.setLayoutY(0);

        this.setTranslateX(0);
        this.setTranslateY(0);

        var recherche = new ComposantCategorieGrille("Recherche");
        recherche.setManaged(false);
        recherche.setVisible(false);

        recherche.getChildren().add(label);

        getChildren().addAll(new BarRecherche(), new Separator(),
                new ComposantCategorieGrille("Sources"),
                new ComposantCategorieGrille("Composants linéaires"),
                new ComposantCategorieGrille("Semiconducteurs"),
                new ComposantCategorieGrille("Interrupteurs"),
                new ComposantCategorieGrille("Portes logiques"),
                new ComposantCategorieGrille("Autre"),
                recherche
        );



        this.addEventHandler(SearchEvent.SEARCH_ENGAGED, this::handleSearchEngaged);
        this.addEventHandler(SearchEvent.SEARCH_CANCELED, this::handleSearchCanceled);
    }

    private void handleSearchEngaged(SearchEvent event){
        for (int i = 2; i < this.getChildren().size() - 1; i++) {
            this.getChildren().get(i).setVisible(false);
            this.getChildren().get(i).setManaged(false);
        }

        this.getChildren().getLast().setManaged(true);
        this.getChildren().getLast().setVisible(true);

        rechercher(this.getChildren().getLast(), event.getSearchQuerry());
        label.setText("Aucun composant nommé: 《 " + event.getSearchQuerry() + " 》");
    }

    private void handleSearchCanceled(SearchEvent event){
        for (int i = 2; i < this.getChildren().size() - 1; i++) {
            this.getChildren().get(i).setVisible(true);
            this.getChildren().get(i).setManaged(true);
        }

        this.getChildren().getLast().setManaged(false);
        this.getChildren().getLast().setVisible(false);

    }

    //TODO: REGEX Logique;
    public void rechercher(Node n, String reg){
    }

    public void loadAllElectricalComponents(){
        try {
            FileReader jsonFile = new FileReader(getClass().getResource("/ca/qc/bdeb/sim/elekflow/Composants.json").getFile());
            JsonArray jsonArray = (JsonArray) Jsoner.deserialize(jsonFile);

            for (Object o : jsonArray){
                JsonObject obj = (JsonObject) o;
                COMPOSANTS_ELECTRIQUES.put(
                        obj.getString(NOM),
                        new ComposantElectrique(obj));
            }
        }catch (FileNotFoundException | JsonException ex){
            Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
        }
    }
}
