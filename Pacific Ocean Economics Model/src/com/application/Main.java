package com.application;

import com.locations.City;
import com.locations.PacificOcean;
import com.ship.Bulker;
import com.ship.ContainerShip;
import com.ship.Shuttle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;


public class Main extends Application {
    public Main() throws IOException {
    }

    public static void main(String[] args) {
        launch();
    }

    Image universalImg = new Image("img/map/minimap1.png");
    Image macroObjectImg = new Image("img/factorycity.png");
    Image shuttleImg = new Image("img/shuttle.png");
    Image bulkerImg = new Image("img/bulker.png");
    Image containerShipImg = new Image("img/containership.png");
    Image materialImg = new Image("img/materials.png");
    Image productionImg = new Image("img/production.png");

    AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Model.fxml")));
    Scene scene = new Scene(root);

    ScrollPane scrollPane = (ScrollPane) root.getChildren().get(0);
    AnchorPane field = (AnchorPane) scrollPane.getContent();
    MenuBar menuBar = (MenuBar) root.getChildren().get(1);
    VBox labels = (VBox) root.getChildren().get(2);
    Label shipCounter = (Label) labels.getChildren().get(0);
    Label cargoCounter = (Label) labels.getChildren().get(1);
    Label mousePos = (Label) labels.getChildren().get(2);
    //minimap
    AnchorPane minimap = (AnchorPane) root.getChildren().get(3);
    private final double SCALE = 0.1;
    //
    Rectangle map = new Rectangle(3225,2711);
    AnchorPane test = new AnchorPane();//new Rectangle(80.625,67.775);

    //cities
    Map<City,BorderPane> cityGraphics = new TreeMap<>();
    //ships
    Map<Shuttle, AnchorPane> shipPositions = new TreeMap<>();
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

        map.setFill(new ImagePattern(universalImg));
        Rectangle a = new Rectangle(80.625,67.775);
        a.setFill(new ImagePattern(shuttleImg));
        Rectangle r = new Rectangle();
        test.getChildren().add(r);
        test.getChildren().add(a);
        test.getChildren().add(new Label("Ship"));
        test.setOnMouseClicked(mouseEvent -> {
            Shuttle ship = null;
            switch (new Random().nextInt(1,4)){
                case 1:
                    ship = new Shuttle();
                    break;
                case 2:
                    ship = new Bulker();
                    break;
                case 3:
                    ship = new ContainerShip();
            }
            AnchorPane anchorPane = drawShip(ship);
            shipPositions.put(ship,anchorPane);
            field.getChildren().add(anchorPane);
            ship.automaticLife();
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
        //root listeners
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
                            image.setFill(new ImagePattern(macroObjectImg));
                            FlowPane parking = new FlowPane();
                            parking.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(10),new Insets(10))));
                            VBox info = new VBox();
                            info.setBackground(new Background(new BackgroundFill(new Color(0.1,0.1,0.1,0.5),new CornerRadii(10),new Insets(0,50,0,0))));
                            info.setMaxWidth(200);
                            info.setAlignment(Pos.BASELINE_LEFT);
                            info.setVisible(false);
                            for (int i = 0; i < 5; i++) {
                                Rectangle temp = new Rectangle(10,10);
                                temp.setFill(Color.RED);
                                parking.getChildren().add(temp);
                            }
                            BorderPane borderPane = new BorderPane();
                            borderPane.setTop(cityName);
                            borderPane.setLeft(image);
                            borderPane.setBottom(parking);
                            borderPane.setCenter(info);
                            image.setOnMouseEntered(mouseEvent -> {
                                info.setVisible(true);
                                borderPane.toFront();
                            });
                            image.setOnMouseExited(mouseEvent -> info.setVisible(false));
                            borderPane.setLayoutX(city.getMapX());
                            borderPane.setLayoutY((city.getMapY()));
                            cityGraphics.put(city,borderPane);
                        });
        cityGraphics.forEach((city,pane) -> field.getChildren().add(pane));
        //прорисовка кораблів
        PacificOcean.getPacificOcean().getShipList()
                .forEach(ship -> {
                    Label name = new Label(ship.getName());
                    Rectangle img = new Rectangle(80.625,67.775);

                    if (ContainerShip.class.equals(ship.getClass())) {
                        img.setFill(new ImagePattern(containerShipImg));
                    } else if (Bulker.class.equals(ship.getClass())) {
                        img.setFill(new ImagePattern(bulkerImg));
                    } else if (Shuttle.class.equals(ship.getClass())) {
                        img.setFill(new ImagePattern(shuttleImg));
                    }
                    AnchorPane anchorPane = new AnchorPane(img,name);
                    anchorPane.setLayoutX(ship.getShipX());
                    anchorPane.setLayoutY(ship.getShipY());
                    shipPositions.put(ship,anchorPane);
                });
        ////minimap
        minimap.setPrefWidth(field.getPrefWidth() * SCALE);
        minimap.setPrefHeight(field.getPrefHeight() * SCALE);
        Rectangle miniWorld = (Rectangle) minimap.getChildren().get(0);
        miniWorld.setWidth(field.getPrefWidth() * SCALE);
        miniWorld.setHeight(field.getPrefHeight() * SCALE);
        miniWorld.setFill(Paint.valueOf("red"));
        minimap.setVisible(true);
        //////////////////////////////stage
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setTitle("Pacific Logistics Model");
        stage.sizeToScene();
        test.toFront();
        ///animationTimer
        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                shipCounter.setText("Кількість кораблів в океані: " + Shuttle.getNumberOfShips());
                cargoCounter.setText("Вага вантажу в океані: " +  Shuttle.getTotalCargoWeight());
                cityGraphics.forEach((city, borderPane) -> {
                    FlowPane parking = (FlowPane) borderPane.getBottom();
                    parking.getChildren().clear();
                    for (int i = 0; i < city.getShipsOnParking().size(); i++) {
                        Rectangle temp = new Rectangle(10,10);
                        temp.setFill(Color.RED);
                        parking.getChildren().add(temp);
                    }
                    VBox info = (VBox) borderPane.getCenter();
                    info.getChildren().clear();
                    city.getShipsOnParking().forEach(ship -> {
                        Label temp = new Label(ship.getName());
                        info.getChildren().add(temp);
                    });
                    shipPositions.forEach((ship,pane) -> {
                        if(ship.getParkingCity() == null){
                            pane.setVisible(true);
                        } else {
                            pane.setVisible(false);
                        }

                    });
                    shipPositions.forEach((ship,pane) -> {
                        pane.setLayoutX(ship.getShipX());
                        pane.setLayoutY(ship.getShipY());
                    });

                });
            }
        };

        mainTimer.start();
        stage.show();
    }
    private AnchorPane drawShip(Shuttle ship){
        Label name = new Label(ship.getName());
        Rectangle img = new Rectangle(80.625,67.775);
        Rectangle cargo = new Rectangle(20,10);
        cargo.setFill(Paint.valueOf("white"));
        cargo.setLayoutX(cargo.getLayoutX() + 60.625);
        Rectangle expLine = new Rectangle(80,5);
        expLine.setFill(Paint.valueOf("black"));
        expLine.setOpacity(0.5);
        expLine.setLayoutY(expLine.getLayoutY()-10);
        Rectangle exp = new Rectangle(5,5);
        exp.setLayoutX(expLine.getLayoutX());
        exp.setLayoutY(expLine.getLayoutY());
        exp.setFill(Paint.valueOf("green"));
        if (ship instanceof ContainerShip) {
            img.setFill(new ImagePattern(containerShipImg));
        } else if (ship instanceof Bulker) {
            img.setFill(new ImagePattern(bulkerImg));
        } else {
            img.setFill(new ImagePattern(shuttleImg));
        }
        AnchorPane anchorPane = new AnchorPane(img,cargo,name,expLine,exp);
        anchorPane.setLayoutY(ship.getShipY());
        anchorPane.setLayoutX(ship.getShipX());
        return anchorPane;
    }
}
