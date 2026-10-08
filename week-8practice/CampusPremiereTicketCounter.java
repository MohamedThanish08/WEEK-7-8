import java.util.*;

interface Seat {
    String getSeatNumber();
    double getPrice();
}

class RegularSeat implements Seat {
    private String seatNumber;

    public RegularSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 150.0;
    }
}

class PremiumSeat implements Seat {
    private String seatNumber;

    public PremiumSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 250.0;
    }
}

class ReclinerSeat implements Seat {
    private String seatNumber;

    public ReclinerSeat(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return 400.0;
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Show {
    private String showTime;
    private boolean started;

    private Map<String, Seat> seats;
    private Set<String> bookedSeats;

    public Show(String showTime) {
        this.showTime = showTime;
        this.started = false;

        seats = new HashMap<>();
        bookedSeats = new HashSet<>();
    }

    public void addSeat(Seat seat) {
        seats.put(seat.getSeatNumber(), seat);
    }

    public boolean isAvailable(String seatNumber) {
        return seats.containsKey(seatNumber)
                && !bookedSeats.contains(seatNumber);
    }

    public Seat getSeat(String seatNumber) {
        return seats.get(seatNumber);
    }

    public boolean bookSeat(String seatNumber) {

        if (!seats.containsKey(seatNumber)) {
            System.out.println("Seat " + seatNumber + " does not exist.");
            return false;
        }

        if (bookedSeats.contains(seatNumber)) {
            System.out.println(
                    "Seat " + seatNumber +
                    " is already booked for this show."
            );
            return false;
        }

        bookedSeats.add(seatNumber);
        return true;
    }

    public void releaseSeat(String seatNumber) {
        bookedSeats.remove(seatNumber);
    }

    public void startShow() {
        started = true;
    }

    public boolean hasStarted() {
        return started;
    }

    public String getShowTime() {
        return showTime;
    }
}

class Booking {
    private Customer customer;
    private Show show;
    private List<Seat> seats;
    private boolean cancelled;

    public Booking(Customer customer, Show show) {
        this.customer = customer;
        this.show = show;
        this.seats = new ArrayList<>();
        this.cancelled = false;
    }

    public boolean addSeat(String seatNumber) {

        if (seats.size() >= 6) {
            System.out.println(
                    "Maximum 6 seats allowed per booking."
            );
            return false;
        }

        if (!show.isAvailable(seatNumber)) {
            return false;
        }

        if (show.bookSeat(seatNumber)) {
            seats.add(show.getSeat(seatNumber));
            return true;
        }

        return false;
    }

    public double calculateTotal() {

        double total = 0;

        for (Seat seat : seats) {
            total += seat.getPrice();
        }

        return total;
    }

    public void confirmBooking() {

        System.out.print(
                "Booking confirmed for " +
                customer.getName() + ": "
        );

        for (int i = 0; i < seats.size(); i++) {

            System.out.print(
                    seats.get(i).getSeatNumber()
            );

            if (i < seats.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.printf(
                ". Total: ₹%.2f%n",
                calculateTotal()
        );
    }

    public void cancelBooking() {

        if (cancelled) {
            System.out.println("Booking is already cancelled.");
            return;
        }

        if (show.hasStarted()) {
            System.out.println(
                    "Cannot cancel booking after show has started."
            );
            return;
        }

        for (Seat seat : seats) {
            show.releaseSeat(seat.getSeatNumber());
        }

        cancelled = true;

        System.out.println(
                customer.getName() +
                "'s booking cancelled."
        );

        System.out.println("Seats released.");
    }
}

public class CampusPremiereTicketCounter {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Show show = new Show("7 PM");

        // Sample seats
        show.addSeat(new RegularSeat("A1"));
        show.addSeat(new RegularSeat("A2"));
        show.addSeat(new PremiumSeat("F5"));
        show.addSeat(new ReclinerSeat("R1"));

        System.out.print("Enter customer name: ");
        Customer customer = new Customer(sc.nextLine());

        Booking booking = new Booking(customer, show);

        System.out.println("\nAvailable sample seats:");
        System.out.println("A1 - Regular - ₹150");
        System.out.println("A2 - Regular - ₹150");
        System.out.println("F5 - Premium - ₹250");
        System.out.println("R1 - Recliner - ₹400");

        System.out.print("\nHow many seats do you want to book? ");
        int count = sc.nextInt();

        if (count > 6) {
            System.out.println(
                    "Maximum 6 seats allowed per booking."
            );
            sc.close();
            return;
        }

        for (int i = 0; i < count; i++) {

            System.out.print("Enter seat number: ");
            String seatNumber = sc.next();

            booking.addSeat(seatNumber);
        }

        booking.confirmBooking();

        System.out.print(
                "\nDo you want to cancel booking? (yes/no): "
        );

        String answer = sc.next();

        if (answer.equalsIgnoreCase("yes")) {
            booking.cancelBooking();
        }

        sc.close();
    }
}