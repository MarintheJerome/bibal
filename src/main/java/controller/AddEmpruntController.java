package controller;

/**
 * Created by jerome on 02/11/2016.
 */
import database.EmpruntDB;
import database.ExemplaireDB;
import database.OeuvreDB;
import database.UsagerDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Exemplaire;
import model.Oeuvre;
import model.Usager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddEmpruntController implements Initializable {

    private OeuvreDB oeuvreDB;
    private UsagerDB usagerDB;
    private ExemplaireDB exemplaireDB;
    private EmpruntDB empruntDB;

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
    private ComboBox<Integer> addExamplaireEmpruntComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oeuvreDB = new OeuvreDB();
        exemplaireDB = new ExemplaireDB();
        usagerDB = new UsagerDB();

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
        String ISBN = addOeuvreEmpruntComboBox.getSelectionModel().getSelectedItem().toString().split(":")[0];
        exemplaires = exemplaireDB.selectAll(ISBN);
        for(Exemplaire exemplaire : exemplaires){
            addExamplaireEmpruntComboBox.getItems().add(exemplaire.getIdExemplaire());
        }
        Oeuvre oeuvre = oeuvreDB.findByISBN(ISBN);
        if(oeuvre.getAuteur() != null) {
            dureeEmprunt.setText("14");
        }
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) addEmprunt.getParent().getParent();
        parent.getChildren().clear();
    }
}
