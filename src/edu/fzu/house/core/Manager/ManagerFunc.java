package edu.fzu.house.core.Manager;

import com.sorm.po.Haddress;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.po.Huser;
import edu.fzu.house.core.columnInterface.ColumnInterface;
import edu.fzu.house.core.confirmInterface.Confirm;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.IOUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerFunc  {
    public static Hsuser getHsuer(String uname)
    {
        //取得个人信息
        Hsuser user=null;
        String sql="select * from hsuser where uname=?";
        user=(Hsuser)MysqlQuery.query.queryRows(sql,Hsuser.class,new Object[]{uname}).get(0);
        return user==null?null:user;
    }

    public static boolean confirm(Object msg,Confirm con)
    {
        if(con==null)
            return true;
        return con.confirm(msg);
    }

    //根据房间id返回照片数据
    public static List<byte[]> getImage(String hID)
    {
        String sql="select photo from house where hid=?";
        String src=(String)MysqlQuery.query.queryValue(sql,new Object[]{hID});

        return IOUtil.readStrImage(src);
    }
    //根据地址号返回地址
    public static String getAddress(int id)
    {
        String sql="select * from haddress where addressid=?";
        Haddress address=(Haddress)MysqlQuery.query.queryRows(sql,Haddress.class,new Object[]{id}).get(0);
        return address.getProvince()+address.getTown();

    }

    /*返回主键-数据 对应的map 用于生成表格*/
    /* sql 查询数据  clz查询的表  params 参数 page 页数 size 一页的大小 dio 处理数据的接口

        改 为了方便排序 改用Integer做key

    * */
    public static HashMap<Integer, List<Object>> getMap(String sql,Class clz,
                                                       Object[] params, int page, int size,ColumnInterface dio){
        HashMap<Integer,List<Object>> map=new HashMap<>();
        List<Object> list = MysqlQuery.query.queryPagenate(sql, clz,  params, page, size);
        /*实现一个接口 对数据进行处理*/
        map=dio.getMap(list);

        return map;
    }

    public static void main(String[] args) {
     //   getHsuer("admin");
        System.out.println(String.valueOf(5000));
    }
}
