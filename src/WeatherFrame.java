import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WeatherFrame extends JFrame {

    // Top Panel
    JPanel topPanel;
    JLabel cityLabel;

    // Input Panel
    JPanel inputPanel;
    JTextField inputField;
    JButton inputButton;

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
        setSize(350,550);
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
        topPanel.setBackground(new Color(92,84,112));


        cityLabel = new JLabel("Search city");
        cityLabel.setFont(new Font("Arial", Font.PLAIN,16));
        cityLabel.setForeground(new Color(250,240,230));
        cityLabel.setBounds(
                20,
                20,
                cityLabel.getPreferredSize().width+5,
                cityLabel.getPreferredSize().height
        );

//        cityLabel.setBounds(
//                getWidth()/2-cityLabel.getPreferredSize().width/2-5,
//                topPanelHeight/2-30,
//                cityLabel.getPreferredSize().width+5,
//                cityLabel.getPreferredSize().height
//        );
//        topPanel.add(cityLabel);
        initInputPanel();
        topPanel.add(cityLabel);
        topPanel.add(inputPanel);
    }

    public void initBottomPanel(){
        bottomPanel = new JPanel();
        bottomPanel.setBounds(0,topPanelHeight, getWidth(),bottomPanelHeight);
        bottomPanel.setLayout(null);
        bottomPanel.setBackground(new Color(53,47,68));

        weatherLabel = new JLabel("test");
        weatherLabel.setFont(new Font("Monospaced", Font.BOLD,14));
        weatherLabel.setForeground(Color.white);
        weatherLabel.setBounds(15,270,getWidth()-20,bottomPanelHeight-20);
        weatherLabel.setHorizontalAlignment(JLabel.LEFT);
        weatherLabel.setVerticalAlignment(JLabel.TOP);

        bottomPanel.add(weatherLabel);
    }

    public void initInputPanel(){
        inputPanel = new JPanel();
        inputPanel.setBounds(cityLabel.getWidth()+30, 15, 200,30);
//        inputPanel.setBounds(getWidth()/2-100, topPanelHeight/2, 200,30);
        inputPanel.setLayout(null);
        inputPanel.setBackground(new Color(53,47,68));

        inputField = new JTextField(20);
        inputField.setBounds(1,1,129,28);
        inputField.setBackground(new Color(250,240,230));
        inputField.setBorder(new EmptyBorder(0,5,0,5));
        inputField.setForeground(new Color(26,47,68));

        inputButton = new JButton("Search");
        inputButton.setSize(70,30);
        inputButton.setBounds(130,0,70,30);
        inputButton.setBackground(new Color(53,47,68));
        inputButton.setForeground(Color.white);
        inputButton.setFont(new Font("Arial", Font.PLAIN, 11));
        inputButton.setBorder(null);
        inputButton.setBorderPainted(false);
        inputButton.setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {

            }
            @Override
            protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {

            }
        });

        inputButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                inputButton.setBackground(new Color(41,37,53));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                inputButton.setBackground(new Color(53,47,68));
            }
        });
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
