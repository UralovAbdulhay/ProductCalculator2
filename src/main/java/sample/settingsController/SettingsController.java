package sample.settingsController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {


    @FXML
    private VBox mainVbox;



private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        showWindow("/Views/languageSettings.fxml");
    }



    @FXML
    private void showPasswordsSettings(ActionEvent event) {
        showWindow("/Views/passwordSettings.fxml");
    }

    @FXML
    private void showLanguageSettings(ActionEvent event) {
        showWindow("/Views/languageSettings.fxml");
    }


    public void showWindow(String Window) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Window));
        loader.setResources(bundle);

        VBox vBox = null;
        try {
            vBox = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

        mainVbox.getChildren().clear();
        mainVbox.getChildren().add(vBox);
        VBox.setVgrow(vBox, Priority.ALWAYS);


    }
}
