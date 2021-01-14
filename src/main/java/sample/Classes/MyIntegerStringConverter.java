package sample.Classes;

import javafx.util.converter.IntegerStringConverter;

public class MyIntegerStringConverter extends IntegerStringConverter {

    @Override
    public Integer fromString(final String value) {

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {

            return 1;
        }

    }


}
