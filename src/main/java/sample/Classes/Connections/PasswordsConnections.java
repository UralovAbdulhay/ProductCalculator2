package sample.Classes.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Moodles.*;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class PasswordsConnections extends Connections {
    
    public boolean equalsPasswords(Password password) {
        String sql = "SELECT * FROM passwords WHERE login = '" + password.getLogin() + "' ;";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                return (resultSet.getString("login").equals(password.getLogin())
                        && resultSet.getString("password").equals(password.getPassword())
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ObservableList<Password> getPasswordList() {

        String sql = "SELECT * FROM passwords ORDER BY type desc;";
        ObservableList<Password> passwords = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            while (resultSet.next()) {
                passwords.add(new Password(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        parseToLocalDate(resultSet.getString("date")),
                        resultSet.getString("label"),
                        resultSet.getBoolean("type")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }


    public boolean updatePassword(Password oldPassword, Password newPassword) {

        String sql = "UPDATE passwords SET " +
                "login = '" + newPassword.getLogin() + "', " +
                "password = '" + newPassword.getPassword() + "' " +
                "WHERE login = '" + oldPassword.getLogin() + "' AND " +
                "password = '" + oldPassword.getPassword() + "' AND " +
                "label = '" + oldPassword.getLabel() + "' AND " +
                "type = " + (oldPassword.isType() ? 1 : 0) + " " +
                ";";
        try (Connection connection = connect()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            int i = statement.executeUpdate();
            return (i != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
