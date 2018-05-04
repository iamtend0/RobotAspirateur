package sample;

import java.io.IOException;

public class TestPiece {

    public static void main(String[] args) {
        Piece piece;

        {
            try {
                piece = new Piece("data/mapiece.txt");
                piece.afficherMatrice();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
