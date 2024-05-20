package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Engine {
    private final String apiKey;
    private ImageIcon icon;

    //private String result;
    private String[] languages = {"pl", "en"};
    private HashMap<String, String> result = new HashMap<>();
    public Engine(){
        apiKey = readConfig().get("API_KEY");
    }

    public String getInfo(String city, String lang, boolean isLanguageChanged) throws Exception {
        if(!isLanguageChanged ) {
            if (!result.isEmpty()) {
                result.clear();
            }
            for (String l : languages) {
                String weatherInfo = "";
                // Tworzenie url
                String apiString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&lang=" + l + "&units=metric&appid=" + apiKey;
                URL apiURL = new URL(apiString);

                // Tworzenie polaczenia
                HttpURLConnection apiConnection = (HttpURLConnection) apiURL.openConnection();
                apiConnection.setRequestMethod("GET");

                // Wczytywanie danych
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(apiConnection.getInputStream())
                );

                String line;
                while ((line = reader.readLine()) != null) {
                    weatherInfo += line;
                }

                // Serializacja danych
                JSONParser parser = new JSONParser();
                JSONObject weatherObject = (JSONObject) parser.parse(weatherInfo);

                String temp = ((JSONObject) weatherObject
                        .get("main"))
                        .get("temp")
                        .toString();
                String temp_min = ((JSONObject) weatherObject
                        .get("main"))
                        .get("temp_min")
                        .toString();
                String temp_max = ((JSONObject) weatherObject
                        .get("main"))
                        .get("temp_max")
                        .toString();
                String feels_like = ((JSONObject) weatherObject
                        .get("main"))
                        .get("feels_like")
                        .toString();
                String pressure = ((JSONObject) weatherObject
                        .get("main"))
                        .get("pressure")
                        .toString();
                String wind_speed = ((JSONObject) weatherObject
                        .get("wind"))
                        .get("speed")
                        .toString();
                Double wind_speed_kmh = Math.round(Double.parseDouble(wind_speed) * 3.6 * 100.0) / 100.0;

                String weather_description = ((JSONObject) ((JSONArray) weatherObject
                        .get("weather"))
                        .get(0))
                        .get("description")
                        .toString();
                String description = weather_description.substring(0, 1).toUpperCase() + weather_description.substring(1);
                String city_name = weatherObject
                        .get("name")
                        .toString();

                String icon_id = ((JSONObject) ((JSONArray) weatherObject
                        .get("weather"))
                        .get(0))
                        .get("icon")
                        .toString();

                String iconString = "https://openweathermap.org/img/wn/" + icon_id + "@2x.png";
                URL iconURL = new URL(iconString);
                Image image = ImageIO.read(iconURL);
                icon = new ImageIcon(image);

                if(l.equals("pl")){
                    result.put("pl", "<html><font size=5><b>" + city_name +
                            "</b></font><br/>" + description +
                            "<br/>Temperatura: " + temp +
                            "°C<br/><font color=#A69D94>Min: " + temp_min +
                            "°C Maks: " + temp_max +
                            "°C<br/><font color=#faf0e6>" +
                            "<br/>Prędkość wiatru: " + wind_speed_kmh +
                            " km/h<br/>Ciśnienie: " + pressure +
                            " hPa</html>");
                } else {
                    result.put("en", "<html><font size=5><b>" + city_name +
                            "</b></font><br/>" + description +
                            "<br/>Temperature: " + temp +
                            "°C<br/><font color=#A69D94>Min: " + temp_min +
                            "°C Max: " + temp_max +
                            "°C<br/><font color=#faf0e6>" +
                            "<br/>Wind speed: " + wind_speed_kmh +
                            " km/h<br/>Pressure: " + pressure +
                            " hPa</html>");
                }

            }
        }
        return result.get(lang);
    }

    public ImageIcon getImage(){
        return icon;
    }

    public HashMap<String, String> readConfig(){
        HashMap<String, String> config = new HashMap<>();
        try {
            Scanner sc = new Scanner(new File("./src/utilities/config.txt"));
            String line = "";
            String[] lineSplitted;
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                lineSplitted = line.replace(":", "").split(" ");
                config.put(lineSplitted[0], lineSplitted[1]);
            }
        } catch(FileNotFoundException fnf){
            System.err.println("Nie znaleziono pliku");
        }
        return config;
    }

    /*

    Temperatura
    Minimalna
    Maksymalna
    Odczuwalna
    Ciśnienie
    Opady
    Obecna pogoda

     */



}
