package sample.Moodles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Locale;

public class Languages {
    private String name;
    private Locale locale;

    private ObservableList<Languages> languages = FXCollections.observableArrayList();

    private void init() {
        languages.add(new Languages("Русский", new Locale("ru", "RU")));
        languages.add(new Languages("English", new Locale("en", "EN")));
    }

    public Languages() {
        init();
    }

    private Languages(String name, Locale locale) {
        this.name = name;
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ObservableList<Languages> getLanguages() {
        return languages;
    }

    @Override
    public String toString() {
        return name;
    }

}
