package sample.Classes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Moodles.Valyuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Api_kurs {


    public List<Valyuta> getCourses() {
        ObservableList<Valyuta> courseList = FXCollections.observableArrayList();
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL("https://nbu.uz/en/exchange-rates/json/");
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return courseList;
        }

        String json = "";
        assert con != null;
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     con.getInputStream()
                             )
                     )
        ) {

            json = "";
            String newLine;

            while ((newLine = reader.readLine()) != null) {
                json += newLine + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonParser parser = new JsonParser();

        JsonArray jsonArray = ((JsonArray) parser.parse(json));

        for (int i = 0; i < jsonArray.size(); i++) {
            Valyuta valyuta = gson.fromJson(jsonArray.get(i), Valyuta.class);
            courseList.add(valyuta);
        }

        return courseList;
    }
}
