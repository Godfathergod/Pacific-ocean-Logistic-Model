package com.locations;

import com.ship.Shuttle;
import com.utils.CargoType;

import java.util.HashSet;
import java.util.Set;

public class FactoryCity extends City{
    private double materialsWeight = 1000;
    private double productionWeight = 1000;
    private static final Set<FactoryCity> factoryCities;
    private FactoryCity(String name,Size size,double x, double y){
        super();
        this.name = name;
        this.citySize = size;
        this.mapX = x;
        this.mapY = y;
    }
    static {
        factoryCities = new HashSet<>(Set.of(new FactoryCity("Пусан",Size.MEDIUM,330,875),
                new FactoryCity("Токіо",Size.LARGE,580,900),
                new FactoryCity("Шанхай",Size.LARGE,215,985),
                new FactoryCity("Тайбей",Size.SMALL,225,1165),
                new FactoryCity("Панама",Size.SMALL,3000,1430)));
        City.getAllCities().addAll(factoryCities);
    }
    public double getMaterialsWeight() {
        return materialsWeight;
    }
    public void setMaterialsWeight(double materialsWeight) {
        this.materialsWeight = materialsWeight;
    }
    public double getProductionWeight() {
        return productionWeight;
    }
    public void setProductionWeight(double productionWeight) {
        this.productionWeight = productionWeight;
    }
    public static Set<FactoryCity> getFactoryCities() {
        return factoryCities;
    }
/*    public void gainShip(Shuttle shuttle, double cargo){
        while(materialsWeight < cargo){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        shuttle.setCargoType(CargoType.PRODUCTION);

        if(shuttle.getCapacity() - shuttle.getCargoWeight() - cargo < 0) {
            materialsWeight -= shuttle.getCapacity() - shuttle.getCargoWeight();
            PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() + shuttle.getCapacity() - shuttle.getCargoWeight());
            shuttle.setCargoWeight(shuttle.getCargoWeight() + shuttle.getCapacity() - shuttle.getCargoWeight());
            return;
        }
        productionWeight -= cargo;
        PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() + cargo);
        shuttle.setCargoWeight(shuttle.getCargoWeight() + cargo);
    }
    public void ungainShip(Shuttle shuttle,double cargo){

        if(shuttle.getCargoWeight() - cargo <= 0) {
            materialsWeight += shuttle.getCargoWeight();
            PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() - shuttle.getCargoWeight());
            shuttle.setCargoWeight(0);
            shuttle.setCargoType(CargoType.NONE);
            return;
        }
        materialsWeight += cargo;
        PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() - cargo);
        shuttle.setCargoWeight(shuttle.getCargoWeight() - cargo);
    }*/
}
