package sample.outputSettings;

import com.google.gson.Gson;
import sample.Classes.LocaleConfig;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputSettings {
    int count;

    public OutputSettings(int count) {
        this.count = count;
    }

    public OutputSettings() {
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        initLocale();
    }

    public static final String OUTPUT_CONFIG_FILE = "outputSetting.json";

    private static String appDataReserve = System.getenv("APPDATA") +
            "\\ProductCalculator\\baza\\reserve\\";


    static public void copy() {
        Path sourceFile = Paths.get("baza/colcul.db");

        OutputSettings outputSettings = OutputSettings.getInstance();
        int count = outputSettings.getCount();

        if (count >= 500) {
            count = 0;
        }

        count++;

        Path targetFile = Paths.get(appDataReserve
                + count + "-" + sourceFile.getFileName()
        );

        try {
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File successful copied!");
        } catch (IOException e) {
            System.out.println("File Not copied!");
        }
        outputSettings.setCount(count);
    }

    public void initLocale() {

        Writer writer = null;
        try {
            writer = new FileWriter(OUTPUT_CONFIG_FILE);
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            Logger.getLogger(OutputSettings.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                Logger.getLogger(OutputSettings.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public static OutputSettings getInstance() {
        OutputSettings config = new OutputSettings();
        Gson gson = new Gson();

        if (new File(OUTPUT_CONFIG_FILE).exists()) {
            try {
                config = gson.fromJson(new FileReader(OUTPUT_CONFIG_FILE), OutputSettings.class);
            } catch (FileNotFoundException e) {
                config.initLocale();
                Logger.getLogger(OutputSettings.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return config;
    }


    @Override
    public String toString() {
        return "OutputSettings{" +
                "count=" + count +
                '}';
    }
}
