package sample.Controllers;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.Classes.Connections.*;
import sample.Moodles.*;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddProject implements Initializable {

    @FXML
    protected JFXComboBox<String> prKiritgan;

    @FXML
    protected JFXComboBox<String> prRahbar;

    @FXML
    protected JFXComboBox<String> prMasul;

    @FXML
    protected JFXTextField prName;

    @FXML
    protected JFXComboBox<String> prClient;

    @FXML
    protected JFXComboBox<String> prFromCom;

    @FXML
    protected JFXComboBox<String> prTypeCol;

    @FXML
    protected JFXCheckBox prIsImportant;           // muhim

    @FXML
    protected JFXCheckBox prIsShoshilinch;             // shoshilinch

    @FXML
    protected JFXDatePicker prDate;

    @FXML
    protected JFXTimePicker prTime;

    @FXML
    private Hyperlink prFile;

    @FXML
    protected JFXTextField prComment;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;


    private ObservableList<String> xodimlarsName = FXCollections.observableArrayList();
    private ObservableList<String> clientsName = FXCollections.observableArrayList();
    private ObservableList<String> companiesName = FXCollections.observableArrayList();


    private ObservableList<TovarZakaz> tovarZakazList = FXCollections.observableArrayList();
    private static String filePath = null;
    private FileChooser faylTanla;
    private File exportFile;

    private boolean isEdit = false;

    private int typeColVar = 1;

    private Project project;

    private EditTovar editTovar;

    private ControllerTable controllerTable;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private Stavkalar stavkalar;
    private ResourceBundle bundle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bundle = resources;
        okButton.setDisable(true);

        objectToName();

        prKiritgan.setItems(xodimlarsName);
        prRahbar.setItems(xodimlarsName);
        prMasul.setItems(xodimlarsName);

        prFromCom.setItems(companiesName);
        prClient.setItems(clientsName);

        prTypeCol.setItems(FXCollections.observableArrayList("DDP без НДС ВЭД", "DDP c НДС ВЭД", "CIP ВЭД"));


        prKiritgan.getEditor().textProperty().addListener(observable -> disContr());
        prRahbar.getEditor().textProperty().addListener(observable -> disContr());
        prMasul.getEditor().textProperty().addListener(observable -> disContr());
        prFromCom.getEditor().textProperty().addListener(observable -> disContr());
        prClient.getEditor().textProperty().addListener(observable -> disContr());
        prComment.textProperty().addListener(observable -> disContr());

        prTypeCol.getEditor().textProperty().addListener(observable -> initPrTypeCol());

        prIsImportant.setOnAction(e -> disContr());
        prIsShoshilinch.setOnAction(e -> disContr());


        if (filePath == null) {
            prFile.setText(bundle.getString("file"));
        } else {
            prFile.setText(filePath);
        }

        faylTanla = new FileChooser();
        faylTanla.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xlsx files", "*.xlsx"),
                new FileChooser.ExtensionFilter("xls files", "*.xls")

        );

        if (filePath != null) {
            faylTanla.setInitialDirectory(new File(filePath));
        } else {
            faylTanla.setInitialDirectory(null);
        }

        Callback<DatePicker, DateCell> kunFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return
                        new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(LocalDate.now().plusDays(1))) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: rgba(163,163,163,0.15)");
                                }

                            }
                        };
            }
        };

        prDate.setDayCellFactory(kunFactory);

    }


    private void initPrTypeCol() {
        typeColVar = prTypeCol.getSelectionModel().getSelectedIndex();
        disContr();
    }

    private void objectToName() {
        new XodimlarConnections().getXodimlarFromSql().forEach(e -> xodimlarsName.add(e.getIsm()));
        new ClientConnections().getClientFromSql().forEach(e -> clientsName.add(e.getName()));
        new CompanyConnections().getCompanyFromSql().forEach(e -> companiesName.add(e.getName()));
    }

    private void initProject() {


        Xodimlar kiritgan = new XodimlarConnections().getXodimlarFromSql().stream().
                filter(e -> e.getIsm().trim().equals(prKiritgan.getValue().trim()))
                .findAny()
                .orElse(new XodimlarConnections().insertToXodimlar(
                        new Xodimlar(prKiritgan.getValue().trim())
                        )
                );


        Xodimlar raxbar = new XodimlarConnections().getXodimlarFromSql().stream().
                filter(
                        e -> e.getIsm().trim().toLowerCase().equals(prRahbar.getValue().trim().toLowerCase())
                )
                .findAny()
                .orElse(new XodimlarConnections().insertToXodimlar(
                        new Xodimlar(prRahbar.getValue().trim())
                        )
                );


        Xodimlar masul = new XodimlarConnections().getXodimlarFromSql().stream().
                filter(
                        e -> e.getIsm().trim().toLowerCase().equals(prMasul.getValue().trim().toLowerCase())
                )
                .findAny()
                .orElse(new XodimlarConnections().insertToXodimlar(
                        new Xodimlar(prMasul.getValue().trim())
                        )
                );

        Client client = new ClientConnections().getClientFromSql().stream().
                filter(
                        e -> e.getName().trim().equals(prClient.getValue().trim())
                )
                .findAny()
                .orElse(new ClientConnections().insertToClient(
                        new Client(prClient.getValue().trim())
                ));


        Company company = new CompanyConnections().getCompanyFromSql().stream().
                filter(
                        e -> e.getName().trim().equals(prFromCom.getValue().trim())
                ).
                findAny()
                .orElse(new CompanyConnections().insertToCompany(
                        new Company(prFromCom.getValue().trim())
                ));


        if (isEdit) {

            project.setPrIsImportant(prIsImportant.isSelected());
            project.setPrIsShoshilinch(prIsShoshilinch.isSelected());
            project.setPrNomi(prName.getText().trim());
            project.setPrClient(client);
            project.setPrKmpCompany(company);
            project.setPrRaxbar(raxbar);
            project.setPrMasul(masul);
            project.setPrFormulaNum(prTypeCol.getSelectionModel().getSelectedIndex());
            project.setTugashVaqti(LocalDateTime.of(prDate.getValue(), prTime.getValue()));
            project.setPrKomment(prComment.getText().trim());
            project.setPrKritgan(kiritgan);

        } else {
            project = new Project(
                    -1, LocalDate.now(), prIsImportant.isSelected(), prIsShoshilinch.isSelected(),
                    prName.getText().trim(),
                    client,
                    company,
                    raxbar,
                    masul,
                    LocalDateTime.of(prDate.getValue(), prTime.getValue()),
                    (prTypeCol.getSelectionModel().getSelectedIndex()),
                    prComment.getText().trim(),
                    kiritgan,
                    tovarZakazList,
                    stavkalar
            );
            System.out.println("init project ");
            System.out.println(project.toString());
        }

    }

    @FXML
    void cancelAdd(ActionEvent event) {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    void okAdd() {

        initProject();
        if (isEdit) {

            System.out.println(project);
            new ProjectConnections().updateProject(project);

            if (exportFile != null) {

                new SaveFile().saveFile(exportFile.getAbsolutePath(), project);
                okButton.getScene().getWindow().hide();

            }
            okButton.getScene().getWindow().hide();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(bundle.getString("editedAlertMass"));
            alert.showAndWait();

            editTovar.refreshProjectList();

        } else {
            if (exportFile == null) {
                saveFile();
            } else {

                new ProjectConnections().insertToProject(project);

                new SaveFile().saveFile(exportFile.getAbsolutePath(), project);
                okButton.getScene().getWindow().hide();


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(bundle.getString("createdAlertMass"));
                alert.showAndWait();

                TovarZakaz.tovarZakazList.clear();
                controllerTable.summaHisobla();
                controllerTable.setDisableNextExportBt();
            }

        }
    }


    public void setPrHisobTuri(int typeCol, ObservableList<TovarZakaz> tovarZakazList) {

        this.tovarZakazList.clear();
        this.tovarZakazList.addAll(tovarZakazList);

//        if (typeCol == 1) {
//            prTypeCol.setValue("DDP c НДС ВЭД");  //1
//        } else if (typeCol == 0) {
//            prTypeCol.setValue("DDP без НДС ВЭД");  // 0
//        } else if (typeCol == 2) {
//            prTypeCol.setValue("CIP ВЭД");
//        }

        prTypeCol.getSelectionModel().select(typeCol);

        System.out.println("setPrHisobTuri ishladi = " + prTypeCol.getSelectionModel().getSelectedIndex());

        //2 CIP
    }

    public void setEditTovar(EditTovar editTovar) {
        this.editTovar = editTovar;
    }

    public void setControllerTable(ControllerTable controllerTable) {
        this.controllerTable = controllerTable;
    }


    @FXML
    void saveFile() {

        faylTanla.setInitialFileName(prName.getText().trim() + " " +
                LocalDate.now().format(dateFormatter)
        );

        File file = faylTanla.showSaveDialog(prFile.getScene().getWindow());

        if (file != null) {
            exportFile = file;
            filePath = exportFile.getParent();
            prFile.setText(exportFile.getPath());
            faylTanla.setInitialDirectory(exportFile.getParentFile());
            faylTanla.setInitialFileName(exportFile.getName());
        }
        disContr();
    }


    @FXML
    public void disContr() {

        boolean tuliqmi = (
                !(prName.getText().trim().isEmpty())
                        && !(prName.getText().trim().isEmpty())
                        && prKiritgan.getValue() != null
                        && !prKiritgan.getEditor().getText().trim().isEmpty()
                        && prRahbar.getValue() != null
//                        && !prRahbar.getEditor().getText().trim().isEmpty()
                        && prMasul.getValue() != null
//                        && !prMasul.getEditor().getText().trim().isEmpty()
                        && prClient.getValue() != null
//                        && !prClient.getEditor().getText().trim().isEmpty()
                        && prFromCom.getValue() != null
//                        && !prFromCom.getEditor().getText().trim().isEmpty()
                        && prTypeCol.getValue() != null
//                        && !prTypeCol.getEditor().getText().trim().isEmpty()
                        && (prDate.getValue() != null)
                        && (prTime.getValue() != null)
        );
        okButton.setDisable(!tuliqmi);

        prTime.getEditor().setFont(new Font(18));

    }

    private void initWindow() {


        prKiritgan.setDisable(!isEdit);

        prKiritgan.setValue(project.getPrKritgan().getIsm());
        prRahbar.setValue(project.getPrRaxbar().getIsm());
        prMasul.setValue(project.getPrMasul().getIsm());
        prName.setText(project.getPrNomi());
        prClient.setValue(project.getPrClient().getName());
        prFromCom.setValue(project.getPrKmpCompany().getName());
        prTypeCol.setValue(project.getPrFormula());

        prIsShoshilinch.setSelected(project.isPrIsShoshilinch());
        prIsImportant.setSelected(project.isPrIsImportant());

        prDate.setValue(project.getTugashVaqti().toLocalDate());
        prTime.setValue(project.getTugashVaqti().toLocalTime());

        prComment.setText(project.getPrKomment());

    }


    public void setEdit(boolean edit) {
        isEdit = edit;
    }


    public void setProject(Project project) {
        this.project = project;
        initWindow();
    }

    public void setStavkalar(Stavkalar stavkalar) {
        this.stavkalar = stavkalar;
    }


    @Override
    public String toString() {
        return "AddProject{" +
                "prKiritgan=" + prKiritgan.getValue() +
                ", prRahbar=" + prRahbar.getValue() +
                ", prMasul=" + prMasul.getValue() +
                ", prName=" + prName.getText() +
                ", prClient=" + prClient.getValue() +
                ", prFromCom=" + prFromCom.getValue() +
                ", prTypeCol=" + prTypeCol.getValue() +
                ", prIsImportant=" + prIsImportant.getText() +
                ", prIsShoshilinch=" + prIsShoshilinch.getText() +
                ", prDate=" + prDate.getValue() +
                ", prTime=" + prTime.getValue() +
                ", prFile=" + prFile.getText() +
                ", prComment=" + prComment.getText() +
                '}';
    }
}