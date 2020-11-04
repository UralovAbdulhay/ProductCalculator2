package sample.Moodles;

import sample.Classes.Connections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Valyuta {
    private String title;
//    private String title_1;
    private String code;
    private String date;

//    private Double cb_priceD = 0.0;
//    private Double nbu_buy_priceD = 0.0;
//    private Double nbu_cell_priceD = 0.0;

    private String cb_price;
    private String nbu_buy_price;
    private String nbu_cell_price;


//    private String dateString;
//    private LocalDate dateTime;


    public Valyuta(String title, String code, String cb_price,
                   String nbu_buy_price, String nbu_cell_price, String date) {
        this.title = title;
        this.code = code;
        this.cb_price = cb_price;
        this.nbu_buy_price = nbu_buy_price;
        this.nbu_cell_price = nbu_cell_price;
        this.date = date;

//        this.dateTime = new Connections().parseToLocalDate(date);
//        this.dateString = date;
//        this.title_1 = 1 + " " + title + ", " + code;
//
//        try {
//            this.cb_priceD = Double.parseDouble(cb_price);
//            this.nbu_buy_priceD = Double.parseDouble(nbu_buy_price);
//            this.nbu_cell_priceD = Double.parseDouble(nbu_cell_price);
//        } catch (NumberFormatException e) {
//            this.cb_priceD = 0.0;
//            this.nbu_buy_priceD = 0.0;
//            this.nbu_cell_priceD = 0.0;
//            e.printStackTrace();
//        }


    }




    public String getTitleForDB() {
        return 1 + " " + title + ", " + code;
    }


    public String getTitle() {
        return title;
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


    public Double getNbu_buy_priceD() {
        try {
            return Double.parseDouble(nbu_buy_price);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }



    public Double getNbu_cell_priceD() {
        try {
            return Double.parseDouble(nbu_cell_price);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }



    public String getDateString() {
        return date;
    }


    public LocalDate getDateTime() {
        return new Connections().parseToLocalDate(date);
    }

//    public void setDateTime(LocalDate dateTime) {
//        this.dateTime = dateTime;
//    }


//    public void setTitle_1(String title_1) {
//        this.title_1 = title_1;
//    }

    public String getCb_price() {
        return cb_price;
    }

//    public void setCb_price(String cb_price) {
//        this.cb_price = cb_price;
//    }

    public String getNbu_buy_price() {
        return nbu_buy_price;
    }

//    public void setNbu_buy_price(String nbu_buy_price) {
//        this.nbu_buy_price = nbu_buy_price;
//    }

    public String getNbu_cell_price() {
        return nbu_cell_price;
    }

//    public void setNbu_cell_price(String nbu_cell_price) {
//        this.nbu_cell_price = nbu_cell_price;
//    }

    public LocalDate getDate() {
        if (date == null) {
            return parseToLocalDateForValyuta("01/01/0001");
        } else {
            return parseToLocalDateForValyuta(date);
        }
    }



    LocalDate parseToLocalDateForValyuta(String value) {
        String s [] = value.split(" ");
//        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("value = " + value);
        System.out.println("s[0] = " + s[0]);
        System.out.println("last element = " + code);
        try {
            long l = Long.parseLong(s[0]);
            Date date = new Date();
            date.setTime(l);
            return LocalDate.parse(format.format(date), dateFormatter);
        } catch (NumberFormatException e) {
            try {
                return LocalDate.parse(s[0], dateFormatter);
            } catch (DateTimeParseException e1) {
                return parseToLocalDateForValyutaForJson(s[0]);
            }
        }
    }

     LocalDate parseToLocalDateForValyutaForJson(String value) {
String s [] = value.split(" ");
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        System.out.println("value = " + value);
        System.out.println("s[0] = " + s[0]);
        System.out.println("last element = " + code);
        try {
        long l = Long.parseLong(s[0]);
        Date date = new Date();
        date.setTime(l);
        return LocalDate.parse(format.format(date), dateFormatter);
    } catch (NumberFormatException e) {
        return LocalDate.parse(s[0], dateFormatter);
    }
}

    @Override
    public String toString() {
        return  "\nValyuta{" +
                "\ntitle='" + title + '\'' +
                "\n, code='" + code + '\'' +
                "\n, cb_price='" + cb_price + '\'' +
                "\n, nbu_buy_price='" + nbu_buy_price + '\'' +
                "\n, nbu_cell_price='" + nbu_cell_price + '\'' +
                "\n, date='" + date + '\'' +
                '}';
    }
}
