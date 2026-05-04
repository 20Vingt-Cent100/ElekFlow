package ca.qc.bdeb.sim.elekflow.UI.Utils;

import com.github.cliftonlabs.json_simple.JsonKey;

public enum JsonCles implements JsonKey {
    NOM("nom"),
    CLE_SVG("cle_svg"),
    CATEGORIES("category"),
    DESCRIPTION("description");

    private final String CLE;

    JsonCles(String cle){
        this.CLE = cle;
    }

    @Override
    public String getKey() {
        return CLE;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
