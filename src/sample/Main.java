package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent anchorPane = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Slider slider = new Slider(0.5, 10, 1);
        ScrollPane scrollPane = new ScrollPane(anchorPane);
        ZoomingPane zoomingPane = new ZoomingPane(scrollPane);
        zoomingPane.zoomFactorProperty().bind(slider.valueProperty());
        BorderPane borderPane = new BorderPane(zoomingPane, null, null, slider, null);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("Dijkstra");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
