package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Engine {

    private final String apiKey = "4681417d9ec199c700797de62f6ce345";
    public Engine(){
    }

    public String getInfo(String city, String lang) throws Exception {

        String weatherInfo = "";

        // Tworzenie url
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&lang=" + lang + "&units=metric&appid=" + apiKey;
        URL url = new URL(urlString);

        // Tworzenie polaczenia
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Wczytywanie danych
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        String line;
        while((line = reader.readLine()) != null){
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
        Double wind_speed_kmh = Math.round(Double.parseDouble(wind_speed)*3.6 * 100.0) / 100.0;

        String weather_description = ((JSONObject) ((JSONArray) weatherObject
                .get("weather"))
                .get(0))
                .get("description")
                .toString();
        String description = weather_description.substring(0,1).toUpperCase()+weather_description.substring(1);

        String result = "";
        if(lang == "en") {
            result = "<html><font size=5><b>" + city +
                    "</b></font><br/>" + description +
                    "<br/>Temperature: " + temp +
                    "°C<br/><font color=#A69D94>Min: " + temp_min +
                    "°C Max: " + temp_max +
                    "°C<br/><font color=#faf0e6>" +
                    "<br/>Wind speed: " + wind_speed_kmh +
                    " km/h<br/>Pressure: " + pressure +
                    " hPa</html>";
        } else {
            result = "<html><font size=5><b>" + city +
                    "</b></font><br/>" + description +
                    "<br/>Temperatura: " + temp +
                    "°C<br/><font color=#A69D94>Min: " + temp_min +
                    "°C Maks: " + temp_max +
                    "°C<br/><font color=#faf0e6>" +
                    "<br/>Prędkość wiatru: " + wind_speed_kmh +
                    " km/h<br/>Ciśnienie: " + pressure +
                    " hPa</html>";
        }

        return result;
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
