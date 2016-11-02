package controller;

import common.Popup;
import database.AuteurDB;
import database.ExemplaireDB;
import database.OeuvreDB;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Oeuvre;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Yhugo on 02/11/2016.
 */
public class UpdateOeuvreController  implements Initializable {

    private OeuvreDB odb = new OeuvreDB();
    private ExemplaireDB edb = new ExemplaireDB();
    private AuteurDB adb = new AuteurDB();
    public Oeuvre currentBook;
    private ArrayList<Oeuvre> oeuvres;

    @FXML
    private TextField isbnOeuvre,nomOeuvre,titreOeuvre, NumeroMagazine, nombreExemplaireOeuvre, nomAuteurOeuvre, prenomAuteurOeuvre;

    @FXML
    private CheckBox checkLivre, checkMagazine;

    @FXML
    private DatePicker dateOeuvre,dateEdition;

    @FXML
    private Button annuleAddOeuvreButton, updateOeuvreButton,addExemplaireOeuvre,deleteExemplaireOeuvre;

    @FXML
    private ComboBox<String> comboBoxMagazine,oeuvreComboBox;

    @FXML
    private TextArea ResumeLivreText;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkLivre.setOnAction(checker());
        checkMagazine.setOnAction(checker());
        remplirComboBox();
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

    private void remplirComboBox() {
        oeuvreComboBox.getItems().clear();
        try {
            oeuvres = odb.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Oeuvre o : oeuvres){
            oeuvreComboBox.getItems().add(o.getISBN()+": "+o.getNomOeuvre()+" - "+o.getTitre());
        }
    }

    @FXML
    public void clickComboBox() throws SQLException {
        if(oeuvreComboBox.getItems().size() > 0){
            String isbn = oeuvreComboBox.getSelectionModel().getSelectedItem().toString().split(":")[0];
            Oeuvre book = odb.findByISBN(isbn);
            currentBook = book;
            isbnOeuvre.setText(book.getISBN());
            isbnOeuvre.setDisable(true);
            nomOeuvre.setText(book.getNomOeuvre());
            titreOeuvre.setText(book.getTitre());
            if(book.getType().equals("Livre")){
                checkMagazine.setDisable(true);
                ResumeLivreText.setText(book.getResume());
                nomAuteurOeuvre.setText(book.getAuteur().getNomAuteur());
                prenomAuteurOeuvre.setText(book.getAuteur().getPrenomAuteur());
                checkLivre.setSelected(true);
                dateEdition.setValue(book.getDateEdition().toLocalDate());
                comboBoxMagazine.setDisable(true);
                NumeroMagazine.setDisable(true);
                comboBoxMagazine.setValue(null);
                NumeroMagazine.setText(null);
                dateEdition.setDisable(false);
                ResumeLivreText.setDisable(false);
                prenomAuteurOeuvre.setDisable(false);
                nomAuteurOeuvre.setDisable(false);
            }
            if(book.getType().equals("Magazine")){
                checkLivre.setDisable(true);
                NumeroMagazine.setText(""+book.getNumero());
                comboBoxMagazine.setValue(book.getPeriodicite());
                checkMagazine.setSelected(true);
                dateEdition.setDisable(true);
                ResumeLivreText.setDisable(true);
                prenomAuteurOeuvre.setDisable(true);
                nomAuteurOeuvre.setDisable(true);
                dateEdition.setValue(null);
                ResumeLivreText.setText(null);
                prenomAuteurOeuvre.setText(null);
                nomAuteurOeuvre.setText(null);
                comboBoxMagazine.setDisable(false);
                NumeroMagazine.setDisable(false);
            }
            nombreExemplaireOeuvre.setText(""+(edb.selectAll(book.getISBN()).size()));
            dateOeuvre.setValue(book.getDateParution().toLocalDate());
        }
    }

    @FXML
    public void annuleUpdateOeuvreButton() throws SQLException {
        AnchorPane parent = (AnchorPane) isbnOeuvre.getParent().getParent();
        parent.getChildren().clear();
    }
    @FXML
    public void UpdateOeuvreButton() throws SQLException {
        Integer auteur = null;
        if(currentBook.getType().equals("Livre")){
            auteur = adb.getIdFromAuteur(nomAuteurOeuvre.getText(), prenomAuteurOeuvre.getText());
            if (auteur == -1){
                adb.insert(nomAuteurOeuvre.getText(), prenomAuteurOeuvre.getText());
                auteur = adb.getIdFromAuteur(nomAuteurOeuvre.getText(), prenomAuteurOeuvre.getText());
            }
        }
        Integer numero = null;
        if(!(NumeroMagazine.getText() == null)) numero = Integer.parseInt(NumeroMagazine.getText());
        java.sql.Date d = null;
        if(!(dateEdition.getValue() == null)) d = java.sql.Date.valueOf(dateEdition.getValue());
        odb.update(isbnOeuvre.getText(), nomOeuvre.getText(), titreOeuvre.getText(),
                java.sql.Date.valueOf(dateOeuvre.getValue()), currentBook.getNbReservation(), d,
                ResumeLivreText.getText(), auteur, numero , comboBoxMagazine.getValue());
        Popup.popUpInfo("Modification", "Vous avez bien modifier l'oeuvre !");
    }

}
