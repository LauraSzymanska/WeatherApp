import javax.swing.*;
import java.awt.*;

public class WeatherFrame extends JFrame {

    // Top Panel
    JPanel topPanel;
    JLabel cityLabel;

    // Input Panel
    JPanel inputPanel;
    JTextField inputField;
    Button inputButton;

    // Bottom Panel
    JPanel bottomPanel;
    JLabel weatherLabel;

    // Height variables
    int topPanelHeight;
    int bottomPanelHeight;


    public WeatherFrame(){
        initSettings();
        setContents();
        setVisible(true);
    }

    public void initSettings(){
        setSize(400,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Weather");
        setResizable(false);
        topPanelHeight = getHeight()/5;
        bottomPanelHeight = getHeight()-topPanelHeight;
    }

    public void setContents(){
        initTopPanel();
        initBottomPanel();
        add(topPanel);
        add(bottomPanel);
    }

    public void initTopPanel(){
        initInputPanel();
        topPanel = new JPanel();
        topPanel.setBackground(SystemColor.PINK);
        topPanel.setBounds(0,0,getWidth(),topPanelHeight);
        topPanel.setLayout(null);

        cityLabel = new JLabel("Input city");
        cityLabel.setFont(new Font("Monospaced", Font.BOLD,20));
        cityLabel.setBounds(
                getWidth()/2-cityLabel.getPreferredSize().width/2,
                topPanelHeight/2-35,
                cityLabel.getPreferredSize().width,
                cityLabel.getPreferredSize().height
        );
        topPanel.add(cityLabel);
        topPanel.add(inputPanel);
    }

    public void initBottomPanel(){
        bottomPanel = new JPanel();
        bottomPanel.setBackground(SystemColor.gray);
        bottomPanel.setBounds(0,topPanelHeight, getWidth(),bottomPanelHeight);
        bottomPanel.setLayout(null);

        weatherLabel = new JLabel("test");
        weatherLabel.setFont(new Font("Monospaced", Font.BOLD,14));
        weatherLabel.setBounds(15,270,getWidth()-20,bottomPanelHeight-20);
        weatherLabel.setHorizontalAlignment(JLabel.LEFT);
        weatherLabel.setVerticalAlignment(JLabel.TOP);
        weatherLabel.setBackground(Color.blue);

        bottomPanel.add(weatherLabel);
    }

    public void initInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setBounds(getWidth()/2-100, topPanelHeight/2+5, 200,30);
        inputPanel.setLayout(null);

        inputField = new JTextField();
        inputField.setBounds(0,0,150,30);

        inputButton = new Button("Search");
        inputButton.setSize(50,30);
        inputButton.setBounds(150,0,50,30);
        inputButton.addActionListener(e -> {
            System.out.println(
                    inputField.getText()
            );
            weatherLabel.setText(inputField.getText());
        });

        inputPanel.add(inputField);
        inputPanel.add(inputButton);
    }
}
