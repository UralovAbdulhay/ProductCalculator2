package sample.Classes;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.Controllers.ControllerOyna;
import sample.Moodles.PriseList;
import sample.Moodles.Stavkalar;

import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main extends Application {


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
        assert root != null;
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(650);
        primaryStage.setOnCloseRequest(e -> {
            copyDB();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

        PriseList.reSetPriseList();
        new Stavkalar().resetStavkaShablons();

        ControllerOyna controller = loader.getController();
        controller.setMainStage(primaryStage);

    }

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");
    private String appDataReserve = System.getenv("APPDATA") +
            "\\ProductCalculator\\baza\\reserve\\";

    private void copyDB() {

        Path sourceFile = Paths.get("baza/colcul.db");
        Path targetFile = Paths.get(appDataReserve +
                dateFormatter.format(LocalDateTime.now()) + " " + sourceFile.getFileName()
        );

        try {
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File successful copied!");
        } catch (IOException e) {
            System.out.println("File Not copied!");
        }
        System.out.println(appDataReserve);
    }

    private void createTables() {

        String createClientTab = "CREATE TABLE IF NOT EXISTS client \n" +
                "(\n" +
                "    id   INTEGER      not null\n" +
                "        primary key autoincrement,\n" +
                "    name VARCHAR(500) not null,\n" +
                "    date DATE default CURRENT_DATE,\n" +
                "    temp numeric\n" +
                ");";

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


        String createCompanyTab = "create table if not exists company\n" +
                "(\n" +
                "    id   INTEGER      not null \n" +
                "        primary key autoincrement,\n" +
                "    name VARCHAR(500) not null,\n" +
                "    date DATE default CURRENT_DATE,\n" +
                "    temp numeric\n" +
                ");";

        String createCompanyTabIndex = "create unique index if not exists company_name_uindex\n" +
                "    on company (name);";


        String createMakerTab = "create table if not exists maker\n" +
                "(\n" +
                "    id      INTEGER      not null\n" +
                "        primary key autoincrement,\n" +
                "    name    VARCHAR(200) not null,\n" +
                "    country VARCHAR(200),\n" +
                "    sana    DATE default current_date not null,\n" +
                "    temp    numeric\n" +
                ");";

        String createMakerTabIndex = "create unique index if not exists maker_name_uindex\n" +
                "    on maker (name);";

        String createPasswordTab = "create table if not exists passwords \n" +
                "(\n" +
                "    userName varchar(50) not null\n" +
                "        constraint passwords_pk\n" +
                "            primary key,\n" +
                "    password varchar(50) not null,\n" +
                "    date     date default current_date\n" +
                ");";


        String createProjectTab = "create table if not exists project\n" +
                "(\n" +
                "    nomer_zakaz       INTEGER      not null\n" +
                "        constraint project_pk\n" +
                "            primary key autoincrement,\n" +
                "    name              VARCHAR(500) not null,\n" +
                "    start_date        DATETIME default CURRENT_TIMESTAMP,\n" +
                "    shoshilinch       BOOLEAN  default 0,\n" +
                "    muhum             BOOLEAN  default 0,\n" +
                "    client_id         INTEGER      not null,\n" +
                "    from_com_id       INTEGER      not null,\n" +
                "    raxbar_xodim_id   INTEGER      not null\n" +
                "        references xodimlar,\n" +
                "    kiritgan_xodim_id INTEGER      not null,\n" +
                "    masul_xodim_id    INTEGER      not null,\n" +
                "    end_date          DATETIME     not null,\n" +
                "    formula           INTEGER  default 0 not null,\n" +
                "    komment           VARCHAR(1500),\n" +
                "    done              BOOLEAN  default 0 not null,\n" +
                "    temp              varchar(30),\n" +
                "    doneDate          date,\n" +
                "    transStavka       double   default 0 not null,\n" +
                "    cipStavka         double   default 0 not null,\n" +
                "    bojStavka         double   default 0 not null,\n" +
                "    nds1S             double   default 0 not null,\n" +
                "    nds1Bez           double   default 0 not null,\n" +
                "    nds2              double   default 0 not null,\n" +
                "    usdSumStavka      double   default 0 not null,\n" +
                "    rubSumStavka      double   default 0 not null,\n" +
                "    eurSumStavka      double   default 0 not null\n" +
                ");";

        String createProjectTabIndex = "create unique index if not exists maker_name_uindex\n" +
                "    on project (temp);";

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


        String createXodimlarTab = "create table if not exists xodimlar\n" +
                "(\n" +
                "    id         INTEGER     not null\n" +
                "        primary key autoincrement,\n" +
                "    first_name VARCHAR(50) not null,\n" +
                "    sure_name  VARCHAR(50),\n" +
                "    last_name  VARCHAR(50),\n" +
                "    birth_day  DATE,\n" +
                "    come_date  date default current_date,\n" +
                "    lavozim    VARCHAR(50),\n" +
                "    temp       numeric\n" +
                ");";


        String createXodimlarTabIndex = "create unique index if not exists xodimlar_first_name_uindex\n" +
                "    on xodimlar (first_name, sure_name, last_name);";

        String createXodimlarTabIndex1 = "create unique index if not exists xodimlar_index_name\n" +
                "    on xodimlar (first_name);";

        String createXodimlarTabIndex2 = "create unique index if not exists xodimlar_sure_name_uindex\n" +
                "    on xodimlar (first_name, sure_name);";


        String createZakazListTab = "create table if not exists zakazList\n" +
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
            System.out.println("createZakazListTab = " + connection.createStatement().execute(createZakazListTab));
            System.out.println("createZakazListTab = " + connection.createStatement().execute(createXodimlarTabIndex2));
            System.out.println("createZakazListTab = " + connection.createStatement().execute(createProjectTabIndex));
            System.out.println("createZakazListTab = " + connection.createStatement().execute(createPasswordTab));

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
        File file = new File("baza");
        file.mkdir();
        file = new File(System.getenv("APPDATA") +
                "\\ProductCalculator\\baza\\reserve\\");

        System.out.println(file.getAbsolutePath()+ "\n" + file.mkdirs());

        launch(args);
    }
}
