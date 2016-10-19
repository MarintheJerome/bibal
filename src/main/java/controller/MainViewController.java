package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
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
    public MenuItem about;

    @FXML
    public AnchorPane panelView;

    public void initialize(URL location, ResourceBundle resources) {
        EventHandler actionMenu = afficheMenu();
        about.setOnAction(actionMenu);
    }

    private EventHandler<ActionEvent> afficheMenu() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                MenuItem mItem = (MenuItem) event.getSource();
                String id = mItem.getId();
                if ("about".equalsIgnoreCase(id)) {
                    panelView.getChildren().clear();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("About.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    panelView.getChildren().add(root);
                }
            }
        };
    }

}
