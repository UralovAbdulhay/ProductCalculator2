package test_1.foo;


import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

public class CustomTextField extends JFXTextField {

    public CustomTextField() {
        getStyleClass().add("custom-text-field");
    }



    // --- left
    private ObjectProperty<Node> left = new SimpleObjectProperty<Node>(this, "left");


    public final ObjectProperty<Node> leftProperty() {
        return left;
    }

    public final Node getLeft() {
        return left.get();
    }

    public final void setLeft(Node value) {
        left.set(value);
    }


    // --- right
    private ObjectProperty<Node> right = new SimpleObjectProperty<Node>(this, "right");


    public final ObjectProperty<Node> rightProperty() {
        return right;
    }

    public final Node getRight() {
        return right.get();
    }

    public final void setRight(Node value) {
        right.set(value);
    }


/*

    @Override public Skin<?> createDefaultSkin() {
        return new CustomTextFieldSkin( this);
    }
*/

    @Override
    public String getUserAgentStylesheet() {
        return CustomTextField.class.getResource("customtextfield.css").toExternalForm();
    }
}
