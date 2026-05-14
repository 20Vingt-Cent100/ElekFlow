package ca.qc.bdeb.sim.elekflow.Logique;

import ca.qc.bdeb.sim.elekflow.UI.Utils.SetCourant;
import org.ejml.simple.SimpleMatrix;

import java.util.*;

public class Simulation {
    // Constantes de type pour ton Record
    public static final int RESISTANCE = 1;
    public static final int SOURCE_COURANT = 2; // Optionnel
    public static final int SOURCE_TENSION = 3;

    public record Composant(List<Integer> listeBornesIndex, int type, double valeur, java.util.function.Consumer<Double> setCourant) {}
    public record Wire(int startNodeIndex, int endNodeIndex){}

    /**
     * Résout le circuit et retourne une Map associant chaque index de borne à sa tension.
     */
    public static void solve(List<Composant> composants, List<Wire> wires) {
        if (composants == null) return;

        // 1. Fusion des nœuds (Union-Find simplifié)
        // On crée une map pour savoir quel index pointe vers quel "parent"
        Map<Integer, Integer> parent = new HashMap<>();

        // Initialisation : chaque nœud est son propre parent
        for (Composant c : composants) {
            for (int node : c.listeBornesIndex) {
                parent.putIfAbsent(node, node);
            }
        }
        for (Wire w : wires) {
            parent.putIfAbsent(w.startNodeIndex, w.startNodeIndex);
            parent.putIfAbsent(w.endNodeIndex, w.endNodeIndex);
        }

        // Application des connexions (Wires)
        for (Wire w : wires) {
            union(parent, w.startNodeIndex, w.endNodeIndex);
        }

        // 2. Recenser les "Super-Nœuds" uniques (après fusion)
        Set<Integer> superNoeudsUniques = new HashSet<>();
        int nbSourcesV = 0;
        for (Composant c : composants) {
            superNoeudsUniques.add(find(parent, c.listeBornesIndex.get(0)));
            superNoeudsUniques.add(find(parent, c.listeBornesIndex.get(1)));
            if (c.type == 3) nbSourcesV++;
        }

        // 3. Remappage vers les index de la matrice (0 est toujours le GND)
        Map<Integer, Integer> mapMatrice = new HashMap<>();
        int nextIdx = 1;

        // On identifie d'abord quel est le Super-Nœud qui contient le GND (0)
        int rootGND = parent.containsKey(0) ? find(parent, 0) : -1;

        for (Integer sn : superNoeudsUniques) {
            if (sn == rootGND) {
                mapMatrice.put(sn, 0);
            } else {
                mapMatrice.put(sn, nextIdx++);
            }
        }

        int N = nextIdx - 1;
        int taille = N + nbSourcesV;
        if (taille == 0) return;

        SimpleMatrix A = new SimpleMatrix(taille, taille);
        SimpleMatrix B = new SimpleMatrix(taille, 1);
        for (int i = 0; i < taille; i++) A.set(i, i, 1e-12); // Stabilisation

        // 4. Stamping (en utilisant find(parent, index))
        int vIdx = 0;
        for (Composant c : composants) {
            int i = mapMatrice.get(find(parent, c.listeBornesIndex.get(0)));
            int j = mapMatrice.get(find(parent, c.listeBornesIndex.get(1)));

            if (c.type == 1) { // RESISTANCE
                double g = 1.0 / Math.max(c.valeur, 1e-7);
                applyResistorStamp(A, i, j, g);
            } else if (c.type == 3) { // VOLTAGE SOURCE
                applyVoltageSourceStamp(A, B, i, j, c.valeur, N + vIdx);
                vIdx++;
            }
        }

        try {
            SimpleMatrix x = A.solve(B);

            // Map temporaire des tensions pour calcul rapide
            Map<Integer, Double> tensions = new HashMap<>();
            for (Integer nOrigine : parent.keySet()) {
                int mIdx = mapMatrice.get(find(parent, nOrigine));
                tensions.put(nOrigine, (mIdx == 0) ? 0.0 : x.get(mIdx - 1));
            }

            // Mise à jour de chaque composant via setCourant
            int currentVIdx = 0;
            for (Composant c : composants) {
                double courant = 0;
                if (c.type == RESISTANCE) {
                    double v1 = tensions.get(c.listeBornesIndex.get(0));
                    double v2 = tensions.get(c.listeBornesIndex.get(1));
                    courant = (v1 - v2) / Math.max(c.valeur, 1e-7);
                } else if (c.type == SOURCE_TENSION) {
                    // Le courant de la source est dans la partie M du vecteur x
                    courant = x.get(N + currentVIdx);
                    currentVIdx++;
                }

                // On injecte la valeur calculée dans le composant
                if (c.setCourant != null) c.setCourant.accept(courant);
            }

        } catch (Exception e) {
            System.err.println("Circuit instable ou court-circuit détecté.");
        }
    }

    // --- Fonctions utilitaires pour Union-Find ---
    private static int find(Map<Integer, Integer> parent, int i) {
        if (parent.get(i) == i) return i;
        int root = find(parent, parent.get(i));
        parent.put(i, root); // Path compression pour la vitesse
        return root;
    }

    private static void union(Map<Integer, Integer> parent, int i, int j) {
        int rootI = find(parent, i);
        int rootJ = find(parent, j);
        if (rootI != rootJ) parent.put(rootI, rootJ);
    }

    // --- Stamps (inchangés) ---
    private static void applyResistorStamp(SimpleMatrix A, int i, int j, double g) {
        if (i > 0) A.set(i - 1, i - 1, A.get(i - 1, i - 1) + g);
        if (j > 0) A.set(j - 1, j - 1, A.get(j - 1, j - 1) + g);
        if (i > 0 && j > 0) {
            A.set(i - 1, j - 1, A.get(i - 1, j - 1) - g);
            A.set(j - 1, i - 1, A.get(j - 1, i - 1) - g);
        }
    }

    private static void applyVoltageSourceStamp(SimpleMatrix A, SimpleMatrix B, int i, int j, double v, int k) {
        if (i > 0) { A.set(i - 1, k, 1.0); A.set(k, i - 1, 1.0); }
        if (j > 0) { A.set(j - 1, k, -1.0); A.set(k, j - 1, -1.0); }
        B.set(k, 0, v);
    }
}
