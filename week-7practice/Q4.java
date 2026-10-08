import java.util.Scanner;

abstract class KitchenTool {
    private int speedLevel = 1;

    public abstract String prepare();

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        if (speedLevel >= 1 && speedLevel <= 5) {
            this.speedLevel = speedLevel;
        }
    }
}

interface Washable {
    String clean();
}

class Blender extends KitchenTool implements Washable {

    @Override
    public String prepare() {
        return "Blending at speed " + getSpeedLevel();
    }

    @Override
    public String clean() {
        return "Blender rinsed and dried";
    }
}

public class Q4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Blender b = new Blender();

        System.out.print("Enter speed level (1-5): ");
        int speed = sc.nextInt();

        b.setSpeedLevel(speed);

        System.out.println("Speed: " + b.getSpeedLevel());
        System.out.println(b.prepare());
        System.out.println(b.clean());

        sc.close();
    }
}