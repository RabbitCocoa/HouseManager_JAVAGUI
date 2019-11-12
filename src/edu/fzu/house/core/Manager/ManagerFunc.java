package edu.fzu.house.core.Manager;

import com.sorm.po.*;
import edu.fzu.house.core.columnInterface.ColumnInterface;
import edu.fzu.house.core.confirmInterface.Confirm;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.MathUtil;

import javax.swing.*;
import java.awt.color.CMMException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerFunc  {

    //生成一个n位的序列号
    public static String generateSerialNum(int n)
    {
        StringBuilder result=new StringBuilder();
        for(int i=0;i<n;i++)
        {
            int count= (int)(Math.random()*10);
            result.append(String.valueOf(count));
        }
        return result.toString();
    }
    //生成一条发送消息
    public static Comment gengerComment(String send,String recv,String title,String text)
    {
        Comment comment=new Comment();
        comment.setCdegree(0);//消息
        comment.setCname(generateSerialNum(20)); //序列号
        //检查是否重复
        while(((long)MysqlQuery.query.queryNumber("select count(cname) from comment where cname=?",
                new Object[]{comment.getCname()})!=0))
        {
            comment.setCname(generateSerialNum(20));
        }
        //
        comment.setCid(send);
        comment.setBcid(recv);
        comment.setCtitle(title);
        comment.setCtext(text);
        comment.setCcreatetime(new Timestamp(System.currentTimeMillis()));
        return comment;

    }

    //生成一条模板正文
    public static String generateText(String id,String hid,boolean fail)
    {
        StringBuilder builder=new StringBuilder();
        String hname=(String)MysqlQuery.query.queryValue("select hname from house where hid=?",new Object[]{hid});

        builder.append("尊敬的").append(id).append("你好,\n");
        builder.append("    你的").append(hname);
        if(fail)
            builder.append("未能通过审核,请重新尝试");
        else
            builder.append("已通过审核，感谢你的支持");
        return builder.toString();
    }

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
