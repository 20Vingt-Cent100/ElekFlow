package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.util.HashMap;

public class BouttonComposant extends Button {
    public final static HashMap<String, String> DESCRIPTIONS = new HashMap<>();

    //TODO: Permettre la modification et l'ajout par un fichier
    public static void setLabels(){
        DESCRIPTIONS.put("Default",
                """
                Survolez un matériel électrique pour voir sa description
                """);

        DESCRIPTIONS.put("SourceCourantContinu",
                """
                Une source de courant continu fournit une tension ou un courant constant dans le temps, 
                sans variation. Elle est utilisée pour représenter des batteries ou alimentations stabilisées.
                Dans une simulation, elle impose une valeur fixe indépendamment des variations du circuit.
                Le reste du circuit détermine le courant ou la tension complémentaire.
                """);

        DESCRIPTIONS.put("SourceCourantAlternatif",
                """
                Une source de courant alternatif produit un signal qui varie périodiquement,
                souvent de forme sinusoïdale. Elle est définie par son amplitude,
                sa fréquence et sa phase.
                Elle est utilisée pour modéliser le courant du réseau électrique ou des signaux oscillants.
                En simulation, sa valeur change à chaque instant selon une fonction mathématique.
                """);

        DESCRIPTIONS.put("SourceIntensiteCourant",
                """
                Une source de courant idéale impose un courant constant quelle que soit la tension à ses bornes.
                Elle peut théoriquement fournir une tension infinie pour maintenir ce courant.
                En simulation, elle injecte un courant fixe dans un nœud.
                Elle est utilisée pour simplifier l’analyse des circuits.
                """);

        DESCRIPTIONS.put("Diode",
                """
                Une diode est un composant qui ne laisse passer le courant que dans un seul sens.
                Elle bloque le courant en polarisation inverse sauf si une tension limite est dépassée.
                Dans une simulation, elle est modélisée avec un seuil de conduction ou une équation non linéaire.
                Elle sert à protéger ou redresser des signaux.
                """);

        DESCRIPTIONS.put("Led",
                """
                Une LED est une diode qui émet de la lumière lorsqu’un courant la traverse.
                Elle possède une tension de seuil spécifique dépendant de sa couleur.
                En simulation, elle est modélisée comme une diode avec chute de tension constante.
                Elle nécessite souvent une résistance pour éviter un courant excessif.
                """);

        DESCRIPTIONS.put("Moteur",
                """
                Un moteur électrique transforme l’énergie électrique en énergie mécanique de rotation.
                Il génère une force contre-électromotrice qui dépend de sa vitesse.
                Dans une simulation, il est représenté par des éléments électriques et une source dépendante.
                Cela permet de modéliser son comportement dynamique.
                """);

        DESCRIPTIONS.put("Résistance",
                """
                Une résistance limite le passage du courant en dissipant de l’énergie sous forme de chaleur.
                Elle obéit à la loi d’Ohm qui relie tension, courant et résistance.
                En simulation, c’est un composant linéaire simple.
                Elle est utilisée pour contrôler les courants et tensions.
                """);

        DESCRIPTIONS.put("ElementChauffant",
                """
                Un élément chauffant est une résistance conçue pour produire de la chaleur lorsqu’un courant la traverse.
                Il transforme l’énergie électrique en énergie thermique.
                En simulation, il est modélisé comme une résistance avec éventuellement une dépendance à la température.
                Il est utilisé dans les systèmes de chauffage.
                """);

        DESCRIPTIONS.put("Inductance",
                """
                Une inductance est un composant qui stocke l’énergie dans un champ magnétique.
                Elle s’oppose aux variations rapides du courant.
                En simulation, elle est décrite par une relation différentielle entre tension et courant.
                Elle est utilisée dans les filtres et circuits oscillants.
                """);

        DESCRIPTIONS.put("Condensateur",
                """
                Un condensateur stocke de l’énergie sous forme de champ électrique entre deux plaques.
                Il s’oppose aux variations rapides de tension. Dans une simulation,
                il est modélisé par une relation entre courant et dérivée de tension.
                Il est utilisé pour filtrer et stabiliser les signaux.
                """);

        DESCRIPTIONS.put("MiseALaTerre",
                """
                La mise à la terre est un point de référence électrique considéré comme zéro volt.
                Elle sert de base pour mesurer toutes les tensions du circuit.
                En simulation, elle est indispensable pour résoudre les équations.
                Elle peut aussi représenter une sécurité dans les circuits réels.
                """);

        DESCRIPTIONS.put("Fusible",
                """
                Un fusible est un dispositif de protection qui coupe le circuit lorsqu’un courant trop élevé le traverse.
                Il fonctionne en fondant lorsqu’un seuil de courant est dépassé.
                En simulation, il est modélisé comme un interrupteur dépendant du courant.
                Il protège les composants contre les surcharges.
                """);

        DESCRIPTIONS.put("InterrupteurDouble",
                """
                Un interrupteur double permet de contrôler deux circuits en même temps avec un seul mécanisme.
                Il peut inverser des connexions ou commuter des signaux.
                En simulation, il agit comme deux interrupteurs synchronisés.
                Il est souvent utilisé pour inverser une polarité.
                """);

        DESCRIPTIONS.put("Commutateur",
                """
                Un commutateur permet de diriger un signal d’une entrée vers plusieurs sorties possibles.
                Il sélectionne un chemin électrique parmi plusieurs options. En simulation,
                une seule connexion est active à la fois.
                Il est utilisé pour choisir entre différentes sources ou configurations.
                """);

        DESCRIPTIONS.put("InterrupteurSimple",
                """
                Un interrupteur simple ouvre ou ferme un circuit électrique.
                Lorsqu’il est fermé, le courant circule normalement.
                Lorsqu’il est ouvert, le circuit est interrompu.
                En simulation, il est modélisé comme une résistance très faible ou infinie.
                """);

        DESCRIPTIONS.put("Ampoule",
                """
                Une ampoule convertit l’énergie électrique en lumière, souvent avec une émission de chaleur.
                Elle se comporte comme une résistance non linéaire dépendante de la température.
                En simulation, elle peut être simplifiée en résistance.
                Elle sert d’indicateur visuel de fonctionnement.
                """);

        DESCRIPTIONS.put("AvertisseurSonore",
                """
                Un avertisseur sonore produit un son lorsqu’il est alimenté électriquement.
                Il peut être actif (son intégré) ou passif (nécessite un signal).
                En simulation, il est modélisé comme une charge ou une source sonore.
                Il est utilisé pour les alertes et signaux.
                """);

        DESCRIPTIONS.put("HautParleur",
                """
                Un haut-parleur convertit un signal électrique en onde sonore.
                Il fonctionne grâce à une bobine qui déplace une membrane.
                En simulation, il est modélisé comme une résistance et une inductance.
                Il représente une sortie audio.
                """);
    }

    private final Text DESCRIPTION = new Text();

    public BouttonComposant(String key, String tooltipStr){
        super("");

        this.getStyleClass().add("btn-composant");

        this.setGraphic(App.atlas.getSVG(key));

        var tooltip = new Tooltip(tooltipStr);
        tooltip.getStyleClass().add("tooltip");
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setHideDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);

        this.setTooltip(tooltip);

        DESCRIPTION.setText(DESCRIPTIONS.get(key));

        setHandles();
    }

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

    private void handleOnKeyPressed(KeyEvent keyEvent) {
    }

    private void handleOnMouseDragExited(MouseDragEvent mouseDragEvent) {
    }

    private void handleOnMouseDragReleased(MouseDragEvent mouseDragEvent) {
    }

    private void handleOnMouseExited(MouseEvent mouseEvent) {
    }

    private void handleOnMouseReleased(MouseEvent mouseEvent) {
    }

    private void handleOnMousePressed(MouseEvent mouseEvent) {
    }

    private void handleOnZoom(ZoomEvent zoomEvent) {
    }

    private void handleOnScroll(ScrollEvent event) {
    }

    private void handleOnMouseClicked(MouseEvent mouseEvent) {
    }

    private void handleOnMouseEntered(MouseEvent mouseEvent) {
        setCursor(Cursor.HAND);
    }

    private void handleOnMouseDragged(MouseEvent mouseEvent) {

    }
}
