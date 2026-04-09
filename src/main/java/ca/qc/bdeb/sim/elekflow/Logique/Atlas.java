package ca.qc.bdeb.sim.elekflow.Logique;

import javafx.scene.Node;
import javafx.scene.image.Image;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

public class Atlas {
   private final HashMap<String, SVGImage> LIST_SVGs = new HashMap<>();
   private final HashMap<String, Image> LIST_IMGs = new HashMap<>();
   private final HashMap<String, String> LIST_STYLESHEETS = new HashMap<>();

   private final String DEFAULT_FOLDER_PATH = "./src/main/resources/";
   private final String RESOURCE_PATH = "ca/qc/bdeb/sim/elekflow/";

    public void loadSvgs(){
        final String SVGs_FOLDER_PATH = DEFAULT_FOLDER_PATH + RESOURCE_PATH + "SVGs";
        File f = new File(SVGs_FOLDER_PATH);
        for (String i : f.list()){
            if(i.endsWith(".svg")) {
                try {
                    LIST_SVGs.put(i.substring(0, i.length() - 4),
                            SVGLoader.load(Files.readString(Path.of(SVGs_FOLDER_PATH + "/" + i))));
                }catch (IOException e){
                    Loggeur.logConsole(e.getCause() + e.getMessage(), NiveauLog.ERREUR);
                }
            }
        }
    }

    public void loadImgs(){
        final String IMGs_FOLDER_PATH = DEFAULT_FOLDER_PATH + RESOURCE_PATH + "IMGs";
        File f = new File(IMGs_FOLDER_PATH);
        for (String i : f.list()){
            try{
                LIST_IMGs.put(
                        i.substring(0, i.indexOf(".")),
                        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/" + RESOURCE_PATH + "IMGs/" + i)))
                );
            }catch (NullPointerException ex){
                Loggeur.logConsole(ex.getCause() + ex.getMessage(), NiveauLog.ERREUR);
            }

        }
    }

    public void loadStylesheets(){
        final String style_FOLDER_PATH = DEFAULT_FOLDER_PATH + RESOURCE_PATH + "stylesheets";
        File f = new File(style_FOLDER_PATH);
        for (String i : f.list()){
            if(i.endsWith(".css")) {
                try {
                    LIST_STYLESHEETS.put(i.substring(0, i.length() - 4),
                            Objects.requireNonNull(getClass().getResource("/" + RESOURCE_PATH + "stylesheets/" + i)).toExternalForm());
                }catch (Exception e){
                    Loggeur.logConsole(e.getCause() + e.getMessage(), NiveauLog.ERREUR);
                }
            }
        }
    }

    /**
     *Get an SVGImage corresponding to the key
     * @param key Name of the svg (without the extension)
     * @return The SVGImage associated to the key inputted
     */
    public SVGImage getSVG(String key){
        return LIST_SVGs.get(key);
    }

    public SVGImage getSVG(String key, double scale){
        return LIST_SVGs.get(key).scale(scale);
    }

    public Image getIMG(String key){
        return LIST_IMGs.get(key);
    }

    public String getStylesheet(String key){
        return LIST_STYLESHEETS.get(key);
    }
}
