package controller;

import common.Popup;
import database.EmpruntDB;
import database.ExemplaireDB;
import database.OeuvreDB;
import database.UsagerDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Emprunt;
import model.Exemplaire;
import model.Oeuvre;
import model.Usager;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class UpdateEmpruntController implements Initializable {

    private ArrayList<Emprunt> emprunts;

    private EmpruntDB empruntDB;

    private UsagerDB usagerDB;

    private ExemplaireDB exemplaireDB;

    private OeuvreDB oeuvreDB;

    @FXML
    private ComboBox<String> deleteEmpruntComboBox, etatExemplaire;

    @FXML
    private TextField nomUtilisateur, prenomUtilisateur, titreOeuvre, nomOeuvre;

    @FXML
    private Label etatExemplaireLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        empruntDB = new EmpruntDB();
        usagerDB = new UsagerDB();
        exemplaireDB = new ExemplaireDB();
        oeuvreDB = new OeuvreDB();

        remplirComposants();
    }

    private void remplirComposants() {
        nomUtilisateur.setText("");
        prenomUtilisateur.setText("");
        titreOeuvre.setText("");
        nomOeuvre.setText("");

        deleteEmpruntComboBox.getItems().clear();
        etatExemplaireLabel.setVisible(false);
        etatExemplaire.getItems().clear();
        etatExemplaire.setVisible(false);

        try {
            emprunts = empruntDB.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for(Emprunt emprunt : emprunts){
            deleteEmpruntComboBox.getItems().add("idUsager: "+emprunt.getUsager().getIdUsager()+" idExemplaire: "+emprunt.getExemplaire().getIdExemplaire());
        }

        etatExemplaire.getItems().addAll("Bon", "Mauvais", "Emprunté");
    }

    @FXML
    public void selectEmprunt() throws SQLException {
        etatExemplaire.setVisible(true);
        etatExemplaireLabel.setVisible(true);

        if(deleteEmpruntComboBox.getItems().size() > 0){
            int index = deleteEmpruntComboBox.getSelectionModel().getSelectedIndex();
            Emprunt emprunt = emprunts.get(index);

            Usager usager = usagerDB.findById(emprunt.getUsager().getIdUsager());
            nomUtilisateur.setText(usager.getNom());
            prenomUtilisateur.setText(usager.getPrenom());

            Oeuvre oeuvre = oeuvreDB.findByISBN(emprunt.getExemplaire().getOeuvre().getISBN());
            titreOeuvre.setText(oeuvre.getTitre());
            nomOeuvre.setText(oeuvre.getNomOeuvre());

            etatExemplaire.setValue(emprunt.getExemplaire().getEtatExemplaire());
        }
    }

    @FXML
    public void finEmprunt() throws SQLException {
        if(!etatExemplaire.getSelectionModel().getSelectedItem().equals("Emprunté")){
            int index = deleteEmpruntComboBox.getSelectionModel().getSelectedIndex();
            Emprunt emprunt = emprunts.get(index);
            Exemplaire exemplaire = emprunt.getExemplaire();

            empruntDB.update(emprunt.getUsager().getIdUsager(), exemplaire.getIdExemplaire(), emprunt.getDateDebut(), emprunt.getDuree(), emprunt.getDateRetourPrevue(), new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

            exemplaireDB.update(exemplaire.getIdExemplaire(), etatExemplaire.getSelectionModel().getSelectedItem(), exemplaire.getOeuvre().getISBN());

            Popup.popUpInfo("Emprunt terminé", "L'exemplaire a été rendu, l'emprunt est terminé.");
            remplirComposants();
        }
        else{
            Popup.popUpError("Impossible de terminé l'emprunt", "Veuillez changer l'état de l'exemplaire");
        }
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) deleteEmpruntComboBox.getParent().getParent();
        parent.getChildren().clear();
    }
}
