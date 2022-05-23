package com.ship;

import com.locations.City;
import javafx.scene.image.Image;

public class ContainerShip extends Bulker {
    private static int numOfContainerShips;
    {
        shipImg = new Image("img/containership.png");
        maxWeight = 600;
    }
    public ContainerShip(String shipName, int shipSpeed, City value) {
        super(shipName,shipSpeed,value);
    }

    public static int getNumOfContainerShips() {
        return numOfContainerShips;
    }
    public ContainerShip(String name, City baseCity, int weight) {
        super(name,baseCity,weight);
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
                '}';
    }
}
