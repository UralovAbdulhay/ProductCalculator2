package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import sample.Classes.Api_kurs;
import sample.Classes.Connections;
import sample.Moodles.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;


public class EditTovar implements Initializable {

    @FXML
    private Tab coursesTab;
    @FXML
    private Tab staffsTab;

    @FXML
    private Tab priseTab;

    @FXML
    private Tab projectsTab;

    @FXML
    private Tab doneProjectsTab;

    @FXML
    private JFXTabPane parentTabPane;

    @FXML
    private TableView<Project> doneProjectTable;

    @FXML
    private JFXRadioButton kursEmptyShow;

    @FXML
    private JFXButton delPrBt;

    @FXML
    private JFXButton addPriseBt;

    @FXML
    private Label kusrSanaLable;

    @FXML
    private TableView<StavkaShablon> stavkaTable;

    @FXML
    private TableView<Valyuta> kursTable;

    @FXML
    private TextField plQidir = TextFields.createSearchField();

    @FXML
    private TableView<PriseList> listTable;

    @FXML
    private TableView<Project> projectTable;

    @FXML
    private ToggleButton deletePriseBt;

    @FXML
    private JFXButton editPriseBt;


    private TableColumn tovarDeleteCol = creatTabCol("O'chirish", 60);
    static boolean ochilgan = false;
    private TableColumn tovarSana;

    private Stage mainStage;
    private AddProductController addProductController = new AddProductController();
    private ObservableList<Valyuta> valyutas = FXCollections.observableArrayList();


    public void tozalaQidir(ActionEvent actionEvent) {
        plQidir.clear();
        editBtControl();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ruyxatUrnat();

        listTable.setFixedCellSize(35);
        editPriseBt.setDisable(true);

        listTable.setStyle("-fx-font-size: 14");
        stavkaTable.setStyle("-fx-font-size: 16");
        projectTable.setStyle("-fx-font-size: 14");
        doneProjectTable.setStyle("-fx-font-size: 14");


        plQidir.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                tovarQidirish(PriseList.priseLists);
                editBtControl();
            }
        });

        listTable.setOnMouseClicked(event -> {
            editBtControl();
        });

        if (valyutas.isEmpty()) {
            valyutas.addAll(new Connections().getCourseFromSql());
        }
        initKursTable();

        if (!PriseList.priseLists.isEmpty()) {
            PriseList.priseLists.forEach(p -> {
                p.getDelCheck().setOnMouseClicked(this::selected);
            });
        }


        listTable.setRowFactory(new Callback<TableView<PriseList>, TableRow<PriseList>>() {
            @Override
            public TableRow<PriseList> call(TableView<PriseList> tableView) {
                final TableRow<PriseList> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Edit");
                MenuItem removeItem = new MenuItem("Delete");
                removeItem.setOnAction(e -> deletePriseConfirmWithSelection());
                editItem.setOnAction(event -> editPrise());
                rowMenu.getItems().addAll(editItem, removeItem);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null)
                );
                return row;
            }
        });

        stavkaTable.setRowFactory(new Callback<TableView<StavkaShablon>, TableRow<StavkaShablon>>() {
            @Override
            public TableRow<StavkaShablon> call(TableView<StavkaShablon> tableView) {
                final TableRow<StavkaShablon> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Edit");
                editItem.setOnAction(event -> stavkaEdit());
                rowMenu.getItems().addAll(editItem);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null)
                );
                return row;
            }
        });

        initStavkaTable();
    }


    private void editBtControl() {
        if (listTable.getSelectionModel().getSelectedItem() != null) {
            editPriseBt.setDisable(deletePriseBt.isSelected());
        } else {
            editPriseBt.setDisable(true);
        }
    }

    private void ruyxatUrnat() {

        if (!ochilgan) {
            ochilgan = true;
        }
        TableColumn tovarTR = creatTabCol("№", 35);
        setValueFactory_(tovarTR, "tr");
        tovarTR.setResizable(false);
        tovarTR.impl_setReorderable(false);


        setValueFactory_(tovarDeleteCol, "delCheck");
        tovarDeleteCol.setSortable(false);
        tovarDeleteCol.setVisible(false);


        TableColumn tovarKod = creatTabCol("ID");
        setValueFactory_(tovarKod, "tovarKod");


        TableColumn tovarNomi = creatTabCol("Nomi", 200, 200, 500);
        setValueFactory_(tovarNomi, "tovarNomi");


        TableColumn tovarModel = creatTabCol("Model");
        setValueFactory_(tovarModel, "tovarModel");


        TableColumn tovarIshCh = creatTabCol("Maker");
        setValueFactory_(tovarIshCh, "tovarIshlabChiqaruvchi");


        TableColumn tovarNarxi = creatTabCol("Narxi");
        setValueFactory_(tovarNarxi, "tovarNarxi");


        TableColumn tovarNarxiTuri = creatTabCol("Valyuta", 80);
        setValueFactory_(tovarNarxiTuri, "tovarNarxTuri");
        tovarNarxiTuri.setResizable(false);
        tovarNarxiTuri.setSortable(false);

        ObservableList<String> valyutaTuri = FXCollections.observableArrayList(
                "usd"
                , "eur"
//                , "rub"
//                , "uzs"
        );


        TableColumn tovarQushimcha = creatTabCol("Qushimcha", 60);
        tovarQushimcha.setResizable(false);


        TableColumn tovarTrans = creatTabCol("Transport", 60);
        setValueFactory_(tovarTrans, "tovarTransportNarxi");
        tovarTrans.setResizable(false);
        tovarTrans.setSortable(false);


        TableColumn tovarAksiz = creatTabCol("Aksiz", 50);
        setValueFactory_(tovarAksiz, "tovarAksiz");
        tovarAksiz.setResizable(false);
        tovarAksiz.setSortable(false);


        TableColumn tovarPoshlina = creatTabCol("Poshlina", 60);
        setValueFactory_(tovarPoshlina, "tovarPoshlina");
        tovarPoshlina.setResizable(false);
        tovarPoshlina.setSortable(false);

        TableColumn<PriseList, Double> tovarDDP = creatTabCol("DDP", 60);
        setValueFactory_(tovarDDP, "tovarDDP");
        tovarDDP.setResizable(false);
        tovarDDP.setSortable(false);


        tovarSana = creatTabCol("Sana", 100);
        setValueFactory_(tovarSana, "tovarSana");
        tovarSana.setResizable(false);
        tovarSana.setSortable(false);


        TableColumn tovarUlchov = creatTabCol("o'lchov", 70);
        setValueFactory_(tovarUlchov, "tovarUlchovBirligi");
        tovarUlchov.setResizable(false);
        tovarUlchov.setSortable(false);


        TableColumn tovarKomment = creatTabCol("Komment", 150);
        setValueFactory_(tovarKomment, "tovarKomment");
        tovarKomment.setSortable(false);


        tovarQushimcha.getColumns().addAll(tovarTrans, tovarAksiz, tovarPoshlina, tovarDDP);

        listTable.getColumns().clear();
        listTable.getColumns().addAll(tovarDeleteCol, tovarTR, tovarKod, tovarNomi,
                tovarModel, tovarIshCh, tovarNarxi, tovarNarxiTuri, tovarQushimcha,
                tovarSana, tovarUlchov, tovarKomment);


        listTable.setItems(PriseList.priseLists);


    }


    @FXML
    private void initKursTable() {

        TableColumn<Valyuta, String> kursTitle_1 = new TableColumn<>("Title");
        kursTitle_1.setCellValueFactory(
                c -> new SimpleStringProperty(c.getValue().getTitle())
        );
        kursTitle_1.setStyle("-fx-alignment: CENTER");
        kursTitle_1.setSortable(false);


        TableColumn<Valyuta, Double> kursCb_price = new TableColumn<>("CENTRAL BANK");
        kursCb_price.setCellValueFactory(
                col -> new SimpleObjectProperty<>(col.getValue().getCb_priceD())
        );
        kursCb_price.setStyle("-fx-alignment: CENTER");


        TableColumn<Valyuta, Double> kursBuy_price = new TableColumn<>("BUY");
        kursBuy_price.setCellValueFactory(
                col -> new SimpleObjectProperty<>(col.getValue().getNbu_buy_priceD())
        );
        kursBuy_price.setStyle("-fx-alignment: CENTER");

        TableColumn<Valyuta, Double> kursSell_price = new TableColumn<>("SELL");
        kursSell_price.setCellValueFactory(
                col -> new SimpleObjectProperty<>(col.getValue().getNbu_cell_priceD())
        );
        kursSell_price.setStyle("-fx-alignment: CENTER");

        kursTable.setStyle("-fx-font-size: 16");

//        if (valyutas.isEmpty()) {
//            valyutas.
//        }

        if (!valyutas.isEmpty()) {
            kusrSanaLable.setText(valyutas.get(0).getDate() + "");
        }
        kursTable.getColumns().clear();
        kursTable.getColumns().addAll(kursTitle_1, kursCb_price, kursBuy_price, kursSell_price);
        kursTable.setItems(valyutas);
        showNotEmptyKurs();
    }

    @FXML
    private void refreshKurs(ActionEvent event) {

        //  baza va internet sinxronizatsiyasi bo'ladi

        new Connections() new Api_kurs().getCourses();

        if (!(new Connections().tableIsEmpty("course"))) {
            System.out.println("tableIsEmpty = " + true);
            valyutas.clear();
            valyutas.addAll(new Connections().getCourseFromSql());
        }

        if (!valyutas.isEmpty()) {
            kusrSanaLable.setText(valyutas.get(0).getDate() + "");
        }
        kursTable.setItems(valyutas);
        showNotEmptyKurs();
    }

    @FXML
    private void showNotEmptyKurs() {
        ObservableList<Valyuta> list = FXCollections.observableArrayList();
        if (kursEmptyShow.isSelected()) {
            valyutas.stream().filter(p -> p.getNbu_buy_priceD() != 0).forEach(list::add);
            kursTable.setItems(list);
        } else {
            kursTable.setItems(valyutas);
        }
        kursTable.refresh();
    }

    <S, T> TableColumn<S, T> creatTabCol(String title, double prefWidth) {

        TableColumn<S, T> newColumn_ = new TableColumn<S, T>(title);
        newColumn_.setPrefWidth(prefWidth);
        newColumn_.setMinWidth(prefWidth);
        return newColumn_;
    }


     <S, T> TableColumn<S, T> creatTabCol(String title, double prefWidth, boolean isSortable) {

        TableColumn<S, T> newColumn_ = new TableColumn<S, T>(title);
        newColumn_.setPrefWidth(prefWidth);
        newColumn_.setSortable(isSortable);
        return newColumn_;
    }

    <S, T> TableColumn<S, T> creatTabCol(String title) {

        TableColumn<S, T> newColumn_ = new TableColumn<S, T>(title);

        newColumn_.setMinWidth(120);
        newColumn_.setPrefWidth(150);
        newColumn_.setMaxWidth(200);

        return newColumn_;
    }


    <S, T> TableColumn<S, T> creatTabCol(String title, double minWidth, double prefWidth,
                            double maxWidth) {

        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);

        newColumn.setMinWidth(minWidth);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setMaxWidth(maxWidth);


        return newColumn;
    }


    void setValueFactory_(TableColumn tableColumn, String titleMark) {

        tableColumn.setCellValueFactory(new PropertyValueFactory(titleMark));

        tableColumn.setStyle("-fx-alignment: CENTER");
    }


    /**
     * priseLists ga tr ni urnatish
     */


    public double doubleYoz(TableColumn.CellEditEvent<Tovar, Double> event) {

        Double value = event.getOldValue();

        ControllerTable contrTable = new ControllerTable();

        if (contrTable.isDouble(String.valueOf(event.getNewValue()))) {

            value = event.getNewValue();
        }

        return value;

    }

    @FXML
    private void tovarQidirish(ObservableList<PriseList> priceListTableItems) {


        String natija = plQidir.getText();

        ObservableList<PriseList> natijaTovar = FXCollections.observableArrayList();

        if (natija.isEmpty()) {
            listTable.setItems(priceListTableItems);
        } else {

            for (int i = 0; i < priceListTableItems.size(); i++) {

                if (PriseList.priseLists.get(i).getTovarNomi().trim().toLowerCase().contains(natija.trim().toLowerCase())
                        || PriseList.priseLists.get(i).getTovarIshlabChiqaruvchi().toLowerCase().trim().contains(natija.trim().toLowerCase())
                ) {
                    natijaTovar.add(priceListTableItems.get(i));
                }
            }
            listTable.setItems(natijaTovar);
            listTable.refresh();
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void addPrise() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/addProduct.fxml"));
        VBox vBox = null;
        try {
            vBox = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(vBox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Add Product");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.setResizable(false);
        stage.show();

        addProductController = loader.getController();
        addProductController.setStage(stage);

    }

    @FXML
    private void editPrise() {
        addPrise();
        addProductController.setPriseList(listTable.getSelectionModel().getSelectedItem());
        addProductController.editPrise();
        TovarZakaz.tovarZakazList.clear();
    }

    @FXML
    private void deletePrise() {

        tovarDeleteCol.setVisible(deletePriseBt.isSelected());
        delPrBt.setVisible(deletePriseBt.isSelected());
        addPriseBt.setDisable(deletePriseBt.isSelected());
        editPriseBt.setDisable(deletePriseBt.isSelected());
        editBtControl();
        for (PriseList priseList : PriseList.priseLists) {
            priseList.getDelCheck().setSelected(false);
        }
    }

    public void selected(MouseEvent mouseEvent) {
        this.delPrBt.setDisable(
                PriseList.priseLists.stream().noneMatch(p -> p.getDelCheck().isSelected())
        );
    }

    @FXML
    private void deletePriseConfirmWithCheckBox() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE");
        alert.setHeaderText("DELETE");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            ObservableList<PriseList> lists = FXCollections.observableArrayList();
            PriseList.priseLists.stream().filter(p -> p.getDelCheck().isSelected()).forEach(p -> lists.add(p));
            removePrises(lists);
            PriseList.setTr();
            deletePriseBt.setSelected(false);
            deletePrise();
        }
    }

    private void deletePriseConfirmWithSelection() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE");
        alert.setHeaderText("DELETE");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            removePrises(listTable.getSelectionModel().getSelectedItem());
            PriseList.setTr();
            deletePriseBt.setSelected(false);
            deletePrise();
        }
    }

    private void removePrises(PriseList priseList) {
        PriseList.priseLists.removeAll(priseList);
        TovarZakaz.tovarZakazList.clear();
    }

    private void removePrises(ObservableList<PriseList> priseList) {
        PriseList.priseLists.removeAll(priseList);
        TovarZakaz.tovarZakazList.clear();
    }

    private void initStavkaTable() {
        new Stavkalar();
        TableColumn<StavkaShablon, String> nomi = new TableColumn<>("Наименование");
        nomi.setStyle("-fx-alignment: CENTER");
        nomi.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getNomi()
        ));


        TableColumn<StavkaShablon, Double> qiymat = new TableColumn<>("Значения ");
        qiymat.setStyle("-fx-alignment: CENTER");
        qiymat.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getQiymat()
        ));


        TableColumn<StavkaShablon, String> komment = new TableColumn<>("Комментария");
        komment.setStyle("-fx-alignment: CENTER");
        komment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getKomment()
        ));
        stavkaTable.getColumns().clear();
//        stavkaTable.getItems().clear();
        stavkaTable.getColumns().addAll(nomi, qiymat, komment);
Stavkalar.stavkaShablons.clear();
Stavkalar.stavkaShablons.addAll(new Connections().getStavkaFromSql());
        stavkaTable.setItems(Stavkalar.stavkaShablons);


    }

    private void stavkaEdit() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/staffEditView.fxml"));
        AnchorPane pane = null;

        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pane != null;
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initOwner(mainStage);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

        StaffEditController controller = loader.getController();
        controller.setStage(stage);
        controller.setShablon(stavkaTable.getSelectionModel().getSelectedItem());
        controller.initEdit();
        controller.setEditTovar(this);

    }

    void refreshStaffTable() {
        stavkaTable.refresh();
    }


    private void initProjectTable() {

        TableColumn<Project, Integer> numZakaz = creatTabCol("Номер Заказа");
        numZakaz.setStyle("-fx-alignment: CENTER");
        numZakaz.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getNumPr()
        ));


        TableColumn<Project, LocalDate> creteDate = creatTabCol("Дата и время");
        creteDate.setStyle("-fx-alignment: CENTER");
        creteDate.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getBoshlanganVaqt()
        ));


        TableColumn<Project, String> priority = creatTabCol("Приоритет");
        priority.setStyle("-fx-alignment: CENTER");
        priority.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getProritet()
        ));

        TableColumn<Project, String> prName = creatTabCol("Название проекта");
        prName.setStyle("-fx-alignment: CENTER");
        prName.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrNomi()
        ));

        TableColumn<Project, String> prClient = creatTabCol("Клиент");
        prClient.setStyle("-fx-alignment: CENTER");
        prClient.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrClient().getName()
        ));

        TableColumn<Project, String> prFromCom = creatTabCol("КМП от");
        prFromCom.setStyle("-fx-alignment: CENTER");
        prFromCom.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKmpCompany().getName()
        ));

        TableColumn<Project, String> prRaxbar = creatTabCol("Руководитель проекта 1/2", 200);
        prRaxbar.setStyle("-fx-alignment: CENTER");
        prRaxbar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrRaxbar().getIsm()
        ));

        TableColumn<Project, String> prMasul = creatTabCol("Ответственное лицо 1/2", 200);
        prMasul.setStyle("-fx-alignment: CENTER");
        prMasul.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrMasul().getIsm()
        ));

        TableColumn<Project, LocalDateTime> prEndDate = creatTabCol("Срок выполнения работ до", 200);
        prEndDate.setStyle("-fx-alignment: CENTER");
        prEndDate.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTugashVaqti()
        ));

        TableColumn<Project, String> prQolganDate = creatTabCol("Срок(дней/час)");
        prQolganDate.setStyle("-fx-alignment: CENTER");
        prQolganDate.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getQolganVaqt()
        ));

        TableColumn<Project, String> prColType = creatTabCol("Условия расчета");
        prColType.setStyle("-fx-alignment: CENTER");
        prColType.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrFormula()
        ));

        TableColumn<Project, String> prFile = creatTabCol("Файл");
        prFile.setStyle("-fx-alignment: CENTER");
        prFile.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrFileName()

        ));

        TableColumn<Project, String> prkomment = creatTabCol("Комментарии", 200);
        prkomment.setStyle("-fx-alignment: CENTER");
        prkomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKomment()
        ));

        projectTable.getColumns().clear();
        projectTable.getItems().clear();
        projectTable.getColumns().addAll(numZakaz, creteDate, priority,
                prName, prClient, prFromCom, prRaxbar, prMasul, prEndDate,
                prQolganDate, prColType, prFile,  prkomment );
        projectTable.setItems(new Connections().getProjectFromSql());

    }

    private void initDoneProjectsTable() {

        TableColumn<Project, Integer> numZakaz = creatTabCol("Номер Заказа");
        numZakaz.setStyle("-fx-alignment: CENTER");
        numZakaz.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getNumPr()
        ));


        TableColumn<Project, LocalDate> creteDate = creatTabCol("Дата и время");
        creteDate.setStyle("-fx-alignment: CENTER");
        creteDate.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getBoshlanganVaqt()
        ));


        TableColumn<Project, String> priority = creatTabCol("Приоритет");
        priority.setStyle("-fx-alignment: CENTER");
        priority.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getProritet()
        ));

        TableColumn<Project, String> prName = creatTabCol("Название проекта");
        prName.setStyle("-fx-alignment: CENTER");
        prName.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrNomi()
        ));

        TableColumn<Project, String> prClient = creatTabCol("Клиент");
        prClient.setStyle("-fx-alignment: CENTER");
        prClient.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrClient().getName()
        ));

        TableColumn<Project, String> prFromCom = creatTabCol("КМП от");
        prFromCom.setStyle("-fx-alignment: CENTER");
        prFromCom.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKmpCompany().getName()
        ));

        TableColumn<Project, String> prRaxbar = creatTabCol("Руководитель проекта 1/2", 200);
        prRaxbar.setStyle("-fx-alignment: CENTER");
        prRaxbar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrRaxbar().getIsm()
        ));

        TableColumn<Project, String> prMasul = creatTabCol("Ответственное лицо 1/2", 200);
        prMasul.setStyle("-fx-alignment: CENTER");
        prMasul.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrMasul().getIsm()
        ));

        TableColumn<Project, LocalDateTime> prEndDate = creatTabCol("Дата завершения");
        prEndDate.setStyle("-fx-alignment: CENTER");
        prEndDate.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTugashVaqti()
        ));


        TableColumn<Project, String> prColType = creatTabCol("Условия расчета");
        prColType.setStyle("-fx-alignment: CENTER");
        prColType.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrFormula()
        ));


        TableColumn<Project, String> prFile = creatTabCol("Файл");
        prFile.setStyle("-fx-alignment: CENTER");
        prFile.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrFileName()
        ));

        TableColumn<Project, String> prkomment = creatTabCol("Комментарии");
        prkomment.setStyle("-fx-alignment: CENTER");
        prkomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKomment()
        ));

        TableColumn<Project, LocalDateTime> prDoneDate =creatTabCol("Дата/время Выполнение", 200);
        prDoneDate.setStyle("-fx-alignment: CENTER");
        prDoneDate.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getPrTugallanganVaqti()
        ));


        doneProjectTable.getColumns().clear();
        doneProjectTable.getColumns().addAll(numZakaz, creteDate, priority,
                prName, prClient, prFromCom, prRaxbar, prMasul, prEndDate,
                 prColType, prFile,  prkomment, prDoneDate);
        doneProjectTable.setItems(new Connections().getProjectFromSql());
    }

    public void SelectionChanged(Event event) {

        switch (parentTabPane.getSelectionModel().getSelectedItem().getId()) {
            case "priseTab": {
                System.out.println("priseTab ishladi");
                ruyxatUrnat();
                break;
            }
            case "staffsTab": {
                System.out.println("staffsTab ishladi");
                initStavkaTable();
                break;
            }
            case "coursesTab": {
                System.out.println("coursesTab ishladi");
                initKursTable();
                break;
            }
            case "projectsTab": {
                System.out.println("projectsTab ishladi");
                initProjectTable();
                break;
            }
            case "doneProjectsTab": {
                System.out.println("doneProjectsTab ishladi");
                initDoneProjectsTable();
                break;
            }
            default: {
                System.out.println("default ishladi");
                break;
            }
        }
    }


}
