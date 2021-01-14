package sample.Moodles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.DecimalFormat;
import java.time.Duration;
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
    private LocalDateTime prTugallanganVaqti;
    private boolean done;

    private Stavkalar prStavkalar;

    private Client prClient;
    private Company prKmpCompany;
    private Xodimlar prRaxbar;
    private Xodimlar prKritgan;
    private Xodimlar prMasul;


    private double prDDPSumma;
    private double prKelishSumma;
    private double prDarSoliq;
    private double prDarSoliqStavka;

    private double prDDPliXarajatlar;
    private double prDDPliXarStavka;                 //*

    private double prNdsliXarajatlar;
    private double prNdsliXarStavka;                 //*


    private double prKichikCipXarajatlar;
    private double prKichikCipXarStavka;      //*
    private double prCipliXarajatlar;
    private double prCipliXarStavka;                 //*

    private double prSertifikatlash;
    private double prDekloratsiyaXizmati;
    private double prFoyda;
    private double prFoydaFoyiz;
    private double prCipFoyda;
    private double prCipFoydaFoyiz;


    private ObservableList<TovarZakaz> projectZakazList = FXCollections.observableArrayList();


    public Project(int numPr, LocalDate boshlanganVaqt, boolean prIsImportant,
                   boolean prIsShoshilinch,
                   String prNomi, Client prClient, Company prKmpCompany, Xodimlar prRaxbar,
                   Xodimlar prMasul, LocalDateTime tugashVaqti,
                   int typeCol, String prKomment, Xodimlar prKritgan
    ) {

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
        this.prFormulaNum = typeCol;           // 0 - Bez; 1 - S; 2-CIP;

        this.prStavkalar = new Stavkalar();


        this.proritet = "В срок";
        if (prIsImportant) {
            this.proritet = "Важны";
        }
        if (prIsShoshilinch) {
            this.proritet = "Срочный";
        }
        if (prIsImportant && prIsShoshilinch) {
            this.proritet = "Важны и Срочный";
        }
        setTypeCol(prFormulaNum);

    }

    private void setTypeCol(int typeCol) {
        switch (typeCol) {
            case 0: {
                this.prFormula = "DDP без НДС ВЭД";
                break;
            }
            case 1: {
                this.prFormula = "DDP c НДС ВЭД";
                break;
            }
            case 2: {
                this.prFormula = "CIP ВЭД";
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
                   ObservableList<TovarZakaz> projectZakazList,
                   Stavkalar prStavkalar) {

        this(numPr, boshlanganVaqt, prIsImportant,
                prIsShoshilinch,
                prNomi, prClient, prKmpCompany, prRaxbar,
                prMasul, tugashVaqti,
                typeCol, prKomment, prKritgan

        );
        this.prStavkalar = prStavkalar;
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
        return this.proritet;
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
        return this.projectZakazList;
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

    public LocalDateTime getPrTugallanganVaqti() {
        return prTugallanganVaqti;
    }

    public void setPrTugallanganVaqti(LocalDateTime prTugallanganVaqti) {
        this.prTugallanganVaqti = prTugallanganVaqti;
    }

    public int getPrFormulaNum() {
        return prFormulaNum;
    }

    public void setPrFormulaNum(int prFormulaNum) {
        this.prFormulaNum = prFormulaNum;
        setTypeCol(this.prFormulaNum);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setPrStavkalar(Stavkalar prStavkalar) {
        this.prStavkalar = prStavkalar;
    }

    public Stavkalar getPrStavkalar() {
        return prStavkalar;
    }


    public double getPrDDPSumma() {
        prDDPSumma = this.projectZakazList.stream().mapToDouble(TovarZakaz::getZakazDDPsumm).sum();
        return prDDPSumma;
    }

    public String getPrDDPSummaStr() {
        prDDPSumma = this.projectZakazList.stream().mapToDouble(TovarZakaz::getZakazDDPsumm).sum();
        return decimalFormat(prDDPSumma);
    }


    public double getPrKelishSumma() {
        prKelishSumma = this.projectZakazList.stream().mapToDouble(TovarZakaz::getZakazKelishSumm).sum();
        return prKelishSumma;
    }


    public String getPrKelishSummaStr() {
        return decimalFormat(getPrKelishSumma());
    }


    public double getPrDarSoliq() {

        prDarSoliq = 0;

        if (projectZakazList.size() != 0) {
            if (prFormulaNum == 0) {
                prDarSoliq = (getPrDDPSumma() - getPrKelishSumma()) * this.projectZakazList.get(0).getStNDS1Bez();
            } else if (prFormulaNum == 1) {
                prDarSoliq = (getPrDDPSumma() - getPrKelishSumma()) * this.projectZakazList.get(0).getStNDS2();
            }
        }
        return prDarSoliq;
    }

    public String getPrDarSoliqStr() {
        return decimalFormat(getPrDarSoliq());
    }

    public double getPrDarSoliqStavka() {
        prDarSoliqStavka = 0;
        if (projectZakazList.size() != 0) {
            if (prFormulaNum == 0) {
                prDarSoliqStavka = this.projectZakazList.get(0).getStNDS1Bez();
            } else if (prFormulaNum == 1) {
                prDarSoliqStavka = this.projectZakazList.get(0).getStNDS2();
            }
        }
        return prDarSoliqStavka;
    }

    public String getPrDarSoliqStavkaStr() {
        return (new DecimalFormat("#,##0.0").format(getPrDarSoliqStavka() * 100) + " %");
    }


    public double getPrDDPliXarajatlar() {
        this.prDDPliXarajatlar = getPrDDPSumma() * getPrDDPliXarStavka();
        return this.prDDPliXarajatlar;
    }

    public String getPrDDPliXarajatlarStr() {
        return decimalFormat(getPrDDPliXarajatlar());
    }


    public double getPrDDPliXarStavka() {
        return this.prDDPliXarStavka;
    }


    public void setPrDDPliXarStavka(double prDDPliXarStavka) {
        this.prDDPliXarStavka = prDDPliXarStavka;
    }

    public double getPrNDSliSumma() {
        return this.projectZakazList.stream().mapToDouble(TovarZakaz::getZakazNDS2liSumm).sum();
    }

    public String getPrNDSliSummaStr() {
        return decimalFormat(getPrNDSliSumma());
    }

    public double getPrNdsliXarajatlar() {
        this.prNdsliXarajatlar = getPrNDSliSumma() * prNdsliXarStavka;
        return this.prNdsliXarajatlar;
    }

    public String getPrNdsliXarajatlarStr() {
        return decimalFormat(getPrNdsliXarajatlar());
    }

    public double getPrNdsliXarStavka() {
        return prNdsliXarStavka;
    }

    public void setPrNdsliXarStavka(double prNdsliXarStavka) {
        this.prNdsliXarStavka = prNdsliXarStavka;
    }

    public double getPrSertifikatlash() {
        return prSertifikatlash;
    }

    public String getPrSertifikatlashStr() {
        return decimalFormat(getPrSertifikatlash());
    }

    public double getPrDekloratsiyaXizmati() {
        return prDekloratsiyaXizmati;
    }

    public String getPrDekloratsiyaXizmatiStr() {
        return decimalFormat(getPrDekloratsiyaXizmati());
    }

    public double getPrFoyda() {
        prFoyda = getPrDDPSumma() - (getPrKelishSumma() + getPrDarSoliq() + getPrDDPliXarajatlar()
                + getPrNdsliXarajatlar() + getPrSertifikatlash() + getPrDekloratsiyaXizmati());
        return prFoyda;
    }

    public String getPrFoydaStr() {
        return decimalFormat(getPrFoyda());
    }


    public double getPrFoydaFoyiz() {
        prFoydaFoyiz = (getPrFoyda() / getPrDDPSumma());
        return prFoydaFoyiz;
    }

    public String getPrFoydaFoyizStr() {
        return decimalFormat(getPrFoydaFoyiz() * 100) + " %";
    }


    public double getPrCipSummaUSD() {
        return projectZakazList.stream().mapToDouble(TovarZakaz::getZakazCIPSummUSD).sum();
    }

    public String getPrCipSummaUSDStr() {
        return decimalFormat(getPrCipSummaUSD());
    }

    public double getPrTransLiSummaCipUchunUSD() {
        return projectZakazList.stream().mapToDouble(TovarZakaz::getZakazTransLiSumma).sum();
    }

    public String getPrTransLiSummaCipUchunUSDStr() {
        return decimalFormat(getPrTransLiSummaCipUchunUSD());
    }

    public double getPrKichikCipXarajatlar() {
        prKichikCipXarajatlar = getPrCipSummaUSD() * prKichikCipXarStavka;
        return prKichikCipXarajatlar;
    }

    public String getPrKichikCipXarajatlarStr() {
        return decimalFormat(getPrKichikCipXarajatlar());
    }

    public double getPrKichikCipXarStavka() {
        return prKichikCipXarStavka;
    }

    public String getPrKichikCipXarajatlarStavkaStr() {
        return decimalFormat(getPrKichikCipXarStavka() * 100) + " %";
    }

    public void setPrKichikCipXarStavka(double prKichikCipXarStavka) {
        this.prKichikCipXarStavka = prKichikCipXarStavka;
    }


    public double getPrCipliXarajatlar() {
        prCipliXarajatlar = getPrCipSummaUSD() * prCipliXarStavka;
        return prCipliXarajatlar;
    }

    public String getPrCipliXarajatlarStr() {
        return decimalFormat(getPrCipliXarajatlar());
    }


    public double getPrCipliXarStavka() {
        return prCipliXarStavka;
    }

    public String getPrCipliXarStavkaStr() {
        return decimalFormat(getPrCipliXarStavka() * 100) + " %";
    }

    public void setPrCipliXarStavka(double prCipliXarStavka) {
        this.prCipliXarStavka = prCipliXarStavka;
    }

    public double getPrCipFoyda() {
        prCipFoyda = (getPrCipSummaUSD() - (getPrTransLiSummaCipUchunUSD()
                + getPrKichikCipXarajatlar() + getPrCipliXarajatlar()));
        return prCipFoyda;
    }

    public String getPrCipFoydaStr() {
        return decimalFormat(getPrCipFoyda());
    }

    public double getPrCipFoydaFoyiz() {
        prCipFoydaFoyiz = getPrCipFoyda() / getPrCipSummaUSD();
        return prCipFoydaFoyiz;
    }

    public String getPrCipFoydaFoyizStr() {
        return decimalFormat(getPrCipFoydaFoyiz() * 100) + " %";
    }


    private String decimalFormat(double d) {
        return new DecimalFormat("#,##0.00").format(d);
    }


    public String getQolganVaqt() {
        Duration duration = Duration.between(LocalDateTime.now(), this.tugashVaqti);
        long second = Math.abs((duration.toMillis() / 1000) % 60);
        long minutes = Math.abs(duration.toMinutes()) % 60;
        long hours = (Math.abs(duration.toHours()) % 24);
        long days = (Math.abs(duration.toDays()));

        if (LocalDateTime.now().isBefore(this.tugashVaqti)) {
            return days + " / " + hours + ":" + minutes + ":" + second;
        } else {
            return "- " + days + " / " + hours + ":" + minutes + ":" + second;
        }
    }


    @Override
    public String toString() {
        return "Project{" +
                "\nnumPr=" + numPr +
                ",\n tr_pr=" + tr_pr +
                ",\n boshlanganVaqt=" + boshlanganVaqt +
                ",\n tugashVaqti=" + tugashVaqti +
                ",\n proritet='" + proritet + '\'' +
                ",\n prIsImportant=" + prIsImportant +
                ",\n prIsShoshilinch=" + prIsShoshilinch +
                ",\n prNomi='" + prNomi + '\'' +
                ",\n prClient='" + prClient + '\'' +
                ",\n prKmpCompany='" + prKmpCompany + '\'' +
                ",\n prRaxbar='" + prRaxbar + '\'' +
                ",\n prKritgan='" + prKritgan + '\'' +
                ",\n prMasul='" + prMasul + '\'' +
                ",\n prFormula='" + prFormula + '\'' +
                ",\n prKomment='" + prKomment + '\'' +
                ",\n projectZakazList=" + projectZakazList +
                "\n}";
    }
}
