package sample.Controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Classes.Connections.Connections;
import sample.Classes.Connections.MakerConnections;
import sample.Moodles.Maker;
import sample.Moodles.PriseList;
import sample.Moodles.Tovar;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddProductController implements Initializable {

    @FXML
    private JFXTextField tovarName;

    @FXML
    private JFXComboBox<String> tovarMaker;

    @FXML
    private JFXTextField tovarModel;

    @FXML
    private JFXTextField tovarEXW;

    @FXML
    private JFXTextField tovarDDP;

    @FXML
    private JFXTextField tovarPoshlina;

    @FXML
    private JFXTextField tovarAksiz;

    @FXML
    private JFXTextField tovarTransport;

    @FXML
    private JFXTextField tovarKod;

    @FXML
    private JFXDatePicker tovarDate;

    @FXML
    private JFXTextField tovarKomment;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXComboBox<String> tovarCostType;

    @FXML
    private JFXComboBox<String> tovarMeasurementType;

    private Stage stage;
    private PriseList priseList;
    private boolean uzgrtirishmi;
    private EditTovar editTovar;

    private ObservableList<String> costType = FXCollections.observableArrayList("usd");
    private ObservableList<String> measurementType = FXCollections.observableArrayList("шт", "кг", "л", "м");
    private ObservableList<Maker> makers = FXCollections.observableArrayList(new MakerConnections().getMakerFromSql());
    private ObservableList<String> makersName = FXCollections.observableArrayList(getMakerName());

    private ResourceBundle bundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        okButton.setDisable(true);


//        tovarDate.getEditor().setEditable(true);

        tovarDate.getEditor().setFont(new Font(18));

        tovarMaker.getEditor().setFont(new Font(18));


        tovarCostType.setItems(costType);
        tovarMeasurementType.setItems(measurementType);
        tovarMaker.setItems(makersName);


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

        tovarDate.setValue(LocalDate.now());

    }

    private ObservableList<String> getMakerName() {
        ObservableList<String> makersName = FXCollections.observableArrayList();
        makers.forEach(e -> makersName.add(e.getName()));
        return makersName;
    }

    @FXML
    void cancelAdd(ActionEvent event) {
        stage.close();
    }

    @FXML
    void okAdd(ActionEvent event) {
//
        if (uzgrtirishmi) {
            priseList.setTovarKod(tovarKod.getText().trim());
            priseList.setTovarNomi(tovarName.getText().trim());
            priseList.setTovarModel(tovarModel.getText().trim());

            priseList.setTovarIshlabChiqaruvchi(makers.stream().
                    filter(e -> e.getName().trim().equals(tovarMaker.getValue().trim()))
                    .findAny().orElse(new MakerConnections().insertToMaker(
                            new Maker(-1, tovarMaker.getValue().trim(), "-", LocalDate.now())
                    )));

            priseList.setTovarNarxi(Double.parseDouble(tovarEXW.getText().trim()));
            priseList.setTovarDDP(Double.parseDouble(tovarDDP.getText().trim()));
            priseList.setTovarNarxTuri(tovarCostType.getValue() + "".trim());

            priseList.setTovarTransportNarxi(Double.parseDouble(tovarTransport.getText().trim()) / 100);
            priseList.setTovarAksiz(Double.parseDouble(tovarAksiz.getText().trim()) / 100);
            priseList.setTovarPoshlina(Double.parseDouble(tovarPoshlina.getText().trim()) / 100);

            priseList.setToarSana(tovarDate.getValue());
            priseList.setTovarUlchovBirligi(tovarMeasurementType.getValue() + "".trim());
            priseList.setTovarKomment(tovarKomment.getText().trim());


            PriseList.setPriseList(priseList);
        } else {

            Maker maker = makers.stream().
                    filter(e -> e.getName().trim().equals(tovarMaker.getValue().trim()))
                    .findAny().orElse(new MakerConnections().insertToMaker(
                            new Maker(-1, tovarMaker.getValue().trim(), "-", LocalDate.now())
                    ));

            priseList = new PriseList(
                    new Tovar(
                            tovarKod.getText().trim(),
                            tovarName.getText().trim(),
                            tovarModel.getText().trim(),
                            maker,
                            Double.parseDouble(tovarEXW.getText().trim()),
                            Double.parseDouble(tovarDDP.getText().trim()),
                            tovarCostType.getValue() + "".trim(),
                            Double.parseDouble(tovarTransport.getText().trim()),
                            Double.parseDouble(tovarAksiz.getText().trim()),
                            Double.parseDouble(tovarPoshlina.getText().trim()),
                            tovarDate.getValue(),
                            tovarMeasurementType.getValue() + "".trim(),
                            tovarKomment.getText().trim()
                    )
            );

            PriseList.addPriseList(priseList);
        }
        editTovar.setMouseActionToChekBox();
        stage.close();
    }


    void editPrise() {
        try {
            tovarName.setText(
                    priseList.getTovarNomi());
            tovarMaker.setValue(
                    priseList.getTovarIshlabChiqaruvchi().getName());
            tovarModel.setText(priseList.getTovarModel());
            tovarEXW.setText(priseList.getTovarNarxi() + "");
            tovarDDP.setText(priseList.getTovarDDP() + "");

            tovarPoshlina.setText(priseList.getTovarPoshlina() * 100 + "");
            tovarAksiz.setText(priseList.getTovarAksiz() * 100 + "");
            tovarTransport.setText(priseList.getTovarTransportNarxi() * 100 + "");

            tovarKod.setText(priseList.getTovarKod() + "");
            tovarKomment.setText(priseList.getTovarKomment() + "");
            tovarDate.setValue(priseList.getTovarSana());
            tovarMeasurementType.setValue(priseList.getTovarUlchovBirligi());
            tovarCostType.setValue(priseList.getTovarNarxTuri());

            uzgrtirishmi = true;
        } catch (NullPointerException e) {
            uzgrtirishmi = false;
            System.out.println("NULL");
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    void setPriseList(PriseList priseList) {
        this.priseList = priseList;
    }

    @FXML
    private void controlButton() {

        boolean tuliqmi = !(
                tovarName.getText().trim().isEmpty())
                && (tovarMaker.getValue() != null)
                && !(tovarModel.getText().trim().isEmpty())
                && !(tovarEXW.getText().trim().isEmpty())
                && (tovarMeasurementType.getValue() != null)
                && !(tovarPoshlina.getText().trim().isEmpty())
                && !(tovarAksiz.getText().trim().isEmpty())
                && !(tovarTransport.getText().trim().isEmpty())
                && !(tovarKod.getText().trim().isEmpty())
                && (tovarDate.getValue() != null)
                && (tovarCostType.getValue() != null);

        okButton.setDisable(!tuliqmi);

    }

    public void setEditTovar(EditTovar editTovar) {
        this.editTovar = editTovar;
    }
}
