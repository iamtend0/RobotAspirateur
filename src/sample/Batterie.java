/*
 * Batterie.java
 */
package sample;

import javafx.scene.control.Label;

import java.util.concurrent.TimeUnit;

public class Batterie extends Thread {

    private int capacité;
    private int energie;

    /**
     * Constructor
     *
     * @param capacité
     */
    public Batterie(int capacité) {
        this.capacité = capacité;
        this.energie = capacité;
    }

    public int getCapacité() {
        return capacité;
    }

    public void setCapacité(int capacité) {
        this.capacité = capacité;
    }

    public int getEnergie() {
        return energie;
    }

    public void setEnergie(int energie) {
        this.energie = energie;
    }

    public void recharge() {
        this.setEnergie(getCapacité());
    }

    public void run() {
        try {
            TimeUnit.SECONDS.sleep((long) 5);
            recharge();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
