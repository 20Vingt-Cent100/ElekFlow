package ca.qc.bdeb.sim.elekflow.UI;

import ca.qc.bdeb.sim.elekflow.Logique.ComposantElectrique;
import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import org.girod.javafx.svgimage.SVGImage;

public class VueComposantElectrique extends Region{
        private final ComposantElectrique composantElectrique;
        private double[] onClickOffset;
        private int[] nodeChildrenIndex;
        private final SVGImage[] listSVGs;

        public VueComposantElectrique(SVGImage imageParDefaut, ComposantElectrique composantElectrique, Point2D[] nodesPos){
                listSVGs = new SVGImage[]{imageParDefaut};
                this.composantElectrique = composantElectrique;

                setJavaFxComponent();
                setHandles();
        }

        public VueComposantElectrique(SVGImage imageParDefaut, ComposantElectrique composantElectrique){
                listSVGs = new SVGImage[]{imageParDefaut};
                this.composantElectrique = composantElectrique;

                setJavaFxComponent();
                setHandles();
        }

        public VueComposantElectrique(SVGImage[] listImage, ComposantElectrique composantElectrique){
                listSVGs = listImage;
                this.composantElectrique = composantElectrique;

                setJavaFxComponent();
                setHandles();
        }

        public void setJavaFxComponent(){
                this.getStyleClass().add("composant");
                this.setCursor(Cursor.HAND);
                //this.setSnapToPixel(true);

                this.getChildren().addAll(listSVGs);

                for(Node n : this.getChildren()){
                        n.setVisible(false);
                }

                this.getChildren().getFirst().setVisible(true);

                var c1 = new VueBorne(0, listSVGs[0].getHeight()/2, 1);
                var c2 = new VueBorne(listSVGs[0].getWidth(), listSVGs[0].getHeight()/2, 1);

                c1.setVisible(false);
                c2.setVisible(false);

                this.getChildren().addAll(c1, c2);
                nodeChildrenIndex = new int[2];
                nodeChildrenIndex[0] = this.getChildren().indexOf(c1);
                nodeChildrenIndex[1] = this.getChildren().indexOf(c2);
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
                if(onClickOffset == null){
                        onClickOffset = new double[]{e.getX(), e.getY()};
                }

                this.getStyleClass().remove("hovered");

                this.setTranslateX(e.getScreenX() - this.getLayoutX() - onClickOffset[0]);
                this.setTranslateY(e.getScreenY() - this.getLayoutY() - (onClickOffset[1]) - this.getHeight());

                e.consume();
        }

        protected void handleOnMouseDragReleased(MouseEvent e){
                this.getStyleClass().add("hovered");
                System.out.println("Mouse drag released");
        }

        protected void handleOnMouseDragExited(MouseEvent e){

        }

        protected void handleOnMouseEntered(MouseEvent e){
                this.getStyleClass().add("hovered");

                for (int i : nodeChildrenIndex){
                        this.getChildren().get(i).setVisible(true);
                }
        }

        protected void handleOnMouseExited(MouseEvent e){
                this.getStyleClass().remove("hovered");

                for (int i : nodeChildrenIndex){
                        try {
                                this.getChildren().get(i).setVisible(false);
                        }catch (IndexOutOfBoundsException ex){
                                Loggeur.logConsole(ex.getMessage(), NiveauLog.ERREUR);
                        }

                }
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
                onClickOffset = null;
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
