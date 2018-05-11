/*
 * Capteur.java
 */
package sample;

/**
 * Classe Capteur
 */
public class Capteur {

    private boolean Etat;
    private Piece piece;
    /**
     * Attributs
     */


    public Capteur(Piece piece, boolean etat) {
        this.piece = piece;
        Etat = etat;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEtat() {
        return Etat;
    }

    public void setEtat(boolean etat) {
        Etat = etat;
    }

    public void capteurCollision(int x,int y) {
        for (ElementPiece element : piece.getMatrice()) {
            if (element.getTypeElement().equals("OO") && element.getLigne() == x && element.getColonne() == y ) {
                setEtat(true);

            }
        }
    }

    public void capteurVide(int x,int y) {
        for (ElementPiece element : piece.getMatrice()) {
            if (element.getTypeElement().equals("VV") && element.getLigne() == x && element.getColonne() == y ) {
                setEtat(true);

            }
        }
    }

    public int[] capteurTapis(int x,int y) {
        int c=0;
        int d=0;
        for (int j=0;j<piece.getMatrice().size();j++) {
            for(int i=0;i<10;i++) {
                if (piece.getMatrice().get(j).getTypeElement().equals("T" +i) && piece.getMatrice().get(j).getLigne() == x && piece.getMatrice().get(j).getColonne() == y) {
                    setEtat(false);
                    c=j;
                    d=i;



                }
            }
        }
        return new int[] {c,d};
    }

    public int[] capteurSol(int x,int y) {
        int c=0;
        int d=0;
        for (int j=0;j<piece.getMatrice().size();j++) {
            for(int i=0;i<10;i++) {
                if (piece.getMatrice().get(j).getTypeElement().equals("0" +i) && piece.getMatrice().get(j).getLigne() == x && piece.getMatrice().get(j).getColonne() == y) {
                    setEtat(false);
                    c=j;
                    d=i;



                }
            }
        }
        return new int[] {c,d};
    }

    public int[] capteurBase(){
        int ligne=0;
        int colonne=0;
        for (ElementPiece element : piece.getMatrice()) {
            if (element.getTypeElement().equals("BB") ) {
                ligne=element.getLigne();
                colonne=element.getColonne();
            }
        }
        return new int[] {ligne,colonne};
    }




}
