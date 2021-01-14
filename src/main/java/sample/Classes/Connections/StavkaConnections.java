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


public class StavkaConnections extends Connections {


    public ObservableList<StavkaShablon> getStavkaFromSql() {
        ObservableList<StavkaShablon> list = FXCollections.observableArrayList();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM stavka;"
            );
            while (resultSet.next()) {
                list.add(new StavkaShablon(
                        resultSet.getString("name"),
                        resultSet.getDouble("qiymat"),
                        resultSet.getString("komment"),
                        resultSet.getString("kod")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public StavkaShablon getStavkaFromSql(String code) {

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM stavka WHERE kod = '" + code + "';"
            );
            return new StavkaShablon(
                    resultSet.getString("name"),
                    resultSet.getDouble("qiymat"),
                    resultSet.getString("komment"),
                    resultSet.getString("kod")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void insertToStavka(StavkaShablon shablon) {

        String sql = "INSERT OR IGNORE INTO stavka (name, qiymat, komment, kod)" +
                " VALUES('" +
                shablon.getNomi() + "', " +
                shablon.getQiymat() + ", " +
                "'" + shablon.getKomment() + "', " +
                "'" + shablon.getKod() + "' " +
                ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean isFullStavka() {

        String sql = "select count(kod) as count from stavka;";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            return (9 == resultSet.getInt("count"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void updateStavka(StavkaShablon shablon) {
        String sql = "UPDATE stavka SET " +
                "name = '" + shablon.getNomi() + "', " +
                "qiymat = " + shablon.getQiymat() + ", " +
                "komment = '" + shablon.getKomment() + "' " +
                "WHERE kod = '" + shablon.getKod() + "' " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateStavka statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
