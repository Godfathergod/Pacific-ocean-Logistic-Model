package com.locations;

import java.util.HashSet;
import java.util.Set;

public class MaterialBaseCity extends City{
    private double materialsWeight = 10000;
    private static final Set<MaterialBaseCity> materialBases;
    private MaterialBaseCity(String name,Size size,double x, double y){
        super();
        this.name = name;
        this.citySize = size;
        this.mapX = x;
        this.mapY = y;
    }
   static {
        materialBases = new HashSet<>(Set.of(new MaterialBaseCity("Владивосток",Size.SMALL,412,725),
                new MaterialBaseCity("Гуанчжоу",Size.LARGE,40,1200),
                new MaterialBaseCity("Брісбен",Size.SMALL,770,2050),
                new MaterialBaseCity("Пуерто-Монт",Size.SMALL,3100,2375),
                new MaterialBaseCity("Ліма",Size.LARGE,3075,1850),
                new MaterialBaseCity("Анкорідж",Size.MEDIUM,1775,275)));
       City.getAllCities().addAll(materialBases);
    }
    public double getMaterialsWeight() {
        return materialsWeight;
    }

    public void setMaterialsWeight(double materialsWeight) {
        this.materialsWeight = materialsWeight;
    }

    public static Set<MaterialBaseCity> getMaterialBases() {
        return materialBases;
    }
   /* public void gainShip(Shuttle shuttle, double cargo){
        while(materialsWeight < cargo){
           try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
            shuttle.setCargoType(CargoType.MATERIALS);

        if(shuttle.getCapacity() - shuttle.getCargoWeight() - cargo < 0) {
            materialsWeight -= shuttle.getCapacity() - shuttle.getCargoWeight();
            PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() + shuttle.getCapacity() - shuttle.getCargoWeight());
            shuttle.setCargoWeight(shuttle.getCargoWeight() + shuttle.getCapacity() - shuttle.getCargoWeight());
            return;
        }
        materialsWeight -= cargo;
        PacificOcean.getPacificOcean().setTotalCargoWeight(PacificOcean.getPacificOcean().getTotalCargoWeight() + cargo);
        shuttle.setCargoWeight(shuttle.getCargoWeight() + cargo);
    }

    @Override
    public void ungainShip(Shuttle ship, double cargo) {
        throw new UnsupportedOperationException();
    }*/

}
