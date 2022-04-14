package com.ship;

import com.actions.ShipTransfer;
import com.locations.City;

public class Balker extends Shuttle {
    public Balker(String name, int capacity, int weight, City baseCity) {
        super(name, capacity, weight, baseCity);
    }
    public Balker(){
        super();
    }
    public void exChangeWithShip(Balker ship,double cargo) {
        new ShipTransfer(this,ship,cargo).start();
    }
    @Override
    public String toString() {
        return "Balker{" +
                "name='" + super.getName() + '\'' +
                ", capacity=" + super.getCapacity() +
                ", weight=" + super.getWeight() +
                ", cargoWeight=" + super.getCargoWeight() +
                ", experience=" + super.getExperience() +
                '}';
    }
}
