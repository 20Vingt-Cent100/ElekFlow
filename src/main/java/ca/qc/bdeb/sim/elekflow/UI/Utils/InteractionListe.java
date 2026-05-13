package ca.qc.bdeb.sim.elekflow.UI.Utils;

import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;
import javafx.event.Event;
import javafx.scene.shape.Path;
import javafx.scene.shape.SVGPath;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.HashMap;
import java.util.Set;

import static javafx.scene.paint.Color.RED;

public class InteractionListe {
    private static final HashMap<String, InteractionComposant> LISTE_INTERACTIONS = new HashMap<>();

    public static void loadListe(){
        LISTE_INTERACTIONS.put("InterrupteurSimple", (event, vue) -> {
            double isFemreValue = vue.getComposantElecGraphique().getPROPRIETES()[0].getValue();
            vue.getComposantElecGraphique().getPROPRIETES()[0].setValue(isFemreValue == 0.0 ? 1.0 : 0.0);


            SVGPath bras = (SVGPath)vue.lookup(".bras");

            bras.setRotate(bras.getRotate() == 0 ? 20 : 0);
            bras.setTranslateY(bras.getTranslateY() == 0 ? 5 : 0);
            App.atlas.getAudioClip("click").play();
        });
        LISTE_INTERACTIONS.put("Commutateur", (event, vue) -> {
            double isFemreValue = vue.getComposantElecGraphique().getPROPRIETES()[0].getValue();
            vue.getComposantElecGraphique().getPROPRIETES()[0].setValue(isFemreValue == 1.0 ? 2.0 : 1.0);


            SVGPath bras = (SVGPath)vue.lookup(".bras");

            bras.setRotate(bras.getRotate() == 0 ? 22 : 0);
            bras.setTranslateY(bras.getTranslateY() == 0 ? 5 : 0);
            App.atlas.getAudioClip("click").play();
        });
        LISTE_INTERACTIONS.put("InterrupteurDouble", (event, vue) -> {
            double isFemreValue = vue.getComposantElecGraphique().getPROPRIETES()[0].getValue();
            vue.getComposantElecGraphique().getPROPRIETES()[0].setValue(isFemreValue == 0.0 ? 1.0 : 0.0);


            vue.lookupAll(".bras").forEach((bras)->{
                bras.setRotate(bras.getRotate() == 0 ? 20 : 0);
                bras.setTranslateY(bras.getTranslateY() == 0 ? 5 : 0);
                App.atlas.getAudioClip("click").play();
            });

            var midPart = vue.lookup(".mid-part");
            midPart.setTranslateY(midPart.getTranslateY() == 0 ? 5 : 0);

        });
    }

    public static InteractionComposant getInteraction(String CLE_SVG) {
        return LISTE_INTERACTIONS.get(CLE_SVG);
    }
}
