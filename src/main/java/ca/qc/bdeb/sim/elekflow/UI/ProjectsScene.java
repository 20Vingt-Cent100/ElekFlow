package ca.qc.bdeb.sim.elekflow.UI;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProjectsScene extends ElekflowScene{

    public ProjectsScene() {
        super(900, 600, WindowMode.WINDOWED);
    }

    //TODO: Refaire ça et implémenter du CSS
    @Override
    public void populateScene() {
        var appLogo = new ImageView(App.atlas.getIMG("LogoDark"));
        appLogo.setPreserveRatio(true);
        appLogo.setFitHeight(getHeight() * 0.37);
        appLogo.setLayoutY(0);
        appLogo.setLayoutX(0);

        var appName = new Text("ElekFlow");
        appName.setFont(Font.font("Inclusive Sans", 48));
        appName.getStyleClass().add("--white");
        appName.setLayoutY(appName.getLayoutY() + ROOT.getHeight() * 0.095);
        appName.setLayoutX(appName.getLayoutX() + ROOT.getWidth() * 0.305);
        appName.setUnderline(true);



        ROOT.setBackground(new Background(new BackgroundFill(Color.web("031927"), null, null)));
        ROOT.getChildren().addAll(appName, appLogo, recentProjects());
    }

    public Region recentProjects(){
        var region = new VBox();

        var regionTitle = new Text("Recent projects");
        regionTitle.setFont(Font.font("Inclusive Sans", 48));
        regionTitle.getStyleClass().add("--white");


        region.getChildren().addAll(regionTitle);
        return region;
    }
}
