package controller;

import common.Popup;
import database.AuteurDB;
import database.OeuvreDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Yhugo on 02/11/2016.
 */
public class AddOeuvreController implements Initializable {

    private OeuvreDB odb = new OeuvreDB();
    private AuteurDB adb = new AuteurDB();

    @FXML
    private TextField isbnOeuvre,nomOeuvre,titreOeuvre, NumeroMagazine, nomAuteurOeuvre, prenomAuteurOeuvre;

    @FXML
    private CheckBox checkLivre, checkMagazine;

    @FXML
    private DatePicker dateOeuvre, dateEdition;

    @FXML
    private Button annuleAddOeuvreButton, addOeuvreButton;

    @FXML
    private ComboBox<String> comboBoxMagazine;

    @FXML
    private TextArea ResumeLivreText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkLivre.setOnAction(checker());
        checkMagazine.setOnAction(checker());
        ResumeLivreText.setDisable(true);
        dateEdition.setDisable(true);
        comboBoxMagazine.setDisable(true);
        NumeroMagazine.setDisable(true);
        prenomAuteurOeuvre.setDisable(true);
        nomAuteurOeuvre.setDisable(true);
        comboBoxMagazine.getItems().addAll("Journalier","Hebdomadaire","Mensuel","Bimensuel","Trimestriel");
    }

    private EventHandler<ActionEvent> checker() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if(checkLivre.isSelected()){
                    checkMagazine.setSelected(false);
                    ResumeLivreText.setDisable(false);
                    dateEdition.setDisable(false);
                    nomAuteurOeuvre.setDisable(false);
                    prenomAuteurOeuvre.setDisable(false);
                }
                if(!checkLivre.isSelected()){
                    ResumeLivreText.setDisable(true);
                    dateEdition.setDisable(true);
                    nomAuteurOeuvre.setDisable(true);
                    prenomAuteurOeuvre.setDisable(true);
                }
                if(checkMagazine.isSelected()){
                    checkLivre.setSelected(false);
                    comboBoxMagazine.setDisable(false);
                    NumeroMagazine.setDisable(false);
                }
                if(!checkMagazine.isSelected()){
                    comboBoxMagazine.setDisable(true);
                    NumeroMagazine.setDisable(true);
                }
            }
        };
    }

    @FXML
    public void annuler() throws SQLException {
        AnchorPane parent = (AnchorPane) isbnOeuvre.getParent().getParent();
        parent.getChildren().clear();
    }


    @FXML
    public void addOeuvre() throws SQLException {
        if(!isbnOeuvre.getText().isEmpty() && !nomOeuvre.getText().isEmpty() && !titreOeuvre.getText().isEmpty()){
            if(odb.findByISBN(isbnOeuvre.getText()) == null){
                if(!checkLivre.isSelected() && !checkMagazine.isSelected()) {
                    Popup.popUpError("Erreur", "Pas de choix fait entre Livre et Magazine.");
                }
                if(checkLivre.isSelected()) {
                    if (!nomAuteurOeuvre.getText().isEmpty() && !prenomAuteurOeuvre.getText().isEmpty()) {
                        int auteur = adb.getIdFromAuteur(nomAuteurOeuvre.getText(), prenomAuteurOeuvre.getText());
                        if (auteur == -1){
                            adb.insert(nomAuteurOeuvre.getText(), prenomAuteurOeuvre.getText());
                            auteur = adb.getIdFromAuteur(nomAuteurOeuvre.getText(), prenomAuteurOeuvre.getText());
                        }
                        odb.insertLivre(isbnOeuvre.getText(), nomOeuvre.getText(), titreOeuvre.getText(),
                                java.sql.Date.valueOf(dateOeuvre.getValue()), 0,
                                java.sql.Date.valueOf(dateEdition.getValue()), ResumeLivreText.getText(), auteur);
                        Popup.popUpInfo("Ajout", "Vous avez correctement ajouté un livre");
                        refresh();
                    } else {
                        Popup.popUpError("Erreur", "Auteur pas mis.");
                    }
                }
                if(checkMagazine.isSelected()){
                    if(!NumeroMagazine.getText().equals("") && !comboBoxMagazine.getValue().equals("")){
                        odb.insertMagasine(isbnOeuvre.getText(), nomOeuvre.getText(), titreOeuvre.getText(),java.sql.Date.valueOf(dateOeuvre.getValue()),0, Integer.parseInt(NumeroMagazine.getText()), comboBoxMagazine.getValue());
                        Popup.popUpInfo("Ajout", "Vous avez correctement ajouté un magazine");
                        refresh();
                    }else{
                        Popup.popUpError("Erreur", "Renseignement magazine non mis.");
                    }
                }
            }
        }else{
            Popup.popUpError("Erreur", "Un attribut vital n'est pas mis.");
        }
    }

    private void refresh(){
        isbnOeuvre.setText("");
        nomOeuvre.setText("");
        titreOeuvre.setText("");
        ResumeLivreText.setText("");
        NumeroMagazine.setText("");
        prenomAuteurOeuvre.setText("");
        nomAuteurOeuvre.setText("");
        dateEdition.setValue(null);
        dateOeuvre.setValue(null);
        checkLivre.setSelected(false);
        checkMagazine.setSelected(false);
    }
}
