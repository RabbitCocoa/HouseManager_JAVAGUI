package edu.fzu.house.core.columnInterface;

import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.po.Huser;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.MysqlQuery;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserColumnImpl implements  ColumnInterface{
    public HashMap<Integer, List<Object>> getMap(List<Object> list)
    {
        HashMap<Integer, List<Object>> map=new HashMap<>();
        int i=0;
        if(list!=null)
            for (Object o : list) {
                List<Object> lists = new ArrayList<>();
                Hsuser user = (Hsuser) o;
                lists.add( user.getUname());
                lists.add( user.getSname());
                lists.add(user.getPhoto());
                lists.add(user.getSsex());
                Timestamp time=user.getLogintime();
                String now=new SimpleDateFormat("yyyy-MM-dd").format(time);

                lists.add(now);
                lists.add(user.getSphone());

                map.put(i++,lists);
            }
        return map;
    }
}
