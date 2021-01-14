package sample.Classes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Classes.Connections.Connections;
import sample.Classes.Connections.CourseConnections;
import sample.Controllers.EditTovar;
import sample.Moodles.Valyuta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Api_kurs extends Thread {

    @Override
    public void run() {
        System.out.println("run start");
        getCourses();

    }

    private EditTovar editTovar;



    public boolean netIsAvailable() {
        try {
            final URL url = new URL("https://nbu.uz/en/exchange-rates/json/");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }


    public void getCourses() {

            ObservableList<Valyuta> courseList = FXCollections.observableArrayList();
            URL url = null;

            URLConnection con = null;
            try {
                url = new URL("https://nbu.uz/en/exchange-rates/json/");
                con = url.openConnection();
                con.connect();
                con.getInputStream();
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

            for (int i = 0; i < jsonArray.size(); i++) {
                Valyuta valyuta = gson.fromJson(jsonArray.get(i), Valyuta.class);
                new CourseConnections().insertToCourse(valyuta);
            }

            System.out.println("run end");

    }


}
