package edu.fzu.house.gui.Buyer.panel;

import com.sorm.po.House;
import com.sorm.po.Hsuser;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Buyer.BuyerFrame;
import edu.fzu.house.gui.Manager.panel.HousePanel;
import edu.fzu.house.gui.Manager.panel.modelPanel;
import edu.fzu.house.gui.Seller.SellerFrame;
import edu.fzu.house.gui.login.panel.TextButton;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BuyerBrowse extends modelPanel {
    public JLayeredPane layer_panel = new JLayeredPane();
    public String sql = "Select * from house where hinformation like ?  and (hname like ? or hname like ?)  and hstate=1";

    JTextField search_field;
    private int deg = 200;
    private int deg2=100;
    int page = 1;
    int totalPage;
    String[] information = {"0", "0", "0", "0", "0"};
    public Hsuser user;
    String getInformation() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (information[i].equals("0")) {
                builder.append("_");
            } else {
                builder.append(1);
            }
        }
        return builder.toString();
    }

    void init() {

        // layer_panel.setBounds(0,0,820,750);


        /*搜索框背景颜色*/
        Color c = new Color(0xF5F3F4);
        /*搜索按钮*/
        ImageIcon search_icon = new ImageIcon("src/image/Icon/Manager/search.png");
        search_icon = ImageUtil.StretchPngImage(search_icon, 20, 20);
        JButton search_button = new JButton(search_icon);


        search_button.setBounds(570, 20, 30, 30);
        search_button.setBorderPainted(false);
        search_button.setFocusPainted(false);
        search_button.setBackground(c);
        search_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        search_button.addActionListener(e -> {
                generateHouseList();
        });

        layer_panel.add(search_button);


        /*搜索输入框*/
        search_field = new JTextField("房间名");

        search_field.setForeground(new Color(0x696768));
        search_field.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        search_field.setBorder(null);
        search_field.setBackground(c);
        search_field.setBounds(600, 20, 150, 30);
        /*鼠标放入变化效果*/
        search_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        /*失去、获得焦点*/
        search_field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (search_field.getText().equals("房间名")) {
                    search_field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (search_field.getText().equals("")) {
                    search_field.setText("房间名");
                }
            }
        });

        layer_panel.add(search_field);

        /*生成筛选窗口*/
        JPanel label_panel = new JPanel();
        label_panel.setBackground(Color.white);
        label_panel.setBounds(40, 60, 740, 40);
        label_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));

        addButton("小区", 0, label_panel);
        addButton("高楼", 1, label_panel);
        addButton("阳台", 2, label_panel);
        addButton("近地铁", 3, label_panel);
        addButton("朝南", 4, label_panel);
        layer_panel.add(label_panel);

        /*按照页数生成房屋列表*/
        generateHouseList();

        generatePages();

    }

    void generatePages()
    {
        JPanel panel=new JPanel();
        panel.setLayout(new GridLayout(1,10));

        panel.setBounds(40,710,740,30);
        writePage(panel);
        layer_panel.add(panel,new Integer(deg2++));
    }

    /*封装页码*/
    public TextButton getNumButton(int i) {
        TextButton text = new TextButton(i + "", new Rectangle(0, 0, 40, 40));
        text.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        text.setForeground(Color.gray);
        if (page == i)
        {
            text.setForeground(Color.blue);
        }

        text.addActionListener(e->{
            page=Integer.parseInt(text.getText());
            generatePages();
            generateHouseList();
        });
        return text;
    }

    void writePage(JPanel panel)
    {
        //规则   ...规则 当前页数-1>3  总页数-当前页数>8  如9  1...8 9 1
        //先画1
        TextButton text=getNumButton(1);
        panel.add(text);
        //是否画...
        if(page-1<4)
        {
            //画到当前页数 然后判断尾页怎么画
            for(int i=2;i<=page;i++) {
                TextButton text2 = getNumButton(i);
                panel.add(text2);
            }

        }
        else{
            JLabel label=new JLabel("........");
            panel.add(label);

            TextButton text2 = getNumButton(page-1);
            panel.add(text2);
            TextButton text3 = getNumButton(page);
            panel.add(text3);
        }
        boolean end=(totalPage-page<8);

        if(end)
        {
            for(int i=page+1;i<=(int)totalPage;i++)
            {
                TextButton text2 = getNumButton(i);
                panel.add(text2);
            }
        }
        else{
            TextButton text2 = getNumButton(page+1);
            panel.add(text2);
            TextButton text3 = getNumButton(page+2);
            panel.add(text3);
            JLabel label=new JLabel("........");

            panel.add(label);
            TextButton text4 = getNumButton((int)totalPage);
            panel.add(text4);
        }

    }

    public void addButton(String text, int num, JPanel panel) {
        JButton label_button = new JButton(text);
        label_button.setPreferredSize(new Dimension(80, 30));
        label_button.setBackground(Color.white);
        label_button.setBorder(BorderFactory.createEtchedBorder(Color.gray, Color.gray));

        label_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        label_button.addActionListener(e -> {
            if (information[num].equals("0")) {
                information[num] = "1";
                label_button.setBackground(new Color(0xD2F8FF));
            } else {
                information[num] = "0";
                label_button.setBackground(Color.white);
            }
            generateHouseList();
        });

        panel.add(label_button);
    }

    public BuyerBrowse(Hsuser user) {
        layer_panel.setBounds(0, 0, 820, 750);
        this.user=user;
        init();
        add(layer_panel);
    }

    //生成房屋列表
    void generateHouseList() {

        JPanel panel = new JPanel();
        panel.setBounds(40, 100, 740, 610);
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(3, 2, 3, 3));



        String search = search_field.getText();
        if (search.equals("房间名")) {
            search = "";
        }
        /*总页数*/
        totalPage=MysqlQuery.query.queryRows(sql,House.class,new Object[]{getInformation(), "%" + search
                , search + "%"})==null?0:MysqlQuery.query.queryRows(sql,House.class,new Object[]{getInformation(), "%" + search
                , search + "%"}).size();
        totalPage=totalPage%6==0?totalPage/6:totalPage/6+1;
        System.out.println(totalPage);

        List<House> houseList = MysqlQuery.query.queryPagenate(sql, House.class, new Object[]{getInformation(), "%" + search
                , search + "%"}, page, 6);

        if (houseList != null) {
            System.out.println(houseList.size());
            for (House house : houseList) {
                generateAHouse(house, panel);
            }
        }
        layer_panel.add(panel, new Integer(deg++));


    }






    /*  生成一栏房屋列表*/
    void generateAHouse(House house, JPanel panel) {
        JPanel child_panel = new JPanel();
        child_panel.setBackground(new Color(0xF5F3F4));;
        child_panel.setLayout(null);

        //   图片
        ImageIcon icon = new ImageIcon(IOUtil.readStrImage(house.getPhoto()).get(0));
        icon = ImageUtil.StretchPngImage(icon, 160, 180);
        JLabel label = new JLabel(icon);
        label.setBounds(30, 10, 160, 180);
        child_panel.add(label);

        //  房间名 点击进入
        TextButton enter = new TextButton(house.getHname(), new Rectangle(195, 10, 160, 40));
        enter.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        child_panel.add(enter);
        enter.setHorizontalAlignment(SwingConstants.LEFT);
        enter.addActionListener(e->{
            BuyerBrowse bs=new BuyerBrowse(user);
            HousePanel hs=new HousePanel(house.getHid(), bs, user);
            layer_panel.add(hs,new Integer(800));
        });

        //  房主名和等级
        String sname = (String) MysqlQuery.query.queryValue("select sname from hsuser where uname=?",
                new Object[]{house.getUname()});

        JLabel label1 = new JLabel(sname);
        label1.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        label1.setForeground(Color.gray);
        label1.setBounds(215, 50, 200, 40);
        label1.setHorizontalAlignment(SwingConstants.LEFT);
        child_panel.add(label1);

        //    画星星
        ImageIcon starIcon = ManagerFunc.DrawStar(5);
        JLabel startlabel = new JLabel(starIcon);
        startlabel.setBounds(160, 80, 200, 40);
        child_panel.add(startlabel);

        //  文本描述
        JTextArea area = new JTextArea(house.getHdec());
        area.setEditable(false);
        area.setLineWrap(true);
        area.setOpaque(false);
        area.setBounds(210, 120, 200, 80);
        child_panel.add(area);

        //地址
        String address = ManagerFunc.getAddress2(house.getAddressid());
        JLabel addresslabel = new JLabel(address);
        addresslabel.setBounds(210, 140, 200, 40);
        addresslabel.setFont(new Font(Font.SERIF, Font.PLAIN,12));
        addresslabel.setForeground(Color.gray);
        child_panel.add(addresslabel);

        panel.add(child_panel);

    }



    public static void main(String[] args) {
    /*    int i=0;
        while(i<20){
        House house=new House();
        house.setHstate(1);
        house.setHid(ManagerFunc.generateSerialNum(8));
        house.setHdec("暂时没有描述信息");
        house.setAddressid(1);
        house.setPhoto("D:\\图标图片库\\博客素材\\图片\\rabbit7.jpg&D:\\图标图片库\\cured.jpg&");
        house.setHprice(55555);
        house.setHname("秘密娃娃饿");
        house.setHtype(4);
        house.setHarea(56);
        house.setHinformation("11111");
        house.setUname("admin5");
        MysqlQuery.query.insert(house);
        i++;
        }}*/
    }
}
