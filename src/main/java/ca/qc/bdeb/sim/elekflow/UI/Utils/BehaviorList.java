package ca.qc.bdeb.sim.elekflow.UI.Utils;

import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.VueComposantElectrique;
import javafx.event.Event;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.HashMap;

import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.YELLOW;

public class BehaviorList {
    private static final HashMap<String, Behavior> LISTE_BEHAVIOR = new HashMap<>();

    public static void loadBehaviors(){
        LISTE_BEHAVIOR.put("Ampoule", new Behavior() {
            @Override
            public void asCurrent(Event event, VueComposantElectrique vue) {
                ((SVGPath)vue.lookup(".current")).setFill(YELLOW);
            }

            @Override
            public void asNoCurrent(Event event, VueComposantElectrique vue) {
                ((SVGPath)vue.lookup(".current")).setFill(TRANSPARENT);
            }
        });
    }

    public static Behavior getBehavior(String key){
        return LISTE_BEHAVIOR.get(key);
    }
}
