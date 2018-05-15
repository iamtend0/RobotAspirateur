/*
 * Main.java
 */
package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe principale
 */
@SuppressWarnings("Duplicates")

public class Main extends Application {

    private static int index = 0;
    private int distanceParcourue = 0; //distance parcourue par le robot
    private int retourBase = 0;        //nombre de fois où le robot est retourné à la base
    private int quantitePoussiere = 0; //quantité de poussière amassé par le robot

    Piece piece;

    {
        try {
            piece = new Piece("data/mapiece.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Batterie batterie = new Batterie(100);
    Reserve reserve = new Reserve(50);
    Capteur capteur = new Capteur(piece, false);
    Base base = new Base(batterie, capteur, reserve);
    Robot robot = new Robot(batterie, reserve, base, piece, 4);


    Pane pane = new Pane();
    GridPane gridpane = new GridPane();

    /* Declaration des Sprites */

    Image a = new Image("File:Sprite/Idle.png");
    ImageView aspirateur = new ImageView(a);

    /* Declaration de tous les labels */
    Label elementBase = new Label("BB");
    Label elementTapis2 = new Label(" ");
    Label elementSol2 = new Label(" ");
    Label aspirationTapis = new Label("Aspiration en cours du tapis...");
    Label aspirationSol = new Label("Aspiration en cours du sol...");
    Label temps = new Label("Temps ecoulé depuis demarrage :");
    Label etatBatterie = new Label("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
    Label etatReserve = new Label("Etat de la Reserve:" + robot.getReserve().getQuantitePoussiere() + "/" + robot.getReserve().getTaille());
    Label poussiereRamasse = new Label("Quantité de poussière aspirée :" + quantitePoussiere);
    Label atBase = new Label("Recharge de la batterie et vidage du reservoir en cours");
    Label distance = new Label("Distance parcourue par le robot :" + distanceParcourue);
    Label nbreBase = new Label("Nombre de fois où le robot est retourné à la base :" + retourBase);
    Label labelSliderBatterie = new Label(" ");
    Label labelSliderReserve = new Label(" ");
    Label optionBatterie = new Label("Capacité de la batterie :");
    Label optionReserve = new Label("Capacité de la reserve: ");

    /* Elements necessaires pour les options */
    MenuItem capaciteBatterie = new MenuItem("Changer la capacité de la batterie et/ou de la reserve");
    Slider changeBatterie = new Slider(1, 100, 50);
    Slider changeReserve = new Slider(1, 100, 50);
    Menu menu1 = new Menu("Option");
    MenuBar barre = new MenuBar();

    Alert alertBatterie= new Alert(Alert.AlertType.INFORMATION,"La batterie est vide");


    /**
     * Programme principal
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Recherche si un node(tapis,sol...) se trouve dejà à un endroit specifique du gridpane et le retire
     *
     * @param gridpane
     * @param col
     * @param row
     */
    private void getNodeFromGridPane(GridPane gridpane, int col, int row) {
        for (Node node : gridpane.getChildren()) {
            if (node != aspirateur && GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                gridpane.getChildren().remove(node);
                break;
            }
        }
    }

    /**
     * Fixe une position en x et y au label
     *
     * @param label
     * @param x
     * @param y
     */
    public void positionLabel(Label label, int x, int y) {
        label.setTranslateX(x);
        label.setTranslateY(y);
    }


    /**
     * Retourne si le capteur a detecté du vide ou non
     *
     * @param x
     * @param y
     * @param positionCapteur
     * @return detection
     */

    public boolean capteurVide(int x, int y, int positionCapteur) {
        Label elementVide = new Label("V");
        boolean detection = false;
        //appelle la fonction capteurVide pour savoir si le capteur detecte du vide en position (x,y)...
        robot.getCapteurs().get(4).capteurVide(x, y);
        if (robot.getCapteurs().get(4).isEtat() == true) {
            /* si le capteur detecte du vide alors on place en fonction du capteur qui a detecté le vide (gauche,droite,haut,bas)
            determiné par l'entier positionCapteur, l'élement du vide sur le gridpane */
            switch (positionCapteur) {
                case 0:
                    gridpane.getChildren().remove(elementVide);
                    gridpane.add(elementVide, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur) - 1);
                    break;
                case 1:
                    gridpane.getChildren().remove(elementVide);
                    gridpane.add(elementVide, gridpane.getColumnIndex(aspirateur) - 1, gridpane.getRowIndex(aspirateur));
                    break;
                case 2:
                    gridpane.getChildren().remove(elementVide);
                    gridpane.add(elementVide, gridpane.getColumnIndex(aspirateur) + 1, gridpane.getRowIndex(aspirateur));
                    break;
                case 3:
                    gridpane.getChildren().remove(elementVide);
                    gridpane.add(elementVide, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur) + 1);
                    break;

            }
            //on remet l'etat du capteur a false
            robot.getCapteurs().get(4).setEtat(false);
            //on a bien detecté du vide donc détection est à true
            detection = true;

        }
        return detection;

    }

    /**
     * Retourne si le capteur a detecté un obstacle ou non
     *
     * @param x
     * @param y
     * @param positionCapteur
     * @return detection
     */

    public boolean capteurObstacle(int x, int y, int positionCapteur) {
        Label elementObstacle = new Label("O");
        boolean detection = false;
        //appelle la fonction capteurObstacle pour savoir si le capteur detecte un obstacle en position (x,y)
        robot.getCapteurs().get(positionCapteur).capteurCollision(x, y);
        if (robot.getCapteurs().get(positionCapteur).isEtat() == true) {
             /* si le capteur detecte un obstacle alors on place en fonction du capteur qui a detecté l'obstacle (gauche,droite,haut,bas)
            determiné par l'entier positionCapteur, l'élement de l'obstacle sur le gridpane */
            switch (positionCapteur) {
                case 0:
                    gridpane.getChildren().remove(elementObstacle);
                    gridpane.add(elementObstacle, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur) - 1);
                    break;
                case 1:
                    gridpane.getChildren().remove(elementObstacle);
                    gridpane.add(elementObstacle, gridpane.getColumnIndex(aspirateur) - 1, gridpane.getRowIndex(aspirateur));
                    break;
                case 2:
                    gridpane.getChildren().remove(elementObstacle);
                    gridpane.add(elementObstacle, gridpane.getColumnIndex(aspirateur) + 1, gridpane.getRowIndex(aspirateur));
                    break;
                case 3:
                    gridpane.getChildren().remove(elementObstacle);
                    gridpane.add(elementObstacle, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur) + 1);
                    break;

            }
            //on remet l'etat du capteur a false
            robot.getCapteurs().get(positionCapteur).setEtat(false);
            //on a bien detecté un obstacle donc detection est à true
            detection = true;

        }
        return detection;

    }

    /**
     * Detection des tapis et aspiration
     *
     * @param x
     * @param y
     * @param positionCapteur
     */

    public void capteurTapis(int x, int y, int positionCapteur) {
        Label elementTapis = new Label("");
        positionLabel(aspirationTapis, 30, 250);
        aspirationTapis.setVisible(false);
        int indice[] = robot.getCapteurs().get(positionCapteur).capteurTapis(x, y);
        int poussiere = indice[1]; //Quantité de poussière actuellement presente sur le tapis
        int poussiereAspire = poussiere - robot.getPuissanceAspiration(); //Quantité de poussiere qui va être aspiré
        int poussiereRestante = robot.getReserve().getTaille() - robot.getReserve().getQuantitePoussiere(); //Quantité de poussière restante dans le reservoir
        robot.getCapteurs().get(positionCapteur).setEtat(true);
        //appelle la fonction capteurTapis pour savoir si le capteur detecte un tapis en position (x,y)
        robot.getCapteurs().get(positionCapteur).capteurTapis(x, y);

        if (!robot.getCapteurs().get(positionCapteur).isEtat()) {
            gridpane.getChildren().remove(elementTapis2);
            elementTapis2.setText("T" + poussiere);
            gridpane.add(elementTapis2, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
            //on decharge la batterie car le robot aspire
            if(robot.getBatterie().getEnergie()-1<0 ) {
                robot.getBatterie().setEnergie(0);
                alertBatterie.showAndWait();
                return;
            }
            else
                robot.dechargerBatterie(1);

            //on decharge la batterie car le robot est sur un tapis
            if(robot.getBatterie().getEnergie()-1.5<0) {
                robot.getBatterie().setEnergie(0);
                alertBatterie.showAndWait();
                return;
            }
            else
                robot.dechargerBatterie((float) 1.5);

            /* le capteur a detecte un tapis que le robot va aspirer si son reservoir n'est pas plein et s'il reste de la poussiere
            sur ce tapis. Le robot devra repasser sur le tapis s'il n'a pas pu aspirer toute la poussière */
            if (poussiere != 0 && poussiereRestante != 0) {
                aspirationTapis.setVisible(true);
                if (poussiere < robot.getPuissanceAspiration()) {
                    if (poussiereRestante < poussiere - robot.getPuissanceAspiration()) {
                        int reste = poussiere - poussiereRestante;
                        elementTapis.setText("T" + reste);
                        robot.remplirReserve(poussiereRestante);
                        quantitePoussiere += poussiereRestante;
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("T" + reste);
                    } else {
                        elementTapis.setText("T" + 0);
                        robot.remplirReserve(poussiere);
                        quantitePoussiere += poussiere;
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("T" + 0);
                    }
                } else {
                    if (poussiereRestante < robot.getPuissanceAspiration()) {
                        int reste = poussiere - poussiereRestante;
                        elementTapis.setText("T" + reste);
                        robot.remplirReserve(poussiereRestante);
                        quantitePoussiere += poussiereRestante;
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("T" + reste);
                    } else {
                        elementTapis.setText("T" + poussiereAspire);
                        robot.remplirReserve(robot.getPuissanceAspiration());
                        quantitePoussiere += robot.getPuissanceAspiration();
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("T" + poussiereAspire);
                    }
                }

                aspirationTapis.setVisible(true);

                //timer qui fixe le temps de l'aspiration à 0.50 secondes
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(0.50), event -> {
                            gridpane.getChildren().remove(elementTapis2);
                            //on regarde s'il n'y a pas un node qui occupe la case actuelle, si oui on le retire
                            getNodeFromGridPane(gridpane, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                            gridpane.add(elementTapis, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                            aspirationTapis.setVisible(false);
                            etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
                            etatReserve.setText("Etat de la Reserve:" + robot.getReserve().getQuantitePoussiere() + "/" + robot.getReserve().getTaille());
                            poussiereRamasse.setText("Quantité de poussière aspirée :" + quantitePoussiere);
                        })

                );
                timer.play();

            }
        }

        robot.getCapteurs().get(positionCapteur).setEtat(false);

    }

    /**
     * Detection des sols et aspiration
     *
     * @param x
     * @param y
     * @param positionCapteur
     */


    public void capteurSol(int x, int y, int positionCapteur) {
        Label elementSol = new Label("");
        positionLabel(aspirationSol, 30, 250);
        aspirationSol.setVisible(false);

        int indice[] = robot.getCapteurs().get(positionCapteur).capteurSol(x, y);
        int poussiere = indice[1]; //Quantité de poussière actuellement presente sur le sol
        int poussiereAspire = poussiere - robot.getPuissanceAspiration();//Quantité de poussiere qui va être aspiré
        int poussiereRestante = robot.getReserve().getTaille() - robot.getReserve().getQuantitePoussiere();//Quantité de poussière restante dans le reservoir
        robot.getCapteurs().get(positionCapteur).setEtat(true);
        //appelle la fonction capteurSol pour savoir si le capteur detecte un sol en position (x,y)
        robot.getCapteurs().get(positionCapteur).capteurSol(x, y);
        if (robot.getCapteurs().get(positionCapteur).isEtat() == false) {
            gridpane.getChildren().remove(elementSol2);
            elementSol2.setText("T" + poussiere);
            gridpane.add(elementSol2, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));

            elementSol2.setText("0" + poussiere);

            if(robot.getBatterie().getEnergie()-1<0 ) {
                robot.getBatterie().setEnergie(0);
                alertBatterie.showAndWait();
                return;
            }
            else
                robot.dechargerBatterie(1);
            /* le capteur a detecte un sol que le robot va aspirer si son reservoir n'est pas plein et s'il reste de la poussiere
            sur ce sol. Le robot devra repasser sur le sol s'il n'a pas pu aspirer toute la poussière */
            if (poussiere != 0 && poussiereRestante != 0) {
                if (poussiere < robot.getPuissanceAspiration()) {
                    if (poussiereRestante < poussiere - robot.getPuissanceAspiration()) {
                        int reste = poussiere - poussiereRestante;
                        elementSol.setText("0" + reste);
                        robot.remplirReserve(poussiereRestante);
                        quantitePoussiere += poussiereRestante;
                        //mise a jour du sol dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("0" + reste);
                    } else {
                        elementSol.setText("0" + 0);
                        robot.remplirReserve(poussiere);
                        quantitePoussiere += poussiere;
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("0" + 0);
                    }
                } else {
                    if (poussiereRestante < robot.getPuissanceAspiration()) {
                        int reste = poussiere - poussiereRestante;
                        elementSol.setText("0" + reste);
                        robot.remplirReserve(poussiereRestante);
                        quantitePoussiere += poussiereRestante;
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("0" + reste);
                    } else {
                        elementSol.setText("0" + poussiereAspire);
                        robot.remplirReserve(robot.getPuissanceAspiration());
                        quantitePoussiere += robot.getPuissanceAspiration();
                        //mise a jour du tapis dans la piece avec sa nouvelle quantité de poussière
                        robot.getPiece().getMatrice().get(indice[0]).setTypeElement("0" + poussiereAspire);
                    }
                }
                aspirationSol.setVisible(true);
                //on decharge la batterie car le robot aspire
                robot.dechargerBatterie(1);
                //timer qui fixe le temps de l'aspiration à 0.50 secondes
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(0.50), event -> {
                            gridpane.getChildren().remove(elementSol2);
                            //on regarde s'il n'y a pas un node qui occupe la case actuelle, si oui on le retire
                            getNodeFromGridPane(gridpane, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                            gridpane.add(elementSol, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                            aspirationSol.setVisible(false);
                            etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
                            etatReserve.setText("Etat de la Reserve:" + robot.getReserve().getQuantitePoussiere() + "/" + robot.getReserve().getTaille());
                            poussiereRamasse.setText("Quantité de poussière aspirée :" + quantitePoussiere);
                        })

                );
                timer.play();
            }
        }

        robot.getCapteurs().get(positionCapteur).setEtat(false);
    }

    /**
     * Ouvre une fenetre d'option pour modifier differentes valeurs
     */
    public void option() {

        menu1.getItems().add(capaciteBatterie);
        barre.getMenus().add(menu1);
        changeBatterie.setTranslateY(20);
        changeReserve.setTranslateY(60);
        barre.setUseSystemMenuBar(true);
        barre.setTranslateX(400);
        barre.setTranslateY(140);


        capaciteBatterie.setOnAction(e -> {
            Pane option = new Pane();

            option.getChildren().removeAll(labelSliderBatterie, labelSliderReserve, changeBatterie, changeReserve, optionBatterie, optionReserve);
            option.getChildren().addAll(labelSliderBatterie, labelSliderReserve, changeBatterie, changeReserve, optionBatterie, optionReserve);

            //affiche en temps reel la valeur du slider pour la batterie
            changeBatterie.valueProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> labelSliderBatterie.textProperty().setValue(
                    String.valueOf((int) changeBatterie.getValue())));
            //affiche en temps reel la valeur du slider pour la reserve
            changeReserve.valueProperty().addListener((ChangeListener) (arg0, arg1, arg2) -> labelSliderReserve.textProperty().setValue(
                    String.valueOf((int) changeReserve.getValue())));

            //creation de la nouvelle fenetre d'option
            Scene secondScene = new Scene(option, 200, 200);
            Stage newWindow = new Stage();
            newWindow.setTitle("Options");
            newWindow.setScene(secondScene);
            newWindow.show();


        });
        //changement en temps reel de la capacite de la batterie avec un slider
        changeBatterie.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                robot.getBatterie().setCapacité((int) new_val.doubleValue());
                robot.getBatterie().setEnergie(robot.getBatterie().getCapacité());
                etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());

            }
        });
        //changement en temps reel de la taille de la reserve avec un slider
        changeReserve.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                robot.getReserve().setTaille((int) new_val.doubleValue());
                etatReserve.setText("Etat de la Reserve:" + robot.getReserve().getQuantitePoussiere() + "/" + robot.getReserve().getTaille());

            }
        });


    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        /*construit les colonnes et les lignes du gridpane
        dont la taille sera la taille de la piece */
        for (int i = 0; i < piece.getNblignes(); i++) {
            RowConstraints row = new RowConstraints(20);
            row.setPercentHeight(50);
            gridpane.getRowConstraints().add(row);
        }
        for (int i = 0; i < piece.getNbcolonnes(); i++) {
            ColumnConstraints column = new ColumnConstraints(20);
            column.setPercentWidth(50);
            gridpane.getColumnConstraints().add(column);
        }

        //appelle la fonction capteurBase pour determiner la position de la Base
        int[] positionBase = robot.getBase().getPresence().capteurBase();
        //fixe la position initial du robot sur la Base
        gridpane.add(aspirateur, positionBase[1], positionBase[0]);
        //ajoute au gridpane l'element de la Base
        gridpane.add(elementBase, positionBase[1], positionBase[0]);


        /*fixe la position de tous les labels */
        positionLabel(temps, 400, 5);
        positionLabel(etatBatterie, 400, 20);
        positionLabel(etatReserve, 400, 40);
        positionLabel(atBase, 30, 250);
        atBase.setVisible(false);
        aspirationTapis.setVisible(false);
        aspirationSol.setVisible(false);
        positionLabel(distance, 400, 60);
        positionLabel(nbreBase, 400, 80);
        positionLabel(poussiereRamasse, 400, 100);
        positionLabel(optionBatterie, 0, 0);
        positionLabel(optionReserve, 0, 45);
        positionLabel(labelSliderBatterie, 0, 30);
        positionLabel(labelSliderReserve, 0, 70);

        //appelle le menu
        option();

        AtomicBoolean etat = new AtomicBoolean(true);
        //afficher la grille pour le debug
        //gridpane.setGridLinesVisible(true);
        gridpane.setPrefSize(200, 200);

        pane.getChildren().add(temps);
        pane.getChildren().add(aspirationTapis);
        pane.getChildren().add(aspirationSol);
        pane.getChildren().add(etatBatterie);
        pane.getChildren().add(etatReserve);
        pane.getChildren().add(atBase);
        pane.getChildren().add(distance);
        pane.getChildren().add(nbreBase);
        pane.getChildren().add(poussiereRamasse);
        pane.getChildren().add(barre);
        pane.getChildren().add(gridpane);


        Scene scene = new Scene(pane, 500, 500);



        /* deplacement du robot en fonction de la touche pressé (haut,bas,gauche ou droite) */
        scene.setOnKeyPressed(key -> {
            //le robot avance tant que sa batterie n'est pas vide
            if (!robot.batterieVide()) {
                distanceParcourue += 1;
                distance.setText("Distance parcourue par le robot :" + distanceParcourue);
                switch (key.getCode()) {
                    case UP:
                        try {
                            if (gridpane.getRowIndex(aspirateur) > 0 && robot.getBase().getPresence().isEtat() == false) {
                                //on regarde si le robot change de direction
                                if (index != gridpane.getRowIndex(aspirateur)) {
                                    if(robot.getBatterie().getEnergie()-2<0){
                                        robot.getBatterie().setEnergie(0);
                                        alertBatterie.showAndWait();
                                        break;
                                    }

                                    else{
                                        robot.dechargerBatterie(2);
                                    }

                                } else {
                                    if(robot.getBatterie().getEnergie()-1 <0) {
                                        robot.getBatterie().setEnergie(0);
                                        alertBatterie.showAndWait();
                                        break;
                                    }
                                    else
                                    robot.dechargerBatterie(1);
                                }
                                gridpane.getChildren().remove(aspirateur);
                                //le robot met 0.25 secondes pour effectuer un mouvement
                                TimeUnit.SECONDS.sleep((long) 0.25);
                                etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
                                int colonne = gridpane.getColumnIndex(aspirateur);
                                if (gridpane.getColumnIndex(aspirateur) != 0)
                                    colonne = gridpane.getColumnIndex(aspirateur) * 2;

                                //on regarde si le robot detecte un obstacle, dans ce cas il n'avance pas
                                if (capteurObstacle(gridpane.getRowIndex(aspirateur) - 1, colonne, 0)) {
                                    gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                } else {
                                    //sinon on regarde si le robot detecte du vide, dans ce cas il n'avance pas
                                    if (capteurVide(gridpane.getRowIndex(aspirateur) - 1, colonne, 0)) {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                    } else {
                                        //sinon on regarde si le robot detecte un tapis...
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur) - 1);
                                        capteurTapis(gridpane.getRowIndex(aspirateur), colonne, 0);
                                        //...ou un sol
                                        capteurSol(gridpane.getRowIndex(aspirateur), colonne, 0);
                                    }
                                }
                                index = gridpane.getRowIndex(aspirateur);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case DOWN:
                        try {
                            if (gridpane.getRowIndex(aspirateur) < (piece.getMatrice().size() / 3) - 1 && robot.getBase().getPresence().isEtat() == false) {
                                if (index != gridpane.getRowIndex(aspirateur)) {
                                    if(robot.getBatterie().getEnergie()-2<0){
                                        alertBatterie.showAndWait();
                                        robot.getBatterie().setEnergie(0);
                                        break;
                                    }

                                    else{
                                        robot.dechargerBatterie(2);
                                    }

                                } else {
                                    if(robot.getBatterie().getEnergie()-1 <0) {
                                        alertBatterie.showAndWait();
                                        robot.getBatterie().setEnergie(0);
                                        break;
                                    }
                                    else
                                        robot.dechargerBatterie(1);
                                }
                                gridpane.getChildren().remove(aspirateur);
                                TimeUnit.SECONDS.sleep((long) 0.25);
                                etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
                                int colonne = gridpane.getColumnIndex(aspirateur);
                                if (gridpane.getColumnIndex(aspirateur) != 0)
                                    colonne = gridpane.getColumnIndex(aspirateur) * 2;
                                if (capteurObstacle(gridpane.getRowIndex(aspirateur) + 1, colonne, 3)) {
                                    gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                } else {
                                    if (capteurVide(gridpane.getRowIndex(aspirateur) + 1, colonne, 3)) {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                    } else {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur) + 1);
                                        capteurTapis(gridpane.getRowIndex(aspirateur), colonne, 3);
                                        capteurSol(gridpane.getRowIndex(aspirateur), colonne, 3);
                                    }
                                }
                                index = gridpane.getRowIndex(aspirateur);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LEFT:
                        try {
                            if (gridpane.getColumnIndex(aspirateur) > 0 && robot.getBase().getPresence().isEtat() == false) {
                                if (index != gridpane.getColumnIndex(aspirateur)) {
                                    if(robot.getBatterie().getEnergie()-2<0){
                                        robot.getBatterie().setEnergie(0);
                                        alertBatterie.showAndWait();
                                        break;
                                    }

                                    else{
                                        robot.dechargerBatterie(2);
                                    }

                                } else {
                                    if(robot.getBatterie().getEnergie()-1 <0) {
                                        alertBatterie.showAndWait();
                                        robot.getBatterie().setEnergie(0);
                                        break;
                                    }
                                    else
                                        robot.dechargerBatterie(1);
                                }
                                gridpane.getChildren().remove(aspirateur);
                                TimeUnit.SECONDS.sleep((long) 0.25);
                                etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
                                int colonne = gridpane.getColumnIndex(aspirateur);
                                if (gridpane.getColumnIndex(aspirateur) % 2 != 0)
                                    colonne = gridpane.getColumnIndex(aspirateur) - 1;

                                if (capteurObstacle(gridpane.getRowIndex(aspirateur), colonne, 1)) {
                                    gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                } else {
                                    if (capteurVide(gridpane.getRowIndex(aspirateur), colonne, 1)) {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                    } else {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur) - 1, gridpane.getRowIndex(aspirateur));
                                        capteurTapis(gridpane.getRowIndex(aspirateur), colonne, 1);
                                        capteurSol(gridpane.getRowIndex(aspirateur), colonne, 1);

                                    }
                                }


                                index = gridpane.getColumnIndex(aspirateur);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case RIGHT:
                        try {
                            if (gridpane.getColumnIndex(aspirateur) < (piece.getMatrice().size() / 4) - 1 && robot.getBase().getPresence().isEtat() == false) {
                                if (index != gridpane.getColumnIndex(aspirateur)) {
                                    if(robot.getBatterie().getEnergie()-2<0){
                                        alertBatterie.showAndWait();
                                        robot.getBatterie().setEnergie(0);
                                        break;
                                    }

                                    else{
                                        robot.dechargerBatterie(2);
                                    }

                                } else {
                                    if(robot.getBatterie().getEnergie()-1 <0) {
                                        alertBatterie.showAndWait();
                                        robot.getBatterie().setEnergie(0);
                                        break;
                                    }
                                    else
                                        robot.dechargerBatterie(1);
                                }
                                gridpane.getChildren().remove(aspirateur);
                                TimeUnit.SECONDS.sleep((long) 0.25);
                                etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());

                                int colonne = gridpane.getColumnIndex(aspirateur);
                                if (gridpane.getColumnIndex(aspirateur) % 2 == 0)
                                    colonne = gridpane.getColumnIndex(aspirateur) + 1;
                                else
                                    colonne = gridpane.getColumnIndex(aspirateur) + 2;

                                if (capteurObstacle(gridpane.getRowIndex(aspirateur), colonne + 1, 2)) {
                                    gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                } else {
                                    if (capteurVide(gridpane.getRowIndex(aspirateur), colonne + 1, 2)) {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur), gridpane.getRowIndex(aspirateur));
                                    } else {
                                        gridpane.add(aspirateur, gridpane.getColumnIndex(aspirateur) + 1, gridpane.getRowIndex(aspirateur));
                                        capteurTapis(gridpane.getRowIndex(aspirateur), colonne + 1, 2);
                                        capteurSol(gridpane.getRowIndex(aspirateur), colonne + 1, 2);
                                    }
                                }
                                index = gridpane.getColumnIndex(aspirateur);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                //quand le robot a demarré son menage, il n'est plus possible d'acceder au menu des options
                if (gridpane.getRowIndex(aspirateur) != positionBase[0] || gridpane.getColumnIndex(aspirateur) != positionBase[1]) {
                    capaciteBatterie.setDisable(true);
                }
                //on regarde si le robot se trouve sur la base
                if (etat.get() == true && gridpane.getRowIndex(aspirateur) == positionBase[0] && gridpane.getColumnIndex(aspirateur) == positionBase[1]) {
                    robot.getBase().getPresence().setEtat(true);
                    etat.set(false);
                    atBase.setVisible(true);
                    retourBase += 1;
                    nbreBase.setText("Nombre de fois où le robot est retourné à la base :" + retourBase);
                    //on commence le processus de recharge de la batterie et de vidage du reservoir
                    new Thread(robot.getBase()).start();
                    Timeline timer = new Timeline(
                            new KeyFrame(Duration.seconds(5), event -> {
                                etatBatterie.setText("Etat de la Batterie :" + robot.getBatterie().getEnergie() + "/" + robot.getBatterie().getCapacité());
                                robot.getBase().getPresence().setEtat(false);
                                etat.set(true);
                                atBase.setVisible(false);
                            })


                    );
                    timer.play();
                    Timeline timer2 = new Timeline(
                            new KeyFrame(Duration.seconds(2), event -> etatReserve.setText("Etat de la Reserve:" + robot.getReserve().getQuantitePoussiere() + "% rempli"))


                    );
                    timer2.play();

                }
            }
            if(robot.batterieVide()){
                alertBatterie.showAndWait();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
