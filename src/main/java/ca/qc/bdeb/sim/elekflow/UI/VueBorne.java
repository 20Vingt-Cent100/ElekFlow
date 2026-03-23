package ca.qc.bdeb.sim.elekflow.UI;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class VueBorne extends Circle {
    private Line line;

    public VueBorne(double centerX, double centerY, double scale){
        super(centerX, centerY, 5 * scale);
        this.getStyleClass().add("borne");

        setHandles();
    }

    private void setHandles(){
        this.setOnMouseDragged(this::handleOnMouseDragged);
    }

    private void handleOnMouseDragged(MouseEvent e){
        e.consume();



        if (line == null){
            line = new Line(this.getTranslateX(), this.getTranslateY(), e.getScreenX(), e.getScreenY());
        }
        else{
            line.setEndX(e.getScreenX());
            line.setEndY(e.getScreenY());
        }
    }
}
