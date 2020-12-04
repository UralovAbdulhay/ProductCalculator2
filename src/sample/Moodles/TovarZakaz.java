package sample.Moodles;

import com.jfoenix.controls.JFXButton;
import com.sun.istack.internal.Nullable;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Paint;
import sample.Controllers.ControllerTable;

import java.time.LocalDate;

public class TovarZakaz extends Tovar {


    static String kursQiymati = "usd";

    private int zakazSoni = 1;
    private Button zakazKamaytirishBt;


    private Spinner<Integer> zakazUzgartir;
    private JFXButton zakazUchirishBt;

    private double zakazSummaExw;
    private double zakazTransProNatija;
    private double zakazTransNarxi;
    private double zakazTransSumm;
    private double zakazTransLiSumma;
    private double zakazTransLiNarx;

    /* private maydonlar  */

    private double zakazCIPStavka;
    private double zakazCIPNarxiUSD;
    private double zakazCIPSummUSD;
    private double zakazCIPNarxiUSZ;
    private double zakazBojYigini;
    private double zakazPoshlinaSumm;
    private double zakazAksizSumm;
    private double zakazNDS1Narxi;
    private double zakazKelishNarxi;
    private double zakazKelishSumm;

    /*    pivate  maydon oxiri  */


    private double zakazDDPnarxi;
    private double zakazDDPsumm;
    private double zakazNDS2liNarxi;
    private double zakazNDS2liSumm;

//    public static double zakUsdUsz;
//    public static double zakEurUsz;
//    public static double zakRubUsz;



    public static boolean selected;

    private ControllerTable controllerTable;

    public static ObservableList<TovarZakaz> tovarZakazList = FXCollections.observableArrayList();
    private Stavkalar stavkalar;


    public TovarZakaz( PriseList priseList, @Nullable Stavkalar stavkalar) {
        super(priseList.getTovar());

        if (stavkalar == null) {
            this.stavkalar = new Stavkalar();
        } else {
            this.stavkalar = stavkalar;
        }

        this.zakazSoni = priseList.getAddCount();                         // zakaz qilingan tovar soni

        this.zakazSummaExw = this.getTovarNarxi() * this.zakazSoni;                   //  n ta tovar summasi
        this.zakazTransProNatija = this.getTovarTransportNarxi() * this.stavkalar.getStTrans();       //  this.tovar ning natijaviy trans. stavkasi
        this.zakazTransNarxi = this.getTovarNarxi() * this.zakazTransProNatija;        //  har bir tovar uchun trans narxi
        this.zakazTransSumm = this.zakazSummaExw * this.zakazTransProNatija;               //  berilgan zakaz uchun trans summasi
        this.zakazTransLiNarx = this.getTovarNarxi() + this.zakazTransNarxi;          //  har bir tovar uchun transport bilan  birgalikdagi Narxi
        this.zakazTransLiSumma = this.zakazTransLiNarx * this.zakazSoni;                     //  berilgan zakaz uchun transport bilan birgalikdagi Summa

        /* private maydonlar  */
        this.zakazCIPStavka = this.stavkalar.getStCIP();
        this.zakazCIPNarxiUSD = this.zakazTransLiNarx / this.stavkalar.getStCIP();                     // CIP Narxi   USD
        this.zakazCIPSummUSD = this.zakazCIPNarxiUSD * this.zakazSoni;                   // CIP summasi USD

        this.zakazCIPNarxiUSZ = this.zakazCIPNarxiUSD * this.stavkalar.getStUSD_USZ();                   // CIP Narxi UZS
//        this.zakazCIPNarxiUSZ = this.zakazCIPNarxiUSD * zakUsdUsz;                   // CIP Narxi UZS
        double a = this.zakazCIPNarxiUSZ;

        this.zakazBojYigini = a * this.stavkalar.getStBojxona();                                     // bojxona yiginlari UZS
        double b = this.zakazBojYigini;

        this.zakazPoshlinaSumm = a * this.getTovarPoshlina();                    // Poshlina summasi UZS
        double c = this.zakazPoshlinaSumm;

        this.zakazAksizSumm = a * this.getTovarAksiz();                   //   Aksiz summasi UZS
        double d = this.zakazAksizSumm;


        if (selected) {
            this.zakazNDS1Narxi = ((a + b + c + d) * this.stavkalar.getStNDS1S());                             //  NDS1  miqdori
        } else {
            this.zakazNDS1Narxi = ((a + b + c + d) * this.stavkalar.getStNDS1Bez());                             //  NDS1  miqdori
        }

        double k = this.zakazNDS1Narxi;

        this.zakazKelishNarxi = a + b + c + d + k;                                       // bitta tovarning yetib kelish narxi
        this.zakazKelishSumm = this.zakazKelishNarxi * this.zakazSoni;                   // tovarning berilgan  miqdoridagi kelish summasi

        /*    pivate  maydon oxiri  */


        this.zakazDDPnarxi = this.zakazKelishNarxi / super.getTovarDDP();                                 // DDP narxi
        this.zakazDDPsumm = this.zakazDDPnarxi * this.zakazSoni;                                //  DDP summasi
        this.zakazNDS2liNarxi = this.zakazDDPnarxi + (this.zakazDDPnarxi * this.stavkalar.getStNDS2());        // NDS2 li narx
        this.zakazNDS2liSumm = this.zakazNDS2liNarxi * this.zakazSoni;                           // NDS2 li summa


        this.zakazUzgartir = new Spinner<>(1, 1_000_000, this.zakazSoni, 1);
        this.zakazUzgartir.setEditable(false);


        this.zakazUchirishBt = new JFXButton();
        this.zakazUchirishBt.setOnAction(event -> {
            new ControllerTable().orderBekor();
        });

        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_BOX);
        icon.setGlyphSize(25);
        icon.setFill(Paint.valueOf("#2d3b9d"));
        this.zakazUchirishBt.setGraphic(icon);

        zakazHisobla();

    }


    public void zakazHisobla() {

        this.zakazSummaExw = (this.getTovarNarxi() * this.zakazSoni);                   //  n ta tovar summasi
        this.zakazTransProNatija = (this.getTovarTransportNarxi() * stavkalar.getStTrans());       //  this.tovar ning natijaviy trans. stavkasi
        this.zakazTransNarxi = (this.getTovarNarxi() * this.zakazTransProNatija);        //  har bir tovar uchun trans narxi
        this.zakazTransSumm = (this.zakazSummaExw * this.zakazTransProNatija);               //  berilgan zakaz uchun trans summasi
        this.zakazTransLiNarx = (this.getTovarNarxi() + this.zakazTransNarxi);          //  har bir tovar uchun transport bilan  birgalikdagi Narxi
        this.zakazTransLiSumma = (this.zakazTransLiNarx * this.zakazSoni);                     //  berilgan zakaz uchun transport bilan birgalikdagi Summa

        /* private maydonlar  */

        this.zakazCIPNarxiUSD = (this.zakazTransLiNarx / stavkalar.getStCIP());                     // CIP Narxi   USD
        this.zakazCIPSummUSD = (this.zakazCIPNarxiUSD * this.zakazSoni);                   // CIP summasi USD

        this.zakazCIPNarxiUSZ = (this.zakazCIPNarxiUSD * this.stavkalar.getStUSD_USZ());                   // CIP Narxi UZS
//        this.zakazCIPNarxiUSZ = (this.zakazCIPNarxiUSD * zakUsdUsz);                   // CIP Narxi UZS

        double a = this.zakazCIPNarxiUSZ;


        this.zakazBojYigini = (a * stavkalar.getStBojxona());                                     // bojxona yiginlari UZS
        double b = this.zakazBojYigini;

        this.zakazPoshlinaSumm = (a * this.getTovarPoshlina());                    // Poshlina summasi UZS
        double c = this.zakazPoshlinaSumm;

        this.zakazAksizSumm = (a * this.getTovarAksiz());                   //   Aksiz summasi UZS
        double d = this.zakazAksizSumm;

        if (selected) {
            this.zakazNDS1Narxi = (((a + b + c + d) * stavkalar.getStNDS1S()));                             //  NDS1  miqdori
        } else {
            this.zakazNDS1Narxi = (((a + b + c + d) * stavkalar.getStNDS1Bez()));                             //  NDS1  miqdori
        }

        double k = this.zakazNDS1Narxi;

        this.zakazKelishNarxi = (a + b + c + d + k);                                       // bitta tovarning yetib kelish narxi
        this.zakazKelishSumm = (this.zakazKelishNarxi * this.zakazSoni);                   // tovarning berilgan  miqdoridagi kelish summasi

        /*    pivate  maydon oxiri  */


        this.zakazDDPnarxi = (this.zakazKelishNarxi / getTovarDDP());                                 // DDP narxi
        this.zakazDDPsumm = (this.zakazDDPnarxi * this.zakazSoni);                                //  DDP summasi
        this.zakazNDS2liNarxi = (this.zakazDDPnarxi + (this.zakazDDPnarxi * stavkalar.getStNDS2()));        // NDS2 li narx
        this.zakazNDS2liSumm = (this.zakazNDS2liNarxi * this.zakazSoni);                // NDS2 li summa


        yaxlitla();
        this.zakazUzgartir.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory
                        (1, 1_000_000, this.zakazSoni, 1)
        );

    }


    private void yaxlitla() {

        this.zakazSummaExw = yaxlitla_2(this.zakazSummaExw, 1000);                   //  n ta tovar summasi
        this.zakazTransProNatija = yaxlitla_2(this.zakazTransProNatija, 1000);       //  this.tovar ning natijaviy trans. stavkasi
        this.zakazTransNarxi = yaxlitla_2(this.zakazTransNarxi, 1000);        //  har bir tovar uchun trans narxi
        this.zakazTransSumm = yaxlitla_2(this.zakazTransSumm, 1000);               //  berilgan zakaz uchun trans summasi
        this.zakazTransLiNarx = yaxlitla_2(this.zakazTransLiNarx, 1000);          //  har bir tovar uchun transport bilan  birgalikdagi Narxi
        this.zakazTransLiSumma = yaxlitla_2(this.zakazTransLiSumma, 1000);                     //  berilgan zakaz uchun transport bilan birgalikdagi Summa

        /* private maydonlar  */

        this.zakazCIPNarxiUSD = yaxlitla_2(this.zakazCIPNarxiUSD, 1000);                     // CIP Narxi   USD
        this.zakazCIPSummUSD = yaxlitla_2(this.zakazCIPSummUSD, 1000);                   // CIP summasi USD

        this.zakazCIPNarxiUSZ = yaxlitla_2(this.zakazCIPNarxiUSZ, 1000);                   // CIP Narxi UZS


        this.zakazBojYigini = yaxlitla_2(this.zakazBojYigini, 1000);                                     // bojxona yiginlari UZS

        this.zakazPoshlinaSumm = yaxlitla_2(this.zakazPoshlinaSumm, 1000);                    // Poshlina summasi UZS

        this.zakazAksizSumm = yaxlitla_2(this.zakazAksizSumm, 1000);                   //   Aksiz summasi UZS

        this.zakazNDS1Narxi = yaxlitla_2(this.zakazNDS1Narxi, 1000);                             //  NDS1  miqdori

        this.zakazKelishNarxi = yaxlitla_2(this.zakazKelishNarxi, 1000);                                       // bitta tovarning yetib kelish narxi
        this.zakazKelishSumm = yaxlitla_2(this.zakazKelishSumm, 10);                   // tovarning berilgan  miqdoridagi kelish summasi

        /*    pivate  maydon oxiri  */


        this.zakazDDPnarxi = yaxlitla_2(this.zakazDDPnarxi, 1000);                                 // DDP narxi
        this.zakazDDPsumm = yaxlitla_2(this.zakazDDPsumm, 10);                                //  DDP summasi
        this.zakazNDS2liNarxi = yaxlitla_2(this.zakazNDS2liNarxi, 1000);        // NDS2 li narx
        this.zakazNDS2liSumm = yaxlitla_2(this.zakazNDS2liSumm, 10);        // NDS2 li summa


    }


    private double yaxlitla_2(double qiymat, double aniqlikQiymati) {
        aniqlikQiymati = aniqlikQiymati * 10;

        return (((long) (qiymat * aniqlikQiymati)) / aniqlikQiymati);
    }


    public Tovar getTovar() {
        return super.getTovar();
    }


    public int getZakazSoni() {
        return zakazSoni;
    }

    public void setZakazSoni(int zakazSoni) {
        this.zakazSoni = zakazSoni;
        zakazHisobla();
    }

    public Stavkalar getStavkalar() {
        return stavkalar;
    }

    public Button getZakazKamaytirishBt() {
        return zakazKamaytirishBt;
    }

    public void setZakazKamaytirishBt(Button zakazKamaytirishBt) {
        this.zakazKamaytirishBt = zakazKamaytirishBt;
    }

    public JFXButton getZakazUchirishBt() {
        return zakazUchirishBt;
    }

    public double getZakazSummaExw() {
        return zakazSummaExw;
    }

    public void setZakazSummaExw(double zakazSummaExw) {
        this.zakazSummaExw = zakazSummaExw;

    }

    public double getZakazTransProNatija() {
        return zakazTransProNatija;
    }

    public void setZakazTransProNatija(double zakazTransProNatija) {
        this.zakazTransProNatija = zakazTransProNatija;

    }

    public double getZakazTransNarxi() {
        return zakazTransNarxi;
    }

    public void setZakazTransNarxi(double zakazTransNarxi) {
        this.zakazTransNarxi = zakazTransNarxi;
    }

    public double getZakazTransSumm() {
        return zakazTransSumm;
    }

    public void setZakazTransSumm(double zakazTransSumm) {
        this.zakazTransSumm = zakazTransSumm;

    }

    public double getZakazTransLiSumma() {
        return zakazTransLiSumma;
    }

    public void setZakazTransLiSumma(double zakazTransLiSumma) {
        this.zakazTransLiSumma = zakazTransLiSumma;

    }

    public double getZakazTransLiNarx() {
        return zakazTransLiNarx;
    }

    public void setZakazTransLiNarx(double zakazTransLiNarx) {
        this.zakazTransLiNarx = zakazTransLiNarx;

    }

    public double getZakazCIPNarxiUSD() {
        return zakazCIPNarxiUSD;
    }

    public void setZakazCIPNarxiUSD(double zakazCIPNarxiUSD) {
        this.zakazCIPNarxiUSD = zakazCIPNarxiUSD;

    }

    public double getZakazCIPSummUSD() {
        return zakazCIPSummUSD;
    }

    public void setZakazCIPSummUSD(double zakazCIPSummUSD) {
        this.zakazCIPSummUSD = zakazCIPSummUSD;

    }

    public double getZakazCIPNarxiUSZ() {
        return zakazCIPNarxiUSZ;
    }

    public void setZakazCIPNarxiUSZ(double zakazCIPNarxiUSZ) {
        this.zakazCIPNarxiUSZ = zakazCIPNarxiUSZ;

    }

    public double getZakazBojYigini() {
        return zakazBojYigini;
    }

    public void setZakazBojYigini(double zakazBojYigini) {
        this.zakazBojYigini = zakazBojYigini;

    }

    public double getZakazPoshlinaSumm() {
        return zakazPoshlinaSumm;
    }

    public void setZakazPoshlinaSumm(double zakazPoshlinaSumm) {
        this.zakazPoshlinaSumm = zakazPoshlinaSumm;

    }

    public double getZakazAksizSumm() {
        return zakazAksizSumm;
    }

    public void setZakazAksizSumm(double zakazAksizSumm) {
        this.zakazAksizSumm = zakazAksizSumm;

    }

    public double getZakazNDS1Narxi() {
        return zakazNDS1Narxi;
    }

    public void setZakazNDS1Narxi(double zakazNDS1Narxi) {
        this.zakazNDS1Narxi = zakazNDS1Narxi;

    }

    public double getZakazKelishNarxi() {
        return zakazKelishNarxi;
    }

    public void setZakazKelishNarxi(double zakazKelishNarxi) {
        this.zakazKelishNarxi = zakazKelishNarxi;

    }

    public double getZakazKelishSumm() {
        return zakazKelishSumm;
    }

    public void setZakazKelishSumm(double zakazKelishSumm) {
        this.zakazKelishSumm = zakazKelishSumm;

    }

    public double getZakazDDPnarxi() {
        return zakazDDPnarxi;
    }

    public void setZakazDDPnarxi(double zakazDDPnarxi) {
        this.zakazDDPnarxi = zakazDDPnarxi;
    }

    public double getZakazDDPsumm() {
        return zakazDDPsumm;
    }

    public void setZakazDDPsumm(double zakazDDPsumm) {
        this.zakazDDPsumm = zakazDDPsumm;
    }

    public double getZakazNDS2liNarxi() {
        return zakazNDS2liNarxi;
    }

    public void setZakazNDS2liNarxi(double zakazNDS2liNarxi) {
        this.zakazNDS2liNarxi = zakazNDS2liNarxi;
    }

    public double getZakazNDS2liSumm() {
        return zakazNDS2liSumm;
    }

    public void setZakazNDS2liSumm(double zakazNDS2liSumm) {
        this.zakazNDS2liSumm = zakazNDS2liSumm;
    }

    public Spinner<Integer> getZakazUzgartir() {
        return zakazUzgartir;
    }

    public void setZakazUzgartir(Spinner<Integer> zakazUzgartir) {
        this.zakazUzgartir = zakazUzgartir;
    }

    public ObservableList<TovarZakaz> getTovarZakazList() {
        return tovarZakazList;
    }


    public static void setTr() {
        for (int i = 0; i < tovarZakazList.size(); i++) {
            tovarZakazList.get(i).setTr(i + 1);
        }
    }

    public void setControllerTable(ControllerTable controllerTable) {
        this.controllerTable = controllerTable;
    }






    public int getTovarId() {
        return super.getTovarId();
    }

    public void setTovarId(int tovarId) {
        super.setTovarId(tovarId);
    }

    public int getTr() {
        return super.getTr();
    }

    public void setTr(int tr) {
        super.setTr(tr);
    }

    public void setTovarNomi(String tovarNomi) {
        super.setTovarNomi(tovarNomi);
    }

    public String getTovarNomi() {
        return super.getTovarNomi();
    }

    public String getTovarKod() {
        return super.getTovarKod();
    }

    public void setTovarKod(String tovarKod) {
        super.setTovarKod(tovarKod);
    }

    public String getTovarModel() {
        return super.getTovarModel();
    }

    public void setTovarModel(String tovarModel) {
        super.setTovarModel(tovarModel);
    }

    public Maker getTovarIshlabChiqaruvchi() {
        return super.getTovarIshlabChiqaruvchi();
    }

    public void setTovarIshlabChiqaruvchi(Maker tovarIshlabChiqaruvchi) {
        super.setTovarIshlabChiqaruvchi(tovarIshlabChiqaruvchi);
    }

    public double getTovarNarxi() {
        return super.getTovarNarxi();
    }

    public void setTovarNarxi(double tovarNarxi) {
        super.setTovarNarxi(tovarNarxi);
        zakazHisobla();
    }

    public double getTovarTransportNarxi() {
        return super.getTovarTransportNarxi();
    }

    public void setTovarTransportNarxi(double tovarTransportNarxi) {
        super.setTovarTransportNarxi(tovarTransportNarxi);
        zakazHisobla();
    }

    public double getTovarAksiz() {
        return super.getTovarAksiz();
    }

    public void setTovarAksiz(double tovarAksiz) {
        super.setTovarAksiz(tovarAksiz);
        zakazHisobla();
    }

    public double getTovarPoshlina() {
        return super.getTovarPoshlina();
    }

    public void setTovarPoshlina(double tovarPoshlina) {
        super.setTovarPoshlina(tovarPoshlina);
        zakazHisobla();
    }

    public LocalDate getTovarSana() {
        return super.getTovarSana();
    }

    public void setTovarSana(LocalDate tovarSana) {
        super.setTovarSana(tovarSana);
    }

    public String getTovarUlchovBirligi() {
        return super.getTovarUlchovBirligi();
    }

    public void setTovarUlchovBirligi(String tovarUlchovBirligi) {
        super.setTovarUlchovBirligi(tovarUlchovBirligi);
        zakazHisobla();
    }

    public String getTovarNarxTuri() {
        return super.getTovarNarxTuri();
    }

    public void setTovarNarxTuri(String tovarNarxTuri) {
        super.setTovarNarxTuri(tovarNarxTuri);
        zakazHisobla();
    }

    public void setStavkalar(Stavkalar stavkalar) {
        this.stavkalar = stavkalar;
    }


    public double getStTrans() {
        return stavkalar.getStTrans();
    }

    public void setStTrans(double stTrans) {
        this.stavkalar.setStTrans(stTrans);
    }

    public double getStCIP() {
        return stavkalar.getStCIP();
    }

    public void setStCIP(double stCIP) {
        this.stavkalar.setStCIP(stCIP);
    }

    public double getStUSD_USZ() {
        return stavkalar.getStUSD_USZ();
    }

    public void setStUSD_USZ(double stUSD_USZ) {
        this.stavkalar.setStUSD_USZ(stUSD_USZ);
    }

    public double getStRUB_USZ() {
        return stavkalar.getStUSD_RUB();
    }

    public void setStRUB_USZ(double stRUB_USZ) {
        this.stavkalar.setStUSD_RUB(stRUB_USZ);
    }

    public double getStEUR_USZ() {
        return stavkalar.getStUSD_EUR();
    }

    public void setStEUR_USZ(double stEUR_USZ) {
        this.stavkalar.setStUSD_EUR(stEUR_USZ);
    }

    public double getStBojxona() {
        return stavkalar.getStBojxona();
    }

    public void setStBojxona(double stBojxona) {
        this.stavkalar.setStBojxona(stBojxona);
    }


    public double getStNDS1S() {
        return stavkalar.getStNDS1S();
    }

    public void setStNDS1S(double stNDS1S) {
        this.stavkalar.setStNDS1S(stNDS1S);
    }


    public double getStNDS2() {
        return stavkalar.getStNDS2();
    }

    public void setStNDS2(double stNDS2) {
        this.stavkalar.setStNDS2(stNDS2);
    }


    @Override
    public String toString() {
        return "TovarZakaz{" +
                "zakazSoni=" + zakazSoni +
                ", zakazSummaExw=" + zakazSummaExw +
                ", zakazTransProNatija=" + zakazTransProNatija +
                ", zakazTransNarxi=" + zakazTransNarxi +
                ", zakazTransSumm=" + zakazTransSumm +
                ", zakazTransLiSumma=" + zakazTransLiSumma +
                ", zakazTransLiNarx=" + zakazTransLiNarx +
                ", zakazCIPStavka=" + zakazCIPStavka +
                ", zakazCIPNarxiUSD=" + zakazCIPNarxiUSD +
                ", zakazCIPSummUSD=" + zakazCIPSummUSD +
                ", zakazCIPNarxiUSZ=" + zakazCIPNarxiUSZ +
                ", zakazBojYigini=" + zakazBojYigini +
                ", zakazPoshlinaSumm=" + zakazPoshlinaSumm +
                ", zakazAksizSumm=" + zakazAksizSumm +
                ", zakazNDS1Narxi=" + zakazNDS1Narxi +
                ", zakazKelishNarxi=" + zakazKelishNarxi +
                ", zakazKelishSumm=" + zakazKelishSumm +
                ", zakazDDPnarxi=" + zakazDDPnarxi +
                ", zakazDDPsumm=" + zakazDDPsumm +
                ", zakazNDS2liNarxi=" + zakazNDS2liNarxi +
                ", zakazNDS2liSumm=" + zakazNDS2liSumm +
                ", stavkalar=" + stavkalar +
                '}';
    }
}





