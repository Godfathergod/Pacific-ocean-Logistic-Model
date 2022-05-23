package com.locations;

import com.ship.Shuttle;
import com.utils.CargoType;

import java.util.List;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class City implements Cloneable,Comparable<City>{

   protected final static Set<City> allCities;
   protected String name;
   protected  Size citySize;
   protected CargoType cargoType;
   protected final List<Shuttle> shipsOnParking = new CopyOnWriteArrayList<>();
   protected double mapX;
   protected double mapY;
   static {
      allCities = new HashSet<>();
      allCities.addAll(MaterialBaseCity.getMaterialBases());
      allCities.addAll(FactoryCity.getFactoryCities());
      allCities.addAll(ConsumerCity.getConsumerCities());
   }
   public void addShip(Shuttle s) {
      shipsOnParking.add(s);
   }
   public void deleteShip(Shuttle s){
         shipsOnParking.remove(s);
   }
   public String getName() {
      return name;
   }
   public Size getCitySize() {
      return citySize;
   }
   public List<? extends Shuttle> getShipsOnParking() {
      return shipsOnParking;
   }

   public static Set<City> getAllCities() {
      return allCities;
   }
   public CargoType getCargoType() {
      return cargoType;
   }
   public double getMapX() {
      return mapX;
   }
   public double getMapY() {
      return mapY;
   }
/*   public abstract void gainShip(Shuttle ship, double cargo);
   public abstract void ungainShip(Shuttle ship, double cargo);*/
   @Override
   public int compareTo(City o) {
      return this.getName().compareTo(o.getName());
   }
}
