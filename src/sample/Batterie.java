/*
 * Batterie.java
 */
package sample;

import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

/**
 * Classe Batterie
 */
public class Batterie extends Thread {
    /**
     * Attributs
     */
    private int capacité;
    private int energie;

    /**
     * Constructeur de la batterie
     *
     * @param capacité
     */
    public Batterie(int capacité) {
        this.capacité = capacité;
        this.energie = capacité;
    }

    /**
     * Accesseur de la capacite
     *
     * @return capacite
     */
    public int getCapacité() {
        return capacité;
    }

    /**
     * Mutateur de la capacite
     *
     * @param capacité
     */
    public void setCapacité(int capacité) {
        this.capacité = capacité;
    }

    /**
     * Accesseur de l'energie
     *
     * @return energie
     */
    public int getEnergie() {
        return energie;
    }

    /**
     * Mutateur de l'energie
     *
     * @param energie
     */
    public void setEnergie(int energie) {
        this.energie = energie;
    }

    /**
     * Recharge la batterie du robot
     */
    public void recharge() {
        this.setEnergie(getCapacité());
    }

    /**
     * Processus de recharge de la batterie avec un thread
     */
    public void run() {
        try {
            TimeUnit.SECONDS.sleep((long) 5);
            recharge();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
