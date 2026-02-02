module ca.qc.bdeb.sim.elekflow.UI {
    requires javafx.controls;
    requires javafx.fxml;


    exports ca.qc.bdeb.sim.elekflow.UI;
    opens ca.qc.bdeb.sim.elekflow.UI to javafx.fxml;
}