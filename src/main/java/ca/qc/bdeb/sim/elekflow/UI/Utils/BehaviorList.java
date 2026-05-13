package ca.qc.bdeb.sim.elekflow.UI.Utils;

import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.util.HashMap;

import static javafx.scene.paint.Color.YELLOW;

public class BehaviorList {
    private static final HashMap<String, Behavior> LISTE_BEHAVIOR = new HashMap<>();

    public static void loadBehaviors(){
        LISTE_BEHAVIOR.put("Ampoule", ((event, vue) -> {
            if(vue.getCurrent() != 0){
                ((SVGPath)vue.lookup("current")).setFill(YELLOW);
            }else{
                ((SVGPath)vue.lookup("current")).setFill(Color.TRANSPARENT);
            }
        }));
    }

    public static Behavior getBehavior(String key){
        return LISTE_BEHAVIOR.get(key);
    }
}
