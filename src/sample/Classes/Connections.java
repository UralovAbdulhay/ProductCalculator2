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


    public PriseList getTovarFromSql(int id) {
        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM tovar WHERE id = " + id + ";"
            );
            return new PriseList(
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
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    public LocalDateTime parseToLocalDateTime(String value) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
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

    public String localDateTimeParseToString(LocalDateTime dateTime) {
        System.out.println(dateTime.format(dateFormatter));
        return dateTime.format(dateFormatter);
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

    public Maker getMakerFromSql(int id) {
        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM maker WHERE id = " + id + ";"
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

    public Xodimlar getXodimlarFromSql(int id) {
        System.out.println("getXodimlarFromSql id = " + id);
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
            System.out.println(xodimlar);
            return xodimlar;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    public StavkaShablon getStavkaFromSql(String code) {

        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM stavka WHERE kod = '" + code + "';"
            );
            return new StavkaShablon(
                    resultSet.getString("name"),
                    resultSet.getDouble("qiymat"),
                    resultSet.getString("komment"),
                    resultSet.getString("kod")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ObservableList<Project> getProjectFromSql() {
        ObservableList<Project> list = FXCollections.observableArrayList();
//        ResultSet resultSet = selectAllFromSql("project");
        ResultSet resultSet = selectOneFromSql("project", "nomer_zakaz", 2);


        try {
            while (resultSet.next()) {

                int numPr = resultSet.getInt("nomer_zakaz");
                LocalDate boshlanganVaqt = parseToLocalDate(resultSet.getString("start_date"));
                LocalDateTime tugashVaqti = parseToLocalDateTime(resultSet.getString("end_date"));
                boolean prIsImportant = resultSet.getBoolean("muhum");
                boolean prIsShoshilinch = resultSet.getBoolean("shoshilinch");
                String prNomi = resultSet.getString("name");
//
//                String prKlient = getStatement().executeQuery("SELECT name FROM client WHERE id = "
//                        + resultSet.getInt("client_id") + ";").getString("name");
//
//                String prKMP_komp = getStatement().executeQuery("SELECT name FROM kmpFromCom WHERE id = "
//                        + resultSet.getInt("from_com_id ") + ";").getString("name");
//
//                String prRaxbar = getStatement().executeQuery("SELECT first_name FROM xodimlar WHERE id = "
//                        + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1) + " " +
//                        getStatement().executeQuery("SELECT sure_name FROM xodimlar WHERE id = "
//                                + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1);
//
//                String prKritgan = getStatement().executeQuery("SELECT first_name FROM xodimlar WHERE id = "
//                        + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1) + " " +
//                        getStatement().executeQuery("SELECT sure_name FROM xodimlar WHERE id = "
//                                + resultSet.getInt("kiritgan_xodim_id ") + ";").getString(1);
//
//                String prMasul = getStatement().executeQuery("SELECT first_name FROM xodimlar WHERE id = "
//                        + resultSet.getInt("raxbar_xodim_id ") + ";").getString(1) + " " +
//                        getStatement().executeQuery("SELECT sure_name FROM xodimlar WHERE id = "
//                                + resultSet.getInt("masul_xodim_id ") + ";").getString(1);

                int prFormula = resultSet.getInt("formula");
                String prKomment = resultSet.getString("komment");

                int clientId = resultSet.getInt("client_id");
                int comId = resultSet.getInt("from_com_id");
                int raxbarId = resultSet.getInt("raxbar_xodim_id");
                int masulId = resultSet.getInt("masul_xodim_id");
                int kiritganId = resultSet.getInt("kiritgan_xodim_id");

                Project project = new Project(
                        numPr, boshlanganVaqt, prIsImportant, prIsShoshilinch, prNomi,
                        getClientFromSql(clientId),
                        getCompanyFromSql(comId),
                        getXodimlarFromSql(raxbarId),
                        getXodimlarFromSql(masulId),
                        tugashVaqti, prFormula, prKomment,
                        getXodimlarFromSql(kiritganId)
                );

                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
                ResultSet set = connection.createStatement().executeQuery(
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

    public Client getClientFromSql(int id) {
        System.out.println("getClientFromSql = " + id);

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
            System.out.println(client);
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public ObservableList<Company> getCompanyFromSql() {

        ObservableList<Company> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("client");
        try {
            while (resultSet.next()) {
                list.add(new Company(
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

    public Company getCompanyFromSql(int id) {
        System.out.println("getCompanyFromSql id = " + id);
        try (Connection connection = connect()) {
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT * FROM kmpFromCom WHERE id = " + id + ";"
            );
            Company company = new Company(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    parseToLocalDate(resultSet.getString("date"))
            );
            System.out.println(company);
            return company;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    public ResultSet selectOneFromSql(String tableName, String keyColumn, String key) {

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
            statement = con.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM " + tableName +
                            " WHERE " + keyColumn + " = " + key + ";"
            );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet selectOneFromSql(String tableName, String keyColumn, int key) {

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
            statement = con.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM " + tableName +
                            " WHERE " + keyColumn + " = " + key + ";"
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
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase)) {
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
                " SELECT ('" + client.getName() + "')  \n" +
                " WHERE NOT EXISTS( " +
                " SELECT 1 FROM client WHERE name = '" +
                client.getName() + "' );";

        String sql = " INSERT OR IGNORE INTO client(name) VALUES('" + client.getName() + "');";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToClient statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertToCourse(Valyuta valyuta) {

        String sql =
                "INSERT OR IGNORE INTO course ( " +
                        " title, code, cb_price, nbu_buy_price, nbu_cell_price, refresh_date) " +
                        "VALUES(" +
                        "'" + valyuta.getTitle() + "', " +
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
                statement1.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertToCompany(Company company) {
        String sql = " INSERT OR IGNORE INTO kmpFromCom(name) VALUES('" + company.getName() + "');";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToCompany statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertToMaker(Maker maker) {
        String sql = " INSERT OR IGNORE INTO kmpFromCom(name, country)" +
                " VALUES('" + maker.getName() + "', " +
                "'" + maker.getCountry() + "' );";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToMaker statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertToProject(Project project) {
        String sql = " INSERT OR IGNORE INTO project(" +
                "name, shoshilinch, muhum, client_id, from_com_id, " +
                "raxbar_xodim_id, kiritgan_xodim_id, masul_xodim_id, " +
                "end_date, formula, komment" +
                ") " +
                "VALUES( " +
                "'" + project.getPrNomi() + "', " +
                "'" + project.isPrIsShoshilinch() + "', " +
                "'" + project.isPrIsImportant() + "', " +
                project.getPrClient().getId() + ", " +
                project.getPrKmpCompany().getId() + ", " +
                project.getPrRaxbar().getId() + ", " +
                project.getPrKritgan().getId() + ", " +
                project.getPrMasul().getId() + ", " +
                "'" + localDateTimeParseToString(project.getTugashVaqti()) + "', " +
                "'" + project.getPrFormula() + "', " +
                "'" + project.getPrKomment() + "' " +
                ");";

        System.out.println("sql = " + sql);
        try (Connection connection = connect()) {

            PreparedStatement statement = connection.prepareStatement(sql);

            System.out.println("insertToMaker statement = " + statement.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
