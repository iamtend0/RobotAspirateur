package sample;

public class ElementPiece {

    private int ligne;   // ligne de la matrice de la pièce;
    private int colonne; // colonne de la matrice de la pièce
    private String typeElement; // "BB", "T0", "OO", ... voir consigne

    /**
     * Constructeur ElementPiece.
     * Si l'élément est invalide, alors le constructeur
     * déclenche l'exception NotElementException
     * @param ligne
     * @param colonne
     * @param typeElement
     */
    public ElementPiece(int ligne, int colonne, String typeElement) throws NotElementException {
        this.ligne = ligne;
        this.colonne = colonne;
        // Si l'élément est invalide, alors on déclenche une exception
        if(typeElement == "BB" || typeElement == "OO" || typeElement == "00" || typeElement == "01" ||
                typeElement == "02" || typeElement == "03" || typeElement == "04" || typeElement == "05" ||
                typeElement == "06" || typeElement == "07" || typeElement == "08" || typeElement == "09" ||
                typeElement == "T0" || typeElement == "T1" || typeElement == "T2" || typeElement == "T3" ||
                typeElement == "T4" || typeElement == "T5" || typeElement == "T6" || typeElement == "T7" ||
                typeElement == "T8" || typeElement == "T9" || typeElement == "VV") {
            this.typeElement = typeElement;
        } else {
            throw new NotElementException(); // exception déclenchée
        }

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
