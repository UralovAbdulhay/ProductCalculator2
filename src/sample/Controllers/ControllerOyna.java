package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOyna implements Initializable {

    @FXML
    private JFXButton b_asosiy;

    @FXML
    private Button zapros;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox centerVbox;

    private static int ochiqOyna;

    private Stage mainStage;

    public ControllerOyna() {

    }

    public BorderPane getBorderPane() {
        return borderPane;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            showWindow("/sample/Views/asosiy.fxml");
            ochiqOyna = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void showAsosiy(ActionEvent event) {
        if (ochiqOyna != 1) {
            try {
                showWindow("/sample/Views/asosiy.fxml");
                ochiqOyna = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void showZapros(ActionEvent event) {
        if (ochiqOyna != 2) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Views/tableView.fxml"));
            VBox vBox = null;
            try {
                vBox = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            borderPane.setCenter(vBox);

            ControllerTable controller = loader.getController();
            controller.setMainStage(mainStage);

            ochiqOyna = 2;
        }

    }

    @FXML
    void showPrice(ActionEvent event) {
        if (ochiqOyna != 3) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Views/editTable.fxml"));
            VBox vBox = null;
            try {
                vBox = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            borderPane.setCenter(vBox);

            EditTovar controller = loader.getController();
            controller.setMainStage(mainStage);

            ochiqOyna = 3;
        }

    }


    public void showWindow(String Window) {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Window));

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }


        Parent root = loader.getRoot();

        VBox box = new VBox();

        VBox.setVgrow(root, Priority.ALWAYS);

        box.setFillWidth(true);

        box.getChildren().add(root);
        box.setAlignment(Pos.CENTER);


        box.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        borderPane.setCenter(box);


    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
