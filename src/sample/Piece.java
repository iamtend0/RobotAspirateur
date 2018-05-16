package sample;

import javax.swing.text.Element;
import java.io.*;
import java.util.ArrayList;

public class Piece {
    private int nblignes;
    private int nbcolonnes;
    ArrayList<ElementPiece> elementsPiece = new ArrayList();

    /**
     * Construteur de pièce.
     * @param pieceTxt fichier à lire pour créer une pièce
     */
    public Piece(String pieceTxt) throws IOException {

        try {
            FileInputStream in = new FileInputStream(pieceTxt);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            // On remplit maintenant la liste des elements de la pièce
            int i = 0;
            while ((line = reader.readLine()) != null) {
                for (int j = 0; j <line.length(); j=j+2) {
                    this.elementsPiece.add(new ElementPiece(i, j, line.substring(j, j+2)));
                }
                i++;
                this.nbcolonnes=line.length()/2;
            }

            this.nblignes = elementsPiece.size()/nbcolonnes;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Accesseur de la matrice
     * @return matrice de la pièce
     */
    public ArrayList<ElementPiece> getMatrice() {
        return elementsPiece;
    }

    /**
     * Accesseur nblignes de la matrice
     * @return nblignes
     */
    public int getNblignes() {
        return nblignes;
    }

    /**
     * Accesseur nbcolonnes de la matrice
     * @return nbcolonnes
     */
    public int getNbcolonnes() {
        return nbcolonnes;
    }

    /**
     * Affiche en console la matrice de la pièce
     */
    public void afficherMatrice() {

        for(ElementPiece element : this.elementsPiece)
        {
            System.out.println("[" + element.getLigne() + ", " + element.getColonne() +
            "] => " + element.getTypeElement());
        }

    }
}

