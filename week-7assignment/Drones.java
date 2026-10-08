import java.util.Scanner;

abstract class Drone {

    public abstract String fly();
}

interface Trackable {
    String getLocation();
}

class DeliveryDrone extends Drone implements Trackable {
    private String id;

    public DeliveryDrone(String id) {
        this.id = id;
    }

    @Override
    public String fly() {
        return id + " flying for delivery";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {
    private String id;

    public ScoutDrone(String id) {
        this.id = id;
    }

    @Override
    public String fly() {
        return id + " flying for scouting";
    }
}

class GroundRobot implements Trackable {
    private String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

public class Drones {

    static String getLocationIfTrackable(Object o) {
        if (o instanceof Trackable) {
            Trackable t = (Trackable) o;
            return t.getLocation();
        } else {
            return "Tracking not available";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Delivery Drone ID: ");
        String deliveryId = sc.nextLine();

        System.out.print("Enter Scout Drone ID: ");
        String scoutId = sc.nextLine();

        System.out.print("Enter Ground Robot ID: ");
        String robotId = sc.nextLine();

        DeliveryDrone d = new DeliveryDrone(deliveryId);
        ScoutDrone s = new ScoutDrone(scoutId);
        GroundRobot g = new GroundRobot(robotId);

        System.out.println(getLocationIfTrackable(d));
        System.out.println(getLocationIfTrackable(s));
        System.out.println(getLocationIfTrackable(g));

        sc.close();
    }
}