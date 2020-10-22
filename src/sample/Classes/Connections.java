package sample.Classes;

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

    public LocalDate parseToLocalDate(String value) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        System.out.println(value);
        try {
            long l = Long.parseLong(value);
            Date date = new Date();
            date.setTime(l);
            return LocalDate.parse(format.format(date), dateFormatter);
        } catch (NumberFormatException e) {
            return LocalDate.parse(value, dateFormatter);
        }
    }

   public   LocalDateTime parseToLocalDateTime(String value) {
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(value);
        try {
            long l = Long.parseLong(value);
            Date date = new Date();
            date.setTime(l);
            return LocalDateTime.parse(format.format(date), dateFormatter);
        } catch (NumberFormatException e) {
            return LocalDateTime.parse(value, dateFormatter);
        }
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
                        parseToLocalDate(resultSet.getString("birth_day")),
                        parseToLocalDate(resultSet.getString("come_day")),
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

                int numPr = resultSet.getInt("nomer_zakaz");
                LocalDateTime boshlanganVaqt = parseToLocalDateTime(resultSet.getString("start_date"));
                LocalDateTime tugashVaqti = parseToLocalDateTime(resultSet.getString("end_date"));
                boolean prIsImportant = resultSet.getBoolean("muhum");
                boolean prIsShoshilinch = resultSet.getBoolean("shoshilinch");
                String prNomi = resultSet.getString("name");
                String prKlient = getStatement().executeQuery("SELECT name FROM client WHERE id = "
                        + resultSet.getInt("client_id ") + ";").getString("name");

                String prKMP_komp = getStatement().executeQuery("SELECT name FROM kmpFromCom WHERE id = "
                        + resultSet.getInt("from_com_id ") + ";").getString("name");

                String prRaxbar = getStatement().executeQuery("SELECT first_name FROM xodimlar WHERE id = "
                        + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1) + " " +
                        getStatement().executeQuery("SELECT sure_name FROM xodimlar WHERE id = "
                                + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1);

                String prKritgan = getStatement().executeQuery("SELECT first_name FROM xodimlar WHERE id = "
                        + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1) + " " +
                        getStatement().executeQuery("SELECT sure_name FROM xodimlar WHERE id = "
                                + resultSet.getInt("kiritgan_xodim_id ") + ";").getString(1);

                String prMasul = getStatement().executeQuery("SELECT first_name FROM xodimlar WHERE id = "
                        + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1) + " " +
                        getStatement().executeQuery("SELECT sure_name FROM xodimlar WHERE id = "
                                + resultSet.getInt("masul_xodim_id ") + ";").getString(1);

                int prFormula = resultSet.getInt("formula");
                String prKomment = resultSet.getString("komment");

                Project project = new Project(
                        numPr, boshlanganVaqt, prIsImportant, prIsShoshilinch, prNomi, prKlient,
                        prKMP_komp, prRaxbar, prMasul, tugashVaqti,
                        prFormula, prKomment, prKritgan
                );

                ResultSet set = getStatement().executeQuery(
                        "SELECT zakazList.id,\n" +
                                "       zakazList.tovar_id, \n" +
                                "       zakazList.project_id, \n" +
                                "       tovar.name                   AS tovarName, \n" +
                                "       tovar.kod                    AS tovarKod, \n" +
                                "       maker.name                   AS tovarMaker, \n" +
                                "       tovar.model                  AS tovarModel, \n" +
                                "       zakazList.tovar_cost         AS tovarNarx, \n" +
                                "       zakazList.tovar_ddp          AS tvarDDP, \n" +
                                "       zakazList.cost_type          AS tovarCostType, \n" +
                                "       zakazList.tovar_trans_cost   AS tovarTrans, \n" +
                                "       zakazList.tovar_aksiz_cost   AS tovarAksiz, \n" +
                                "       zakazList.tovar_poshlin_cost AS tovarPoshlina, \n" +
                                "       tovar.date                   AS tovarSana, \n" +
                                "       zakazList.ulchov_type        AS ulchovType, \n" +
                                "       tovar.komment                AS tovarKomment,\n" +
                                "       zakazList.count                AS soni \n" +
                                "\n" +
                                "FROM zakazList\n" +
                                "         INNER JOIN tovar\n" +
                                "                    ON zakazList.tovar_id = tovar.id\n" +
                                "         INNER JOIN maker\n" +
                                "                    ON zakazList.maker_id = maker.id\n" +
                                "WHERE zakazList.project_id = " + numPr + "; "
                );

                while (set.next()) {

                    PriseList priseList = new PriseList(
                            new Tovar(
                                    set.getInt("tovar_id"),
                                    set.getString("tovarKod"),
                                    set.getString("tovarName"),
                                    set.getString("tovarModel"),
                                    set.getString("tovarMaker"),
                                    set.getDouble("tovarNarx"),
                                    set.getDouble("tvarDDP"),
                                    set.getString("tovarCostType"),
                                    set.getDouble("tovarTrans"),
                                    set.getDouble("tovarAksiz"),
                                    set.getDouble("tovarPoshlina"),
                                    parseToLocalDate(set.getString("tovarSana")),
                                    set.getString("ulchovType"),
                                    set.getString("tovarKomment")
                            )
                    );
                    priseList.setAddCount(set.getInt("soni"));
                    project.addProjectZakazList(new TovarZakaz(priseList));
                }
                list.add(project);
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
                        parseToLocalDate(resultSet.getString(3))
                ));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ObservableList<Valyuta> getCourseFromSql() {
        ObservableList<Valyuta> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("course");

        try {
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

            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
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

    public Statement getStatement() {
        Statement statement = null;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase)) {

            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public boolean tableIsEmpty(String tableName) {
        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase)) {
            Statement statement = connection.createStatement();
            if (0 == statement.executeQuery("SELECT Count(*) FROM " + tableName + " ;").getInt(1)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public void insertToClient(Client client) {
        String sql1 = "INSERT INTO client ( name ) \n" +
                " SELECT ('"  +client.getName() + "')  \n" +
                " WHERE NOT EXISTS( " +
                " SELECT 1 FROM client WHERE name = '" +
                client.getName() + "' );";

        String sql = " INSERT OR IGNORE INTO client(name) VALUES('" + client.getName() + "');";

        try(Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
