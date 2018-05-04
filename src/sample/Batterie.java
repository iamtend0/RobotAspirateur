/*
 * Batterie.java
 */
package sample;

import java.util.concurrent.TimeUnit;

public class Batterie extends Thread{

    private int capacité;
    private int energie;

    /**
     * Constructor
     * @param capacité
     * @param energie
     */
    public Batterie(int capacité,int energie) {
        this.capacité = capacité;
        this.energie=capacité;
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

    public void recharge(){
        setEnergie(getCapacité());
    }

    public void run(){
        try {
            TimeUnit.SECONDS.sleep((long) 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recharge();
    }
}
