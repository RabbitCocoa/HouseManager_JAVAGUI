package edu.fzu.house.core.setter;

import com.sorm.po.Haddress;
import com.sorm.po.House;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.MysqlQuery;

public class SetterFunc {
    public static int Register(String uname,String hname,int htype,String photo,int harea,
                               String hdedc,int hprice,String hinfomation,int addressid)
    {
        House house=new House();

        house.setUname(uname);
        house.setHinformation(hinfomation);
        house.setHarea(harea);
        house.setHstate(0);
        house.setHtype(htype);
        house.setHid(ManagerFunc.generateSerialNum(8));
        house.setPhoto(photo);
        house.setHname(hname);
        house.setAddressid(addressid);
        house.setHprice(hprice);
        house.setHdec(hdedc);

        int count= MysqlQuery.query.insert(house);
        return count;
    }
}
