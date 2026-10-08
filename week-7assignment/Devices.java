import java.util.Scanner;

abstract class ClassroomDevice {

    public abstract String operate();
}

interface Chargeable {
    String charge();
    String charge(int minutes);
}

class Tablet extends ClassroomDevice implements Chargeable {
    private String assetTag;

    public Tablet(String assetTag) {
        this.assetTag = assetTag;
    }

    @Override
    public String operate() {
        return "Tablet " + assetTag + " displaying lesson";
    }

    @Override
    public String charge() {
        return assetTag + " charging";
    }

    @Override
    public String charge(int minutes) {
        return assetTag + " charging for " + minutes + " minutes";
    }
}

public class Devices {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter tablet asset tag: ");
        String assetTag = sc.nextLine();

        System.out.print("Enter charging minutes: ");
        int minutes = sc.nextInt();

        Tablet t = new Tablet(assetTag);

        System.out.println(t.operate());
        System.out.println(t.charge());
        System.out.println(t.charge(minutes));

        sc.close();
    }
}