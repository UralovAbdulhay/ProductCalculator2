package sample.Moodles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Classes.Connections.Connections;
import sample.Classes.Connections.StavkaConnections;

import java.text.DecimalFormat;


public class Stavkalar {

    private double stTrans;
    private double stCIP;
    private double stUSZ_USD;
    private double stUSZ_RUB;
    private double stUSZ_EUR;
    private double stBojxona;
    private double stNDS1Bez;
    private double stNDS1S;
    private double stNDS2;

    public static ObservableList<StavkaShablon> stavkaShablons = FXCollections.observableArrayList();

//    {
//        if (stavkaShablons.size() == 0) {
//        stavkaShablons.add(0, new StavkaShablon("Транспорт ставка", stTrans, "Транспорт ставка", "trans"));
//            stavkaShablons.add(1, new StavkaShablon("CIP ставка", stCIP, "Для Расчетов \"CIP ВЭД\"", "cip"));
//            stavkaShablons.add(2, new StavkaShablon("Таможня собр", stBojxona, "Таможня собр", "boj"));
//            stavkaShablons.add(3, new StavkaShablon("НДС 1", stNDS1S, "Для Расчетов  \" DDP с НДС ВЭД\"", "nds1s"));
//            stavkaShablons.add(4, new StavkaShablon("НДС 1", 0.15, "Для Расчетов  \"DDP без НДС ВЭД\"", "nds1bez"));
//            stavkaShablons.add(5, new StavkaShablon("НДС 2", stNDS2, "Для Расчетов \"DDP c НДС ВЭД\"", "nds2"));
//            stavkaShablons.add(6, new StavkaShablon("Доллар СУМ-США", stUSZ_USD, "СУМ", "usd_sum"));
//            stavkaShablons.add(7, new StavkaShablon("Доллар СУМ-РУБЛЬ", stUSZ_RUB, "СУМ", "usd_rub"));
//            stavkaShablons.add(8, new StavkaShablon("Доллар СУМ-ЕВРО", stUSZ_EUR, "СУМ", "usd_euro"));
//        }
//    }

    public Stavkalar() {
        resetStavkaShablons();
    }

    public Stavkalar(double stTrans, double stCIP,
                     double stUSZ_USD, double stUSZ_RUB,
                     double stUSZ_EUR, double stBojxona,
                     double stNDS1Bez, double stNDS1S,
                     double stNDS2) {

        initValue();

        this.stTrans = stTrans;
        this.stCIP = stCIP;
        this.stUSZ_USD = stUSZ_USD;
        this.stUSZ_RUB = stUSZ_RUB;
        this.stUSZ_EUR = stUSZ_EUR;
        this.stBojxona = stBojxona;
        this.stNDS1Bez = stNDS1Bez;
        this.stNDS1S = stNDS1S;
        this.stNDS2 = stNDS2;
    }

    private void initValue() {
        for (StavkaShablon shablon : stavkaShablons) {
            switch (shablon.getKod()) {
                case "trans": {
                    this.setStTrans(shablon.getQiymat());
                    break;
                }
                case "cip": {
                    this.setStCIP(shablon.getQiymat());
                    break;
                }
                case "boj": {
                    this.setStBojxona(shablon.getQiymat());
                    break;
                }
                case "nds1s": {
                    this.setStNDS1S(shablon.getQiymat());
                    break;
                }
                case "nds1bez": {
                    this.setStNDS1Bez(shablon.getQiymat());
                    break;
                }
                case "nds2": {
                    this.setStNDS2(shablon.getQiymat());
                    break;
                }
                case "usd_sum": {
                    this.setStUSD_USZ(shablon.getQiymat());
                    break;
                }
                case "usd_rub": {
                    this.setStUSD_RUB(shablon.getQiymat());
                    break;
                }
                case "usd_euro": {
                    this.setStUSD_EUR(shablon.getQiymat());
                    break;
                }
                default: {
                    break;
                }
            }
        }


    }

    public void resetStavkaShablons() {
        stavkaShablons.clear();
        stavkaShablons.addAll(new StavkaConnections().getStavkaFromSql());
        initValue();
    }


    public double getStTrans() {
        return stTrans;
    }

    public String getStTransStr() {
        return decimalFormat(stTrans);
    }

    public void setStTrans(double stTrans) {
        this.stTrans = stTrans;
    }

    public double getStCIP() {
        return stCIP;
    }

    public String getStCIPStr() {
        return decimalFormat(stCIP);
    }

    public void setStCIP(double stCIP) {
        this.stCIP = stCIP;
    }

    public double getStUSD_USZ() {
        return stUSZ_USD;
    }

    public String getStUSD_USZStr() {
        return decimalFormat(stUSZ_USD);
    }

    public void setStUSD_USZ(double stUSD_USZ) {
        this.stUSZ_USD = stUSD_USZ;
    }

    public double getStUSD_RUB() {
        return stUSZ_RUB;
    }

    public String getStUSD_RUBStr() {
        return decimalFormat(stUSZ_RUB);
    }

    public void setStUSD_RUB(double stUSD_RUB) {
        this.stUSZ_RUB = stUSD_RUB;
    }

    public double getStUSD_EUR() {
        return stUSZ_EUR;
    }

    public String getStUSD_EURStr() {
        return decimalFormat(stUSZ_EUR);
    }

    public void setStUSD_EUR(double stUSD_EUR) {
        this.stUSZ_EUR = stUSD_EUR;
    }

    public double getStBojxona() {
        return stBojxona;
    }

    public String getStBojxonaStr() {
        return decimalFormat(stBojxona * 100);
    }

    public void setStBojxona(double stBojxona) {
        this.stBojxona = stBojxona;
    }

    public double getStNDS2() {
        return stNDS2;
    }

    public String getStNDS2Str() {
        return decimalFormat(stNDS2 * 100);
    }

    public void setStNDS2(double stNDS2) {
        this.stNDS2 = stNDS2;
    }

    public double getStNDS1S() {
        return stNDS1S;
    }

    public String getStNDS1SStr() {
        return decimalFormat(stNDS1S * 100);
    }

    public void setStNDS1S(double stNDS1S) {
        this.stNDS1S = stNDS1S;
    }

    public double getStNDS1Bez() {
        return stNDS1Bez;
    }

    public String getStNDS1BezStr() {
        return decimalFormat(stNDS1Bez * 100);
    }

    public void setStNDS1Bez(double stNDS1Bez) {
        this.stNDS1Bez = stNDS1Bez;
    }


    private String decimalFormat(double d) {
        return new DecimalFormat("#,##0.00").format(d);
    }

    @Override
    public String toString() {

        return "\nStavkalar{" +
                "\nstTrans=" + stTrans +
                ",\n stCIP=" + stCIP +
                ",\n stUSZ_USD=" + stUSZ_USD +
                ",\n stUSZ_RUB=" + stUSZ_RUB +
                ",\n stUSZ_EUR=" + stUSZ_EUR +
                ",\n stBojxona=" + stBojxona +
                ",\n stNDS1S=" + stNDS1S +
                ",\n stNDS2=" + stNDS2 +
                "\n}";
    }
}
