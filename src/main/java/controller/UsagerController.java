package controller;

/**
 * Created by jerome on 02/11/2016.
 */
import common.Popup;
import database.UsagerDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UsagerController implements Initializable {

    private Stage stage;

    private UsagerDB usagerDB;

    @FXML
    private TextField nameText;

    @FXML
    private TextField prenomText;

    @FXML
    private TextField adresseText;

    @FXML
    private TextField mailText;

    @FXML
    private Button annuleAddUsagerButton;

    @FXML
    private Button addUsagerButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usagerDB = new UsagerDB();
    }

    @FXML
    public void addUsager() throws SQLException{
        if(verifValeurs()){
            usagerDB.insert(nameText.getText(), prenomText.getText(), mailText.getText(), adresseText.getText());
            Popup.popUpInfo("Ajout réussi", "L'utilisateur a été ajouté.");
            prenomText.setText("");
            nameText.setText("");
            adresseText.setText("");
            mailText.setText("");
        }
        else{
            Popup.popUpError("Impossible d'insérer", "Veuillez rentrer un nom et un prénom.");
        }
    }

    private boolean verifValeurs() {
        return (!prenomText.getText().isEmpty() && !nameText.getText().isEmpty());
    }
}

