package com.locations;

import com.ship.Shuttle;

import java.util.*;

public class City implements Cloneable,Comparable<City>{

   private final static List<City> allCities;
   private final String name;
   private  LocationType cityType;
   private  Size citySize;
   private CargoType cargoType;
   private double materialsWeight = 1000;
   private double productionWeight = 1000;
   private final List<Shuttle> shipsOnParking = new ArrayList<>();
   private double mapX;
   private double mapY;
   ///

   public void addShip(Shuttle s) {
      shipsOnParking.add(s);
   }

   public void deleteShip(Shuttle s){
         shipsOnParking.remove(s);
   }

   public void setCityType(LocationType cityType) {
      this.cityType = cityType;
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
      return materialsWeight;
   }

   public void setCargoWeight(double cargoWeight) {
      this.materialsWeight = cargoWeight;
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
   public void gainShip(Shuttle shuttle,double cargo){
      while(materialsWeight < cargo){
         try {
            Thread.sleep(1000);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }
      if(shuttle.getCargoType() == CargoType.NONE) {
         shuttle.setCargoType(this.getCargoType());
      } else if(this.cargoType != shuttle.getCargoType()){
         throw new UnsupportedOperationException("Difference between cargoType property");
      }
      if(shuttle.getCapacity() - shuttle.getCargoWeight() - cargo < 0) {
         materialsWeight -= shuttle.getCapacity() - shuttle.getCargoWeight();
         shuttle.setCargoWeight(shuttle.getCargoWeight() + shuttle.getCapacity() - shuttle.getCargoWeight());
         return;
      }
      materialsWeight -= cargo;
      shuttle.setCargoWeight(shuttle.getCargoWeight() + cargo);
   }
   public void ungainShip(Shuttle shuttle,double cargo){

      if(shuttle.getCargoWeight() - cargo < 0) {
         materialsWeight += shuttle.getCargoWeight() - cargo;
         shuttle.setCargoWeight(0);
         shuttle.setCargoType(CargoType.NONE);
         return;
      }
      materialsWeight += cargo;
      shuttle.setCargoWeight(shuttle.getCargoWeight() - cargo);
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
      if(cityType == LocationType.MATERIALBASE){
         this.cargoType = CargoType.MATERIALS;
      } else {
         this.cargoType = CargoType.PRODUCTION;
      }
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
