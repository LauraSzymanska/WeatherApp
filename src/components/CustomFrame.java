package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

import utilities.Engine;

public class CustomFrame extends JFrame {

    // Top Panel
    JPanel topPanel;
    JLabel cityLabel;

    // Input Panel
    JPanel inputPanel;
    JTextField inputField;
    JButton inputButton;
    JButton buttonPL;
    JButton buttonEN;

    // Bottom Panel
    JPanel bottomPanel;
    JLabel weatherLabel;

    // Height variables
    int topPanelHeight;
    int bottomPanelHeight;

    // utilities.Engine
    Engine engine = new Engine();

    URL iconURL = getClass().getResource("/Resources/icon.png");
    ImageIcon img = new ImageIcon(iconURL);

    String lang = "en";

    private final Color linenColor = new Color(250,240,230);
    private final Color lightBlueColor = new Color(92,84,112);
    private final Color darkBlueColor = new Color(53,47,68);

    private final Color darkerBlueColor = new Color(73,67,89);
    private final Color darkestBlueColor = new Color(41,37,53);




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
        cityLabel.setFont(new Font("Arial", Font.PLAIN,16));
        cityLabel.setForeground(linenColor);
        cityLabel.setBounds(
                20,
                20,
                cityLabel.getPreferredSize().width+5,
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

        buttonPL = new CustomButton("PL", lightBlueColor, darkerBlueColor);
        buttonPL.setBounds(5, 5, 50, 20);
        buttonPL.addActionListener(e -> lang = "pl");
        buttonEN = new CustomButton("EN", lightBlueColor, darkerBlueColor);
        buttonEN.setBounds(60, 5, 50, 20);
        buttonPL.addActionListener(e -> lang = "en");

        String defaultInfo;
        try {
            defaultInfo = engine.getInfo("Warsaw", lang);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        weatherLabel = new JLabel(defaultInfo);
        weatherLabel.setFont(new Font("Arial", Font.PLAIN,14));
        weatherLabel.setForeground(linenColor);
        weatherLabel.setBounds(10,35,200,bottomPanelHeight-55);
        weatherLabel.setHorizontalAlignment(JLabel.LEFT);
        weatherLabel.setVerticalAlignment(JLabel.TOP);
        bottomPanel.add(buttonPL);
        bottomPanel.add(buttonEN);
        bottomPanel.add(weatherLabel);
    }

    public void initInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setBounds(cityLabel.getWidth()+30, 15, 200,30);
//        inputPanel.setBounds(getWidth()/2-100, topPanelHeight/2, 200,30);
        inputPanel.setLayout(null);
        inputPanel.setBackground(darkBlueColor);

        inputField = new JTextField(20);
        inputField.setBounds(1,1,129,28);
        inputField.setBackground(new Color(250,240,230));
        inputField.setBorder(new EmptyBorder(0,5,0,5));
        inputField.setForeground(new Color(26,47,68));

        inputButton = new CustomButton("Search", darkBlueColor, darkestBlueColor);
        inputButton.setBounds(130,0,70,30);
        inputButton.addActionListener(e -> {
            String city = inputField.getText();
            String info;
            try {
                info = engine.getInfo(city, lang);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            weatherLabel.setText(info);
        });

        inputPanel.add(inputField);
        inputPanel.add(inputButton);
    }

}

