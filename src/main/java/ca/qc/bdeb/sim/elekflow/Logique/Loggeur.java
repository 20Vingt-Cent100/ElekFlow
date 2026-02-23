package ca.qc.bdeb.sim.elekflow.Logique;

public class Loggeur {
    private static NiveauLog niveauLog = NiveauLog.TOTAL;

    public static void changerNiveauLog(NiveauLog n){
        niveauLog = n;
    }

    public static void logConsole(String message, NiveauLog niveauMessage){
        switch (niveauLog){
            case TOTAL: System.out.println(message);
                break;
            case INACTIF:
                break;
            case ALERTE:
            case ERREUR:
                if (niveauMessage == niveauLog)
                    System.out.println(message);
                break;
        }
    }
}
