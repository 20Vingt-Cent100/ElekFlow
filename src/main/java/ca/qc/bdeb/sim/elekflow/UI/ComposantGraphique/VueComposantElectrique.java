package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.Logique.Loggeur;
import ca.qc.bdeb.sim.elekflow.Logique.NiveauLog;
import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ComponentEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.ShowInfoEvent;
import ca.qc.bdeb.sim.elekflow.UI.Utils.*;
import ca.qc.bdeb.sim.elekflow.proto.Component;
import javafx.geometry.Point2D;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.scene.transform.Rotate;
import org.girod.javafx.svgimage.SVGImage;

import java.math.BigDecimal;
import java.util.ArrayList;

public class VueComposantElectrique extends Region{
        boolean isMovable = false;

        private long clickTime = 0;
        private boolean isDrag = false;

        private final Rotate rotate;
        private final double centerX;
        private final double centerY;
        private final Vec2 dernierClick = new Vec2();
        private final ArrayList<VueBorne> bornes = new ArrayList<>();

        private final ComposantJSON composantElecGraphique;
        private SVGImage svgComposantTop;
        private final ArrayList<SVGImage> listSvgs = new ArrayList<>();

        private double[] size;

        private final InteractionComposant interactionComposant;
        private final Behavior behavior;

        private Component.Builder component;

        public VueComposantElectrique(ComposantJSON composantElecGraphique, double posX, double posY, double rotation, Component.Builder component){
                composantElecGraphique.getCLE_SVG().forEach((str)->{
                        listSvgs.add(App.atlas.getSVG(str));
                });

                interactionComposant = InteractionListe.getInteraction(composantElecGraphique.getCLE_SVG().getFirst());
                behavior = BehaviorList.getBehavior(composantElecGraphique.getCLE_SVG().getFirst());

                svgComposantTop = listSvgs.getFirst();
                this.getChildren().addAll(svgComposantTop);
                this.setLayoutX(posX);
                this.setLayoutY(posY);
                this.setHandles();
                this.setSnapToPixel(true);
                this.setFocusTraversable(true);


                calculateSize();

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

                this.component = component;
                if (this.component == null){
                        this.component = Component.newBuilder();

                        this.component.setLayoutX(getLayoutX())
                                .setLayoutY(getLayoutY())
                                .setRotation(getRotate())
                                .setKey(getComposantNom());
                }

                this.rotate.setAngle(rotation);



                this.getStyleClass().addAll("vue-composant", "cursor");
        }

        private void calculateSize(){
                size = new double[]{svgComposantTop.getWidth(), svgComposantTop.getHeight()};
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
                isDrag = true;

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
                bornes.forEach((b) -> b.show(false));
        }

        protected void handleOnMouseExited(MouseEvent e){
                bornes.forEach((b) -> b.hide(false));
        }

        protected void handleOnScroll(ScrollEvent e){
                if(!e.isControlDown()) {
                        rotate.setAngle(rotate.getAngle() + (e.getDeltaY() * 9) / 12.);
                        e.consume();

                        component.setRotation(this.rotate.getAngle());
                }
        }

        protected void handleOnZoom(ZoomEvent e){

        }

        protected void handleOnMousePressed(MouseEvent e){
                dernierClick.x = e.getSceneX();
                dernierClick.y = e.getSceneY();
                clickTime = System.currentTimeMillis();

                e.consume();
        }

        protected void handleOnMouseReleased(MouseEvent e){
                isMovable = false;
                component.setLayoutX(getLayoutX());
                component.setLayoutY(getLayoutY());
        }

        protected void handleOnMouseClicked(MouseEvent e){
                this.requestFocus();
                e.consume();

                fireEvent(new ShowInfoEvent(ShowInfoEvent.SHOW_INFO, null, composantElecGraphique));

                if(interactionComposant != null && System.currentTimeMillis() - clickTime < 400 && !isDrag)
                        interactionComposant.executeOnClick(e, this);

                isDrag = false;
        }

        protected void handleOnKeyPressed(KeyEvent e){
                switch (e.getCode()){
                        case UP -> this.setLayoutY(this.getLayoutY() - 1);
                        case DOWN -> this.setLayoutY(this.getLayoutY() + 1);
                        case LEFT -> this.setLayoutX(this.getLayoutX() - 1);
                        case RIGHT -> this.setLayoutX(this.getLayoutX() + 1);
                        case DELETE, BACK_SPACE -> fireEvent(new ComponentEvent(ComponentEvent.DELETE_COMPONENT, this, null));
                }

                component.setLayoutX(getLayoutX());
                component.setLayoutY(getLayoutY());
                e.consume();
        }

        public String getComposantNom(){
                return composantElecGraphique.getNOM();
        }

        public ComposantJSON getComposantElecGraphique() {
                return composantElecGraphique;
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

        public void setCurrent(double newCurrent){

        }

        public double getCurrent(){
                return 1.0;
        }

        public void addBornes(ZoneSimulation zoneSimulation){
                bornes.forEach((v) -> v.addToAll(zoneSimulation));
        }

        public Component getComponent(){
                return component.build();
        }
}
