package controller;

import common.Popup;
import database.OeuvreDB;
import database.ReservationDB;
import database.UsagerDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Reservation;
import model.Usager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by jerome on 03/11/2016.
 */
public class DeleteReservationController implements Initializable {

    private UsagerDB usagerDB;

    private OeuvreDB oeuvreDB;

    private ReservationDB reservationDB;

    private ArrayList<Reservation> reservations;

    @FXML
    private Button annulerDeleteReservation;

    @FXML
    private Button deleteReservation;

    @FXML
    private ComboBox<String> deleteReservationComboBox;

    @FXML
    private TextField nomUtilisateur;

    @FXML
    private TextField prenomUtilisateur;

    @FXML
    private TextField titreOeuvre;

    @FXML
    private TextField nomOeuvre;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteReservation.setDisable(true);
        usagerDB = new UsagerDB();
        oeuvreDB = new OeuvreDB();
        reservationDB = new ReservationDB();

        remplirComposants();
    }

    private void remplirComposants() {
        deleteReservationComboBox.getItems().clear();
        prenomUtilisateur.setText("");
        nomUtilisateur.setText("");
        titreOeuvre.setText("");
        nomOeuvre.setText("");

        try {
            reservations = reservationDB.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Reservation reservation : reservations){
            deleteReservationComboBox.getItems().add("idUsager:"+reservation.getUsager().getIdUsager() + " ISBN:"+reservation.getOeuvre().getISBN());
        }
    }

    @FXML
    public void selectReservation(){
        deleteReservation.setDisable(false);
        if(deleteReservationComboBox.getItems().size() > 0) {
            int index = deleteReservationComboBox.getSelectionModel().getSelectedIndex();
            Reservation reservation = reservations.get(index);
            nomUtilisateur.setText(reservation.getUsager().getNom());
            prenomUtilisateur.setText(reservation.getUsager().getPrenom());
            titreOeuvre.setText(reservation.getOeuvre().getTitre());
            nomOeuvre.setText(reservation.getOeuvre().getTitre());
        }
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) deleteReservationComboBox.getParent().getParent();
        parent.getChildren().clear();
    }

    @FXML
    public void deleteReservation() throws SQLException {
        if(deleteReservationComboBox.getSelectionModel().getSelectedItem() != null) {
            int index = deleteReservationComboBox.getSelectionModel().getSelectedIndex();
            Reservation reservation = reservations.get(index);
            reservationDB.delete(reservation.getUsager().getIdUsager(), reservation.getOeuvre().getISBN());
            Popup.popUpInfo("Reservation supprimée", "La reservation a été correctement supprimée");
            remplirComposants();
        }else{
            Popup.popUpError("Erreurt", "Veuillez selectionner une réservation.");
        }
    }
}
