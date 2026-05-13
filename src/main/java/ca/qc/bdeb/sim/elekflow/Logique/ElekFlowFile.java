package ca.qc.bdeb.sim.elekflow.Logique;

import ca.qc.bdeb.sim.elekflow.proto.*;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;
import com.google.protobuf.util.JsonFormat;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

public class ElekFlowFile {

    private static final File projects = new File("./projets");
    private static final Long magicNumber = 6762053594109791614L;
    private static final int appVersion = 1;

    private Path pathAtCreation;

    private Elk data;
    private MetaData metaData;
    private final Date currentDate;

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

    public static boolean createNewFile(File file) {

        try {

            boolean created = file.createNewFile();

            if (!created) {
                Loggeur.logConsole(
                        "Le fichier existe déjà",
                        NiveauLog.ERREUR
                );

                return false;
            }

            return true;

        } catch (IOException ex) {

            Loggeur.logConsole(
                    ex.getMessage(),
                    NiveauLog.ERREUR
            );

            return false;
        }
    }

    public static HashMap<String, Path> loadRecentProjetsList() {
        HashMap<String, Path> recentProjectsPaths = new HashMap<>();

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
                    str,
                    Path.of(projects.getAbsolutePath() + "/" + str)
            );

            Loggeur.logConsole(
                    str + " was put in recent project hash",
                    NiveauLog.TOTAL
            );
        }

        return recentProjectsPaths;
    }

    public static ElekFlowFile loadElekFlowFile(File file) {
        if (!file.getName().endsWith(".elk")) {
            return null;
        }

        ElekFlowFile elekFlowFile = new ElekFlowFile();
        elekFlowFile.pathAtCreation = file.toPath();

        if (elekFlowFile.verifyMetadata(file)) {


            return elekFlowFile;
        } else {
            return null;
        }
    }

    public static ElekFlowFile createNewElekFlowFile(String fileName, Path path) {
        ElekFlowFile elekFlowFile = new ElekFlowFile(fileName, path);
        return elekFlowFile;
    }

    public ElekFlowFile() {
        currentDate = getCurrentDate();
    }

    public ElekFlowFile(String fileName, Path path) {
        currentDate = getCurrentDate();

        pathAtCreation = Path.of(path.toString() + "/" + fileName + ".elk");
        metaData = MetaData.newBuilder()
                .setAppVersion(appVersion)
                .setMagicNumber(magicNumber)
                .setProjectName(fileName + ".elk")
                .setLastModified(currentDate)
                .build();

        data = Elk.newBuilder()
                .setMetaData(metaData)
                .build();
    }

    private Date getCurrentDate() {
        var newDate = Date.newBuilder()
                .setDay(LocalDate.now().getDayOfMonth())
                .setMonth(LocalDate.now().getMonthValue())
                .setYear(LocalDate.now().getYear())
                .setHour(LocalTime.now().getHour())
                .setMinute(LocalTime.now().getMinute())
                .build();

        return newDate;
    }


    private boolean verifyMetadata(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            this.data = Elk.parseFrom(fileInputStream);
            metaData = data.getMetaData();
            fileInputStream.close();
            return metaData.getMagicNumber() == magicNumber && metaData.getAppVersion() == appVersion;

        } catch (IOException ex) {
            Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            return false;
        }
    }

    public boolean saveFile(File file, Content content) {

        File saveFile = file == null ? pathAtCreation.toFile() : new File(file.getPath() + ".elk");

        if (!currentDate.equals(getCurrentDate())) {
            metaData.toBuilder().setLastModified(getCurrentDate());
        }

        if (!saveFile.exists()) {
            if (!createNewFile(saveFile))
                return false;
        }

        try {

            data = data.toBuilder().setContent(content).build();

            FileOutputStream outputStream = new FileOutputStream(saveFile);
            outputStream.write(data.toByteArray());
            outputStream.close();

            debugPrintAsJson();
            return true;


        } catch (IOException ex) {
            Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
            return false;
        }
    }

    public Circuit getCircuit(){
        return data.getContent().getCircuit();
    }

    public void debugPrintAsJson() {
        // Le printer permet de rendre le JSON "beau" (indentation)
        String debugString = TextFormat.printer().printToString(data);

        System.out.println("--- STRUCTURE DU FICHIER ---");
        System.out.println(debugString);

    }
}