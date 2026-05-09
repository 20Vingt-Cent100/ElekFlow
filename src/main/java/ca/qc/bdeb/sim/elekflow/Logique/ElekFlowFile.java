package ca.qc.bdeb.sim.elekflow.Logique;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class ElekFlowFile {
    private final static File projects = new File("./projets");

    public static void createNewFile(String fileName, Path path){
        File projectFile = new File(path.toAbsolutePath() + "/" + fileName + ".elk");

        try {

            projectFile.createNewFile();
            addToRecentProject(projectFile.toPath());

        }catch (IOException ex){
            Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
        }

    }

    public static void addToRecentProject(Path path){
        try{
            File projetRecent = new File(projects.getPath() + "ProjectPaths");
            FileOutputStream writableFile = new FileOutputStream(projetRecent);

            writableFile.write(path.getFileName().toString().getBytes());
            writableFile.write("\n".getBytes());
            writableFile.write(path.toAbsolutePath().toString().getBytes());

            writableFile.close();

        }catch (NullPointerException | IOException ex){
            Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
        }
    }

    public static HashMap<String, Path> loadRecentProjetsList(){
        HashMap<String, Path> recentProjectsPaths = new HashMap<>();

        for (String str : projects.list()){
            if(!str.endsWith(".elk")){
                continue;
            }

            recentProjectsPaths.put(str.substring(0, str.length()-4), Path.of(projects.getAbsolutePath() + str));
            Loggeur.logConsole(str + " was put in recent project hash", NiveauLog.TOTAL);
        }

        return recentProjectsPaths;
    }


}
