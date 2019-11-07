package edu.fzu.house.core.login;

import com.sorm.core.Query;
import com.sorm.po.House;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MysqlQuery extends Query {
    public static MysqlQuery query = new MysqlQuery();
    @Override
    public List<Object> queryPagenate(String sql, Class clazz,Object[] params,int pagenum,int size){
        pagenum-=1;
        sql+=" limit ?,?";
        Object[] newparams=new Object[params.length+2];
        for(int i=0;i<params.length;i++)
            newparams[i]=params[i];
        newparams[params.length]=pagenum*size;
        newparams[params.length+1]=size;

        return  queryRows(sql,clazz,newparams);
    }

    public static void main(String[] args) {
        String sql="select * from house where hstate=?";
        List<Object> list=new ArrayList<>();

        list= query.queryPagenate(sql,House.class,new Object[]{0},2,10);
        for (Object ob : list) {
            House house=(House)ob;
            System.out.println(house.getHid());
        }

    }
}
