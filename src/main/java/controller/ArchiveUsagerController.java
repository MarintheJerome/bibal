package controller;

/**
 * Created by jerome on 02/11/2016.
 */
import common.Popup;
import database.UsagerDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Usager;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ArchiveUsagerController implements Initializable {

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
    private Button annuleDeleteOeuvreButton;

    @FXML
    private Button deleteUsagerButton;

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
            int idUsager = Integer.parseInt(usagerComboBox.getSelectionModel().getSelectedItem().split(":")[0]);
            Usager usager = usagerDB.findById(idUsager);
            prenomText.setText(usager.getPrenom());
            nameText.setText(usager.getNom());
            mailText.setText(usager.getMail());
            adresseText.setText(usager.getAdresse());
        }
    }

    @FXML
    public void deleteUsager() throws SQLException{
        if(usagerComboBox.getItems().size() > 0 && usagerComboBox.getSelectionModel().getSelectedItem() != null){
            int idUsager = Integer.parseInt(usagerComboBox.getSelectionModel().getSelectedItem().split(":")[0]);
            usagerDB.archive(idUsager);
            remplirComboBox();
            prenomText.setText("");
            nameText.setText("");
            adresseText.setText("");
            mailText.setText("");
            usagerComboBox.setValue(null);
            Popup.popUpInfo("Archive réussie", "L'usager a été correctement archivé");
        }else{
            Popup.popUpError("Erreur", "Il faut selectionner un usager !");
        }
    }

    @FXML
    public void annuler(){
        AnchorPane parent = (AnchorPane) nameText.getParent().getParent();
        parent.getChildren().clear();
    }
}

