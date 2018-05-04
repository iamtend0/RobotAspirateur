package sample;

import java.io.FileInputStream;

public class Piece {
    private int longueur;
    private int largeur;
    String[][] matrice = new String[longueur][largeur];

    /**
     * Construteur de pièce.
     */
    public Piece() {

        FileInputStream in = new FileInputStream("mapiece.txt");
    }
}
