package sample.Moodles;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sample.Classes.ProjectFile;
import sample.Classes.TimeHisobla;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Proyekt {

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    private int tr_pr;
    private LocalDateTime boshlanganVaqt;
    private String proritet;
    private String prNomi;
    private String klient;
    private String KMP_komp;
    private String raxbar;
    private String masul;
    private LocalDateTime tugashVaqti;
    private TimeHisobla qoganVaqt;
    private String formula;
    private ProjectFile file;
    private String prKomment;

    private String stringVaqt;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton okButton;

    @FXML
    void cancelAdd(ActionEvent event) {

    }



    @FXML
    void okAdd(ActionEvent event) {

    }




    public Proyekt(int tr_pr, LocalDateTime boshlanganVaqt, String proritet,
                   String prNomi, String klient, String KMP_komp, String raxbar,
                   String masul, LocalDateTime tugashVaqti, TimeHisobla qoganVaqt,
                   String formula, ProjectFile file, String prKomment) {

        this.tr_pr = tr_pr;
        this.boshlanganVaqt = boshlanganVaqt;
        this.proritet = proritet;
        this.prNomi = prNomi;
        this.klient = klient;
        this.KMP_komp = KMP_komp;
        this.raxbar = raxbar;
        this.masul = masul;
        this.tugashVaqti = tugashVaqti;
        this.qoganVaqt = qoganVaqt;
        this.formula = formula;
        this.file = file;
        this.prKomment = prKomment;
    }

    public static DateTimeFormatter getFormat() {
        return format;
    }

    public int getTr_pr() {
        return tr_pr;
    }

    public void setTr_pr(int tr_pr) {
        this.tr_pr = tr_pr;
    }

    public LocalDateTime getBoshlanganVaqt() {
        return boshlanganVaqt;
    }

    public void setBoshlanganVaqt(LocalDateTime boshlanganVaqt) {
        this.boshlanganVaqt = boshlanganVaqt;
    }

    public String getProritet() {
        return proritet;
    }

    public void setProritet(String proritet) {
        this.proritet = proritet;
    }

    public String getPrNomi() {
        return prNomi;
    }

    public void setPrNomi(String prNomi) {
        this.prNomi = prNomi;
    }

    public String getKlient() {
        return klient;
    }

    public void setKlient(String klient) {
        this.klient = klient;
    }

    public String getKMP_komp() {
        return KMP_komp;
    }

    public void setKMP_komp(String KMP_komp) {
        this.KMP_komp = KMP_komp;
    }

    public String getRaxbar() {
        return raxbar;
    }

    public void setRaxbar(String raxbar) {
        this.raxbar = raxbar;
    }

    public String getMasul() {
        return masul;
    }

    public void setMasul(String masul) {
        this.masul = masul;
    }

    public LocalDateTime getTugashVaqti() {
        return tugashVaqti;
    }

    public void setTugashVaqti(LocalDateTime tugashVaqti) {
        this.tugashVaqti = tugashVaqti;
    }

    public TimeHisobla getQoganVaqt() {
        return qoganVaqt;
    }

    public void setQoganVaqt(TimeHisobla qoganVaqt) {
        this.qoganVaqt = qoganVaqt;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public ProjectFile getFile() {
        return file;
    }

    public void setFile(ProjectFile file) {
        this.file = file;
    }

    public String getPrKomment() {
        return prKomment;
    }

    public void setPrKomment(String prKomment) {
        this.prKomment = prKomment;
    }

    @Override
    public String toString() {
        return "Proyekt{" +
                "tr_pr=" + tr_pr +
                ", boshlanganVaqt=" + boshlanganVaqt +
                ", proritet='" + proritet + '\'' +
                ", prNomi='" + prNomi + '\'' +
                ", klient='" + klient + '\'' +
                ", KMP_komp='" + KMP_komp + '\'' +
                ", raxbar='" + raxbar + '\'' +
                ", masul='" + masul + '\'' +
                ", tugashVaqti=" + tugashVaqti +
                ", qoganVaqt=" + qoganVaqt +
                ", formula='" + formula + '\'' +
                ", file=" + file +
                ", prKomment='" + prKomment + '\'' +
                '}';
    }








}
