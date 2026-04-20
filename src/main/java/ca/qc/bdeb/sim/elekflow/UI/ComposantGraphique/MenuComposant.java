package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Events.SearchEvent;
import com.github.cliftonlabs.json_simple.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ca.qc.bdeb.sim.elekflow.UI.Utils.JsonCles.*;

public class MenuComposant extends VBox {
    private HashMap<String, ComposantElectrique> COMPOSANTS_ELECTRIQUES = new HashMap<>();
    private HashSet<String> categoriesSet = new HashSet<>();
    private final Label label = new Label();

    private final VBox categorieVBox;

    public MenuComposant(){
        loadAllElectricalComponents();

        this.setFocusTraversable(true);
        this.requestFocus();

        this.setId("menu-composant");

        this.setLayoutX(0);
        this.setLayoutY(0);

        this.setTranslateX(0);
        this.setTranslateY(0);

        var recherche = new ComposantCategorieGrille("Recherche", null);
        recherche.setManaged(false);
        recherche.setVisible(false);

        recherche.getChildren().add(label);

        var listeTemporaire = new ArrayList<ComposantElectrique>();
        listeTemporaire.add(COMPOSANTS_ELECTRIQUES.get("Ampoule"));

        categorieVBox = new VBox();
        categorieVBox.getStyleClass().add("vbox");
        var categorieScrollPane = new ScrollPane(categorieVBox);

        categoriesSet.forEach((str) -> {
            List<ComposantElectrique> c =  new ArrayList<>();
            COMPOSANTS_ELECTRIQUES.forEach((k, v) -> {
                if (v.getCATEGORY().equals(str))
                    c.add(v);
            });

            categorieVBox.getChildren().add(
                    new ComposantCategorieGrille(str, c));
        });

        categorieVBox.getChildren().add(recherche);

        getChildren().addAll(new BarRecherche(), new Separator(), categorieScrollPane);

        this.addEventHandler(SearchEvent.SEARCH_ENGAGED, this::handleSearchEngaged);
        this.addEventHandler(SearchEvent.SEARCH_CANCELED, this::handleSearchCanceled);
    }

    private void handleSearchEngaged(SearchEvent event){
        categorieVBox.getChildren().forEach((n) ->{
            n.setVisible(false);
            n.setManaged(false);
        });

        categorieVBox.getChildren().getLast().setManaged(true);
        categorieVBox.getChildren().getLast().setVisible(true);

        rechercher(this.getChildren().getLast(), event.getSearchQuerry());

    }

    private void handleSearchCanceled(SearchEvent event){
        categorieVBox.getChildren().forEach((n) ->{
            n.setVisible(true);
            n.setManaged(true);
        });

        categorieVBox.getChildren().getLast().setManaged(false);
        categorieVBox.getChildren().getLast().setVisible(false);

    }

    //TODO: REGEX Logique;
    public void rechercher(Node n, String reg){
        label.setText("Aucun composant nommé: 《 " + reg + " 》");
    }

    public void loadAllElectricalComponents(){
        try {
            FileReader jsonFile = new FileReader(getClass().getResource("/ca/qc/bdeb/sim/elekflow/Composants.json").getFile(), StandardCharsets.UTF_8);
            JsonArray jsonArray = (JsonArray) Jsoner.deserialize(jsonFile);

            for (Object o : jsonArray){
                JsonObject obj = (JsonObject) o;

                if (!obj.getString(CATEGORIES).isEmpty()) {

                    COMPOSANTS_ELECTRIQUES.put(
                        obj.getString(NOM),
                        new ComposantElectrique(obj));
                    categoriesSet.add(obj.getString(CATEGORIES));
                    Loggeur.logConsole("Category added: " + obj.getString(CATEGORIES), NiveauLog.TOTAL);
                }
            }
        }catch (JsonException | NullPointerException | IOException ex){
            Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
        }
    }
}
