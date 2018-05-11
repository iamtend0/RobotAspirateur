/*
 * Base.java
 */
package sample;

/**
 * Classe Base
 */
public class Base extends Thread{

    /** Attributs */
    private Capteur presence;
    private Batterie batterie;
    private Reserve reserve;
    private int positionx;
    private int positiony;

    public Base(Batterie batterie,Capteur presence,Reserve reserve){
        this.batterie=batterie;
        this.presence=presence;
        this.reserve=reserve;
    }

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


    public Batterie getBatterie() {
        return batterie;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void run(){
            if(getPresence().isEtat()==true) {
                new Thread(this.getBatterie()).start();
                new Thread(this.getReserve()).start();
            }

    }

}
