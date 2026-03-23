package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Interrupteur;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.scene.input.MouseEvent;
import org.girod.javafx.svgimage.SVGImage;

public class VueInterrupteur extends VueComposantElectrique{
    private boolean isOpen = true;

    public VueInterrupteur() {
        super(
                new SVGImage[]{App.atlas.getSVG("InterrupteurOuvert"), App.atlas.getSVG("InterrupteurFerme")},
                new Interrupteur()
        );
    }

    @Override
    protected void handleOnMousePressed(MouseEvent e) {
        if(!e.isDragDetect())
            changeState();
    }

    private void changeState(){
        isOpen = !isOpen;

        getChildren().getFirst().setVisible(!isOpen);
        getChildren().get(1).setVisible(isOpen);

        Loggeur.logConsole("L'etat de l'interrupteur a ete change", NiveauLog.TOTAL);
    }
}
