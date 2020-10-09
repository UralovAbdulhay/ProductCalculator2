import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import test_1.foo.TextFields;





public class TextFieldCleareble extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        VBox root = new VBox(10);
        root.setPadding(new Insets(50,50,50,50));

        Label txt = new Label("Clearable TextFields");

        JFXTextField txtField = (JFXTextField) TextFields.createSearchField();

//        JFXPasswordField passField = (JFXPasswordField) TextFields.createSearchField();

        JFXTextField jfx = new JFXTextField();



        root.getChildren().addAll(txt,txtField);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String args[]){

        launch(args);

    }
}
