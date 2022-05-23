package com.ship;

import com.locations.PacificOcean;
import com.utils.CargoType;
import com.utils.ShipTransfer;
import com.locations.City;
import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;

public class Bulker extends Shuttle {
    private static int numOfBulkers;
    private boolean onExChange = false;
    protected static Comparator<? extends Bulker> ComparingByCapacity;
    {
        shipImg = new Image("img/bulker.png");
        maxWeight = 400;
    }
    public Bulker(String shipName, int shipSpeed, City value) {
        super(shipName,shipSpeed,value);
    }

    public static int getNumOfBulkers() {
        return numOfBulkers;
    }

    public Bulker(String name,City baseCity,int weight) {
        super(name, baseCity,weight);
        ++numOfBulkers;
    }
    public Bulker(){
        super();

        ++numOfBulkers;
    }
    @Override
    public void automaticLife() {
        this.autoLife = new Thread(() -> {
            while (!Thread.interrupted()) {
                if (targetCity != null) {
                    moveTo(targetCity);
                    ungain();
                    targetCity = null;
                }

                if(this.getCargoType() == CargoType.NONE){
                    moveTo(findMaxCargoCity());
                    if(Thread.interrupted()) break;
                    gain();
                }
               /*synchronized(this){
                    if (rand.nextBoolean()) {
                        int size = (int) PacificOcean.getPacificOcean().getShipList().size();
                        Object[] objectsArray = PacificOcean.getPacificOcean().getShipList().toArray();
                        Bulker[] mainArray = null;
                        while (mainArray == null || mainArray.length == 0) {
                            mainArray = Arrays.stream(objectsArray)
                                    .map(o -> (Shuttle) o)
                                    .filter(shuttle -> shuttle instanceof Bulker
                                            && shuttle.getCargoType() == CargoType.NONE
                                            && !((Bulker) shuttle).onExChange
                                            && shuttle != this)
                                    .toArray(Bulker[]::new);
                            try{
                                Thread.sleep(100);
                            }catch (InterruptedException ignored){}
                        }
                        Arrays.sort(mainArray);
                        Bulker targetShip = mainArray[mainArray.length - 1];
                        targetShip.onExChange = true;
                        targetShip.getAutoLife().interrupt();
                        this.moveTo(targetShip);
                        ShipTransfer shipTransfer = new ShipTransfer(this, targetShip, this.getCargoWeight() / 2);
                        shipTransfer.start();
                        onExChange = false;
                        targetShip.targetCity = findCityforUngain(targetShip.getCargoType());
                        if(!targetShip.isActive())targetShip.automaticLife();
                    }
                }*/
                switch (this.getCargoType()) {
                    case MATERIALS -> {
                        moveTo(findCityforUngain(CargoType.MATERIALS));
                        ungain();
                    }
                    case PRODUCTION -> {
                        moveTo((findCityforUngain(CargoType.PRODUCTION)));
                        ungain();
                    }
                }

            }
        });
        autoLife.setDaemon(true);
        autoLife.setName("Thread " + this.getName());
        autoLife.start();
    }
    public void exChangeWithShip(Bulker ship, double cargo) {
        new ShipTransfer(this,ship,cargo).start();
    }

    @Override
    public String toString() {
        return "Bulker{" +
                "onExChange=" + onExChange +
                ", weight=" + weight +
                ",capacity=" + this.getCapacity() +
                ", shipX=" + shipX +
                ", shipY=" + shipY +
                ", autoLife=" + autoLife +
                ", shipImg=" + shipImg +
                ", flagImg=" + flagImg +
                ", targetCity=" + targetCity +
                ", parkingCity=" + this.getParkingCity() +
                '}';
    }
}
