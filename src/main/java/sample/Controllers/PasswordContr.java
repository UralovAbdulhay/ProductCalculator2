package sample.Controllers;

import com.jfoenix.controls.JFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Classes.Connections.Connections;
import sample.Classes.Connections.PasswordsConnections;
import sample.Controllers.projectEndView.ProjectEndBezController;
import sample.Controllers.projectEndView.ProjectEndCipController;
import sample.Controllers.projectEndView.ProjectEndSController;
import sample.Moodles.Password;
import sample.Moodles.Project;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PasswordContr implements Initializable {

    @FXML
    private JFXPasswordField passwordContr;

    @FXML
    private Button okBt;

    private int typeCol;
    private Project project;
    private ResourceBundle bundle;
    private Stage mainStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;

        passwordContr.textProperty().addListener(e -> {
            passwordContr.setStyle("-fx-background-color: transparent");
        });

    }


    @FXML
    private void tekshir(KeyEvent keyEvent) {
        okBt.setDisable(passwordContr.getText().isEmpty());
    }


    @FXML
    private void parolTugrimi(ActionEvent event) {
        if (new PasswordsConnections().equalsPasswords(new Password("showProjectResultView", passwordContr.getText(),
                LocalDate.now(), "Show project result view", true))) {
            showWindow();
            cancel();
            System.out.println("To'g'ri");
        } else {
            passwordContr.setStyle("-fx-background-color: #e50d00");
            passwordContr.selectAll();
            System.out.println("Noto'g'ri");
        }
    }

    private void showWindow() {
        if (typeCol == 0) {
            showProjectEndBez();
        } else if (typeCol == 1) {
            showProjectEndS();
        } else if (typeCol == 2) {
            showProjectEndCip();
        }
    }



    private void showProjectEndBez() {

        FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/Views/projectEndBez.fxml"));

        Parent parent = null;
        loader.setResources(bundle);

        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Project View");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(mainStage);
        stage.show();
        ProjectEndBezController controller = loader.getController();
        controller.setProject(project);


        okBt.getScene().getWindow().hide();

    }



    private void showProjectEndS() {


        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Views/projectEndS.fxml"));

        Parent parent = null;
        loader.setResources(bundle);

        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Project View");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(mainStage);
        stage.show();
        ProjectEndSController controller = loader.getController();
        controller.setProject(project);

        okBt.getScene().getWindow().hide();

    }


    private void showProjectEndCip() {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Views/projectEndCip.fxml"));

        Parent parent = null;
        loader.setResources(bundle);

        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Project View");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(mainStage);
        stage.show();
        ProjectEndCipController controller = loader.getController();
        controller.setProject(project);

        okBt.getScene().getWindow().hide();

    }




    @FXML
    private void cancel() {
        this.okBt.getScene().getWindow().hide();
    }

    public int getTypeCol() {
        return typeCol;
    }

    public void setTypeCol(int typeCol) {
        this.typeCol = typeCol;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
