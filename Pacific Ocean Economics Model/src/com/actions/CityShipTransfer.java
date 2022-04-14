package com.actions;

import com.actions.Transfer;
import com.application.Shuttle;
import com.locations.City;

public class CityShipTransfer implements Transfer {
    private City giver;
    private Shuttle receiver;
    private double cargo;
    private boolean isExecuted = false;
    @Override
    public void start() {
        if(isExecuted){
            return;
        }
        giver.setMaterials(giver.getMaterials() - this.cargo);
        receiver.setCargoWeight(receiver.getCargoWeight() + this.cargo);
        System.out.println("Місто " + giver.getName() + " передало " + this.cargo + " об'єкту " + receiver.getName());
        isExecuted = true;
    }
    public CityShipTransfer(City giver, Shuttle reciever, double cargo) {
        if(giver.getMaterials() < cargo || (reciever.getCapacity() - reciever.getCargoWeight()) < cargo) {
            System.out.println("Impossible deal.");
            isExecuted = true;
            return;
        }
        this.giver = giver;
        this.receiver = reciever;
        this.cargo = cargo;
    }
}
