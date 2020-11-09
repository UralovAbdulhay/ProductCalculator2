package sample.Controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.Moodles.PriseList;
import sample.Moodles.Project;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ReviewProjectController implements Initializable {

    @FXML
    private TableView projectReviewTable;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initTable();
    }

    public void setProject(Project project) {
        this.project = project;

        ObservableList<PriseList> priseList = observableArrayList();
        this.project.getProjectZakazList().forEach(e-> priseList.add(new PriseList(e.getTovar())));
        initWindow();
        projectReviewTable.setItems(priseList);
    }

    public void initWindow() {

        nameLab.setText(project.getPrNomi());
        startDateLab.setText(project.getBoshlanganVaqt().format(dateFormatter));
        endDateLab.setText(project.getTugashVaqti().format(dateTimeFormatter));
        formulaLab.setText(project.getPrFormula());
        rahbarLab.setText(project.getPrRaxbar().getIsm());
        masulLab.setText(project.getPrMasul().getIsm());
        clientLab.setText(project.getPrClient().getName());

        if (project.isDone()) {
            doneDateLab.setText(project.getPrTugallanganVaqti().format(dateTimeFormatter));
        } else {
            doneDateLab.setText("--.--.----");
        }

    }

    private void initTable() {


        TableColumn<PriseList, Integer> tovarTR = creatTabCol("№", 35);
        tovarTR.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTr()
        ));
        tovarTR.setResizable(false);
        tovarTR.impl_setReorderable(false);


        TableColumn<PriseList, String> tovarKod = creatTabCol("ID");
        tovarKod.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKod()
        ));


        TableColumn<PriseList, String> tovarNomi = creatTabCol("Nomi", 200, 200, 500);
        tovarNomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNomi()
        ));


        TableColumn<PriseList, String> tovarModel = creatTabCol("Model", 200, 200, 500);
        tovarModel.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarModel()
        ));


        TableColumn<PriseList, String> tovarIshCh = creatTabCol("Maker", 200, 200, 500);
        tovarIshCh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarIshlabChiqaruvchi().getName()
        ));


        TableColumn<PriseList, Double> tovarNarxi = creatTabCol("Narxi");
        tovarNarxi.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarNarxi()
        ));


        TableColumn<PriseList, String> tovarNarxiTuri = creatTabCol("Valyuta", 80);
        tovarNarxiTuri.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxTuri()
        ));
        tovarNarxiTuri.setResizable(false);
        tovarNarxiTuri.setSortable(false);


        TableColumn tovarQushimcha = creatTabCol("Qushimcha");
        tovarQushimcha.setResizable(false);


        TableColumn<PriseList, String> tovarTrans = creatTabCol("Transport", 80);
        tovarTrans.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarTransportNarxiString()
        ));
        tovarTrans.setSortable(false);


        TableColumn<PriseList, String> tovarAksiz = creatTabCol("Aksiz", 80);
        tovarAksiz.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarAksizString()
        ));
        tovarAksiz.setSortable(false);


        TableColumn<PriseList, String> tovarPoshlina = creatTabCol("Poshlina", 80);
        tovarPoshlina.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarPoshlinaString()
        ));
        tovarPoshlina.setSortable(false);

        TableColumn<PriseList, Double> tovarDDP = creatTabCol("DDP", 80);
        tovarDDP.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarDDP()
        ));
        tovarDDP.setSortable(false);


        TableColumn<PriseList, String> tovarSana = creatTabCol("Sana", 100);
        tovarSana.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarSana().format(dateFormatter)
        ));
        tovarSana.setSortable(false);


        TableColumn<PriseList, String> tovarUlchov = creatTabCol("o'lchov", 70);
        tovarUlchov.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarUlchovBirligi()
        ));
        tovarUlchov.setResizable(false);
        tovarUlchov.setSortable(false);


        TableColumn<PriseList, String> tovarKomment = creatTabCol("Komment", 150);
        tovarKomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKomment()
        ));
        tovarKomment.setSortable(false);

        projectReviewTable.getColumns().clear();
        projectReviewTable.getColumns().addAll( tovarTR, tovarKod, tovarNomi,
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

}
