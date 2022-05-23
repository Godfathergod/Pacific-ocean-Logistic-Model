package com.utils;

import com.ship.Bulker;

public class ShipTransfer {
    private Bulker giver;
    private Bulker receiver;
    private double cargo;
    private boolean isExecuted = false;
    public void start() {
        if(isExecuted){
            return;
        }
        giver.setCargoWeight(giver.getCargoWeight() - this.cargo);
        receiver.setCargoWeight(receiver.getCargoWeight() + this.cargo);
        receiver.setCargoType(giver.getCargoType());
        System.out.println(giver.getName() + " передав " + this.cargo + " об'єкту " + receiver.getName());
        isExecuted = true;
    }
    public ShipTransfer(Bulker giver, Bulker reciever, double cargo) {
        if(giver.getCargoWeight() < cargo || (reciever.getCapacity() - reciever.getCargoWeight()) < cargo) {
            System.out.println("Impossible deal.");
            isExecuted = true;
            return;
        }
        this.giver = giver;
        this.receiver = reciever;
        this.cargo = cargo;
    }
}
