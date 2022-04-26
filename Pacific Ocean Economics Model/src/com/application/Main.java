package com.application;

import com.locations.PacificOcean;
import com.ship.Shuttle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;


public class Main extends Application {
    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch();
    }

    Image universal = new Image("img/map/minimap1.png");
    Image macroObject = new Image("img/factorycity.png");

    AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Model.fxml")));
    Scene scene = new Scene(root);

    ScrollPane scrollPane = (ScrollPane) root.getChildren().get(0);
    AnchorPane field = (AnchorPane) scrollPane.getContent();
    ParallelCamera camera = (ParallelCamera) field.getChildren().get(0);
    MenuBar menuBar = (MenuBar) root.getChildren().get(1);
    VBox labels = (VBox) root.getChildren().get(2);
    Label shipCounter = (Label) labels.getChildren().get(0);
    Label cargoCounter = (Label) labels.getChildren().get(1);
    Label mousePos = (Label) labels.getChildren().get(2);

    Rectangle map = new Rectangle(3225,2711);
    Rectangle test = new Rectangle(80.625,67.775);

    //cities
    List<BorderPane> cityGraphics = new ArrayList<>();
    Random a = new Random(1232465);

    @Override
    public void start(Stage stage) throws Exception {
        root.setPrefSize(Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setMinHeight(800);
        stage.setMinWidth(1000);

        //налаштування видимого екрану

        scrollPane.setPrefHeight(Screen.getPrimary().getOutputScaleY());
        scrollPane.setPrefWidth(Screen.getPrimary().getOutputScaleX());

        field.getChildren().add(map);
        field.getChildren().add(test);

        /////////////////////////////////

        map.setFill(new ImagePattern(universal));
        test.setFill(Color.RED);
        test.setOnMouseClicked(mouseEvent -> {
            test.setFill(Color.color(a.nextDouble(),a.nextDouble(),a.nextDouble()));
        });
        root.setOnKeyPressed((keyEvent -> {
            switch (keyEvent.getCode()){
                case W -> {
                    test.setLayoutY(test.getLayoutY()-5);
                }
                case S -> {
                    test.setLayoutY(test.getLayoutY()+5);
                }
                case A -> {
                    test.setLayoutX(test.getLayoutX()-5);
                }
                case D -> {
                    test.setLayoutX(test.getLayoutX()+5);
                }
            }
        }));
        root.setOnMouseClicked(mouseEvent -> {
            mousePos.setText("X: " + (mouseEvent.getX() +  scrollPane.getLayoutX()) + " Y: " + (mouseEvent.getY() + scrollPane.getLayoutY()));
        });
        // прорисовка міст
        PacificOcean.getPacificOcean().getAllCities()
                .stream()
                        .forEach(city -> {
                            Label cityName = new Label(city.getName());
                            cityName.setFont(new Font(18));
                            cityName.setLayoutY(-10);
                            cityName.setBackground(new Background(new BackgroundFill(Color.GOLD,new CornerRadii(10),new Insets(-5))));
                            Rectangle image = new Rectangle(75,75);
                            image.setFill(new ImagePattern(macroObject));
                            FlowPane parking = new FlowPane();
                            parking.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(10),new Insets(10))));
                            for (int i = 0; i < 5; i++) {
                                Rectangle temp = new Rectangle(10,10);
                                temp.setFill(Color.RED);
                                parking.getChildren().add(temp);
                            }
                            BorderPane group = new BorderPane();
                            group.setTop(cityName);
                            group.setLeft(image);
                            group.setBottom(parking);
                            group.setLayoutX(city.getMapX());
                            group.setLayoutY((city.getMapY()));
                            cityGraphics.add(group);
                        });
        cityGraphics.forEach(group -> field.getChildren().add(group));
        //////////////////////////////
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setTitle("Pacific Logistics Model");
        stage.sizeToScene();
        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                shipCounter.setText("Кількість кораблів в океані: " + Shuttle.getNumberOfShips());
                cargoCounter.setText("Вага вантажу в океані: " +  Shuttle.getTotalCargoWeight());
            }
        };

        mainTimer.start();
        stage.show();
    }
}
