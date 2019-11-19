package edu.fzu.house.gui.Manager.button;

import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class CircleButton  extends JPanel {
    public JButton button;
    public CircleButton(Color c,Rectangle r)
    {
        this.setLayout(null); //空布局
        this.setBounds(r);
        this.setOpaque(false);
        //画圆角矩形 作为外边框
        BufferedImage bi=new BufferedImage(r.width,r.height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=bi.createGraphics();
        //透明处理
        bi = g2.getDeviceConfiguration().createCompatibleImage(r.width,r.height, Transparency.TRANSLUCENT);
        g2.dispose();
        g2=bi.createGraphics();
        //平滑
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //填充圆角矩形
        g2.setColor(c);
        g2.fill(new Ellipse2D.Double(0,0,r.width-1,r.height-1));

        JLabel label=new JLabel(new ImageIcon(bi));
        label.setBounds(0,0,r.width,r.height);
        this.add(label);

        //增加按钮
        button=new JButton();
        button.setBorderPainted(false); //去除边框
        button.setContentAreaFilled(false); //透明
        button.setFocusPainted(false); //去焦点
        button.setBounds(r.width/10,0,r.width*9/10,r.height);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        this.add(button);


    }

    public static void main(String[] args) {
        JFrame frame =new JFrame("2");
        frame.setLayout(new GridLayout(2,1));
        frame.setSize(250,400);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.pink);
        frame.setLayout(null);

        CircleButton button=new CircleButton(Color.yellow,new Rectangle(40,40,40,40));
        frame.add(button);
        frame.setVisible(true);
    }
}
