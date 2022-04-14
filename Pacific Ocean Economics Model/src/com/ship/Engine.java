package com.ship;

public class Engine {
    private static final int maxEnergy = 100;
    private static final int maxSpeed = 10;
    private int speed;
    private int energy;
    public Engine(int speed,int energy) {
        this.speed = speed;
        this.energy = energy;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
