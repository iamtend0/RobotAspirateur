/*
 * Robot.java
 */
package sample;

import java.util.ArrayList;

public class Robot {

    /* Coordonnées du robot */
    private int positionx;
    private int positiony;

    private Batterie batterie;
    private Reserve reserve;
    private Direction deplacements;
    private ArrayList<Capteur> capteur = new ArrayList<Capteur>(5);
    private Base base;
    private Piece piece;
    private boolean aspiration;

    public Robot(Batterie batterie, Reserve reserve, Direction deplacements, ArrayList<Capteur> capteur, Base base, Piece piece, boolean aspiration) {
        this.positionx = base.getPositionx();
        this.positiony = base.getPositiony();
        this.batterie = batterie;
        this.reserve = reserve;
        this.deplacements = deplacements;
        this.capteur = capteur;
        this.base = base;
        this.piece = piece;
        this.aspiration = aspiration;
    }

}
