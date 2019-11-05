package edu.fzu.house.util;

import javax.swing.*;

public class FrameUtil {
    /*GUI界面的通用设置 */
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 800;

    public static void Setting(JFrame frame) {
        //默认关闭
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //默认大小
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        //默认位置
        frame.setLocationRelativeTo(null);
        //默认观感



    }
}
