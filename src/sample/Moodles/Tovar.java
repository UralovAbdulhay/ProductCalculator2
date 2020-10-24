package sample.Moodles;

import java.time.LocalDate;

public class Tovar {

    private int tr;
    private int tovarId;
    private String tovarKod;
    private String tovarNomi;
    private String tovarModel;
    private double tovarNarxi;
    private String tovarNarxTuri;

    private double tovarTransportNarxi;
    private double tovarAksiz;
    private double tovarPoshlina;

    private double tovarDDP;

    private LocalDate tovarSana;
    private String tovarUlchovBirligi;
    private String tovarKomment;

//    private String tovarIshlabChiqaruvchi;

    private Maker tovarIshlabChiqaruvchi;

//    private Button tovarAdd;
//    private int addCount = 1;


    public Tovar(String tovarKod, String tovarNomi,
                 String tovarModel, Maker tovarIshlabChiqaruvchi,
                 double tovarNarxi, double tovarDDP, String tovarNarxTuri, double tovarTransportNarxi,
                 double tovarAksiz, double tovarPoshlina, LocalDate tovarSana,
                 String tovarUlchovBirligi, String tovarKomment) {

        this(-1, tovarKod, tovarNomi, tovarModel, tovarIshlabChiqaruvchi, tovarNarxi,
                tovarDDP, tovarNarxTuri, tovarTransportNarxi, tovarAksiz, tovarPoshlina,
                tovarSana, tovarUlchovBirligi, tovarKomment);
    }


    Tovar(Tovar tovar) {
        this(tovar.getTovarId(), tovar.getTovarKod(), tovar.getTovarNomi(), tovar.getTovarModel(),
                tovar.getTovarIshlabChiqaruvchi(), tovar.getTovarNarxi(), tovar.getTovarDDP(), tovar.getTovarNarxTuri(),
                tovar.getTovarTransportNarxi(), tovar.getTovarAksiz(), tovar.getTovarPoshlina(),
                tovar.getTovarSana(), tovar.getTovarUlchovBirligi(), tovar.getTovarKomment()
        );
    }


    public Tovar(int tovarId, String tovarKod, String tovarNomi,
                 String tovarModel, Maker tovarIshlabChiqaruvchi,
                 double tovarNarxi, double tovarDDP, String tovarNarxTuri,
                 double tovarTransportNarxi, double tovarAksiz,
                 double tovarPoshlina, LocalDate tovarSana,
                 String tovarUlchovBirligi, String tovarKomment) {

        this.tovarId = tovarId;
        this.tovarKod = tovarKod;
        this.tovarNomi = tovarNomi;
        this.tovarModel = tovarModel;
        this.tovarIshlabChiqaruvchi = tovarIshlabChiqaruvchi;
        this.tovarNarxi = tovarNarxi;
        this.tovarDDP = tovarDDP;
        this.tovarNarxTuri = tovarNarxTuri;
        this.tovarTransportNarxi = tovarTransportNarxi;
        this.tovarAksiz = tovarAksiz;
        this.tovarPoshlina = tovarPoshlina;
        this.tovarSana = tovarSana;
        this.tovarUlchovBirligi = tovarUlchovBirligi;
        this.tovarKomment = tovarKomment;
    }


    public Tovar getTovar() {
        return this;
    }

    public int getTovarId() {
        return tovarId;
    }

    public void setTovarId(int tovarId) {
        this.tovarId = tovarId;
    }

    public String getTovarKod() {
        return tovarKod;
    }

    public void setTovarKod(String tovarKod) {
        this.tovarKod = tovarKod;
    }

    public String getTovarNomi() {
        return tovarNomi;
    }

    public void setTovarNomi(String tovarNomi) {
        this.tovarNomi = tovarNomi;
    }

    public String getTovarModel() {
        return tovarModel;
    }

    public void setTovarModel(String tovarModel) {
        this.tovarModel = tovarModel;
    }

    public Maker getTovarIshlabChiqaruvchi() {
        return tovarIshlabChiqaruvchi;
    }

    public void setTovarIshlabChiqaruvchi(Maker tovarIshlabChiqaruvchi) {
        this.tovarIshlabChiqaruvchi = tovarIshlabChiqaruvchi;
    }

    public double getTovarDDP() {
        return tovarDDP;
    }

    public void setTovarDDP(double tovarDDP) {
        this.tovarDDP = tovarDDP;
    }

    public double getTovarNarxi() {
        return tovarNarxi;
    }

    public void setTovarNarxi(double tovarNarxi) {
        this.tovarNarxi = tovarNarxi;
    }

    public double getTovarTransportNarxi() {
        return tovarTransportNarxi;
    }

    public String getTovarTransportNarxiString() {
        return ((double)(((int) (tovarTransportNarxi * 1000))/10))+ " %";
    }

    public void setTovarTransportNarxi(double tovarTransportNarxi) {
        this.tovarTransportNarxi = tovarTransportNarxi;
    }

    public double getTovarAksiz() {
        return tovarAksiz;
    }

    public String getTovarAksizString() {
        return ((double)(((int) (tovarAksiz * 1000))/10))+ " %";
    }

    public void setTovarAksiz(double tovarAksiz) {
        this.tovarAksiz = tovarAksiz;
    }

    public double getTovarPoshlina() {
        return tovarPoshlina;
    }

    public String getTovarPoshlinaString() {
        return ((double)(((int) (tovarPoshlina * 1000))/10))+ " %";
    }

    public void setTovarPoshlina(double tovarPoshlina) {
        this.tovarPoshlina = tovarPoshlina;
    }

    public LocalDate getTovarSana() {
        return tovarSana;
    }

    public void setTovarSana(LocalDate tovarSana) {
        this.tovarSana = tovarSana;
    }

    public String getTovarUlchovBirligi() {
        return tovarUlchovBirligi;
    }

    public void setTovarUlchovBirligi(String tovarUlchovBirligi) {
        this.tovarUlchovBirligi = tovarUlchovBirligi;
    }

    public String getTovarKomment() {
        return this.tovarKomment;
    }

    public void setTovarKomment(String tovarKomment) {
        this.tovarKomment = tovarKomment;
    }

    public String getTovarNarxTuri() {
        return tovarNarxTuri;
    }

    public void setTovarNarxTuri(String tovarNarxTuri) {
        this.tovarNarxTuri = tovarNarxTuri;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    @Override
    public String toString() {
        return "Tovar{" +
                "tr=" + tr +
                ", tovarId=" + tovarId +
                ", tovarKod='" + tovarKod + '\'' +
                ", tovarNomi='" + tovarNomi + '\'' +
                ", tovarModel='" + tovarModel + '\'' +
                ", tovarIshlabChiqaruvchi='" + tovarIshlabChiqaruvchi + '\'' +
                ", tovarNarxi=" + tovarNarxi +
                ", tovarNarxTuri='" + tovarNarxTuri + '\'' +
                ", tovarTransportNarxi=" + tovarTransportNarxi +
                ", tovarAksiz=" + tovarAksiz +
                ", tovarPoshlina=" + tovarPoshlina +
                ", tovarDDP=" + tovarDDP +
                ", tovarSana=" + tovarSana +
                ", tovarUlchovBirligi='" + tovarUlchovBirligi + '\'' +
                ", tovarKomment='" + tovarKomment + '\'' +
                '}';
    }
}
