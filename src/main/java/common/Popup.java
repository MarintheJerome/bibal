package common;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Created by Yhugo on 26/10/2016.
 */
public class Popup {

    public static void popUpInfo(Stage stage, String titre, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
