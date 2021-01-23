package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Moodles.TovarZakaz;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class ZakazEditViewController implements Initializable {


    @FXML
    private JFXTextField tovarName;

    @FXML
    private JFXTextField tovarMaker;

    @FXML
    private JFXTextField tovarModel;

    @FXML
    private JFXTextField tovarDDP;

    @FXML
    private JFXTextField tovarEXW;

    @FXML
    private JFXComboBox<String> tovarCostType;

    @FXML
    private JFXTextField tovarPoshlina;

    @FXML
    private JFXTextField tovarAksiz;

    @FXML
    private JFXTextField tovarTransport;

    @FXML
    private JFXTextField tovarKod;

    @FXML
    private JFXComboBox<String> tovarMeasurementType;

    @FXML
    private JFXDatePicker tovarDate;

    @FXML
    private JFXTextField tovarKomment;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;

    private ObservableList<String> costType = FXCollections.observableArrayList("usd");
    private ObservableList<String> measurementType = FXCollections.observableArrayList("шт", "кг", "л", "м");
    private Stage stage;
    private TovarZakaz tovarZakaz;
    private ControllerTable controllerTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        okButton.setDisable(true);

        tovarCostType.getEditor().setEditable(true);
        tovarCostType.getEditor().setFont(new Font(18));
        tovarCostType.getEditor().setEditable(false);

        tovarMeasurementType.getEditor().setEditable(true);
        tovarMeasurementType.getEditor().setFont(new Font(18));
        tovarMeasurementType.getEditor().setEditable(false);


        tovarDate.getEditor().setFont(new Font(18));
        tovarDate.setDisable(true);

        tovarCostType.setItems(costType);
        tovarMeasurementType.setItems(measurementType);


        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,4})?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        TextFormatter<Double> formatter = new TextFormatter<>(filter);

        tovarPoshlina.setTextFormatter(formatter);
        tovarAksiz.setTextFormatter(new TextFormatter<Double>(filter));
        tovarTransport.setTextFormatter(new TextFormatter<Double>(filter));
        tovarEXW.setTextFormatter(new TextFormatter<Double>(filter));
        tovarDDP.setTextFormatter(new TextFormatter<Double>(filter));
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void setControllerTable(ControllerTable controllerTable) {
        this.controllerTable = controllerTable;
    }

    public TovarZakaz getTovarZakaz() {
        return tovarZakaz;
    }

    public void setTovarZakaz(TovarZakaz tovarZakaz) {
        this.tovarZakaz = tovarZakaz;
    }


    @FXML
    private void controlButton(Event event) {
        boolean tuliqmi = (
                !(tovarEXW.getText().trim().isEmpty())
                        && !(tovarPoshlina.getText().trim().isEmpty())
                        && !(tovarAksiz.getText().trim().isEmpty())
                        && !(tovarTransport.getText().trim().isEmpty())
                        && (tovarMeasurementType.getValue() != null)
                        && (tovarCostType.getValue() != null));

        okButton.setDisable(!tuliqmi);
    }

    @FXML
    void okAdd(ActionEvent event) {

        this.tovarZakaz.setTovarDDP(Double.parseDouble(tovarDDP.getText()));
        this.tovarZakaz.setTovarNarxi(Double.parseDouble(tovarEXW.getText()));

        this.tovarZakaz.setTovarTransportNarxi(Double.parseDouble(tovarTransport.getText()) / 100);
        this.tovarZakaz.setTovarAksiz(Double.parseDouble(tovarAksiz.getText()) / 100);
        this.tovarZakaz.setTovarPoshlina(Double.parseDouble(tovarPoshlina.getText()) / 100);

        this.tovarZakaz.setTovarUlchovBirligi(tovarMeasurementType.getValue());
        this.tovarZakaz.setTovarNarxTuri(tovarCostType.getValue());

        controllerTable.refreshHelperTable();

        stage.close();
        controllerTable.summaHisobla();
    }

    @FXML
    private void cancelAdd(ActionEvent event) {
        stage.close();
        controllerTable.summaHisobla();
    }

    void init() {
        try {
            tovarName.setText(
                    tovarZakaz.getTovarNomi());
            tovarMaker.setText(
                    tovarZakaz.getTovarIshlabChiqaruvchi().getName());
            tovarModel.setText(tovarZakaz.getTovarModel());
            tovarEXW.setText(tovarZakaz.getTovarNarxi() + "");
            tovarDDP.setText(tovarZakaz.getTovarDDP() + "");

            tovarPoshlina.setText(tovarZakaz.getTovarPoshlina() * 100 + "");
            tovarAksiz.setText(tovarZakaz.getTovarAksiz() * 100 + "");
            tovarTransport.setText(tovarZakaz.getTovarTransportNarxi() * 100 + "");

            tovarKod.setText(tovarZakaz.getTovarKod() + "");
            tovarKomment.setText(tovarZakaz.getTovarKomment() + "");
            tovarDate.setValue(tovarZakaz.getTovarSana());
            tovarMeasurementType.setValue(tovarZakaz.getTovarUlchovBirligi());
            tovarCostType.setValue(tovarZakaz.getTovarNarxTuri());


        } catch (NullPointerException e) {
            System.out.println("init funksiyada null qiymat");
            System.out.println(e.getMessage());

        }
    }

}


