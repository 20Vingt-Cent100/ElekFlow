package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import org.girod.javafx.svgimage.SVGImage;

public class BoutonComposant extends Button {

    private final ComposantJSON composantJSON;
    private VueComposantElectrique vueCreer = null;

    private final Tooltip tooltip;
    private boolean mouseInside = false;

    public BoutonComposant(ComposantJSON compElec) {

        super("");

        this.composantJSON = compElec;

        this.getStyleClass().addAll(
                "btn-composant",
                "cursor",
                "grow-animation"
        );

        SVGImage svg = App.atlas.getSVG(compElec.getCLE_SVG().getFirst());
        svg.getStyleClass().add("light-color");

        this.setGraphic(svg);
        this.setWidth(svg.getWidth());
        this.setHeight(svg.getHeight());

        this.tooltip = createCustomTooltip();

        setTooltipBehavior();

        setHandles();
    }

    /**
     * TOOLTIP STYLE THEME
     */
    private Tooltip createCustomTooltip() {

        Label titre = new Label(composantJSON.getNOM());
        titre.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        titre.setTextFill(Color.web("#FFFFFF"));

        Label description = new Label(composantJSON.getDESCRIPTION());
        description.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        description.setTextFill(Color.web("#B8C7D9"));
        description.setWrapText(true);
        description.setMaxWidth(260);

        Region sep = new Region();
        sep.setMinHeight(1);
        sep.setPrefHeight(1);
        sep.setMaxHeight(1);
        sep.setStyle("-fx-background-color: rgba(255,255,255,0.08);");

        VBox content = new VBox(titre, sep, description);
        content.setSpacing(10);
        content.setPadding(new Insets(12));

        content.setBackground(new Background(
                new BackgroundFill(
                        Color.web("#0B1623"),
                        new CornerRadii(14),
                        Insets.EMPTY
                )
        ));

        content.setBorder(new Border(
                new BorderStroke(
                        Color.web("#1F2A38"),
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(14),
                        new BorderWidths(1)
                )
        ));

        content.setEffect(new DropShadow(
                18,
                Color.rgb(0, 0, 0, 0.45)
        ));

        Tooltip tt = new Tooltip();
        tt.setGraphic(content);

        tt.setShowDelay(Duration.millis(150));
        tt.setHideDelay(Duration.millis(80));
        tt.setShowDuration(Duration.INDEFINITE);

        tt.setStyle("""
            -fx-background-color: transparent;
            -fx-padding: 0;
        """);

        return tt;
    }

    private void setTooltipBehavior() {

        this.setOnMouseEntered(e -> {
            mouseInside = true;
            tooltip.show(this, e.getScreenX() + 10, e.getScreenY() + 10);
        });

        this.setOnMouseMoved(e -> {
            if (mouseInside) {
                tooltip.setX(e.getScreenX() + 10);
                tooltip.setY(e.getScreenY() + 10);
            }
        });

        this.setOnMouseExited(e -> {
            mouseInside = false;
            tooltip.hide();
        });

        /* sécurité si le bouton est détruit / drag
        this.setOnMouseDragExited(e -> {
            mouseInside = false;
            tooltip.hide();
        });*/
    }

    private void setHandles() {
        setOnMouseDragged(this::handleOnMouseDragged);
        setOnMousePressed(this::handleOnMousePressed);
        setOnMouseReleased(this::handleOnMouseReleased);
        setOnMouseClicked(this::handleOnMouseClicked);
        setOnScroll(this::handleOnScroll);
        setOnZoom(this::handleOnZoom);
        setOnKeyPressed(this::handleOnKeyPressed);
    }

    protected void handleOnMousePressed(MouseEvent event) {
        vueCreer = new VueComposantElectrique(
                composantJSON,
                event.getSceneX() - event.getX(),
                event.getSceneY() - event.getY()
        );

        fireEvent(new ComponentEvent(
                ComponentEvent.CREATE_NEW_COMPONENT,
                vueCreer,
                event
        ));
    }

    protected void handleOnMouseDragged(MouseEvent event) {
        fireEvent(new ComponentEvent(
                ComponentEvent.BUTTON_DRAGGED,
                vueCreer,
                event
        ));
    }

    protected void handleOnMouseReleased(MouseEvent event) {

        if (this.getParent().getParent().contains(
                vueCreer.getTranslateX() + vueCreer.getCenterX(),
                vueCreer.getTranslateY() + vueCreer.getCenterY()
        )) {

            fireEvent(new ComponentEvent(
                    ComponentEvent.DELETE_GHOST_COMPONENT,
                    vueCreer,
                    event
            ));

            vueCreer = null;

        } else {

            fireEvent(new ComponentEvent(
                    ComponentEvent.PLACED,
                    vueCreer,
                    event
            ));
        }
    }
    protected void handleOnKeyPressed(KeyEvent event) {}
    protected void handleOnMouseClicked(MouseEvent event) {}
    protected void handleOnScroll(ScrollEvent event) {}
    protected void handleOnZoom(ZoomEvent event) {}
}