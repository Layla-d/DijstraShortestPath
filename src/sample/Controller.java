package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import sun.java2d.pipe.OutlineTextRenderer;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    AnchorPane map;

    Graph graph;

    @FXML
    public TextField to;

    @FXML
    public TextField from;

    @FXML
    public TextArea result;

    @FXML
    public TextArea path;
    @FXML
    private Button checkButton;

    @FXML
    void check(ActionEvent event) {
        double distance = graph.findShortestDistanceBetween(Integer.parseInt(from.getText()), Integer.parseInt(to.getText()));
        result.setText(String.valueOf(distance));
        path.setText(graph.findTotalPath(Integer.parseInt(from.getText()), Integer.parseInt(to.getText())));
    }

    Circle firstClickedCircle = null;
    int firstClicked = -1, secondClicked = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            graph = new Graph();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        City[] cities = graph.cities;

        System.out.println("loading map...");

        for (int i = 0; i < cities.length; i += 1) {
            Circle circle = new Circle(1);
//            Circle circle = new Circle(30);
            circle.setUserData(i);
            Tooltip tip = new Tooltip(i + " ");
            bindTooltip(circle, tip);
            AnchorPane.setTopAnchor(circle, getScaledY(cities[i].y));
            AnchorPane.setLeftAnchor(circle, getScaledX(cities[i].x));
            circle.setOnMouseClicked(event -> {
                circle.setFill(Paint.valueOf("#63b7af"));
//
//                double distance = graph.findShortestDistanceBetween(Integer.parseInt(from.getText()), Integer.parseInt(to.getText()));
//                 result.setText(String.valueOf(distance));
//                  path.setText(graph.findTotalPath(Integer.parseInt(from.getText()), Integer.parseInt(to.getText())));
                if (firstClicked == -1) {
                    firstClicked = (int) circle.getUserData();
                    firstClickedCircle = circle;
                    System.out.println(" first clicked is " + firstClicked);
                }
                else {
                    secondClicked = (int) circle.getUserData();
                    System.out.println(" second clicked is " + secondClicked);
                    showShortestPath();
                    firstClickedCircle.setFill(Paint.valueOf("000000"));
                    circle.setFill(Paint.valueOf("000000"));
                    firstClicked = -1;
                    secondClicked = -1;
                }
            });
            map.getChildren().add(circle);
        }

     //   double distance = graph.findShortestDistanceBetween(Integer.parseInt(from.getText()), Integer.parseInt(to.getText()));
     //   result.setText(String.valueOf(distance));
     //   path.setText(graph.findTotalPath(Integer.parseInt(from.getText()), Integer.parseInt(to.getText())));
     // System.out.println(distance);
     // System.out.println(graph.findTotalPath(Integer.parseInt(from.getText()), Integer.parseInt(to.getText())));
    }

    private void showShortestPath() {


        double distance = graph.findShortestDistanceBetween(firstClicked, secondClicked);
        System.out.println(distance);
        String str = String.format("%.2f", distance);
        System.out.println(graph.findTotalPath(firstClicked, secondClicked));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.getDialogPane().setPrefSize(550, 400);
        alert.setTitle("Shortest Path");
        alert.setHeaderText("Shortest Distance is " + str + " units");

        TextArea textArea = new TextArea();
        textArea.setText(graph.findTotalPath(firstClicked, secondClicked));
       // textArea.setText("And the path is "+"\n" + graph.findTotalPath(firstClicked, secondClicked));

        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
       // expContent.setMaxWidth(Double.MAX_VALUE);
      expContent.add(textArea, 0, 4);

// Set expandable Exception into the dialog pane.
       alert.getDialogPane().setExpandableContent(expContent);

     //   alert.setContentText("And the path is "+"\n" + graph.findTotalPath(firstClicked, secondClicked));

        alert.showAndWait();
//        String totalPath = graph.findTotalPath(firstClicked, secondClicked);
    }

    public static void bindTooltip(final Node node, final Tooltip tooltip) {
        node.setOnMouseMoved(event -> {
            // +15 moves the tooltip 15 pixels below the mouse cursor;
            // if you don't change the y coordinate of the tooltip, you
            // will see constant screen flicker
            tooltip.show(node, event.getScreenX(), event.getScreenY() + 15);
        });
        node.setOnMouseExited(event -> tooltip.hide());
    }






    double getScaledX(double x) {

        return x / graph.maxX * 1300 + graph.minX;
    }

    double getScaledY(double y) {

        return y / graph.maxY * 800 + graph.minY;
    }
}
