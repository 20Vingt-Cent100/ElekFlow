package ca.qc.bdeb.sim.elekflow.UI.ComposantGraphique;

import ca.qc.bdeb.sim.elekflow.UI.App;
import ca.qc.bdeb.sim.elekflow.UI.Events.ConsoleEvent;
import ca.qc.bdeb.sim.elekflow.UI.Events.LogEvent;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Console extends VBox {
    private static final VBox textVBox = new VBox();


    public Console(){
        ScrollPane logs = new ScrollPane(textVBox);
        textVBox.heightProperty().addListener((oldValue, newValue, meta) ->{
            logs.setVvalue(1.0);
        });

        HBox consoleTitle = new HBox();

        Button hideButton = new Button();
        hideButton.setGraphic(App.atlas.getSVG("XIcone"));
        hideButton.getStyleClass().addAll("no-style-btn", "cursor");

        consoleTitle.getChildren().addAll(new Label("Console"), hideButton);

        hideButton.setOnAction((e) -> fireEvent(new ConsoleEvent(ConsoleEvent.HIDE_CONSOLE)));

        this.getChildren().addAll(consoleTitle, new Separator(),logs);
        this.getStyleClass().add("console");
    }

    public static void addLog(String message){
        textVBox.getChildren().add(new Text(message));
    }
}
