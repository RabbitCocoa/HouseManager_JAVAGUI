package edu.fzu.house.gui.Seller.panel;

import com.sorm.po.Comment;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.po.Orderform;
import edu.fzu.house.core.ActionInterfaces.ActionInterface;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.DealingColumnImpl;
import edu.fzu.house.core.columnInterface.MsgColumnImpl;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Buyer.BuyerFrame;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.panel.modelPanel;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.Seller.SellerFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class SellerDealing extends modelPanel {
    public JLayeredPane layer_panel = new JLayeredPane();
    public DataTablePanel table;
    private String sql;
    public static  Color color1=new Color(0xC9DEFB); //单行文本背景色
    public static  Color color2=new Color(246,250,255);  //双行文本背景

    public SellerDealing(Hsuser user)
    {
        layer_panel.setBounds(0, 0, 820, 750);
        sql = "select oid,hid,hname,photo,hprice,bid,ostate,createtime from orderform " +
                " where sid=?  " +
                "order by createtime";
        table = new DataTablePanel(sql, Orderform.class, new Object[]{user.getUname()}, new String[]{"订单号", "房源号",
                "房名","照片","价格",  "买家名", "当前状态"
        ,"创造时间"},
                8,
                new DealingColumnImpl(), new Rectangle(10, 50, 720, 650), layer_panel);


        table.addMap("确认", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                java.util.List<Object> keys = (List<Object>) Keys[0];
                String state= (String) keys.get(6);
                if(state.equals("等待买家付款"))
                {
                    JOptionPane.showMessageDialog(null,"请等待卖家付款");
                    return;
                }
                else if(("已完成".equals(state)||"已退款".equals(state)))
                {
                    JOptionPane.showMessageDialog(null,"这笔订单已经完成啦~");
                    return;
                }
                else if("订单信息异常".equals(state))
                {
                    JOptionPane.showMessageDialog(null,"已提交给管理员处理");
                    /*其实没有处理哒*/
                    return;
                }
                else if("等待确认".equals(state))
                {
                  try {
                      //订单状态处理
                      Orderform order = new Orderform();
                      order.setOstate(2);
                      order.setOid((String) keys.get(0));

                      MysqlQuery.query.update(order, new String[]{"ostate"});

                      //Hsuser加钱
                      Hsuser hsuser= SellerFrame.user;

                      int price=MysqlQuery.query.queryNumber("select hprice from orderform where oid=?",
                              new Object[]{order.getOid()}).intValue();

                      hsuser.setSurplus(hsuser.getSurplus()+price);
                      MysqlQuery.query.update(hsuser,new String[]{"Surplus"});

                      //House信息删除
                      House house = new House();
                      house.setHid((String) keys.get(1));
                      MysqlQuery.query.delete(house);
                      JOptionPane.showMessageDialog(null,"订单已确认");
                  }
                  catch (Exception e)
                  {
                      e.printStackTrace();
                  }
                }
                else if("退款中".equals(state)){
                    //订单状态处理
                    Orderform order = new Orderform();
                    order.setOstate(4);
                    order.setOid((String) keys.get(0));

                    MysqlQuery.query.update(order, new String[]{"ostate"});
                    //House状态改变
                    House house = new House();
                    house.setHid((String) keys.get(1));
                    house.setHstate(1);
                    MysqlQuery.query.update(house,new String[]{"hstate"});


                    //Hsuser加钱
                    Hsuser hsuser= (Hsuser) MysqlQuery.query.queryRows("select * from hsuser where uname=?"
                            ,Hsuser.class,new Object[]{keys.get(5)}).get(0);

                    int price=MysqlQuery.query.queryNumber("select hprice from orderform where oid=?",
                                new Object[]{order.getOid()}).intValue();

                        hsuser.setSurplus(hsuser.getSurplus()+price);
                        MysqlQuery.query.update(hsuser,new String[]{"Surplus"});

                        //发送一条退款信息
                        Comment comment=new Comment();
                        comment.setCtext("您好,你的退款消息已处理,退款金额:"+price+"元已退到您的账户上。");
                        comment.setCtitle("系统通知");
                        comment.setCname(ManagerFunc.generateSerialNum(8));
                        comment.setCcreatetime(new Timestamp(System.currentTimeMillis()));
                        comment.setCid("admin");
                        comment.setBcid(hsuser.getUname());
                        comment.setCdegree(0);
                        MysqlQuery.query.insert(comment);



                    JOptionPane.showMessageDialog(null,"退款成功");

                }
                table.generateTable();

            }
        });

        table.setColor1(color1);
        table.setColor2(color2);
        table.generateTable();
        add(layer_panel);
    }

    public static void main(String[] args) {
        Orderform order=new Orderform();
        order.setOid(ManagerFunc.generateSerialNum(12));
        order.setHid("6509309881");
        order.setBid("buy1");
        order.setSid("admin5");
        order.setHprice(350000);
        order.setHname("Rabbit House");
        order.setCreatetime(new Timestamp(System.currentTimeMillis()));
        order.setOstate(0);
        MysqlQuery.query.insert(order);
    }
}
