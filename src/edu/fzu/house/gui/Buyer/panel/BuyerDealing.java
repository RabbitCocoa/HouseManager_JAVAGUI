package edu.fzu.house.gui.Buyer.panel;

import com.sorm.po.Comment;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.po.Orderform;
import edu.fzu.house.core.ActionInterfaces.ActionInterface;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.BuyerDealingColumnImpl;
import edu.fzu.house.core.columnInterface.DealingColumnImpl;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Buyer.BuyerFrame;
import edu.fzu.house.gui.Manager.panel.modelPanel;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.Seller.SellerFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class BuyerDealing extends modelPanel {
    public JLayeredPane layer_panel = new JLayeredPane();
    public DataTablePanel table;
    private String sql;
    public static Color color1 = new Color(0xC9DEFB); //单行文本背景色
    public static Color color2 = new Color(246, 250, 255);  //双行文本背景

    public BuyerDealing(Hsuser user) {
        layer_panel.setBounds(0, 0, 820, 750);
        sql = "select oid,hid,hname,photo,hprice,sid,ostate,createtime from orderform " +
                " where bid=?  " +
                "order by createtime";
        table = new DataTablePanel(sql, Orderform.class, new Object[]{user.getUname()}, new String[]{"订单号", "房源号",
                "房名", "照片", "价格", "卖家名", "当前状态"
                , "创造时间"},
                8,
                new BuyerDealingColumnImpl(), new Rectangle(10, 50, 720, 650), layer_panel);

        table.addMap("取消交易", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                List<Object> ks = (List<Object>) Keys[0];
                String state = (String) ks.get(6);
                switch (state) {
                    case "已完成":
                        JOptionPane.showMessageDialog(null, "订单已完成，无法退款");
                        break;
                    case "正在退款中":
                    case "已退款":
                        JOptionPane.showMessageDialog(null, "你的请求已在处理或已处理完成。");
                        return;

                }

                int count = JOptionPane.showConfirmDialog(null, "你确定要取消本次交易吗？", "提示",
                        JOptionPane.YES_NO_OPTION);

                if (count == 1) {
                    return;
                } else {
                    Orderform order = new Orderform();
                    order.setOid((String)ks.get(0));



                    //给卖家发送消息
                    String sql3="select sid from orderform where oid=?";
                    String sid= (String) MysqlQuery.query.queryValue(sql3,new Object[]{order.getOid()});

                    //发送一条订单信息
                    Comment comment=new Comment();
                    comment.setCtext("您好,你的订单"+order.getOid()+"收到一条退款消息，请及时处理。");
                    comment.setCtitle("系统通知");
                    comment.setCname(ManagerFunc.generateSerialNum(8));
                    comment.setCcreatetime(new Timestamp(System.currentTimeMillis()));
                    comment.setCid("admin");
                    comment.setBcid(sid);
                    comment.setCdegree(0);
                    MysqlQuery.query.insert(comment);

                    /*设置订单状态为退款中*/
                    order.setOstate(3);
                    MysqlQuery.query.update(order,new String[]{"ostate"});
                    

                    JOptionPane.showMessageDialog(null,"退款成功");
                }


            }
        });

        table.setColor1(color1);
        table.setColor2(color2);
        table.generateTable();
        add(layer_panel);
    }
}
