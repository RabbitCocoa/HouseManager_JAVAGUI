package edu.fzu.house.core.columnInterface;

import com.sorm.po.House;
import com.sorm.po.Orderform;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DealingColumnImpl implements  ColumnInterface {
    @Override
    public HashMap<Integer, List<Object>> getMap(List<Object> list)
    {
        HashMap<Integer,List<Object>> maps=new HashMap<>();
        int i=0;

        if(list!=null)
        {
            for (Object o : list) {
                o= (Orderform)o;
                List<Object> lists = new ArrayList<>();
                //订单号
                lists.add(((Orderform) o).getOid());
                //房源号
                lists.add(((Orderform) o).getHid());
                //房名
                lists.add(((Orderform) o).getHname());
                //照片
                lists.add((byte[])IOUtil.readStrImage(((Orderform) o).getPhoto()).get(0));
                //价格 用万表示
                int value=((Orderform) o).getHprice();
                StringBuilder apprice=new StringBuilder();
                if(value>10000)
                {
                    apprice.append(value/10000+".").append((value-value/10000*10000)+"万");
                }
                else
                {
                    apprice.append(value+"元");
                }
                lists.add(apprice.toString());

                //买家名
                lists.add(((Orderform) o).getBid());
                //状态
                int state=((Orderform) o).getOstate();
                String msg_state;
                switch (state)
                {
                    case 0:
                        msg_state="等待买家付款";
                        break;
                    case 1:
                        msg_state="等待确认";
                        break;
                    case 2:
                        msg_state="已完成";
                        break;
                    case 3:
                        msg_state="退款中";
                        break;
                    case 4:
                        msg_state="已退款";
                        break;
                    default:
                        msg_state="订单信息异常";
                        break;
                }
                lists.add(msg_state);
                //创造时间
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String time=simpleDateFormat.format(((Orderform) o).getCreatetime());
                lists.add(time);
                maps.put(i++,lists);
            }
        }
        return maps;
    }
}
