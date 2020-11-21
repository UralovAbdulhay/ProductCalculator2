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
                Maker tovarMaker = getMakerFromSql(resultSet.getInt("maker"));
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
            Maker tovarMaker = getMakerFromSql(resultSet.getInt("maker"));
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


    public LocalDate parseToLocalDate(String value) {
        if (value == null) {
            value = "0001-01-01";
        }
        String s[] = value.split(" ");
        value = value.trim();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            long l = Long.parseLong(s[0]);
            Date date = new Date();
            date.setTime(l);
            return LocalDate.parse(format.format(date), dateFormatter);
        } catch (NumberFormatException e) {
            return LocalDate.parse(s[0], dateFormatter);
        }
    }


    public LocalDateTime parseToLocalDateTime(String value) {
        if (value == null) {
            value = "0001-01-01 00:00:00";
        }
        value = value.trim();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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
        System.out.println("connection localDateTimeParseToString dateTime = " + dateTime);
        return dateTime.format(dateTimeFormatter);
    }

    public String localDateParseToString(LocalDate date) {
        return date.format(dateFormatter);
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
                        parseToLocalDate(resultSet.getString("come_date")),
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

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM project ;"
            );


            while (resultSet.next()) {

                int numPr = resultSet.getInt("nomer_zakaz");
                LocalDate boshlanganVaqt = parseToLocalDate(resultSet.getString("start_date"));
                LocalDateTime tugashVaqti = parseToLocalDateTime(resultSet.getString("end_date"));
                boolean prIsImportant = resultSet.getBoolean("muhum");
                boolean prIsShoshilinch = resultSet.getBoolean("shoshilinch");
                String prNomi = resultSet.getString("name");

                int prFormula = resultSet.getInt("formula");
                String prKomment = resultSet.getString("komment");

                int clientId = resultSet.getInt("client_id");
                int comId = resultSet.getInt("from_com_id");
                int raxbarId = resultSet.getInt("raxbar_xodim_id");
                int masulId = resultSet.getInt("masul_xodim_id");
                int kiritganId = resultSet.getInt("kiritgan_xodim_id");
                boolean done = resultSet.getBoolean("done");
                LocalDateTime doneDate = resultSet.getString("doneDate") != null ?
                        parseToLocalDateTime(resultSet.getString("doneDate")) : null;
                double trans = resultSet.getDouble("transStavka");
                double cip = resultSet.getDouble("cipStavka");
                double boj = resultSet.getDouble("bojStavka");
                double nds1S = resultSet.getDouble("nds1S");
                double nds1Bez = resultSet.getDouble("nds1Bez");
                double nds2 = resultSet.getDouble("nds2");
                double usd = resultSet.getDouble("usdSumStavka");
                double rub = resultSet.getDouble("rubSumStavka");
                double eur = resultSet.getDouble("eurSumStavka");

                Stavkalar stavkalar = new Stavkalar(trans, cip, usd, rub, eur, boj, nds1Bez, nds1S, nds2);


                Project project = new Project(
                        numPr, boshlanganVaqt, prIsImportant, prIsShoshilinch, prNomi,
                        getClientFromSql(clientId),
                        getCompanyFromSql(comId),
                        getXodimlarFromSql(raxbarId),
                        getXodimlarFromSql(masulId),
                        tugashVaqti, prFormula, prKomment,
                        getXodimlarFromSql(kiritganId)
                );
                project.setDone(done);
                project.setPrTugallanganVaqti(doneDate);
                project.setPrStavkalar(stavkalar);


                ResultSet set = connection.createStatement().executeQuery(
                        "SELECT  " +
                                "       zakazList.tovar_id, " +
                                "       zakazList.project_id, " +
                                "       tovar.name                   AS tovarName, \n" +
                                "       tovar.kod                    AS tovarKod, \n" +
                                "       zakazList.maker_id           AS tovarMakerId, \n" +
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
                    Maker tovarMaker = getMakerFromSql(set.getInt("tovarMakerId"));
                    PriseList priseList = new PriseList(
                            new Tovar(
                                    set.getInt("tovar_id"),
                                    set.getString("tovarKod"),
                                    set.getString("tovarName"),
                                    set.getString("tovarModel"),
                                    tovarMaker,
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
                    project.addProjectZakazList(new TovarZakaz(priseList, project.getPrStavkalar()));
                }
                list.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ObservableList<Project> getProjectsDoneFromSql() {
        ObservableList<Project> list = FXCollections.observableArrayList();
        getProjectFromSql().stream().filter(Project::isDone).forEach(list::add);
        return list;
    }


    public ObservableList<Project> getProjectsNotDoneFromSql() {
        ObservableList<Project> list = FXCollections.observableArrayList();
        getProjectFromSql().stream().filter(e -> !e.isDone()).forEach(list::add);
        return list;
    }

    public Project selectProjectFromDB(long temp) {
        System.out.println("temp = " + temp);
        Project project = null;

        try (Connection con = connect()) {
            ResultSet resultSet = con.createStatement().executeQuery(
                    "SELECT * FROM project " +
                            " WHERE temp = '" + temp + "' ;"
            );

            if (resultSet.next()) {

                int numPr = resultSet.getInt("nomer_zakaz");
                LocalDate boshlanganVaqt = parseToLocalDate(resultSet.getString("start_date"));
                LocalDateTime tugashVaqti = parseToLocalDateTime(resultSet.getString("end_date"));
                boolean prIsImportant = resultSet.getBoolean("muhum");
                boolean prIsShoshilinch = resultSet.getBoolean("shoshilinch");
                String prNomi = resultSet.getString("name");

                int prFormula = resultSet.getInt("formula");
                String prKomment = resultSet.getString("komment");

                int clientId = resultSet.getInt("client_id");
                int comId = resultSet.getInt("from_com_id");
                int raxbarId = resultSet.getInt("raxbar_xodim_id");
                int masulId = resultSet.getInt("masul_xodim_id");
                int kiritganId = resultSet.getInt("kiritgan_xodim_id");
                boolean done = resultSet.getBoolean("done");
                LocalDateTime doneDate = resultSet.getString("doneDate") != null ?
                        parseToLocalDateTime(resultSet.getString("doneDate")) : null;
                double trans = resultSet.getDouble("transStavka");
                double cip = resultSet.getDouble("cipStavka");
                double boj = resultSet.getDouble("bojStavka");
                double nds1S = resultSet.getDouble("nds1S");
                double nds1Bez = resultSet.getDouble("nds1Bez");
                double nds2 = resultSet.getDouble("nds2");
                double usd = resultSet.getDouble("usdSumStavka");
                double rub = resultSet.getDouble("rubSumStavka");
                double eur = resultSet.getDouble("eurSumStavka");

                Stavkalar stavkalar = new Stavkalar(trans, cip, usd, rub, eur, boj, nds1Bez, nds1S, nds2);

                project = new Project(
                        numPr, boshlanganVaqt, prIsImportant, prIsShoshilinch, prNomi,
                        getClientFromSql(clientId),
                        getCompanyFromSql(comId),
                        getXodimlarFromSql(raxbarId),
                        getXodimlarFromSql(masulId),
                        tugashVaqti, prFormula, prKomment,
                        getXodimlarFromSql(kiritganId)
                );
                project.setDone(done);
                project.setPrTugallanganVaqti(doneDate);
                project.setPrStavkalar(stavkalar);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = " UPDATE project SET " +
                "temp = " + null + " " +
                "WHERE temp = '" + temp + "' " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("selectProjectFromDB statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return project;
    }


    public ObservableList<Client> getClientFromSql() {

        ObservableList<Client> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("client");
        try {
            while (resultSet.next()) {
                list.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        parseToLocalDate(resultSet.getString("date"))
                ));
            }
            con.close();
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


    public ObservableList<Company> getCompanyFromSql() {

        ObservableList<Company> list = FXCollections.observableArrayList();
        ResultSet resultSet = selectAllFromSql("company");
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
        ResultSet resultSet = null;
        try (Connection con = DriverManager.getConnection("jdbc:sqlite:" + dataBase)) {
            Class.forName("org.sqlite.JDBC");
            resultSet = con.createStatement().executeQuery(
                    "SELECT * FROM " + tableName +
                            " WHERE " + keyColumn + " = '" + key + "';"
            );
        } catch (ClassNotFoundException | SQLException e) {
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
        try (Connection connection = connect()) {

            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public boolean tableIsEmpty(String tableName) {
        try (Connection connection = connect()) {
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


    public Client insertToClient(Client client) {
        long temp = System.currentTimeMillis();

        String sql1 = "INSERT INTO client ( name ) \n" +
                " SELECT ('" + client.getName() + "')  \n" +
                " WHERE NOT EXISTS( " +
                " SELECT 1 FROM client WHERE name = '" +
                client.getName() + "' );";

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


    public void insertToCourse(Valyuta valyuta) {

        String sql =
                "INSERT OR IGNORE INTO course ( " +
                        " title, code, cb_price, nbu_buy_price, nbu_cell_price, refresh_date) " +
                        "VALUES(" +
                        "'" + valyuta.getTitleForDB() + "', " +
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
                System.out.println("updateToCourse = " + statement1.executeUpdate());
            } else {
                System.out.println("insertToCourse = " + 1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(Valyuta valyuta) {
        String sql = "DELETE FROM course WHERE code = '" + valyuta.getCode() + "';";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteValyuta statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Company insertToCompany(Company company) {

        Company company1 = null;
        long temp = System.currentTimeMillis();

        String sql = " INSERT OR IGNORE INTO company (name, temp) VALUES('"
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


    public void insertToProject(Project project) {

        long temp = System.currentTimeMillis();

        int muhum = project.isPrIsImportant() ? 1 : 0;
        int shoshilinch = project.isPrIsShoshilinch() ? 1 : 0;

        String sql = " INSERT " +
                "OR IGNORE " +
                "INTO project(" +
                "name, shoshilinch, muhum, client_id, from_com_id, " +
                "raxbar_xodim_id, kiritgan_xodim_id, masul_xodim_id, " +
                "end_date, formula, komment, temp, transStavka, cipStavka, bojStavka, " +
                "nds1S, nds1Bez, nds2, usdSumStavka, rubSumStavka, eurSumStavka " +
                ") " +
                "VALUES( " +
                "'" + project.getPrNomi().replace("'", "`") + "', " +
                " " + shoshilinch + ", " +
                " " + muhum + ", " +
                project.getPrClient().getId() + ", " +
                project.getPrKmpCompany().getId() + ", " +
                project.getPrRaxbar().getId() + ", " +
                project.getPrKritgan().getId() + ", " +
                project.getPrMasul().getId() + ", " +
                "'" + localDateTimeParseToString(project.getTugashVaqti()) + "', " +
                "'" + project.getPrFormulaNum() + "', " +
                "'" + project.getPrKomment().replace("'", "`") + "', " +
                "" + temp + ", " +
                "" + project.getPrStavkalar().getStTrans() + ", " +
                "" + project.getPrStavkalar().getStCIP() + ", " +
                "" + project.getPrStavkalar().getStBojxona() + ", " +
                "" + project.getPrStavkalar().getStNDS1S() + ", " +
                "" + project.getPrStavkalar().getStNDS1Bez() + ", " +
                "" + project.getPrStavkalar().getStNDS2() + ", " +
                "" + project.getPrStavkalar().getStUSD_USZ() + ", " +
                "" + project.getPrStavkalar().getStUSD_RUB() + ", " +
                "" + project.getPrStavkalar().getStUSD_EUR() + " " +
                ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("insertToProject statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("temp = " + temp);
        Project project1 = selectProjectFromDB(temp);
        System.out.println(project1);
        project1.addProjectZakazList(project.getProjectZakazList());
        insertToZakazList(project1);
    }

    public void updateProject(Project project) {
        int muhum = project.isPrIsImportant() ? 1 : 0;
        int shoshilinch = project.isPrIsShoshilinch() ? 1 : 0;

        String sql = "UPDATE project SET " +
                "name = '" + project.getPrNomi() + "', " +
                "shoshilinch = " + shoshilinch + ", " +
                "muhum = " + muhum + ", " +
                "client_id = " + project.getPrClient().getId() + ", " +
                "from_com_id = " + project.getPrKmpCompany().getId() + ", " +
                "raxbar_xodim_id = " + project.getPrRaxbar().getId() + ", " +
                "kiritgan_xodim_id = " + project.getPrKritgan().getId() + ", " +
                "masul_xodim_id = " + project.getPrMasul().getId() + ", " +
                "end_date = '" + localDateTimeParseToString(project.getTugashVaqti()) + "', " +
                "formula = " + project.getPrFormulaNum() + ", " +
                "komment = '" + project.getPrKomment() + "' " +
                "WHERE nomer_zakaz = " + project.getNumPr() +
                " ;";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateProject statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setProjectDone(Project projectDone) {
        int d = projectDone.isDone() ? 1 : 0;
        String doneDate = localDateTimeParseToString(projectDone.getPrTugallanganVaqti());

        String sql = "UPDATE project SET " +
                "done = " + d + ", " +
                "doneDate = '" + doneDate + "' " +
                "WHERE nomer_zakaz = " + projectDone.getNumPr() +
                ";";
        System.out.println(sql);
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("setProjectDone statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setProjectNotDone(Project projectDone) {
        int d = projectDone.isDone() ? 1 : 0;

        String sql = "UPDATE project SET " +
                "done = " + d + ", " +
                "doneDate = " + null + " " +
                "WHERE nomer_zakaz = " + projectDone.getNumPr() +
                ";";
        System.out.println(sql);
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("setProjectDone statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProject(Project project) {
        String sql = "DELETE FROM project WHERE nomer_zakaz = " + project.getNumPr() + ";";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("deleteProject statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (TovarZakaz tovarZakaz : project.getProjectZakazList()) {
            deleteFromZakazList(project.getNumPr(), tovarZakaz.getTovarId());
        }
    }


    public int insertToStavka(StavkaShablon shablon) {

        String sql = " INSERT OR IGNORE INTO stavka (name, qiymat, komment, kod)" +
                " VALUES('" +
                shablon.getNomi() + "', " +
                shablon.getQiymat() + ", " +
                "'" + shablon.getKomment() + "', " +
                "'" + shablon.getKod() + "' " +
                ");";

        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            int result = statement.executeUpdate();
            System.out.println("insertToStavka statement = " + result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateStavka(StavkaShablon shablon) {
        String sql = "UPDATE stavka SET " +
                "name = '" + shablon.getNomi() + "', " +
                "qiymat = " + shablon.getQiymat() + ", " +
                "komment = '" + shablon.getKomment() + "' " +
                "WHERE kod = '" + shablon.getKod() + "' " +
                ";";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("updateStavka statement = " + statement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int insertToTovar(PriseList priseList) {
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
            int result = statement.executeUpdate();
            System.out.println("insertToTovar statement = " + result);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
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


    public Xodimlar insertToXodimlar(Xodimlar xodimlar) {
        long temp = System.currentTimeMillis();
        Xodimlar xodimlar1 = null;

        String sql = " INSERT OR IGNORE INTO xodimlar (" +
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
