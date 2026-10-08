import java.util.*;

abstract class Vehicle {
    protected String name;
    protected boolean available;

    Vehicle(String name) {
        this.name = name;
        this.available = true;
    }

    abstract double calculateCharge(int days);

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class Sedan extends Vehicle {
    Sedan(String name) {
        super(name);
    }

    @Override
    double calculateCharge(int days) {
        return days * 50;
    }
}

class SUV extends Vehicle {
    SUV(String name) {
        super(name);
    }

    @Override
    double calculateCharge(int days) {
        return days * 80;
    }
}

class Truck extends Vehicle {
    Truck(String name) {
        super(name);
    }

    @Override
    double calculateCharge(int days) {
        return days * 100;
    }
}

class Customer {
    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Rental {
    Vehicle vehicle;
    Customer customer;
    int days;
    double charge;

    Rental(Vehicle vehicle, Customer customer, int days) {
        this.vehicle = vehicle;
        this.customer = customer;
        this.days = days;
        this.charge = vehicle.calculateCharge(days);
    }
}

public class VehicleRentalSystem {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Enter customer 1 name: ");
        Customer c1 = new Customer(sc.nextLine());

        System.out.print("Enter customer 2 name: ");
        Customer c2 = new Customer(sc.nextLine());

        System.out.print("Enter customer 3 name: ");
        Customer c3 = new Customer(sc.nextLine());

        System.out.print("Enter Sedan name: ");
        Vehicle sedan = new Sedan(sc.nextLine());

        System.out.print("Enter SUV name: ");
        Vehicle suv = new SUV(sc.nextLine());

        System.out.print("Enter days for " + c1.name + " to rent Sedan: ");
        int days1 = sc.nextInt();

        rentVehicle(sedan, c1, days1);

        System.out.print("Enter days for " + c2.name + " to rent Sedan: ");
        int days2 = sc.nextInt();

        rentVehicle(sedan, c2, days2);

        returnVehicle(sedan, c1);

        System.out.print("Enter days for " + c3.name + " to rent SUV: ");
        int days3 = sc.nextInt();

        rentVehicle(suv, c3, days3);

        sc.close();
    }

    static void rentVehicle(Vehicle vehicle, Customer customer, int days) {

        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getName() + " is currently unavailable.");
            return;
        }

        vehicle.setAvailable(false);

        Rental rental = new Rental(vehicle, customer, days);

        System.out.println(
            vehicle.getName() + " rented successfully by "
            + customer.name
        );

        System.out.println(
            "Rental charge: $" + rental.charge
        );
    }

    static void returnVehicle(Vehicle vehicle, Customer customer) {

        vehicle.setAvailable(true);

        System.out.println(
            vehicle.getName() + " returned by " + customer.name
        );
    }
}