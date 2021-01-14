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


public class ZakazlistConnections extends Connections {
 
    public void insertToZakazList(Project project) {

        int projectId = project.getNumPr();

        for (TovarZakaz tovarZakaz : project.getProjectZakazList()) {

            String sql = "INSERT OR IGNORE INTO zakazList (" +
                    "tovar_id, project_id, maker_id, count, " +
                    "tovar_cost, tovar_ddp, tovar_trans_cost, " +
                    "tovar_aksiz_cost, " +
                    "tovar_poshlin_cost, cost_type, ulchov_type) " +
                    "VALUES (" +
                    tovarZakaz.getTovarId() + ", " +
                    "" + projectId + ", " +
                    "" + tovarZakaz.getTovarIshlabChiqaruvchi().getId() + ", " +
                    "" + tovarZakaz.getZakazSoni() + ", " +
                    "" + tovarZakaz.getTovarNarxi() + ", " +
                    "" + tovarZakaz.getTovarDDP() + ", " +
                    "" + tovarZakaz.getTovarTransportNarxi() + ", " +
                    "" + tovarZakaz.getTovarAksiz() + ", " +
                    "" + tovarZakaz.getTovarPoshlina() + ", " +
                    "'" + tovarZakaz.getTovarNarxTuri() + "', " +
                    "'" + tovarZakaz.getTovarUlchovBirligi() + "' " +
                    " );";

            try (Connection connection = connect()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                int result = statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void updateZakazList(Project project) {

        for (TovarZakaz tovarZakaz : project.getProjectZakazList()) {

            String sql = "UPDATE zakazList SET " +
                    "count = " + tovarZakaz.getZakazSoni() + ", " +
                    "tovar_cost = " + tovarZakaz.getTovarNarxi() + ", " +
                    "tovar_ddp = " + tovarZakaz.getTovarDDP() + ", " +
                    "tovar_trans_cost = " + tovarZakaz.getTovarTransportNarxi() + ", " +
                    "tovar_aksiz_cost = " + tovarZakaz.getTovarAksiz() + ", " +
                    "tovar_poshlin_cost = " + tovarZakaz.getTovarPoshlina() + ", " +
                    "cost_type = '" + tovarZakaz.getTovarNarxTuri() + "', " +
                    "ulchov_type = '" + tovarZakaz.getTovarUlchovBirligi() + "' " +

                    "WHERE tovar_id = " + tovarZakaz.getTovarId() + " AND " +
                    "project_id = " + project.getNumPr() +
                    ";";

            try (Connection connection = connect()) {
                PreparedStatement statement = connection.prepareStatement(sql);
                System.out.println("updateZakazList statement = " + statement.executeUpdate());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void deleteFromZakazList(int projectId, int tovarID) {

        String sql = "DELETE FROM zakazList WHERE project_id = " + projectId + " AND " +
                "tovar_id = " + tovarID + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteFromZakazList statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
