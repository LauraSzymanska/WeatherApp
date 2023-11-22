package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

import utilities.Engine;

public class CustomFrame extends JFrame {

    // Top Panel
    private JPanel topPanel;
    private JLabel cityLabel;

    // Input Panel
    private JPanel inputPanel;
    private JTextField inputField;
    private JButton inputButton;
    private JButton buttonPL;
    private JButton buttonEN;

    // Bottom Panel
    private JPanel bottomPanel;
    private JLabel weatherLabel;
    private JLabel weatherImage;

    // Height variables
    private int topPanelHeight;
    private int bottomPanelHeight;

    // utilities.Engine
    private final Engine engine = new Engine();

    private final URL iconURL = getClass().getResource("/Resources/icon.png");
    private final ImageIcon img = new ImageIcon(iconURL);

    private String lang = "en";
    private String userCity = null;

    private final Color linenColor = new Color(250,240,230);
    private final Color lightBlueColor = new Color(92,84,112);


    private final Color dLightBlueColor = new Color(73,67,89);
    private final Color darkBlueColor = new Color(53,47,68);
    private final Color dDarkBlueColor = new Color(41,37,53);




    public CustomFrame(){
        initSettings();
        setContents();
        setVisible(true);
    }

    public void initSettings(){
        setIconImage(img.getImage());
        setSize(350,270);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Weather");
        setResizable(false);
        topPanelHeight = 60;
        bottomPanelHeight = getHeight()-topPanelHeight;
    }

    public void setContents(){
        initTopPanel();
        initBottomPanel();
        add(topPanel);
        add(bottomPanel);
    }

    public void initTopPanel(){

        topPanel = new JPanel();
        topPanel.setBounds(0,0,getWidth(),topPanelHeight);
        topPanel.setLayout(null);
        topPanel.setBackground(lightBlueColor);


        cityLabel = new JLabel("Search city");
        cityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cityLabel.setFont(new Font("Arial", Font.PLAIN,16));
        cityLabel.setForeground(linenColor);
        cityLabel.setBounds(
                10,
                20,
                cityLabel.getPreferredSize().width+25,
                cityLabel.getPreferredSize().height
        );

        initInputPanel();
        topPanel.add(cityLabel);
        topPanel.add(inputPanel);
    }

    public void initBottomPanel(){
        bottomPanel = new JPanel();
        bottomPanel.setBounds(0,topPanelHeight, getWidth(),bottomPanelHeight);
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(darkBlueColor);

        String defaultInfo;
        try {
            defaultInfo = engine.getInfo("Warsaw", lang);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        buttonPL = new CustomButton("PL", lightBlueColor, dLightBlueColor);
        buttonPL.setBounds(5, 5, 50, 20);
        // Zmiana na polski
        buttonPL.addActionListener(e -> {
            lang = "pl";
            cityLabel.setText("Wpisz miasto");
            inputButton.setText("Szukaj");
            setInfo(Objects.requireNonNullElse(userCity, "Warsaw"));
        });
        buttonEN = new CustomButton("EN", lightBlueColor, dLightBlueColor);
        buttonEN.setBounds(60, 5, 50, 20);
        // Zmiana na angielski
        buttonEN.addActionListener(e -> {
            lang = "en";
            cityLabel.setText("Search city");
            inputButton.setText("Search");
            setInfo(Objects.requireNonNullElse(userCity, "Warsaw"));

        });

        weatherLabel = new JLabel(defaultInfo);
        weatherLabel.setFont(new Font("Arial", Font.PLAIN,14));
        weatherLabel.setForeground(linenColor);
        weatherLabel.setBounds(10,35,200,130);
        weatherLabel.setHorizontalAlignment(JLabel.LEFT);
        weatherLabel.setVerticalAlignment(JLabel.TOP);

        weatherImage = new JLabel(engine.getImage());
        weatherImage.setBounds(210,40,100,100);


        bottomPanel.add(buttonPL);
        bottomPanel.add(buttonEN);
        bottomPanel.add(weatherLabel);
        bottomPanel.add(weatherImage);

    }

    public void initInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setBounds(cityLabel.getWidth()+30, 15, 180,30);
        inputPanel.setLayout(null);
        inputPanel.setBackground(darkBlueColor);

        inputField = new JTextField();
        inputField.setBounds(1,1,109,28);
        inputField.setBackground(linenColor);
        inputField.setBorder(new EmptyBorder(0,5,0,5));
        inputField.setForeground(new Color(26,47,68));

        inputButton = new CustomButton("Search", darkBlueColor, dDarkBlueColor);
        inputButton.setBounds(110,0,70,30);
        inputButton.addActionListener(e -> {
            userCity = inputField.getText();
            setInfo(userCity);
        });

        inputPanel.add(inputField);
        inputPanel.add(inputButton);
    }

    public void setInfo(String city){

        String info;
        try {
            info = engine.getInfo(city, lang);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        weatherLabel.setText(info);
        weatherImage.setIcon(engine.getImage());
    }

}

