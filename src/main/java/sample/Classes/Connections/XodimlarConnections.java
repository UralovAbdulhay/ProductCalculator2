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


public class XodimlarConnections extends Connections {

    public ObservableList<Xodimlar> getXodimlarFromSql() {
        ObservableList<Xodimlar> list = FXCollections.observableArrayList();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM xodimlar;"
            );

            while (resultSet.next()) {
                list.add(new Xodimlar(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("sure_name"),
                        resultSet.getString("last_name"),
                        parseToLocalDate(resultSet.getString("birth_day")),
                        parseToLocalDate(resultSet.getString("come_date")),
                        resultSet.getString("lavozim")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Xodimlar getXodimlarFromSql(int id) {

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM xodimlar WHERE id = " + id + ";"
            );
            Xodimlar xodimlar = new Xodimlar(
                    resultSet.getInt("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("sure_name"),
                    resultSet.getString("last_name"),
                    parseToLocalDate(resultSet.getString("birth_day")),
                    parseToLocalDate(resultSet.getString("come_date")),
                    resultSet.getString("lavozim")
            );
            return xodimlar;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Xodimlar insertToXodimlar(Xodimlar xodimlar) {
        long temp = System.currentTimeMillis();
        Xodimlar xodimlar1 = null;

        String sql = "INSERT OR IGNORE INTO xodimlar (" +
                "first_name, sure_name, last_name, birth_day, lavozim, temp ) " +
                "VALUES('" +
                xodimlar.getIsm() + "', " +
                "'" + xodimlar.getFamiliya() + "', " +
                "'" + xodimlar.getOchestva() + "', " +
                "'" + xodimlar.getTugilganVaqt() + "', " +
                "'" + xodimlar.getLavozim() + "', " +
                "'" + temp + "' " +
                ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql1 = "SELECT * FROM xodimlar WHERE temp = '" + temp + "';";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql1);
            if (resultSet.next()) {
                xodimlar1 = new Xodimlar(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("sure_name"),
                        resultSet.getString("last_name"),
                        parseToLocalDate(resultSet.getString("birth_day")),
                        parseToLocalDate(resultSet.getString("come_date")),
                        resultSet.getString("lavozim")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql3 = " UPDATE xodimlar SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "'" +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println("Xodimlar setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return xodimlar1;
    }


    public void updateXodimlar(Xodimlar xodimlar) {
        String sql = "UPDATE xodimlar SET " +
                "first_name = '" + xodimlar.getIsm() + "', " +
                "sure_name = '" + xodimlar.getFamiliya() + "', " +
                "last_name = '" + xodimlar.getOchestva() + "', " +
                "birth_day = '" + localDateParseToString(xodimlar.getTugilganVaqt()) + "', " +
                "lavozim = '" + xodimlar.getLavozim() + "' " +
                "WHERE id = '" + xodimlar.getId() + "' " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateTovar statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteXodimlar(int id) {
        String sql = "DELETE FROM xodimlar WHERE id = " + id + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteXodimlar statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
