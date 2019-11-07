package edu.fzu.house.gui.login.panel;

import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class RoundButtonPanel extends JPanel  {
    public JButton button;
    public Color bk;
    public Color font;
    public  BufferedImage bi;
    public RoundButtonPanel(ImageIcon icon, int width, int height, String text, Color bk, Color font, int size)
    {
        this.setSize(width,height);
        this.setLayout(null); //空布局
        this.bk=bk;
        this.font=font;

        //画圆角矩形 作为外边框
        bi=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=bi.createGraphics();
       //透明处理
        bi = g2.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2.dispose();
        g2=bi.createGraphics();
        //平滑
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bk);
        //填充圆角矩形
        g2.fill(new RoundRectangle2D.Float(0,0,width-10,height-10,45,45));

        JLabel  label=new JLabel(new ImageIcon(bi));
        label.setBounds(0,0,width,height);

        //按钮本身
        if(text!=null)
         button=new JButton(text);
        if(icon!=null)
            button.setIcon(icon);
        button.setBorderPainted(false); //去除边框
        button.setContentAreaFilled(false); //透明
        button.setFocusPainted(false); //去焦点
        button.setBounds(-10,-5,width,height);
        button.setFont(new Font("微软雅黑", Font.BOLD,size));
        button.setForeground(font);
        button.setHorizontalAlignment(SwingConstants.CENTER);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


                int width=bi.getWidth();
                int height=bi.getHeight();

                Graphics2D g2=bi.createGraphics();
                g2.setColor(bk.brighter());
                g2.fill(new RoundRectangle2D.Float(0,0,width-10,height-10,45,45));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                int width=bi.getWidth();
                int height=bi.getHeight();

                Graphics2D g2=bi.createGraphics();
                g2.setColor(bk);
                g2.fill(new RoundRectangle2D.Float(0,0,width-10,height-10,45,45));
                button.setForeground(font);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            //    button.setForeground(font);
                int width=bi.getWidth();
                int height=bi.getHeight();

                Graphics2D g2=bi.createGraphics();
                g2.setColor(bk);
                g2.fill(new RoundRectangle2D.Float(0,0,width-10,height-10,45,45));
                button.setForeground(font);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                int width=bi.getWidth();
                int height=bi.getHeight();

                Graphics2D g2=bi.createGraphics();
                g2.setColor(bk.darker());
                g2.fill(new RoundRectangle2D.Float(0,0,width-10,height-10,45,45));
                button.setForeground(font.darker());
            }


        });


        this.add(button);
        this.add(label);


        //this.setMargin(new Insets(0,0,0,0));

    }

    public static void main(String[] args) {
        JFrame frame =new JFrame("2");
        frame.setLayout(new GridLayout(2,1));
        frame.setSize(250,400);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.pink);

        ImageIcon icon=new ImageIcon("src/image/Icon/pwd.png");
        icon = ImageUtil.StretchPngImage(icon, 30, 30);
        RoundButtonPanel button=new RoundButtonPanel(icon,200,60,"Go",Color.blue,Color.pink,14);
        button.setBounds(0,0,250,40);

        ImageIcon login_icon=new ImageIcon("src/image/Icon/login.png");
        login_icon=ImageUtil.StretchPngImage(login_icon,30,30);
        RoundButtonPanel button2=new RoundButtonPanel(null,220,40,"GO~~",new Color(102,126,175),
                new Color(219, 237, 255),12);
        button.setBounds(30,300,220,40);
        frame.add(button);
        frame.add(button2);

        frame.setVisible(true);
    }
}
