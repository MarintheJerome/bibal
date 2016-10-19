package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * Created by Yhugo on 18/10/2016.
 */
public class MainViewLauncher extends Application {

    public static Scene scene;
    public static Stage stagePrincipal;

    public void start(Stage stage) throws Exception {
        FXMLLoader preloader = new FXMLLoader(getClass().getClassLoader().getResource("MainView.fxml"));
        Parent root = preloader.load();
        scene = new Scene(root);
        stage.setTitle("Bibal");
        stage.setScene(scene);
        stagePrincipal = stage;
        stagePrincipal.setResizable(false);
        stage.show();
    }
}
