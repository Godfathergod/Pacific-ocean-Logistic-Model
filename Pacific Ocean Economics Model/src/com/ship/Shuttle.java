package com.ship;
import com.locations.CargoType;
import com.locations.City;
import com.locations.LocationType;
import com.locations.PacificOcean;

import java.util.*;

public class Shuttle implements Cloneable, Comparable<Shuttle>{
    private static int numberOfShips;
    private static double totalCargoWeight;
    protected static int maxExp = 100;

    public static int getNumberOfShips() {
        return numberOfShips;
    }

    public static double getTotalCargoWeight() {
        return totalCargoWeight;
    }

    private String name;
    private final int capacity;
    protected final int weight;
    private double cargoWeight;
    private CargoType cargoType;
    private int experience;
    private City baseCity;
    private City parkingCity;
    private Engine engine;
    protected double shipX;
    protected double shipY;

    static {
        numberOfShips = 0;
        totalCargoWeight = 0;
    }

    {
        cargoType = CargoType.NONE;
        experience = 0;
        cargoWeight = 0;
        engine = new Engine(rand.nextInt(1,Engine.maxSpeed+1));
    }

    //геттери та сеттери

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public double getShipX() {
        return shipX;
    }

    public void setShipX(double shipX) {
        this.shipX = shipX;
    }

    public double getShipY() {
        return shipY;
    }

    public void setShipY(double shipY) {
        this.shipY = shipY;
    }

    public City getParkingCity() {
        return parkingCity;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public String getName() {
        return name;
    }

    public double getCargoWeight() {
        return cargoWeight;
    }

    public void setCargoWeight(double cargoWeight) {
        if(cargoWeight < 0 || cargoWeight > capacity) {
            System.out.println("Недопустима вага вантажу.");
            return;
        }
        this.cargoWeight = cargoWeight;
    }

    public int getExperience() {
        return experience;
    }

    //геттери для фіналізованих
    public City getBaseCity() {
        return baseCity;
    }

    public int getCapacity() {
        return capacity;
    }

    //конструктори
    public Shuttle(String name, int capacity, int weight, City baseCity) {
        this.name = name;
        this.capacity = capacity;
        this.weight = weight;
        this.baseCity = baseCity;
        numberOfShips++;
        totalCargoWeight += this.cargoWeight;
        PacificOcean.getPacificOcean().addShip(this);
    }

    public Shuttle() {
        this(lastNames[rand.nextInt(18)] + " " + firstNames[rand.nextInt(18)],
                rand.nextInt(50,250),
                rand.nextInt(75,300),
                City.getAllCities()
                        .get(rand.nextInt(City.getAllCities().size())));
        if(!rand.nextBoolean()) {
            this.stayInCity(City.getAllCities()
                    .get(rand.nextInt(City.getAllCities().size())));
        }
        if (parkingCity == null){
            setShipX(rand.nextInt(415,3100));
            setShipY(rand.nextInt(275,2250));
        } else{
            setShipY(parkingCity.getMapY());
            setShipX((parkingCity.getMapX()));
        }
    }
//вивід об'єкта на екран
    public void print() {
        System.out.println(this.toString());
    }
    //функції життєдіяльності об'єкта
    public void automaticLife() {
        new Thread(() -> {
            while(experience != maxExp){
                moveTo(findMaxCargoCity());
                gain();
                switch (this.getCargoType()){
                    case MATERIALS -> {
                        moveTo(findMinCargoCity(LocationType.FACTORY));
                        ungain();
                    }
                    case PRODUCTION -> {
                        moveTo((findMinCargoCity(LocationType.CITYCONSUMER)));
                        ungain();
                    }
                }

            }
        }).start();

    }
    public void moveTo(City city){
            leaveCity(this.parkingCity);
         while(!(this.getShipX() < city.getMapX()+20 && this.getShipX() > city.getMapX()-20
                 && this.getShipY() < city.getMapY()+20 && this.getShipY() > city.getMapY()-20)) {
             if(this.getShipX() < city.getMapX()+10){
                 this.shipX += engine.getSpeed() * 0.1;
             } else if(this.getShipX() > city.getMapX()-10){
                 this.shipX -= engine.getSpeed() * 0.1;
             }
             if(this.getShipY() < city.getMapY()+10){
                 this.shipY += engine.getSpeed() * 0.1;
             } else if(this.getShipY() > city.getMapY()-10){
                 this.shipY -= engine.getSpeed() * 0.1;
             }
             try {
                 Thread.sleep(10);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         stayInCity(city);
    }
    public City findMaxCargoCity() {
        //пошук міста з найбільшою кількістю вантажу
            return City.getAllCities()
                    .stream()
                    .filter(city -> city.getCityType() != LocationType.CITYCONSUMER)
                    .max((c1,c2) -> (int) (c1.getCargoWeight() - c2.getCargoWeight())).get();
    }
    public City findMaxCargoCity(LocationType type) {
        //пошук міста з найбільшою кількістю вантажу за типом міста
        return City.getAllCities()
                .stream()
                .filter(city -> city.getCityType() == type)
                .max((c1,c2) -> (int) (c1.getCargoWeight() - c2.getCargoWeight())).get();
    }
    public City findMinCargoCity() {
        //пошук міста з найменшою кількістю вантажу
        return City.getAllCities()
                .stream()
                .min((c1,c2) -> (int) (c1.getCargoWeight() - c2.getCargoWeight())).get();
    }
    public City findMinCargoCity(LocationType type) {
        //пошук міста з найменшою кількістю вантажу за типом міста
        return City.getAllCities()
                .stream()
                .filter(city -> city.getCityType() == type && city.getCargoWeight() < 100)
                .findAny().get();
    }
    public void stayInCity(City city){
        city.addShip(this);
        parkingCity = city;
        System.out.println(this.getName() + " прибув до " + city);
    }
    public void leaveCity(City city){
        try{
            city.deleteShip(this);
        }catch (NullPointerException ignored){

        }
        parkingCity = null;
        System.out.println(this.getName() + " покинув " + city);
    }
    public void gain(){
        if(parkingCity == null){
            return;
        }
           while(cargoWeight != capacity || parkingCity.getCargoWeight() < 10){
               try {
                   parkingCity.gainShip(this,25);
               } catch (UnsupportedOperationException e) {
                   System.out.println("AAAAAAAAAAAAAA");
               }
               try {
                   Thread.sleep(100);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
    }
    public void ungain(){
        if(parkingCity == null){
            return;
        }
        while(cargoWeight != 0 && parkingCity.getCargoWeight() < 2000){
            try {
                parkingCity.ungainShip(this,25);
            } catch (UnsupportedOperationException e) {
                System.out.println("AAAAAAAAAAAAAA");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //базові функції Object
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shuttle shuttle)) return false;
        return capacity == shuttle.capacity && Double.compare(shuttle.weight, weight) == 0
                && Double.compare(shuttle.cargoWeight, cargoWeight) == 0 && name.equals(shuttle.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity, weight, cargoWeight);
    }

    @Override
    public String toString() {
        return "Shuttle{" +
                "name='" + getName() + '\'' +
                ", capacity=" + getCapacity() +
                ", weight=" + weight +
                ", cargoWeight=" + getCargoWeight() +
                ", experience=" + getExperience() +
                '}';
    }
    //клонування
    @Override
    public Shuttle clone() {
        try {
            Shuttle ret = (Shuttle) super.clone();
            ret.engine = this.engine.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
    //імена
    private static Random rand = new Random(1868048);
    private static String[] firstNames =
            {"Maximus","George III",
                    "Anna Maria","Dick",
                    "Nemo","Dream",
                    "Holland", "Shpitzbergen",
                    "Bamako", "Jakarta",
                    "Jhon", "Denis",
                    "Kitty","Puppy",
                    "Andrew","Xiaomi",
                    "Apple","Mile"};
    private static String[] lastNames =
            {"Captain","Admiral",
                    "Green","Big",
                    "Little","Tiny",
                    "Great","Awesome",
                    "Queen","King",
                    "Mama","Baby",
                    "Cute","Beautiful",
                    "Depressed","Cool",
                    "Sleepy","Shy"};
    //компаратори
    @Override
    public int compareTo(Shuttle s) {
        return this.name.compareTo(s.getName());
    }

    static class ComparingByExp implements Comparator<Shuttle> {
        @Override
        public int compare(Shuttle o1, Shuttle o2) {
            return o1.getExperience() - o2.getExperience();
        }
    }
    static class ComparingByCapacity implements Comparator<Shuttle> {
        @Override
        public int compare(Shuttle o1, Shuttle o2) {
            return o1.getCapacity() - o2.getCapacity();
        }
    }
    static class ComparingByCargoWeight implements Comparator<Shuttle> {
        @Override
        public int compare(Shuttle o1, Shuttle o2) {
            return (int) Math.round(o1.getCargoWeight() - o2.getCargoWeight());
        }
    }
}
