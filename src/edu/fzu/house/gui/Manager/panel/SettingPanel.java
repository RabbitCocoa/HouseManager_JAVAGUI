package edu.fzu.house.gui.Manager.panel;

import com.sorm.core.Query;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.util.ReflectionUtil;
import com.sun.deploy.panel.GeneralPanel;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.ColumnInterface;
import edu.fzu.house.core.columnInterface.HouseColumImpl;
import edu.fzu.house.core.confirmInterface.*;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.TextButton;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.ImageUtil;


import javax.jws.WebService;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingPanel extends modelPanel {
    public  String Paramsql =  "select hid,hname,htype,photo,addressid,hprice,uname from house where hstate=? order by hprice";

    public JLayeredPane layer_panel=new JLayeredPane();

    public static  Color color1=new Color(0xC9DEFB); //单行文本背景色
    public static  Color color2=new Color(246,250,255);  //双行文本背景
    public static Color pagecolor=new Color(0xFFFFFF); //背景颜色

    //生成一栏个人信息项
    public JPanel generateSetting(String text, String fieldName, Color c, Color font, Confirm con) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(500, 40);
        panel.setBackground(c);
        panel.setBorder(BorderFactory.createLineBorder(Color.gray));
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        label.setForeground(font);
        label.setBounds(30, -10, 100, 60);
        panel.add(label);

        //输入框 默认不可编辑 点击修改后可编辑
        JTextField textField = new JTextField();
        textField.setBounds(150, 5, 250, 30);
        textField.setOpaque(false); //透明
        textField.setBorder(null);  //无边框
        textField.setEditable(false);//不可编辑
        String body = (String) ReflectionUtil.invokeGet(fieldName, ManagerFrame.user);
        if (body == null || body.equals(""))
            body = "(未设置)";
        textField.setText(body);
        //设置事件 失去焦点后修改并更新数据库
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!ManagerFunc.confirm(textField.getText(), con)) {
                    textField.requestFocus();
                    return;
                }
                ReflectionUtil.invokeSet(fieldName, ManagerFrame.user, textField.getText());
                MysqlQuery.query.update(ManagerFrame.user, new String[]{fieldName});
                textField.setEditable(false);//文本框不可编辑
                if (fieldName.equals("sname")) {
                    ManagerFrame.id_label.setText(textField.getText());
                    ManagerFrame.name_label.setText(textField.getText());
                }
            }
        });
        panel.add(textField);
        //修改按钮
        TextButton modify = new TextButton("修改", new Rectangle(420, 0, 70, 40));
        modify.addActionListener(e -> {
            textField.setEditable(true);
            textField.requestFocus();
        });
        panel.add(modify);

        //确认按钮
        TextButton confirm = new TextButton("确认", new Rectangle(380, 0, 70, 40));
        panel.add(confirm);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    confirm.requestFocus();
            }
        });
        return panel;
    }

    public SettingPanel() {

        super();
        layer_panel.setBounds(0,0,820,750);

     //   layer_panel.setBackground(Color.pink);
        JPanel bk=new JPanel();
        bk.setBounds(0,0,820,750);
        bk.setBackground(pagecolor);
        layer_panel.add(bk,JLayeredPane.DEFAULT_LAYER);
        //个人信息标签
        JLabel msg1 = new JLabel("个人信息");
        msg1.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        msg1.setBounds(40, 23, 80, 80);
        layer_panel.add(msg1,new Integer(100));

        //开始创造用户信息表格 自动实现了修改 确定功能
        Color c= new Color(0xD5F3F5);
        JPanel panel = generateSetting("用户名:", "sname", c,  new Color(0x000000), null);
        panel.setBounds(40, 80, 500, 40);
        layer_panel.add(panel,new Integer(100));
        panel = generateSetting("性别:", "ssex",c, new Color(0x000000), new SexConfirm());
        panel.setBounds(40, 120, 500, 40);
        layer_panel.add(panel,new Integer(100));
        panel = generateSetting("邮箱:", "semail", c, new Color(0x000000), new EmialConfirm());
        panel.setBounds(40, 160, 500, 40);
        layer_panel.add(panel,new Integer(100));
        panel = generateSetting("身份证号:", "scardid",c, new Color(0x000000), new IDConfirm());
        panel.setBounds(40, 200, 500, 40);
        layer_panel. add(panel,new Integer(100));
        panel = generateSetting("手机号:", "sphone",c, new Color(0x000000), new PhoneConfirm());
        panel.setBounds(40, 240, 500, 40);
        layer_panel.add(panel,new Integer(100));

        //头像框
        JPanel photo = new JPanel();
        photo.setLayout(null);
        photo.setSize(200, 40);
        photo.setBackground(c);
        photo.setBorder(BorderFactory.createLineBorder(Color.gray));
        photo.setBounds(540, 80, 200, 200);
        //头像
        ImageIcon image = new ImageIcon(ManagerFrame.user.getPhoto());
        image = ImageUtil.CutCircleImage(image, 100);
        JLabel label = new JLabel(image);
        label.setBounds(40, 20, 100, 100);
        photo.add(label);

        //修改头像按钮
        TextButton photo_button = new TextButton("修改头像", new Rectangle(30, 120, 120, 40));
        photo_button.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        photo_button.setForeground(Color.gray);
        photo.add(photo_button);
        //功能
        photo_button.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg/png/jpeg", "jpg", "png", "jpeg");
            fileChooser.setFileFilter(filter);
            fileChooser.showOpenDialog(null);

            File file = fileChooser.getSelectedFile();
            BufferedInputStream bis = null;
            ByteArrayOutputStream bos = null;
            if (file == null)
                return;
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                int len = -1;
                bos = new ByteArrayOutputStream();
                byte[] dates = new byte[1024 * 1024];
                while ((len = bis.read(dates)) != -1) {
                    bos.write(dates, 0, len);
                }
                bos.flush();
                ManagerFrame.user.setPhoto(bos.toByteArray());
                int count = MysqlQuery.query.update(ManagerFrame.user, new String[]{"photo"});
                if (count == 1) {
                    ImageIcon photo1 = new ImageIcon(ManagerFrame.user.getPhoto());
                    photo1 = ImageUtil.CutCircleImage(photo1, 40);
                    ManagerFrame.photo_label.setIcon(photo1);

                    ImageIcon photo2 = new ImageIcon(ManagerFrame.user.getPhoto());
                    photo2 = ImageUtil.CutCircleImage(photo2, 100);
                    ManagerFrame.photo_label2.setIcon(photo2);

                    ImageIcon photo3 = new ImageIcon(ManagerFrame.user.getPhoto());
                    photo3 = ImageUtil.CutCircleImage(photo3, 100);
                    label.setIcon(photo3);
                } else {
                    System.out.println("文件过大");
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                IOUtil.Close(bos, bis);
            }

        });
        // photo.setBounds();
        layer_panel.add(photo,new Integer(100));

        //*消息列表*//*
        JLabel msg2 = new JLabel("审核信息");
        msg2.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        msg2.setBounds(40, 260, 80, 80);
        layer_panel. add(msg2,new Integer(100));

   /*     List<Integer>  imageID=new ArrayList<>();
        imageID.add(new Integer(3));
        JPanel msgPanel=generateMsg(7,new String[]{"序列号","房名","房型","照片","地址","售价","房主"},
                 new HouseColumImpl(),740,200,color1,
                color2,imageID,13,apage);
        msgPanel.setBounds(40,320,740,392);
        layer_panel.add(msgPanel,new Integer(100));*/

    //生成数据库表格

        DataTablePanel datetable=new DataTablePanel(Paramsql,House.class ,new Object[]{0},
                new String[]{"序列号","房名","房型","照片","地址","售价","房主"},7, new HouseColumImpl(),
                new Rectangle(10,320,800,400 ),layer_panel);
        datetable.setColor1(color1);
        datetable.setColor2(color2);
        datetable.setPagecolor(pagecolor);
        datetable.setImageID(3);
        datetable.setSize(8);
        datetable.generateTable();
//        JPanel msgPanel=datetable.generateMsg();
//        layer_panel.add(msgPanel,new Integer(100));


        //转页

        add(layer_panel);

    }

    public static void main(String[] args) {
/*      for(int i=0;i<1000;i++)
        {
            House house=new House();
            house.setHid(ManagerFunc.generateSerialNum(8));
            house.setHname("火山居"+i);
            house.setHtype((int)(Math.random()*5+1));
            house.setHarea((int)(Math.random()*451+50));
            //Clob clob=new com.mysql.cj.jdbc.Clob("src/image/Icon/Manager/msg.png&src/image/Icon/Manager/user.png&",null);
            house.setHinformation(ManagerFunc.generateSerialNum(8));
            house.setHstate(0);
            house.setPhoto("src/image/house/house1.jpg&src/image/house/test.jpg&");
            house.setAddressid((int)(Math.random()*3+1));
            house.setHprice((int)(Math.random()*9999-1000+1000));
            house.setUname("admin");
            MysqlQuery.query.insert(house);
        }*/
    }
}
