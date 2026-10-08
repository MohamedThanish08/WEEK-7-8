import java.util.Scanner;

interface WashType {
    String getName();
    int getDuration();
    double getCharge();
}

class QuickWash implements WashType {
    public String getName() {
        return "Quick";
    }

    public int getDuration() {
        return 30;
    }

    public double getCharge() {
        return 20.0;
    }
}

class NormalWash implements WashType {
    public String getName() {
        return "Normal";
    }

    public int getDuration() {
        return 45;
    }

    public double getCharge() {
        return 30.0;
    }
}

class HeavyWash implements WashType {
    public String getName() {
        return "Heavy";
    }

    public int getDuration() {
        return 60;
    }

    public double getCharge() {
        return 45.0;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class WashCycle {
    private Student student;
    private WashingMachine machine;
    private WashType washType;

    public WashCycle(Student student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }

    public void displayDetails() {
        System.out.println(washType.getName() + " wash started on "
                + machine.getMachineId() + " for "
                + student.getName() + " (" + washType.getDuration() + " min).");

        System.out.printf("Charge: ₹%.2f%n", washType.getCharge());
    }
}

class WashingMachine {
    private String machineId;
    private boolean busy;

    public WashingMachine(String machineId) {
        this.machineId = machineId;
        this.busy = false;
    }

    public String getMachineId() {
        return machineId;
    }

    public boolean isBusy() {
        return busy;
    }

    public WashCycle startWash(Student student, WashType washType) {
        if (busy) {
            System.out.println("Machine " + machineId + " is currently busy.");
            return null;
        }

        busy = true;

        WashCycle cycle = new WashCycle(student, this, washType);
        cycle.displayDetails();

        return cycle;
    }

    public void completeWash() {
        if (busy) {
            busy = false;
            System.out.println("Machine " + machineId + " cycle completed.");
            System.out.println("Machine " + machineId + " is now free.");
        } else {
            System.out.println("Machine " + machineId + " is already free.");
        }
    }
}

public class HostelLaundryQueue {

    public static WashType getWashType(int choice) {
        switch (choice) {
            case 1:
                return new QuickWash();
            case 2:
                return new NormalWash();
            case 3:
                return new HeavyWash();
            default:
                return null;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        System.out.print("Enter student name: ");
        Student student1 = new Student(sc.nextLine());

        System.out.println("Choose wash type:");
        System.out.println("1. Quick - 30 min - ₹20");
        System.out.println("2. Normal - 45 min - ₹30");
        System.out.println("3. Heavy - 60 min - ₹45");

        int choice = sc.nextInt();

        WashType wash = getWashType(choice);

        if (wash == null) {
            System.out.println("Invalid wash type.");
            sc.close();
            return;
        }

        m1.startWash(student1, wash);

        System.out.print("Enter second student name: ");
        sc.nextLine();
        Student student2 = new Student(sc.nextLine());

        System.out.println("Choose wash type for second student:");
        System.out.println("1. Quick");
        System.out.println("2. Normal");
        System.out.println("3. Heavy");

        choice = sc.nextInt();
        wash = getWashType(choice);

        if (wash != null) {
            m1.startWash(student2, wash);
        }

        System.out.println("\nStarting wash on M2...");
        m2.startWash(student2, new HeavyWash());

        System.out.println("\nCompleting M1 wash...");
        m1.completeWash();

        System.out.print("\nEnter third student name: ");
        sc.nextLine();
        Student student3 = new Student(sc.nextLine());

        m1.startWash(student3, new NormalWash());

        sc.close();
    }
}
