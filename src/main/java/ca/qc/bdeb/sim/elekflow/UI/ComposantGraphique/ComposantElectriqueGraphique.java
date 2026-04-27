package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.Utils.JsonCles;
import com.github.cliftonlabs.json_simple.JsonObject;

public class ComposantElectriqueGraphique {
    private final String NOM;
    private final String CLE_SVG;
    private final String CATEGORY;
    private final String DESCRIPTION;
    private final boolean SVGFILL;

    public ComposantElectriqueGraphique(JsonObject obj){
        this.NOM = obj.getString(JsonCles.NOM);
        this.CLE_SVG = obj.getString(JsonCles.CLE_SVG);
        this.CATEGORY = obj.getString(JsonCles.CATEGORIES);
        this.DESCRIPTION = obj.getString(JsonCles.DESCRIPTION);
        this.SVGFILL = obj .getBoolean(JsonCles.SVGFILL);

        Loggeur.logConsole("The electrical component " + this + " was created", NiveauLog.TOTAL);
    }

    @Override
    public String toString(){
        return String.format("{\"nom\" = \"%s\", \"cle-svg\" = \"%s\", \"categories\" = \"%s\", \"description\" = \"%s\", \"svg-fill\" = \"%b\"}",
                NOM, CLE_SVG, CATEGORY, DESCRIPTION, SVGFILL);
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

    public boolean isSVGFILL() {
        return SVGFILL;
    }
}
