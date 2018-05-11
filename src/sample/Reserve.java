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
    private int taille; //capacite de la reserve
    private int quantitePoussiere; //quantite de poussiere contenue dans la reserve

    /**
     * Constructeur de la reserve
     * @param taille
     */
    public Reserve(int taille) {
        this.taille = taille;
        this.quantitePoussiere=0;
    }

    /**
     * Accesseur de la taille de la reserve
     * @return taille
     */
    public int getTaille() {
        return taille;
    }

    /**
     * Mutateur de la taille de la reserve
     * @param taille
     */
    public void setTaille(int taille) {
        this.taille = taille;
    }

    /**
     * Accesseur de la quantite de poussiere
     * @return quantitePoussiere
     */
    public int getQuantitePoussiere() {
        return quantitePoussiere;
    }

    /**
     * Mutateur de la quantite de poussiere
     * @param quantitePoussiere
     */
    public void setQuantitePoussiere(int quantitePoussiere) {
        this.quantitePoussiere = quantitePoussiere;
    }

    /**
     * Vide la reserve de poussiere
     */
    public void vidage(){
        this.quantitePoussiere=0;
    }

    /**
     * Processus de vidage de la reserve avec un thread
     */
    public void run(){
        try {
            TimeUnit.SECONDS.sleep((long) 2);
            vidage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
