
package sample.Controllers;


import com.jfoenix.controls.JFXButton;
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
import sample.Classes.Connections.PasswordsConnections;
import sample.Classes.MyIntegerStringConverter;
import sample.Moodles.Password;
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
    private JFXButton showPrXisBt;

    @FXML
    private PasswordField passwordContr;

    @FXML
    private ToggleGroup colType;

    @FXML
    private RadioButton radioCip;

    @FXML
    private RadioButton radioS;

    @FXML
    private RadioButton radioBez;


    private ObservableList<TableColumn> privateColumns = FXCollections.observableArrayList();
    private ObservableList<TableColumn> columnsBez = FXCollections.observableArrayList();
    private ObservableList<TableColumn> privateColumnsBez = FXCollections.observableArrayList();
    private ObservableList<TableColumn> columnsS = FXCollections.observableArrayList();
    private ObservableList<TableColumn> privateColumnsS = FXCollections.observableArrayList();
    private ObservableList<TableColumn> columnsCip = FXCollections.observableArrayList();
    private ObservableList<TableColumn> privateColumnsCip = FXCollections.observableArrayList();

    static double zakazSumma = 0;
    static ObservableList<TextField> kursLar = FXCollections.observableArrayList();
    static ObservableList<Label> summLable = FXCollections.observableArrayList();

    private ControllerOyna controllerOyna;
    private BorderPane borderPane;

    private Stage mainStage;


    private TableColumn<TovarZakaz, String> zNDS1Narxi;
    private TableColumn<TovarZakaz, String> zCIPnarxUzs;
    private Stavkalar stavkalar;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;


        stavkalar = new Stavkalar();

        usdTf.setText(stavkalar.getStUSD_USZ() + "");
        rubTf.setText(stavkalar.getStUSD_RUB() + "");
        eurTf.setText(stavkalar.getStUSD_EUR() + "");


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
            MenuItem editItem = new MenuItem(bundle.getString("edit"));
            MenuItem removeItem = new MenuItem(bundle.getString("delete"));
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

//        switchIsSelected = ddpSwitch.isSelected();
        switchAction();

        EditTovar.ishla = false;

        passwordContr.textProperty().addListener(e -> {
            passwordContr.setStyle("-fx-border-width: 1.5; -fx-border-color:  #861386");
            ok_bt.setStyle("-fx-border-width: 1.5; -fx-border-color:  #861386");
        });

        kursYangila_2();
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


        TableColumn<PriseList, Button> tovarAddBt = new TableColumn<>(bundle.getString("add"));
        tovarAddBt.setPrefWidth(60);
        tovarAddBt.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarAdd()
        ));
        tovarAddBt.setSortable(false);
        tovarAddBt.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, Integer> tovarAddCount = new TableColumn<>(bundle.getString("soni"));
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


        TableColumn<PriseList, String> tovarId = new TableColumn<>(bundle.getString("kod"));
        tovarId.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKod()
        ));
        tovarId.setMinWidth(100);
        tovarId.setPrefWidth(120);
        tovarId.setMaxWidth(150);
        tovarId.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarNomi = new TableColumn<>(bundle.getString("tovarNomi"));
        tovarNomi.setPrefWidth(300);
        tovarNomi.setMaxWidth(500);
        tovarNomi.setMinWidth(200);
        tovarNomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNomi()
        ));
        tovarNomi.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarModel = new TableColumn<>(bundle.getString("model"));
        tovarModel.setMinWidth(100);
        tovarModel.setPrefWidth(120);
        tovarModel.setMaxWidth(150);
        tovarModel.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarModel()
        ));
        tovarModel.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarIshCh = new TableColumn<>(bundle.getString("maker"));
        tovarIshCh.setMinWidth(100);
        tovarIshCh.setPrefWidth(120);
        tovarIshCh.setMaxWidth(150);
        tovarIshCh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarIshlabChiqaruvchi().getName()
        ));
        tovarIshCh.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarNarxi = new TableColumn<>(bundle.getString("narxi"));
        tovarNarxi.setMinWidth(100);
        tovarNarxi.setPrefWidth(120);
        tovarNarxi.setMaxWidth(150);
        tovarNarxi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxiStr()
        ));
        tovarNarxi.setStyle("-fx-alignment: CENTER");


        TableColumn<PriseList, String> tovarNarxiTuri = new TableColumn<>(bundle.getString("valyuta"));
        tovarNarxiTuri.setResizable(false);
        tovarNarxiTuri.setSortable(false);
        tovarNarxiTuri.setMinWidth(60);
        tovarNarxiTuri.setPrefWidth(60);
        tovarNarxiTuri.setStyle("-fx-alignment: CENTER");
        tovarNarxiTuri.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxTuri()
        ));


        TableColumn tovarQushimcha = new TableColumn(bundle.getString("more"));
        tovarQushimcha.setPrefWidth(150);
        tovarQushimcha.setResizable(false);


        TableColumn<PriseList, String> tovarTrans = new TableColumn<>(bundle.getString("transport"));
        tovarTrans.setSortable(false);
        tovarTrans.setMinWidth(60);
        tovarTrans.setPrefWidth("Transport".length() * 12);
        tovarTrans.setStyle("-fx-alignment: CENTER");
        tovarTrans.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarTransportNarxiString()
        ));


        TableColumn<PriseList, String> tovarAksiz = new TableColumn<>(bundle.getString("aksiz"));
        tovarAksiz.setSortable(false);
        tovarAksiz.setMinWidth(60);
        tovarAksiz.setPrefWidth(60);
        tovarAksiz.setStyle("-fx-alignment: CENTER");
        tovarAksiz.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarAksizString()
        ));


        TableColumn<PriseList, String> tovarPoshlina = new TableColumn<>(bundle.getString("poshlina"));
        tovarPoshlina.setSortable(false);
        tovarPoshlina.setMinWidth(60);
        tovarPoshlina.setPrefWidth("Poshlina".length() * 12);
        tovarPoshlina.setStyle("-fx-alignment: CENTER");
        tovarPoshlina.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarPoshlinaString()
        ));

        TableColumn<PriseList, String> tovarDDP = new TableColumn<>(bundle.getString("ddp"));
        tovarDDP.setResizable(false);
        tovarDDP.setSortable(false);
        tovarDDP.setMinWidth(60);
        tovarDDP.setPrefWidth(60);
        tovarDDP.setStyle("-fx-alignment: CENTER");
        tovarDDP.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarDDPStr()
        ));


        TableColumn<PriseList, LocalDate> tovarSana = new TableColumn<>(bundle.getString("sana"));
        tovarSana.setResizable(false);
        tovarSana.setMinWidth(100);
        tovarSana.setPrefWidth(100);
        tovarSana.setStyle("-fx-alignment: CENTER");
        tovarSana.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTovarSana()
        ));

        TableColumn<PriseList, String> tovarUlchov = new TableColumn<>(bundle.getString("ulchov"));
        tovarUlchov.setResizable(false);
        tovarUlchov.setSortable(false);
        tovarUlchov.setMinWidth(70);
        tovarUlchov.setPrefWidth(70);
        tovarUlchov.setStyle("-fx-alignment: CENTER");
        tovarUlchov.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarUlchovBirligi()
        ));


        TableColumn<PriseList, String> tovarKomment = creatTabCol(bundle.getString("komment"), 150);
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
                    TovarZakaz.tovarZakazList.add(new TovarZakaz(mainTable.getItems().get(i), this.stavkalar));
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


    void summaHisobla() {
        double localSumm = 0;
        double cipSummUsd = 0;

        DecimalFormat format = new DecimalFormat("#,##0.000");


        if (getTypeColcul() == 0) {
            localSumm = TovarZakaz.tovarZakazList.stream().mapToDouble(TovarZakaz::getZakazDDPsumm).sum();
        } else if (getTypeColcul() == 1) {
            localSumm = TovarZakaz.tovarZakazList.stream().mapToDouble(TovarZakaz::getZakazNDS2liSumm).sum();
        } else {
            cipSummUsd = TovarZakaz.tovarZakazList.stream().mapToDouble(TovarZakaz::getZakazCIPSummUSD).sum();
        }

        zakazSumma = localSumm;

        if (!TovarZakaz.tovarZakazList.isEmpty()) {
            if (getTypeColcul() == 2) {

                summLable.get(0).setText(format.format(cipSummUsd * stavkalar.getStUSD_USZ()));
                summLable.get(1).setText(format.format(cipSummUsd));
                summLable.get(2).setText(format.format((cipSummUsd * stavkalar.getStUSD_USZ())
                        / stavkalar.getStUSD_RUB()));
                summLable.get(3).setText(format.format((cipSummUsd * stavkalar.getStUSD_USZ())
                        / stavkalar.getStUSD_EUR()));

            } else {

                summLable.get(0).setText(format.format(localSumm));
                summLable.get(1).setText(format.format(localSumm
                        / stavkalar.getStUSD_USZ()));
                summLable.get(2).setText(format.format(localSumm
                        / stavkalar.getStUSD_RUB()));
                summLable.get(3).setText(format.format(localSumm
                        / stavkalar.getStUSD_EUR()));
            }

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


        TableColumn<TovarZakaz, Spinner> zKamBt = new TableColumn<>(bundle.getString("uzgartirish"));
        zKamBt.setSortable(false);
        zKamBt.setPrefWidth(100);
        zKamBt.setStyle("-fx-alignment: CENTER");
        zKamBt.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazUzgartir()
        ));

        TableColumn<TovarZakaz, JFXButton> zUchBt = new TableColumn<>(bundle.getString("uchirish"));
        zUchBt.setSortable(false);
        zUchBt.setPrefWidth(100);
        zUchBt.setStyle("-fx-alignment: CENTER");
        zUchBt.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getZakazUchirishBt()
        ));


        TableColumn<TovarZakaz, String> zNomi = new TableColumn<>(bundle.getString("tovarNomi"));
        zNomi.setSortable(false);
        zNomi.setPrefWidth(200);
        zNomi.setMinWidth(200);
        zNomi.setStyle("-fx-alignment: CENTER");
        zNomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNomi()
        ));


        TableColumn<TovarZakaz, String> zIshCh = creatTabCol(bundle.getString("maker"));
        zIshCh.setStyle("-fx-alignment: CENTER");
        zIshCh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarIshlabChiqaruvchi().getName()
        ));


        TableColumn<TovarZakaz, String> zModel = creatTabCol(bundle.getString("model"));
        zModel.setStyle("-fx-alignment: CENTER");
        zModel.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarModel()
        ));


        TableColumn<TovarZakaz, String> zTavId = creatTabCol(bundle.getString("kod"));
        zTavId.setStyle("-fx-alignment: CENTER");
        zTavId.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarKod()
        ));


        TableColumn<TovarZakaz, String> zNarxiTuri = creatTabCol(bundle.getString("valyuta"), 60, false);
        zNarxiTuri.setResizable(false);
        zNarxiTuri.setStyle("-fx-alignment: CENTER");
        zNarxiTuri.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxTuri()
        ));


        TableColumn<TovarZakaz, String> ztavEXWNarxi = creatTabCol(bundle.getString("exwNarxi"));
        ztavEXWNarxi.setStyle("-fx-alignment: CENTER");
        ztavEXWNarxi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarNarxiStr()
        ));


        TableColumn<TovarZakaz, String> ztavEXWSumm = creatTabCol(bundle.getString("exwSumma"));
        ztavEXWSumm.setStyle("-fx-alignment: CENTER");
        ztavEXWSumm.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazSummaExwStr()
        ));


        TableColumn zTavTransSt = new TableColumn(bundle.getString("transNatija"));
        zTavTransSt.setSortable(false);
        zTavTransSt.setResizable(false);


        TableColumn<TovarZakaz, String> zTavTrans = creatTabCol((stavkalar.getStTransStr()));
        zTavTrans.setResizable(false);
        zTavTrans.setSortable(false);
        zTavTrans.setStyle("-fx-alignment: CENTER");
        zTavTrans.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazTransProNatijaStr()
        ));

        zTavTransSt.getColumns().addAll(zTavTrans);


        TableColumn<TovarZakaz, String> ztavTransSumm = creatTabCol(bundle.getString("transSumma"), 150);
        ztavTransSumm.setStyle("-fx-alignment: CENTER");
        ztavTransSumm.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazTransSummStr()
        ));


        TableColumn<TovarZakaz, String> ztavTransLiSumm = creatTabCol(bundle.getString("transLiNarx"), 150);
        ztavTransLiSumm.setStyle("-fx-alignment: CENTER");
        ztavTransLiSumm.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazTransLiSummaStr()
        ));


        ///**********************************14///

        TableColumn<TovarZakaz, String> zCIPst = creatTabCol(bundle.getString("cipStavka"));
        zCIPst.setResizable(false);
        zCIPst.setVisible(false);
        zCIPst.setStyle("-fx-alignment: CENTER");
        zCIPst.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getStCIPStr()
        ));


        TableColumn<TovarZakaz, String> zCIPnarxi = creatTabCol(bundle.getString("cipNarxiUsd"));
        zCIPnarxi.setVisible(false);
        zCIPnarxi.setStyle("-fx-alignment: CENTER");
        zCIPnarxi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazCIPNarxiUSDStr()
        ));

        TableColumn<TovarZakaz, String> zCIPsumm = creatTabCol(bundle.getString("cipSummaUsd"));
        zCIPsumm.setVisible(false);
        zCIPsumm.setStyle("-fx-alignment: CENTER");
        zCIPsumm.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazCIPSummUSDStr()
        ));

        TableColumn zCIPuzsLab = new TableColumn(bundle.getString("cipNarxiUzs"));
        zCIPuzsLab.setMinWidth(100);
        zCIPuzsLab.setPrefWidth(120);
        zCIPuzsLab.setMaxWidth(150);
        zCIPuzsLab.setVisible(false);
        zCIPuzsLab.setResizable(false);


        zCIPnarxUzs = creatTabCol((this.stavkalar.getStUSD_USZStr()) + " " + bundle.getString("sum"));
        zCIPnarxUzs.setResizable(false);
        zCIPnarxUzs.setStyle("-fx-alignment: CENTER");
        zCIPnarxUzs.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazCIPNarxiUSZStr()
        ));
        zCIPnarxUzs.setVisible(false);
        zCIPuzsLab.getColumns().addAll(zCIPnarxUzs);


        TableColumn<TovarZakaz, String> zCIPsummUzs = creatTabCol(bundle.getString("cipSummaUzs"));
        zCIPsummUzs.setResizable(false);
        zCIPsummUzs.setStyle("-fx-alignment: CENTER");
        zCIPsummUzs.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazCIPSummUSZStr()
        ));
        zCIPsummUzs.setVisible(false);


        TableColumn zBojSt = new TableColumn(bundle.getString("bojNarxi"));
        zBojSt.setVisible(false);
        zBojSt.setPrefWidth(150);
        zBojSt.setResizable(false);
        zBojSt.setStyle("-fx-alignment: CENTER");


        TableColumn<TovarZakaz, String> zBoj = creatTabCol((stavkalar.getStBojxonaStr()) + " %");
        zBoj.setVisible(false);
        zBoj.setPrefWidth(150);
        zBoj.setResizable(false);
        zBoj.setStyle("-fx-alignment: CENTER");
        zBoj.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazBojYiginiStr()
        ));
        zBojSt.getColumns().addAll(zBoj);


        TableColumn<TovarZakaz, String> ztovarPoshlinaOr = creatTabCol(bundle.getString("poshlina"), 60);
        ztovarPoshlinaOr.setResizable(false);
        ztovarPoshlinaOr.setVisible(false);
        ztovarPoshlinaOr.setStyle("-fx-alignment: CENTER");
        ztovarPoshlinaOr.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarPoshlinaStr()
        ));


        TableColumn<TovarZakaz, String> zSumPosh = creatTabCol(bundle.getString("poshlinaSumm"));
        zSumPosh.setVisible(false);
        zSumPosh.setStyle("-fx-alignment: CENTER");
        zSumPosh.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazPoshlinaSummStr()
        ));


        TableColumn<TovarZakaz, String> ztovarAksizOr = creatTabCol(bundle.getString("aksiz"), 50);
        ztovarAksizOr.setResizable(false);
        ztovarAksizOr.setVisible(false);
        ztovarAksizOr.setStyle("-fx-alignment: CENTER");
        ztovarAksizOr.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarAksizStr()
        ));


        TableColumn<TovarZakaz, String> zSumAk = creatTabCol(bundle.getString("aksizSumm"));
        zSumAk.setVisible(false);
        zSumAk.setStyle("-fx-alignment: CENTER");
        zSumAk.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazAksizSummStr()
        ));

        TableColumn zNDS1 = creatTabCol(bundle.getString("nds1"));
        zNDS1.setResizable(false);
        zNDS1.setSortable(false);
        zNDS1.setVisible(false);


        zNDS1Narxi = creatTabCol((stavkalar.getStNDS1SStr()) + " %");
        zNDS1Narxi.setResizable(false);
        zNDS1Narxi.setVisible(false);
        zNDS1Narxi.setStyle("-fx-alignment: CENTER");
        zNDS1Narxi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazNDS1NarxiStr()
        ));
        zNDS1.getColumns().addAll(zNDS1Narxi);


        TableColumn<TovarZakaz, String> zKelNar = creatTabCol(bundle.getString("kelishNarxi"));
        zKelNar.setVisible(false);
        zKelNar.setStyle("-fx-alignment: CENTER");
        zKelNar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazKelishNarxiStr()
        ));


        TableColumn<TovarZakaz, String> zKelSum = creatTabCol(bundle.getString("kelishSumm"));
        zKelSum.setVisible(false);
        zKelSum.setStyle("-fx-alignment: CENTER");
        zKelSum.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazKelishSummStr()
        ));


        TableColumn<TovarZakaz, String> zDDPst = creatTabCol(bundle.getString("ddpStavka"));
        zDDPst.setVisible(false);
        zDDPst.setStyle("-fx-alignment: CENTER");
        zDDPst.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTovarDDPStr()
        ));


        ///********************************25///


        TableColumn<TovarZakaz, String> zDDPNar = creatTabCol(bundle.getString("ddpNarxi"));
        zDDPNar.setStyle("-fx-alignment: CENTER");
        zDDPNar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazDDPnarxiStr()
        ));


        TableColumn<TovarZakaz, String> zDDPSum = creatTabCol(bundle.getString("ddpSumm"));
        zDDPSum.setStyle("-fx-alignment: CENTER");
        zDDPSum.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazDDPsummStr()
        ));


        TableColumn nds2Stavka = new TableColumn(bundle.getString("nds2LiNarx"));
        nds2Stavka.setPrefWidth(150);
        nds2Stavka.setResizable(false);


        TableColumn<TovarZakaz, String> zNDS2Nar = new TableColumn<>((stavkalar.getStNDS2Str()) + " %");
        zNDS2Nar.setPrefWidth(150);
        zNDS2Nar.setResizable(false);
        zNDS2Nar.setStyle("-fx-alignment: CENTER");
        zNDS2Nar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazNDS2liNarxiStr()
        ));
        zNDS2Nar.setVisible(false);
        nds2Stavka.getColumns().add(zNDS2Nar);


        TableColumn<TovarZakaz, String> nds2Summa = creatTabCol(bundle.getString("nds2LiSumma"));
        nds2Summa.setStyle("-fx-alignment: CENTER");
        nds2Summa.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getZakazNDS2liSummStr()
        ));

//
//        privateColumns.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs,
//                zBojSt, zBoj, ztovarPoshlinaOr, zSumPosh, ztovarAksizOr,
//                zSumAk, zNDS1, zNDS1Narxi, zKelNar, zKelSum, zDDPst,
//                zDDPNar, zDDPSum, nds2Stavka, zNDS2Nar, nds2Summa
//        );


        columnsBez.clear();
        columnsBez.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs,
                zCIPsummUzs, zBojSt, zBoj, ztovarPoshlinaOr, zSumPosh, ztovarAksizOr,
                zSumAk, zNDS1, zNDS1Narxi, zKelNar, zKelSum, zDDPst,
                zDDPNar, zDDPSum
        );


        privateColumnsBez.clear();
        privateColumnsBez.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs, zCIPsummUzs,
                zBojSt, zBoj, ztovarPoshlinaOr, zSumPosh, ztovarAksizOr,
                zSumAk, zNDS1, zNDS1Narxi, zKelNar, zKelSum, zDDPst
        );


        columnsS.clear();
        columnsS.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs, zCIPsummUzs,
                zBojSt, zBoj, ztovarPoshlinaOr, zSumPosh, ztovarAksizOr,
                zSumAk, zNDS1, zNDS1Narxi, zKelNar, zKelSum, zDDPst,
                zDDPNar, zDDPSum, nds2Stavka, zNDS2Nar, nds2Summa
        );


        privateColumnsS.clear();
        privateColumnsS.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs, zCIPsummUzs,
                zBojSt, zBoj, ztovarPoshlinaOr, zSumPosh, ztovarAksizOr,
                zSumAk, zNDS1, zNDS1Narxi, zKelNar, zKelSum, zDDPst
        );


        privateColumnsCip.clear();
        privateColumnsCip.addAll(zCIPst);

        columnsCip.clear();
        columnsCip.addAll(zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPnarxUzs, zCIPsummUzs);


        helperTable.getColumns().addAll(
                zTr, zKamBt, zUchBt, zNomi, zIshCh, zModel, zTavId, zNarxiTuri,
                ztavEXWNarxi, ztavEXWSumm, zTavTransSt, ztavTransSumm,
                ztavTransLiSumm, zCIPst, zCIPnarxi, zCIPsumm, zCIPuzsLab, zCIPsummUzs, zBojSt,
                ztovarPoshlinaOr, zSumPosh, ztovarAksizOr, zSumAk,
                zNDS1, zKelNar, zKelSum, zDDPst, zDDPNar, zDDPSum,
                nds2Stavka, nds2Summa
        );


        helperTable.setItems(TovarZakaz.tovarZakazList);
        helperTable.refresh();
    }


// password fieldni kursatish

    public void showDialog() {

        if (kursatBt.isSelected()) {
            passHb.setVisible(true);
            passwordContr.clear();
            MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.EYE_OFF);
            iconView.setGlyphSize(20);
            iconView.setFill(Paint.valueOf("#2d3b9d"));
            kursatBt.setGraphic(iconView);

            passwordContr.setStyle("-fx-border-width: 1.5; -fx-border-color:  #861386");
            ok_bt.setStyle("-fx-border-width: 1.5; -fx-border-color:  #861386");
            passwordContr.selectAll();

        } else {
            passHb.setVisible(false);
            offAllColumns();
            showDefaultColumns();
            passwordContr.clear();
            MaterialDesignIconView iconView = new MaterialDesignIconView(MaterialDesignIcon.EYE);
            iconView.setGlyphSize(20);
            iconView.setFill(Paint.valueOf("#2d3b9d"));
            kursatBt.setGraphic(iconView);
        }

        helperTable.refresh();
    }

    // parolni tekshirish

    @FXML
    private void parolTugrimi() {


        if (new PasswordsConnections().equalsPasswords(new Password("showPrivateColumns", passwordContr.getText(),
                LocalDate.now(), "Show private columns", true))) {
            passwordContr.setText("");
            passHb.setVisible(false);
            ustunKursat();
        } else {
            passwordContr.setStyle("-fx-border-width: 1.5; -fx-background-color: red; -fx-border-color: red ");
            passwordContr.selectAll();
            ok_bt.setStyle("-fx-border-width: 1.5; -fx-background-color: red; -fx-border-color: red");
        }
    }


    // default qiymat uranatish, yoki bush bugandagi qiymatni urnatish

    @FXML
    private void tekshir() {
        if (!passwordContr.getText().trim().isEmpty()) {
            ok_bt.setDisable(false);
        } else {
            ok_bt.setDisable(true);
            passwordContr.setStyle("-fx-border-width: 1.5; -fx-border-color:  #861386");
            ok_bt.setStyle("-fx-border-width: 1.5; -fx-border-color:  #861386");
        }

    }

    // private ustunlarni kusatish
    private void ustunKursat() {

        offAllColumns();

        if (kursatBt.isSelected()) {
            if (getTypeColcul() == 0) {
                privateColBezOn();
            } else if (getTypeColcul() == 1) {
                privateColSOn();
            } else if (getTypeColcul() == 2) {
                privateColCipOn();
            }
        } else {
            if (getTypeColcul() == 0) {
                privateColBezOff();
            } else if (getTypeColcul() == 1) {
                privateColSOff();
            } else if (getTypeColcul() == 2) {
                privateColCipOff();
            }
        }

        helperTable.refresh();
    }


    private void showDefaultColumns() {

        if (getTypeColcul() == 0) {
            privateColBezOff();
        } else if (getTypeColcul() == 1) {
            privateColSOff();
        } else if (getTypeColcul() == 2) {
            privateColCipOff();
        }

        helperTable.refresh();
    }


    private void privateColBezOn() {
        offAllColumns();
        columnsBez.forEach(e -> e.setVisible(true));
        privateColumnsBez.forEach(e -> e.setVisible(true));
        privateColumnsBez.forEach(e -> e.setStyle("-fx-background-color: rgba(226,167,255,0.23); -fx-alignment: CENTER"));
    }

    private void privateColBezOff() {
        offAllColumns();
        columnsBez.forEach(e -> e.setVisible(true));
        privateColumnsBez.forEach(e -> e.setVisible(false));
        privateColumnsBez.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
    }


    private void privateColSOn() {
        offAllColumns();
        columnsS.forEach(e -> e.setVisible(true));
        privateColumnsS.forEach(e -> e.setVisible(true));
        privateColumnsS.forEach(e -> e.setStyle("-fx-background-color: rgba(226,167,255,0.23); -fx-alignment: CENTER"));
    }

    private void privateColSOff() {
        offAllColumns();
        columnsS.forEach(e -> e.setVisible(true));
        privateColumnsS.forEach(e -> e.setVisible(false));
        privateColumnsS.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
    }


    private void privateColCipOn() {
        offAllColumns();
        columnsCip.forEach(e -> e.setVisible(true));
        privateColumnsCip.forEach(e -> e.setVisible(true));
        privateColumnsCip.forEach(e -> e.setStyle("-fx-background-color: rgba(226,167,255,0.23); -fx-alignment: CENTER"));
    }

    private void privateColCipOff() {
        offAllColumns();
        columnsCip.forEach(e -> e.setVisible(true));
        privateColumnsCip.forEach(e -> e.setVisible(false));
        privateColumnsCip.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
    }


    private void offAllColumns() {
        offAllPrivateColumns();
        columnsBez.forEach(e -> e.setVisible(false));
        columnsS.forEach(e -> e.setVisible(false));
        columnsCip.forEach(e -> e.setVisible(false));

        columnsBez.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
        columnsS.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
        columnsCip.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
    }


    private void offAllPrivateColumns() {
        privateColumnsBez.forEach(e -> e.setVisible(false));
        privateColumnsS.forEach(e -> e.setVisible(false));
        privateColumnsCip.forEach(e -> e.setVisible(false));

        privateColumnsBez.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
        privateColumnsS.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
        privateColumnsCip.forEach(e -> e.setStyle("-fx-alignment: CENTER"));
    }



    @FXML
    private void showProjectView(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/projectView.fxml"));

        loader.setResources(bundle);

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
        controller.setControllerTable(this);
        controller.setStavkalar(stavkalar);
        int typeCol = 0;


        if (radioBez.isSelected()) {
            typeCol = 0;   //0
        } else if (radioS.isSelected()) {
            typeCol = 1;   //1
        } else if (radioCip.isSelected()) {
            typeCol = 2;   //2
        }

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
        loader.setLocation(getClass().getResource("/Views/zakazEdit.fxml"));
        loader.setResources(bundle);
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

        stage.setOnCloseRequest(e -> {
            summaHisobla();
        });

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

    boolean b = true;

    @FXML
    private void kursniYangila() {

        try {
            stavkalar.setStUSD_RUB(Double.parseDouble(rubTf.getText()));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        try {
            stavkalar.setStUSD_EUR(Double.parseDouble(eurTf.getText()));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        try {
            stavkalar.setStUSD_USZ(Double.parseDouble(usdTf.getText()));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

        zCIPnarxUzs.setText((this.stavkalar.getStUSD_USZStr()) + " " + bundle.getString("sum"));

        TovarZakaz.tovarZakazList.forEach(TovarZakaz::zakazHisobla);
        summaHisobla();
        helperTable.refresh();
    }

    @FXML
    private void kursYangila_2() {

        this.stavkalar = new Stavkalar();
        TovarZakaz.tovarZakazList.forEach(e -> e.setStavkalar(this.stavkalar));
        usdTf.setText(stavkalar.getStUSD_USZ() + "");
        rubTf.setText(stavkalar.getStUSD_RUB() + "");
        eurTf.setText(stavkalar.getStUSD_EUR() + "");
        kursniYangila();
    }

    @FXML
    private void switchAction() {

        if (getTypeColcul() == 0) {
            zNDS1Narxi.setText((stavkalar.getStNDS1BezStr()) + " %");
            TovarZakaz.selected = false;
            ustunKursat();

        } else if (getTypeColcul() == 1) {
            zNDS1Narxi.setText((stavkalar.getStNDS1SStr()) + " %");
            TovarZakaz.selected = true;
            ustunKursat();

        } else if (getTypeColcul() == 2) {
            ustunKursat();
        }

        helperTable.refresh();
        TovarZakaz.tovarZakazList.forEach(TovarZakaz::zakazHisobla);
        summaHisobla();

    }


    private int getTypeColcul() {

        if (radioS.isSelected()) {
            return 1;   //1
        } else if (radioCip.isSelected()) {
            return 2;   //2
        } else if (radioBez.isSelected()) {
            return 0;   //0
        }

        return 0;
    }


    @FXML
    private void showProjectXisob() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/passwordContr.fxml"));

        loader.setResources(bundle);

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
        stage.setTitle("Project View");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(mainStage);
        stage.show();

        PasswordContr passwordContr = loader.getController();

//        passwordContr.setProject();
        passwordContr.setTypeCol(getTypeColcul());
        passwordContr.setMainStage(mainStage);
    }

}


