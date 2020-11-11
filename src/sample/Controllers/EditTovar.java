package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import sample.Classes.Api_kurs;
import sample.Classes.Connections;
import sample.Moodles.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


public class EditTovar implements Initializable {

    @FXML
    private JFXButton courseRefreshBt;

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


    private TableColumn<PriseList, JFXCheckBox> tovarDeleteCol = creatTabCol("O'chirish", 60);
    static boolean ochilgan = false;


    private Stage mainStage;
    private AddProductController addProductController = new AddProductController();
    private ObservableList<Valyuta> valyutas = FXCollections.observableArrayList();

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static ObservableList<Project> notDoneProjects = FXCollections.observableArrayList();
    public static ObservableList<Project> doneProjects = FXCollections.observableArrayList();


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

        refreshProjectList();


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

        projectTable.setRowFactory(new Callback<TableView<Project>, TableRow<Project>>() {
            @Override
            public TableRow<Project> call(TableView<Project> tableView) {

                final TableRow<Project> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem editItem = new MenuItem("Edit");
                MenuItem doneItem = new MenuItem("Add to DONE");
                MenuItem saveToFile = new MenuItem("Save to file");
                MenuItem reviewProject = new MenuItem("Review project");

                saveToFile.setOnAction(event -> saveToFile(projectTable.getSelectionModel().getSelectedItem()));
                editItem.setOnAction(event -> editProject(projectTable.getSelectionModel().getSelectedItem()));
                doneItem.setOnAction(event -> setDone(projectTable.getSelectionModel().getSelectedItem()));
                reviewProject.setOnAction(event -> reviewProject(projectTable.getSelectionModel().getSelectedItem()));

                SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

                rowMenu.getItems().addAll(editItem, doneItem, saveToFile, separatorMenuItem, reviewProject);

                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null)
                );
                return row;
            }
        });

        doneProjectTable.setRowFactory(new Callback<TableView<Project>, TableRow<Project>>() {
            @Override
            public TableRow<Project> call(TableView<Project> tableView) {

                final TableRow<Project> row = new TableRow<>();
                final ContextMenu rowMenu = new ContextMenu();
                MenuItem saveToFile = new MenuItem("Save to file");
                MenuItem backItem = new MenuItem("Return back");
                MenuItem reviewProject = new MenuItem("Review project");
                MenuItem deleteItem = new MenuItem("Delete");

                saveToFile.setOnAction(event -> saveToFile(doneProjectTable.getSelectionModel().getSelectedItem()));
                reviewProject.setOnAction(event -> reviewProject(doneProjectTable.getSelectionModel().getSelectedItem()));
                backItem.setOnAction(event -> setNotDone(doneProjectTable.getSelectionModel().getSelectedItem()));
                deleteItem.setOnAction(event -> doneProjectDelete(doneProjectTable.getSelectionModel().getSelectedItem()));

                SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();

                rowMenu.getItems().addAll(saveToFile, backItem, deleteItem,  separatorMenuItem, reviewProject);
                row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                .then(rowMenu)
                                .otherwise((ContextMenu) null)
                );
                return row;
            }
        });


        initStavkaTable();
        initDoneProjectsTable();
        initProjectTable();
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
        TableColumn<PriseList, Integer> tovarTR = creatTabCol("№", 35);
        tovarTR.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getTr()
        ));
        tovarTR.setResizable(false);
        tovarTR.impl_setReorderable(false);


        tovarDeleteCol.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getDelCheck()
        ));
        tovarDeleteCol.setSortable(false);
        tovarDeleteCol.setVisible(false);


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

        ObservableList<String> valyutaTuri = FXCollections.observableArrayList(
                "usd"
                , "eur"
//                , "rub"
//                , "uzs"
        );


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


        TableColumn<Valyuta, Double> kursCb_price = new TableColumn<>("CENTRAL BANK (UZS)");
        kursCb_price.setCellValueFactory(
                col -> new SimpleObjectProperty<>(col.getValue().getCb_priceD())
        );
        kursCb_price.setStyle("-fx-alignment: CENTER");


        TableColumn<Valyuta, Double> kursBuy_price = new TableColumn<>("BUY (UZS)");
        kursBuy_price.setCellValueFactory(
                col -> new SimpleObjectProperty<>(col.getValue().getNbu_buy_priceD())
        );
        kursBuy_price.setStyle("-fx-alignment: CENTER");

        TableColumn<Valyuta, Double> kursSell_price = new TableColumn<>("SELL (UZS)");
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
        courseRefreshBt.setDisable(true);

        for (Valyuta cours : new Api_kurs().getCourses()) {
            new Connections().insertToCourse(cours);
        }

        if (!(new Connections().tableIsEmpty("course"))) {
            valyutas.clear();
            valyutas.addAll(new Connections().getCourseFromSql());
        }

        if (!valyutas.isEmpty()) {
            kusrSanaLable.setText(valyutas.get(1).getDate() + "");

        }
        kursTable.setItems(valyutas);
        showNotEmptyKurs();
        courseRefreshBt.setDisable(false);
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

        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setMinWidth(prefWidth);
        newColumn.setStyle("-fx-alignment: CENTER");
        return newColumn;
    }


    <S, T> TableColumn<S, T> creatTabCol(String title, double prefWidth, boolean isSortable) {

        TableColumn<S, T> newColumn = new TableColumn<S, T>(title);
        newColumn.setPrefWidth(prefWidth);
        newColumn.setSortable(isSortable);
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
                        || PriseList.priseLists.get(i).getTovarIshlabChiqaruvchi().getName().toLowerCase().trim().contains(natija.trim().toLowerCase())
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
        new Connections().deleteTovar(priseList.getTovarId());
//        PriseList.priseLists.removeAll(priseList);
        TovarZakaz.tovarZakazList.clear();
    }

    private void removePrises(ObservableList<PriseList> priseLists) {
        new Connections().deleteTovar(priseLists);
//        PriseList.priseLists.removeAll(priseList);
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
        Stavkalar.resetStavkaShablons();
        stavkaTable.refresh();
    }


    private void initProjectTable() {

        TableColumn<Project, Integer> numZakaz = creatTabCol("Номер Заказа");
        numZakaz.setStyle("-fx-alignment: CENTER");
        numZakaz.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getNumPr()
        ));


        TableColumn<Project, String> creteDate = creatTabCol("Дата и время");
        creteDate.setStyle("-fx-alignment: CENTER");
        creteDate.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getBoshlanganVaqt().format(dateFormatter)
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

        TableColumn<Project, String> prKiritgan = creatTabCol("Запрос разместил", 200);
        prKiritgan.setStyle("-fx-alignment: CENTER");
        prKiritgan.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKritgan().getIsm()
        ));

        TableColumn<Project, String> prRaxbar = creatTabCol("Руководитель проекта", 200);
        prRaxbar.setStyle("-fx-alignment: CENTER");
        prRaxbar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrRaxbar().getIsm()
        ));

        TableColumn<Project, String> prMasul = creatTabCol("Ответственное лицо", 200);
        prMasul.setStyle("-fx-alignment: CENTER");
        prMasul.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrMasul().getIsm()
        ));

        TableColumn<Project, String> prEndDate = creatTabCol("Срок выполнения работ до", 200);
        prEndDate.setStyle("-fx-alignment: CENTER");
        prEndDate.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTugashVaqti().format(dateTimeFormatter)
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

//        TableColumn<Project, String> prFile = creatTabCol("Файл");
//        prFile.setStyle("-fx-alignment: CENTER");
//        prFile.setCellValueFactory(e -> new SimpleStringProperty(
//                e.getValue().getPrFileName()
//
//        ));

        TableColumn<Project, String> prkomment = creatTabCol("Комментарии", 200);
        prkomment.setStyle("-fx-alignment: CENTER");
        prkomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKomment()
        ));

        projectTable.getColumns().clear();
        projectTable.getItems().clear();
        projectTable.getColumns().addAll(numZakaz, creteDate, priority,
                prName, prClient, prFromCom, prKiritgan, prRaxbar, prMasul, prEndDate,
                prQolganDate, prColType, prkomment);
        projectTable.setItems(new Connections().getProjectsNotDoneFromSql());

    }

    private void initDoneProjectsTable() {

        TableColumn<Project, Integer> numZakaz = creatTabCol("Номер Заказа");
        numZakaz.setStyle("-fx-alignment: CENTER");
        numZakaz.setCellValueFactory(e -> new SimpleObjectProperty<>(
                e.getValue().getNumPr()
        ));


        TableColumn<Project, String> creteDate = creatTabCol("Дата и время");
        creteDate.setStyle("-fx-alignment: CENTER");
        creteDate.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getBoshlanganVaqt().format(dateFormatter)
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

        TableColumn<Project, String> prRaxbar = creatTabCol("Руководитель проекта", 200);
        prRaxbar.setStyle("-fx-alignment: CENTER");
        prRaxbar.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrRaxbar().getIsm()
        ));


        TableColumn<Project, String> prKiritgan = creatTabCol("Запрос разместил", 200);
        prKiritgan.setStyle("-fx-alignment: CENTER");
        prKiritgan.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKritgan().getIsm()
        ));

        TableColumn<Project, String> prMasul = creatTabCol("Ответственное лицо", 200);
        prMasul.setStyle("-fx-alignment: CENTER");
        prMasul.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrMasul().getIsm()
        ));

        TableColumn<Project, String> prEndDate = creatTabCol("Дата завершения");
        prEndDate.setStyle("-fx-alignment: CENTER");
        prEndDate.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getTugashVaqti().format(dateTimeFormatter)
        ));


        TableColumn<Project, String> prColType = creatTabCol("Условия расчета");
        prColType.setStyle("-fx-alignment: CENTER");
        prColType.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrFormula()
        ));


//        TableColumn<Project, String> prFile = creatTabCol("Файл");
//        prFile.setStyle("-fx-alignment: CENTER");
//        prFile.setCellValueFactory(e -> new SimpleStringProperty(
//                e.getValue().getPrFileName()
//        ));

        TableColumn<Project, String> prkomment = creatTabCol("Комментарии");
        prkomment.setStyle("-fx-alignment: CENTER");
        prkomment.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrKomment()
        ));

        TableColumn<Project, String> prDoneDate = creatTabCol("Дата/время Выполнение", 200);
        prDoneDate.setStyle("-fx-alignment: CENTER");
        prDoneDate.setCellValueFactory(e -> new SimpleStringProperty(
                e.getValue().getPrTugallanganVaqti() != null ?
                        e.getValue().getPrTugallanganVaqti().format(dateTimeFormatter) : null
        ));


        doneProjectTable.getColumns().clear();
        doneProjectTable.getColumns().addAll(numZakaz, creteDate, priority,
                prName, prClient, prFromCom, prKiritgan, prRaxbar, prMasul, prEndDate,
                prColType, prkomment, prDoneDate);
        doneProjectTable.setItems(new Connections().getProjectsDoneFromSql());
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
                projectTable.refresh();
//                initProjectTable();
                break;
            }
            case "doneProjectsTab": {
                System.out.println("doneProjectsTab ishladi");
                doneProjectTable.refresh();
//                initDoneProjectsTable();
                break;
            }
            default: {
                System.out.println("default ishladi");
                break;
            }
        }
    }

    void refreshProjectList() {
        notDoneProjects.clear();
        doneProjects.clear();
        doneProjects.addAll(new Connections().getProjectsDoneFromSql());
        notDoneProjects.addAll(new Connections().getProjectsNotDoneFromSql());
        projectTable.setItems(notDoneProjects);
        doneProjectTable.setItems(doneProjects);
        projectTable.refresh();
        doneProjectTable.refresh();
    }

    private void editProject(Project project) {

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
        stage.setTitle("Edit Project");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.initOwner(mainStage);
        stage.show();

        AddProject addProject = loader.getController();
        addProject.setOwnerStage((Stage) projectTable.getScene().getWindow());
        addProject.setProject(project);
        addProject.setEdit(true);
        addProject.setEditTovar(this);
    }

    private void setDone(Project project) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add to DONE");
        alert.setHeaderText("Are you  sure?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            project.setDone(true);
            project.setPrTugallanganVaqti(LocalDateTime.now());
            new Connections().setProjectDone(project);
            refreshProjectList();
        }
    }

    private void setNotDone(Project project) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return back");
        alert.setHeaderText("Are you  sure?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            project.setDone(false);
            project.setPrTugallanganVaqti(null);
            new Connections().setProjectNotDone(project);
            refreshProjectList();
        }
    }

    private void doneProjectDelete(Project project) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("DELETE");
        alert.setHeaderText("Are you  sure?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            new Connections().deleteProject(project);
            refreshProjectList();
        }
    }

    private static String filePath;

    private void saveToFile(Project project) {

        FileChooser faylTanla = new FileChooser();
        faylTanla.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xlsx files", "*.xlsx"),
                new FileChooser.ExtensionFilter("xls files", "*.xls")
        );

        if (filePath != null) {
            faylTanla.setInitialDirectory(new File(filePath));
        } else {
            faylTanla.setInitialDirectory(null);
        }

        faylTanla.setInitialFileName(project.getPrNomi().trim() + " " +
                LocalDate.now().format(dateFormatter)
        );

        File file = faylTanla.showSaveDialog(mainStage);
        if (file != null) {
            filePath = file.getParent();
            new SaveFile().saveFile(file.getAbsolutePath(), project);
        }
    }

    private void reviewProject( Project project) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/Views/reviewProject.fxml"));
        Parent parent = null;
        try {
            parent = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("List Products");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(mainStage);
        stage.setResizable(false);
        stage.show();

        ReviewProjectController controller = loader.getController();
        controller.setProject(project);
    }


}
