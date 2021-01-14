package sample.Controllers.projectEndView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import sample.Classes.Connections.Connections;
import sample.Classes.Connections.ProjectConnections;
import sample.Moodles.Project;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ProjectEndCipController implements Initializable {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXTextField cipSumma;

    @FXML
    private JFXTextField kichikXarajatlarCipStavka;

    @FXML
    private JFXTextField kichikXarajatlarCip;

    @FXML
    private JFXTextField transLiSumma;

    @FXML
    private JFXTextField cipLixarajatlarStavka;

    @FXML
    private JFXTextField cipLixarajatlar;

    @FXML
    private JFXTextField foyda;

    @FXML
    private JFXTextField foyizliFoyda;

    private ResourceBundle bundle;
    private Project project;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;


        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,4})?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        cipLixarajatlarStavka.setTextFormatter(new TextFormatter<Double>(filter));
        kichikXarajatlarCipStavka.setTextFormatter(new TextFormatter<Double>(filter));

    }

    @FXML
    void cipLiXarajatlarAction(KeyEvent event) {
        try {
            project.setPrCipliXarStavka(Double.parseDouble(cipLixarajatlarStavka.getText()) / 100);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        init2();
    }

    @FXML
    void kichikXarajatlarAction(KeyEvent event) {

        try {
            project.setPrKichikCipXarStavka(Double.parseDouble(kichikXarajatlarCipStavka.getText()) / 100);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        init2();
    }


    public void setProject(Project project) {
        this.project = project;
        init();
    }

    private void init() {

        kichikXarajatlarCipStavka.setText(project.getPrKichikCipXarStavka() * 100 + "");
        cipLixarajatlarStavka.setText(project.getPrCipliXarStavka() * 100 + "");
        init2();
    }

    private void init2() {

        cipSumma.setText(project.getPrCipSummaUSDStr());
        kichikXarajatlarCip.setText(project.getPrKichikCipXarajatlarStr());
        transLiSumma.setText(project.getPrTransLiSummaCipUchunUSDStr());
        cipLixarajatlar.setText(project.getPrCipliXarajatlarStr());

        foyda.setText(project.getPrCipFoydaStr());
        foyizliFoyda.setText(project.getPrCipFoydaFoyizStr());

    }

    @FXML
    private void cancelAdd(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    private void okAdd(ActionEvent event) {
        new ProjectConnections().updateProject(project);
        cancelAdd(event);
    }
}