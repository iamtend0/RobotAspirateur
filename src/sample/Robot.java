/*
 * Robot.java
 */
package sample;


import java.util.ArrayList;


public class Robot {
;

    private Batterie batterie;
    private Reserve reserve;
    private ArrayList<Capteur> capteurs = new ArrayList<Capteur>(5);
    private Base base;
    private Piece piece;
    private int puissanceAspiration;
    private boolean aspiration;

    public Robot(Batterie batterie, Reserve reserve, Base base, Piece piece,int puissanceAspiration, boolean aspiration) {
        this.batterie = batterie;
        this.reserve = reserve;
        for(int i=0;i<5;i++){
            capteurs.add(new Capteur(piece,false));
        }
        this.base = base;
        this.piece = piece;
        this.puissanceAspiration=puissanceAspiration;
        this.aspiration = aspiration;
    }


    public Batterie getBatterie() {
        return batterie;
    }

    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }


    public ArrayList<Capteur> getCapteurs() {
        return capteurs;
    }

    public void setCapteurs(ArrayList<Capteur> capteurs) {
        this.capteurs = capteurs;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getPuissanceAspiration() {
        return puissanceAspiration;
    }

    public void setPuissanceAspiration(int puissanceAspiration) {
        this.puissanceAspiration = puissanceAspiration;
    }

    public boolean isAspiration() {
        return aspiration;
    }

    public void setAspiration(boolean aspiration) {
        this.aspiration = aspiration;
    }

    public void dechargerBatterie(int taux){
        if(taux==2){
            this.getBatterie().setEnergie(getBatterie().getEnergie()-2);
        }
        else {
            this.getBatterie().setEnergie(getBatterie().getEnergie()-1);
        }
    }

    public boolean batterieVide(){
        return getBatterie().getEnergie()<1;
    }

    public void remplirReserve(int poussiere){
        this.getReserve().setQuantitePoussiere(getReserve().getQuantitePoussiere()+poussiere);
    }

}
