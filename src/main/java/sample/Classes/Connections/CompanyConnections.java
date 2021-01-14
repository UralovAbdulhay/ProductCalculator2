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


public class CompanyConnections extends Connections {
    

    public ObservableList<Company> getCompanyFromSql() {

        ObservableList<Company> list = FXCollections.observableArrayList();

        try (Connection connection = connect()) {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM company;"
            );

            while (resultSet.next()) {
                list.add(new Company(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        parseToLocalDate(resultSet.getString(3))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public Company getCompanyFromSql(int id) {

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM company WHERE id = " + id + ";"
            );
            Company company = new Company(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    parseToLocalDate(resultSet.getString("date"))
            );
            return company;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Company insertToCompany(Company company) {

        Company company1 = null;
        long temp = System.currentTimeMillis();

        String sql = "INSERT OR IGNORE INTO company (name, temp) VALUES('"
                + company.getName() + "', " +
                temp + ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToCompany statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql1 = "SELECT * FROM company WHERE temp = " + temp + ";";

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(sql1);
            if (resultSet.next()) {
                company1 = new Company(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        parseToLocalDate(resultSet.getString("date"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String sql3 = " UPDATE company SET " +
                "temp = " + null + " " +
                "WHERE temp = " + temp + " " +
                ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql3);
            System.out.println(" Company setTempNull statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return company1;
    }


    public void updateCompany(Company company) {
        String sql = " UPDATE company SET " +
                "name = '" + company.getName() + "' " +
                "WHERE id = " + company.getId() +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateCompany statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCompany(Company company) {
        String sql = "DELETE FROM company WHERE id = " + company.getId() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteCompany statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
