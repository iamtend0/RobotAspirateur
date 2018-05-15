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
    private float capacité;
    private float energie;

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
    public float getCapacité() {
        return capacité;
    }

    /**
     * Mutateur de la capacite
     *
     * @param capacité
     */
    public void setCapacité(float capacité) {
        this.capacité = capacité;
    }

    /**
     * Accesseur de l'energie
     *
     * @return energie
     */
    public float getEnergie() {
        return energie;
    }

    /**
     * Mutateur de l'energie
     *
     * @param energie
     */
    public void setEnergie(float energie) {
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
