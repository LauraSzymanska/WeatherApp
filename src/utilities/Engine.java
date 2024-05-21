package utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Engine {
    private final String apiKey;
    private ImageIcon icon;

    private final String[] languages;
    private HashMap<String, String> result = new HashMap<>();
    private Properties langProperties;
    public Engine(){
        apiKey = readConfig().get("API_KEY");
        languages = readConfig().get("LANGUAGES").split(",");
    }

    public String getInfo(String city, String lang, boolean isLanguageChanged) throws Exception {
        if(!isLanguageChanged ) {
            if (!result.isEmpty()) {
                result.clear();
            }
            for (String l : languages) {
                loadLanguageProperties(l);
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
                reader.close();

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

                String formattedText = String.format(
                        "<html>" +
                                "<font size=5><b>" +
                                city_name +
                                "</b></font><br/>" +
                                description +
                                "<br/>" +
                                langProperties.getProperty("temperature") + temp +"°C" +
                                "<br/><font color=#A69D94>" +
                                langProperties.getProperty("min") + temp_min + "°C " +
                                langProperties.getProperty("max") + temp_max + "°C" +
                                "<br/><font color=#faf0e6><br/>" +
                                langProperties.getProperty("wind_speed") + wind_speed_kmh + " km/h<br/>" +
                                langProperties.getProperty("pressure") + pressure + " hPa" +
                                "</html>"
                );

                result.put(l, formattedText);
            }
        }
        return result.get(lang);
    }

    private void loadLanguageProperties(String lang) throws Exception {
        langProperties = new Properties();
        String propertiesFileName = "Resources/language_" + lang + ".properties";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (inputStream != null) {
                langProperties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            } else {
                System.err.println("Nie znaleziono pliku");
            }
        }
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
            sc.close();
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
