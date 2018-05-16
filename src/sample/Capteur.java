/*
 * Capteur.java
 */
package sample;

/**
 * Classe Capteur
 */
public class Capteur {

    /**
     * Attributs
     */
    private boolean Etat; //etat du capteur (active ou desactive)
    private Piece piece;  //piece que le robot doit cartographier

    /**
     * Constructeur de capteur
     *
     * @param piece
     * @param etat
     */
    public Capteur(Piece piece, boolean etat) {
        this.piece = piece;
        Etat = etat;
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
     * Accesseur de l'etat du capteur
     *
     * @return Etat du capteur
     */
    public boolean isEtat() {
        return Etat;
    }

    /**
     * Mutateur de l'etat du capteur
     *
     * @param etat
     */
    public void setEtat(boolean etat) {
        Etat = etat;
    }

    /**
     * Recherche d'obstacle dans la piece
     *
     * @param x position du robot en x
     * @param y position du robot en y
     */
    public void capteurCollision(int x, int y) {
        for (ElementPiece element : piece.getMatrice()) {
            //on regarde si les capteurs detectent un obstacle
            if (element.getTypeElement().equals("OO") && element.getLigne() == x && element.getColonne() == y) {
                setEtat(true);

            }
        }
    }

    /**
     * Recherche du vide dans la piece
     *
     * @param x position du robot en x
     * @param y position du robot en y
     */
    public void capteurVide(int x, int y) {
        for (ElementPiece element : piece.getMatrice()) {
            //on regarde si les capteurs detectent du vide
            if (element.getTypeElement().equals("VV") && element.getLigne() == x && element.getColonne() == y) {
                setEtat(true);

            }
        }
    }

    /**
     * Recherche de tapis dans la piece
     *
     * @param x position du robot en x
     * @param y position du robot en y
     * @return liste de l'indice du tapis detecte avec son taux de poussiere
     */
    public int[] capteurTapis(int x, int y) {
        int indiceTapis = 0; //indice de l'element tapis dans la piece
        int tauxPoussiere = 0; //taux de poussière sur le tapis
        for (int j = 0; j < piece.getMatrice().size(); j++) {
            for (int i = 0; i < 10; i++) {
                if (piece.getMatrice().get(j).getTypeElement().equals("T" + i) && piece.getMatrice().get(j).getLigne() == x && piece.getMatrice().get(j).getColonne() == y) {
                    setEtat(false);
                    indiceTapis = j;
                    tauxPoussiere = i;


                }
            }
        }
        return new int[]{indiceTapis, tauxPoussiere};
    }

    /**
     * Recherche de sol dans la piece
     *
     * @param x
     * @param y
     * @return liste de l'indice du sol detecte avec son taux de poussiere
     */
    public int[] capteurSol(int x, int y) {
        int indiceSol = 0; //indice de l'element sol dans la piece
        int tauxPoussiere = 0; //taux de poussière sur le sol
        for (int j = 0; j < piece.getMatrice().size(); j++) {
            for (int i = 0; i < 10; i++) {
                if (piece.getMatrice().get(j).getTypeElement().equals("0" + i) && piece.getMatrice().get(j).getLigne() == x && piece.getMatrice().get(j).getColonne() == y) {
                    System.out.println(y);
                    setEtat(false);
                    indiceSol = j;
                    tauxPoussiere = i;


                }
            }
        }
        return new int[]{indiceSol, tauxPoussiere};
    }

    /**
     * Recherche de la base dans la piece
     *
     * @return la position de la base
     */
    public int[] capteurBase() {
        int ligne = 0; //ligne où se situe la base
        int colonne = 0;//colonne où se situe la base
        for (ElementPiece element : piece.getMatrice()) {
            if (element.getTypeElement().equals("BB")) {
                ligne = element.getLigne();
                colonne = element.getColonne();
            }
        }
        return new int[]{ligne, colonne};
    }


}
