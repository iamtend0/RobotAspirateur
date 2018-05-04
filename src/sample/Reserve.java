/*
 * Reserve.java
 */
package sample;

import java.util.concurrent.TimeUnit;

/**
 * Classe Reserve
 */
public class Reserve extends Thread{

    /** Attributs */
    private int taille;

    public Reserve(int taille) {
        this.taille = taille;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public void vidage(){
        this.taille=0;
    }

    public void run(){
        try {
            TimeUnit.SECONDS.sleep((long) 2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vidage();
    }
}
