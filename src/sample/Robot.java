/*
 * Robot.java
 */
package sample;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

    public int getPositionx() {
        return positionx;
    }

    public void setPositionx(int positionx) {
        this.positionx = positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public void setPositiony(int positiony) {
        this.positiony = positiony;
    }

    public Batterie getBatterie() {
        return batterie;
    }

    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    public Direction getDeplacements() {
        return deplacements;
    }

    public void setDeplacements(Direction deplacements) {
        this.deplacements = deplacements;
    }

    public ArrayList<Capteur> getCapteur() {
        return capteur;
    }

    public void setCapteur(ArrayList<Capteur> capteur) {
        this.capteur = capteur;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isAspiration() {
        return aspiration;
    }

    public void setAspiration(boolean aspiration) {
        this.aspiration = aspiration;
    }

    public void deplacement() throws InterruptedException {
        switch (deplacements){
            case HAUT:
                TimeUnit.SECONDS.sleep((long) 0.25);
                batterie.setEnergie(batterie.getEnergie()-1);
                positiony-=1;
                break;
            case BAS:
                TimeUnit.SECONDS.sleep((long) 0.25);
                batterie.setEnergie(batterie.getEnergie()-1);
                positiony+=1;
                break;
            case GAUCHE:
                TimeUnit.SECONDS.sleep((long) 0.25);
                batterie.setEnergie(batterie.getEnergie()-1);
                positionx-=1;
                break;
            case DROITE:
                TimeUnit.SECONDS.sleep((long) 0.25);
                batterie.setEnergie(batterie.getEnergie()-1);
                positionx+=1;
                break;
        }
    }
}
