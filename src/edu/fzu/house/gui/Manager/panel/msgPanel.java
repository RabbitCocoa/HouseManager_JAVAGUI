package edu.fzu.house.gui.Manager.panel;

import com.sorm.po.Comment;
import com.sorm.po.House;
import edu.fzu.house.core.ActionInterfaces.ActionInterface;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.MsgColumnImpl;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.RoundInputButton;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class msgPanel extends modelPanel {
    public JLayeredPane layer_panel = new JLayeredPane();
    public DataTablePanel table;
    private String sql;
    public static  Color color1=new Color(0xC9DEFB); //单行文本背景色
    public static  Color color2=new Color(246,250,255);  //双行文本背景
    public msgPanel() {
        layer_panel.setBounds(0, 0, 820, 740);
        sql = "select hid,hname,htype,harea,addressid,hprice,uname,hstate from house where hstate=? " +
                "and hname like ? " +
                "order by hprice";
        table = new DataTablePanel(sql, House.class, new Object[]{0,"%"}, new String[]{"序列号", "房名", "房型", "面积", "地址", "价格", "房主"},
                7,
                new MsgColumnImpl(), new Rectangle(30, 50, 530, 650), layer_panel);

        table.setColor1(color1);
        table.setColor2(color2);
        table.addMap("通过", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                //通过后 生成一条消息
                House house = new House();
                List<Object> keys = (List<Object>) Keys[0];
                house.setHid((String) keys.get(0));
                house.setHstate(1);
                MysqlQuery.query.update(house, new String[]{"hstate"});

                Comment comment = ManagerFunc.gengerComment
                        (ManagerFrame.user.getUname(), (String) keys.get(5), "你的请求", ManagerFunc.generateText
                                ((String) keys.get(5), house.getHid(), false));
                System.out.println(comment.getCtext());
                table.generateTable();

            }
        });
        table.addMap("驳回", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                //驳回 删除数据 并生成一条信息  注意要先生成消息
                List<Object> keys = (List<Object>) Keys[0];
                Comment comment = ManagerFunc.gengerComment
                        (ManagerFrame.user.getUname(), (String) keys.get(5), "你的请求", ManagerFunc.generateText
                                ((String) keys.get(5), (String) keys.get(0), true));

                MysqlQuery.query.delete(House.class, new Object[]{keys.get(0)});

                System.out.println(comment.getCtext());
                table.generateTable();
                /*    Comment*/
            }
        });
        table.addMap("查看", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {

            }
        });
        table.generateTable();


        //搜索框

        ImageIcon icon=new ImageIcon("src/image/Icon/Manager/search.png");
        icon = ImageUtil.StretchPngImage(icon, 20, 20);
        RoundInputButton rb=new RoundInputButton(icon,new Rectangle(500,10,250,30),"房间名"
                ,color1,color1,Color.LIGHT_GRAY,new Font(Font.SERIF,Font.PLAIN,12));
        rb.setBackground(Color.white);
        rb.button.addActionListener(e->{
            Object[] obj=table.getParams();
            obj[1]=rb.file.getText()+"%";
            table.generateTable();
        });
        layer_panel.add(rb);


        add(layer_panel);
    }
}
