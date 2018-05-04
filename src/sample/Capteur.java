/*
 * Capteur.java
 */
package sample;

/**
 * Classe Capteur
 */
public class Capteur {

    /** Attributs */
    private Piece piece;
    boolean Etat;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEtat() {
        return Etat;
    }

    public void setEtat(boolean etat) {
        Etat = etat;
    }
}
