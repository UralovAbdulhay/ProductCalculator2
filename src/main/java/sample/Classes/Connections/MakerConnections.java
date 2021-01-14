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


public class MakerConnections extends Connections {



    public ObservableList<Maker> getMakerFromSql() {
        ObservableList<Maker> list = FXCollections.observableArrayList();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM maker;"
            );

            while (resultSet.next()) {
                list.add(new Maker(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        LocalDate.parse(resultSet.getString(4), dateFormatter)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Maker getMakerFromSql(int id) {
        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM maker WHERE id = " + id + ";"
            );
            return new Maker(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("country"),
                    parseToLocalDate(resultSet.getString(4))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Maker getMakerFromSql(String name) {
        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM maker WHERE name = " + name + ";"
            );
            return new Maker(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    LocalDate.parse(resultSet.getString(4), dateFormatter)
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Maker insertToMaker(Maker maker) {
        long temp = System.currentTimeMillis();
        Maker maker1 = null;

        String sql = " INSERT OR IGNORE INTO maker(name, temp)" +
                " VALUES('" + maker.getName() + "', " +
                temp + " );";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToMaker statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql1 = "SELECT * FROM maker WHERE temp = " + temp + ";";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql1);
            if (resultSet.next()) {
                maker1 = new Maker(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("country"),
                        parseToLocalDate(resultSet.getString("sana"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE maker SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "'" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Maker setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maker1;
    }


    public void updateMaker(Maker maker) {
        String sql = "UPDATE maker SET " +
                "name = '" + maker.getName() + "', " +
                "country = '" + maker.getCountry() + "' " +
                "WHERE id = " + maker.getId() +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateMaker statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteMaker(Maker maker) {
        String sql = "DELETE FROM maker WHERE id = " + maker.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteMaker statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
