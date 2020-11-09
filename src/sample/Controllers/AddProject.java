package sample.Controllers;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Classes.Connections;
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

    private ObservableList<Xodimlar> xodimlars = FXCollections.observableArrayList(new Connections().getXodimlarFromSql());
    private ObservableList<Client> clients = FXCollections.observableArrayList(new Connections().getClientFromSql());
    private ObservableList<Company> companies = FXCollections.observableArrayList(new Connections().getCompanyFromSql());


    private ObservableList<String> xodimlarsName = FXCollections.observableArrayList();
    private ObservableList<String> clientsName = FXCollections.observableArrayList();
    private ObservableList<String> companiesName = FXCollections.observableArrayList();


    private ObservableList<TovarZakaz> tovarZakazList = FXCollections.observableArrayList();
    private String filePre = "Файл: - ";
    private static String filePath = null;
    private Stage ownerStage;
    private FileChooser faylTanla;
    private File exportFile;

    private boolean isEdit = false;

    private Project project;

    private EditTovar editTovar;

    private ControllerTable controllerTable;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        okButton.setDisable(true);

        objectTOName();

        prKiritgan.setItems(xodimlarsName);
        prRahbar.setItems(xodimlarsName);
        prMasul.setItems(xodimlarsName);

        prFromCom.setItems(companiesName);
        prClient.setItems(clientsName);


        prTime.getEditor().setFont(new Font(18));
        prKiritgan.getEditor().setFont(new Font(18));
        prRahbar.getEditor().setFont(new Font(18));
        prMasul.getEditor().setFont(new Font(18));
        prTypeCol.getEditor().setFont(new Font(18));
        prFromCom.getEditor().setFont(new Font(18));
        prClient.getEditor().setFont(new Font(18));
        prDate.getEditor().setFont(new Font(18));

        prTypeCol.getEditor().setEditable(false);

        prKiritgan.getEditor().textProperty().addListener(observable -> disContr());
        prRahbar.getEditor().textProperty().addListener(observable -> disContr());
        prMasul.getEditor().textProperty().addListener(observable -> disContr());
        prTypeCol.getEditor().textProperty().addListener(observable -> disContr());
        prFromCom.getEditor().textProperty().addListener(observable -> disContr());
        prClient.getEditor().textProperty().addListener(observable -> disContr());


        if (filePath == null) {
            prFile.setText(filePre);
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

    }

    private void objectTOName() {
        xodimlars.forEach(e -> xodimlarsName.add(e.getIsm()));
        clients.forEach(e -> clientsName.add(e.getName()));
        companies.forEach(e -> companiesName.add(e.getName()));
    }

    private void initProject() {
        int typeCol;
        switch (prTypeCol.getValue().trim()) {
            case "DDP без НДС ВЭД": {
                typeCol = 0;
                break;
            }
            case "DDP c НДС ВЭД": {
                typeCol = 1;
                break;
            }
            default: {
                typeCol = -1;
                break;
            }
        }

        Xodimlar kiritgan = xodimlars.stream().
                filter(e -> e.getIsm().trim().equals(prKiritgan.getValue().trim()))
                .findAny()
                .orElse(new Connections().insertToXodimlar(
                        new Xodimlar(prKiritgan.getValue().trim())
                        )
                );



        Xodimlar raxbar = xodimlars.stream().
                filter(
                        e -> e.getIsm().trim().toLowerCase().equals(prRahbar.getValue().trim().toLowerCase())
                )
                .findAny()
                .orElse(new Connections().insertToXodimlar(
                        new Xodimlar(prRahbar.getValue().trim())
                )
                );


        Xodimlar masul = xodimlars.stream().
                filter(
                        e -> e.getIsm().trim().toLowerCase().equals(prMasul.getValue().trim().toLowerCase())
                )
                .findAny()
                .orElse(new Connections().insertToXodimlar(
                        new Xodimlar(prMasul.getValue().trim())
                        )
                );

        Client client = clients.stream().
                filter(
                        e -> e.getName().trim().equals(prClient.getValue().trim())
                ).
                findAny()
                .orElse(new Connections().insertToClient(
                        new Client(prClient.getValue().trim())
                ));


        Company company = companies.stream().
                filter(
                        e -> e.getName().trim().equals(prFromCom.getValue().trim())
                ).
                findAny()
                .orElse(new Connections().insertToCompany(
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
                    typeCol, prComment.getText().trim(),
                    kiritgan,
                    tovarZakazList
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
            new Connections().updateProject(project);

            if (exportFile != null) {

                    new SaveFile().saveFile(exportFile.getAbsolutePath(), project);
                    okButton.getScene().getWindow().hide();

            }
            okButton.getScene().getWindow().hide();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Success edit!");
            alert.showAndWait();

            editTovar.refreshProjectList();

        } else {
            if (exportFile == null) {
                saveFile();
            } else {

                    new SaveFile().saveFile(exportFile.getAbsolutePath(), project);
                    okButton.getScene().getWindow().hide();

                    new Connections().insertToProject(project);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Success create!");
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
        if (typeCol == 1) {
            prTypeCol.setValue("DDP c НДС ВЭД");  //1
        } else if (typeCol == 0) {
            prTypeCol.setValue("DDP без НДС ВЭД");  // 0
        }
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

        faylTanla.setInitialFileName(prName.getText().trim() + " "+
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

    }


    @FXML
    public void disContr() {

        boolean tuliqmi = (
                !(prName.getText().trim().isEmpty())
                        && !(prName.getText().trim().isEmpty())
                        && (prKiritgan.getValue() != null && !prKiritgan.getEditor().getText().trim().isEmpty())
                        && (prRahbar.getValue() != null && !prRahbar.getEditor().getText().trim().isEmpty())
                        && (prMasul.getValue() != null && !prMasul.getEditor().getText().trim().isEmpty())
                        && (prClient.getValue() != null && !prClient.getEditor().getText().trim().isEmpty())
                        && (prFromCom.getValue() != null && !prFromCom.getEditor().getText().trim().isEmpty())
                        && (prTypeCol.getValue() != null && !prTypeCol.getEditor().getText().trim().isEmpty())
                        && (prDate.getValue() != null)
                        && (prTime.getValue() != null)
        );
        okButton.setDisable(!tuliqmi);

        prTime.getEditor().setFont(new Font(18));

    }

    private void initWindow() {

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


    void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
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