package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Yhugo on 18/10/2016.
 */
public class MainViewController implements Initializable {

    @FXML
    public MenuItem about,
            addUsager, deleteUsager, updateUsager,
            addOeuvre, deleteOeuvre, updateOeuvre,
            addReservation, deleteReservation,
            addEmprunt, deleteEmprunt;

    @FXML
    public AnchorPane panelView;

    public void initialize(URL location, ResourceBundle resources) {
        EventHandler actionMenu = afficheMenu();
        about.setOnAction(actionMenu);
        addUsager.setOnAction(actionMenu);
        deleteUsager.setOnAction(actionMenu);
        updateUsager.setOnAction(actionMenu);
        addOeuvre.setOnAction(actionMenu);
        deleteOeuvre.setOnAction(actionMenu);
        updateOeuvre.setOnAction(actionMenu);
        addReservation.setOnAction(actionMenu);
        deleteReservation.setOnAction(actionMenu);
        addEmprunt.setOnAction(actionMenu);
        deleteEmprunt.setOnAction(actionMenu);
    }

    private EventHandler<ActionEvent> afficheMenu() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String id = mItem.getId();
                panelView.getChildren().clear();
                Parent root = null;
                FXMLLoader fxmlLoader = null;
                if ("about".equalsIgnoreCase(id)) {
                     fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("About.fxml"));
                }
                if ("addUsager".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AddUsager.fxml"));
                }
                if ("updateUsager".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UpdateUsager.fxml"));
                }
                if ("deleteUsager".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("DeleteUsager.fxml"));
                }
                if ("addOeuvre".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AddOeuvre.fxml"));
                }
                if ("updateOeuvre".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("UpdateOeuvre.fxml"));
                }
                if ("deleteOeuvre".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("DeleteOeuvre.fxml"));
                }
                if ("addReservation".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AddReservation.fxml"));
                }
                if ("deleteReservation".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("DeleteReservation.fxml"));
                }
                if ("addEmprunt".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("AddEmprunt.fxml"));
                }
                if ("deleteEmprunt".equalsIgnoreCase(id)) {
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("DeleteEmprunt.fxml"));
                }
                if(fxmlLoader==null){
                    fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ErrorNotFound.fxml"));
                }
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                panelView.getChildren().add(root);
            }
        };
    }
}
