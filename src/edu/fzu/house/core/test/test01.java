package edu.fzu.house.core.test;

import edu.fzu.house.core.Manager.ManagerFunc;

import java.util.ArrayList;
import java.util.List;

//测试生成随机数的重复问题
public class test01 {
    public static void main(String[] args) {
        List<String> lists=new ArrayList<>();
        for(int i=0;i<100;i++)
        {
            String num=ManagerFunc.generateSerialNum(20);
            System.out.println(num);
            if(lists.contains(num))
                System.out.println(i);
            else  lists.add(num);
        }

    }
}
