package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.SearchEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class BarRecherche extends HBox {
    private TextField rechercheText = new TextField();



    public BarRecherche(){

        this.setId("search-bar");

        rechercheText.getStyleClass().add("medium-text");
        rechercheText.setPromptText("Recherchez...");

        rechercheText.setOnKeyTyped((e) ->{

            SearchEvent event = getRechercheText().isEmpty() ?
                    new SearchEvent(SearchEvent.SEARCH_CANCELED, getRechercheText())
                    : new SearchEvent(SearchEvent.SEARCH_ENGAGED, getRechercheText());

            
            fireEvent(event);
            Loggeur.logConsole(event.getSearchQuerry(), NiveauLog.TOTAL);
        });

        var iconRecherche = App.atlas.getSVG("SearchIcon");
        iconRecherche.setId("search-icon");

        var btnAnnule = new Button("");
        btnAnnule.setGraphic(App.atlas.getSVG("XIcone"));

        btnAnnule.setOnAction((e)->{
            if(!rechercheText.getText().isEmpty())
                rechercheText.clear();

            fireEvent(new SearchEvent(SearchEvent.SEARCH_CANCELED, null));
        });

        getChildren().addAll(iconRecherche, rechercheText, btnAnnule);
    }

    public String getRechercheText() {
        return rechercheText.getText();
    }
}
