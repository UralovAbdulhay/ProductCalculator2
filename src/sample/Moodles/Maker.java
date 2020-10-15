package sample.Moodles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Maker {
    private int id;
    private String name;
    private String country;
    private LocalDate sana;

    public static ObservableList<Maker> makerList = FXCollections.observableArrayList();

    public Maker(int id, String name, String country, LocalDate sana) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.sana = sana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getSana() {
        return sana;
    }

    public void setSana(LocalDate sana) {
        this.sana = sana;
    }

    @Override
    public String toString() {
        return "Maker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", sana=" + sana +
                '}';
    }
}
