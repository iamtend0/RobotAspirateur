/*
 * Base.java
 */
package sample;

/**
 * Classe Base
 */
public class Base {

    /** Attributs */
    private Capteur presence;
    private Robot robot;
    private int positionx;
    private int positiony;

    /**
     * Constructor
     * @param presence
     * @param robot
     * @param positionx
     * @param positiony
     */
    public Base(Capteur presence,Robot robot, int positionx, int positiony) {
        this.presence = presence;
        this.robot=robot;
        this.positionx = positionx;
        this.positiony = positiony;
    }

    /**
     * Accesseur
     * @return
     */
    public Capteur getPresence() {
        return presence;
    }

    /**
     * Accesseur position x
     * @return x
     */
    public int getPositionx() {
        return positionx;
    }

    /**
     * Accesseur position y
     * @return y
     */
    public int getPositiony() {
        return positiony;
    }

    public void recharge(){
        if(getPresence().Etat==true){
            robot.getBatterie().start();
            robot.getReserve().start();
        }
        getPresence().setEtat(false);
    }

}
