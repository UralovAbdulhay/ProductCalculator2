package sample.Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Moodles.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Connections {

    private Connection con = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String dataBase = "baza/colcul.db";


    public ObservableList<PriseList> getTovarFromSql() {
        ObservableList<PriseList> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("tovar");
        try {
            while (resultSet.next()) {
                list.add(
                        new PriseList(
                                new Tovar(
                                        resultSet.getInt("id"),
                                        resultSet.getString("kod"),
                                        resultSet.getString("name"),
                                        resultSet.getString("model"),
                                        resultSet.getString("maker"),
                                        resultSet.getDouble("cost"),
                                        resultSet.getDouble("ddp_cost"),
                                        resultSet.getString("cost_type"),
                                        resultSet.getDouble("trans_cost"),
                                        resultSet.getDouble("aksiz_cost"),
                                        resultSet.getDouble("poshlina_cost"),
                                        LocalDate.parse(
                                                resultSet.getString("date")
                                                , dateTimeFormatter),
                                        resultSet.getString("ulchov_type"),
                                        resultSet.getString("komment")
                                )
                        )
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Maker> getMakerFromSql() {
        ObservableList<Maker> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("maker");
        try {
            while (resultSet.next()) {
                list.add(new Maker(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        LocalDate.parse(resultSet.getString(4), dateFormatter)
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Xodimlar> getXodimlarFromSql() {
        ObservableList<Xodimlar> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("xodimlar");
        try {
            while (resultSet.next()) {
                list.add(new Xodimlar(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("sure_name"),
                        resultSet.getString("last_name"),
                        LocalDate.parse(resultSet.getString("birth_day"), dateFormatter),
                        LocalDate.parse(resultSet.getString("come_day"), dateFormatter),
                        resultSet.getString("lavozim")
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<StavkaShablon> getStavkaFromSql() {
        ObservableList<StavkaShablon> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("stavka");
        try {
            while (resultSet.next()) {
                list.add(new StavkaShablon(
                        resultSet.getString("name"),
                        resultSet.getDouble("qiymat"),
                        resultSet.getString("komment"),
                        resultSet.getString("kod")
                        ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Project> getProjectFromSql() {
        ObservableList<Project> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("project");

        try {
            while (resultSet.next()) {
                list.add(new Project(
                        resultSet.getInt("id"),
                        resultSet.getString("nomer_zakaz"),
                        resultSet.getDouble("qiymat"),
                        resultSet.getString("komment"),
                        resultSet.getString("kod")
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getZakazListFromSQL() {

    }

    public ObservableList<Client> getClientFromSql() {

        ObservableList<Client> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("client");
        try {
            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        LocalDateTime.parse(resultSet.getString(3), dateTimeFormatter)
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void getCourseFromSql() {

    }


    public ResultSet selectAllFromSql(String tableName) {

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
            statement = con.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM " + tableName + "; "
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

}
