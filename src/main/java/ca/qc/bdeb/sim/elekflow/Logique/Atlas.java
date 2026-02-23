package ca.qc.bdeb.sim.elekflow.Logique;

import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import org.girod.javafx.svgimage.xml.parsers.SVGParsingException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

public class Atlas {
   private final HashMap<String, SVGImage> LIST_SVG = new HashMap<>();

    public void loadSvgs() throws IOException {
        String svgFolderPath = "./src/main/resources/ca/qc/bdeb/sim/elekflow/SVGs";
        File f = new File(svgFolderPath);
        for (String i : Objects.requireNonNull(f.list())){
            if(i.endsWith(".svg")){
                LIST_SVG.put(i.substring(0, i.length()-4), SVGLoader.load(Files.readString(Path.of(svgFolderPath + "/" + i))));
            }
        }
    }

    public SVGImage getSVG(String key){
        return LIST_SVG.get(key);
    }
}
