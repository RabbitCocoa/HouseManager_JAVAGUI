package edu.fzu.house.gui.Buyer;

import com.sorm.po.Hsuser;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.gui.Buyer.panel.BuyerBrowse;
import edu.fzu.house.gui.Buyer.panel.BuyerDealing;
import edu.fzu.house.gui.Buyer.panel.BuyerSetting;

import edu.fzu.house.gui.Seller.SellerFrame;
import edu.fzu.house.gui.Seller.panel.SellerDealing;
import edu.fzu.house.gui.Seller.panel.sellerHouse;
import edu.fzu.house.gui.Seller.panel.sellerNotice;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuyerFrame extends JFrame {
    private  JLayeredPane layer_panel = new JLayeredPane();
    public static Color bkcolor = new Color(133, 153, 190);
    /*显示信息*/
    public static JLabel id_label;
    public static JLabel name_label;
    public static JLabel photo_label;
    public static JLabel photo_label2;
    /*个人信息*/
    public static Hsuser user;
    private String date;
    public static int deg = 2;


    public RoundButtonPanel addButton(int x, int y, String src, Color bk, Color font, JComponent panel, String text) {

        ImageIcon setting = new ImageIcon(src);
        if (setting != null)
            setting = ImageUtil.StretchPngImage(setting, 15, 15);
        RoundButtonPanel set_button = new RoundButtonPanel(setting, 180, 50, text, bk, font, 14);
        set_button.setBounds(x, y, 180, 40);
        set_button.setBackground(new Color(39, 46, 50));
        panel.add(set_button);
        return set_button;
    }

    //初始化左界面
    public void InitLeft() {
        JPanel left = new JPanel();
        left.setLayout(null);
        /*宽度和背景颜色*/
        left.setBounds(0, 0, 180, 800);
        left.setBackground(new Color(39, 46, 50));

        /*头像部分*/
        ImageIcon photo = new ImageIcon(user.getPhoto());
        photo = ImageUtil.CutCircleImage(photo, 100);
        photo_label2 = new JLabel(photo);
        photo_label2.setBounds(40, 20, 100, 100);
        left.add(photo_label2);

        /*用户名*/
        String id = user.getSname();
        if (id == null || id.equals(""))
            id = "(未设置)";
        id_label = new JLabel(id);
        id_label.setBounds(65, 120, 100, 40);
        id_label.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        id_label.setForeground(Color.white);


        /*个人信息按钮*/
        RoundButtonPanel setting = addButton(0, 180, "src/image/Icon/Manager/setting.png", (new Color(39, 46, 50)), Color.white, left, "个人信息");
        setting.button.addActionListener(e -> {
            BuyerSetting set = new BuyerSetting(user);
            set.setBounds(180, 50, 820, 950);
            layer_panel.add(set, new Integer(deg++));
        });

        /*房屋信息按钮*/
        RoundButtonPanel user = addButton(0, 240, "src/image/Icon/Manager/user.png", (new Color(39, 46, 50)), Color.white, left, "房屋查询");
        user.button.addActionListener(e -> {
            BuyerBrowse   set = new BuyerBrowse(BuyerFrame.user);
            set.setBounds(180, 50, 820, 950);
            layer_panel.add(set, new Integer(deg++));
        });

        /*订单查询*/
        RoundButtonPanel examine = addButton(0, 300, "src/image/Icon/Manager/msg.png", (new Color(39, 46, 50)), Color.white, left, "交易列表");
        examine.button.addActionListener(e -> {
           BuyerDealing set = new BuyerDealing(BuyerFrame.user);
            set.setBounds(180, 50, 820, 950);
            layer_panel.add(set, new Integer(deg++));
        });

        /*消息*/
        RoundButtonPanel notice = addButton(0, 360, "src/image/Icon/Manager/notice.png", (new Color(39, 46, 50)), Color.white, left, "消息列表");
        notice.button.addActionListener(e -> {
            sellerNotice set = new sellerNotice(BuyerFrame.user);
            set.setBounds(180, 50, 820, 950);
            layer_panel.add(set, new Integer(deg++));
        });

        left.add(id_label);
        layer_panel.add(left);
    }

    public void Init() {
        InitLeft();
    }

    public BuyerFrame(String uname) {
        setSize(1000, 800);
        FrameUtil.Setting(this, layer_panel, 900, bkcolor);
        /*初始化个人信息*/
        user = ManagerFunc.getHsuer(uname);
        /*初始化当前时间*/
        date = new SimpleDateFormat("yyyy-MM-dd-EEEE").format(new Date(System.currentTimeMillis()));
        /*顶部区域*

        /*时间*/
        JLabel time = new JLabel(date);
        time.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        time.setForeground(new Color(0x13192C));
        time.setBounds(200, 5, 150, 40);
        layer_panel.add(time);
        /*头像部分*/
        ImageIcon photo = new ImageIcon(user.getPhoto());
        photo = ImageUtil.CutCircleImage(photo, 40);
        photo_label = new JLabel(photo);
        photo_label.setBounds(700, 5, 40, 40);
        layer_panel.add(photo_label);
        /*名字部分*/
        String name;
        name = user.getSname();
        if (name == null || name.equals(""))
        {
            name = "(未设置)";
        }

        name_label = new JLabel(name);
        name_label.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        name_label.setForeground(new Color(0x13192C));
        name_label.setBounds(750, 0, 200, 50);
        layer_panel.add(name_label);

        Init();
        this.add(layer_panel);

        BuyerSetting set = new BuyerSetting(user);
        set.setBounds(180, 50, 820, 950);
        layer_panel.add(set);

        setVisible(true);
    }


    public static void main(String[] args) {
        BuyerFrame mgf = new BuyerFrame("buy1");
    }
}
