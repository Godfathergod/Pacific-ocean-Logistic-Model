package com.ship;
import com.locations.*;
import com.utils.CargoType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class Shuttle implements Cloneable, Comparable<Shuttle>{
    private final String name;
    private int capacity;
    protected final int weight;
    private double cargoWeight;
    private CargoType cargoType;
    private final City baseCity;
    private City parkingCity;
    private Engine engine;
    protected double shipX;
    protected double shipY;
    private boolean isDeleted;
    private boolean isActive;
    private boolean onGain;
    private boolean onUngain;
    private static boolean activeWork;
    protected City targetCity;
    protected static int maxWeight = 250;
    protected Image shipImg = new Image("img/shuttle.png");
    protected Image flagImg = new Image("img/flag.png");

    {
        isDeleted = false;
        isActive = false;
        activeWork = true;
        cargoType = CargoType.NONE;
        cargoWeight = 0;
    }

    //геттери та сеттери

    public boolean isOnUngain() {
        return onUngain;
    }

    public void setOnUngain(boolean onUngain) {
        this.onUngain = onUngain;
    }

    public boolean isOnGain() {
        return onGain;
    }

    public void setOnGain(boolean onGain) {
        this.onGain = onGain;
    }

    public City getTargetCity() {
        return targetCity;
    }

    public void setTargetCity(City targetCity) {
        this.targetCity = targetCity;
    }

    public static boolean isActiveWork() {
        return activeWork;
    }

    public static void setActiveWork(boolean flag) {
        activeWork = flag;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setCargoWeight(double cargoWeight) {
        if(cargoWeight < 0 || cargoWeight > capacity) {
            System.out.println("Недопустима вага вантажу.");
            return;
        }
        this.cargoWeight = cargoWeight;
    }

    public City getBaseCity() {
        return baseCity;
    }

    public int getCapacity() {
        return capacity;
    }

    //конструктори
    public Shuttle(String name,City baseCity, int weight) {
        this.name = name;
        this.weight = weight;
        this.baseCity = baseCity;
        PacificOcean.getPacificOcean().addShip(this);
    }
    public Shuttle(String name, int speed, City staycity) {
        this.name = name;
        this.capacity = rand.nextInt(50,201);
        this.weight = rand.nextInt(50,201);
        this.engine = new Engine(speed);
        this.baseCity = PacificOcean.getPacificOcean().getAllCities().toArray(City[]::new)[rand.nextInt(PacificOcean.getPacificOcean().getAllCities().size())];
        this.parkingCity = staycity;
        if (parkingCity == null){
            setShipX(rand.nextInt(415,3100));
            setShipY(rand.nextInt(275,2250));
        } else{
            setShipY(parkingCity.getMapY());
            setShipX((parkingCity.getMapX()));
        }
        PacificOcean.getPacificOcean().addShip(this);
    }
    public Shuttle() {
        this(lastNames[rand.nextInt(18)] + " " + firstNames[rand.nextInt(18)],PacificOcean
                        .getPacificOcean()
                        .getAllCities()
                        .toArray(City[]::new)[rand.nextInt(PacificOcean.getPacificOcean().getAllCities().size())],
                rand.nextInt(maxWeight/2,maxWeight));
        this.capacity = rand.nextInt(this.weight/2, (int) (this.weight * 1.25));
        if(!rand.nextBoolean()) {
            this.stayInCity(City.getAllCities()
                    .stream()
                    .findAny().get());
        }
        this.engine = new Engine(rand.nextInt(1,Engine.maxSpeed+1));
        if (parkingCity == null){
            setShipX(rand.nextInt(415,3100));
            setShipY(rand.nextInt(275,2250));
        } else{
            setShipY(parkingCity.getMapY());
            setShipX((parkingCity.getMapX()));
        }
    }
    //функції життєдіяльності об'єкта


    public void moveTo(City city){
            leaveCity();
            final double k = 0.1;
             if(this.getShipX() < city.getMapX()-25){
                 this.shipX += engine.getSpeed() * k;
             } else if(this.getShipX() > city.getMapX()+25){
                 this.shipX -= engine.getSpeed() * k;
             }
             if(this.getShipY() < city.getMapY()-25){
                 this.shipY += engine.getSpeed() * k;
             } else if(this.getShipY() > city.getMapY()+25){
                 this.shipY -= engine.getSpeed() * k;
             }
             if(shipX < city.getMapX() + 50 && shipX > city.getMapX() - 50
             && shipY < city.getMapY() + 50 && shipY > city.getMapY() - 50){
                 stayInCity(city);
                 targetCity = null;
             }
    }
    public void moveTo(Shuttle shuttle){
        leaveCity();
        final double k = 0.3;
            if(this.getShipX() < shuttle.getShipX()+10){
                this.shipX += engine.getSpeed() * k;
            } else if(this.getShipX() > shuttle.getShipX()-10){
                this.shipX -= engine.getSpeed() * k;
            }
            if(this.getShipY() < shuttle.getShipY()+10){
                this.shipY += engine.getSpeed() * k;
            } else if(this.getShipY() > shuttle.getShipY()-10){
                this.shipY -= engine.getSpeed() * k;
            }
    }
    public static City findMaxCargoCity() {
        City result;
            if (rand.nextBoolean()){
               result = City.getAllCities()
                        .stream()
                        .filter(city -> city instanceof MaterialBaseCity)
                        .max((c1,c2) -> (int) (((MaterialBaseCity) c1).getMaterialsWeight() - ((MaterialBaseCity)c2).getMaterialsWeight())).get();
            } else {
               result = City.getAllCities()
                        .stream()
                        .filter(city -> city instanceof FactoryCity)
                        .max((c1,c2) -> (int) (((FactoryCity) c1).getProductionWeight() - ((FactoryCity)c2).getProductionWeight())).get();
            }
             return result;
    }
    public static City findCityforUngain(CargoType cargoType) {
        City result;
        if(cargoType == CargoType.MATERIALS){
            result = City.getAllCities()
                    .stream()
                    .filter(city -> city instanceof FactoryCity)
                    .min((c1,c2) -> (int) (((FactoryCity) c1).getMaterialsWeight() - ((FactoryCity)c2).getMaterialsWeight())).get();
        } else {
            result = City.getAllCities()
                    .stream()
                    .filter(city -> city instanceof ConsumerCity)
                    .min((c1,c2) -> (int) (((ConsumerCity) c1).getProductionWeight() - ((ConsumerCity)c2).getProductionWeight())).get();
        }
         return result;
    }
    public void stayInCity(City city){
        city.addShip(this);
        parkingCity = city;

    }
    public void leaveCity(){
        try{
            this.parkingCity.deleteShip(this);
        }catch (NullPointerException ignored){}
        parkingCity = null;
    }
    public void gain(){
        if(parkingCity == null) return;
        onGain = true;
        double cargo = 1;
        if(parkingCity instanceof MaterialBaseCity){
             cargoType = CargoType.MATERIALS;
             if(((MaterialBaseCity) parkingCity).getMaterialsWeight()  < 5) return;
            ((MaterialBaseCity) parkingCity).setMaterialsWeight(((MaterialBaseCity) parkingCity).getMaterialsWeight() - cargo);
        } else if (parkingCity instanceof FactoryCity){
            if(((FactoryCity) parkingCity).getProductionWeight() < 5) return;
            cargoType = CargoType.PRODUCTION;
            ((FactoryCity) parkingCity).setProductionWeight(((FactoryCity) parkingCity).getProductionWeight() - cargo);
        }
        this.cargoWeight += cargo;
        if(cargoWeight == capacity)onGain = false;
    }
    public void ungain(){
        if(parkingCity == null) return;
        onUngain = true;
        double cargo = 1;
        if (parkingCity instanceof FactoryCity){
            ((FactoryCity) parkingCity).setProductionWeight(((FactoryCity) parkingCity).getProductionWeight() + cargo);
        } else {
            ((ConsumerCity)parkingCity).setProductionWeight(((ConsumerCity) parkingCity).getProductionWeight() + cargo);
        }
        this.cargoWeight -= cargo;
        if(cargoWeight == 0) {
            cargoType = CargoType.NONE;
            onUngain = false;
        }
    }
    public AnchorPane drawShip(){
        Label name = new Label(this.getName());
        Rectangle img = new Rectangle(80.625,67.775);
        Rectangle cargo = new Rectangle(30,15);
        Rectangle flag = new Rectangle(20,25);
        flag.setFill(new ImagePattern(flagImg));
        flag.setLayoutY(20);
        flag.toBack();
        flag.setVisible(false);
        cargo.setFill(Paint.valueOf("white"));
        cargo.setLayoutX(cargo.getLayoutX() + 60.625);
        Rectangle expLine = new Rectangle(80,5);
        expLine.setFill(Paint.valueOf("black"));
        expLine.setOpacity(0.5);
        expLine.setLayoutY(expLine.getLayoutY()-10);
        Rectangle exp = new Rectangle(5,5);
        exp.setLayoutX(expLine.getLayoutX());
        exp.setLayoutY(expLine.getLayoutY());
        exp.setFill(Paint.valueOf("green"));
        img.setFill(new ImagePattern(shipImg));
        AnchorPane anchorPane = new AnchorPane(img,cargo,name,flag);
        anchorPane.setLayoutY(this.getShipY());
        anchorPane.setLayoutX(this.getShipX());
       /* anchorPane.setOnMouseClicked(mouseEvent -> {
            if(this.isActive()){
                this.setActive(false);
                flag.setVisible(false);
                this.automaticLife();
            } else {
                System.out.println(this);
                this.setActive(true);
                flag.setVisible(true);
                this.getAutoLife().interrupt();
            }
        });*/
        return anchorPane;
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
    protected static final Random rand = new Random(1868048);
    private static final String[] firstNames =
            {"Maximus","George III",
                    "Anna Maria","Dick",
                    "Nemo","Dream",
                    "Holland", "Shpitzbergen",
                    "Bamako", "Jakarta",
                    "Jhon", "Denis",
                    "Kitty","Puppy",
                    "Andrew","Xiaomi",
                    "Apple","Mile"};
    private static final String[] lastNames =
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
        return this.getCapacity()-s.getCapacity();
    }

    static class ComparingByCargoWeight implements Comparator<Shuttle> {
        @Override
        public int compare(Shuttle o1, Shuttle o2) {
            return (int) Math.round(o1.getCargoWeight() - o2.getCargoWeight());
        }
    }
}
