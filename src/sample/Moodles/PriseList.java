package sample.Moodles;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Classes.Connections;

import java.time.LocalDate;

public class PriseList extends Tovar {

    private JFXCheckBox delCheck;
    private JFXDatePicker sanaTanlagich;
    private JFXButton tovarAdd;
    private int addCount;

    public static ObservableList<PriseList> priseLists = FXCollections.observableArrayList();

    public PriseList(Tovar tovar) {
        super(tovar);

        this.delCheck = new JFXCheckBox();

        this.sanaTanlagich = new JFXDatePicker(tovar.getTovarSana());
        this.sanaTanlagich.setEditable(false);

        this.tovarAdd = new JFXButton("");
        MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.PLUS);
        icon.setGlyphSize(18);
        this.tovarAdd.setGraphic(icon);
        addCount = 1;
        setTr();
    }




    public JFXCheckBox getDelCheck() {
        return this.delCheck;
    }

    public void setDelCheck(JFXCheckBox delCheck) {
        this.delCheck = delCheck;
    }

    public JFXDatePicker getSanaTanlagich() {
        return sanaTanlagich;
    }

    public void setSanaTanlagich(JFXDatePicker sanaTanlagich) {
        this.sanaTanlagich = sanaTanlagich;
    }

    public static void addPriseList(PriseList priseList) {
        new Connections().insertToTovar(priseList);
        PriseList.reSetPriseList();
    }

    public static void addAllPriseLists(ObservableList<PriseList> priseLists) {
        priseLists.forEach(e -> new Connections().insertToTovar(e));
        PriseList.reSetPriseList();
    }

    public static void reSetPriseList() {
        priseLists.clear();
        priseLists.addAll(new Connections().getTovarFromSql());
        setTr();
    }

    public static void setPriseList(PriseList priseList) {
        new Connections().updateTovar(priseList);
        PriseList.reSetPriseList();
    }

    public JFXButton getTovarAdd() {
        return tovarAdd;
    }

    public void setTovarAdd(JFXButton tovarAdd) {
        this.tovarAdd = tovarAdd;
    }

    public int getAddCount() {
        return this.addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public int getTovarId() {
        return super.getTovarId();
    }

    public void setTovarId(int tovarId) {
        super.setTovarId(tovarId);
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
    }

    public double getTovarDDP() {
        return super.getTovarDDP();
    }

    public void setTovarDDP(double tovarDDP) {
        super.setTovarDDP(tovarDDP);
    }

    public double getTovarTransportNarxi() {
        return super.getTovarTransportNarxi();
    }

    public void setTovarTransportNarxi(double tovarTransportNarxi) {
        super.setTovarTransportNarxi(tovarTransportNarxi);
    }

    public double getTovarAksiz() {
        return super.getTovarAksiz();
    }

    public void setTovarAksiz(double tovarAksiz) {
        super.setTovarAksiz(tovarAksiz);
    }

    public double getTovarPoshlina() {
        return super.getTovarPoshlina();
    }

    public void setTovarPoshlina(double tovarPoshlina) {
        super.setTovarPoshlina(tovarPoshlina);
    }

    public LocalDate getTovarSana() {
        return super.getTovarSana();
    }

    public void setToarSana(LocalDate tovarSana) {
        super.setTovarSana(tovarSana);
    }

    public String getTovarUlchovBirligi() {
        return super.getTovarUlchovBirligi();
    }

    public void setTovarUlchovBirligi(String tovarUlchovBirligi) {
        super.setTovarUlchovBirligi(tovarUlchovBirligi);
    }

    public String getTovarNarxTuri() {
        return super.getTovarNarxTuri();
    }

    public void setTovarNarxTuri(String tovarNarxTuri) {
        super.setTovarNarxTuri(tovarNarxTuri);
    }

    public int getTr() {
        return super.getTr();
    }

    public void setTr(int tr) {
        super.setTr(tr);
    }


    public String getTovarKomment() {
        return super.getTovarKomment();
    }

    public void setTovarKomment(String tovarKomment) {
        super.setTovarKomment(tovarKomment);
    }

    public static void setTr() {
        for (int i = 0; i < priseLists.size(); i++) {
            priseLists.get(i).setTr(i + 1);
        }
    }

    public static void setTr(ObservableList<Tovar> tovars) {
        for (int i = 0; i < tovars.size(); i++) {
            tovars.get(i).setTr(i+1);
        }
    }


}
