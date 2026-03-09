package ca.qc.bdeb.sim.elekflow.Logique;

import javafx.scene.image.Image;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class Atlas {
   private final HashMap<String, SVGImage> LIST_SVGs = new HashMap<>();
   private final HashMap<String, Image> LIST_IMGs = new HashMap<>();

   private final String RESOURCE_PATH = "./src/main/resources/ca/qc/bdeb/sim/elekflow/";

    public void loadSvgs(){
        final String SVGs_FOLDER_PATH = RESOURCE_PATH + "SVGs";
        File f = new File(SVGs_FOLDER_PATH);
        for (String i : f.list()){
            if(i.endsWith(".svg")) {
                try {
                    LIST_SVGs.put(i.substring(0, i.length() - 4), SVGLoader.load(Files.readString(Path.of(SVGs_FOLDER_PATH + "/" + i))));
                }catch (IOException e){
                    Loggeur.logConsole(e.getMessage(), NiveauLog.ERREUR);
                }
            }
        }
    }

    public void loadImgs(){
        final String IMGs_FOLDER_PATH = RESOURCE_PATH + "IMGs";
        File f = new File(IMGs_FOLDER_PATH);
        for (String i : f.list()){
            LIST_IMGs.put(i.substring(0, i.indexOf(".")), new Image(IMGs_FOLDER_PATH + "/"  + i));
        }
    }

    public SVGImage getSVG(String key){
        return LIST_SVGs.get(key);
    }

    public SVGImage getSVG(String key, double scale){
        return LIST_SVGs.get(key).scale(scale);
    }

    public Image getIMG(String key){
        return LIST_IMGs.get(key);
    }
}
