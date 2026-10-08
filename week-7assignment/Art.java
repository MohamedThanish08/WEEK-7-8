import java.util.Scanner;

abstract class ArtPiece {
    private static int counter = 1;
    private final String pieceId;

    public ArtPiece() {
        pieceId = "P" + counter++;
    }

    public abstract String describe();

    public String getPieceId() {
        return pieceId;
    }
}

class Painting extends ArtPiece {
    private String title;

    public Painting(String title) {
        this.title = title;
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}

class Sculpture extends ArtPiece {
    private String title;

    public Sculpture(String title) {
        this.title = title;
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}

public class Art {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter painting title: ");
        String paintingTitle = sc.nextLine();

        System.out.print("Enter sculpture title: ");
        String sculptureTitle = sc.nextLine();

        Painting p = new Painting(paintingTitle);
        Sculpture s = new Sculpture(sculptureTitle);

        System.out.println(p.describe());
        System.out.println("Piece ID: " + p.getPieceId());

        System.out.println(s.describe());
        System.out.println("Piece ID: " + s.getPieceId());

        sc.close();
    }
}