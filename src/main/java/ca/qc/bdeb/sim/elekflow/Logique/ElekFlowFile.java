package ca.qc.bdeb.sim.elekflow.Logique;

import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.file.Path;
import java.util.Scanner;

public class ElekFlowFile {
    public static boolean createNewFile(String fileName, Path path) throws IOException {
        File projectFile = new File(path.toAbsolutePath() + "/" + fileName);

        return projectFile.createNewFile();
    }

    public static String[][] loadProjetsList(){
        File projectFile = new File("projets/ProjectPaths");

        if(projectFile.exists()) {
            try {
                var scanner = new Scanner(projectFile);
                while (scanner.hasNext()) {

                }
            } catch (FileNotFoundException ex) {
                Loggeur.logConsole("File was not found", NiveauLog.ERREUR);
            }
        }
        else {
            Loggeur.logConsole("The project's path file doesn't exist", NiveauLog.ALERTE);
            try {
                projectFile.createNewFile();
            }catch (IOException ex){
                Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            }catch (SecurityException ex) {
                Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            }
        }

        return null;
    }


}
