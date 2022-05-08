package com.ship;

import com.actions.ShipTransfer;
import com.locations.City;

public class Bulker extends Shuttle {
    private static int numOfBulkers;

    public static int getNumOfBulkers() {
        return numOfBulkers;
    }

    public Bulker(String name, int capacity, int weight, City baseCity) {
        super(name, capacity, weight, baseCity);
        ++numOfBulkers;
    }
    public Bulker(){
        super();
        ++numOfBulkers;
    }
    public void exChangeWithShip(Bulker ship, double cargo) {
        new ShipTransfer(this,ship,cargo).start();
    }
    @Override
    public String toString() {
        return "Balker{" +
                "name='" + super.getName() + '\'' +
                ", capacity=" + super.getCapacity() +
                ", weight=" + weight +
                ", cargoWeight=" + super.getCargoWeight() +
                ", experience=" + super.getExperience() +
                '}';
    }
}
