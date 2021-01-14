package sample.Moodles;

import java.time.LocalDate;

public class UserPasswords {

 private String name;
 private String password;
 private LocalDate date;

    public UserPasswords(String name, String password, LocalDate date) {
        this.name = name;
        this.password = password;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public String toString() {
        return "UserPasswords{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", date=" + date +
                '}';
    }
}
