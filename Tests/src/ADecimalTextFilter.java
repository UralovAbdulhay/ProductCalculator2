import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class ADecimalTextFilter extends Application{


    @Override
    public void start(Stage primaryStage) {

        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,3})?");

        UnaryOperator<Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        TextFormatter<Double> formatter = new TextFormatter<>(filter);

        TextField textField = new TextField();
        textField.setTextFormatter(formatter);
        Button button = new Button();
        button.setOnAction(event -> {
            System.out.println(Double.parseDouble(textField.getText()));
        });
        button.setLayoutX(20);
        button.setLayoutY(30);
        AnchorPane root = new AnchorPane(textField, button);

        root.setPadding(new Insets(24));

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
