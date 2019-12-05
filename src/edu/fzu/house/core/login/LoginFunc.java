package edu.fzu.house.core.login;

import com.sorm.core.Query;
import com.sorm.po.Hsuser;
import com.sorm.po.Huser;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LoginFunc {
    public static String pwddes = "src/edu/fzu/house/log/rem.log";
    public static String unames = "src/edu/fzu/house/log/unames.log";

    public static String uname; //记住的账号
    public static String pwd;  //记住的密码
    public static List<String> names; //匹配的字符集

    //登陆验证功能 返回type类型
    public static int Login(String name, String password) {
        String sql = "select utype from huser where uname=? and password=? ";
        Object type = MysqlQuery.query.queryValue(sql, new Object[]{name, password});
        int count = (type == null) ? -1 : (int) type;
        return count;
    }

    //记住密码生成文件功能
    public static void RemberPassword(String name, String password, boolean isRem) {
        /*生成保留登陆过的账号文件*/
        try {
            if (!names.contains(name)) {
                FileWriter writer = new FileWriter(new File(unames));
                BufferedWriter bw = new BufferedWriter(writer);
                bw.write(name);
                bw.newLine();
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* 生成记住密码文件*/
        try {
            FileWriter writer = new FileWriter(new File(pwddes));
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(name);
            if (isRem) {
                bw.newLine();
                bw.write(password);
            }
            bw.flush();
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //登陆界面自动设置数据功能
    public static void ReadPassword() {
        names = new ArrayList<>();
        //读完names
        try {
            File file = new File(unames);
            if (!file.exists())
                file.createNewFile();
            FileReader read = new FileReader(unames);
            BufferedReader br = new BufferedReader(read);
            String msg;
            while ((msg = br.readLine()) != null && !msg.equals("")) {
                names.add(msg);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File file = new File(pwddes);
            if (!file.exists())
                return;
            FileReader read = new FileReader(pwddes);
            BufferedReader br = new BufferedReader(read);
            String msg;
            if ((msg = br.readLine()) != null && !msg.equals("")) {
                uname = msg;
            }
            if ((msg = br.readLine()) != null && !msg.equals("")) {
                pwd = msg;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //注册功能
    public static int Register(String name, String pwd, String rpwd, int type) {
        if (pwd.equals(rpwd)) {
            Huser user = new Huser();

            user.setUname(name);
            user.setPassword(pwd);
            user.setUtype(type);

            int count = MysqlQuery.query.insert(user);
            if (count == 1) {
                Hsuser suser = new Hsuser();
                suser.setUname(user.getUname());
                suser.setLogintime(new Timestamp(System.currentTimeMillis()));
                suser.setPhoto(IOUtil.readStrImage("src/image/bkimage/default.jpg&").get(0));
                suser.setSurplus(0);
                suser.setSemail("<未设置>");
                suser.setSphone("<未设置>");
                suser.setSsex("<未设置>");
                suser.setSname("<未设置>");
                suser.setSurplus(10000000);
                count = MysqlQuery.query.insert(suser);
            }
            return count;
        } else {
            JOptionPane.showMessageDialog(null, "请确认两次密码是否一致");
            return 0;
        }
    }

    //读取数据库中的二进制图片
    public static byte[] readImage(String uname) {

        String sql = "select photo from hsuser where uname=? ";

        return (byte[]) MysqlQuery.query.queryValue(sql, new Object[]{uname});

    }

    public static void main(String[] args) {
      /*  try {

            ImageIcon icon=readImage("xg108575");
            JLabel label=new JLabel(icon);
            JFrame frame=new JFrame("1");
            frame.setSize(1000,800);
            frame.add(label, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
