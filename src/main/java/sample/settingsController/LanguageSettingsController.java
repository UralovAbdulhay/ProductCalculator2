package sample.settingsController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.Classes.LocaleConfig;
import sample.Classes.Main;
import sample.Moodles.Languages;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageSettingsController implements Initializable {

    @FXML
    private JFXComboBox<Languages> lanBox;

    @FXML
    private JFXButton lanSaveBt;

    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        Languages languages = new Languages();
        lanBox.getItems().clear();
        lanBox.setItems(languages.getLanguages());


        lanBox.getItems().forEach(e->{
            if (e.getLocale().equals(bundle.getLocale())) {
                lanBox.setValue(e);
                return;
            }
        });
    }


    @FXML
    private void selectLanguage(Event event) {
        if (lanBox.getValue()!=null) {
            lanSaveBt.setDisable(lanBox.getValue().getLocale().equals(bundle.getLocale()));
        }
        else {
            lanSaveBt.setDisable(true);
        }
    }

    @FXML
    private void lanSave() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        //o'zbek tilini tanlash
        alert.setContentText(bundle.getString("alertConText"));

        // ishonchingiz komilmi
        alert.setHeaderText(bundle.getString("alertHeaderText"));

        //tilni o'zgartirish
        alert.setTitle(bundle.getString("languageAlertTitle"));


        if (alert.showAndWait().get() == ButtonType.OK) {
            LocaleConfig config = new LocaleConfig();
            config.setLocale(lanBox.getValue().getLocale());
            Main main = new Main();
            Main.initResourceBundle();
            main.start((Stage) lanBox.getScene().getWindow());
        }
    }

}
