package sample.Moodles;

import sample.Classes.Connections;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Valyuta {
    private String title;
    private String title_1;
    private String code;

    private Double cb_priceD;
    private Double nbu_buy_priceD;
    private Double nbu_cell_priceD;

    private String cb_price;
    private String nbu_buy_price;
    private String nbu_cell_price;

    private String dateString;
    private LocalDateTime dateTime;


    public Valyuta(String title, String code, String cb_price,
                   String nbu_buy_price, String nbu_cell_price, String date) {
        this.title = title;
        this.code = code;
        this.cb_price = cb_price;
        this.nbu_buy_price = nbu_buy_price;
        this.nbu_cell_price = nbu_cell_price;
        this.dateTime = new Connections().parseToLocalDateTime(date);
        this.dateString = date;
        this.title_1 = 1 + " " + title + ", " + code;

        try {
            this.cb_priceD = Double.parseDouble(cb_price);
            this.nbu_buy_priceD = Double.parseDouble(nbu_buy_price);
            this.nbu_cell_priceD = Double.parseDouble(nbu_cell_price);
        } catch (NumberFormatException e) {
            this.cb_priceD = 0.0;
            this.nbu_buy_priceD = 0.0;
            this.nbu_cell_priceD = 0.0;
            e.printStackTrace();
        }


    }




    public String getTitle() {
        return 1 + " " + title + ", " + code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getCb_priceD() {
        try {
            return Double.parseDouble(cb_price);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setCb_priceD(Double cb_priceD) {
        this.cb_priceD = cb_priceD;
    }

    public Double getNbu_buy_priceD() {
        try {
            return Double.parseDouble(nbu_buy_price);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setNbu_buy_priceD(Double nbu_buy_priceD) {
        this.nbu_buy_priceD = nbu_buy_priceD;
    }

    public Double getNbu_cell_priceD() {
        try {
            return Double.parseDouble(nbu_cell_price);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setNbu_cell_priceD(Double nbu_cell_priceD) {
        this.nbu_cell_priceD = nbu_cell_priceD;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle_1() {
        return title_1;
    }

    public void setTitle_1(String title_1) {
        this.title_1 = title_1;
    }

    public String getCb_price() {
        return cb_price;
    }

    public void setCb_price(String cb_price) {
        this.cb_price = cb_price;
    }

    public String getNbu_buy_price() {
        return nbu_buy_price;
    }

    public void setNbu_buy_price(String nbu_buy_price) {
        this.nbu_buy_price = nbu_buy_price;
    }

    public String getNbu_cell_price() {
        return nbu_cell_price;
    }

    public void setNbu_cell_price(String nbu_cell_price) {
        this.nbu_cell_price = nbu_cell_price;
    }

    public LocalDate getDate() {
       return  new Connections().parseToLocalDate(dateString);
    }

    @Override
    public String toString() {
        return "Valyuta{" +
                "title='" + title + '\'' +
                ", title_1='" + title_1 + '\'' +
                ", code='" + code + '\'' +
                ", cb_priceD=" + cb_priceD +
                ", nbu_buy_priceD=" + nbu_buy_priceD +
                ", nbu_cell_priceD=" + nbu_cell_priceD +
                ", cb_price='" + cb_price + '\'' +
                ", nbu_buy_price='" + nbu_buy_price + '\'' +
                ", nbu_cell_price='" + nbu_cell_price + '\'' +
                ", date='" + dateTime + '\'' +
                '}';
    }


}
