package sample.Moodles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Stavkalar {
    public static double stTrans = 1;
   public static double stCIP = 0.99;
   public static double stUSD_USZ = 9550;
   public static double stUSD_RUB = 139.61;
   public static double stUSD_EUR = 11037;
   public static double stUSD_USD = 1;
   public static double stBojxona = 0.002;
   public static double stNDS1S = 0;
   public static double stNDS1Bez = 0.15;
   public static double stNDS2 = 0.15;

    public static ObservableList<StavkaShablon> stavkaShablons = FXCollections.observableArrayList();
    {
        if (stavkaShablons.size() == 0) {
            stavkaShablons.add(0, new StavkaShablon("Транспорт ставка", stTrans, "Транспорт ставка", "trans"));
            stavkaShablons.add(1, new StavkaShablon("CIP ставка", stCIP, "Для Расчетов \"CIP ВЭД\"", "cip"));
            stavkaShablons.add(2, new StavkaShablon("Таможня собр", stBojxona, "Таможня собр", "boj"));
            stavkaShablons.add(3, new StavkaShablon("НДС 1", stNDS1S, "Для Расчетов  \" DDP с НДС ВЭД\"", "nds1s"));
            stavkaShablons.add(4, new StavkaShablon("НДС 1", stNDS1Bez, "Для Расчетов  \"DDP без НДС ВЭД\"", "nds1bez"));
            stavkaShablons.add(5, new StavkaShablon("НДС 2", stNDS2, "Для Расчетов \"DDP c НДС ВЭД\"", "nds2"));
            stavkaShablons.add(6, new StavkaShablon("Доллар США-СУМ", stUSD_USZ, "СУМ", "usd_sum"));
            stavkaShablons.add(7, new StavkaShablon("Доллар США-РУБЛЬ", stUSD_RUB, "РУБЛЬ", "usd_rub"));
            stavkaShablons.add(8, new StavkaShablon("Доллар США-ЕВРО", stUSD_EUR, "ЕВРО", "usd_euro"));
            stavkaShablons.add(9, new StavkaShablon("Доллар США-США", stUSD_USD, "США", "usd_usd"));
        }
    }


    public double getStTrans() {
        return stTrans;
    }

    public  void setStTrans(double stTrans) {
        Stavkalar.stTrans = stTrans;
    }

    public double getStCIP() {
        return stCIP;
    }

    public  void setStCIP(double stCIP) {
        Stavkalar.stCIP = stCIP;
    }

    public double getStUSD_USZ() {
        return stUSD_USZ;
    }

    public  void setStUSD_USZ(double stUSD_USZ) {
        Stavkalar.stUSD_USZ = stUSD_USZ;
    }

    public double getStUSD_RUB() {
        return stUSD_RUB;
    }

    public  void setStUSD_RUB(double stUSD_RUB) {
        Stavkalar.stUSD_RUB = stUSD_RUB;
    }

    public double getStUSD_EUR() {
        return stUSD_EUR;
    }

    public  void setStUSD_EUR(double stUSD_EUR) {
        Stavkalar.stUSD_EUR = stUSD_EUR;
    }

    public double getStBojxona() {
        return stBojxona;
    }

    public  void setStBojxona(double stBojxona) {
        Stavkalar.stBojxona = stBojxona;
    }

    public double getStNDS1() {
        return stNDS1S;
    }

    public  void setStNDS1(double stNDS1) {
        Stavkalar.stNDS1S = stNDS1;
    }

    public double getStNDS2() {
        return stNDS2;
    }

    public  void setStNDS2(double stNDS2) {
        Stavkalar.stNDS2 = stNDS2;
    }


    public static double getStUSD_USD() {
        return stUSD_USD;
    }

    public static void setStUSD_USD(double stUSD_USD) {
        Stavkalar.stUSD_USD = stUSD_USD;
    }

    public  double getStNDS1S() {
        return stNDS1S;
    }

    public  void setStNDS1S(double stNDS1S) {
        Stavkalar.stNDS1S = stNDS1S;
    }

    public static double getStNDS1Bez() {
        return stNDS1Bez;
    }

    public static void setStNDS1Bez(double stNDS1Bez) {
        Stavkalar.stNDS1Bez = stNDS1Bez;
    }


    @Override
    public String toString() {
        return "Stavkalar{" +
                "stTrans=" + stTrans +
                ", stCIP=" + stCIP +
                ", stUSD_USZ=" + stUSD_USZ +
                ", stUSD_RUB=" + stUSD_RUB +
                ", stUSD_EUR=" + stUSD_EUR +
                ", stUSD_USD=" + stUSD_USD +
                ", stBojxona=" + stBojxona +
                ", stNDS1S=" + stNDS1S +
                ", stNDS1Bez=" + stNDS1Bez +
                ", stNDS2=" + stNDS2 +
                '}';
    }
}
