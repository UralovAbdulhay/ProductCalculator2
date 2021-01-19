package sample.settingsController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import sample.Classes.Connections.Connections;
import sample.Classes.Connections.PasswordsConnections;
import sample.Moodles.Password;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PasswordSettingsController implements Initializable {


    @FXML
    private Label headerLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXPasswordField currentPass;

    @FXML
    private JFXPasswordField reNewPass;

    @FXML
    private JFXPasswordField newPass;

    @FXML
    private JFXListView<Password> passList;

    private ResourceBundle bundle;


    ArrayList<JFXPasswordField> fields = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        PasswordsConnections connections = new PasswordsConnections();

        fields.add(currentPass);
        fields.add(newPass);
        fields.add(reNewPass);

        fields.forEach(e -> {
            e.setOnKeyReleased(q -> {
                cancelButton.setDisable(fields.stream().allMatch(w -> w.getText().isEmpty()));
                okButton.setDisable(!(fields.stream().noneMatch(w -> w.getText().isEmpty())
                        && (newPass.getText().equals(reNewPass.getText()))) );
            });
        });

        passList.getItems().clear();
        passList.getItems().addAll(connections.getPasswordList());


        passList.setOnKeyReleased(event -> {
            nameLabel.setText(passList.getSelectionModel().getSelectedItem() + "");
        });

        passList.getSelectionModel().selectFirst();
        nameLabel.setText(passList.getSelectionModel().getSelectedItem() + "");

    }


    @FXML
    private void selectPassword(MouseEvent mouseEvent) {
        nameLabel.setText(passList.getSelectionModel().getSelectedItem() + "");
    }


    @FXML
    private void okAdd(ActionEvent event) {
        Password oldPassword = passList.getSelectionModel().getSelectedItem();
        String newValue = newPass.getText().trim();
        String currentValue = currentPass.getText().trim();
        if (oldPassword.getPassword()
                .equals(currentValue)) {
            PasswordsConnections conn = new PasswordsConnections();

            if (conn.equalsPasswords(oldPassword)) {
                Password newPassword = new Password(
                        oldPassword.getLogin(), newValue, oldPassword.getDate(),
                        oldPassword.getLabel(), oldPassword.isType()
                        );

                // password ni almashtirganligi haqidagi habar
                if(conn.updatePassword(oldPassword, newPassword)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(bundle.getString("changedAlertMass"));
                    alert.showAndWait();
                    cancelAdd(event);
                    passList.getItems().clear();
                    passList.getItems().addAll(conn.getPasswordList());
                    passList.getSelectionModel().selectFirst();

                    passList.refresh();
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(bundle.getString("alertErrorTitle"));
                    alert.setContentText(bundle.getString("passwordAlertMass"));
                    alert.showAndWait();
                }
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(bundle.getString("alertErrorTitle"));
            alert.setContentText(bundle.getString("alertErrorTitle"));
            alert.setHeaderText(bundle.getString("passwordAlertMass"));
            alert.showAndWait();
            cancelAdd(event);
        }

    }

    @FXML
    private void cancelAdd(ActionEvent event) {
        fields.forEach(TextInputControl::clear);
        cancelButton.setDisable(fields.stream().allMatch(w -> w.getText().isEmpty()));
        okButton.setDisable(fields.stream().anyMatch(w -> w.getText().isEmpty()));
    }

}
