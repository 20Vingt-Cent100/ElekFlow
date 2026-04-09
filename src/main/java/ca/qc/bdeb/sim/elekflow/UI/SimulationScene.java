package ca.qc.bdeb.sim.elekflow.UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SimulationScene extends Scene {
    private WindowMode mode = WindowMode.FULLSCREEN;

    public static SimulationScene creerSceneDeSimulation(File projectFile){
        var root = new AnchorPane();

        return new SimulationScene(root, 1920, 1080);
    }

    private SimulationScene(Parent racine, double width, double height) {
        super(racine, width, height);

        AnchorPane root = (AnchorPane) racine;

        this.getStylesheets().add(
                Objects.requireNonNull(getClass()
                                .getResource("/ca/qc/bdeb/sim/elekflow/stylesheet.css"))
                        .toString()
        );


        root.setId("mainBox");

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

        Button b1 = new Button();
        b1.setGraphic(App.atlas.getSVG("SourceCourantContinu"));
        b1.setId("bouton");
        Tooltip tooltipB1 = new Tooltip("Source de courant continu");
        tooltipB1.setId("tooltip");
        b1.setTooltip(tooltipB1);
        Tooltip.install(b1, tooltipB1);
        b1.setOnMouseEntered(e -> {

        });
        b1.setOnMouseExited(e -> {

        });

        Button b2 = new Button();
        b2.setGraphic(App.atlas.getSVG("SourceCourantAlternatif"));
        b2.setId("bouton");
        Tooltip tooltipB2 = new Tooltip("Source de courant alternatif");
        tooltipB2.setId("tooltip");
        b2.setTooltip(tooltipB2);
        Tooltip.install(b2, tooltipB2);

        Button b3 = new Button();
        b3.setGraphic(App.atlas.getSVG("SourceCourantCustom"));
        b3.setId("bouton");

        Tooltip tooltipB3 = new Tooltip("Source de courant idéal");
        tooltipB3.setId("tooltip");
        b3.setTooltip(tooltipB1);
        Tooltip.install(b3, tooltipB3);

        TilePane tile = new TilePane();
        tile.setPrefColumns(3);
        tile.setHgap(35);
        tile.setPadding(new Insets(10,20,10,20));

        tile.getChildren().addAll(b1, b2, b3);

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

        Button b4 = new Button();
        b4.setGraphic(App.atlas.getSVG("Diode"));
        b4.setId("bouton");
        Tooltip tooltipB4 = new Tooltip("Diode");
        tooltipB4.setId("tooltip");
        b4.setTooltip(tooltipB4);
        Tooltip.install(b4, tooltipB4);

        Button b5 = new Button();
        b5.setGraphic(App.atlas.getSVG("DiodeLumiere"));
        b5.setId("bouton");
        Tooltip tooltipB5 = new Tooltip("LED");
        tooltipB5.setId("tooltip");
        b5.setTooltip(tooltipB5);
        Tooltip.install(b5, tooltipB5);

        Button b6 = new Button();
        b6.setGraphic(App.atlas.getSVG("Moteur"));
        b6.setId("bouton");
        Tooltip tooltipB6 = new Tooltip("Moteur");
        tooltipB6.setId("tooltip");
        b6.setTooltip(tooltipB6);
        Tooltip.install(b6, tooltipB6);

        Button b7 = new Button();
        b7.setGraphic(App.atlas.getSVG("Resisteur"));
        b7.setId("bouton");
        Tooltip tooltipB7 = new Tooltip("Résisteur");
        tooltipB7.setId("tooltip");
        b7.setTooltip(tooltipB7);
        Tooltip.install(b7, tooltipB7);

        Button b8 = new Button();
        b8.setGraphic(App.atlas.getSVG("ElementChauffant"));
        b8.setId("bouton");
        Tooltip tooltipB8 = new Tooltip("Élément chauffant");
        tooltipB8.setId("tooltip");
        b8.setTooltip(tooltipB8);
        Tooltip.install(b8, tooltipB8);

        Button b9 = new Button();
        b9.setGraphic(App.atlas.getSVG("Inductance"));
        b9.setId("bouton");
        Tooltip tooltipB9 = new Tooltip("Inductance");
        tooltipB9.setId("tooltip");
        b9.setTooltip(tooltipB9);
        Tooltip.install(b9, tooltipB9);

        Button b10 = new Button();
        b10.setGraphic(App.atlas.getSVG("Condensateur"));
        b10.setId("bouton");
        Tooltip tooltipB10 = new Tooltip("Condensateur");
        tooltipB10.setId("tooltip");
        b10.setTooltip(tooltipB10);
        Tooltip.install(b10, tooltipB10);

        Button b11 = new Button();
        b11.setGraphic(App.atlas.getSVG("MiseTerre"));
        b11.setId("bouton");
        Tooltip tooltipB11 = new Tooltip("Mise à la terre");
        tooltipB11.setId("tooltip");
        b11.setTooltip(tooltipB11);
        Tooltip.install(b11, tooltipB11);

        Button b12 = new Button();
        b12.setGraphic(App.atlas.getSVG("Fusible"));
        b12.setId("bouton");
        Tooltip tooltipB12 = new Tooltip("Fusible");
        tooltipB12.setId("tooltip");
        b12.setTooltip(tooltipB12);
        Tooltip.install(b12, tooltipB12);

        Button b13 = new Button();
        b13.setGraphic(App.atlas.getSVG("InterrupteurDouble"));
        b13.setId("bouton");
        Tooltip tooltipB13 = new Tooltip("Interrupteur double");
        tooltipB13.setId("tooltip");
        b13.setTooltip(tooltipB13);
        Tooltip.install(b13, tooltipB13);

        Button b14 = new Button();
        b14.setGraphic(App.atlas.getSVG("Commutateur"));
        b14.setId("bouton");
        Tooltip tooltipB14 = new Tooltip("Commutateur");
        tooltipB14.setId("tooltip");
        b14.setTooltip(tooltipB14);
        Tooltip.install(b14, tooltipB14);

        Button b15 = new Button();
        b15.setGraphic(App.atlas.getSVG("InterrupteurSimple"));
        b15.setId("bouton");
        Tooltip tooltipB15 = new Tooltip("Interrupteur simple");
        tooltipB15.setId("tooltip");
        b15.setTooltip(tooltipB15);
        Tooltip.install(b15, tooltipB15);

        Button b16 = new Button();
        b16.setGraphic(App.atlas.getSVG("Ampoule"));
        b16.setId("bouton");
        Tooltip tooltipB16 = new Tooltip("Ampoule");
        tooltipB16.setId("tooltip");
        b16.setTooltip(tooltipB16);
        Tooltip.install(b16, tooltipB16);

        Button b17 = new Button();
        b17.setGraphic(App.atlas.getSVG("Electrique"));
        b17.setId("bouton");
        Tooltip tooltipB17 = new Tooltip("Avertisseur sonore");
        tooltipB17.setId("tooltip");
        b17.setTooltip(tooltipB17);
        Tooltip.install(b17, tooltipB17);

        Button b18 = new Button();
        b18.setGraphic(App.atlas.getSVG("HautParleur"));
        b18.setId("bouton");
        Tooltip tooltipB18 = new Tooltip("Haut-parleur");
        tooltipB18.setId("tooltip");
        b18.setTooltip(tooltipB18);
        Tooltip.install(b18, tooltipB18);

        TilePane tile2 = new TilePane();
        tile2.setPrefColumns(3);
        tile2.setVgap(20);
        tile2.setHgap(30);
        tile2.setPadding(new Insets(10,20,10,20));
        tile2.setPrefTileWidth(80);
        tile2.setPrefTileHeight(50);
        tile2.getChildren().addAll(b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18);

        AnchorPane.setTopAnchor(tile2, 337.0 + 50); //Y
        AnchorPane.setLeftAnchor(tile2, 65.0); //X

        ArrayList<Tooltip> listeTooltip = new ArrayList<>();
        listeTooltip.addAll(Arrays.asList(tooltipB1, tooltipB2, tooltipB3, tooltipB4, tooltipB5, tooltipB6, tooltipB7, tooltipB8, tooltipB9, tooltipB10, tooltipB11, tooltipB12, tooltipB13, tooltipB14, tooltipB15, tooltipB16, tooltipB17, tooltipB18));

        for(int i = 0; i < listeTooltip.size(); i++){
            listeTooltip.get(i).setShowDelay(Duration.ZERO); //délai avant apparition
            listeTooltip.get(i).setHideDelay(Duration.seconds(0)); //délai avant disparition
            listeTooltip.get(i).setShowDuration(Duration.INDEFINITE); //durée d'affichage maximum
        }

        VBox vBox5 = new VBox();
        vBox5.setId("vBoxDeff");
        vBox5.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(vBox5, 782.0); //Y
        AnchorPane.setLeftAnchor(vBox5, 65.0); //X
        Label labelDeff = new Label("Survolez un matériel électrique pour voir sa description");
        labelDeff.setId("titreDeff");
        labelDeff.setWrapText(true);
        labelDeff.setTextAlignment(TextAlignment.CENTER);
        labelDeff.setAlignment(Pos.CENTER);
        vBox5.getChildren().add(labelDeff);

        Label sourceDC = new Label("Une source de courant continu fournit une tension ou un courant constant dans le temps, sans variation. Elle est utilisée pour représenter des batteries ou alimentations stabilisées. Dans une simulation, elle impose une valeur fixe indépendamment des variations du circuit. Le reste du circuit détermine le courant ou la tension complémentaire.");
        Label sourceAC = new Label("Une source de courant alternatif produit un signal qui varie périodiquement, souvent de forme sinusoïdale. Elle est définie par son amplitude, sa fréquence et sa phase. Elle est utilisée pour modéliser le courant du réseau électrique ou des signaux oscillants. En simulation, sa valeur change à chaque instant selon une fonction mathématique.");
        Label sourceIdeal = new Label("Une source de courant idéale impose un courant constant quelle que soit la tension à ses bornes. Elle peut théoriquement fournir une tension infinie pour maintenir ce courant. En simulation, elle injecte un courant fixe dans un nœud. Elle est utilisée pour simplifier l’analyse des circuits.");
        Label diode = new Label("Une diode est un composant qui ne laisse passer le courant que dans un seul sens. Elle bloque le courant en polarisation inverse sauf si une tension limite est dépassée. Dans une simulation, elle est modélisée avec un seuil de conduction ou une équation non linéaire. Elle sert à protéger ou redresser des signaux.");
        Label LED = new Label("Une LED est une diode qui émet de la lumière lorsqu’un courant la traverse. Elle possède une tension de seuil spécifique dépendant de sa couleur. En simulation, elle est modélisée comme une diode avec chute de tension constante. Elle nécessite souvent une résistance pour éviter un courant excessif.");
        Label moteur = new Label("Un moteur électrique transforme l’énergie électrique en énergie mécanique de rotation. Il génère une force contre-électromotrice qui dépend de sa vitesse. Dans une simulation, il est représenté par des éléments électriques et une source dépendante. Cela permet de modéliser son comportement dynamique.");
        Label resistance = new Label("Une résistance limite le passage du courant en dissipant de l’énergie sous forme de chaleur. Elle obéit à la loi d’Ohm qui relie tension, courant et résistance. En simulation, c’est un composant linéaire simple. Elle est utilisée pour contrôler les courants et tensions.");
        Label elementCHauffant = new Label("Un élément chauffant est une résistance conçue pour produire de la chaleur lorsqu’un courant la traverse. Il transforme l’énergie électrique en énergie thermique. En simulation, il est modélisé comme une résistance avec éventuellement une dépendance à la température. Il est utilisé dans les systèmes de chauffage.");
        Label inductance = new Label("Une inductance est un composant qui stocke l’énergie dans un champ magnétique. Elle s’oppose aux variations rapides du courant. En simulation, elle est décrite par une relation différentielle entre tension et courant. Elle est utilisée dans les filtres et circuits oscillants.");
        Label condensateur = new Label("Un condensateur stocke de l’énergie sous forme de champ électrique entre deux plaques. Il s’oppose aux variations rapides de tension. Dans une simulation, il est modélisé par une relation entre courant et dérivée de tension. Il est utilisé pour filtrer et stabiliser les signaux.");
        Label miseTerre = new Label("La mise à la terre est un point de référence électrique considéré comme zéro volt. Elle sert de base pour mesurer toutes les tensions du circuit. En simulation, elle est indispensable pour résoudre les équations. Elle peut aussi représenter une sécurité dans les circuits réels.");
        Label fusible = new Label("Un fusible est un dispositif de protection qui coupe le circuit lorsqu’un courant trop élevé le traverse. Il fonctionne en fondant lorsqu’un seuil de courant est dépassé. En simulation, il est modélisé comme un interrupteur dépendant du courant. Il protège les composants contre les surcharges.");
        Label interrupteurDouble = new Label("Un interrupteur double permet de contrôler deux circuits en même temps avec un seul mécanisme. Il peut inverser des connexions ou commuter des signaux. En simulation, il agit comme deux interrupteurs synchronisés. Il est souvent utilisé pour inverser une polarité.");
        Label commutateur = new Label("Un commutateur permet de diriger un signal d’une entrée vers plusieurs sorties possibles. Il sélectionne un chemin électrique parmi plusieurs options. En simulation, une seule connexion est active à la fois. Il est utilisé pour choisir entre différentes sources ou configurations.");
        Label interrupteurSimple = new Label("Un interrupteur simple ouvre ou ferme un circuit électrique. Lorsqu’il est fermé, le courant circule normalement. Lorsqu’il est ouvert, le circuit est interrompu. En simulation, il est modélisé comme une résistance très faible ou infinie.");
        Label ampoule = new Label("Une ampoule convertit l’énergie électrique en lumière, souvent avec une émission de chaleur. Elle se comporte comme une résistance non linéaire dépendante de la température. En simulation, elle peut être simplifiée en résistance. Elle sert d’indicateur visuel de fonctionnement.");
        Label avertisseurSonore = new Label("Un avertisseur sonore produit un son lorsqu’il est alimenté électriquement. Il peut être actif (son intégré) ou passif (nécessite un signal). En simulation, il est modélisé comme une charge ou une source sonore. Il est utilisé pour les alertes et signaux.");
        Label hautParleur = new Label("Un haut-parleur convertit un signal électrique en onde sonore. Il fonctionne grâce à une bobine qui déplace une membrane. En simulation, il est modélisé comme une résistance et une inductance. Il représente une sortie audio.");

        ArrayList<Label> listeLabel = new ArrayList<>();
        listeLabel.addAll(Arrays.asList(sourceDC, sourceAC, sourceIdeal, diode, LED, moteur, resistance, elementCHauffant, inductance, condensateur, miseTerre, fusible, interrupteurDouble, commutateur, interrupteurSimple, ampoule, avertisseurSonore, hautParleur));

        ArrayList<Button> listeBouttons = new ArrayList<>();
        listeBouttons.addAll(Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17, b18));

        for(int i = 0; i < listeBouttons.size(); i++){

            Button boutton = listeBouttons.get(i);
            Label label = listeLabel.get(i);

            label.setId("labelDeff");
            label.setWrapText(true);
            label.setTextAlignment(TextAlignment.CENTER);
            label.setAlignment(Pos.CENTER);

            boutton.setOnMouseEntered(e -> {
                vBox5.getChildren().setAll(label);
                vBox5.setId("vBoxDeffComposante");

            });

            boutton.setOnMouseExited(e -> {
                vBox5.getChildren().setAll(labelDeff);
                vBox5.setId("vBoxDeff");
            });
        }

        root.getChildren().addAll(vBox1, vBox2, vBox3, vBox4, vBoxSeparateur1, vBoxSeparateur2, tile, tile2, vBox5);
    }
}
