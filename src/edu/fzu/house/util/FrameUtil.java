package edu.fzu.house.util;

import com.sun.awt.AWTUtilities;
import edu.fzu.house.gui.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class FrameUtil {
    /*GUI界面的通用设置 */
    private static Point mouse_position = new Point(0, 0);

    //鼠标拖住移动窗口
    public static void MoveWindow(JFrame frame,int x, int y) {
        frame.setLocation(frame.getX() + x, frame.getY() + y);
    }

    /*@frame 一般传自己
         @Comment 传添加关闭按钮的容器
         @posx     传右移的位置
         @Color 背景色
       */
    public static void Setting(JFrame frame,JComponent panel,int posx,Color c) {
        //默认关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //默认位置
        frame.setLocationRelativeTo(null);
        //默认观感
        frame.setUndecorated(true);//无标题栏
        AWTUtilities.setWindowShape(frame, new RoundRectangle2D.Double(0.0d, 0.0d, frame.getWidth(), frame.getHeight(),
                30, 30));//圆角窗口
        //默认背景颜色
        frame.getContentPane().setBackground(c);
        //默认焦点
        frame.setFocusable(true);

        //默认监控
        //监控鼠标 可以拖动
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouse_position = e.getPoint();
            }

        });
        frame.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point now_position = e.getPoint();
                int x = now_position.x - mouse_position.x;
                int y = now_position.y - mouse_position.y;
                MoveWindow(frame,x, y);
            }
        });

        //默认关闭按钮 可选择位置
        /*关闭按钮*/
        ImageIcon close_image = new ImageIcon("src/image/Icon/login/close.png");
        close_image = ImageUtil.StretchPngImage(close_image, 40, 40);
        JButton close_button = new JButton(close_image);
        close_button.setBorderPainted(false); //去除边框
        close_button.setBackground(c); //透明
        close_button.setFocusPainted(false); //去焦点
        //设置颜色事件
        close_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                close_button.setBackground(c.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                close_button.setBackground(c);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                close_button.setBackground(c.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                close_button.setBackground(c);
            }
        });
        //关闭事件
        close_button.addActionListener(e -> {
            System.exit(0);
        });
        //按钮大小
        close_button.setBounds(posx, 5, 40, 40);
        panel.add(close_button);
    }
}
