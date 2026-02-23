package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Atlas;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGImage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Atlas atlas = new Atlas();
        atlas.loadSvgs();
        atlas.loadImgs();

        AnchorPane root = new AnchorPane();
        root.setId("mainBox");

        VBox vBox1 = new VBox();
        vBox1.setId("mainVBox");

        AnchorPane.setTopAnchor(vBox1, 40.0); //Y
        AnchorPane.setLeftAnchor(vBox1, 40.0); //X

        VBox vBox2 = new VBox();
        vBox2.setId("vBoxRecherche");

        AnchorPane.setTopAnchor(vBox2, 62.0); //Y
        AnchorPane.setLeftAnchor(vBox2, 65.0); //X


        Label titre = new Label("\uD83D\uDD0C Matériel életique");
        titre.setId("mainTitre");
        titre.setPadding(new Insets(11.14, 0, 0, 10.33));

        vBox2.getChildren().addAll(titre);

        VBox vBox3 = new VBox();
        vBox3.setId("vBoxRechercheSources");

        AnchorPane.setTopAnchor(vBox3, 134.0); //Y
        AnchorPane.setLeftAnchor(vBox3, 65.0); //X

        VBox vBox4 = new VBox();
        vBox4.setId("vBoxRechercheComposantes");

        AnchorPane.setTopAnchor(vBox4, 393.0); //Y
        AnchorPane.setLeftAnchor(vBox4, 65.0); //X

        root.getChildren().addAll(vBox1, vBox2, vBox3, vBox4);

        Scene scene = new Scene(root,320, 240);
        scene.getStylesheets().add(getClass().getResource("/ca/qc/bdeb/sim/elekflow/stylesheet.css").toExternalForm());

        stage.setTitle("Simulation de circuits électriques");
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}