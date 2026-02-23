package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Atlas;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGImage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();
        Scene scene = new Scene(root,320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        Loggeur.changerNiveauLog(NiveauLog.TOTAL);
        var atlas = new Atlas();
        atlas.loadSvgs();
        atlas.loadImgs();
    }

    public static void main(String[] args) {
        launch();
    }
}