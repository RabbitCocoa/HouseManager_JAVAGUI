package edu.fzu.house.gui.login.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextButton extends JButton {
    public TextButton(String text,Rectangle r)
    {
        super(text);

        this.setBorderPainted(false); //去除边框
        this.setContentAreaFilled(false); //透明
        this.setFocusPainted(false); //去焦点
        this.setBounds(r);
        this.setFont(new Font(Font.SERIF,Font.PLAIN,12));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                setForeground(Color.black);
            }
        });
    }
}
