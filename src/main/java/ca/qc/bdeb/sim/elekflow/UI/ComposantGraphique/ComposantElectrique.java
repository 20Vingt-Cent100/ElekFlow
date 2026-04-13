package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Utils.JsonCles;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.util.Map;

public class ComposantElectrique {
    private final String NOM;
    private final String CLE_SVG;
    //private final Map<String, String> CATEGORIES;
    private final String DESCRIPTION;
    private final boolean SVGFILL;

    public ComposantElectrique(JsonObject obj){
        this.NOM = obj.getString(JsonCles.NOM);
        this.CLE_SVG = obj.getString(JsonCles.CLE_SVG);
        this.DESCRIPTION = obj.getString(JsonCles.DESCRIPTION);
        this.SVGFILL = obj .getBoolean(JsonCles.SVGFILL);

        Loggeur.logConsole("The electrical component " + this + " was created", NiveauLog.TOTAL);
    }

    @Override
    public String toString(){
        return String.format("{\"nom\" = \"%s\", \"cle-svg\" = \"%s\", \"description\" = \"%s\", \"svg-fill\" = \"%b\"}",
                NOM, CLE_SVG, DESCRIPTION, SVGFILL);
    }
}
