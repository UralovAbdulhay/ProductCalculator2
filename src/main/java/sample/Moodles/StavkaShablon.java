package sample.Moodles;

import java.text.DecimalFormat;

public class StavkaShablon {
    private String nomi;
    private double qiymat;
    private String komment;
    private String kod;

    public StavkaShablon(String nomi, double qiymat,
                         String komment, String kod) {
        this.nomi = nomi;
        this.qiymat = qiymat;
        this.komment = komment;
        this.kod = kod;
    }

    public String getNomi() {
        return nomi;
    }

    public void setNomi(String nomi) {
        this.nomi = nomi;
    }

    public double getQiymat() {
        return qiymat;
    }

    public String getQiymatStr() {

        if (this.getKod().equals("boj") || this.getKod().equals("nds1s")
                || this.getKod().equals("nds1bez") || this.getKod().equals("nds2") ){
            return new DecimalFormat("#,##0.00").format(qiymat * 100);
        }else {
            return new DecimalFormat("#,##0.00").format(qiymat);
        }
    }

    public void setQiymat(double qiymat) {
        this.qiymat = qiymat;
        synchronizationStavka();
    }

    public String getKomment() {
        return komment;
    }

    public void setKomment(String komment) {
        this.komment = komment;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }


    private void synchronizationStavka() {

        switch (this.getKod()) {

            case "trans": {
                new Stavkalar().setStTrans(this.getQiymat());
                break;
            }
            case "cip": {
                new Stavkalar().setStCIP(this.getQiymat());
                break;
            }
            case "boj": {
                new Stavkalar().setStBojxona(this.getQiymat());
                break;
            }
            case "nds1s":
            case "nds1bez": {
                new Stavkalar().setStNDS1S(this.getQiymat());
                break;
            }
            case "nds2": {
                new Stavkalar().setStNDS2(this.getQiymat());
                break;
            }
            case "usd_sum": {
                new Stavkalar().setStUSD_USZ(this.getQiymat());
                break;
            }
            case "usd_rub": {
                new Stavkalar().setStUSD_RUB(this.getQiymat());
                break;
            }
            case "usd_euro": {
                new Stavkalar().setStUSD_EUR(this.getQiymat());
                break;
            }

            default:
                break;
        }

        TovarZakaz.tovarZakazList.forEach(TovarZakaz::zakazHisobla);
    }

    @Override
    public String toString() {
        return "StavkaShablon{" +
                "nomi='" + nomi + '\'' +
                ", qiymat=" + qiymat +
                ", komment='" + komment + '\'' +
                '}';
    }
}
