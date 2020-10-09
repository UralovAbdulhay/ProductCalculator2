package sample.Moodles;

public class StavkaShablon {
    private String nomi;
    private double qiymat;
    private String komment;
    private String kod;

    public StavkaShablon(String nomi, double qiymat, String komment, String kod) {
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

            case "trans":{
                Stavkalar.stTrans = this.getQiymat();
                break;
            }
            case "cip":{
                Stavkalar.stCIP = this.getQiymat();
                break;
            }
            case "boj":{
                Stavkalar.stBojxona = this.getQiymat();
                break;
            }
            case "nds1s":{
                Stavkalar.stNDS1S = this.getQiymat();
                break;
            }
            case "nds1bez":{
                Stavkalar.stNDS1Bez = this.getQiymat();
                break;
            }
            case "nds2":{
                Stavkalar.stNDS2 = this.getQiymat();
                break;
            }
            case "usd_sum":{
                Stavkalar.stUSD_USZ = this.getQiymat();
                break;
            }
            case "usd_rub":{
                Stavkalar.stUSD_RUB = this.getQiymat();
                break;
            }
            case "usd_euro":{
                Stavkalar.stUSD_EUR = this.getQiymat();
                break;
            }
            case "usd_usd":{
                Stavkalar.stUSD_USD = this.getQiymat();
                break;
            }

            default:break;


        }

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
