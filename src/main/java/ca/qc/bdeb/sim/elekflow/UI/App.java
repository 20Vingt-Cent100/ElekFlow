package ca.qc.bdeb.sim.elekflow.UI;
import ca.qc.bdeb.sim.elekflow.Logique.Atlas;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;

public class App extends Application {
    public static Atlas atlas;
    private static final HashMap<String, ElekFlowStage> STAGES = new HashMap<>();


    /**
     *Point de départ de l'application
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        //Configurations initiales
        loadAtlas();
        setLoggeur(NiveauLog.TOTAL);

        //Ajout du stage à la liste de stages de l'application
        addStage(ElekFlowStage.createStage("ElekFlow Project Manager", atlas.getIMG("LogoDark"), true),
                "Primaire",
                false);

        changeScene(new SimulationScene(1920, 1080, WindowMode.MAXIMISED), "Primaire");
        getStage("Primaire").setShow(true);

    }

    /**
     * Charge en mémoire toutes les images et SVGs présent dans les dossiers IMGs et SVGs
     */
    private void loadAtlas(){
        atlas = new Atlas();
        atlas.loadSvgs();
        atlas.loadImgs();
        atlas.loadStylesheets();
    }

    /**
     * Permet de définir quel niveau de logs est voulu
     * @param niveauLog Niveau de messages à écrire dans la console
     */
    private void setLoggeur(NiveauLog niveauLog){
        Loggeur.changerNiveauLog(niveauLog);
        Loggeur.logConsole("Logger was set to: " + niveauLog.toString(), NiveauLog.TOTAL);
    }


    /**
     * Permet d'ajouté un stage à l'application
     * @param stage Stage à ajouter au dictionnaire des Stages de l'application
     * @param cle Clé pour récupérer un stage depuis le dictionnaire
     * @param show Affiche, ou non, le stage à l'écran
     */
    public static void addStage(ElekFlowStage stage, String cle, boolean show){
        STAGES.put(cle, stage);
        stage.setShow(show);
    }

    /**
     * Obtenir un stage grâce à sa clé
     * @param cle
     * @return le stage associé à la clé
     */
    public static ElekFlowStage getStage(String cle){
        return STAGES.get(cle);
    }

    /**
     * Permet de changer de scène
     * @param scene Scene à affiché
     * @param stageKey clé associé au stage dont l'on veut changer la scène
     */
    public static void changeScene(ElekflowScene scene, String stageKey){
        changeScene(scene, stageKey, WindowMode.WINDOWED);
    }

    /**
     * Permet de changer de scène
     * @param scene Scene à affiché
     * @param stageKey clé associé au stage dont l'on veut changer la scène
     * @param mode Mode d'affichage à l'écran (Fullscreen, Maximized, Windowed)
     */
    public static void changeScene(ElekflowScene scene, String stageKey, WindowMode mode){
        Stage stage = STAGES.get(stageKey);
        stage.setScene(scene);

        switch (mode){
            case FULLSCREEN -> stage.setFullScreen(true);
            case WINDOWED -> {stage.setFullScreen(false); stage.setMaximized(false);}
            case MAXIMISED -> {stage.setMaximized(true);}
        }

        setHandles(scene);
    }

    private static void setHandles(Scene scene){}

    /**
     * Point d'entré du projet
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}