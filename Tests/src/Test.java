import java.util.Calendar;

public class Test {
    public static void main(String[] args) {
        double a;
        String s = "3sd";

        try {
            a = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            a = 0;
        }

        System.out.println(" a = " + a);

    }
}

