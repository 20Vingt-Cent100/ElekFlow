package ca.qc.bdeb.sim.elekflow.UI;
import ca.qc.bdeb.sim.elekflow.Logique.Atlas;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static Atlas atlas;
    private final boolean isDebugMode = false;

    private static Stage stage;
    private Pane root;


    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        loadAtlas();
        setLoggeur(NiveauLog.TOTAL);

        setScene(StartupScene.createStartupScene());

        setStage();
    }

    private void loadAtlas(){
        atlas = new Atlas();
        atlas.loadSvgs();
        atlas.loadImgs();
    }
    private void setLoggeur(NiveauLog niveauLog){
        Loggeur.changerNiveauLog(niveauLog);
    }

    private void setSimulationScene(){
        root = new Pane();

        Scene app = new Scene(root,1920, 1080);
        app.getStylesheets().add(
                Objects.requireNonNull(getClass()
                                .getResource("/ca/qc/bdeb/sim/elekflow/stylesheet.css"))
                        .toString()
        );

        root.setBackground(new Background(new BackgroundFill(Color.web("D5DFE5"), null, null)));

        setScene(app, WindowMode.MAXIMISED);
    }

    private void setStage(){
        stage.getIcons().add(atlas.getIMG("iconDark"));
        stage.setFullScreenExitHint("");
        stage.getIcons().add(atlas.getIMG("iconLight"));
        stage.setTitle("ElekFlow");
        stage.show();

        stage.setOnCloseRequest((e) ->{
            Platform.exit();
        });
    }

    public static void setScene(Scene scene){
        stage.setScene(scene);
        setHandles(scene);
    }

    public static void setScene(Scene scene, WindowMode mode){
        stage.setScene(scene);

        switch (mode){
            case FULLSCREEN -> stage.setFullScreen(true);
            case WINDOWED -> {stage.setFullScreen(false); stage.setMaximized(false);}
            case MAXIMISED -> {stage.setMaximized(true);}
        }

        setHandles(scene);
    }

    private static void setHandles(Scene scene){}

    public static void main(String[] args) {
        launch();
    }
}