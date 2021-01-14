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


public class CourseConnections extends Connections{
    
    public ObservableList<Valyuta> getCourseFromSql() {
        ObservableList<Valyuta> list = FXCollections.observableArrayList();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM course;"
            );
            while (resultSet.next()) {
                list.add(new Valyuta(
                        resultSet.getString("title"),
                        resultSet.getString("code"),
                        resultSet.getString("cb_price"),
                        resultSet.getString("nbu_buy_price"),
                        resultSet.getString("nbu_cell_price"),
                        resultSet.getString("refresh_date")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Valyuta getCourseFromSql(String code) {

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM course WHERE  code= '" + code + "';"
            );
            return new Valyuta(
                    resultSet.getString("title"),
                    resultSet.getString("code"),
                    resultSet.getString("cb_price"),
                    resultSet.getString("nbu_buy_price"),
                    resultSet.getString("nbu_cell_price"),
                    resultSet.getString("refresh_date")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void insertToCourse(Valyuta valyuta) {

        String sql =
                "INSERT OR IGNORE INTO course ( " +
                        " title, code, cb_price, nbu_buy_price, nbu_cell_price, refresh_date) " +
                        "VALUES(" +
                        "'" + valyuta.getTitleForDB() + "', " +
                        "'" + valyuta.getCode() + "', " +
                        "" + valyuta.getCb_priceD() + ", " +
                        "" + valyuta.getNbu_buy_priceD() + ", " +
                        "" + valyuta.getNbu_cell_priceD() + ", " +
                        "'" + valyuta.getDate() + "' " +
                        "); ";
        String sql1 =
                "UPDATE course SET " +
                        "cb_price = " + valyuta.getCb_priceD() + ", " +
                        "nbu_buy_price = " + valyuta.getNbu_buy_priceD() + ", " +
                        "nbu_cell_price = " + valyuta.getNbu_cell_priceD() + ", " +
                        "refresh_date = '" + valyuta.getDate() + "' " +
                        "WHERE code = '" + valyuta.getCode() + "'; ";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);

            if (0 == statement.executeUpdate()) {
                PreparedStatement statement1 = connection.prepareStatement(sql1);
                System.out.println("updateToCourse = " + statement1.executeUpdate());
            } else {
                System.out.println("insertToCourse = " + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCourse(Valyuta valyuta) {
        String sql = "DELETE FROM course WHERE code = '" + valyuta.getCode() + "';";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteValyuta statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
