import java.util.Scanner;

interface Printable {
    String printLabel();
}

class PackageBox implements Printable {
    private String trackingId;

    public PackageBox(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String printLabel() {
        return "Package label: " + trackingId;
    }
}

class Invoice implements Printable {
    private String invoiceNumber;

    public Invoice(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String printLabel() {
        return "Invoice label: " + invoiceNumber;
    }
}

public class Q2 {

    static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter tracking ID: ");
        String trackingId = sc.nextLine();

        System.out.print("Enter invoice number: ");
        String invoiceNumber = sc.nextLine();

        PackageBox p = new PackageBox(trackingId);
        Invoice i = new Invoice(invoiceNumber);

        Printable[] items = {p, i};

        printAll(items);

        sc.close();
    }
}