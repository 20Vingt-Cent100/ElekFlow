package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.ComposantElectrique;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Region;
import org.girod.javafx.svgimage.SVGImage;

public class VueComposantElectrique extends Region{
        private ComposantElectrique composantElectrique;
        private double[] onClickOffset;

        public VueComposantElectrique(SVGImage imageParDefaut, ComposantElectrique composantElectrique){
                SVGImage[] arrayImage = {imageParDefaut};
                new VueComposantElectrique(arrayImage, composantElectrique);
        }

        public VueComposantElectrique(SVGImage[] listImage, ComposantElectrique composantElectrique){
                this.getChildren().addAll(listImage);

                for (int i = 1; i < this.getChildren().size(); i++) {
                        this.getChildren().get(i).setVisible(false);
                }

                this.getStyleClass().add("composant");
                this.setCursor(Cursor.HAND);

                this.composantElectrique = composantElectrique;

                setHandles();
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
        }

        protected void handleOnMouseDragged(MouseEvent e){
                if(onClickOffset == null){
                        onClickOffset = new double[]{e.getX(), e.getY()};
                }

                this.getStyleClass().remove("hovered");

                this.setTranslateX(e.getScreenX() - this.getLayoutX() - onClickOffset[0]);
                this.setTranslateY(e.getScreenY() - this.getLayoutY() - (onClickOffset[1]) - this.getHeight());
        }

        protected void handleOnMouseDragReleased(MouseEvent e){

        }

        protected void handleOnMouseDragExited(MouseEvent e){

        }

        protected void handleOnMouseEntered(MouseEvent e){
                this.getStyleClass().add("hovered");
        }

        protected void handleOnMouseExited(MouseEvent e){
                this.getStyleClass().remove("hovered");
        }



        protected void handleOnScroll(ScrollEvent e){
                this.setRotate(this.getRotate() + (e.getDeltaY() * 9) / 12.);
                e.consume();
        }

        protected void handleOnZoom(ZoomEvent e){

        }

        protected void handleOnMousePressed(MouseEvent e){

        }

        protected void handleOnMouseReleased(MouseEvent e){
                onClickOffset = null;
        }

        protected void handleOnMouseClicked(MouseEvent e){

        }
}
