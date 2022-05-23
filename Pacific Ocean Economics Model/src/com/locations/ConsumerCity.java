package com.locations;

import com.ship.Shuttle;
import com.utils.CargoType;

import java.util.HashSet;
import java.util.Set;

public class ConsumerCity extends City{
    private double productionWeight = 1000;
    private static Set<ConsumerCity> consumerCities;
    private ConsumerCity(String name,Size size,double x, double y){
        super();
        this.name = name;
        this.citySize = size;
        this.mapX = x;
        this.mapY = y;

    }
    static    {
        consumerCities = new HashSet<>(Set.of(new ConsumerCity("Сідней",Size.LARGE,745,2270),
                new  ConsumerCity("Веллінгтон",Size.SMALL,1170,2300),
                new  ConsumerCity("Гуаякіль",Size.MEDIUM,2975,1650),
                new  ConsumerCity("Лос-Анджелес",Size.LARGE,2275,950),
                new  ConsumerCity("Сан-Франциско",Size.LARGE,2230,800)));
        City.getAllCities().addAll(consumerCities);
    }
    public double getProductionWeight() {
        return productionWeight;
    }
    public void setProductionWeight(double productionWeight) {
        this.productionWeight = productionWeight;
    }
    public static Set<ConsumerCity> getConsumerCities() {
        return consumerCities;
    }

    @Override
    public void gainShip(Shuttle ship, double cargo) {
        throw new UnsupportedOperationException();
    }

    public void ungainShip(Shuttle shuttle, double cargo){

        if(shuttle.getCargoWeight() - cargo <= 0) {
            productionWeight += shuttle.getCargoWeight();
            PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() - shuttle.getCargoWeight());
            shuttle.setCargoWeight(0);
            shuttle.setCargoType(CargoType.NONE);
            return;
        }
        productionWeight += cargo;
        PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() - cargo);
        shuttle.setCargoWeight(shuttle.getCargoWeight() - cargo);
    }
}
