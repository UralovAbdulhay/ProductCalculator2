package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import sample.Classes.Connections;
import sample.Moodles.StavkaShablon;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class StaffEditController implements Initializable {

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXTextField qiymatTF;

    private Stage stage;
    private StavkaShablon shablon;
    private EditTovar editTovar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,4})?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        qiymatTF.setTextFormatter(new TextFormatter<Double>(filter));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setShablon(StavkaShablon shablon) {
        this.shablon = shablon;
    }

    public void setEditTovar(EditTovar editTovar) {
        this.editTovar = editTovar;
    }

    public void okEdit(ActionEvent event) {

        try{
            shablon.setQiymat(Double.parseDouble(qiymatTF.getText()));
            new Connections().updateStavka(shablon);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        stage.close();
        editTovar.refreshStaffTable();
    }

    public void initEdit() {
        qiymatTF.setText(shablon.getQiymat()+"");
        qiymatTF.setPromptText(shablon.getNomi()+" \'"+shablon.getKomment()+"\'");
    }

    public void cancelEdit(ActionEvent event) {
        stage.close();
    }


}
