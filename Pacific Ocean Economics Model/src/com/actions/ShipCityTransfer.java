package com.actions;

import com.ship.Shuttle;
import com.locations.City;

public class ShipCityTransfer implements Transfer{
    private Shuttle giver;
    private City receiver;
    private double cargo;
    private boolean isExecuted = false;
    @Override
    public void start() {
        if(isExecuted){
            return;
        }
        giver.setCargoWeight(giver.getCargoWeight() - this.cargo);
        receiver.setCargoWeight(receiver.getCargoWeight() + this.cargo);
        System.out.println("Корабель " + giver.getName() + " передав " + this.cargo + " місту " + receiver.getName());
        isExecuted = true;
    }
    public ShipCityTransfer(Shuttle giver, City reciever, double cargo) {
        if(giver.getCargoWeight() < cargo) {
            System.out.println("Impossible deal.");
            isExecuted = true;
            return;
        }
        this.giver = giver;
        this.receiver = reciever;
        this.cargo = cargo;
    }
}
