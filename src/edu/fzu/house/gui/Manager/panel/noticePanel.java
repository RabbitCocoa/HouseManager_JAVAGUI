package edu.fzu.house.gui.Manager.panel;

import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.Manager.table.NoticePanel;

import javax.swing.*;
import java.awt.*;

public class noticePanel extends modelPanel {
     private JLayeredPane  layer_panel;
     private NoticePanel noPanel;
    public noticePanel()
    {
        layer_panel=new JLayeredPane();
        layer_panel.setBounds(0, 0, 820, 750);

        noPanel=new NoticePanel(layer_panel,ManagerFrame.user.getUname(),new Rectangle(0,0,820,750),
                ManagerFrame.bkcolor
                , Color.white,ManagerFrame.user);

        this.add(layer_panel);
    }
}
