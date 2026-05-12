module ca.qc.bdeb.sim.elekflow.UI {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.girod.javafx.svgimage;
    requires json.simple;
    requires javafx.graphics;
    requires java.desktop;
    requires ejml.core;

    exports ca.qc.bdeb.sim.elekflow.UI;
    exports ca.qc.bdeb.sim.elekflow.Logique;
    exports ca.qc.bdeb.sim.elekflow.UI.Scene;
    exports ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;
    exports ca.qc.bdeb.sim.elekflow.UI.Utils;
}