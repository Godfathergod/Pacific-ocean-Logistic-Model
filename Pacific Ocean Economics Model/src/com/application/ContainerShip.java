package com.application;

import com.locations.City;

public class ContainerShip extends Balker {

    public ContainerShip(String name, int capacity, int weight, City baseCity) {
        super(name, capacity, weight, baseCity);
    }
   public ContainerShip(){
        super();
   }
    @Override
    public String toString() {
        return  "ContainerShip{" +
                "name='" + super.getName() + '\'' +
                ", capacity=" + super.getCapacity() +
                ", weight=" + super.getWeight() +
                ", cargoWeight=" + super.getCargoWeight() +
                ", experience=" + super.getExperience() +
                '}';
    }
}
