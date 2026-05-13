package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.ProprieteElectrique;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class InfoMenu extends VBox {

    private static final String BACKGROUND = "#031927";
    private static final String BORDER = "#03131E";

    private static final String TITLE = "#CFE2F3";
    private static final String SUBTITLE = "#8FA9C4";
    private static final String LABEL = "#7FA1C2";
    private static final String VALUE = "#D7E6F5";

    public InfoMenu(ComposantJSON comp) {

        setSpacing(8);
        setPadding(new Insets(12));
        setAlignment(Pos.TOP_LEFT);
        setMaxHeight(Region.USE_PREF_SIZE);

        setStyle("""
            -fx-background-color: %s;
            -fx-background-radius: 12;
            -fx-border-radius: 12;
            -fx-border-color: %s;
            -fx-border-width: 1;
        """.formatted(BACKGROUND, BORDER));

        Label title = new Label(comp.getNOM());
        title.setStyle("""
            -fx-text-fill: %s;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
        """.formatted(TITLE));

        Label subtitle = new Label("Propriétés");
        subtitle.setStyle("""
            -fx-text-fill: %s;
            -fx-font-size: 11px;
        """.formatted(SUBTITLE));

        Separator sep = new Separator();
        sep.setStyle("""
            -fx-background-color: %s;
            -fx-opacity: 0.8;
        """.formatted(BORDER));

        getChildren().addAll(title, subtitle, sep);

        if (comp.getPROPRIETES() != null) {
            loadPropriete(comp.getPROPRIETES());
        }
    }

    private void loadPropriete(ProprieteElectrique[] props) {

        DecimalFormat format = new DecimalFormat("#.###");

        for (ProprieteElectrique p : props) {

            HBox row = new HBox();
            row.setSpacing(10);
            row.setAlignment(Pos.CENTER_LEFT);

            Label name = new Label(p.getPropertyName());
            name.setPrefWidth(110);
            name.setStyle("""
                -fx-text-fill: %s;
                -fx-font-size: 12px;
            """.formatted(LABEL));

            TextField value = new TextField(String.valueOf(p.getValue()));
            value.setStyle("""
                -fx-background-color: %s;
                -fx-text-fill: %s;
                -fx-background-radius: 6;
                -fx-border-radius: 6;
                -fx-border-color: %s;
                -fx-font-size: 12px;
                -fx-padding: 3 6 3 6;
            """.formatted(BORDER, VALUE, BORDER));

            value.setTextFormatter(new TextFormatter<>(c -> {

                if (c.getControlNewText().isEmpty()) return c;

                ParsePosition pos = new ParsePosition(0);
                Object obj = format.parse(c.getControlNewText(), pos);

                if (obj == null || pos.getIndex() < c.getControlNewText().length()) {
                    return null;
                }

                return c;
            }));

            row.getChildren().addAll(name, value);
            getChildren().add(row);
        }
    }
}