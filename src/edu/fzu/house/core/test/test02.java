package edu.fzu.house.core.test;

import com.sorm.po.Comment;
import com.sorm.po.Hsuser;
import com.sorm.po.Huser;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.LoginFunc;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.util.IOUtil;

import java.sql.Timestamp;

//测试时间
public class test02 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Huser user = new Huser();
            user.setUtype(1);
            user.setUname(ManagerFunc.generateSerialNum(8));
            user.setPassword("123");

            Hsuser huser = new Hsuser();
            huser.setUname(user.getUname());
            huser.setSname(ManagerFunc.generateSerialNum(3));
            huser.setPhoto(IOUtil.readStrImage("src/image/Icon/Manager/user.png&").get(0));
            huser.setLogintime(new Timestamp(System.currentTimeMillis()));
            huser.setScardid(ManagerFunc.generateSerialNum(18));
            if (i % 2 == 0)
                huser.setSsex("男");
            else
                huser.setSsex("女");
            huser.setSphone(ManagerFunc.generateSerialNum(12));
            huser.setSemail(ManagerFunc.generateSerialNum(8) + "@qq.com");

            MysqlQuery.query.insert(user);
            MysqlQuery.query.insert(huser);
        }

    }
}
