package sample.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Controllers.ControllerOyna;
import sample.Moodles.PriseList;
import sample.Moodles.Stavkalar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class Main extends Application {

    //    private Connection con = null;
//    private Statement statement = null;
//    private ResultSet resultSet = null;
    private String dataBase = "baza/colcul.db";


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
    public void start(Stage primaryStage) {
        if (createNewDatabase()) {
            createTables();
        } else {
            createNewDatabase();
            createTables();
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/oyna.fxml"));
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }


        primaryStage.setTitle("Product Calculator");
        Image icon = new Image("/sample/Views/icon.png");
        primaryStage.getIcons().addAll(icon);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(650);
        primaryStage.show();

        PriseList.reSetPriseList();
        new Stavkalar().resetStavkaShablons();

        ControllerOyna controller = loader.getController();
        controller.setMainStage(primaryStage);

    }

    private void createTables() {

        String createClientTab = "CREATE TABLE IF NOT EXISTS client \n" +
                "(\n" +
                "    id   INTEGER      not null\n" +
                "        primary key autoincrement,\n" +
                "    name VARCHAR(500) not null,\n" +
                "    date DATE default CURRENT_DATE\n" +
                "); ";

        String createClientTabIndex = "CREATE UNIQUE index IF NOT EXISTS client_name_uindex \n" +
                " ON client (name);";

        String createCourseTab = "create table if not exists course\n" +
                "(\n" +
                "    title          VARCHAR(50) not null,\n" +
                "    code           VARCHAR(50) not null\n" +
                "        constraint course_pk\n" +
                "            primary key,\n" +
                "    cb_price       DOUBLE      not null,\n" +
                "    nbu_buy_price  DOUBLE default 0,\n" +
                "    nbu_cell_price DOUBLE default 0,\n" +
                "    date           DATE   default CURRENT_DATE,\n" +
                "    refresh_date   DATE        not null\n" +
                ");";


        String createCompanyTab = "create table if not exists kmpFromCom\n" +
                "(\n" +
                "    id   INTEGER      not null\n" +
                "        primary key autoincrement,\n" +
                "    name VARCHAR(500) not null,\n" +
                "    date DATE default CURRENT_DATE\n" +
                ");";

        String createCompanyTabIndex = "create unique index if not exists kmpFromCom_name_uindex\n" +
                "    on kmpFromCom (name);";




        String createMakerTab = "create table if not exists maker\n" +
                "(\n" +
                "    id      INTEGER      not null\n" +
                "        primary key autoincrement,\n" +
                "    name    VARCHAR(200) not null,\n" +
                "    country VARCHAR(200),\n" +
                "    sana    DATE default current_date not null\n" +
                ");";

        String createMakerTabIndex = "create unique index if not exists maker_name_uindex\n" +
                "    on maker (name);";



        String createProjectTab = "create table if not exists project\n" +
                "(\n" +
                "    nomer_zakaz       INTEGER      not null\n" +
                "        constraint project_pk\n" +
                "            primary key autoincrement,\n" +
                "    name              VARCHAR(500) not null,\n" +
                "    start_date        DATETIME default CURRENT_TIMESTAMP,\n" +
                "    shoshilinch       BOOLEAN  default FALSE not null,\n" +
                "    muhum             BOOLEAN  default false not null,\n" +
                "    client_id         INTEGER      not null,\n" +
                "    from_com_id       INTEGER      not null,\n" +
                "    raxbar_xodim_id   INTEGER      not null,\n" +
                "    kiritgan_xodim_id INTEGER      not null,\n" +
                "    masul_xodim_id    INTEGER      not null,\n" +
                "    end_date          DATETIME     not null,\n" +
                "    formula           INTEGER  default 0 not null,\n" +
                "    komment           VARCHAR(1500),\n" +
                "    done              BOOLEAN  default FALSE not null\n" +
                ");\n";



        String createStavkaTabIn = "create table if not exists stavka\n" +
                "(\n" +
                "    name    VARCHAR(50) not null,\n" +
                "    qiymat  DOUBLE      not null,\n" +
                "    komment VARCHAR(50),\n" +
                "    kod     VARCHAR(50) not null\n" +
                "        constraint stavka_pk\n" +
                "            primary key\n" +
                ");";


        String createTovarTab = "create table if not exists tovar\n" +
                "(\n" +
                "    id            INTEGER      not null\n" +
                "        primary key autoincrement,\n" +
                "    name          VARCHAR(200) not null,\n" +
                "    model         VARCHAR(200) not null,\n" +
                "    kod           VARCHAR(50)  not null,\n" +
                "    maker         INTEGER      not null,\n" +
                "    cost          DOUBLE       not null,\n" +
                "    cost_type     VARCHAR(20)  not null,\n" +
                "    trans_cost    DOUBLE       not null,\n" +
                "    aksiz_cost    DOUBLE       not null,\n" +
                "    poshlina_cost DOUBLE       not null,\n" +
                "    ddp_cost      DOUBLE       not null,\n" +
                "    date          DATE default CURRENT_DATE not null,\n" +
                "    ulchov_type   VARCHAR(10)  not null,\n" +
                "    komment       VARCHAR(1500)\n" +
                ");";

        String createTovarTabIndex = "create unique index if not exists tovar_name_uindex\n" +
                "    on tovar (name);";



        String createXodimlarTab  = "create table if not exists xodimlar\n" +
                "(\n" +
                "    id         INTEGER     not null\n" +
                "        primary key autoincrement,\n" +
                "    first_name VARCHAR(50) not null,\n" +
                "    sure_name  VARCHAR(50),\n" +
                "    last_name  VARCHAR(50),\n" +
                "    birth_day  DATE,\n" +
                "    come_date  date default current_date,\n" +
                "    lavozim    VARCHAR(50)\n" +
                ");";


        String createXodimlarTabIndex = "create unique index if not exists xodimlar_first_name_uindex\n" +
                "    on xodimlar (first_name, sure_name, last_name);";

        String createXodimlarTabIndex1 = "create unique index if not exists xodimlar_sure_name_uindex\n" +
                "    on xodimlar (first_name, sure_name);";


        String createZakazListTabIndex = "create table if not exists zakazList\n" +
                "(\n" +
                "    tovar_id           INTEGER     not null,\n" +
                "    maker_id           INTEGER     not null,\n" +
                "    count              INTEGER     not null,\n" +
                "    tovar_cost         DOUBLE      not null,\n" +
                "    tovar_ddp          DOUBLE      not null,\n" +
                "    tovar_trans_cost   DOUBLE      not null,\n" +
                "    tovar_aksiz_cost   DOUBLE      not null,\n" +
                "    tovar_poshlin_cost DOUBLE      not null,\n" +
                "    cost_type          VARCHAR(20) not null,\n" +
                "    ulchov_type        VARCHAR(20) not null,\n" +
                "    zakaz_date         DATETIME default CURRENT_TIMESTAMP,\n" +
                "    project_id         INTEGER     not null,\n" +
                "    primary key (project_id, tovar_id)\n" +
                ");";


        try (Connection connection = connect()) {
            System.out.println("createClientTab = " + connection.createStatement().execute(createClientTab));
            System.out.println("createClientTabIndex = " + connection.createStatement().execute(createClientTabIndex));
            System.out.println("createCourseTab = " + connection.createStatement().execute(createCourseTab));
            System.out.println("createCompanyTab = " + connection.createStatement().execute(createCompanyTab));
            System.out.println("createCompanyTabIndex = " + connection.createStatement().execute(createCompanyTabIndex));
            System.out.println("createMakerTab = " + connection.createStatement().execute(createMakerTab));
            System.out.println("createMakerTabIndex = " + connection.createStatement().execute(createMakerTabIndex));
            System.out.println("createProjectTab = " + connection.createStatement().execute(createProjectTab));
            System.out.println("createStavkaTabIn = " + connection.createStatement().execute(createStavkaTabIn));
            System.out.println("createTovarTab = " + connection.createStatement().execute(createTovarTab));
            System.out.println("createTovarTabIndex = " + connection.createStatement().execute(createTovarTabIndex));
            System.out.println("createXodimlarTab = " + connection.createStatement().execute(createXodimlarTab));
            System.out.println("createXodimlarTabIndex = " + connection.createStatement().execute(createXodimlarTabIndex));
            System.out.println("createXodimlarTabIndex1 = " + connection.createStatement().execute(createXodimlarTabIndex1));
            System.out.println("createZakazListTabIndex = " + connection.createStatement().execute(createZakazListTabIndex));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    private boolean createNewDatabase() {

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dataBase)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {

        launch(args);
    }
}
