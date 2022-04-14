package com.locations;

import com.ship.Shuttle;

import java.util.*;

public class City implements Cloneable,Comparable<City>{

   private final static List<City> allCities;
   private final String name;
   private  LocationType cityType;
   private  Size citySize;
   private CargoType cargoType;
   private double cargoWeight = 1000;
   private final List<Shuttle> shipsOnParking = new ArrayList<>();
   ///
   public boolean addShip(Shuttle s) {
      return shipsOnParking.add(s);
   }

   public boolean deleteShip(Shuttle s){
      return shipsOnParking.remove(s);
   }

   public String getName() {
      return name;
   }

   public LocationType getCityType() {
      return cityType;
   }

   public Size getCitySize() {
      return citySize;
   }

   public double getCargoWeight() {
      return cargoWeight;
   }

   public void setCargoWeight(double cargoWeight) {
      this.cargoWeight = cargoWeight;
   }

   public List<? extends Shuttle> getShipsOnParking() {
      return shipsOnParking;
   }

   public static List<City> getAllCities() {
      return allCities;
   }
   ///

   static {
      allCities = List.of(new City("Владивосток",LocationType.MATERIALBASE,Size.SMALL),
              new City("Пусан",LocationType.FACTORY,Size.MEDIUM),
              new City("Токіо",LocationType.FACTORY,Size.LARGE),
              new City("Шанхай",LocationType.FACTORY,Size.LARGE),
              new City("Тайбей",LocationType.FACTORY,Size.SMALL),
              new City("Гуанчжоу",LocationType.MATERIALBASE,Size.LARGE),
              new City("Брісбен",LocationType.MATERIALBASE,Size.SMALL),
              new City("Сідней",LocationType.CITYCONSUMER,Size.LARGE),
              new City("Веллінгтон",LocationType.CITYCONSUMER,Size.SMALL),
              new City("Пуерто-Монт",LocationType.MATERIALBASE,Size.SMALL),
              new City("Ліма",LocationType.MATERIALBASE,Size.LARGE),
              new City("Гуаякіль", LocationType.CITYCONSUMER,Size.MEDIUM),
              new City("Панама",LocationType.FACTORY,Size.SMALL),
              new City("Лос-Анджелес",LocationType.CITYCONSUMER,Size.LARGE),
              new City("Сан-Франциско",LocationType.CITYCONSUMER,Size.LARGE),
              new City("Анкорідж",LocationType.MATERIALBASE,Size.MEDIUM));
   }

   private City(String name,LocationType type,Size size){
      this.name = name;
      this.cityType = type;
      this.citySize = size;
   }
   public City(String name){
      this.name = name;
   }
   //////
   @Override
   public String toString() {
      return "Port{" +
              "name='" + name + '\'' +
              ", portType=" + cityType +
              ", portSize=" + citySize +
              '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof City)) return false;
      City city = (City) o;
      return Double.compare(city.getCargoWeight(),
              getCargoWeight()) == 0 && Objects.equals(getName(),
              city.getName()) && getCityType() == city.getCityType() && getCitySize() == city.getCitySize() && Objects.equals(getShipsOnParking(),
              city.getShipsOnParking());
   }

   @Override
   public int hashCode() {
      return Objects.hash(getName(), getCityType(), getCitySize(), getCargoWeight(), getShipsOnParking());
   }

   @Override
   public City clone() throws CloneNotSupportedException {
      return (City) super.clone();
   }

   @Override
   public int compareTo(City o) {
      return this.getName().compareTo(o.getName());
   }
}
