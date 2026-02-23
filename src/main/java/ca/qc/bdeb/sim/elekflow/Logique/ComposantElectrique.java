package ca.qc.bdeb.sim.elekflow.Logique;

import java.util.ArrayList;

public abstract class ComposantElectrique {
    final ArrayList<Noeud> LISTE_NOEUDS = new ArrayList<>();
    double potentielElectrique;
    double intensiteCourant;
    double resistance;

    public ComposantElectrique(double resistanceParDefaut){
        resistance = resistanceParDefaut;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public boolean lierNoeud(int index, Noeud n){
        try{
            LISTE_NOEUDS.set(index, n);
            return true;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }
}
