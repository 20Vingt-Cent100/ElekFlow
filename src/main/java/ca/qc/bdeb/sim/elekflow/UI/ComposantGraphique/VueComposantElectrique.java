package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.Vec2;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;

import java.math.BigDecimal;
import java.util.ArrayList;

public class VueComposantElectrique extends Region{
        boolean isMovable = false;

        private final Rotate rotate;
        private final double centerX;
        private final double centerY;
        private final Vec2 dernierClick = new Vec2();
        private ArrayList<VueBorne> bornes = new ArrayList<>();

        private final ComposantJSON composantElecGraphique;

        private final double[] size;

        public VueComposantElectrique(ComposantJSON composantElecGraphique, double posX, double posY){
                var svg = App.atlas.getSVG(composantElecGraphique.getCLE_SVG());
                this.getChildren().addAll(svg);
                this.setLayoutX(posX);
                this.setLayoutY(posY);
                this.setHandles();
                this.setSnapToPixel(true);
                this.setFocusTraversable(true);

                size = new double[]{svg.getWidth(), svg.getHeight()};

                if(composantElecGraphique.getBORNES() != null){
                    BigDecimal[][] listBorne = composantElecGraphique.getBORNES();
                        for (int i = 0; i < listBorne.length; i++) {
                            var borne = new VueBorne(listBorne[i], size);
                            bornes.add(borne);
                            Loggeur.logConsole("borne creer", NiveauLog.TOTAL);
                            this.getChildren().add(borne);
                            borne.toFront();
                        }
                }

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

        public void moveComponent(MouseEvent event){
                double newPosX = event.getSceneX();
                double newPosY = event.getSceneY();

                double deltaX = newPosX - dernierClick.x;
                double deltaY = newPosY - dernierClick.y;

                this.setLayoutX(this.getLayoutX() + deltaX / this.getParent().getParent().getScaleX());
                this.setLayoutY(this.getLayoutY() + deltaY / this.getParent().getParent().getScaleY());

                dernierClick.x = newPosX;
                dernierClick.y = newPosY;
        }

        public ArrayList<VueBorne> getBornes() {
                return bornes;
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
                Point2D coord = this.getParent().sceneToLocal(e.getSceneX(), e.getSceneY());
                Point2D center = this.localToParent(getCenterX(), getCenterY());

                if(Math.abs(coord.getX() - center.getX()) > 15 || Math.abs(coord.getY() - center.getY()) > 15){
                       isMovable = true;
                }

                if(isMovable){
                        moveComponent(e);
                }
        }

        protected void handleOnMouseDragReleased(MouseEvent e){
                Loggeur.logConsole("Drag released", NiveauLog.TOTAL);
        }

        protected void handleOnMouseDragExited(MouseEvent e){
                Loggeur.logConsole("Drag exited", NiveauLog.TOTAL);
        }

        protected void handleOnMouseEntered(MouseEvent e){
                bornes.forEach(VueBorne::show);
        }

        protected void handleOnMouseExited(MouseEvent e){
                bornes.forEach(VueBorne::hide);
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

                e.consume();
        }

        protected void handleOnMouseReleased(MouseEvent e){
                isMovable = false;
        }

        protected void handleOnMouseClicked(MouseEvent e){
                this.requestFocus();
                e.consume();

                fireEvent(new ShowInfoEvent(ShowInfoEvent.SHOW_INFO, null, composantElecGraphique));
        }

        protected void handleOnKeyPressed(KeyEvent e){
                switch (e.getCode()){
                        case UP -> this.setLayoutY(this.getLayoutY() - 1);
                        case DOWN -> this.setLayoutY(this.getLayoutY() + 1);
                        case LEFT -> this.setLayoutX(this.getLayoutX() - 1);
                        case RIGHT -> this.setLayoutX(this.getLayoutX() + 1);
                        case DELETE, BACK_SPACE -> fireEvent(new ComponentEvent(ComponentEvent.DELETE_COMPONENT, this, null));
                }

                e.consume();
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
