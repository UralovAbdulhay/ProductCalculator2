package sample.Moodles;

import java.time.LocalDate;
import java.util.Locale;

public class Password {

    private String login;
    private String password;
    private LocalDate date;
    private String label;

    // true - settings, false - user passwords;
    private boolean type;

    public Password(String login, String password, LocalDate date, String label, boolean type) {
        this.login = login;
        this.password = password;
        this.date = date;
        this.label = label;
        this.type = type;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String name) {
        this.login = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return label;
    }
}
