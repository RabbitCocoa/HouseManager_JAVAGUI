package edu.fzu.house.gui.login.panel;

import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class RectInputPanel extends JPanel {
    private int width = 250;
    private int height = 40;
    private JPasswordField password;
    private JTextField input;
    private boolean pwd;
    public String getText()
    {
            if(pwd)
                return password.getPassword().toString();
            return
                    input.getText();
    }

    public RectInputPanel(ImageIcon icon, String text, boolean pwd) {
        setLayout(null);//设置空布局
        this.setSize(width, height);
        this.pwd=pwd;
        //绘制
        BufferedImage bi = new BufferedImage(210, height, BufferedImage.TYPE_INT_RGB);
        /*透明处理*/
        Graphics2D g = bi.createGraphics();
        bi = g.getDeviceConfiguration().createCompatibleImage(210, height, Transparency.TRANSLUCENT);
        g.dispose();
        g = bi.createGraphics();
        //
        g.draw(new RoundRectangle2D.Double(0, 0, 210, height - 2, 30, 30));
        g.drawImage(icon.getImage(), 10, 0, null);

        JLabel label = new JLabel(new ImageIcon(bi));
        label.setBounds(20, 0, 210, 40);
        this.add(label);
        //设置文本输入框属性
            input = new JTextField(text);
            input.setBounds(80, 5, 130, 30);
            input.setBorder(null);//无边框
            input.setOpaque(false);//设置透明
            input.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 11));
            input.setForeground(Color.gray);
            //设置文本输入框事件
            input.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (input.getFont().getSize() < 12) {
                        input.setText("");
                        input.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
                        input.setForeground(Color.RED);
                        input.setHorizontalAlignment(JTextField.LEFT);//居中对齐
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            input.setHorizontalAlignment(JTextField.CENTER);//居中对齐
            this.add(input);

        //如果pwd为true 则加入密码框事件



    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("实例");

        frame.setSize(250,400);
        ImageIcon icon=new ImageIcon("src/image/Icon/login.png");
        icon= ImageUtil.StretchPngImage(icon,40,40);
        RectInputPanel panel=new RectInputPanel(icon,"密码",true);
        frame.add(panel);
        frame.setVisible(true);
        frame.setFocusable(true);
    }
}





