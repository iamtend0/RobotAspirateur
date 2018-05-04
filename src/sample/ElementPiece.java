package sample;

public class ElementPiece {

    private int ligne;   // ligne de la matrice de la pièce;
    private int colonne; // colonne de la matrice de la pièce
    private String typeElement; // "BB", "T0", "OO", ... voir consigne

    /**
     * Constructeur ElementPiece
     * @param ligne
     * @param colonne
     * @param typeElement
     */
    public ElementPiece(int ligne, int colonne, String typeElement) {
        this.ligne = ligne;
        this.colonne = colonne;
        this.typeElement = typeElement;
    }

    /**
     * Accesseur ligne
     * @return ligne
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * Mutateur ligne
     * @param ligne
     */
    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    /**
     * Accesseur colonne
     * @return colonne
     */
    public int getColonne() {
        return colonne;
    }

    /**
     * Mutateur colonne
     * @param colonne
     */
    public void setColonne(int colonne) {
        this.colonne = colonne;
    }

    /**
     * Accesseur typeElement
     * @return typeElement
     */
    public String getTypeElement() {
        return typeElement;
    }

    /**
     * Mutateur typeElement
     * @param typeElement
     */
    public void setTypeElement(String typeElement) {
        this.typeElement = typeElement;
    }
}
