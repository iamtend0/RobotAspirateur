package sample;

/**
 * Classe exception.
 * Permet de gérer les éléments d'une pièce.
 */
public class NotElementException extends Exception {
    public NotElementException(){

        System.out.println("Exception : votre pièce contient des éléments invalides.");

    }
}
