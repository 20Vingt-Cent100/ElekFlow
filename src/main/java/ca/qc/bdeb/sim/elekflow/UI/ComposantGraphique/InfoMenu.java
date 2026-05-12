package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.ProprieteElectrique;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.text.ParsePosition;

public class InfoMenu extends VBox {
    public InfoMenu(ComposantJSON comp){
        this.setFocusTraversable(true);

        Label compName = new Label(comp.getNOM());
        this.getChildren().addAll(compName, new Separator());

        if(comp.getPROPRIETES() != null){
            loadPropriete(comp.getPROPRIETES());
        }


    }

    private void loadPropriete(ProprieteElectrique[] proprieteElectriques){
        for (ProprieteElectrique p : proprieteElectriques){
            HBox propriete = new HBox();
            Label proprieteNom = new Label(p.getPropertyName() + ": ");
            TextField numericField = new TextField(String.valueOf(p.getValue()));

            DecimalFormat format = new DecimalFormat( "#.0" );

            numericField.setTextFormatter( new TextFormatter<>(c ->
            {
                if ( c.getControlNewText().isEmpty() )
                {
                    return c;
                }

                ParsePosition parsePosition = new ParsePosition( 0 );
                Object object = format.parse( c.getControlNewText(), parsePosition );

                if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
                {
                    return null;
                }
                else
                {
                    return c;
                }
            }));
            propriete.getChildren().addAll(proprieteNom, numericField);
            this.getChildren().add(propriete);
        }
    }
}
