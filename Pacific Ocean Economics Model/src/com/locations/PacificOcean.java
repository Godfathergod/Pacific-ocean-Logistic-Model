package com.locations;

import com.ship.Shuttle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class PacificOcean {
    private static final PacificOcean universalObject = new PacificOcean();
    public static PacificOcean getPacificOcean(){
        return universalObject;
    }
    private PacificOcean(){}
    private final Set<City> allCities = City.getAllCities();
    private final List<Shuttle> shuttleList = new CopyOnWriteArrayList<>();
    private double totalCargoWeight = 0;

    public void addShip(Shuttle sh) {
        shuttleList.add(sh);
    }

    public Set<City> getAllCities() {
        return allCities;
    }

    public List<Shuttle> getShipList() {
        return shuttleList;
    }

    public double getTotalCargoWeight() {
        return totalCargoWeight;
    }

    public void setTotalCargoWeight(double totalCargoWeight) {
        this.totalCargoWeight = totalCargoWeight;
    }
}
