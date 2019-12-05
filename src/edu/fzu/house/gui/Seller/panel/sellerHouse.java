package edu.fzu.house.gui.Seller.panel;

import com.sorm.po.Comment;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import edu.fzu.house.core.ActionInterfaces.ActionInterface;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.MsgColumnImpl;
import edu.fzu.house.core.columnInterface.SellerHouseColumImpl;
import edu.fzu.house.core.login.LoginFunc;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.panel.HousePanel;
import edu.fzu.house.gui.Manager.panel.modelPanel;
import edu.fzu.house.gui.Manager.panel.msgPanel;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.Seller.RegisterHouse;
import edu.fzu.house.gui.Seller.SellerFrame;
import edu.fzu.house.gui.login.LoginFrame;
import edu.fzu.house.gui.login.panel.RectInputPanel;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.RoundInputButton;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class sellerHouse extends modelPanel {
    public JLayeredPane layer_panel = new JLayeredPane();
    public DataTablePanel table;
    private String sql;
    public static Color color1=new Color(0xC9DEFB); //单行文本背景色
    public static  Color color2=new Color(246,250,255);  //双行文本背景




    public sellerHouse(Hsuser user) {
        layer_panel.setBounds(0, 0, 820, 750);

        //添加新房屋信息
        ImageIcon icon=new ImageIcon("src/image/Icon/seller/add.png");
        icon=ImageUtil.StretchPngImage(icon,20,20);
        RoundButtonPanel button=new RoundButtonPanel(icon,140,40,"添加新房源",new Color(0x63A2FF),Color.white,12);
        button.setBounds(new Rectangle(600,10,200,40));
        button.button.addActionListener(e->{
            new RegisterHouse(user);
        });

        layer_panel.add(button);

        //表格
        sql = "select hid,hname,htype,harea,addressid,hprice,hstate from house where uname=? order by hprice";


        table = new DataTablePanel(sql, House.class, new Object[]{SellerFrame.user.getUname()}, new String[]{
                "序列号", "房名", "房型", "图片", "地址", "价格", "状态"},
                7,
                new SellerHouseColumImpl(), new Rectangle(30, 50, 600, 650), layer_panel);

        table.setColor1(color1);
        table.setColor2(color2);

        table.addMap("查看", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                java.util.List<Object> key= (List<Object>) Keys[0];
                sellerHouse m=new sellerHouse(user);
                HousePanel panel=new HousePanel((String)key.get(0),m, user);
                layer_panel.add(panel,new Integer(300));
            }
        });

        table.addMap("删除", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                List<Object> keys = (List<Object>) Keys[0];
                MysqlQuery.query.delete(House.class, new Object[]{keys.get(0)});
                table.generateTable();
            }
        });

        table.generateTable();



        add(layer_panel);
    }

}
