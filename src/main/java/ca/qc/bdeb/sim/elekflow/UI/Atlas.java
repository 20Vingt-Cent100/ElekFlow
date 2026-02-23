package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import org.girod.javafx.svgimage.xml.parsers.SVGParsingException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public class Atlas {
   private final HashMap<String, SVGImage> LIST_SVG = new HashMap<>();

    public void loadSvgs(){
        String svgFolderPath = "./src/main/resources/ca/qc/bdeb/sim/elekflow/SVGs";
        File f = new File(svgFolderPath);
        for (String i : Objects.requireNonNull(f.list())){
            if(i.endsWith(".svg")){

                try {
                    LIST_SVG.put(i.substring(0, i.length()-4), SVGLoader.load(svgFolderPath + "/" + i));
                }catch (SVGParsingException e){
                    Loggeur.logConsole("Le fichier " + i + " n'a pas pus etre cree", NiveauLog.ERREUR);
                    continue;
                }
                Loggeur.logConsole("Le fichier " + i + " a ete cree.", NiveauLog.TOTAL);
            }
        }
    }

    public SVGImage getSVG(String key){
        return LIST_SVG.get(key);
    }
}
