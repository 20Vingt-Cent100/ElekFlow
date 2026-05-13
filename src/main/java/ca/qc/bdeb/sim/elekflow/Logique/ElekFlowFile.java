package ca.qc.bdeb.sim.elekflow.Logique;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class ElekFlowFile {

    private static final File projects = new File("./projets");

    // Création automatique du dossier projets
    static {
        if (!projects.exists()) {

            boolean created = projects.mkdirs();

            if (created) {
                Loggeur.logConsole(
                        "Le dossier projets a été créé",
                        NiveauLog.TOTAL
                );
            } else {
                Loggeur.logConsole(
                        "Impossible de créer le dossier projets",
                        NiveauLog.ERREUR
                );
            }
        }
    }

    public static boolean createNewFile(String fileName, Path path) {

        File projectFile = new File(
                path.toAbsolutePath() + "/" + fileName + ".elk"
        );

        try {

            boolean created = projectFile.createNewFile();

            if (!created) {
                Loggeur.logConsole(
                        "Le fichier existe déjà",
                        NiveauLog.ERREUR
                );

                return false;
            }

            addToRecentProject(projectFile.toPath());

            return true;

        } catch (IOException ex) {

            Loggeur.logConsole(
                    ex.getMessage(),
                    NiveauLog.ERREUR
            );

            return false;
        }
    }

    public static void addToRecentProject(Path path) {

        try {

            // Vérifie encore que le dossier existe
            if (!projects.exists()) {
                projects.mkdirs();
            }

            File projetRecent = new File(
                    projects.getPath() + "/ProjectPaths"
            );

            FileOutputStream writableFile =
                    new FileOutputStream(projetRecent);

            writableFile.write(
                    path.getFileName().toString().getBytes()
            );

            writableFile.write("\n".getBytes());

            writableFile.write(
                    path.toAbsolutePath().toString().getBytes()
            );

            writableFile.close();

            Loggeur.logConsole(
                    "Projet récent sauvegardé",
                    NiveauLog.TOTAL
            );

        } catch (IOException ex) {

            Loggeur.logConsole(
                    ex.getMessage(),
                    NiveauLog.ERREUR
            );
        }
    }

    public static HashMap<String, Path> loadRecentProjetsList() {

        HashMap<String, Path> recentProjectsPaths =
                new HashMap<>();

        // Sécurise le dossier
        if (!projects.exists()) {

            boolean created = projects.mkdirs();

            if (!created) {

                Loggeur.logConsole(
                        "Impossible d'accéder au dossier projets",
                        NiveauLog.ERREUR
                );

                return recentProjectsPaths;
            }
        }

        String[] files = projects.list();

        // Empêche le NullPointerException
        if (files == null) {

            Loggeur.logConsole(
                    "Le dossier projets est vide ou inaccessible",
                    NiveauLog.ERREUR
            );

            return recentProjectsPaths;
        }

        for (String str : files) {

            if (!str.endsWith(".elk")) {
                continue;
            }

            recentProjectsPaths.put(
                    str.substring(0, str.length() - 4),
                    Path.of(projects.getAbsolutePath() + "/" + str)
            );

            Loggeur.logConsole(
                    str + " was put in recent project hash",
                    NiveauLog.TOTAL
            );
        }

        return recentProjectsPaths;
    }
}