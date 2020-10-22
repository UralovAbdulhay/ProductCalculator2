package sample.Moodles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Project {

    public static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private int numPr;
    private int tr_pr;
    private LocalDate boshlanganVaqt;
    private LocalDateTime tugashVaqti;
    private String proritet;
    private boolean prIsImportant;
    private boolean prIsShoshilinch;
    private String prNomi;
    private String prFormula;
    private int prFormulaNum;
    private String prKomment;


//    private String prClient;
//    private String prKmpCompany;
//    private String prRaxbar;
//    private String prKritgan;
//    private String prMasul;

    Client prClient;
    Company prKmpCompany;
    Xodimlar prRaxbar;
    Xodimlar prKritgan;
    Xodimlar prMasul;


    private ObservableList<TovarZakaz> projectZakazList = FXCollections.observableArrayList();


    public Project(int numPr, LocalDate boshlanganVaqt, boolean prIsImportant,
                   boolean prIsShoshilinch,
                   String prNomi, Client prClient, Company prKmpCompany, Xodimlar prRaxbar,
                   Xodimlar prMasul, LocalDateTime tugashVaqti,
                   int typeCol, String prKomment, Xodimlar prKritgan) {

        this.numPr = numPr;
        this.boshlanganVaqt = boshlanganVaqt;
        this.prIsImportant = prIsImportant;
        this.prIsShoshilinch = prIsShoshilinch;
        this.prNomi = prNomi;
        this.prClient = prClient;
        this.prKmpCompany = prKmpCompany;
        this.prRaxbar = prRaxbar;
        this.prMasul = prMasul;
        this.tugashVaqti = tugashVaqti;
        this.prKomment = prKomment;
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

        switch (typeCol) {
            case 0: {
                this.prFormula = "DDP без НДС ВЭД";
                break;
            }
            case 1: {
                this.prFormula = "DDP c НДС ВЭД";
                break;
            }
            default: {
                this.prFormula = "--";
                break;
            }
        }
    }

    public Project(int numPr, LocalDate boshlanganVaqt, boolean prIsImportant,
                   boolean prIsShoshilinch,
                   String prNomi, Client prClient, Company prKmpCompany, Xodimlar prRaxbar,
                   Xodimlar prMasul, LocalDateTime tugashVaqti,
                   int typeCol, String prKomment, Xodimlar prKritgan,
                   ObservableList<TovarZakaz> projectZakazList) {


        this(numPr, boshlanganVaqt, prIsImportant,
                prIsShoshilinch,
                prNomi, prClient, prKmpCompany, prRaxbar,
                prMasul, tugashVaqti,
                typeCol, prKomment, prKritgan
        );
        this.projectZakazList.addAll(projectZakazList);
    }

    public int getTr_pr() {
        return tr_pr;
    }

    public void setTr_pr(int tr_pr) {
        this.tr_pr = tr_pr;
    }

    public LocalDate getBoshlanganVaqt() {
        return boshlanganVaqt;
    }

    public void setBoshlanganVaqt(LocalDate boshlanganVaqt) {
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

    public Client getPrClient() {
        return prClient;
    }

    public void setPrClient(Client prClient) {
        this.prClient = prClient;
    }

    public Company getPrKmpCompany() {
        return prKmpCompany;
    }

    public void setPrKmpCompany(Company prKmpCompany) {
        this.prKmpCompany = prKmpCompany;
    }

    public Xodimlar getPrRaxbar() {
        return prRaxbar;
    }

    public void setPrRaxbar(Xodimlar prRaxbar) {
        this.prRaxbar = prRaxbar;
    }

    public Xodimlar getPrKritgan() {
        return prKritgan;
    }

    public void setPrKritgan(Xodimlar prKritgan) {
        this.prKritgan = prKritgan;
    }

    public Xodimlar getPrMasul() {
        return prMasul;
    }

    public void setPrMasul(Xodimlar prMasul) {
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

    public void addProjectZakazList(ObservableList<TovarZakaz> projectZakazList) {
        this.projectZakazList.addAll(projectZakazList);
    }
    public void addProjectZakazList(TovarZakaz projectZakazList) {
        this.projectZakazList.addAll(projectZakazList);
    }

    public int getNumPr() {
        return numPr;
    }

    public void setNumPr(int numPr) {
        this.numPr = numPr;
    }

    @Override
    public String toString() {
        return "Project{" +
                "numPr=" + numPr +
                ", tr_pr=" + tr_pr +
                ", boshlanganVaqt=" + boshlanganVaqt +
                ", tugashVaqti=" + tugashVaqti +
                ", proritet='" + proritet + '\'' +
                ", prIsImportant=" + prIsImportant +
                ", prIsShoshilinch=" + prIsShoshilinch +
                ", prNomi='" + prNomi + '\'' +
                ", prClient='" + prClient + '\'' +
                ", prKmpCompany='" + prKmpCompany + '\'' +
                ", prRaxbar='" + prRaxbar + '\'' +
                ", prKritgan='" + prKritgan + '\'' +
                ", prMasul='" + prMasul + '\'' +
                ", prFormula='" + prFormula + '\'' +
                ", prKomment='" + prKomment + '\'' +
                ", projectZakazList=" + projectZakazList +
                '}';
    }
}
