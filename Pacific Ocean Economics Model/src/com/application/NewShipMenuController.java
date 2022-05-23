package com.application;

import com.locations.City;
import com.ship.Shuttle;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class NewShipMenuController {
    @FXML
    AnchorPane window;
    @FXML
    TextField name;
    @FXML
    TextField speed;
    @FXML
    CheckBox checkCity;
    @FXML
    ChoiceBox<Shuttle> degreeBox;
    @FXML
    ChoiceBox<City> cityBox;

    public void initNewShipMenu(){

    }
}
