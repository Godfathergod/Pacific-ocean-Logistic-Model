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
   private double mapX;
   private double mapY;
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

   public CargoType getCargoType() {
      return cargoType;
   }

   public double getMapX() {
      return mapX;
   }

   public double getMapY() {
      return mapY;
   }
   ///

   static {
      allCities = List.of(new City("Владивосток",LocationType.MATERIALBASE,Size.SMALL,412,725),
              new City("Пусан",LocationType.FACTORY,Size.MEDIUM,330,875),
              new City("Токіо",LocationType.FACTORY,Size.LARGE,580,900),
              new City("Шанхай",LocationType.FACTORY,Size.LARGE,215,985),
              new City("Тайбей",LocationType.FACTORY,Size.SMALL,225,1165),
              new City("Гуанчжоу",LocationType.MATERIALBASE,Size.LARGE,40,1200),
              new City("Брісбен",LocationType.MATERIALBASE,Size.SMALL,770,2050),
              new City("Сідней",LocationType.CITYCONSUMER,Size.LARGE,745,2270),
              new City("Веллінгтон",LocationType.CITYCONSUMER,Size.SMALL,1170,2300),
              new City("Пуерто-Монт",LocationType.MATERIALBASE,Size.SMALL,3100,2375),
              new City("Ліма",LocationType.MATERIALBASE,Size.LARGE,3075,1850),
              new City("Гуаякіль", LocationType.CITYCONSUMER,Size.MEDIUM,2975,1650),
              new City("Панама",LocationType.FACTORY,Size.SMALL,3000,1430),
              new City("Лос-Анджелес",LocationType.CITYCONSUMER,Size.LARGE,2275,950),
              new City("Сан-Франциско",LocationType.CITYCONSUMER,Size.LARGE,2230,800),
              new City("Анкорідж",LocationType.MATERIALBASE,Size.MEDIUM,1775,275));
   }
   private City(String name,LocationType type,Size size,double x, double y){
      this.name = name;
      this.cityType = type;
      this.citySize = size;
      this.mapX = x;
      this.mapY = y;
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
