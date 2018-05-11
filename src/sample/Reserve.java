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
    private int quantitePoussiere;

    public Reserve(int taille) {
        this.taille = taille;
        this.quantitePoussiere=0;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getQuantitePoussiere() {
        return quantitePoussiere;
    }

    public void setQuantitePoussiere(int quantitePoussiere) {
        this.quantitePoussiere = quantitePoussiere;
    }

    public void vidage(){
        this.quantitePoussiere=0;
    }

    public void run(){
        try {
            TimeUnit.SECONDS.sleep((long) 2);
            vidage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
