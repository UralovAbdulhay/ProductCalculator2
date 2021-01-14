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


public class TovarConnections extends Connections {


    public ObservableList<PriseList> getTovarFromSql() {
        ObservableList<PriseList> list = FXCollections.observableArrayList();
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM tovar;"
            );
            while (resultSet.next()) {
                Maker tovarMaker = new MakerConnections().getMakerFromSql(resultSet.getInt("maker"));
                list.add(
                        new PriseList(
                                new Tovar(
                                        resultSet.getInt("id"),
                                        resultSet.getString("kod"),
                                        resultSet.getString("name"),
                                        resultSet.getString("model"),
                                        tovarMaker,
                                        resultSet.getDouble("cost"),
                                        resultSet.getDouble("ddp_cost"),
                                        resultSet.getString("cost_type"),
                                        resultSet.getDouble("trans_cost"),
                                        resultSet.getDouble("aksiz_cost"),
                                        resultSet.getDouble("poshlina_cost"),
                                        parseToLocalDate(resultSet.getString("date")),
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


    public PriseList getTovarFromSql(int id) {
        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM tovar WHERE id = " + id + ";"
            );
            Maker tovarMaker = new MakerConnections().getMakerFromSql(resultSet.getInt("maker"));
            return new PriseList(
                    new Tovar(
                            resultSet.getInt("id"),
                            resultSet.getString("kod"),
                            resultSet.getString("name"),
                            resultSet.getString("model"),
                            tovarMaker,
                            resultSet.getDouble("cost"),
                            resultSet.getDouble("ddp_cost"),
                            resultSet.getString("cost_type"),
                            resultSet.getDouble("trans_cost"),
                            resultSet.getDouble("aksiz_cost"),
                            resultSet.getDouble("poshlina_cost"),
                            parseToLocalDate(resultSet.getString("date")),
                            resultSet.getString("ulchov_type"),
                            resultSet.getString("komment")
                    )
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void insertToTovar(PriseList priseList) {
        Tovar tovar = priseList.getTovar();
        String sql = " INSERT OR IGNORE INTO tovar (" +
                "name, model, kod, maker, cost, cost_type, trans_cost, aksiz_cost, " +
                "poshlina_cost, ddp_cost, ulchov_type, komment) " +
                "VALUES('" +
                tovar.getTovarNomi() + "', " +
                "'" + tovar.getTovarModel() + "', " +
                "'" + tovar.getTovarKod() + "', " +
                "" + tovar.getTovarIshlabChiqaruvchi().getId() + ", " +
                "" + tovar.getTovarNarxi() + ", " +
                "'" + tovar.getTovarNarxTuri() + "', " +
                "" + tovar.getTovarTransportNarxi() + ", " +
                "" + tovar.getTovarAksiz() + ", " +
                "" + tovar.getTovarPoshlina() + ", " +
                "" + tovar.getTovarDDP() + ", " +
                "'" + tovar.getTovarUlchovBirligi() + "', " +
                "'" + tovar.getTovarKomment() + "' " +
                ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateTovar(PriseList priseList) {
        String sql = "UPDATE tovar SET " +
                "name = '" + priseList.getTovarNomi() + "', " +
                "model = '" + priseList.getTovarModel() + "', " +
                "maker  = " + priseList.getTovarIshlabChiqaruvchi().getId() + ", " +
                "cost = " + priseList.getTovarNarxi() + ", " +
                "cost_type = '" + priseList.getTovarNarxTuri() + "', " +
                "trans_cost = " + priseList.getTovarTransportNarxi() + ", " +
                "aksiz_cost = " + priseList.getTovarAksiz() + ", " +
                "poshlina_cost = " + priseList.getTovarPoshlina() + ", " +
                "ddp_cost = " + priseList.getTovarDDP() + ", " +
                "ulchov_type = '" + priseList.getTovarUlchovBirligi() + "', " +
                "komment = '" + priseList.getTovarKomment() + "' " +
                "WHERE id = '" + priseList.getTovarId() + "' " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateTovar statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteTovar(int id) {
        String sql = "DELETE FROM tovar WHERE id = " + id + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteTovar statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PriseList.reSetPriseList();
    }


    public void deleteTovar(ObservableList<PriseList> priseLists) {

        try (Connection connection = connect()) {
            for (PriseList priseList : priseLists) {
                String sql = "DELETE FROM tovar WHERE id = " + priseList.getTovarId() + ";";
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.println("deleteTovar statement = " + statement.executeUpdate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PriseList.reSetPriseList();
    }


}
