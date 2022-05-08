package com.ship;

import com.locations.City;

public class ContainerShip extends Bulker {
    private static int numOfContainerShips;
    public static int getNumOfContainerShips() {
        return numOfContainerShips;
    }
    public ContainerShip(String name, int capacity, int weight, City baseCity) {
        super(name, capacity, weight, baseCity);
        ++numOfContainerShips;
    }
   public ContainerShip(){
        super();
       ++numOfContainerShips;
   }
    @Override
    public String toString() {
        return  "ContainerShip{" +
                "name='" + super.getName() + '\'' +
                ", capacity=" + super.getCapacity() +
                ", weight=" + weight +
                ", cargoWeight=" + super.getCargoWeight() +
                ", experience=" + super.getExperience() +
                '}';
    }
}
