package com.application;

import com.locations.*;
import com.ship.Bulker;
import com.ship.ContainerShip;
import com.ship.Shuttle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Main extends Application {
    public Main() throws IOException {
    }
    Image universalImg = new Image("img/map/minimap1.png");
    Image macroObjectImg = new Image("img/factorycity.png");
    Image materialImg = new Image("img/materials.png");
    Image productionImg = new Image("img/production.png");
    //stage elements
    AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Model.fxml")));
    Scene scene = new Scene(root);
    ScrollPane scrollPane = (ScrollPane) root.getChildren().get(0);
    AnchorPane field = (AnchorPane) scrollPane.getContent();
    MenuBar menuBar = (MenuBar) root.getChildren().get(1);
    VBox labels = (VBox) root.getChildren().get(2);
    Label shipCounter = (Label) labels.getChildren().get(0);
    Label cargoCounter = (Label) labels.getChildren().get(1);
    AnchorPane minimap = (AnchorPane) root.getChildren().get(3);//anchorpane мінікарти
    //newShipMenu
    AnchorPane newShip = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NewShipMenu.fxml")));
    ChoiceBox<String> degreeBox = (ChoiceBox) newShip.getChildren().get(3);
    TextField name = (TextField) newShip.getChildren().get(5);
    TextField speed = (TextField) newShip.getChildren().get(7);
    CheckBox checkCity = (CheckBox) newShip.getChildren().get(6);
    ChoiceBox<City> cityBox = (ChoiceBox) newShip.getChildren().get(10);
    Button addButton = (Button) newShip.getChildren().get(0);
    Label cityBoxLabel = (Label) newShip.getChildren().get(9);
    Label badInput = (Label) newShip.getChildren().get(11);
    Label badDegree = (Label) newShip.getChildren().get(12);
    private final double SCALE = 0.1;
    //universal object
    Rectangle map = new Rectangle(3225,2711);
    Label mapName = new Label("Тихий Океан");
    //cities
    Map<City, AnchorPane> cityGraphics = new HashMap<City, AnchorPane>();
    //ships
    Map<Shuttle, AnchorPane> shipPositions = new ConcurrentHashMap<>();
    Map<Shuttle,Rectangle> shipsOnMinimap = new ConcurrentHashMap<>();

    @Override
    public void start(Stage stage){
        root.setPrefSize(Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setMinHeight(800);
        stage.setMinWidth(1000);
        scrollPane.setPrefHeight(Screen.getPrimary().getOutputScaleY());
        scrollPane.setPrefWidth(Screen.getPrimary().getOutputScaleX());
        field.getChildren().add(map);
        mapName.setFont(new Font(40));
        field.getChildren().add(mapName);
        mapName.setLayoutX(1400);
        mapName.setLayoutY(25);
        root.getChildren().add(newShip);
        labels.setBackground(new Background(new BackgroundFill(Color.rgb(0,0,0,0.25),new CornerRadii(5),new Insets(1,1,1,1))));
        //newShipMenu
        newShip.setLayoutY(Screen.getPrimary().getBounds().getHeight()/4);
        newShip.setLayoutX(Screen.getPrimary().getBounds().getWidth()/3);
        newShip.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(10),new Insets(0,0,0,0))));
        degreeBox.getItems().add(0,"Shuttle");
        degreeBox.getItems().add(1,"Bulker");
        degreeBox.getItems().add(2,"ContainerShip");
        StringConverter<City> cityConverter = new StringConverter<>() {
            @Override
            public String toString(City city) {
                if(city == null) return null;
                return city.getName();
            }

            @Override
            public City fromString(String s) {
                return PacificOcean.getPacificOcean().getAllCities()
                        .stream()
                        .filter(city -> Objects.equals(city.getName(), s))
                        .findAny().get();
            }
        };
        cityBox.setConverter(cityConverter);
        PacificOcean.getPacificOcean().getAllCities().forEach(city -> cityBox.getItems().add(city));
        checkCity.setSelected(false);
        badInput.setVisible(false);
        badDegree.setVisible(false);
        newShip.toBack();
        addButton.setOnAction(event -> {
            String shipName = name.getText();
            int shipSpeed;
            try{
                shipSpeed = Integer.parseInt(speed.getText());
            }catch (NumberFormatException ignored){
                badInput.setVisible(true);
                return;
            }
            if(degreeBox.getValue() == null){
                badDegree.setVisible(true);
                return;
            }
            Shuttle ship;
            if(checkCity.isSelected()){
                ship = switch (degreeBox.getValue()) {
                    case "Shuttle" -> new Shuttle(shipName,shipSpeed,cityBox.getValue());
                    case "Bulker" -> new Bulker(shipName,shipSpeed,cityBox.getValue());
                    case "ContainerShip" -> new ContainerShip(shipName,shipSpeed,cityBox.getValue());
                    default -> null;
                };
            } else {
              ship = switch (degreeBox.getValue()) {
                    case "Shuttle" -> new Shuttle(shipName,shipSpeed,null);
                    case "Bulker" -> new Bulker(shipName,shipSpeed,null);
                    case "ContainerShip" -> new ContainerShip(shipName,shipSpeed,null);
                    default -> null;
                };
            }
            assert false;
            AnchorPane anchorPane = ship.drawShip();
            shipPositions.put(ship, anchorPane);
            Rectangle miniShip = new Rectangle(4, 4);
            miniShip.setFill(Color.BLACK);
            shipsOnMinimap.put(ship, miniShip);
            field.getChildren().add(anchorPane);
            minimap.getChildren().add(miniShip);
            ship.automaticLife();
            newShip.toBack();
        });
        ////minimap
        minimap.setPrefWidth(map.getWidth() * SCALE);
        minimap.setPrefHeight(map.getHeight() * SCALE);
        minimap.setLayoutX(1213);
        Rectangle miniWorld = (Rectangle) minimap.getChildren().get(0);
        miniWorld.setWidth(minimap.getPrefWidth());
        miniWorld.setHeight(minimap.getPrefHeight());
        miniWorld.setFill(new ImagePattern(universalImg));
        Rectangle2D screen = Screen.getPrimary().getBounds();
        Line left = new Line();
        left.setStartX(0);
        left.setStartY(0);
        left.setEndX(0);
        left.setStartY(screen.getHeight() * SCALE);
        Line top = new Line();
        top.setStartX(0);
        top.setStartY(0);
        top.setEndX(screen.getWidth() * SCALE);
        top.setEndY(0);
        Line right = new Line();
        right.setStartX(screen.getWidth() * SCALE);
        right.setStartY(0);
        right.setEndX(screen.getWidth() * SCALE);
        right.setEndY(screen.getHeight() * SCALE);
        Line bottom = new Line();
        bottom.setStartX(0);
        bottom.setStartY(screen.getHeight() * SCALE);
        bottom.setEndX(screen.getWidth() * SCALE);
        bottom.setEndY(screen.getHeight() * SCALE);
        Group visibleArea = new Group(left,top,right,bottom);
        minimap.getChildren().add(visibleArea);
        miniWorld.setOnMouseClicked(event -> {
            scrollPane.setHvalue((event.getX() - (screen.getWidth() * SCALE)/2)/(SCALE*map.getWidth()- (screen.getWidth() * SCALE)));
            scrollPane.setVvalue((event.getY() - (screen.getHeight() * SCALE)/2)/(SCALE*map.getHeight()- (screen.getHeight() * SCALE)));
        });
        //universal object
        map.setFill(new ImagePattern(universalImg));
        //eventlisteners
        root.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case N -> {
                    Shuttle ship = switch (new Random().nextInt(1, 4)) {
                        case 1 -> new Shuttle();
                        case 2 -> new Bulker();
                        case 3 -> new ContainerShip();
                        default -> null;
                    };
                    assert ship != null;
                    AnchorPane anchorPane = ship.drawShip();
                    shipPositions.put(ship, anchorPane);
                    Rectangle miniShip = new Rectangle(5, 5);
                    miniShip.setFill(Color.BLACK);
                    shipsOnMinimap.put(ship, miniShip);
                    field.getChildren().add(anchorPane);
                    minimap.getChildren().add(miniShip);
                    ship.automaticLife();
                }
                case W -> PacificOcean.getPacificOcean().getShipList()
                        .stream()
                        .filter(shuttle -> shuttle.isActive() && shuttle.getParkingCity() == null)
                        .forEach(shuttle ->{
                            if(shuttle.getShipY() - 0.5 * shuttle.getEngine().getSpeed() < map.getLayoutY()){
                                return;
                            }
                                shuttle.setShipY(shuttle.getShipY() - 0.5 * shuttle.getEngine().getSpeed());
                        });
                case S -> PacificOcean.getPacificOcean().getShipList()
                        .stream()
                        .filter(shuttle -> shuttle.isActive() && shuttle.getParkingCity() == null)
                        .forEach(shuttle ->
                        {
                            if(shuttle.getShipY() + 0.5 * shuttle.getEngine().getSpeed() > map.getLayoutY() + map.getHeight()){
                                return;
                            }
                            shuttle.setShipY(shuttle.getShipY() + 0.5 * shuttle.getEngine().getSpeed());
                        });
                case A -> PacificOcean.getPacificOcean().getShipList()
                        .stream()
                        .filter(shuttle -> shuttle.isActive() && shuttle.getParkingCity() == null)
                        .forEach(shuttle ->
                        {
                            if(shuttle.getShipX() - 0.5 * shuttle.getEngine().getSpeed() < map.getLayoutX()){
                                return;
                            }
                            shuttle.setShipX(shuttle.getShipX() - 0.5 * shuttle.getEngine().getSpeed());
                        });
                case D -> PacificOcean.getPacificOcean().getShipList()
                        .stream()
                        .filter(shuttle -> shuttle.isActive() && shuttle.getParkingCity() == null)
                        .forEach(shuttle ->
                        {
                            if(shuttle.getShipX() + 0.5 * shuttle.getEngine().getSpeed() > map.getLayoutX() + map.getWidth()){
                                return;
                            }
                            shuttle.setShipX(shuttle.getShipX() + 0.5 * shuttle.getEngine().getSpeed());
                        });
                case ESCAPE -> shipPositions.forEach((ship, pane) -> {
                    if(ship.isActive()) {
                        ship.setActive(false);
                        Rectangle flag = (Rectangle) pane.getChildren().get(3);
                        flag.setVisible(false);
                        ship.automaticLife();
                    }

                });
                case DELETE -> PacificOcean.getPacificOcean()
                        .getShipList()
                        .stream()
                        .filter(Shuttle::isActive)
                        .forEach(ship -> {
                            ship.setDeleted(true);
                            ship.getAutoLife().interrupt();
                            PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight()-ship.getCargoWeight());
                            PacificOcean.getPacificOcean().getShipList().remove(ship);
                        });
                case INSERT -> newShip.toFront();
                case ENTER -> {
                    if(PacificOcean.getPacificOcean().getShipList().stream().filter(Shuttle::isActive).count() != 1) break;
                    Shuttle shuttle = PacificOcean.getPacificOcean().getShipList().stream().filter(Shuttle::isActive).findAny().get();
                    if(shuttle.getParkingCity() == null){
                        City enterCity;
                        try{
                            enterCity = PacificOcean.getPacificOcean().getAllCities()
                                    .stream()
                                    .filter(city -> Math.abs(city.getMapX() - shuttle.getShipX()) < 50
                                            && Math.abs(city.getMapY() - shuttle.getShipY()) < 50).findAny().get();
                        }catch (NoSuchElementException ignored){
                            break;
                        }
                        shuttle.stayInCity(enterCity);
                    } else {
                        shuttle.leaveCity();
                    }
                }
                /*case R -> {
                    if(Shuttle.isActiveWork()){
                        Shuttle.setActiveWork(false);
                        PacificOcean.getPacificOcean().getShipList().forEach(ship -> {
                            ship.getAutoLife().interrupt();
                            new Thread(() -> ship.moveTo(ship.getBaseCity())).start();
                        });
                    } else {
                        Shuttle.setActiveWork(true);
                        PacificOcean.getPacificOcean().getShipList().forEach(ship -> {
                            ship.automaticLife();
                        });
                    }

                }*/
            }
        });
        //CITIES
        PacificOcean.getPacificOcean().getAllCities()
                        .forEach(city -> {
                            Label cityName = new Label(city.getName());
                            cityName.setFont(new Font(15));
                            cityName.setLayoutY(-20);
                            cityName.setBackground(new Background(new BackgroundFill(Color.GOLD,new CornerRadii(10),new Insets(-5))));
                            Rectangle image = new Rectangle(75,75);
                            image.setFill(new ImagePattern(macroObjectImg));
                            FlowPane parking = new FlowPane();
                            parking.setBackground(new Background(new BackgroundFill(Color.ORANGE,new CornerRadii(10),new Insets(10))));
                            parking.setLayoutY(75);
                            VBox info = new VBox();
                            info.setLayoutX(100);
                            info.setBackground(new Background(new BackgroundFill(new Color(0.1,0.1,0.1,0.5),new CornerRadii(10),new Insets(0,50,0,0))));
                            info.setMaxWidth(200);
                            info.setVisible(false);
                            for (int i = 0; i < 5; i++) {
                                Rectangle temp = new Rectangle(10,10);
                                temp.setFill(Color.RED);
                                parking.getChildren().add(temp);
                            }
                            AnchorPane borderPane = new AnchorPane(image,cityName,parking,info);
                            image.setOnMouseEntered(mouseEvent -> {
                                info.setVisible(true);
                                borderPane.toFront();
                            });
                            image.setOnMouseExited(mouseEvent -> info.setVisible(false));
                            borderPane.setLayoutX(city.getMapX());
                            borderPane.setLayoutY((city.getMapY()));
                            cityGraphics.put(city,borderPane);
                            field.getChildren().add(borderPane);
                        });
        PacificOcean.getPacificOcean().getAllCities().forEach(city -> {
            Rectangle spot = new Rectangle(8,8);
            spot.setFill(Color.RED);
            spot.setLayoutX(city.getMapX() * SCALE);
            spot.setLayoutY(city.getMapY() * SCALE);
            minimap.getChildren().add(spot);
        });
        //SHIPS
        PacificOcean.getPacificOcean().getShipList()
                .forEach(ship -> {
                   AnchorPane anchorPane = ship.drawShip();
                    shipPositions.put(ship,anchorPane);
                });

        PacificOcean.getPacificOcean().getShipList().forEach(ship -> {
            Rectangle spot = new Rectangle(3,3);
            spot.setFill(Color.BLACK);
            spot.setLayoutX(ship.getShipX() * SCALE);
            spot.setLayoutY(ship.getShipY() * SCALE);
            shipsOnMinimap.put(ship,spot);
            minimap.getChildren().add(spot);
        });
        //////////////////////////////stage
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setTitle("Pacific Logistics Model");
        //stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("ALT+ENTER"));
        stage.sizeToScene();
        ///animationTimer
        AnimationTimer mainTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                shipCounter.setText("Кількість кораблів в океані: " + PacificOcean.getPacificOcean().getShipList().size());
                cargoCounter.setText("Вага вантажу в океані: " +  PacificOcean.getPacificOcean().getTotalCargoWeight());
                cityGraphics.forEach((city, borderPane) -> {
                    FlowPane parking = (FlowPane) borderPane.getChildren().get(2);
                    parking.getChildren().clear();
                    for (int i = 0; i < city.getShipsOnParking().size(); i++) {
                        Rectangle temp = new Rectangle(10,10);
                        temp.setFill(Color.RED);
                        parking.getChildren().add(temp);
                    }
                    VBox info = (VBox) borderPane.getChildren().get(3);
                    info.getChildren().clear();
                    Label cityStorage = new Label();
                    if(city instanceof MaterialBaseCity){
                        cityStorage.setText(String.valueOf(((MaterialBaseCity)city).getMaterialsWeight()));
                    } else if (city instanceof FactoryCity){
                        cityStorage.setText(String.valueOf(((FactoryCity)city).getProductionWeight()));
                    } else {
                        cityStorage.setText(String.valueOf(((ConsumerCity)city).getProductionWeight()));
                    }
                    info.getChildren().add(cityStorage);
                    city.getShipsOnParking().forEach(ship -> {
                        Label temp = new Label(ship.getName() + " " + ship.getCargoWeight());
                        info.getChildren().add(temp);
                    });
                });
                MaterialBaseCity.getMaterialBases().forEach(city -> {
                    if(l % 10000 == 0) city.setMaterialsWeight(city.getMaterialsWeight()+10);
                });
                FactoryCity.getFactoryCities().forEach(city -> {
                    if(l % 10000 == 0 && city.getMaterialsWeight()-city.getProductionWeight() > 0){
                        city.setMaterialsWeight(city.getMaterialsWeight()-10);
                        city.setProductionWeight(city.getProductionWeight()+10);
                    }
                });
                ConsumerCity.getConsumerCities().forEach(consumerCity -> {
                    if(l % 10000 == 0 && consumerCity.getProductionWeight() > 10)consumerCity.setProductionWeight(consumerCity.getProductionWeight()-10);
                });
                shipPositions.forEach((ship,pane) -> {
                    if (ship.isDeleted()){
                        pane.setLayoutX(0);
                        pane.setLayoutY(0);
                        pane.setVisible(false);
                        shipPositions.remove(ship);
                        return;
                    }
                    pane.setVisible(ship.getParkingCity() == null);
                    pane.setLayoutX(ship.getShipX());
                    pane.setLayoutY(ship.getShipY());
                    Rectangle cargo = (Rectangle) pane.getChildren().get(1);
                    switch (ship.getCargoType()){
                        case NONE -> {
                           cargo.setFill(Color.TRANSPARENT);
                           cargo.setStroke(Color.BLACK);
                        }
                        case MATERIALS -> cargo.setFill(new ImagePattern(materialImg));
                        case PRODUCTION -> cargo.setFill(new ImagePattern(productionImg));
                    }
                });
                shipsOnMinimap.forEach((ship,pane) -> {
                    if (ship.isDeleted()){
                        pane.setLayoutX(0);
                        pane.setLayoutY(0);
                        pane.setVisible(false);
                        shipPositions.remove(ship);
                        return;
                    }
                    pane.setLayoutX(ship.getShipX() * SCALE);
                    pane.setLayoutY(ship.getShipY() * SCALE);
                });
                visibleArea.setLayoutX(scrollPane.getHvalue() * map.getWidth() * SCALE - (scrollPane.getHvalue() * (screen.getWidth() * SCALE)));
                visibleArea.setLayoutY(scrollPane.getVvalue() * map.getHeight() * SCALE - (scrollPane.getVvalue() * (screen.getHeight() * SCALE)));
                if(checkCity.isSelected()){
                    cityBox.setVisible(true);
                    cityBoxLabel.setVisible(true);
                } else {
                    cityBox.setVisible(false);
                    cityBoxLabel.setVisible(false);
                }
            }
        };

        mainTimer.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
