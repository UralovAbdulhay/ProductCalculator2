package sample.Moodles;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Stavkalar {
    public static double stTrans = 1;
   public static double stCIP = 0.99;
   public static double stUSZ_USD = 9550;
   public static double stUSZ_RUB = 139.61;
   public static double stUSZ_EUR = 11037;
   public static double stBojxona = 0.002;
   public static double stNDS1S = 0;
   public static double stNDS2 = 0.15;

    public static ObservableList<StavkaShablon> stavkaShablons = FXCollections.observableArrayList();
    {
        if (stavkaShablons.size() == 0) {
            stavkaShablons.add(0, new StavkaShablon("Транспорт ставка", stTrans, "Транспорт ставка", "trans"));
            stavkaShablons.add(1, new StavkaShablon("CIP ставка", stCIP, "Для Расчетов \"CIP ВЭД\"", "cip"));
            stavkaShablons.add(2, new StavkaShablon("Таможня собр", stBojxona, "Таможня собр", "boj"));
            stavkaShablons.add(3, new StavkaShablon("НДС 1", stNDS1S, "Для Расчетов  \" DDP с НДС ВЭД\"", "nds1s"));
            stavkaShablons.add(4, new StavkaShablon("НДС 1", 0.15, "Для Расчетов  \"DDP без НДС ВЭД\"", "nds1bez"));
            stavkaShablons.add(5, new StavkaShablon("НДС 2", stNDS2, "Для Расчетов \"DDP c НДС ВЭД\"", "nds2"));
            stavkaShablons.add(6, new StavkaShablon("Доллар СУМ-США", stUSZ_USD, "СУМ", "usd_sum"));
            stavkaShablons.add(7, new StavkaShablon("Доллар СУМ-РУБЛЬ", stUSZ_RUB, "СУМ", "usd_rub"));
            stavkaShablons.add(8, new StavkaShablon("Доллар СУМ-ЕВРО", stUSZ_EUR, "СУМ", "usd_euro"));
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
        return stUSZ_USD;
    }

    public  void setStUSD_USZ(double stUSD_USZ) {
        Stavkalar.stUSZ_USD = stUSD_USZ;
    }

    public double getStUSD_RUB() {
        return stUSZ_RUB;
    }

    public  void setStUSD_RUB(double stUSD_RUB) {
        Stavkalar.stUSZ_RUB = stUSD_RUB;
    }

    public double getStUSD_EUR() {
        return stUSZ_EUR;
    }

    public  void setStUSD_EUR(double stUSD_EUR) {
        Stavkalar.stUSZ_EUR = stUSD_EUR;
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

    public  double getStNDS1S() {
        return stNDS1S;
    }

    public  void setStNDS1S(double stNDS1S) {
        Stavkalar.stNDS1S = stNDS1S;
    }


    @Override
    public String toString() {
        return  "\nStavkalar{" +
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
