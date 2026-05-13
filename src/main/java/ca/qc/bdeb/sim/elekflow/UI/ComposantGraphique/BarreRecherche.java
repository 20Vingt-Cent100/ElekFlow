package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.SearchEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class BarreRecherche extends HBox {
    private final TextField rechercheText = new TextField();

    /**
     * Barre de recherche
     * @cssValue:   css classe : search-bar
     */
    public BarreRecherche(){
        this.getStyleClass().addAll("search-bar", "center");
        setChildren();
    }

    /**
     * Ajoute les balises enfants de la barre de recherche
     */
    private void setChildren(){
        rechercheText.getStyleClass().addAll("medium-text", "text-light");
        rechercheText.setPromptText("Recherchez...");
        rechercheText.setOnKeyTyped(this::handleOnKeyTyped);

        var iconeRecherche = App.atlas.getSVG("SearchIcon");
        iconeRecherche.getStyleClass().add("light-color");

        var cancelButton = new Button("");
        var graphic = App.atlas.getSVG("XIcone");
        graphic.getStyleClass().add("light-color");
        cancelButton.setGraphic(graphic);
        cancelButton.getStyleClass().addAll("cursor", "no-style-btn", "grow-animation");
        cancelButton.setOnAction(this::handleOnCancelButtonPressed);

        getChildren().addAll(iconeRecherche, rechercheText, cancelButton);
    }

    /**
     * Gère la recherche
     * @param event event inutilisé.
     * @implNote Lance un SEARCH_CANCELED event si le champ de texte est vide
     * et SEARCH_ENGAGED si le champ de texte contient des caractères
     */
    private void handleOnKeyTyped(KeyEvent event){
        SearchEvent searchEventevent = getRechercheText().isEmpty() ?
                new SearchEvent(SearchEvent.SEARCH_CANCELED, getRechercheText())
                : new SearchEvent(SearchEvent.SEARCH_ENGAGED, getRechercheText());


        fireEvent(searchEventevent);
        Loggeur.logConsole("Search event fired. Content: " + searchEventevent.getSearchQuerry(), NiveauLog.TOTAL);
    }

    /**
     * Gère l'annulation de la recherche lorsque le bouton est appuyé
     * @param event Évènement non utilisé
     * @implNote  Efface le contenu du champ texte et lance un SEARCH_CANCELED event
     */
    private void handleOnCancelButtonPressed(ActionEvent event){
        if(!getRechercheText().isEmpty())
            rechercheText.clear();

        SearchEvent firedEvent = new SearchEvent(SearchEvent.SEARCH_CANCELED, null);

        fireEvent(firedEvent);
        Loggeur.logConsole("Search event fired. Content: " + firedEvent.getSearchQuerry(), NiveauLog.TOTAL);
    }

    public String getRechercheText() {
        return rechercheText.getText();
    }
}
