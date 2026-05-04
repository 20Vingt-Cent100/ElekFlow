package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Utils.JsonCles;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;

public class ComposantJSON {
    private final String NOM;
    private final String CLE_SVG;
    private final String CATEGORY;
    private final String DESCRIPTION;
    private final BigDecimal[][] BORNES;

    public ComposantJSON(JsonObject obj){
        this.NOM = obj.getString(JsonCles.NOM);
        this.CLE_SVG = obj.getString(JsonCles.CLE_SVG);
        this.CATEGORY = obj.getString(JsonCles.CATEGORIES);
        this.DESCRIPTION = obj.getString(JsonCles.DESCRIPTION);

        JsonArray bornes = obj.getCollection(JsonCles.BORNES);

        if (bornes != null){
            BORNES = new BigDecimal[bornes.size()][];

            for (int i = 0; i < bornes.size(); i++) {
                JsonArray borne = (JsonArray) bornes.get(i);

                BORNES[i] = new BigDecimal[]{(BigDecimal) borne.getFirst(), (BigDecimal) borne.getLast()};
            }
        }else{
            BORNES = null;
        }

        Loggeur.logConsole("The electrical component " + this + " was created", NiveauLog.TOTAL);
    }

    @Override
    public String toString(){
        return String.format("{\"nom\" = \"%s\", \"cle-svg\" = \"%s\", \"categories\" = \"%s\", \"description\" = \"%s\"}",
                NOM, CLE_SVG, CATEGORY, DESCRIPTION);
    }

    public String getCLE_SVG() {
        return CLE_SVG;
    }

    public String getNOM() {
        return NOM;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public BigDecimal[][] getBORNES() {
        return BORNES;
    }
}
