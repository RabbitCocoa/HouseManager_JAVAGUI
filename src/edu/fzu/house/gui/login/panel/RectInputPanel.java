package edu.fzu.house.gui.login.panel;

import edu.fzu.house.gui.login.LoginFrame;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class RectInputPanel extends JLayeredPane {
    private int width = 250;
    private int height = 40;
    private JPasswordField password;
    private JTextField input;
    public  JLabel lab;
    public BufferedImage bi;
    public void setXY(int x,int y)
    {
        lab.setBounds(x,y,200,50);
    }

    public JPasswordField getPassword() {
        return password;
    }

    public JTextField getInput() {
        return input;
    }

    private boolean pwd;
    public String getText()
    {
            if(pwd)
                return new String(password.getPassword());
            return
                    input.getText();
    }
    public static RectInputPanel addInput(ImageIcon icon, String text, boolean pwd, RectInputPanel input, Rectangle r) {
        if(icon!=null)
        icon = ImageUtil.StretchPngImage(icon, 30, 30);
        input = new RectInputPanel(icon, text, pwd);
        input.setBounds(r);
        return input;
    }
    public RectInputPanel(ImageIcon icon, String text, boolean pwd) {
        setLayout(null);//设置空布局
        this.setSize(width, height);
        this.pwd=pwd;
        //绘制
         bi = new BufferedImage(210, height, BufferedImage.TYPE_INT_RGB);
        /*透明处理*/
        Graphics2D g = bi.createGraphics();
        bi = g.getDeviceConfiguration().createCompatibleImage(210, height, Transparency.TRANSLUCENT);
        g.dispose();
        g = bi.createGraphics();
        //
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING , RenderingHints.VALUE_ANTIALIAS_ON); //设置抗锯齿
        g.setStroke(new BasicStroke(2.0f));
        g.draw(new RoundRectangle2D.Double(0, 0, 208, height - 2, 45, 45));
        g.drawImage(icon.getImage(), 10, 5, null);

        JLabel label = new JLabel(new ImageIcon(bi));
        label.setBounds(20, 0, 210, 40);
        this.add(label);
        //设置文本输入框属性
        if(!pwd){
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
                        input.setForeground(new Color(219, 237, 255));
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
            //设置焦点获得事件
            input.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (input.getFont().getSize() < 12) {
                        input.setText("");
                        input.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
                        input.setForeground(new Color(219, 237, 255));
                        input.setHorizontalAlignment(JTextField.LEFT);//居中对齐
                    }
                }
            });

            input.setHorizontalAlignment(JTextField.CENTER);//居中对齐
            this.add(input);
        }
        else{
            lab=new JLabel();
            lab.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 11));
            lab.setForeground(Color.gray);
            lab.setText(text);
            lab.setBounds(105,-5,200,50);

            password = new JPasswordField();
            password.setBounds(80, 5, 130, 30);
            password.setBorder(null);//无边框
            password.setOpaque(false);//设置透明
            password.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 11));
            password.setForeground(Color.gray);
            //设置文本输入框事件
            password.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (!lab.getText().equals("")) {
                        lab.setText("");
                        password.setText("");
                        password.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
                        password.setForeground(new Color(219, 237, 255));
                        password.setHorizontalAlignment(JTextField.LEFT);//居中对齐
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
            //设置焦点事件
            //设置焦点获得事件
            password.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (!lab.getText().equals("")) {
                        lab.setText("");
                        password.setText("");
                        password.setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
                        password.setForeground(new Color(219, 237, 255));
                        password.setHorizontalAlignment(JTextField.LEFT);//居中对齐
                    }
                }
            });
            password.setHorizontalAlignment(JTextField.CENTER);//居中对齐
            this.add(lab);
            this.add(password);
        }




    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("实例");

        frame.setSize(250,400);
        ImageIcon icon=new ImageIcon("src/image/Icon/uname.png");
        icon= ImageUtil.StretchPngImage(icon,40,40);
        RectInputPanel panel=new RectInputPanel(icon,"密码",true);
        RectInputPanel panel2=new RectInputPanel(icon,"用户名",false);

        frame.setLayout(new GridLayout(3,1));
        frame.add(panel);
        frame.add(panel2);
        frame.setVisible(true);
        frame.setFocusable(true);
    }
}





