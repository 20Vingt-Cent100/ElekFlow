package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.girod.javafx.svgimage.SVGImage;

public class BoutonComposant extends Button {
    //Texte décrivant le composant électrique du bouton
    private final Text DESCRIPTION = new Text();

    //Structure de donnée correspondant à un entrée JSON du fichier "Composants.json"
    private final ComposantJSON composantJSON;

    //Vue créer lorsque le bouton est appuyé
    private VueComposantElectrique vueCreer = null;

    /**
     * Crée un bouton qui permet de copier la vue graphique d'un composant électrique
     * @param compElec Composant électrique qui sera représenté par le bouton
     *
     * @cssValue: btn-composant
     */
    public BoutonComposant(ComposantJSON compElec){
        super("");

        this.composantJSON = compElec;
        this.getStyleClass().addAll("btn-composant", "cursor", "grow-animation");

        SVGImage svg = App.atlas.getSVG(compElec.getCLE_SVG());
        svg.getStyleClass().addAll("light-color");

        this.setGraphic(svg);
        this.setWidth(svg.getWidth());
        this.setHeight(svg.getHeight());

        DESCRIPTION.setText(compElec.getDESCRIPTION());

        setTooltip(new Tooltip(composantJSON.getNOM()));
        setHandles();
    }

    /**
     * Met un tooltip qui indique le nom du composant lorsque celui-ci est survolé
     */
    private void setTooltip(){
        var tooltip = new Tooltip(composantJSON.getNOM());
        tooltip.getStyleClass().addAll("tooltip", "medium-text");
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
    }

    /**
     * Assigne une méthode pour chaque évènement qu'écoute le bouton
     */
    private void setHandles(){
        setOnMouseDragged(this::handleOnMouseDragged);
        setOnMouseEntered(this::handleOnMouseEntered);
        setOnMouseClicked(this::handleOnMouseClicked);
        setOnScroll(this::handleOnScroll);
        setOnZoom(this::handleOnZoom);
        setOnMousePressed(this::handleOnMousePressed);
        setOnMouseReleased(this::handleOnMouseReleased);
        setOnMouseExited(this::handleOnMouseExited);
        setOnMouseDragReleased(this::handleOnMouseDragReleased);
        setOnMouseDragExited(this::handleOnMouseDragExited);
        setOnKeyPressed(this::handleOnKeyPressed);
    }

    /**
     * Gère lorsque le bouton est appuyé
     * @param event évènement de la souris
     * @Description: Crée une nouvelle VueComposante du composant associé au bouton
     * puis lance un CREATE_NEW_COMPONENT event
     */
    protected void handleOnMousePressed(MouseEvent event) {
        vueCreer = new VueComposantElectrique(composantJSON,
                event.getSceneX() - event.getX(),
                event.getSceneY() - event.getY());

        fireEvent(new ComponentEvent(
                ComponentEvent.CREATE_NEW_COMPONENT,
                vueCreer,
                event));
    }

    /**
     * Gère lorsque le bouton est glissé
     * @param event évènement du glissage
     * @Description: Lance un BUTTON_DRAGGED event
     */
    protected void handleOnMouseDragged(MouseEvent event) {
        fireEvent(new ComponentEvent(
                ComponentEvent.BUTTON_DRAGGED, vueCreer,
                event));
    }

    /**
     * Gère lorsque la souris est lachée.
     * @param event évènement de la souris
     * @Description: Si le composant est laché au dessus du menu de composants, celui-ci sera détruit.
     * Sinon, il sera initialisé dans le groupe de la zone de simulation.
     */
    protected void handleOnMouseReleased(MouseEvent event) {
        if (this.getParent().contains(vueCreer.getTranslateX() + vueCreer.getCenterX(),
                vueCreer.getTranslateY() + vueCreer.getCenterY())){
            fireEvent(new ComponentEvent(ComponentEvent.DELETE_COMPONENT,
                    vueCreer,
                    event));
            vueCreer = null;

        }else{
            fireEvent(new ComponentEvent(ComponentEvent.PLACED,
                    vueCreer,
                    event));
        }
    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnKeyPressed(KeyEvent event) {

    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnMouseDragExited(MouseDragEvent event) {

    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnMouseDragReleased(MouseDragEvent event) {
    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnMouseExited(MouseEvent event) {
    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnZoom(ZoomEvent event) {
    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnScroll(ScrollEvent event) {
    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnMouseClicked(MouseEvent event) {

    }

    /**
     * Inutilisé par défaut
     * @param event évènement reçu
     */
    protected void handleOnMouseEntered(MouseEvent event) {
        //fireEvent(new ComponentEvent(ComponentEvent.SHOW_DESCRIPTION, ));
    }
}
