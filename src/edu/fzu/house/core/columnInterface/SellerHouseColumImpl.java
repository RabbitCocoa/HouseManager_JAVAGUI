package edu.fzu.house.core.columnInterface;

import com.sorm.po.House;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.MysqlQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellerHouseColumImpl implements ColumnInterface {

    public HashMap<Integer, List<Object>> getMap(List<Object> list)
    {
        HashMap<Integer, List<Object>> map=new HashMap<>();
        int i=0;
        if(list!=null)
            for (Object o : list) {
                List<Object> lists = new ArrayList<>();
                House house = (House) o;
                lists.add( house.getHid());
                lists.add( house.getHname());
                /*根据房型转化为汉字*/
                switch (house.getHtype()) {
                    case 1:
                        lists.add(2, new String("一室"));
                        break;
                    case 2:
                        lists.add( new String("二室"));
                        break;
                    case 3:
                        lists.add( new String("三室"));
                        break;
                    case 4:
                        lists.add( new String("四室"));
                        break;
                    case 5:
                        lists.add( new String("五室"));
                        break;
                    default:
                        lists.add(new String("五室及以上"));
                        break;
                }
                byte[] photo= ManagerFunc.getImage(house.getHid()).get(0);
                //存入第一张图像数据
                lists.add(photo);
                //地址
                lists.add(ManagerFunc.getAddress(house.getAddressid()));
                //价格
                lists.add(String.valueOf(house.getHprice()));
                //状态
                if(house.getHstate()==0)
                    lists.add("待审核");
                else if(house.getHstate()==1)
                    lists.add("待出售");
                    else{ lists.add("交易中...");
                }
                map.put(i++,lists);
            }
        return map;
    }

}
