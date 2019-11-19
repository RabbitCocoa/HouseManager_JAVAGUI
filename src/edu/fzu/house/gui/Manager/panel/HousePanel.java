package edu.fzu.house.gui.Manager.panel;



import com.sorm.po.Comment;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.ColumnInterface;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.Manager.button.CircleButton;
import edu.fzu.house.gui.Manager.table.DataTablePanel;
import edu.fzu.house.gui.Seller.panel.SellerSetting;
import edu.fzu.house.gui.Seller.panel.sellerSettingForOhter;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.TextButton;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.ImageUtil;
import edu.fzu.house.util.MathUtil;
import sun.plugin.javascript.navig.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class HousePanel extends modelPanel {
    private JLayeredPane layer;
    private House house;
    private Hsuser user;
    private Hsuser click_user;
    private JPanel msg;
    private int deg2=20;
    private int deg=10;
    public HousePanel(String hid, JPanel msg,Hsuser click_user)
    {
        this.msg=msg;
        this.click_user=click_user;
        //初始化面板 设置大小
        layer=new JLayeredPane();
        layer.setBounds(0,0,820,750);
        //获取信息
        String sql="select * from house where hid=?";
        List<House> houses=(List<House>)MysqlQuery.query.queryRows(sql,House.class,new Object[]{hid});
        house=houses.get(0);
        String sql2="select * from hsuser where uname=?";
        user=((List<Hsuser>)MysqlQuery.query.queryRows(sql2,Hsuser.class,new Object[]{house.getUname()})).get(0);
        //初始化布局
        //返回按钮
        ImageIcon ret=new ImageIcon("src/image/Icon/Manager/return.png");
        ret=ImageUtil.StretchPngImage(ret,20,20);

        RoundButtonPanel rtrn=new RoundButtonPanel(ret,120,40,"返回",new Color(0x92B3FF),Color.white,12);
        rtrn.setBounds(20,20,120,40);
        rtrn.button.addActionListener(e->{

            msg.setBounds(0,0,820,750);
            layer.add(msg,new Integer(deg2++));
        });

        layer.add(rtrn);

        //房名
        JTextArea fname=new JTextArea(house.getHname());
        fname.setLineWrap(true);
        fname.setFont(new Font("微软雅黑",Font.BOLD,24));
        fname.setColumns(12);
        fname.setBounds(40,70,500,90);
        fname.setOpaque(false);
        fname.setEditable(false);
        layer.add(fname);

        //图片展览
        ShowPictures(0);

        //房名 房东信息
        InitName();


        //购买按钮
        ImageIcon rc=new ImageIcon("src/image/Icon/Manager/buy.png");
        rc=ImageUtil.StretchPngImage(rc,30,30);
        RoundButtonPanel buy_button=new RoundButtonPanel(rc,160,50,"购买",
                new Color(0xFF4645), Color.white,16);
        buy_button.setBounds(580,340,160,50);

        buy_button.button.addActionListener(e->{
            int type=MysqlQuery.query.queryNumber("select utype from huser where uname=?",
                    new Object[]{user.getUname()}).intValue();
            if(type!=0)
                JOptionPane.showMessageDialog(null,"亲，先去注册个买家账号再来噢o(*￣▽￣*)ブ");
        });

        layer.add(buy_button,new Integer(deg++));

        //两个按钮
        //描述区
        ImageIcon ic=new ImageIcon("src/image/Icon/Manager/dec.png");
        ic=ImageUtil.StretchPngImage(ic,20,20);

        RoundButtonPanel describle_button=new RoundButtonPanel(ic,160,50,"房源描述",
                new Color(0xE7FD9F), Color.gray,14);
        describle_button.setBounds(50,420,160,50);
        describle_button.button.addActionListener(e->{
            JPanel panel=new JPanel();

            panel.setBounds(40,465,740,300);
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            String dec;
            if(house.getHdec()!=null)
                dec=house.getHdec();
            else
                dec="暂无描述";
            JTextArea area=new JTextArea(dec);
            area.setPreferredSize(new Dimension(500,300));
            area.setOpaque(false);
            area.setEditable(false);
            area.setFont(new Font(Font.SERIF,Font.BOLD,16));
            area.setLineWrap(true);

            panel.add(area);
            layer.add(panel,new Integer(deg++));
        });
        layer.add(describle_button);


        //信息表
        ic=new ImageIcon("src/image/Icon/Manager/information.png");
        ic=ImageUtil.StretchPngImage(ic,20,20);
        describle_button=new RoundButtonPanel(ic,160,50,"生活环境",
                new Color(0xE7FD9F), Color.gray,14);
        describle_button.setBounds(220,420,160,50);

        describle_button.button.addActionListener(e->{
            DataTablePanel table=new DataTablePanel("select hinformation from house where hid=? ",
                        House.class, new Object[]{house.getHid()}, new String[]{"小区", "高楼", "阳台", "近地铁", "朝南"},
                        5, new ColumnInterface() {
                    public HashMap<Integer, List<Object>> getMap(List<Object> list) {
                        HashMap<Integer, List<Object>> map=new HashMap<>();

                        String formation= ((House)list.get(0)).getHinformation();

                        List<byte[]> rights= IOUtil.readStrImage
                                ("src/image/Icon/Manager/error.png&src/image/Icon/Manager/right.png&");
                        byte[] error=rights.get(0);
                        byte[] right=rights.get(1);

                        List<Object> lists=new ArrayList<>();
                        for (int i=0;i<5;i++) {
                            if(formation.charAt(i)!='0')
                                lists.add(right);
                            else
                                lists.add(error);
                        }
                        map.put(0,lists);
                        return map;
                    }
                },new Rectangle(40,465,740,300),layer);
            JPanel panel=table.generateMsg();

            panel.setBounds(new Rectangle(40,465,740,300));
            layer.add(panel,new Integer(deg++));

        });


        layer.add(describle_button);




        this.add(layer);
    }

    public void InitName()
    {
        JPanel panel=new JPanel();
        panel.setBackground(new Color(0xECECF1));
        panel.setLayout(null);
        panel.setBounds(430,130,360,280);



        //头像
        ImageIcon icon=new ImageIcon(user.getPhoto());
        icon=ImageUtil.CutCircleImage(icon,80);
        JLabel plabel=new JLabel(icon);
        plabel.setBounds(20,90,80,80);
        panel.add(plabel);

        //姓名
        String sname= user.getSname();
        TextButton name=new TextButton(sname,new Rectangle(5,160,sname.length()*30,47));
        name.addActionListener(e->{
            sellerSettingForOhter set = new sellerSettingForOhter(user,click_user);
            set.setBounds(0, 0, 820, 950);
            layer.add(set, new Integer(deg++));
        });
        panel.add(name);


        //五颗星等级
        //获得评价平均值
        JLabel star;
        String sql="select sum(cdegree) from comment where bcid=? and cdegree!=0";
        String sql2="select count(1) from comment where bcid=? and cdegree!=0";
        int sum=MysqlQuery.query.queryNumber(sql,new Object[]{user.getUname()}).intValue();
        int total=MysqlQuery.query.queryNumber(sql2,new Object[]{user.getUname()}).intValue();
        if(total<10){
            star=new JLabel("暂无评分");
            star.setForeground(Color.gray);
            star.setFont(new Font(Font.SERIF,Font.PLAIN,12));
            star.setBounds(30,190,100,20);}
        else{
            ImageIcon stars=ManagerFunc.DrawStar(sum/total);
            star=new JLabel(stars);
            star.setBounds(10,200,100,20);
        }
        panel.add(star);


        //房屋信息
        JPanel house_panel=new JPanel();
        house_panel.setLayout(new GridLayout(6,2));
        house_panel.setBounds(100,0,260,280);
        house_panel.setBackground(new Color(0xECECF1));



        //价格
        int value=house.getHprice();
        StringBuilder apprice=new StringBuilder();
        if(value>10000)
            apprice.append(value/10000+".").append((value-value/10000*10000)+"万");
        else
            apprice.append(value+"元");
        JLabel price=new JLabel("售价");
        price.setFont(new Font(Font.SERIF,Font.BOLD,16));
        price.setHorizontalAlignment(SwingConstants.CENTER);
        house_panel.add(price);

        price=new JLabel(apprice.toString());
        price.setHorizontalAlignment(SwingConstants.LEFT);
        price.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,16));
        price.setForeground(Color.red);
        house_panel.add(price);


        //房型
        String type;
        switch (house.getHtype()) {
        case 1:
            type="一室";
            break;
        case 2:
            type="二室";
            break;
        case 3:
            type="三室";
            break;
        case 4:
            type="四室";
            break;
        case 5:
            type="五室";
            break;
        default:
            type="五室以上";
            break;
    }
        JLabel htype=new JLabel("房型");
        htype.setFont(new Font(Font.SERIF,Font.BOLD,16));
        htype.setHorizontalAlignment(SwingConstants.CENTER);
        house_panel.add(htype);
        htype=new JLabel(type);
        htype.setForeground(Color.gray);
        htype.setHorizontalAlignment(SwingConstants.LEFT);
        htype.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,16));
        house_panel.add(htype);





        //面积

        htype=new JLabel("面积");
        htype.setFont(new Font(Font.SERIF,Font.BOLD,16));
        htype.setHorizontalAlignment(SwingConstants.CENTER);
        house_panel.add(htype);
        htype=new JLabel(""+house.getHarea()+"㎡");
        htype.setForeground(Color.gray);
        htype.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,16));
        house_panel.add(htype);
        //地址
        htype=new JLabel("地址");
        htype.setFont(new Font(Font.SERIF,Font.BOLD,16));
        htype.setHorizontalAlignment(SwingConstants.CENTER);
        house_panel.add(htype);

        JTextArea hare=new JTextArea((ManagerFunc.getAddress2(house.getAddressid())));
        hare.setLineWrap(true);
        hare.setOpaque(false);
        hare.setFont(new Font(Font.DIALOG_INPUT,Font.BOLD,16));
        hare.setForeground(Color.gray);
        house_panel.add(hare);

        panel.add(house_panel);
        layer.add(panel);
    }

    public void ShowPictures(int index)
    {
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBounds(20,130,380,280);
        List<byte[]> pics= ManagerFunc.getImage(house.getHid());
        //默认显示第一张
        ImageIcon icon =new ImageIcon(pics.get(index));
        icon= ImageUtil.StretchPngImage(icon,380,280);
        JLabel pic=new JLabel(icon);
        pic.setBounds(0,0,380,280);


        //切换图片小圆圈 只有一张不显示
        if(pics.size()>1)
        {
            int start=380/2-(pics.size()/2)*20;

            for(int i=0;i<pics.size();i++)
            {
                int width=10;
                int height=10;
                int inde=i;
                if(i==index)
                {
                    width=15;
                    height=15;
                }
                CircleButton cir=new CircleButton(new Color(0x9EAFAE),new Rectangle(
                        start+i*30,260,width,height  )
                );
                cir.button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ShowPictures(inde);
                    }
                });
                panel.add(cir);
            }
        }
        panel.add(pic);
        layer.add(panel,new Integer(deg++));
    }

    public static void main(String[] args) {
        //随机生成评星
        int i=0;
        while(i++<10){
        Comment com=new Comment();
        com.setCname(ManagerFunc.generateSerialNum(20));
        Random d=new Random();
        com.setCdegree(5);
        com.setBcid("admin5");
        MysqlQuery.query.insert(com);
    }}
}
