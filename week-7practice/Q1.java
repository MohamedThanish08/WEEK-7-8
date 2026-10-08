import java.util.Scanner;

abstract class Toy {
    private static int counter = 1000;
    private final String toyId;
    protected String name;

    public Toy(String name) {
        this.name = name;
        counter++;
        toyId = "TOY-" + counter;
    }

    public abstract String makeSound();

    public String getToyId() {
        return toyId;
    }
}

class ToyCar extends Toy {
    public ToyCar(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Vroom vroom!";
    }
}

class ToyRobot extends Toy {
    public ToyRobot(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Beep boop!";
    }
}

public class Q1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter car name: ");
        String carName = sc.nextLine();

        System.out.print("Enter robot name: ");
        String robotName = sc.nextLine();

        ToyCar car = new ToyCar(carName);
        ToyRobot robot = new ToyRobot(robotName);

        System.out.println(car.makeSound());
        System.out.println(robot.makeSound());

        System.out.println(car.getToyId());
        System.out.println(robot.getToyId());

        sc.close();
    }
}