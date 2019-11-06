package edu.fzu.house.gui.Manager;

import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.util.FrameUtil;

import javax.swing.*;

public class ManagerFrame extends JFrame {
    private JLayeredPane layer_panel= new JLayeredPane();;
    public  ManagerFrame(String uname)
    {
        setSize(800,600);
        FrameUtil.Setting(this,layer_panel,700);


        this.add(layer_panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        ManagerFrame mgf=new ManagerFrame("admin");
    }
}
