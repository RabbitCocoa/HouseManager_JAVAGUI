package edu.fzu.house.gui.login.panel;

import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

//带图标的按钮+输入框 类似输入框
public class RoundInputButton extends JPanel{
    public JButton button=new JButton();
    public JTextField file;


    public RoundInputButton(ImageIcon icon, Rectangle r, String text, Color btcolor, Color Input,Color line, Font font)
    {
        this.setBounds(r);
        this.setLayout(null); //空布局
        this.setBounds(r.x,r.y,r.width,r.height);
        int width=r.width;
        int height=r.height;
        //画圆角矩形 作为外边框
        BufferedImage bi=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=bi.createGraphics();
        //透明处理
        bi = g2.getDeviceConfiguration().createCompatibleImage(width,height, Transparency.TRANSLUCENT);
        g2.dispose();
        g2=bi.createGraphics();
        //平滑
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //填充圆角矩形
        g2.setColor(btcolor);
        g2.fill(new RoundRectangle2D.Float(0,0,width,height-1,30,30));

        g2.setColor(Input);
        g2.fill(new RoundRectangle2D.Float(0,0,width/5,height-1,30,30));
        JLabel  label=new JLabel(new ImageIcon(bi));
        label.setBounds(0,0,width,height);
        //画边线
        g2.setColor(line);
        g2.draw(new RoundRectangle2D.Float(0,0,width,height-1,30,30));
        g2.draw(new RoundRectangle2D.Float(0,0,width/5,height-1,30,30));

        button.setIcon(icon);
        button.setBorderPainted(false); //去除边框
        button.setContentAreaFilled(false); //透明
        button.setFocusPainted(false); //去焦点
        button.setBounds(0,0,width/5,height);
      //  button.setForeground(font);
        button.setHorizontalAlignment(SwingConstants.CENTER);

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

        //增加输入框
         file=new JTextField(text);
         file.addFocusListener(new FocusAdapter() {
             @Override
             public void focusGained(FocusEvent e) {
                 if(file.getText().equals(text))
                 {
                     file.setText("");
                     file.setFont(font);
                     file.setForeground(Color.black);
                 }
             }
         });
        file.setFont(new Font(Font.SERIF,Font.PLAIN,12));
        file.setForeground(Color.gray);
        file.setBounds(width/4,0,width-width/3,height);
        file.setOpaque(false);
        file.setBorder(null);

        this.add(file);
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
        frame.setLayout(null);

        ImageIcon icon=new ImageIcon("src/image/Icon/Manager/search.png");
        icon = ImageUtil.StretchPngImage(icon, 35, 35);

        RoundInputButton button=new RoundInputButton
                (icon,new Rectangle(20,20,200,40),"", Color.white,
                        Color.gray, Color.gray,new Font(Font.SERIF,Font.PLAIN,16));



        frame.add(button);


        frame.setVisible(true);
    }
}
