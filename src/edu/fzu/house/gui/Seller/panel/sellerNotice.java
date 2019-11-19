package edu.fzu.house.gui.Seller.panel;

import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.panel.modelPanel;
import edu.fzu.house.gui.Manager.table.NoticePanel;
import edu.fzu.house.gui.Seller.SellerFrame;

import javax.swing.*;
import java.awt.*;

public class sellerNotice extends modelPanel {
    private JLayeredPane layer_panel;
    private NoticePanel noPanel;
    public sellerNotice()
    {
        layer_panel=new JLayeredPane();
        layer_panel.setBounds(0, 0, 820, 750);

        noPanel=new NoticePanel(layer_panel, SellerFrame.user.getUname(),new Rectangle(0,0,820,750),
                ManagerFrame.bkcolor
                , Color.white,SellerFrame.user);

        this.add(layer_panel);
    }
}

