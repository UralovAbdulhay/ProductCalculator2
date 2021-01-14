package sample.Classes;

import com.google.gson.Gson;

import java.io.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocaleConfig {

    public static final String LOCALE_CONFIG_FILE = "language.json";

    private String language;
    private String country;
    private Locale locale;

    public LocaleConfig() {
        this.language = "ru";
        this.country = "RU";
        this.locale = new Locale(this.language, this.country);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
        setLocale(new Locale(this.language, this.country));
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
        setLocale(new Locale(this.language, this.country));
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        this.country = locale.getCountry();
        this.language = locale.getLanguage();
        initLocale();
    }

    public void initLocale() {
        Writer writer = null;
        try {
            writer = new FileWriter(LOCALE_CONFIG_FILE);
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            Logger.getLogger(LocaleConfig.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                Logger.getLogger(LocaleConfig.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public static LocaleConfig getInstance() {
        LocaleConfig config = new LocaleConfig();
        Gson gson = new Gson();
        try {
            config = gson.fromJson(new FileReader(LOCALE_CONFIG_FILE), LocaleConfig.class);
        } catch (FileNotFoundException e) {
            config.initLocale();
            Logger.getLogger(LocaleConfig.class.getName()).log(Level.SEVERE, null, e);
        }
        return config;
    }




    @Override
    public String toString() {
        return "LocaleConfig{" +
                "language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", locale=" + locale +
                '}';
    }
}
