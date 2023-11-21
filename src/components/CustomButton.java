package components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {

    public CustomButton(String text, Color bgColor, Color bgColorPressed){
        setText(text);
        //setSize(width,heigth);
        setBackground(bgColor);
        setForeground(Color.white);
        setFont(new Font("Arial", Font.PLAIN, 11));
        setBorder(null);
        setBorderPainted(false);
        setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {

            }
            @Override
            protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(bgColorPressed);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(bgColor);
            }
        });

    }

}
