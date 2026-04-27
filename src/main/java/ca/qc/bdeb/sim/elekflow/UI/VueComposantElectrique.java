package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.ComposantElectriqueGraphique;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;

public class VueComposantElectrique extends Region{

        private final Rotate rotate;
        private final double centerX;
        private final double centerY;

        private final ComposantElectriqueGraphique composantElecGraphique;

        public VueComposantElectrique(ComposantElectriqueGraphique composantElecGraphique, double posX, double posY){
                this.getChildren().add(App.atlas.getSVG(composantElecGraphique.getCLE_SVG()));
                this.setLayoutX(posX);
                this.setLayoutY(posY);
                this.setHandles();

                this.composantElecGraphique = composantElecGraphique;

                centerX = this.getBoundsInLocal().getWidth() /2;
                centerY = this.getBoundsInLocal().getHeight() /2;

                rotate = new Rotate(0, centerX, centerY);
                this.getTransforms().add(rotate);

                this.getStyleClass().add("vue-composant");

                if(composantElecGraphique.isSVGFILL())
                        this.getStyleClass().add("svg-fill");
        }

        public void move(double dx, double dy){
                this.setLayoutX(this.getLayoutX() + dx);
                this.setLayoutY(this.getLayoutY() + dy);
        }

        public void move(MouseEvent e){
                this.move(e.getSceneX() - this.getLayoutX() - centerX, e.getSceneY() - this.getLayoutY() - centerY);
        }

        private void setHandles(){
                setOnMouseDragged(this::handleOnMouseDragged);
                setOnMouseEntered(this::handleOnMouseEntered);
                setOnMouseClicked(this::handleOnMouseClicked);
                setOnScroll(this::handleOnScroll);
                setOnZoom(this::handleOnZoom);
                setOnMousePressed(this::handleOnMousePressed);
                setOnMouseReleased(this::handleOnMouseReleased);
                setOnMouseExited(this::handleOnMouseExited);
                setOnMouseDragReleased(this::handleOnMouseDragReleased);
                setOnMouseDragExited(this::handleOnMouseDragExited);
                setOnKeyPressed(this::handleOnKeyPressed);

        }

        protected void handleOnMouseDragged(MouseEvent e){
                move(e);
        }

        protected void handleOnMouseDragReleased(MouseEvent e){
                Loggeur.logConsole("Drag released", NiveauLog.TOTAL);
        }

        protected void handleOnMouseDragExited(MouseEvent e){
                Loggeur.logConsole("Drag exited", NiveauLog.TOTAL);
        }

        protected void handleOnMouseEntered(MouseEvent e){

        }

        protected void handleOnMouseExited(MouseEvent e){

        }

        protected void handleOnScroll(ScrollEvent e){
                if(!e.isControlDown()) {
                        rotate.setAngle(rotate.getAngle() + (e.getDeltaY() * 9) / 12.);
                }
        }

        protected void handleOnZoom(ZoomEvent e){

        }

        protected void handleOnMousePressed(MouseEvent e){

        }

        protected void handleOnMouseReleased(MouseEvent e){
                Loggeur.logConsole("Mouse Released", NiveauLog.TOTAL);
                if(this.getLayoutX() <= 396) {
                        fireEvent(new ComponentEvent(ComponentEvent.DELETE_COMPONENT,
                                this,
                                e));
                }
        }

        protected void handleOnMouseClicked(MouseEvent e){
                this.requestFocus();
                fireEvent(new ShowInfoEvent(ShowInfoEvent.SHOW_INFO, null, composantElecGraphique));
        }

        protected void handleOnKeyPressed(KeyEvent e){
                switch (e.getCode()){
                        case UP -> this.setTranslateY(this.getTranslateY()-1);
                        case DOWN -> this.setTranslateY(this.getTranslateY()+1);
                        case LEFT -> this.setTranslateX(this.getTranslateX()-1);
                        case RIGHT -> this.setTranslateX(this.getTranslateX()+1);
                }
        }

        public String getComposantNom(){
                return composantElecGraphique.getNOM();
        }
}
