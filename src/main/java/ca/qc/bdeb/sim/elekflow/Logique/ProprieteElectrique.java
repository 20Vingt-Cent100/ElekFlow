package ca.qc.bdeb.sim.elekflow.Logique;

public class ProprieteElectrique {
    private final String propertyName;
    private double value;

    public ProprieteElectrique(String propertyName, double value){
        this.propertyName = propertyName;
        this.value = value;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
