import java.util.Scanner;

abstract class DeliveryNote {
    protected String trackingId;

    public DeliveryNote(String trackingId) {
        this.trackingId = trackingId;
    }

    public abstract String confirmDelivery();

    public String confirmDelivery(String signature) {
        return confirmDelivery() + ", signed by " + signature;
    }
}

class ParcelNote extends DeliveryNote {

    public ParcelNote(String trackingId) {
        super(trackingId);
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}

class LetterNote extends DeliveryNote {

    public LetterNote(String trackingId) {
        super(trackingId);
    }

    @Override
    public String confirmDelivery() {
        return "Letter " + trackingId + " delivered";
    }
}

public class Q5 {

    static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter parcel tracking ID: ");
        String parcelId = sc.nextLine();

        System.out.print("Enter letter tracking ID: ");
        String letterId = sc.nextLine();

        ParcelNote parcel = new ParcelNote(parcelId);
        LetterNote letter = new LetterNote(letterId);

        System.out.println(parcel.confirmDelivery());

        System.out.print("Enter signature: ");
        String signature = sc.nextLine();

        System.out.println(parcel.confirmDelivery(signature));

        DeliveryNote[] notes = {parcel, letter};
        logAll(notes);

        sc.close();
    }
}