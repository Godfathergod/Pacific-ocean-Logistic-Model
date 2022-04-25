package com.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Stack;


public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    Image universal = new Image("img/map/minimap.png");
    Rectangle map = new Rectangle(1608,1320);



    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Model.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        ScrollPane scrollPane = (ScrollPane) root.centerProperty().get();
        AnchorPane field = (AnchorPane) scrollPane.getContent();
        field.getChildren().add(map);
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setTitle("Pacific Logistics Model");
        stage.sizeToScene();
        map.setFill(new ImagePattern(universal));


        stage.show();
    }
}
