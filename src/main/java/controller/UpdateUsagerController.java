package controller;

import common.Popup;
import database.UsagerDB;
import javafx.fxml.Initializable;

/**
 * Created by jerome on 02/11/2016.
 */
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Usager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateUsagerController implements Initializable {

    private UsagerDB usagerDB;

    private ArrayList<Usager> usagers;

    @FXML
    private TextField nameText;

    @FXML
    private TextField prenomText;

    @FXML
    private TextField adresseText;

    @FXML
    private TextField mailText;

    @FXML
    private ComboBox<String> usagerComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usagerDB = new UsagerDB();
        remplirComboBox();
    }

    private void remplirComboBox() {
        usagerComboBox.getItems().clear();
        try {
            usagers = usagerDB.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Usager usager : usagers){
            usagerComboBox.getItems().add(usager.getIdUsager()+": "+usager.getNom()+" "+usager.getPrenom());
        }
    }

    @FXML
    public void clickComboBox() throws SQLException {
        if(usagerComboBox.getItems().size() > 0){
            int idUsager = Integer.parseInt(usagerComboBox.getSelectionModel().getSelectedItem().toString().split(":")[0]);
            Usager usager = usagerDB.findById(idUsager);
            nameText.setText(usager.getNom());
            prenomText.setText(usager.getPrenom());
            mailText.setText(usager.getMail());
            adresseText.setText(usager.getAdresse());
        }
    }

    @FXML
    public void update() throws SQLException{
        if(verifValeurs()){
            int idUsager = Integer.parseInt(usagerComboBox.getSelectionModel().getSelectedItem().toString().split(":")[0]);
            usagerDB.update(idUsager, nameText.getText(), prenomText.getText(), mailText.getText(), adresseText.getText());
            Popup.popUpInfo("Modification réussie", "L'utilisateur a été modifié.");
            prenomText.setText(prenomText.getText());
            nameText.setText(nameText.getText());
            adresseText.setText(mailText.getText());
            mailText.setText(adresseText.getText());
            remplirComboBox();
            usagerComboBox.setValue(idUsager+": "+nameText.getText()+" "+prenomText.getText());
        }
        else{
            Popup.popUpError("Impossible de modifier", "Veuillez rentrer un nom et un prénom.");
        }
    }

    private boolean verifValeurs() {
        return (!prenomText.getText().isEmpty() && !nameText.getText().isEmpty());
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) nameText.getParent().getParent();
        parent.getChildren().clear();
    }
}
