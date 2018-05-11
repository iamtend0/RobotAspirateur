/*
 * Robot.java
 */
package sample;


import java.util.ArrayList;

/**
 * Classe Robot
 */
public class Robot {

    /**
     * Attributs
     */
    private Batterie batterie;
    private Reserve reserve;
    private ArrayList<Capteur> capteurs = new ArrayList<Capteur>(5); //liste des capteurs du robot
    private Base base; //base du robot
    private Piece piece; //piece pour le robot
    private int puissanceAspiration; //puissance d'aspiration de la poussière par le robot


    /**
     * Constructeur de robot
     *
     * @param batterie
     * @param reserve
     * @param base
     * @param piece
     * @param puissanceAspiration
     */
    public Robot(Batterie batterie, Reserve reserve, Base base, Piece piece, int puissanceAspiration) {
        this.batterie = batterie;
        this.reserve = reserve;
        for (int i = 0; i < 5; i++) {
            capteurs.add(new Capteur(piece, false));
        }
        this.base = base;
        this.piece = piece;
        this.puissanceAspiration = puissanceAspiration;
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
     * Mutateur batterie
     *
     * @param batterie
     */
    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
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
     * Mutateur reserve
     *
     * @param reserve
     */
    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    /**
     * Accesseur de la liste des capteurs
     *
     * @return liste des capteurs
     */
    public ArrayList<Capteur> getCapteurs() {
        return capteurs;
    }

    /**
     * Mutateur de la liste des capteurs
     *
     * @param capteurs liste des capteurs
     */
    public void setCapteurs(ArrayList<Capteur> capteurs) {
        this.capteurs = capteurs;
    }

    /**
     * Accesseur de la base
     *
     * @return base
     */
    public Base getBase() {
        return base;
    }

    /**
     * Mutateur de la base
     *
     * @param base
     */
    public void setBase(Base base) {
        this.base = base;
    }

    /**
     * Accesseur de la piece
     *
     * @return piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Mutateur de la piece
     *
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Accesseur de la puissance d'aspiration
     *
     * @return puissanceAspiration
     */
    public int getPuissanceAspiration() {
        return puissanceAspiration;
    }

    /**
     * Mutateur de la puissance d'aspiration
     *
     * @param puissanceAspiration
     */
    public void setPuissanceAspiration(int puissanceAspiration) {
        this.puissanceAspiration = puissanceAspiration;
    }


    /**
     * Decharge la batterie du robot selon le taux
     *
     * @param taux
     */
    public void dechargerBatterie(int taux) {
        if (taux == 2) {
            this.getBatterie().setEnergie(getBatterie().getEnergie() - 2);
        } else {
            this.getBatterie().setEnergie(getBatterie().getEnergie() - 1);
        }
    }

    /**
     * Regarde si la batterie est vide
     *
     * @return booleen
     */
    public boolean batterieVide() {
        return getBatterie().getEnergie() < 1;
    }

    /**
     * Remplie la reserve de poussiere
     *
     * @param poussiere quantite de poussiere à rajouter à la reserve
     */
    public void remplirReserve(int poussiere) {
        this.getReserve().setQuantitePoussiere(getReserve().getQuantitePoussiere() + poussiere);
    }

}
