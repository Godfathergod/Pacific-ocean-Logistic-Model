package com.locations;

import com.ship.Shuttle;
import java.util.ArrayList;
import java.util.List;

public class PacificOcean {
    private static final PacificOcean universalObject = new PacificOcean();
    public static PacificOcean getPacificOcean(){
        return universalObject;
    }
    private PacificOcean(){}

    private List<City> allCities = City.getAllCities();
    private List<Shuttle> shuttleList = new ArrayList<>();

    public void addShip(Shuttle sh) {
        shuttleList.add(sh);
    }

    public void deleteShip(Shuttle sh) {
        shuttleList.remove(sh);
    }
    public List<City> getAllCities() {
        return allCities;
    }

    public List<Shuttle> getShipList() {
        return shuttleList;
    }


}
