package controller;

/**
 * Created by jerome on 02/11/2016.
 */
import com.sun.org.apache.xml.internal.security.algorithms.implementations.IntegrityHmac;
import common.Popup;
import common.Variable;
import database.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Emprunt;
import model.Exemplaire;
import model.Oeuvre;
import model.Usager;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.sql.Date;

public class AddEmpruntController implements Initializable {

    private OeuvreDB oeuvreDB;
    private UsagerDB usagerDB;
    private ExemplaireDB exemplaireDB;
    private EmpruntDB empruntDB;
    private ReservationDB reservationDB;

    private ArrayList<Oeuvre> oeuvres;
    private ArrayList<Usager> usagers;
    private ArrayList<Exemplaire> exemplaires;

    @FXML
    private Button addEmprunt;

    @FXML
    private Button annuleAddEmprunt;

    @FXML
    private TextField dureeEmprunt;

    @FXML
    private ComboBox<String> addOeuvreEmpruntComboBox;

    @FXML
    private ComboBox<String> addUsagerEmpruntComboBox;

    @FXML
    private ComboBox<String> addExamplaireEmpruntComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oeuvreDB = new OeuvreDB();
        exemplaireDB = new ExemplaireDB();
        usagerDB = new UsagerDB();
        reservationDB = new ReservationDB();
        empruntDB = new EmpruntDB();

        remplirComboBox();
    }

    private void remplirComboBox() {
        addOeuvreEmpruntComboBox.getItems().clear();
        addUsagerEmpruntComboBox.getItems().clear();
        addExamplaireEmpruntComboBox.getItems().clear();

        try {
            oeuvres = oeuvreDB.selectAll();
            usagers = usagerDB.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Oeuvre oeuvre : oeuvres){
            addOeuvreEmpruntComboBox.getItems().add(oeuvre.getISBN()+": "+oeuvre.getTitre());
        }
        for(Usager usager : usagers){
            addUsagerEmpruntComboBox.getItems().add(usager.getIdUsager()+": "+usager.getNom()+" "+usager.getPrenom());
        }
    }

    @FXML
    public void selectOeuvre() throws SQLException {
        String ISBN = addOeuvreEmpruntComboBox.getSelectionModel().getSelectedItem().split(":")[0];
        exemplaires = exemplaireDB.selectAll(ISBN);

        for(Exemplaire exemplaire : exemplaires){
            if(!exemplaire.getEtatExemplaire().equals("Emprunté") && !exemplaire.getEtatExemplaire().equals("Mauvais")){
                addExamplaireEmpruntComboBox.getItems().add("Exemplaire:"+exemplaire.getIdExemplaire());
            }
        }
        Oeuvre oeuvre = oeuvreDB.findByISBN(ISBN);
        if(oeuvre.getType().equals("Livre")) {
            dureeEmprunt.setText(String.valueOf(Variable.EMPRUNTLIVRE));
        }
        if(oeuvre.getType().equals("Magazine")){
            dureeEmprunt.setText(String.valueOf(Variable.EMPUNTMAGAZINE));
        }
    }

    @FXML
    public void reservation() throws SQLException {
        if(addUsagerEmpruntComboBox.getSelectionModel().getSelectedItem() != null && addOeuvreEmpruntComboBox.getSelectionModel().getSelectedItem() != null){
            int idUsager = Integer.parseInt(addUsagerEmpruntComboBox.getSelectionModel().getSelectedItem().split(":")[0]);
            String ISBN = addOeuvreEmpruntComboBox.getSelectionModel().getSelectedItem().split(":")[0];
            reservationDB.insert(idUsager, ISBN, new Date(Calendar.getInstance().getTime().getTime()), "En cours");
            Popup.popUpInfo("Reservation créée", "La réservation a réussi.");
        }else{
            Popup.popUpError("Impossible de faire la réservation", "Veuillez selectionner un usager et une oeuvre.");
        }
    }

    @FXML
    public void addEmprunt() throws SQLException {
        if(addUsagerEmpruntComboBox.getSelectionModel().getSelectedItem() != null && addExamplaireEmpruntComboBox.getSelectionModel().getSelectedItem() != null){

            int idUsager =  Integer.parseInt(addUsagerEmpruntComboBox.getSelectionModel().getSelectedItem().split(":")[0]);
            int idExemplaire = Integer.parseInt(addExamplaireEmpruntComboBox.getSelectionModel().getSelectedItem().split(":")[1]);
            Emprunt emprunt = empruntDB.findByIds(idUsager, idExemplaire);
            if(emprunt == null || emprunt.getDateRetourPrevue().compareTo(new Date(Calendar.getInstance().getTime().getTime())) < 0){
                Exemplaire exemplaire = exemplaireDB.findById(idExemplaire);

                int duree = Integer.parseInt(dureeEmprunt.getText());
                Date dateNow = new Date(Calendar.getInstance().getTime().getTime());
                LocalDate localDate = dateNow.toLocalDate();
                localDate = localDate.plusDays(duree);
                empruntDB.insert(idUsager, idExemplaire, dateNow, duree, Date.valueOf(localDate), null);
                exemplaireDB.update(exemplaire.getIdExemplaire(), "Emprunté", exemplaire.getOeuvre().getISBN());

                Popup.popUpInfo("Emprunt crée", "L'emprunt a réussi.");
            }
            else{
                Popup.popUpError("Impossible de réaliser l'emprunt", "Exemplaire déjà reservé par l'utilisateur.");
            }
        }
        else{
            Popup.popUpError("Impossible de faire l'emprunt", "Veuillez selectionner un usager et un exemplaire.");
        }
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) addEmprunt.getParent().getParent();
        parent.getChildren().clear();
    }
}
