package sample.Classes;

import sample.Classes.Connections.Connections;
import sample.Classes.Connections.StavkaConnections;
import sample.Moodles.StavkaShablon;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DDLConroller {

    public DDLConroller() {
        createNewDatabase();
        createTables();
        initStavka();
    }

    private void createTables() {


        // ClientTable
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


        //CompanyTable
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


        // CourseTable
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

        //MakerTable
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


        //PasswordTable
        String createPasswordTab = "create table if not exists passwords \n" +
                "(\n" +
                "    login    varchar(50) not null\n" +
                "        constraint passwords_pk\n" +
                "            primary key,\n" +
                "    password varchar(50) not null,\n" +
                "    date     date    default current_date,\n" +
                "    label    varchar(50),\n" +
                "    type     boolean default false not null\n" +
                ");\n";


        //ProjectTable
        String createProjectTab = "create table if not exists project\n" +
                "(\n" +
                "    nomer_zakaz          INTEGER      not null\n" +
                "        constraint project_pk\n" +
                "            primary key autoincrement,\n" +
                "    name                 VARCHAR(500) not null,\n" +
                "    start_date           DATETIME default CURRENT_TIMESTAMP,\n" +
                "    shoshilinch          BOOLEAN  default 0,\n" +
                "    muhum                BOOLEAN  default 0,\n" +
                "    client_id            INTEGER      not null,\n" +
                "    from_com_id          INTEGER      not null,\n" +
                "    raxbar_xodim_id      INTEGER      not null\n" +
                "        references xodimlar,\n" +
                "    kiritgan_xodim_id    INTEGER      not null,\n" +
                "    masul_xodim_id       INTEGER      not null,\n" +
                "    end_date             DATETIME     not null,\n" +
                "    formula              INTEGER  default 0 not null,\n" +
                "    komment              VARCHAR(1500),\n" +
                "    done                 BOOLEAN  default 0 not null,\n" +
                "    temp                 varchar(30),\n" +
                "    doneDate             date,\n" +
                "    transStavka          double   default 0 not null,\n" +
                "    cipStavka            double   default 0 not null,\n" +
                "    bojStavka            double   default 0 not null,\n" +
                "    nds1S                double   default 0 not null,\n" +
                "    nds1Bez              double   default 0 not null,\n" +
                "    nds2                 double   default 0 not null,\n" +
                "    usdSumStavka         double   default 0 not null,\n" +
                "    rubSumStavka         double   default 0 not null,\n" +
                "    eurSumStavka         double   default 0 not null,\n" +
                "    prDDPliXarStavka     double   default 0,\n" +
                "    prNdsliXarStavka     double   default 0,\n" +
                "    prKichikCipXarStavka double   default 0,\n" +
                "    prCipliXarStavka     double   default 0\n" +
                ");";

        String createProjectTabIndex = "create unique index if not exists maker_name_uindex\n" +
                "    on project (temp);";

        //StavkaTable
        String createStavkaTabIn = "create table if not exists stavka\n" +
                "(\n" +
                "    name    VARCHAR(50) not null,\n" +
                "    qiymat  DOUBLE      not null,\n" +
                "    komment VARCHAR(50),\n" +
                "    kod     VARCHAR(50) not null\n" +
                "        constraint stavka_pk\n" +
                "            primary key\n" +
                ");";


        //TovarTable
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


        //XodimlarTable
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


        //ZakazListTable
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
                ");\n";


        try (Connection connection = new Connections().connect()) {
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


    private void createNewDatabase() {

        try (Connection conn = new Connections().connect()) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void initStavka() {

        List<StavkaShablon> stavkaShablons = new ArrayList<>();
        stavkaShablons.add(0, new StavkaShablon("Транспорт ставка", 0, "Транспорт ставка", "trans"));
        stavkaShablons.add(1, new StavkaShablon("CIP ставка", 0, "Для Расчетов \"CIP ВЭД\"", "cip"));
        stavkaShablons.add(2, new StavkaShablon("Таможня собр", 0, "Таможня собр", "boj"));
        stavkaShablons.add(3, new StavkaShablon("НДС 1", 0, "Для Расчетов  \" DDP с НДС ВЭД\"", "nds1s"));
        stavkaShablons.add(4, new StavkaShablon("НДС 1", 0, "Для Расчетов  \"DDP без НДС ВЭД\"", "nds1bez"));
        stavkaShablons.add(5, new StavkaShablon("НДС 2", 0, "Для Расчетов \"DDP c НДС ВЭД\"", "nds2LiNarx"));
        stavkaShablons.add(6, new StavkaShablon("Доллар СУМ-США", 0, "СУМ", "usd_sum"));
        stavkaShablons.add(7, new StavkaShablon("Доллар СУМ-РУБЛЬ", 0, "СУМ", "usd_rub"));
        stavkaShablons.add(8, new StavkaShablon("Доллар СУМ-ЕВРО", 0, "СУМ", "usd_euro"));

        stavkaShablons.forEach(e -> new StavkaConnections().insertToStavka(e));
    }

}
