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


public class ClientConnections extends Connections {


    public ObservableList<Client> getClientFromSql() {
        ObservableList<Client> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM client;"
            );

            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        parseToLocalDate(resultSet.getString("date"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public Client getClientFromSql(int id) {

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM client WHERE id = " + id + ";"
            );
            Client client = null;
            if (resultSet.next()) {
                client = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        parseToLocalDate(resultSet.getString("date"))
                );
            }
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Client insertToClient(Client client) {
        long temp = System.currentTimeMillis();


        String sql = "INSERT OR IGNORE INTO client (name, temp) VALUES('"
                + client.getName() + "', " +
                temp + " );";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToClient statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql2 = "SELECT * FROM client WHERE temp = " + temp + ";";

        Client client1 = null;

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql2);
            if (resultSet.next()) {
                client1 = new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        parseToLocalDate(resultSet.getString("date"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE client SET " +
                "temp = " + null + " " +
                "WHERE temp = " + temp + " " +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println(" Client setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client1;
    }


    public void updateClient(Client client) {
        String sql = " UPDATE client SET " +
                "name = '" + client.getName() + "' " +
                "WHERE id = " + client.getId() +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateClient statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteClient(Client client) {
        String sql = "DELETE FROM client WHERE id = " + client.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteClient statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
