import java.time.LocalDate;
import java.util.*;

abstract class Room {

    protected int roomNumber;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    abstract double calculatePrice(long days);

    public int getRoomNumber() {
        return roomNumber;
    }
}

class StandardRoom extends Room {

    StandardRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    double calculatePrice(long days) {
        return days * 100;
    }
}

class DeluxeRoom extends Room {

    DeluxeRoom(int roomNumber) {
        super(roomNumber);
    }

    @Override
    double calculatePrice(long days) {
        return days * 150;
    }
}

class Suite extends Room {

    Suite(int roomNumber) {
        super(roomNumber);
    }

    @Override
    double calculatePrice(long days) {
        return days * 250;
    }
}

class Customer {

    String name;

    Customer(String name) {
        this.name = name;
    }
}

class Reservation {

    Customer customer;
    Room room;
    LocalDate startDate;
    LocalDate endDate;
    LocalDate cancellationDeadline;
    boolean cancelled;

    Reservation(
        Customer customer,
        Room room,
        LocalDate startDate,
        LocalDate endDate
    ) {
        this.customer = customer;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;

        // Cancellation allowed until one day before start date
        this.cancellationDeadline =
            startDate.minusDays(1);

        this.cancelled = false;
    }

    double calculatePrice() {

        long days =
            startDate.until(
                endDate
            ).getDays();

        return room.calculatePrice(days);
    }

    boolean overlaps(
        LocalDate start,
        LocalDate end
    ) {

        return !end.isEqual(startDate)
            && !start.isEqual(endDate)
            && start.isBefore(endDate)
            && end.isAfter(startDate)
            && !cancelled;
    }

    void cancel(LocalDate currentDate) {

        if (cancelled) {
            System.out.println(
                "Reservation is already cancelled."
            );
            return;
        }

        if (currentDate.isAfter(cancellationDeadline)) {
            System.out.println(
                "Cancellation deadline has passed."
            );
            return;
        }

        cancelled = true;

        System.out.println(
            "Reservation for "
            + customer.name
            + ", Room "
            + room.getRoomNumber()
            + " cancelled successfully."
        );
    }
}

public class HotelBookingSystem {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Reservation> reservations =
        new ArrayList<>();

    public static void main(String[] args) {

        System.out.print("Enter customer A name: ");
        Customer customerA =
            new Customer(sc.nextLine());

        System.out.print("Enter customer B name: ");
        Customer customerB =
            new Customer(sc.nextLine());

        System.out.print("Enter customer C name: ");
        Customer customerC =
            new Customer(sc.nextLine());

        Room standard =
            new StandardRoom(101);

        Room deluxe =
            new DeluxeRoom(201);

        System.out.println("\n--- Customer A Booking ---");

        System.out.print("Enter start date (yyyy-mm-dd): ");
        LocalDate startA =
            LocalDate.parse(sc.nextLine());

        System.out.print("Enter end date (yyyy-mm-dd): ");
        LocalDate endA =
            LocalDate.parse(sc.nextLine());

        if (isAvailable(standard, startA, endA)) {

            Reservation r =
                new Reservation(
                    customerA,
                    standard,
                    startA,
                    endA
                );

            reservations.add(r);

            System.out.println(
                "Reservation confirmed for "
                + customerA.name
            );

            System.out.println(
                "Price: $" + r.calculatePrice()
            );

        } else {
            System.out.println(
                "Room is not available."
            );
        }

        System.out.println("\n--- Customer B Booking ---");

        System.out.print("Enter start date (yyyy-mm-dd): ");
        LocalDate startB =
            LocalDate.parse(sc.nextLine());

        System.out.print("Enter end date (yyyy-mm-dd): ");
        LocalDate endB =
            LocalDate.parse(sc.nextLine());

        if (isAvailable(standard, startB, endB)) {

            Reservation r =
                new Reservation(
                    customerB,
                    standard,
                    startB,
                    endB
                );

            reservations.add(r);

            System.out.println(
                "Reservation confirmed."
            );

        } else {

            System.out.println(
                "Room "
                + standard.getRoomNumber()
                + " is not available for these dates."
            );
        }

        System.out.println("\n--- Cancel Customer A Booking ---");

        System.out.print(
            "Enter cancellation date (yyyy-mm-dd): "
        );

        LocalDate cancellationDate =
            LocalDate.parse(sc.nextLine());

        for (Reservation r : reservations) {

            if (r.customer == customerA) {
                r.cancel(cancellationDate);
            }
        }

        System.out.println("\n--- Customer C Booking ---");

        System.out.print("Enter start date (yyyy-mm-dd): ");
        LocalDate startC =
            LocalDate.parse(sc.nextLine());

        System.out.print("Enter end date (yyyy-mm-dd): ");
        LocalDate endC =
            LocalDate.parse(sc.nextLine());

        if (isAvailable(deluxe, startC, endC)) {

            Reservation r =
                new Reservation(
                    customerC,
                    deluxe,
                    startC,
                    endC
                );

            reservations.add(r);

            System.out.println(
                "Reservation confirmed for "
                + customerC.name
            );

            System.out.println(
                "Price: $" + r.calculatePrice()
            );

        } else {
            System.out.println(
                "Deluxe room is not available."
            );
        }

        sc.close();
    }

    static boolean isAvailable(
        Room room,
        LocalDate start,
        LocalDate end
    ) {

        for (Reservation r : reservations) {

            if (r.room.getRoomNumber()
                    == room.getRoomNumber()
                && r.overlaps(start, end)) {

                return false;
            }
        }

        return true;
    }
}