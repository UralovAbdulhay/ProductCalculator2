package sample.Moodles;

import java.time.LocalDate;

public class Xodimlar {

    int id;
    String ism;
    String familiya;
    String ochestva;
    LocalDate tugilganVaqt;
    LocalDate comeDay;
    String lavozim;

    public Xodimlar(int id, String ism, String familiya,
                    String ochestva, LocalDate tugilganVaqt,
                    LocalDate comeDay, String lavozim) {
        this.id = id;
        this.ism = ism;
        this.familiya = familiya;
        this.ochestva = ochestva;
        this.tugilganVaqt = tugilganVaqt;
        this.comeDay = comeDay;
        this.lavozim = lavozim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsm() {
        return ism;
    }

    public void setIsm(String ism) {
        this.ism = ism;
    }

    public String getFamiliya() {
        return familiya;
    }

    public void setFamiliya(String familiya) {
        this.familiya = familiya;
    }

    public String getOchestva() {
        return ochestva;
    }

    public void setOchestva(String ochestva) {
        this.ochestva = ochestva;
    }

    public LocalDate getTugilganVaqt() {
        return tugilganVaqt;
    }

    public void setTugilganVaqt(LocalDate tugilganVaqt) {
        this.tugilganVaqt = tugilganVaqt;
    }

    public LocalDate getComeDay() {
        return comeDay;
    }

    public void setComeDay(LocalDate comeDay) {
        this.comeDay = comeDay;
    }

    public String getLavozim() {
        return lavozim;
    }

    public void setLavozim(String lavozim) {
        this.lavozim = lavozim;
    }


    @Override
    public String toString() {
        return "Xodimlar{" +
                "id=" + id +
                ", ism='" + ism + '\'' +
                ", familiya='" + familiya + '\'' +
                ", ochestva='" + ochestva + '\'' +
                ", tugilganVaqt=" + tugilganVaqt +
                ", comeDay=" + comeDay +
                ", lavozim='" + lavozim + '\'' +
                '}';
    }
}
