package com.application;
import com.locations.City;
import com.locations.LocationType;
import com.locations.PacificOcean;

import java.util.*;

public class Shuttle implements Cloneable, Comparable<Shuttle>{
    private static int numberOfShips;
    private static double totalCargoWeight;
    private final static List<City> allCities;
    private final static int maxExp = 100;
    private static PacificOcean universal = PacificOcean.getPacificOcean();

    public static int getNumberOfShips() {
        return numberOfShips;
    }

    public static double getTotalCargoWeight() {
        return totalCargoWeight;
    }

    public static List<City> getAllCities() {
        return allCities;
    }

    private String name;
    private final int capacity;
    private final int weight;
    private double cargoWeight;
    private int experience;
    private City baseCity;
    private City parkingCity;
    private final Engine engine;

    static {
        numberOfShips = 0;
        totalCargoWeight = 0;
        allCities = City.getAllCities();
    }

    {
        experience = 0;
        cargoWeight = 0;
        engine = new Engine(rand.nextInt(5,10),100);
    }

    //геттери та сеттери

    public City getParkingCity() {
        return parkingCity;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setExperience(int experience) {
        this.experience = experience;
    }

    //геттери для фіналізованих
    public City getBaseCity() {
        return baseCity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getWeight() {
        return weight;
    }
    //конструктори
    public Shuttle(String name, int capacity, int weight, City baseCity) {
        this.name = name;
        this.capacity = capacity;
        this.weight = weight;
        this.baseCity = baseCity;
        numberOfShips++;
        totalCargoWeight += this.cargoWeight;
        universal.addShip(this);
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
    }

    public void print() {
        System.out.println(this.toString());
    }
    //функції життєдіяльності об'єкта
    public void automaticLife() {

    }
    public void moveTo(City city){
        try {
            leaveCity(this.parkingCity);
        }catch (NullPointerException ignored){}
       stayInCity(city);
    }
    public void findOrder() {
        //пошук міста з найбільшою кількістю матеріалів та відправлення на загрузку в те місто
            City aimCity = getAllCities()
                    .stream()
                    .filter((city) ->  city.getCityType() == LocationType.MATERIALBASE)
                    .max((c1,c2) -> (int) (c1.getMaterials() - c2.getMaterials())).get();
        moveTo(aimCity);
    }
    public void stayInCity(City city){
        city.addShip(this);
        parkingCity = city;
        System.out.println(this.getName() + " прибув до " + city);
    }
    public void leaveCity(City city) throws NullPointerException{
        city.deleteShip(this);
        parkingCity = null;
        System.out.println(this.getName() + " покинув " + city);
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
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", weight=" + weight +
                ", cargoWeight=" + cargoWeight +
                ", experience=" + experience +
                '}';
    }

    @Override
    public Shuttle clone() {
        try {
            Shuttle ret = (Shuttle) super.clone();
            ret.baseCity = this.baseCity.clone();
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
    static class ComparingByWeight implements Comparator<Shuttle> {
        @Override
        public int compare(Shuttle o1, Shuttle o2) {
            return o1.getWeight() - o2.getWeight();
        }
    }
    static class ComparingByCargoWeight implements Comparator<Shuttle> {
        @Override
        public int compare(Shuttle o1, Shuttle o2) {
            return (int) Math.round(o1.getCargoWeight() - o2.getCargoWeight());
        }
    }
}
