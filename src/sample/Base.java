/*
 * Base.java
 */
package sample;

/**
 * Classe Base
 */
public class Base extends Thread {

    /**
     * Attributs
     */
    private Capteur presence;  //capteur de presence de la base
    private Batterie batterie; //batterie du robot
    private Reserve reserve;   //reserve de poussière du robot


    /**
     * Constructeur de base
     *
     * @param batterie
     * @param presence
     * @param reserve
     */
    public Base(Batterie batterie, Capteur presence, Reserve reserve) {
        this.batterie = batterie;
        this.presence = presence;
        this.reserve = reserve;
    }


    /**
     * Accesseur presence
     *
     * @return presence
     */
    public Capteur getPresence() {
        return presence;
    }

    /**
     * Accesseur batterie
     *
     * @return batterie
     */
    public Batterie getBatterie() {
        return batterie;
    }

    /**
     * Accesseur reserve
     *
     * @return reserve
     */
    public Reserve getReserve() {
        return reserve;
    }

    /**
     * Processus de recharge de la batterie et vidage du reservoir avec des threads
     */
    public void run() {
        if (getPresence().isEtat() == true) {
            //Demarre le thread de recharge de la batterie
            new Thread(this.getBatterie()).start();
            //Demarre le thread de vidage du reservoir
            new Thread(this.getReserve()).start();
        }

    }

}
