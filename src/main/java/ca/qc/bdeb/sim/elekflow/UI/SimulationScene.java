package ca.qc.bdeb.sim.elekflow.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class SimulationScene extends ElekflowScene{
    public SimulationScene(double width, double height, WindowMode mode) {
        super(width, height, mode);
        BouttonComposant.setLabels();
    }

    @Override
    public void populateScene() {
        ROOT.getChildren().add(menuComposant());
        addStyleSheet(App.atlas.getStylesheet("stylesheet"));
    }

    private AnchorPane menuComposant(){
        AnchorPane menu = new AnchorPane();
        menu.setId("--menu-composant");

        VBox vBox1 = new VBox();
        vBox1.setId("mainVBox");
        AnchorPane.setTopAnchor(vBox1, 40.0); //Y
        AnchorPane.setLeftAnchor(vBox1, 40.0); //X

        VBox vBox2 = new VBox();
        vBox2.setId("vBoxRecherche");
        vBox2.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(vBox2, 62.0); //Y
        AnchorPane.setLeftAnchor(vBox2, 65.0); //X
        Label titre = new Label("Matériel électrique");
        titre.setId("mainTitre");
        //titre.setPadding(new Insets(11.14, 0, 0, 10.33));
        vBox2.getChildren().addAll(titre);

        VBox vBox3 = new VBox();
        vBox3.setId("vBoxRechercheSources");
        vBox3.setAlignment(Pos.BASELINE_CENTER);
        AnchorPane.setTopAnchor(vBox3, 134.0); //Y
        AnchorPane.setLeftAnchor(vBox3, 65.0); //X
        Label titre2 = new Label("Sources de courant");
        titre2.setId("titreSources");
        //titre2.setPadding(new Insets(12.28, 0, 0, 17.56));
        VBox vBoxSeparateur1 = new VBox();
        vBoxSeparateur1.setId("separateurVBox");
        AnchorPane.setTopAnchor(vBoxSeparateur1, 134.0 + 42.99); //Y
        AnchorPane.setLeftAnchor(vBoxSeparateur1, 65.0 + 17.16); //X
        vBox3.getChildren().addAll(titre2);

        TilePane tile = new TilePane();
        tile.setPrefColumns(3);
        tile.setHgap(35);
        tile.setPadding(new Insets(10,20,10,20));

        tile.getChildren().addAll(
                new BouttonComposant("SourceCourantContinu", "Source de courant continu"
                        ),
                new BouttonComposant("SourceCourantAlternatif", "Source de courant alternatif"
                        ),
                new BouttonComposant("SourceCourantCustom", "Source de courant idéal"
                        )
        );

        AnchorPane.setTopAnchor(tile, 134.0 + 50); //Y
        AnchorPane.setLeftAnchor(tile, 65.0); //X

        VBox vBox4 = new VBox();
        vBox4.setId("vBoxRechercheComposantes");
        vBox4.setAlignment(Pos.BASELINE_CENTER);
        AnchorPane.setTopAnchor(vBox4, 337.0); //Y
        AnchorPane.setLeftAnchor(vBox4, 65.0); //X
        Label titre3 = new Label("Composantes électriques");
        titre3.setId("titreComposantes");
        //titre3.setPadding(new Insets(12.28, 0, 0, 17.56));
        vBox4.getChildren().addAll(titre3);
        VBox vBoxSeparateur2 = new VBox();
        vBoxSeparateur2.setId("separateurVBox");
        AnchorPane.setTopAnchor(vBoxSeparateur2, 42.99 + 393 - 56); //Y
        AnchorPane.setLeftAnchor(vBoxSeparateur2, 65.0 + 17.16); //X

        TilePane tile2 = new TilePane();
        tile2.setPrefColumns(3);
        tile2.setVgap(20);
        tile2.setHgap(30);
        tile2.setPadding(new Insets(10,20,10,20));
        tile2.setPrefTileWidth(80);
        tile2.setPrefTileHeight(50);
        tile2.getChildren().addAll(
                new BouttonComposant("Diode", "Diode"),
                new BouttonComposant("Del", "Diode électroluminescente"),
                new BouttonComposant("Moteur", "Moteur"),
                new BouttonComposant("Resistance", "Résistance"),
                new BouttonComposant("ElementChauffant", "Élément chauffant"),
                new BouttonComposant("Inductance", "Inductance"),
                new BouttonComposant("Condensateur", "Condensateur"),
                new BouttonComposant("MiseALaTerre", "Mise à la terre"),
                new BouttonComposant("Fusible", "Fusible"),
                new BouttonComposant("InterrupteurDouble", "Interrupteur double"),
                new BouttonComposant("Commutateur", "Commutateur"),
                new BouttonComposant("InterrupteurSimple", "Interrupteur simple"),
                new BouttonComposant("Ampoule", "Ampoule"),
                new BouttonComposant("AvertisseurSonore", "Avertisseur sonore"),
                new BouttonComposant("HautParleur", "Haut-parleur"));

        AnchorPane.setTopAnchor(tile2, 337.0 + 50); //Y
        AnchorPane.setLeftAnchor(tile2, 65.0); //X

        VBox informationsComposant = new VBox();
        informationsComposant.setId("vBoxDeff");
        informationsComposant.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(informationsComposant, 782.0); //Y
        AnchorPane.setLeftAnchor(informationsComposant, 65.0); //X
        Label labelDeff = new Label("Survolez un matériel électrique pour voir sa description");
        labelDeff.setId("titreDeff");
        labelDeff.setWrapText(true);
        labelDeff.setTextAlignment(TextAlignment.CENTER);
        labelDeff.setAlignment(Pos.CENTER);
        informationsComposant.getChildren().add(labelDeff);

        /*for(int i = 0; i < listeBouttons.size(); i++){

            Button boutton = listeBouttons.get(i);
            Label label = listeLabel.get(i);

            label.setId("labelDeff");
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setAlignment(Pos.CENTER);

            boutton.setOnMouseEntered(e -> {
                informationsComposant.getChildren().setAll(label);
                informationsComposant.setId("vBoxDeffComposante");

            });

            boutton.setOnMouseExited(e -> {
                informationsComposant.getChildren().setAll(labelDeff);
                informationsComposant.setId("vBoxDeff");
            });
        }*/

        menu.getChildren().addAll(vBox1, vBox2, vBox3, vBox4, vBoxSeparateur1, vBoxSeparateur2, tile, tile2, informationsComposant);

        return menu;
    }
}
