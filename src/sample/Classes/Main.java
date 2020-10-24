package sample.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Controllers.ControllerOyna;
import sample.Moodles.PriseList;
import sample.Moodles.Xodimlar;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


public class Main extends Application {

//    static {
//        PriseList.priseLists.add(new PriseList(new Tovar(1, "8479899709", "Birinchi",
//                "HBD 275/H90", "OZAK/Кsfdaай", 120, 0.8, "usd",
//                0.08, 0.1, 0.05, LocalDate.of(1999, 3, 24),
//                "шт", "Eng qimmat tavar")));
//
//        PriseList.priseLists.addAll(
//
//                new PriseList(new Tovar(2, "8472161200", "Ikkinchi",
//                        "SITC14023", "ITC/Китай", 100, 0.70, "usd",
//                        0.09, 0.05, 0.1, LocalDate.of(2000, 8, 6),
//                        "шт", "Chedirmalar mavjud")),
//
//                new PriseList(new Tovar(3, "8479899708", "HBD 275/H90 ДОРОЖНЫЙ БОЛЛАРД (В комплекте 4шт.  БОЛЛАРДОВЫХ СТОЛБ)",
//                        "HBD 275/H90", "OZAK/Китай", 100, 0.99, "usd",
//                        0.08, 0.1, 0.05, LocalDate.of(1999, 1, 24),
//                        "шт", "Xitoy tavari")),
//
//                new PriseList(new Tovar(4, "8472161201", "SITC14023",
//                        "SITC14023", "ITC/Китай", 102, 0.99, "usd",
//                        0.09, 0.1, 0.05, LocalDate.of(1236, 2, 28),
//                        "шт", "Chedirmalar mavjud")),
//
//                new PriseList(new Tovar(5, "8472161202", "SITC14023",
//                        "SITC14023", "ITC/Китай", 102, 0.99, "usd",
//                        0.09, 0.1, 0.05, LocalDate.of(1998, 9, 10),
//                        "шт", "Chedirmalar mavjud")),
//
//                new PriseList(new Tovar(6, "8472161203", "SITC14023",
//                        "SITC14023", "ITC/Китай", 102, 0.99, "usd",
//                        0.09, 0.1, 0.05, LocalDate.of(1993, 7, 8),
//                        "шт", "Orgilan tavar")),
//
//                new PriseList(new Tovar(7, "8472161204", "SITC14023",
//                        "SITC14023", "ITC/Китай", 102, 0.99, "usd",
//                        0.09, 0.1, 0.05, LocalDate.of(1994, 12, 3),
//                        "шт", "Chedirmalar mavjud")),
//
//                new PriseList(new Tovar(8, "8472161205", "SITC14023",
//                        "SITC14023", "ITC/Китай", 102, 0.99, "usd",
//                        0.09, 0.1, 0.05, LocalDate.of(1989, 9, 4),
//                        "шт", "Xitoy tavari")),
//
//                new PriseList(new Tovar(9, "8472161206", "SITC14023",
//                        "SITC14023", "ITC/Китай", 102, 0.99, "usd",
//                        0.09, 0.1, 0.05, LocalDate.now(),
//                        "шт", "Chedirmalar mavjud"))
//        );
//
//
//        PriseList.addPriseList(new PriseList(new Tovar(10, "8479899709", "Birinchi",
//                "HBD 275/H90", "OZAK/Кsfdaай", 100, 0.99, "usd",
//                0.08, 0.1, 0.05, LocalDate.of(1999, 3, 24),
//                "шт.", "Eng qimmat tavar")));
//    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/oyna.fxml"));
        BorderPane root = loader.load();


        primaryStage.setTitle("Product Calculator");
        Image icon = new Image("/sample/Views/icon.png");
        primaryStage.getIcons().addAll(icon);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(650);
        primaryStage.show();

        ControllerOyna controller = loader.getController();
        controller.setMainStage(primaryStage);

//        ulanish();
    }

    private void ulanish() {

        try {
            Class.forName("org.sqlite.JDBC");

            ResultSet resultSet = selectAllFromSql("client", "id");


            while (resultSet.next()) {
                int id = resultSet.getInt(1);

                String name = resultSet.getString(2);
                String date = (
                        resultSet.getString(3)
                );
                System.out.println();

                System.out.println("connected :) ");
                System.out.println(" id : " + id);
                System.out.println(" name : " + name);
                System.out.println(" date : " + date);

//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDate dateTime = LocalDate.parse(date, formatter);


//                System.out.println("dateTime = " + dateTime);

                ResultSet resultSet1 = selectAllFromSql("project", "nomer_zakaz");

                while (resultSet1.next()) {
                    System.out.println(resultSet1.getString("name"));
                }
            }
            con.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }


    private Connection con = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private String dataBase = "baza/colcul.db";

    ResultSet selectAllFromSql(String tableName, String id) {

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
            statement = con.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM " + tableName + " WHERE " + id + " <= 32; "
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    Statement getStatement() {
        Statement statement = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase)) {

            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }


    public static void main(String[] args) {
        Connections connections = new Connections();
        PriseList.addAllPriseLists(connections.getTovarFromSql());
//        launch(args);

        new Connections().insertToXodimlar(
                new Xodimlar(
                        2,"abdulhay", "Uralov", "adsfas",
                        LocalDate.now(), LocalDate.now(), "ishchi"
                        )
        );

    }
}
