package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.Atlas;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique.ComposantElectrique;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Region;
import org.girod.javafx.svgimage.SVGImage;

public class VueComposantElectrique extends Region{

        public VueComposantElectrique(ComposantElectrique composantElecGraphique, double posX, double posY){
                this.getChildren().add(App.atlas.getSVG(composantElecGraphique.getCLE_SVG()));
                this.setLayoutX(posX);
                this.setLayoutY(posY);
                this.setHandles();
        }

        public void move(double dx, double dy){
                this.setLayoutX(this.getLayoutX() + dx);
                this.setLayoutY(this.getLayoutY() + dx);
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
                this.move(e.getSceneX() - this.getLayoutX(), e.getSceneY() - this.getLayoutX());
        }

        protected void handleOnMouseDragReleased(MouseEvent e){
                this.getStyleClass().add("hovered");
                System.out.println("Mouse drag released");
        }

        protected void handleOnMouseDragExited(MouseEvent e){

        }

        protected void handleOnMouseEntered(MouseEvent e){

        }

        protected void handleOnMouseExited(MouseEvent e){

        }

        protected void handleOnScroll(ScrollEvent e){
                if(!e.isControlDown()) {
                        this.setRotate(this.getRotate() + (e.getDeltaY() * 9) / 12.);
                        e.consume();
                }
        }

        protected void handleOnZoom(ZoomEvent e){

        }

        protected void handleOnMousePressed(MouseEvent e){

        }

        protected void handleOnMouseReleased(MouseEvent e){

        }

        protected void handleOnMouseClicked(MouseEvent e){
                this.requestFocus();
        }

        protected void handleOnKeyPressed(KeyEvent e){
                switch (e.getCode()){
                        case UP -> this.setTranslateY(this.getTranslateY()-1);
                        case DOWN -> this.setTranslateY(this.getTranslateY()+1);
                        case LEFT -> this.setTranslateX(this.getTranslateX()-1);
                        case RIGHT -> this.setTranslateX(this.getTranslateX()+1);
                }
        }
}
