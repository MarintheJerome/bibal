package controller;

import common.Popup;
import database.ExemplaireDB;
import database.OeuvreDB;
import database.ReservationDB;
import database.UsagerDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import model.Exemplaire;
import model.Oeuvre;
import model.Usager;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by jerome on 03/11/2016.
 */
public class AddReservationController implements Initializable{

    private UsagerDB usagerDB;

    private OeuvreDB oeuvreDB;

    private ReservationDB reservationDB;

    private ExemplaireDB exemplaireDB;

    private ArrayList<Usager> usagers;

    private ArrayList<Oeuvre> oeuvres;

    @FXML
    private Button annulerAddReservation;

    @FXML
    private Button addReservation;

    @FXML
    private ComboBox<String> addReservationOeuvreComboBox;

    @FXML
    private ComboBox<String> addReservationUsagerComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usagerDB = new UsagerDB();
        exemplaireDB = new ExemplaireDB();
        oeuvreDB = new OeuvreDB();
        reservationDB = new ReservationDB();

        remplirComposants();
    }

    private void remplirComposants() {
        addReservationUsagerComboBox.getItems().clear();
        addReservationOeuvreComboBox.getItems().clear();
        try {
            usagers = usagerDB.selectAll();
            oeuvres = oeuvreDB.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Usager usager : usagers){
            addReservationUsagerComboBox.getItems().add(usager.getIdUsager()+": "+usager.getNom()+" "+usager.getPrenom());
        }
        for(Oeuvre o : oeuvres){
            addReservationOeuvreComboBox.getItems().add(o.getISBN()+": "+o.getNomOeuvre()+" - "+o.getTitre());
        }
    }

    @FXML
    public void selectOeuvre() throws SQLException {
        addReservation.setDisable(false);
        int index = addReservationOeuvreComboBox.getSelectionModel().getSelectedIndex();
        Oeuvre oeuvre = oeuvres.get(index);
        ArrayList<Exemplaire> exemplaires = exemplaireDB.selectAll(oeuvre.getISBN());
        boolean peutEmprunter = false;
        for(Exemplaire exemplaire : exemplaires){
            if (exemplaire.getEtatExemplaire().equals("Bon")){
                peutEmprunter = true;
            }
        }

        if(peutEmprunter){
            Popup.popUpInfo("Emprunt possible", "Un exemplaire peut être emprunté. Veuillez vous rendre sur la page correspondante.");
            addReservation.setDisable(true);
        }
    }

    @FXML
    public void ajouterReservation() throws SQLException {
        if(addReservationOeuvreComboBox.getSelectionModel().getSelectedItem() != null && addReservationUsagerComboBox.getSelectionModel().getSelectedItem() != null){
            int index = addReservationOeuvreComboBox.getSelectionModel().getSelectedIndex();
            Oeuvre oeuvre = oeuvres.get(index);
            index = addReservationUsagerComboBox.getSelectionModel().getSelectedIndex();
            Usager usager = usagers.get(index);
            reservationDB.insert(usager.getIdUsager(), oeuvre.getISBN(), new Date(Calendar.getInstance().getTime().getTime()), "En cours");
            Popup.popUpInfo("Reservation réussie", "L'oeuvre a été reservée.");
        }
        else{
            Popup.popUpError("Impossible de réserver", "Veuillez selectionner une oeuvre et un usager");
        }
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) addReservationUsagerComboBox.getParent().getParent();
        parent.getChildren().clear();
    }
}
