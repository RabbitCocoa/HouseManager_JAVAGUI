package edu.fzu.house.gui.Manager.panel;

import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.po.Huser;
import edu.fzu.house.core.ActionInterfaces.ActionInterface;
import edu.fzu.house.core.columnInterface.MsgColumnImpl;
import edu.fzu.house.core.columnInterface.UserColumnImpl;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.RoundInputButton;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class userPanel extends modelPanel {
    public JLayeredPane layer_panel = new JLayeredPane();
    public DataTablePanel table;
    private String sql;
    public static  Color color1=new Color(0xC9DEFB); //单行文本背景色
    public static  Color color2=new Color(246,250,255);  //双行文本背景
    public userPanel() {
        layer_panel.setBounds(0, 0, 820, 740);


        sql = "select distinct huser.uname,sname,photo,ssex,logintime,sphone " +
                " from hsuser join huser on huser.uname=hsuser.uname where (sname like ? or sname like ?) and utype=? order by logintime";

        table = new DataTablePanel(sql, Hsuser.class, new Object[]{"%","%",1}, new String[]{"账号", "用户名", "头像",
                "性别", "注册时间", "电话号码"},
                6,
                new UserColumnImpl(), new Rectangle(30, 50, 650, 650), layer_panel);

        table.addMap("封禁", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                java.util.List<Object> keys = (List<Object>) Keys[0];
                Hsuser user = new Hsuser();
                user.setUname((String) keys.get(0));
                int count = MysqlQuery.query.delete(user);
                table.generateTable();
            }
        });

        table.setColor1(color1);
        table.setColor2(color2);
        table.generateTable();



        //搜索框和下拉框
        JComboBox<String> roles=new JComboBox<>();
        roles.addItem("卖家");
        roles.addItem("买家");
        roles.addActionListener(e->{
            String role= roles.getItemAt(roles.getSelectedIndex());
            Object[] obj=table.getParams();
            if(role.equals("卖家"))
                obj[2]=1;
            else
                obj[2]=0;
            table.generateTable();
        });
        roles.setEditable(false);
        roles.setBounds(300,10,100,30);
        layer_panel.add(roles);

        //搜索框

        ImageIcon icon=new ImageIcon("src/image/Icon/Manager/search.png");
        icon = ImageUtil.StretchPngImage(icon, 20, 20);
        RoundInputButton rb=new RoundInputButton(icon,new Rectangle(500,10,250,30),"用户名"
        ,color1,color1,Color.LIGHT_GRAY,new Font(Font.SERIF,Font.PLAIN,12),false);
        rb.setBackground(Color.white);
        rb.button.addActionListener(e->{
            Object[] obj=table.getParams();
            obj[0]=rb.file.getText()+"%";
            obj[1]="%"+rb.file.getText()+"%";
            table.generateTable();
        });
        layer_panel.add(rb);

        add(layer_panel);
    }
}
