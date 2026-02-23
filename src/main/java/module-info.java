module ca.qc.bdeb.sim.elekflow.UI {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.girod.javafx.svgimage;


    exports ca.qc.bdeb.sim.elekflow.UI;
    opens ca.qc.bdeb.sim.elekflow.UI to javafx.fxml;
    exports ca.qc.bdeb.sim.elekflow.Logique;
    opens ca.qc.bdeb.sim.elekflow.Logique to javafx.fxml;
}