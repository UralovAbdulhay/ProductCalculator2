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

public class ProjectEndBezController implements Initializable {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;

    @FXML
    private JFXTextField summaDDP;

    @FXML
    private JFXTextField ddpLiXarajatStavka;

    @FXML
    private JFXTextField ddpLiXarajat;

    @FXML
    private JFXTextField darSoliqStavka;

    @FXML
    private JFXTextField darSoliq;

    @FXML
    private JFXTextField kelishSumma;

    @FXML
    private JFXTextField sertifikatlash;

    @FXML
    private JFXTextField deklaratsiya;

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

        ddpLiXarajatStavka.setTextFormatter(new TextFormatter<Double>(filter));

    }


    @FXML
    void ddpLiXarajatlarStavka(KeyEvent event) {
        try {
            project.setPrDDPliXarStavka(Double.parseDouble(ddpLiXarajatStavka.getText()) / 100);
            init2();
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }


    public void setProject(Project project) {
        this.project = project;
        init();
    }

    private void init() {
        ddpLiXarajatStavka.setText(project.getPrDDPliXarStavka() * 100 + "");
        init2();
    }

    private void init2() {
        summaDDP.setText(project.getPrDDPSummaStr());
        ddpLiXarajat.setText(project.getPrDDPliXarajatlarStr());
        darSoliq.setText(project.getPrDarSoliqStr());
        kelishSumma.setText(project.getPrKelishSummaStr());
        sertifikatlash.setText(project.getPrSertifikatlashStr());
        deklaratsiya.setText(project.getPrDekloratsiyaXizmatiStr());

        darSoliqStavka.setText(project.getPrDarSoliqStavkaStr());

        foyda.setText(project.getPrFoydaStr());
        foyizliFoyda.setText(project.getPrFoydaFoyizStr());
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
