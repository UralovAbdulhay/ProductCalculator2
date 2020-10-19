package sample.Moodles;


import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Project {

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private int numPr;
    private int tr_pr;
    private LocalDateTime boshlanganVaqt;
    private LocalDateTime tugashVaqti;
    private String proritet;
    private boolean prIsImportant;
    private boolean prIsShoshilinch;
    private String prNomi;
    private String prKlient;
    private String prKMP_komp;
    private String prRaxbar;
    private String prKritgan;
    private String prMasul;
    private String prFormula;
    private String prKomment;

    private ObservableList<TovarZakaz> projectZakazList;


    public Project(int numPr, LocalDateTime boshlanganVaqt, boolean prIsImportant,
                   boolean prIsShoshilinch,
                   String prNomi, String prKlient, String prKMP_komp, String prRaxbar,
                   String prMasul, LocalDateTime tugashVaqti,
                   String prFormula, String prKomment, String prKritgan,
                   ObservableList<TovarZakaz> projectZakazList ) {

        this.numPr = numPr;
        this.boshlanganVaqt = boshlanganVaqt;
        this.prIsImportant = prIsImportant;
        this.prIsShoshilinch = prIsShoshilinch;
        this.prNomi = prNomi;
        this.prKlient = prKlient;
        this.prKMP_komp = prKMP_komp;
        this.prRaxbar = prRaxbar;
        this.prMasul = prMasul;
        this.tugashVaqti = tugashVaqti;
        this.prFormula = prFormula;
        this.prKomment = prKomment;
        this.projectZakazList = projectZakazList;
        this.prKritgan = prKritgan;
        this.proritet = "";

        if (prIsImportant) {
            this.proritet = "Важны";
        }
        if (prIsShoshilinch) {
            this.proritet = "Срочный";
        }
        if (prIsImportant && prIsShoshilinch) {
            this.proritet = "Важны и Срочный";
        }
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

    public LocalDateTime getTugashVaqti() {
        return tugashVaqti;
    }

    public void setTugashVaqti(LocalDateTime tugashVaqti) {
        this.tugashVaqti = tugashVaqti;
    }

    public String getProritet() {
        return proritet;
    }

    public void setProritet(String proritet) {
        this.proritet = proritet;
    }

    public boolean isPrIsImportant() {
        return prIsImportant;
    }

    public void setPrIsImportant(boolean prIsImportant) {
        this.prIsImportant = prIsImportant;
    }

    public boolean isPrIsShoshilinch() {
        return prIsShoshilinch;
    }

    public void setPrIsShoshilinch(boolean prIsShoshilinch) {
        this.prIsShoshilinch = prIsShoshilinch;
    }

    public String getPrNomi() {
        return prNomi;
    }

    public void setPrNomi(String prNomi) {
        this.prNomi = prNomi;
    }

    public String getPrKlient() {
        return prKlient;
    }

    public void setPrKlient(String prKlient) {
        this.prKlient = prKlient;
    }

    public String getPrKMP_komp() {
        return prKMP_komp;
    }

    public void setPrKMP_komp(String prKMP_komp) {
        this.prKMP_komp = prKMP_komp;
    }

    public String getPrRaxbar() {
        return prRaxbar;
    }

    public void setPrRaxbar(String prRaxbar) {
        this.prRaxbar = prRaxbar;
    }

    public String getPrKritgan() {
        return prKritgan;
    }

    public void setPrKritgan(String prKritgan) {
        this.prKritgan = prKritgan;
    }

    public String getPrMasul() {
        return prMasul;
    }

    public void setPrMasul(String prMasul) {
        this.prMasul = prMasul;
    }

    public String getPrFormula() {
        return prFormula;
    }

    public void setPrFormula(String prFormula) {
        this.prFormula = prFormula;
    }

    public String getPrKomment() {
        return prKomment;
    }

    public void setPrKomment(String prKomment) {
        this.prKomment = prKomment;
    }

    public ObservableList<TovarZakaz> getProjectZakazList() {
        return projectZakazList;
    }

    public void setProjectZakazList(ObservableList<TovarZakaz> projectZakazList) {
        this.projectZakazList = projectZakazList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "tr_pr=" + tr_pr +
                ", boshlanganVaqt=" + boshlanganVaqt +
                ", tugashVaqti=" + tugashVaqti +
                ", proritet='" + proritet + '\'' +
                ", prIsImportant=" + prIsImportant +
                ", prIsShoshilinch=" + prIsShoshilinch +
                ", prNomi='" + prNomi + '\'' +
                ", prKlient='" + prKlient + '\'' +
                ", prKMP_komp='" + prKMP_komp + '\'' +
                ", prRaxbar='" + prRaxbar + '\'' +
                ", prKritgan='" + prKritgan + '\'' +
                ", prMasul='" + prMasul + '\'' +
                ", prFormula='" + prFormula + '\'' +
                ", prKomment='" + prKomment + '\'' +
                ", projectZakazList=" + projectZakazList +
                '}';
    }
}
