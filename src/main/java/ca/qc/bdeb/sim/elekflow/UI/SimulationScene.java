package ca.qc.bdeb.sim.elekflow.UI;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.File;
import java.util.Objects;

public class SimulationScene extends Scene {
    private WindowMode mode = WindowMode.FULLSCREEN;

    public static SimulationScene creerSceneDeSimulation(File projectFile){
        var root = new Pane();

        return new SimulationScene(root, 1920, 1080);
    }

    private SimulationScene(Parent root, double width, double height) {
        super(root, width, height);


        this.getStylesheets().add(
                Objects.requireNonNull(getClass()
                                .getResource("/ca/qc/bdeb/sim/elekflow/stylesheet.css"))
                        .toString()
        );
    }
}
