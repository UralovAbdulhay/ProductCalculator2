package sample.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Moodles.PriseList;
import sample.Moodles.Project;
import sample.Moodles.Stavkalar;
import sample.Moodles.TovarZakaz;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ReviewProjectController implements Initializable {

    @FXML
    private Label summaUZSLab;
    @FXML
    private TableView<TovarZakaz> projectReviewTable;
    @FXML
    private Label nameLab;
    @FXML
    private Label startDateLab;
    @FXML
    private Label endDateLab;
    @FXML
    private Label formulaLab;
    @FXML
    private Label rahbarLab;
    @FXML
    private Label masulLab;
    @FXML
    private Label clientLab;
    @FXML
    private Label doneDateLab;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Project project;
    private ResourceBundle bundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        initTable();

    }

    public void setProject(Project project) {
        this.project = project;

        ObservableList<TovarZakaz> tovarZakazs = observableArrayList(this.project.getProjectZakazList());
        PriseList.setTr(tovarZakazs);
        projectReviewTable.setItems(tovarZakazs);
        initWindow();
    }

    public void initWindow() {

        nameLab.setText(project.getPrNomi());
        startDateLab.setText(project.getBoshlanganVaqt().format(dateFormatter));
        endDateLab.setText(project.getTugashVaqti().format(dateTimeFormatter));
        formulaLab.setText(project.getPrFormula());
        rahbarLab.setText(project.getPrRaxbar().getIsm());
        masulLab.setText(project.getPrMasul().getIsm());
        clientLab.setText(project.getPrClient().getName());

        DecimalFormat format = new DecimalFormat("#,##0.000");

        if (project.getPrFormulaNum() == 1) {
            summaUZSLab.setText(format.format(
                    projectReviewTable.getItems().stream().mapToDouble(TovarZakaz::getZakazNDS2liSumm).sum())
                    + " " + bundle.getString("sum"));

        } else if (project.getPrFormulaNum() == 0) {
            summaUZSLab.setText(format.format(
                    projectReviewTable.getItems().stream().mapToDouble(TovarZakaz::getZakazDDPsumm).sum())
                    + " " + bundle.getString("sum"));
        } else if (project.getPrFormulaNum() == 2) {
            summaUZSLab.setText(format.format(
                    projectReviewTable.getItems().stream().mapToDouble(TovarZakaz::getZakazCIPSummUSD).sum())
                    + " " + "usd");
        }


        if (project.isDone() && project.getPrTugallanganVaqti() != null) {
            doneDateLab.setText(project.getPrTugallanganVaqti().format(dateTimeFormatter));
        } else {
            doneDateLab.setText("--.--.----");
        }
    }

    private void initTable() {

        TableColumn<TovarZakaz, Integer> tovarTR = creatTabCol("№", 35);
        tovarTR.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTr()
        ));
        tovarTR.setResizable(false);
        tovarTR.impl_setReorderable(false);


        TableColumn<TovarZakaz, String> tovarKod = creatTabCol(bundle.getString("kod"));
        tovarKod.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKod()
        ));

        TableColumn<TovarZakaz, Integer> tovarSoni = creatTabCol(bundle.getString("soni"));
        tovarSoni.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazSoni()
        ));

        TableColumn<TovarZakaz, String> tovarNomi = creatTabCol(bundle.getString("tovarNomi"), 200, 200, 500);
        tovarNomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNomi()
        ));


        TableColumn<TovarZakaz, String> tovarModel = creatTabCol(bundle.getString("model"), 200, 200, 500);
        tovarModel.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarModel()
        ));


        TableColumn<TovarZakaz, String> tovarIshCh = creatTabCol(bundle.getString("maker"), 200, 200, 500);
        tovarIshCh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarIshlabChiqaruvchi().getName()
        ));


        TableColumn<TovarZakaz, String> tovarNarxi = creatTabCol(bundle.getString("narxi"));
        tovarNarxi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxiStr()
        ));


        TableColumn<TovarZakaz, String> tovarNarxiTuri = creatTabCol(bundle.getString("valyuta"), 80);
        tovarNarxiTuri.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxTuri()
        ));
        tovarNarxiTuri.setResizable(false);
        tovarNarxiTuri.setSortable(false);


        TableColumn tovarQushimcha = creatTabCol(bundle.getString("more"));
        tovarQushimcha.setResizable(false);


        TableColumn<TovarZakaz, String> tovarTrans = creatTabCol(bundle.getString("transport"), 80);
        tovarTrans.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarTransportNarxiString()
        ));
        tovarTrans.setSortable(false);


        TableColumn<TovarZakaz, String> tovarAksiz = creatTabCol(bundle.getString("aksiz"), 80);
        tovarAksiz.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarAksizString()
        ));
        tovarAksiz.setSortable(false);


        TableColumn<TovarZakaz, String> tovarPoshlina = creatTabCol(bundle.getString("poshlina"), 80);
        tovarPoshlina.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarPoshlinaString()
        ));
        tovarPoshlina.setSortable(false);

        TableColumn<TovarZakaz, String> tovarDDP = creatTabCol(bundle.getString("ddp"), 80);
        tovarDDP.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarDDPStr()
        ));
        tovarDDP.setSortable(false);


        TableColumn<TovarZakaz, String> tovarSana = creatTabCol(bundle.getString("sana"), 100);
        tovarSana.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarSana().format(dateFormatter)
        ));
        tovarSana.setSortable(false);


        TableColumn<TovarZakaz, String> tovarUlchov = creatTabCol(bundle.getString("ulchov"), 70);
        tovarUlchov.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarUlchovBirligi()
        ));
        tovarUlchov.setSortable(false);


        TableColumn<TovarZakaz, String> tovarKomment = creatTabCol(bundle.getString("komment"), 150);
        tovarKomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKomment()
        ));
        tovarKomment.setSortable(false);

        tovarQushimcha.getColumns().addAll(tovarTrans, tovarAksiz, tovarPoshlina, tovarDDP);

        projectReviewTable.getColumns().clear();
        projectReviewTable.getColumns().addAll(tovarTR, tovarKod, tovarSoni, tovarNomi,
                tovarModel, tovarIshCh, tovarNarxi, tovarNarxiTuri, tovarQushimcha,
                tovarSana, tovarUlchov, tovarKomment);

    }


    <S, T> TableColumn<S, T> creatTabCol(String title, double prefWidth) {

        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setMinWidth(prefWidth);
        newColumn.setStyle("-fx-alignment: CENTER");
        return newColumn;
    }

    <S, T> TableColumn<S, T> creatTabCol(String title) {

        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);

        newColumn.setMinWidth(120);
        newColumn.setPrefWidth(150);
        newColumn.setMaxWidth(200);
        newColumn.setStyle("-fx-alignment: CENTER");

        return newColumn;
    }


    <S, T> TableColumn<S, T> creatTabCol(String title, double minWidth, double prefWidth,
                                         double maxWidth) {

        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);

        newColumn.setMinWidth(minWidth);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setMaxWidth(maxWidth);
        newColumn.setStyle("-fx-alignment: CENTER");


        return newColumn;
    }

    @FXML
    private void cancel(ActionEvent event) {
        this.projectReviewTable.getScene().getWindow().hide();
    }




}
