package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.Vec2;
import ca.qc.bdeb.sim.elekflow.UI.VueBorne;
import javafx.geometry.Point2D;
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
        private final Vec2 dernierClick = new Vec2();
        private VueBorne borne = new VueBorne();

        private final ComposantJSON composantElecGraphique;

        public VueComposantElectrique(ComposantJSON composantElecGraphique, double posX, double posY){
                this.getChildren().addAll(App.atlas.getSVG(composantElecGraphique.getCLE_SVG()), borne);
                this.setLayoutX(posX);
                this.setLayoutY(posY);
                this.setHandles();
                this.setSnapToPixel(true);

                borne.hide();
                this.composantElecGraphique = composantElecGraphique;

                centerX = this.getBoundsInLocal().getWidth() /2;
                centerY = this.getBoundsInLocal().getHeight() /2;

                dernierClick.x = posX + centerX;
                dernierClick.y = posY + centerY;

                rotate = new Rotate(0, centerX, centerY);
                this.getTransforms().add(rotate);

                this.getStyleClass().addAll("vue-composant", "cursor");
        }

        public void moveOnDrag(MouseEvent e){
                double newPosX = e.getSceneX();
                double newPosY = e.getSceneY();

                this.setTranslateX(getTranslateX() + (newPosX - dernierClick.x) / getScaleX());
                this.setTranslateY(getTranslateY() + (newPosY - dernierClick.y) / getScaleY());

                dernierClick.x = newPosX;
                dernierClick.y = newPosY;
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
                e.consume();
                moveOnDrag(e);
        }

        protected void handleOnMouseDragReleased(MouseEvent e){
                Loggeur.logConsole("Drag released", NiveauLog.TOTAL);
        }

        protected void handleOnMouseDragExited(MouseEvent e){
                Loggeur.logConsole("Drag exited", NiveauLog.TOTAL);
        }

        protected void handleOnMouseEntered(MouseEvent e){
                borne.show();
        }

        protected void handleOnMouseExited(MouseEvent e){
                borne.hide();
        }

        protected void handleOnScroll(ScrollEvent e){
                if(!e.isControlDown()) {
                        rotate.setAngle(rotate.getAngle() + (e.getDeltaY() * 9) / 12.);
                        e.consume();
                }
        }

        protected void handleOnZoom(ZoomEvent e){

        }

        protected void handleOnMousePressed(MouseEvent e){
                dernierClick.x = e.getSceneX();
                dernierClick.y = e.getSceneY();
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

        public double getCenterX() {
                return centerX;
        }

        public double getCenterY() {
                return centerY;
        }

        public Point2D getCenter(){
                return new Point2D(centerX, centerY);
        }
}
