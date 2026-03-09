package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Atlas;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Atlas atlas = new Atlas();
        atlas.loadSvgs();
        atlas.loadImgs();

        AnchorPane root = new AnchorPane();
        root.setId("mainBox");

        VBox vBox1 = new VBox();
        vBox1.setId("mainVBox");
        AnchorPane.setTopAnchor(vBox1, 40.0); //Y
        AnchorPane.setLeftAnchor(vBox1, 40.0); //X

        VBox vBox2 = new VBox();
        vBox2.setId("vBoxRecherche");
        AnchorPane.setTopAnchor(vBox2, 62.0); //Y
        AnchorPane.setLeftAnchor(vBox2, 65.0); //X
        Label titre = new Label("  Matériel électrique");
        titre.setId("mainTitre");
        titre.setPadding(new Insets(11.14, 0, 0, 10.33));
        vBox2.getChildren().addAll(titre);

        VBox vBox3 = new VBox();
        vBox3.setId("vBoxRechercheSources");
        AnchorPane.setTopAnchor(vBox3, 134.0); //Y
        AnchorPane.setLeftAnchor(vBox3, 65.0); //X
        Label titre2 = new Label("Sources de courant");
        titre2.setId("titreSources");
        titre2.setPadding(new Insets(12.28, 0, 0, 17.56));
        VBox vBoxSeparateur1 = new VBox();
        vBoxSeparateur1.setId("separateurVBox");
        AnchorPane.setTopAnchor(vBoxSeparateur1, 134.0 + 42.99); //Y
        AnchorPane.setLeftAnchor(vBoxSeparateur1, 65.0 + 17.16); //X
        vBox3.getChildren().addAll(titre2);

        Button b1 = new Button();
        b1.setGraphic(atlas.getSVG("SourceCourantContinu"));
        b1.setId("bouton");
        AnchorPane.setTopAnchor(b1, 134.0 + 55.99); //Y
        AnchorPane.setLeftAnchor(b1, 65.0 + 17.16); //X
        Tooltip tooltipB1 = new Tooltip("Source de courant continu");
        tooltipB1.setId("tooltip");
        b1.setTooltip(tooltipB1);
        Tooltip.install(b1, tooltipB1);

        Button b2 = new Button();
        b2.setGraphic(atlas.getSVG("SourceCourantAlternatif"));
        b2.setId("bouton");
        AnchorPane.setTopAnchor(b2, 134.0 + 55.99); //Y
        AnchorPane.setLeftAnchor(b2, 65.0 + 17.16 + 56 + 17.16); //X
        Tooltip tooltipB2 = new Tooltip("Source de courant alternatif");
        tooltipB2.setId("tooltip");
        b2.setTooltip(tooltipB2);
        Tooltip.install(b2, tooltipB2);

        Button b3 = new Button();
        b3.setGraphic(atlas.getSVG("SourceCourantCustom"));
        b3.setId("bouton");
        AnchorPane.setTopAnchor(b3, 134.0 + 55.99); //Y
        AnchorPane.setLeftAnchor(b3, 65.0 + 17.16 + 56 + 17.16 + 56 + 17.16); //X
        Tooltip tooltipB3 = new Tooltip("Source de courant idéal");
        tooltipB3.setId("tooltip");
        b3.setTooltip(tooltipB1);
        Tooltip.install(b3, tooltipB3);

        VBox vBox4 = new VBox();
        vBox4.setId("vBoxRechercheComposantes");
        AnchorPane.setTopAnchor(vBox4, 393.0); //Y
        AnchorPane.setLeftAnchor(vBox4, 65.0); //X
        Label titre3 = new Label("Composantes électriques");
        titre3.setId("titreComposantes");
        titre3.setPadding(new Insets(12.28, 0, 0, 17.56));
        vBox4.getChildren().addAll(titre3);
        VBox vBoxSeparateur2 = new VBox();
        vBoxSeparateur2.setId("separateurVBox");
        AnchorPane.setTopAnchor(vBoxSeparateur2, 42.99 + 393); //Y
        AnchorPane.setLeftAnchor(vBoxSeparateur2, 65.0 + 17.16); //X

        Button b4 = new Button();



















        ArrayList<Tooltip> listeTooltip = new ArrayList<>();
        listeTooltip.addAll(Arrays.asList(tooltipB1, tooltipB2, tooltipB3));

        for(int i = 0; i < listeTooltip.size(); i++){
            listeTooltip.get(i).setShowDelay(javafx.util.Duration.ZERO); //délai avant apparition
            listeTooltip.get(i).setHideDelay(javafx.util.Duration.seconds(0)); //délai avant disparition
            listeTooltip.get(i).setShowDuration(javafx.util.Duration.INDEFINITE); //durée d'affichage maximum
        }

        root.getChildren().addAll(vBox1, vBox2, vBox3, vBox4, vBoxSeparateur1, vBoxSeparateur2, b1, b2, b3);

        Scene scene = new Scene(root,320, 240);
        scene.getStylesheets().add(getClass().getResource("/ca/qc/bdeb/sim/elekflow/stylesheet.css").toExternalForm());

        stage.setTitle("Simulation de circuits électriques");
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}