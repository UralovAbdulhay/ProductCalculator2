package sample.Classes;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Moodles.Valyuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Api_kurs {

    public static ObservableList<Valyuta> apiKurs = FXCollections.observableArrayList();

    public void getCourses() {
        URL url = null;
        try {
            url = new URL("https://nbu.uz/en/exchange-rates/json/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            assert url != null;
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
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
apiKurs.clear();
        for (int i = 0; i < jsonArray.size(); i++) {
            Valyuta valyuta = gson.fromJson(jsonArray.get(i), Valyuta.class);
            apiKurs.add(valyuta);
        }
    }
}
