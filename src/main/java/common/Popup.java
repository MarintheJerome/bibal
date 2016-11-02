package common;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Created by Yhugo on 26/10/2016.
 */
public class Popup {

    public static void popUpInfo(String titre, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void popUpError(String titre, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
