package com.actions;

import com.ship.Balker;

public class ShipTransfer implements Transfer {
    private Balker giver;
    private Balker receiver;
    private double cargo;
    private boolean isExecuted = false;
    @Override
    public void start() {
        if(isExecuted){
            return;
        }
        giver.setCargoWeight(giver.getCargoWeight() - this.cargo);
        receiver.setCargoWeight(receiver.getCargoWeight() + this.cargo);
        System.out.println(giver.getName() + " передав " + this.cargo + " об'єкту " + receiver.getName());
        isExecuted = true;
    }
    public ShipTransfer(Balker giver, Balker reciever, double cargo) {
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
