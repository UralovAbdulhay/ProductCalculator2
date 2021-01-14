package sample.Classes.Connections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Moodles.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class ProjectConnections extends Connections {
 
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


                double prDDPliXarStavka = resultSet.getDouble("prDDPliXarStavka");
                double prNdsliXarStavka = resultSet.getDouble("prNdsliXarStavka");
                double prKichikCipXarStavka = resultSet.getDouble("prKichikCipXarStavka");
                double prCipliXarStavka = resultSet.getDouble("prCipliXarStavka");


                Stavkalar stavkalar = new Stavkalar(trans, cip, usd, rub, eur, boj, nds1Bez, nds1S, nds2);


                Project project = new Project(
                        numPr, boshlanganVaqt, prIsImportant, prIsShoshilinch, prNomi,
                        new ClientConnections().getClientFromSql(clientId),
                        new CompanyConnections().getCompanyFromSql(comId),
                        new XodimlarConnections().getXodimlarFromSql(raxbarId),
                        new XodimlarConnections().getXodimlarFromSql(masulId),
                        tugashVaqti, prFormula, prKomment,
                        new XodimlarConnections().getXodimlarFromSql(kiritganId)
                );
                project.setDone(done);
                project.setPrTugallanganVaqti(doneDate);
                project.setPrStavkalar(stavkalar);

                project.setPrDDPliXarStavka(prDDPliXarStavka);
                project.setPrNdsliXarStavka(prNdsliXarStavka);
                project.setPrKichikCipXarStavka(prKichikCipXarStavka);
                project.setPrCipliXarStavka(prCipliXarStavka);


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
                    Maker tovarMaker = new MakerConnections().getMakerFromSql(set.getInt("tovarMakerId"));
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
                        new ClientConnections().getClientFromSql(clientId),
                        new CompanyConnections().getCompanyFromSql(comId),
                        new XodimlarConnections().getXodimlarFromSql(raxbarId),
                        new XodimlarConnections().getXodimlarFromSql(masulId),
                        tugashVaqti, prFormula, prKomment,
                        new XodimlarConnections().getXodimlarFromSql(kiritganId)
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


        Project project1 = selectProjectFromDB(temp);
        project1.addProjectZakazList(project.getProjectZakazList());
        new ZakazlistConnections().insertToZakazList(project1);
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
                "komment = '" + project.getPrKomment() + "', " +
                "prDDPliXarStavka = " + project.getPrDDPliXarStavka() + ", " +
                "prNdsliXarStavka = " + project.getPrNdsliXarStavka() + ", " +
                "prKichikCipXarStavka = " + project.getPrKichikCipXarStavka() + ", " +
                "prCipliXarStavka = " + project.getPrCipliXarStavka() + " " +
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
            new ZakazlistConnections().deleteFromZakazList(project.getNumPr(), tovarZakaz.getTovarId());
        }
    }


}
