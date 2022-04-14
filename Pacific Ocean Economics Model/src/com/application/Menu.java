package com.application;

import com.actions.CityShipTransfer;
import com.actions.ShipCityTransfer;
import com.actions.ShipTransfer;
import com.locations.City;
import com.locations.PacificOcean;

import java.util.*;
import java.util.regex.Pattern;

public class Menu {
    private final Scanner scan = new Scanner(System.in);

    public void start() {
        boolean isMenuOn = true;
        System.out.println("""
                1.Додати мікрооб’єкт.
                2.Вивести на екран зміст універсального об’єкта та всіх макрооб’єктів.
                3.Взаємодія мікрооб’єктів.
                4.Взаємодія двох макрооб’єктів.
                5.Підрахунок мікрооб’єктів.
                6.Видалити мікрооб’єкт.
                >>>""");
        while(isMenuOn) {
            int userInt = scan.nextInt();
            scan.nextLine();
            switch (userInt) {
                case 1:
                    System.out.println("1.Вказати параметри\n2.Створити рандомний об'єкт");
                    userInt = scan.nextInt();
                    scan.nextLine();
                    switch (userInt){
                        case 1:
                            Shuttle temp = null;
                            System.out.println("""
                            Вкажіть рівень мікрооб'єкта.
                            1.Човен.
                            2.Балкер
                            3.Контейнеровоз.
                            >>>""");
                            userInt = scan.nextInt();
                            scan.nextLine();
                            while(temp == null){
                                System.out.println("Вкажіть назву корабля.");
                                String name = scan.nextLine();
                                System.out.println("Вкажіть місткість, вагу, назву базового міста");
                                String arguments = scan.nextLine();
                                Pattern separator = Pattern.compile("\s");
                                String[] parameters = separator.split(arguments);
                                switch (userInt){
                                    case 1:
                                        try{
                                            temp = new Shuttle(name,
                                                    Integer.parseInt(parameters[0]),
                                                    Integer.parseInt(parameters[1]),
                                                    City.getAllCities().stream()
                                                            .filter(city -> city.getName().equals(parameters[2]))
                                                            .findFirst().get());
                                        }catch (NumberFormatException|NoSuchElementException|ArrayIndexOutOfBoundsException ignored) {
                                            System.out.println("Неправильний ввід аргументів або введене неіснуюче місто.");
                                        }
                                        break;
                                    case 2:
                                        try{
                                            temp = new Balker(name,
                                                    Integer.parseInt(parameters[0]),
                                                    Integer.parseInt(parameters[1]),
                                                    City.getAllCities().stream()
                                                            .filter(city -> city.getName().equals(parameters[2]))
                                                            .findFirst().get());
                                        }catch (NumberFormatException|NoSuchElementException ignored) {
                                            System.out.println("Неправильний ввід аргументів або введене неіснуюче місто.");
                                        }
                                        break;
                                    case 3:
                                        try{
                                            temp = new ContainerShip(name,
                                                    Integer.parseInt(parameters[0]),
                                                    Integer.parseInt(parameters[1]),
                                                    City.getAllCities().stream()
                                                            .filter(city -> city.getName().equals(parameters[2]))
                                                            .findFirst().get());
                                        }catch (NumberFormatException|NoSuchElementException ignored) {
                                            System.out.println("Неправильний ввід аргументів або введене неіснуюче місто.");
                                        }
                                        break;
                                    default:
                                        System.out.println("Такого рівня корабля немає.");
                                }
                            }
                            boolean cityPresent = true;
                            do {
                                System.out.println("Помістити корабель в місто? Напишіть назву міста чи введіть \"Ні\"");
                                String userAnswer = scan.nextLine();
                                if(!userAnswer.equals("Ні")){
                                    cityPresent = false;
                                    try{
                                        temp.stayInCity(City.getAllCities().stream()
                                                .filter(city -> city.getName().equals(userAnswer))
                                                .findFirst().get());
                                        cityPresent = true;
                                    } catch (NoSuchElementException ignored) {
                                        System.out.println("Такого міста немає.");
                                    }
                                }
                            } while(!cityPresent);
                                break;
                        case 2:
                            System.out.println("""
                            Вкажіть рівень мікрооб'єкта.
                            1.Човен.
                            2.Балкер
                            3.Контейнеровоз.
                            >>>""");
                            userInt = scan.nextInt();
                            scan.nextLine();
                            switch (userInt) {
                                case 1:
                                    new Shuttle();
                                    break;
                                case 2:
                                    new Balker();
                                    break;
                                case 3:
                                    new ContainerShip();
                                    break;
                                default:
                                    System.out.println("Такої функції немає.");
                            }
                            }
                            break;
            case 2:
                  PacificOcean.getPacificOcean().getAllCities()
                          .stream()
                          .sorted(Comparator.comparing(City::getName))
                          .forEach(city -> {
                              System.out.println(city.getName() + " " + city.getMaterials());
                              Iterator<? extends Shuttle> it = city.getShipsOnParking().iterator();
                              while(it.hasNext()) {
                                  System.out.println("\t" + it.next());
                              }
                          });
                  PacificOcean.getPacificOcean().getShipList()
                          .stream()
                          .filter(shuttle -> shuttle.getParkingCity() == null)
                          .forEach(System.out::println);
                    break;
                case 3:

                    microActions();
                    break;
                case 4:
                    macroAction();
                    break;
                case 5:
                    countShips();
                    break;
                case 6:
                    deleteShip();
                    break;
                case 7:
                    isMenuOn = false;
                default:
                    System.out.println("Такої команди немає.");

            }
        }
    }
    private void countShips() {
        System.out.println("""
                Оберіть критерій підрахунку.
                1. Кораблі у містах.
                2. Кораблі в океані.
                3. Контейнеровози.
                """);
        Scanner user = new Scanner(System.in);
        int userInt = user.nextInt();
        user.nextLine();
        switch (userInt) {
            case 1:
                System.out.println("Кількість кораблів у містах: " +
                        PacificOcean.getPacificOcean().getShipList()
                                .stream()
                                .filter(shuttle -> shuttle.getParkingCity() != null)
                                .count());
                break;
            case 2:
                System.out.println("Кількість кораблів поза містами: " +
                        PacificOcean.getPacificOcean().getShipList()
                                .stream()
                                .filter(shuttle -> shuttle.getParkingCity() == null)
                                .count());
                break;
            case 3:
                System.out.println("Кількість контейнеровозів: " +
                        PacificOcean.getPacificOcean().getShipList()
                                .stream()
                                .filter(shuttle -> shuttle instanceof ContainerShip)
                                .count());
                break;
            default:
                System.out.println("Такої опції немає.");
        }

    }
    private void deleteShip() {
        Scanner user = new Scanner(System.in);
        System.out.println("Вкажіть назву корабля, який треба видалити.");

        boolean isDeleted = false;
        do {
            try{
                String name = user.nextLine();
                Shuttle ship = PacificOcean.getPacificOcean().getShipList()
                        .stream()
                        .filter(shuttle -> shuttle.getName().equals(name))
                        .findFirst().get();
                PacificOcean.getPacificOcean()
                        .getShipList()
                        .remove(ship);
                ship.leaveCity(ship.getParkingCity());
                System.out.println("Видалено.");
                isDeleted = true;
            }catch (NullPointerException e ) {
                System.out.println("Видалено.");
                isDeleted = true;
            }
            catch(NoSuchElementException ignored) {
                System.out.println("Немає такого корабля.");
            }
        } while(!isDeleted);

    }
    private void microActions(){
        if(PacificOcean.getPacificOcean().getShipList().size() < 2){
            System.out.println("Менше 2 кораблів.");
            return;
        }
        System.out.println("""
                            Вкажіть рівень мікрооб'єктів.
                            1.Балкер
                            2.Контейнеровоз.
                            >>>""");
        int userInt = scan.nextInt();
        scan.nextLine();
        switch (userInt){
            case 1:
                Balker ship1 = (Balker) PacificOcean.getPacificOcean()
                        .getShipList()
                        .stream()
                        .filter(shuttle -> shuttle instanceof Balker&& !(shuttle instanceof ContainerShip))
                        .findAny()
                        .get();
                Balker ship2 = (Balker) PacificOcean.getPacificOcean()
                        .getShipList()
                        .stream()
                        .filter(shuttle -> shuttle != ship1 && shuttle instanceof Balker && !(shuttle instanceof ContainerShip))
                        .findAny()
                        .get();

                if(ship1.getParkingCity() == ship2.getParkingCity() && ship1.getParkingCity() != null) {
                    new CityShipTransfer(ship1.getParkingCity(),ship1,25).start();
                    new ShipTransfer(ship1,ship2,12.5).start();
                } else if(ship1.getParkingCity() == null && ship2.getParkingCity() == null){
                    ship1.findOrder();
                    new CityShipTransfer(ship1.getParkingCity(),ship1,25).start();
                    ship1.leaveCity(ship1.getParkingCity());
                    new ShipTransfer(ship1,ship2,12.5).start();
                } else if (ship1.getParkingCity() == null && ship2.getParkingCity() != null) {
                    ship1.moveTo(ship2.getParkingCity());
                    new CityShipTransfer(ship1.getParkingCity(), ship1,25).start();
                    new ShipTransfer(ship1,ship2,12.5).start();
                } else{
                    new CityShipTransfer(ship1.getParkingCity(),ship1,25).start();
                    ship1.leaveCity(ship1.getParkingCity());
                    new ShipTransfer(ship1,ship2,12.8).start();
                }
                break;
            case 2:
                ContainerShip ship3 = (ContainerShip) PacificOcean.getPacificOcean()
                        .getShipList()
                        .stream()
                        .filter(shuttle -> shuttle instanceof ContainerShip)
                        .findAny()
                        .get();
                ContainerShip ship4 = (ContainerShip) PacificOcean.getPacificOcean()
                        .getShipList()
                        .stream()
                        .filter(shuttle -> shuttle != ship3 && shuttle instanceof ContainerShip)
                        .findAny()
                        .get();

                if(ship3.getParkingCity() == ship4.getParkingCity() && ship3.getParkingCity() != null) {
                    new CityShipTransfer(ship3.getParkingCity(),ship3,25).start();
                    new ShipTransfer(ship3,ship4,12.5).start();
                } else if(ship3.getParkingCity() == null && ship4.getParkingCity() == null){
                    ship3.findOrder();
                    new CityShipTransfer(ship3.getParkingCity(),ship3,25).start();
                    ship3.leaveCity(ship3.getParkingCity());
                    new ShipTransfer(ship3,ship4,12.5).start();
                } else if (ship3.getParkingCity() == null && ship4.getParkingCity() != null) {
                    ship3.moveTo(ship4.getParkingCity());
                    new CityShipTransfer(ship3.getParkingCity(), ship3,25).start();
                    new ShipTransfer(ship3,ship4,12.5).start();
                } else{
                    new CityShipTransfer(ship3.getParkingCity(),ship3,25).start();
                    ship3.leaveCity(ship3.getParkingCity());
                    new ShipTransfer(ship3,ship4,12.8).start();
                }
                break;
        }
    }
    public void macroAction(){
        City city1 = City.getAllCities().get(new Random(14621313).nextInt(0,City.getAllCities().size()));
        City city2 = City.getAllCities()
                .stream()
                .filter(city -> city != city1)
                .findAny().get();

        while(city1.getMaterials() != 0) {
            PacificOcean.getPacificOcean().getShipList()
                    .forEach( shuttle -> {
                            shuttle.moveTo(city1);
                            new CityShipTransfer(city1,shuttle,
                                    (shuttle.getCapacity() < city1.getMaterials())? shuttle.getCapacity(): city1.getMaterials()).start();
                            shuttle.moveTo(city2);
                            new ShipCityTransfer(shuttle,city2,shuttle.getCargoWeight()).start();
                    });

        }
    }
}
