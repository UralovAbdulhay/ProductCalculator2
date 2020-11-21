package sample.Controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Classes.MyIntegerStringConverter;
import sample.Moodles.PriseList;
import sample.Moodles.Stavkalar;
import sample.Moodles.TovarZakaz;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;


public class ControllerTable implements Initializable {


    @FXML
    private TableView<PriseList> mainTable;

    @FXML
    private TableView<TovarZakaz> helperTable;

    @FXML
    private Label uzsSumm;

    @FXML
    private Label usdSumm;

    @FXML
    private Label rubSumm;

    @FXML
    private Label ddpBezLabel;

    @FXML
    private Label ddpSLabel;

    @FXML
    private Label eurSumm;

    @FXML
    private TextField eurTf;

    @FXML
    private TextField usdTf;

    @FXML
    private TextField rubTf;

    @FXML
    private VBox tableVBox;

    @FXML
    private TextField tovarQidir;

    @FXML
    private ToggleButton kursatBt;

    @FXML
    private HBox passHb;

    @FXML
    private Button ok_bt;

    @FXML
    private Button nextExportBt;

    @FXML
    private Button orderCanBt;

    @FXML
    private JFXButton altHisobla;

    @FXML
    private JFXToggleButton ddpSwitch;

    @FXML
    private PasswordField passwordContr;


    static ObservableList<TableColumn> privateColumns = FXCollections.observableArrayList();
    static double zakazSumma = 0;
    static ObservableList<TextField> kursLar = FXCollections.observableArrayList();
    static ObservableList<Label> summLable = FXCollections.observableArrayList();

    private ControllerOyna controllerOyna;
    private BorderPane borderPane;

    private Stage mainStage;
    private static boolean kursUrnataymi;
    private TableColumn nds2Stavka;
    private TableColumn<TovarZakaz, Double> zNDS2Nar;
    private TableColumn<TovarZakaz, Double> nds2Summa;
    private TableColumn<TovarZakaz, Double> zNDS1Narxi;
    private TableColumn<TovarZakaz, Double> zCIPnarxUzs;
    private static boolean switchIsSelected;

    private Stavkalar stavkalar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stavkalar = new Stavkalar();
        if (!kursUrnataymi) {

            usdTf.setText(new Stavkalar().getStUSD_USZ() + "");
            rubTf.setText(new Stavkalar().getStUSD_RUB() + "");
            eurTf.setText(new Stavkalar().getStUSD_EUR() + "");

            TovarZakaz.zakUsdUsz = new Stavkalar().getStUSD_USZ();
            TovarZakaz.zakRubUsz = new Stavkalar().getStUSD_RUB();
            TovarZakaz.zakEurUsz = new Stavkalar().getStUSD_EUR();
            kursUrnataymi = true;
        } else {
            usdTf.setText(TovarZakaz.zakUsdUsz + "");
            rubTf.setText(TovarZakaz.zakRubUsz + "");
            eurTf.setText(TovarZakaz.zakEurUsz + "");
        }

        BirinchiTable();
        zakazTazble();
        mainTable.setEditable(true);
        helperTable.setStyle("-fx-font-size: 14");
        mainTable.setStyle("-fx-font-size: 14");

        setDisableNextExportBt();

        tovarQidir.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                tovarQidirish(PriseList.priseLists);
            }
        });

        kursLar = FXCollections.observableArrayList(usdTf, rubTf, eurTf);

        summLable = FXCollections.observableArrayList(uzsSumm, usdSumm, rubSumm, eurSumm);

        onActionPriseBt();

        Pattern decimalPattern = Pattern.compile("\\d*(\\.\\d{0,4})?");

        Pattern integerP = Pattern.compile("[0-9]*");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null;
            }
        };

        usdTf.setTextFormatter(new TextFormatter<Double>(filter));
        rubTf.setTextFormatter(new TextFormatter<Double>(filter));
        eurTf.setTextFormatter(new TextFormatter<Double>(filter));

        helperTable.setRowFactory(tableView -> {
            final TableRow<TovarZakaz> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Edit");
            MenuItem removeItem = new MenuItem("Delete");
            removeItem.setOnAction(e -> deleteZakazItem());
            editItem.setOnAction(this::editZakazItem);
            rowMenu.getItems().addAll(editItem, removeItem);
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null)
            );
            return row;
        });

        switchIsSelected = ddpSwitch.isSelected();
        switchAction();

        EditTovar.ishla = false;

    }

    void setControllerOyna(ControllerOyna controllerOyna) {
        this.controllerOyna = controllerOyna;
    }


    private void BirinchiTable() {

        mainTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        TableColumn<PriseList, Integer> tovarTR = new TableColumn<>("№");
        tovarTR.setPrefWidth(35);
        tovarTR.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTr()
        ));
        tovarTR.setResizable(false);
        tovarTR.impl_setReorderable(false);
        tovarTR.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, Button> tovarAddBt = new TableColumn<>("Add");
        tovarAddBt.setPrefWidth(60);
        tovarAddBt.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarAdd()
        ));
        tovarAddBt.setSortable(false);
        tovarAddBt.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, Integer> tovarAddCount = new TableColumn<>("Soni");
        tovarAddCount.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getAddCount()
        ));
        tovarAddCount.setPrefWidth(50);
        tovarAddCount.setSortable(false);
        tovarAddCount.setStyle("-fx-alignment: CENTER");

        tovarAddCount.setCellFactory(TextFieldTableCell.forTableColumn(new MyIntegerStringConverter()));
        tovarAddCount.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<PriseList, Integer>>) event -> {
            mainTable.getItems().get(event.getTablePosition().getRow()).setAddCount(castInt(event));
            mainTable.refresh();
        });


        TableColumn<PriseList, String> tovarId = new TableColumn<>("Kod");
        tovarId.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKod()
        ));
        tovarId.setMinWidth(100);
        tovarId.setPrefWidth(120);
        tovarId.setMaxWidth(150);
        tovarId.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarNomi = new TableColumn<>("Nomi");
        tovarNomi.setPrefWidth(300);
        tovarNomi.setMaxWidth(500);
        tovarNomi.setMinWidth(200);
        tovarNomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNomi()
        ));
        tovarNomi.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarModel = new TableColumn<>("Model");
        tovarModel.setMinWidth(100);
        tovarModel.setPrefWidth(120);
        tovarModel.setMaxWidth(150);
        tovarModel.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarModel()
        ));
        tovarModel.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarIshCh = new TableColumn<>("Maker");
        tovarIshCh.setMinWidth(100);
        tovarIshCh.setPrefWidth(120);
        tovarIshCh.setMaxWidth(150);
        tovarIshCh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarIshlabChiqaruvchi().getName()
        ));
        tovarIshCh.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, Double> tovarNarxi = new TableColumn<>("Narxi");
        tovarNarxi.setMinWidth(100);
        tovarNarxi.setPrefWidth(120);
        tovarNarxi.setMaxWidth(150);
        tovarNarxi.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarNarxi()
        ));
        tovarNarxi.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarNarxiTuri = new TableColumn<>("Valyuta");
        tovarNarxiTuri.setResizable(false);
        tovarNarxiTuri.setSortable(false);
        tovarNarxiTuri.setMinWidth(60);
        tovarNarxiTuri.setPrefWidth(60);
        tovarNarxiTuri.setStyle("-fx-alignment: CENTER");
        tovarNarxiTuri.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxTuri()
        ));


        TableColumn tovarQushimcha = new TableColumn("Qushimcha");
        tovarQushimcha.setPrefWidth(150);
        tovarQushimcha.setResizable(false);


        TableColumn<PriseList, String> tovarTrans = new TableColumn<>("Transport");
        tovarTrans.setSortable(false);
        tovarTrans.setMinWidth(60);
        tovarTrans.setPrefWidth(60);
        tovarTrans.setStyle("-fx-alignment: CENTER");
        tovarTrans.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarTransportNarxiString()
        ));


        TableColumn<PriseList, String> tovarAksiz = new TableColumn<>("Aksiz");
        tovarAksiz.setSortable(false);
        tovarAksiz.setMinWidth(60);
        tovarAksiz.setPrefWidth(60);
        tovarAksiz.setStyle("-fx-alignment: CENTER");
        tovarAksiz.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarAksizString()
        ));


        TableColumn<PriseList, String> tovarPoshlina = new TableColumn<>("Poshlina");
        tovarPoshlina.setSortable(false);
        tovarPoshlina.setMinWidth(60);
        tovarPoshlina.setPrefWidth(60);
        tovarPoshlina.setStyle("-fx-alignment: CENTER");
        tovarPoshlina.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarPoshlinaString()
        ));

        TableColumn<PriseList, Double> tovarDDP = new TableColumn<>("DDP");
        tovarDDP.setResizable(false);
        tovarDDP.setSortable(false);
        tovarDDP.setMinWidth(60);
        tovarDDP.setPrefWidth(60);
        tovarDDP.setStyle("-fx-alignment: CENTER");
        tovarDDP.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarDDP()
        ));


        TableColumn<PriseList, LocalDate> tovarSana = new TableColumn<>("Sana");
        tovarSana.setResizable(false);
        tovarSana.setMinWidth(100);
        tovarSana.setPrefWidth(100);
        tovarSana.setStyle("-fx-alignment: CENTER");
        tovarSana.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarSana()
        ));

        TableColumn<PriseList, String> tovarUlchov = new TableColumn<>("o'lchov");
        tovarUlchov.setResizable(false);
        tovarUlchov.setSortable(false);
        tovarUlchov.setMinWidth(70);
        tovarUlchov.setPrefWidth(70);
        tovarUlchov.setStyle("-fx-alignment: CENTER");
        tovarUlchov.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarUlchovBirligi()
        ));


        TableColumn<PriseList, String> tovarKomment = creatTabCol("Komment", 150);
        tovarKomment.setSortable(false);
        tovarKomment.setMinWidth(200);
        tovarKomment.setPrefWidth(200);
        tovarKomment.setStyle("-fx-alignment: CENTER");
        tovarKomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKomment()
        ));


        tovarQushimcha.getColumns().addAll(tovarTrans, tovarAksiz, tovarPoshlina, tovarDDP);


        mainTable.getColumns().addAll(tovarTR, tovarAddBt, tovarAddCount, tovarId, tovarNomi,
                tovarModel, tovarIshCh, tovarNarxi, tovarNarxiTuri, tovarQushimcha,
                tovarSana, tovarUlchov, tovarKomment);


        mainTable.setItems(PriseList.priseLists);
        mainTable.setTableMenuButtonVisible(true);


    }

    private void onActionPriseBt() {
        for (PriseList priseList : PriseList.priseLists) {
            priseList.getTovarAdd().setOnAction(this::orderAdd);
        }
    }


    private <S, T> TableColumn<S, T> creatTabCol(String title, double minWidth, double prefWidth,
                                                 double maxWidth) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setMinWidth(minWidth);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setMaxWidth(maxWidth);
        return newColumn;
    }

    private <S, T> TableColumn<S, T> creatTabCol(String title, double minWidth, double prefWidth,
                                                 double maxWidth, boolean isSortable) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setMinWidth(minWidth);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setMaxWidth(maxWidth);
        newColumn.setSortable(isSortable);
        return newColumn;
    }

    private <S, T> TableColumn<S, T> creatTabCol(String title) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setMinWidth(100);
        newColumn.setPrefWidth(150);
        newColumn.setMaxWidth(150);
        return newColumn;
    }

    private <S, T> TableColumn<S, T> creatTabCol(String title, boolean isSortable) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setPrefWidth(150);
        newColumn.setSortable(isSortable);
        return newColumn;
    }


    private <S, T> TableColumn<S, T> creatTabCol(String title, double prefWidth) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setPrefWidth(prefWidth);
        return newColumn;
    }


    private <S, T> TableColumn<S, T> creatTabCol(String title, double prefWidth, boolean isSortable) {
        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setSortable(isSortable);
        return newColumn;
    }


    //text field ni tozalash
    @FXML
    private void tozalaQidir() {
        tovarQidir.clear();
    }


    // qidirish

    @FXML
    private void tovarQidirish(ObservableList<PriseList> tovarTableItems) {

        String natija = tovarQidir.getText();

        ObservableList<PriseList> natijaTovar = FXCollections.observableArrayList();

        if (natija.isEmpty()) {
            mainTable.setItems(tovarTableItems);
        } else {

            for (int i = 0; i < tovarTableItems.size(); i++) {

                if (PriseList.priseLists.get(i).getTovarNomi().trim().toLowerCase().contains(tovarQidir.getText().trim().toLowerCase())
                        || PriseList.priseLists.get(i).getTovarIshlabChiqaruvchi().getName().toLowerCase().trim().contains(tovarQidir.getText().trim().toLowerCase())
                ) {
                    natijaTovar.add(tovarTableItems.get(i));
                }
            }
            mainTable.setItems(natijaTovar);
            mainTable.refresh();
        }
    }


    // Order list ga elementlarni qo'shish


    private void orderAdd(ActionEvent event) {

        for (int i = 0; i < mainTable.getItems().size(); i++) {
            if (mainTable.getItems().get(i).getTovarAdd().isArmed()) {
                boolean bormi = false;
                for (int j = 0; j < TovarZakaz.tovarZakazList.size(); j++) {
                    if (mainTable.getItems().get(i).getTovarId() == (TovarZakaz.tovarZakazList.get(j).getTovarId())) {
                        TovarZakaz.tovarZakazList.get(j).setZakazSoni(TovarZakaz.tovarZakazList.get(j).getZakazSoni() +
                                mainTable.getItems().get(i).getAddCount());
                        bormi = true;
                        break;
                    }
                }
                if (!bormi) {
                    TovarZakaz.tovarZakazList.add(new TovarZakaz(mainTable.getItems().get(i)));
                    TovarZakaz.tovarZakazList.forEach(
                            e -> {
                                e.getZakazUzgartir().setOnMouseClicked(
                                        event1 -> orderUzgartir()
                                );
                            }
                    );
                    TovarZakaz.setTr();
                    onActionAddBt();
                }
                mainTable.getItems().get(i).setAddCount(1);
            }
        }

        setDisableNextExportBt();
        summaHisobla();
        mainTable.refresh();
        helperTable.refresh();

    }


     void setDisableNextExportBt() {
        if (TovarZakaz.tovarZakazList.size() != 0) {
            orderCanBt.setDisable(false);
            nextExportBt.setDisable(false);
        } else {
            nextExportBt.setDisable(true);
            orderCanBt.setDisable(true);
        }
    }

    private void onActionAddBt() {
        for (TovarZakaz tovarZakaz : TovarZakaz.tovarZakazList) {
            tovarZakaz.getZakazUchirishBt().setOnAction(event -> {
                orderBekor();
            });
        }
    }


    public void orderUzgartir() {

        for (int i = 0; i < TovarZakaz.tovarZakazList.size(); i++) {
            TovarZakaz.tovarZakazList.get(i).setZakazSoni(
                    TovarZakaz.tovarZakazList.get(i).getZakazUzgartir().getValue());
        }
        TovarZakaz.tovarZakazList.forEach(TovarZakaz::zakazHisobla);
        summaHisobla();
        helperTable.refresh();
    }


    public void summaHisobla() {
        double localSumm = 0;

        if (switchIsSelected) {
            for (int i = 0; i < TovarZakaz.tovarZakazList.size(); i++) {
                localSumm += TovarZakaz.tovarZakazList.get(i).getZakazNDS2liSumm();
            }
        } else {
            for (int i = 0; i < TovarZakaz.tovarZakazList.size(); i++) {
                localSumm += TovarZakaz.tovarZakazList.get(i).getZakazDDPsumm();
            }
        }


        DecimalFormat format = new DecimalFormat("#,000.000");
        zakazSumma = localSumm;


        if (TovarZakaz.tovarZakazList.size() != 0) {

            summLable.get(0).setText(format.format(zakazSumma));
            summLable.get(1).setText(format.format(zakazSumma
                    / TovarZakaz.zakUsdUsz));
            summLable.get(2).setText(format.format(zakazSumma
                    / TovarZakaz.zakRubUsz));
            summLable.get(3).setText(format.format(zakazSumma
                    / TovarZakaz.zakEurUsz));

        } else {

            summLable.forEach(e -> e.setText((format.format(0))));

        }
    }


    @FXML
    void orderCancel() {

        TovarZakaz.tovarZakazList.removeAll(TovarZakaz.tovarZakazList);
        summaHisobla();
        setDisableNextExportBt();
        helperTable.refresh();
    }


    public void orderBekor() {

        for (int i = 0; i < TovarZakaz.tovarZakazList.size(); i++) {
            if (TovarZakaz.tovarZakazList.get(i).getZakazUchirishBt().isArmed()) {
                TovarZakaz.tovarZakazList.remove(i);
            }
        }

        for (int i = 0; i < TovarZakaz.tovarZakazList.size(); i++) {
            TovarZakaz.tovarZakazList.get(i).setTr(i + 1);
        }
        summaHisobla();
        setDisableNextExportBt();
        helperTable.refresh();
    }


    private void zakazTazble() {


        TableColumn<TovarZakaz, Integer> zTr = new TableColumn<>("№");
        zTr.setResizable(false);
        zTr.setPrefWidth(35);
        zTr.setStyle("-fx-alignment: CENTER");
        zTr.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTr()
        ));


        TableColumn<TovarZakaz, Spinner> zKamBt = new TableColumn<>("O'zgartir");
        zKamBt.setSortable(false);
        zKamBt.setPrefWidth(100);
        zKamBt.setStyle("-fx-alignment: CENTER");
        zKamBt.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazUzgartir()
        ));

        TableColumn<TovarZakaz, JFXButton> zUchBt = new TableColumn<>("O'chir");
        zUchBt.setSortable(false);
        zUchBt.setPrefWidth(100);
        zUchBt.setStyle("-fx-alignment: CENTER");
        zUchBt.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazUchirishBt()
        ));


        TableColumn<TovarZakaz, String> zNomi = new TableColumn<>("Nomi");
        zNomi.setSortable(false);
        zNomi.setPrefWidth(200);
        zNomi.setMinWidth(200);
        zNomi.setStyle("-fx-alignment: CENTER");
        zNomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNomi()
        ));


        TableColumn<TovarZakaz, String> zIshCh = creatTabCol("Maker");
        zIshCh.setStyle("-fx-alignment: CENTER");
        zIshCh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarIshlabChiqaruvchi().getName()
        ));


        TableColumn<TovarZakaz, String> zModel = creatTabCol("Model");
        zModel.setStyle("-fx-alignment: CENTER");
        zModel.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarModel()
        ));


        TableColumn<TovarZakaz, String> zTavId = creatTabCol("Kod");
        zTavId.setStyle("-fx-alignment: CENTER");
        zTavId.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKod()
        ));


        TableColumn<TovarZakaz, String> zNarxiTuri = creatTabCol("Valyuta", 60, false);
        zNarxiTuri.setResizable(false);
        zNarxiTuri.setStyle("-fx-alignment: CENTER");
        zNarxiTuri.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxTuri()
        ));


        TableColumn<TovarZakaz, Double> ztavEXWNarxi = creatTabCol("EXW narxi");
        ztavEXWNarxi.setStyle("-fx-alignment: CENTER");
        ztavEXWNarxi.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarNarxi()
        ));


        TableColumn<TovarZakaz, Double> ztavEXWSumm = creatTabCol("EXW summasi");
        ztavEXWSumm.setStyle("-fx-alignment: CENTER");
        ztavEXWSumm.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazSummaExw()
        ));


        TableColumn zTavTransSt = new TableColumn("Transport natija");
        zTavTransSt.setSortable(false);
        zTavTransSt.setResizable(false);


        TableColumn<TovarZakaz, Double> zTavTrans = creatTabCol((stavkalar.getStTrans()) + "");
        zTavTrans.setResizable(false);
        zTavTrans.setSortable(false);
        zTavTrans.setStyle("-fx-alignment: CENTER");
        zTavTrans.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazTransProNatija()
        ));

        zTavTransSt.getColumns().addAll(zTavTrans);


        TableColumn<TovarZakaz, Double> ztavTransSumm = creatTabCol("Transport summasi", 150);
        ztavTransSumm.setStyle("-fx-alignment: CENTER");
        ztavTransSumm.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazTransSumm()
        ));


        TableColumn<TovarZakaz, Double> ztavTransLiSumm = creatTabCol("TransportLi summasi", 150);
        ztavTransLiSumm.setStyle("-fx-alignment: CENTER");
        ztavTransLiSumm.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazTransLiSumma()
        ));


        ///**********************************14///

        TableColumn<TovarZakaz, Double> zCIPst = creatTabCol("*CIP stavkasi");
        zCIPst.setResizable(false);
        zCIPst.setVisible(false);
        zCIPst.setStyle("-fx-alignment: CENTER");
        zCIPst.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getStCIP()
        ));


        TableColumn<TovarZakaz, Double> zCIPnarxi = creatTabCol("*CIP narxi (USD)");
        zCIPnarxi.setVisible(false);
        zCIPnarxi.setStyle("-fx-alignment: CENTER");
        zCIPnarxi.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazCIPNarxiUSD()
        ));

        TableColumn<TovarZakaz, Double> zCIPsumm = creatTabCol("*CIP summa (USD)");
        zCIPsumm.setVisible(false);
        zCIPsumm.setStyle("-fx-alignment: CENTER");
        zCIPsumm.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazCIPSummUSD()
        ));

        TableColumn zCIPuzsLab = new TableColumn("*CIP narxi (UZS)");
        zCIPuzsLab.setMinWidth(100);
        zCIPuzsLab.setPrefWidth(120);
        zCIPuzsLab.setMaxWidth(150);
        zCIPuzsLab.setVisible(false);
        zCIPuzsLab.setResizable(false);


        zCIPnarxUzs = creatTabCol((TovarZakaz.zakUsdUsz) + " so'm");
        zCIPnarxUzs.setResizable(false);
        zCIPnarxUzs.setStyle("-fx-alignment: CENTER");
        zCIPnarxUzs.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazCIPNarxiUSZ()
        ));
        zCIPnarxUzs.setVisible(false);
        zCIPuzsLab.getColumns().addAll(zCIPnarxUzs);


        TableColumn zBojSt = new TableColumn("*Bojlar narxi");
        zBojSt.setVisible(false);
        zBojSt.setPrefWidth(150);
        zBojSt.setResizable(false);
        zBojSt.setStyle("-fx-alignment: CENTER");


        TableColumn<TovarZakaz, Double> zBoj = creatTabCol((stavkalar.getStBojxona() * 100) + " %");
        zBoj.setVisible(false);
        zBoj.setPrefWidth(150);
        zBoj.setResizable(false);
        zBoj.setStyle("-fx-alignment: CENTER");
        zBoj.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazBojYigini()
        ));
        zBojSt.getColumns().addAll(zBoj);


        TableColumn<TovarZakaz, Double> ztovarPoshlinaOr = creatTabCol("*Poshlina", 60);
        ztovarPoshlinaOr.setResizable(false);
        ztovarPoshlinaOr.setVisible(false);
        ztovarPoshlinaOr.setStyle("-fx-alignment: CENTER");
        ztovarPoshlinaOr.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarPoshlina()
        ));


        TableColumn<TovarZakaz, Double> zSumPosh = creatTabCol("*Poshlina summasi");
        zSumPosh.setVisible(false);
        zSumPosh.setStyle("-fx-alignment: CENTER");
        zSumPosh.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazPoshlinaSumm()
        ));


        TableColumn<TovarZakaz, Double> ztovarAksizOr = creatTabCol("*Aksiz", 50);
        ztovarAksizOr.setResizable(false);
        ztovarAksizOr.setVisible(false);
        ztovarAksizOr.setStyle("-fx-alignment: CENTER");
        ztovarAksizOr.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarAksiz()
        ));


        TableColumn<TovarZakaz, Double> zSumAk = creatTabCol("*Aksiz summasi");
        zSumAk.setVisible(false);
        zSumAk.setStyle("-fx-alignment: CENTER");
        zSumAk.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazAksizSumm()
        ));


        TableColumn zNDS1 = creatTabCol("*NDS 1 narxi");
        zNDS1.setResizable(false);
        zNDS1.setSortable(false);
        zNDS1.setVisible(false);


        zNDS1Narxi = creatTabCol((stavkalar.getStNDS1S() * 100) + " %");
        zNDS1Narxi.setResizable(false);
        zNDS1Narxi.setVisible(false);
        zNDS1Narxi.setStyle("-fx-alignment: CENTER");
        zNDS1Narxi.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazNDS1Narxi()
        ));
        zNDS1.getColumns().addAll(zNDS1Narxi);


        TableColumn<TovarZakaz, Double> zKelNar = creatTabCol("*Kelish narxi");
        zKelNar.setVisible(false);
        zKelNar.setStyle("-fx-alignment: CENTER");
        zKelNar.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazKelishNarxi()
        ));


        TableColumn<TovarZakaz, Double> zKelSum = creatTabCol("*Kelish summasi");
        zKelSum.setVisible(false);
        zKelSum.setStyle("-fx-alignment: CENTER");
        zKelSum.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazKelishSumm()
        ));


        TableColumn<TovarZakaz, Double> zDDPst = creatTabCol("*DDP stavka");
        zDDPst.setVisible(false);
        zDDPst.setStyle("-fx-alignment: CENTER");
        zDDPst.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarDDP()
        ));


        privateColumns.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs,
                zBojSt, zBoj, ztovarPoshlinaOr, zSumPosh, ztovarAksizOr,
                zSumAk, zNDS1, zNDS1Narxi, zKelNar, zKelSum, zDDPst);

        ///********************************25///


        TableColumn<TovarZakaz, Double> zDDPNar = creatTabCol("DDP narxi");
        zDDPNar.setStyle("-fx-alignment: CENTER");
        zDDPNar.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazDDPnarxi()
        ));


        TableColumn<TovarZakaz, Double> zDDPSum = creatTabCol("DDP summasi");
        zDDPSum.setStyle("-fx-alignment: CENTER");
        zDDPSum.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazDDPsumm()
        ));


        nds2Stavka = new TableColumn("NDS 2 stavka");
        nds2Stavka.setPrefWidth(150);
        nds2Stavka.setResizable(false);


        zNDS2Nar = new TableColumn<>((stavkalar.getStNDS2() * 100) + " %");
        zNDS2Nar.setPrefWidth(150);
        zNDS2Nar.setResizable(false);
        zNDS2Nar.setStyle("-fx-alignment: CENTER");
        zNDS2Nar.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazNDS2liNarxi()
        ));
        zNDS2Nar.setVisible(false);
        nds2Stavka.getColumns().addAll(zNDS2Nar);


        nds2Summa = creatTabCol("NDS 2 li summasi");
        nds2Summa.setStyle("-fx-alignment: CENTER");
        nds2Summa.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazNDS2liSumm()
        ));


        helperTable.getColumns().addAll(
                zTr, zKamBt, zUchBt, zNomi, zIshCh, zModel, zTavId, zNarxiTuri,
                ztavEXWNarxi, ztavEXWSumm, zTavTransSt, ztavTransSumm,
                ztavTransLiSumm, zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zBojSt,
                ztovarPoshlinaOr, zSumPosh, ztovarAksizOr, zSumAk,
                zNDS1, zKelNar, zKelSum, zDDPst, zDDPNar, zDDPSum,
                nds2Stavka, nds2Summa
        );


        helperTable.setItems(TovarZakaz.tovarZakazList);
        helperTable.refresh();
    }

// password fieldni kursatish

    public void showDialog() throws IOException {

        if (kursatBt.isSelected()) {
            passHb.setVisible(true);
            passwordContr.setText("");
            MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.EYE_OFF);
            iconView.setGlyphSize(20);
            iconView.setFill(Paint.valueOf("#2d3b9d"));
            kursatBt.setGraphic(iconView);

            passwordContr.setStyle("-fx-border-width: 1.5");
            passwordContr.setStyle("-fx-border-color:  #861386");
            ok_bt.setStyle("-fx-border-width: 1.5");
            ok_bt.setStyle("-fx-border-color:  #861386");

        } else {
            passHb.setVisible(false);
            ustunKursat();
            passwordContr.setText("");
            MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.EYE);
            iconView.setGlyphSize(20);
            iconView.setFill(Paint.valueOf("#2d3b9d"));
            kursatBt.setGraphic(iconView);


        }
    }

    // parolni tekshirish

    public void parolTugrimi() {

        if (passwordContr.getText().equals("12345")) {
            passwordContr.setText("");
            passHb.setVisible(false);
            ustunKursat();
        } else {
            passwordContr.setStyle("-fx-background-color: red");
            ok_bt.setStyle("-fx-background-color: red");
        }
    }

    // default qiymat uranatish, yoki bush bugandagi qiymatni urnatish

    public void tekshir() {

        if (!passwordContr.getText().trim().isEmpty()) {
            ok_bt.setDisable(false);
        } else {
            ok_bt.setDisable(true);
            passwordContr.setStyle("-fx-border-width: 1.5");
            passwordContr.setStyle("-fx-border-color:  #861386");
            ok_bt.setStyle("-fx-border-width: 1.5");
            ok_bt.setStyle("-fx-border-color:  #861386");

        }

    }

    // private ustunlarni kusatish
    public void ustunKursat() {


        for (TableColumn privateColumn : privateColumns) {

            privateColumn.setVisible(kursatBt.isSelected());

        }
    }


    @FXML
    private void showProjectView(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/projectView.fxml"));

        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Create Project");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(mainStage);
        stage.show();

        AddProject controller = loader.getController();
        controller.setOwnerStage(mainStage);
        controller.setControllerTable(this);
        controller.setStavkalar(stavkalar);
        int typeCol = 0;
        typeCol = ddpSwitch.isSelected() ? 1 : 0;
        controller.setPrHisobTuri(typeCol, TovarZakaz.tovarZakazList);

    }


    private double castDouble(TableColumn.CellEditEvent event) {

        try {
            return Double.parseDouble(String.valueOf(event.getNewValue()));
        } catch (NumberFormatException e) {
            return Double.parseDouble(String.valueOf(event.getOldValue()));
        }
    }

    private int castInt(TableColumn.CellEditEvent event) {

        try {
            return Integer.parseInt(String.valueOf(event.getNewValue()));
        } catch (NumberFormatException e) {
            return Integer.parseInt(String.valueOf(event.getOldValue()));
        }
    }

    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }


    private void editZakazItem(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/zakazEdit.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        assert vBox != null;
        Scene scene = new Scene(vBox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Edit Product");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.setResizable(false);
        stage.show();

        ZakazEditViewController controller = loader.getController();
        controller.setTovarZakaz(helperTable.getSelectionModel().getSelectedItem());
        controller.init();
        controller.setStage(stage);
        controller.setControllerTable(this);

    }

    private void deleteZakazItem() {
        TovarZakaz.tovarZakazList.remove(helperTable.getSelectionModel().getSelectedItem());
        TovarZakaz.setTr();
        helperTable.refresh();
        summaHisobla();
    }

    public void refreshHelperTable() {
        helperTable.refresh();
    }

    @FXML
    private void kursniYangila() {

        try {
            TovarZakaz.zakRubUsz = Double.parseDouble(rubTf.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            TovarZakaz.zakRubUsz = 0;
        }
        try {
            TovarZakaz.zakEurUsz = Double.parseDouble(eurTf.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            TovarZakaz.zakEurUsz = 0;
        }
        try {
            TovarZakaz.zakUsdUsz = Double.parseDouble(usdTf.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            TovarZakaz.zakUsdUsz = 0;
        }

        TovarZakaz.tovarZakazList.forEach(TovarZakaz::zakazHisobla);
        zCIPnarxUzs.setText((TovarZakaz.zakUsdUsz) + " so'm");
        helperTable.refresh();

    }

    @FXML
    private void kursYangila_2() {

        usdTf.setText(new Stavkalar().getStUSD_USZ() + "");
        rubTf.setText(new Stavkalar().getStUSD_RUB() + "");
        eurTf.setText(new Stavkalar().getStUSD_EUR() + "");
        kursniYangila();
    }

    @FXML
    private void switchAction() {
        switchIsSelected = ddpSwitch.isSelected();
        TovarZakaz.selected = ddpSwitch.isSelected();
        if (ddpSwitch.isSelected()) {
            ddpSLabel.setStyle("-fx-background-radius: 10; -fx-background-color: #0EBD00");
            ddpBezLabel.setStyle("-fx-background-color: white");
            nds2Stavka.setVisible(true);
            zNDS2Nar.setVisible(true);
            nds2Summa.setVisible(true);
//            stavkalar.setStNDS1S(Stavkalar.stavkaShablons.stream().filter(e -> e.getKod().equals("nds1s")).findFirst().get().getQiymat());
            zNDS1Narxi.setText((stavkalar.getStNDS1S() * 100) + " %");


        } else {
            ddpBezLabel.setStyle("-fx-background-radius: 10; -fx-background-color: #0EBD00");
            ddpSLabel.setStyle("-fx-background-color: white");
            nds2Stavka.setVisible(false);
            zNDS2Nar.setVisible(false);
            nds2Summa.setVisible(false);
//            stavkalar.setStNDS1S(Stavkalar.stavkaShablons.stream().filter(e -> e.getKod().equals("nds1bez")).findFirst().get().getQiymat());
            zNDS1Narxi.setText((stavkalar.getStNDS1Bez() * 100) + " %");

        }
        summaHisobla();
        TovarZakaz.tovarZakazList.forEach(TovarZakaz::zakazHisobla);
    }

}


