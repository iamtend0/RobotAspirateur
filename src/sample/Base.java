/*
 * Base.java
 */
package sample;

/**
 * Classe Base
 */
public class Base {

    /** Attributs */
    private Capteur presence;
    private int positionx;
    private int positiony;

    /**
     * Constructor
     * @param presence
     * @param positionx
     * @param positiony
     */
    public Base(Capteur presence, int positionx, int positiony) {
        this.presence = presence;
        this.positionx = positionx;
        this.positiony = positiony;
    }

    /**
     * Accesseur
     * @return
     */
    public Capteur getPresence() {
        return presence;
    }

    /**
     * Accesseur position x
     * @return x
     */
    public int getPositionx() {
        return positionx;
    }

    /**
     * Accesseur position y
     * @return y
     */
    public int getPositiony() {
        return positiony;
    }
}
