package edu.fzu.house.gui.Manager.panel;



import com.sorm.po.Comment;
import com.sorm.po.House;
import com.sorm.po.Hsuser;
import com.sorm.po.Orderform;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.ColumnInterface;
import edu.fzu.house.core.login.LoginFunc;
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
import jdk.nashorn.internal.scripts.JO;
import sun.plugin.javascript.navig.Image;
import sun.rmi.log.ReliableLog;

import javax.swing.*;
import javax.xml.crypto.dsig.keyinfo.KeyName;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class HousePanel extends modelPanel {
    private JLayeredPane layer;
    private House house;
    private Hsuser user;
    private Hsuser click_user;
    private JPanel msg;
    private int deg2=20;
    private int deg=10;
    private int star=5;
    private int page=1;
    private int totalPage;
    private int curent_num;
    private    JLayeredPane layeredPane;

    /*封装页码*/
    public TextButton getNumButton(int i, int w)
    {
        TextButton text = new TextButton(i + "", new Rectangle(20 * w, 0, 40, 40));
        text.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        text.setForeground(Color.gray);
        if(page==i)
        {
            text.setForeground(Color.blue);
        }

        text.addActionListener(e->{
            page=Integer.parseInt(text.getText());
            generComment();
        });
        return text;
    }

    public JPanel generatePage() {
        JPanel panel = new JPanel();
        panel.setSize(770,40);
        String sumsql = "select count(hid) from house where hstate=?";

        int start=page-1;
        //规则   ...规则 当前页数-1>3  总页数-当前页数>8  如9  1...8 9 1
        int w=0;
        //先画1
        TextButton text=getNumButton(1,w++);
        panel.add(text);
        //是否画...
        if(page-1<4)
        {
            //画到当前页数 然后判断尾页怎么画
            for(int i=2;i<=page;i++) {
                TextButton text2 = getNumButton(i, w++);
                panel.add(text2);
            }

        }
        else{
            JLabel label=new JLabel("........");
            label.setBounds(20*(w++),0,20,40);
            panel.add(label);
            TextButton text2 = getNumButton(page-1, w++);
            panel.add(text2);
            TextButton text3 = getNumButton(page, w++);
            panel.add(text3);
        }
        boolean end=(totalPage-page<8);

        if(end)
        {
            for(int i=page+1;i<=(int)totalPage;i++)
            {
                TextButton text2 = getNumButton(i, w++);
                panel.add(text2);
            }
        }
        else{
            TextButton text2 = getNumButton(page+1, w++);
            panel.add(text2);
            TextButton text3 = getNumButton(page+2, w++);
            panel.add(text3);
            JLabel label=new JLabel("........");
            label.setBounds(20,0,20*(w++),40);
            panel.add(label);
            TextButton text4 = getNumButton((int)totalPage, w++);
            panel.add(text4);
        }

        return panel;
    }




    void generStar(int num, Rectangle r,JLayeredPane panel) {
        JPanel im_panel=new JPanel();
        im_panel.setBounds(r);

        ImageIcon star_icon = ManagerFunc.DrawStar(num);
        JLabel im_button = new JLabel(star_icon);


        im_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                star=e.getX()/21+1;
                generStar(e.getX()/21+1,r,panel);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });

        im_panel.add(im_button);
        panel.add(im_panel,deg++);

    }

    void generCommentPanel(JLayeredPane layer_panel)
    {
        JPanel panel=new JPanel();
        panel.setLayout(null);
        panel.setBounds(0,160,720,260*curent_num-200);

        //查询信息，评论者头像，评论内容，评论时间

        //查询所有被评论者是user的comment
        String sql="select * from comment where bcid=? and cdegree>0 order by ccreatetime desc";
        String csql="select count(1) from comment where bcid=? and  cdegree>0 ";
        int total= MysqlQuery.query.queryNumber(csql,new Object[]{user.getUname()}).intValue();
        totalPage=total%4==0?total/4:total/4+1;

        List<Comment> comments=MysqlQuery.query.queryPagenate(sql,Comment.class,
                new Object[]{user.getUname()},page,4);

        curent_num=comments==null?0:comments.size();
        //将所有信息封装进来
        Map<Integer,Map<String,Object>> mmaps=new HashMap<>();
        int i=0;
        if(comments!=null) {
            for (Comment comment : comments) {
                Map<String, Object> maps = new HashMap<>();
                //存评论内容 评论时间
                maps.put("text", comment.getCtext());
                maps.put("time", comment.getCcreatetime());
                //获取头像信息
                byte[] photo = LoginFunc.readImage(comment.getCid());
                maps.put("image", photo);
                //姓名
                sql = "select sname from hsuser where uname=?";
                String name = (String) MysqlQuery.query.queryValue(sql, new Object[]{comment.getCid()});
                maps.put("name", name);

                mmaps.put(i++, maps);

            }

            //开始绘制
            for (i = 0; i < mmaps.size(); i++) {
                Map<String, Object> map = mmaps.get(i);

                //添加头像
                ImageIcon icon = new ImageIcon((byte[]) map.get("image"));
                icon = ImageUtil.CutCircleImage(icon, 80);
                JLabel image_label = new JLabel(icon);
                image_label.setBounds(10, i * 200 + 3, 80, 80);
                panel.add(image_label);

                //添加姓名
                JLabel name = new JLabel((String) map.get("name"));
                name.setBounds(25, i * 200 + 70, 100, 40);
                panel.add(name);

                //添加事件
                Timestamp time = (Timestamp) map.get("time");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String now_time = simpleDateFormat.format(time);
                JLabel label_time = new JLabel(now_time);
                label_time.setForeground(Color.gray);
                label_time.setBounds(15, i * 200 + 90, 100, 40);
                panel.add(label_time);

                //添加文本
                JTextArea textArea = new JTextArea((String) map.get("text"));
                textArea.setBorder(BorderFactory.createLineBorder(Color.black));
                textArea.setBounds(120, i * 200 + 10, 500, 180);
                textArea.setEditable(false);
                textArea.setOpaque(false);
                panel.add(textArea);

            }
        }

        layer_panel.add(panel,deg++);
    }
    void generPage(JLayeredPane layer_panel)
    {
        JPanel panel=new JPanel();

        panel.setBounds(0,curent_num*260-40,770,40);
        JPanel pagepanel=generatePage();

        panel.add(pagepanel);
        layer_panel.add(panel,deg++);
    }

    void generComment()
    {
        String cusql="select * from comment where bcid=? and cdegree>0 ";
        List<Comment> comments=MysqlQuery.query.queryPagenate(cusql,Comment.class,
                new Object[]{user.getUname()},page,4);


        curent_num=comments==null?0:comments.size();

        layeredPane=new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(740,curent_num*260));

        //发表评论区
        //头像
        String p_sql = "select photo from hsuser where uname=?";
        ImageIcon icon = new ImageIcon((byte[]) MysqlQuery.query.queryValue(p_sql, new Object[]{click_user.getUname()}));
        icon = ImageUtil.CutCircleImage(icon, 80);

        //设置头像
        JLabel head = new JLabel(icon);
        head.setBounds(10, 20, 100, 100);
        layeredPane.add(head);

        //文本区域
        JTextArea comment_area=new JTextArea();
        comment_area.setBorder(BorderFactory.createLineBorder(new Color(0xB1ECFF)));
        comment_area.setBounds(110,30,480,100);
        comment_area.setLineWrap(true);
        layeredPane.add(comment_area);


        //发表评论按钮
        JButton buttonPanel=new JButton("评论");
        buttonPanel.setBounds(620,30,80,60);
        buttonPanel.setFont(new Font(Font.SERIF,Font.PLAIN,16));
        layeredPane.add(buttonPanel);
        buttonPanel.addActionListener(ee->{
            if(comment_area.getText().length()<6)
            {
                JOptionPane.showMessageDialog(null,"内容过少");
                return;
            }

            Comment comment=new Comment();
            comment.setCdegree(star);
            comment.setCid(click_user.getUname());
            comment.setBcid(user.getUname());
            comment.setCcreatetime(new Timestamp(System.currentTimeMillis()));
            comment.setCname(ManagerFunc.generateSerialNum(20));
            comment.setCtext(comment_area.getText());
            int count=MysqlQuery.query.insert(comment);
            if(count==1)
            {
                JOptionPane.showMessageDialog(null,"评论成功！");
                generComment();
            }
        });


        //星级
        generStar(5,new Rectangle(610,100,100,40),layeredPane);


        //生成评论列表
        generCommentPanel(layeredPane);


        //生成换页栏
        generPage(layeredPane);

        JScrollPane scrollPane=new JScrollPane(layeredPane);
        scrollPane.setBounds(new Rectangle(40,465,740,300));


        layer.add(scrollPane,new Integer(deg++));
    }

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
                    new Object[]{click_user.getUname()}).intValue();
            if(type!=0)
            {
                JOptionPane.showMessageDialog(null,"亲，先去注册个买家账号再来噢o(*￣▽￣*)ブ");
                return;
            }
            else{
                if(click_user.getSurplus()<house.getHprice())
                {
                    JOptionPane.showMessageDialog(null,"亲,你的余额不足哦");
                    return ;
                }
                int count=JOptionPane.showConfirmDialog(null,"确认购买吗","确认交易",JOptionPane.YES_NO_OPTION);
                if (count == 1) {
                    return;
                } else {
                   //给买家扣钱
                    click_user.setSurplus(click_user.getSurplus()-house.getHprice());
                    MysqlQuery.query.update(click_user,new String[]{"surplus"});
                    //生成订单
                    Orderform orderform=new Orderform();
                    orderform.setOid(ManagerFunc.generateSerialNum(8));
                    orderform.setOstate(1);
                    orderform.setCreatetime(new Timestamp(System.currentTimeMillis()));
                    orderform.setHname(house.getHname());
                    orderform.setHprice(house.getHprice());
                    orderform.setSid(user.getUname());
                    orderform.setBid(click_user.getUname());
                    orderform.setPhoto(house.getPhoto());
                    orderform.setHid(house.getHid());
                    MysqlQuery.query.insert(orderform);

                    //改变房子状态
                    house.setHstate(2);
                    MysqlQuery.query.update(house,new String[]{"hstate"});

                    /*发送消息提醒*/
                    Comment comment=new Comment();
                    comment.setCid("admin");
                    comment.setCdegree(0);
                    comment.setBcid(user.getUname());
                    comment.setCtitle("系统通知");
                    comment.setCtext("亲爱的"+user.getSname()+",你有一笔新的订单，请及时处理。");
                    comment.setCcreatetime(new Timestamp(System.currentTimeMillis()));
                    comment.setCname(ManagerFunc.generateSerialNum(8));
                    MysqlQuery.query.insert(comment);

                    JOptionPane.showMessageDialog(null,"购买成功，请等待买家确认。");
                }
        }});

        layer.add(buy_button,new Integer(deg++));

        //两个按钮
        //描述区
        ImageIcon ic=new ImageIcon("src/image/Icon/Manager/dec.png");
        ic=ImageUtil.StretchPngImage(ic,20,20);

        RoundButtonPanel describle_button=new RoundButtonPanel(ic,160,50,"房源描述",
                new Color(0x47CCFF), Color.gray,14);
        describle_button.setBounds(50,420,160,50);
        describle_button.button.addActionListener(ee->{
            JPanel panel=new JPanel();
            panel.setBounds(40,465,740,300);
            panel.setBorder(BorderFactory.createLineBorder(Color.black));
            String dec;
            if(house.getHdec()!=null) {
                dec = house.getHdec();
            }
            else
            {
                dec="暂无描述";
            }
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
        ic=ImageUtil.StretchPngImage(ic,30,30);
        describle_button=new RoundButtonPanel(ic,160,50,"生活环境",
                new Color(0x47CCFF), Color.gray,14);
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
                            {
                                lists.add(right);
                            }
                            else
                            {
                                lists.add(error);
                            }
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
        //卖家评论

        ic=new ImageIcon("src/image/Icon/seller/comment.png");
        ic=ImageUtil.StretchPngImage(ic,30,30);
        describle_button=new RoundButtonPanel(ic,160,50,"房主评论",
                new Color(0x47CCFF), Color.gray,14);
        describle_button.setBounds(390,420,160,50);
        describle_button.button.addActionListener(e->{
            generComment();
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
            star.setBounds(40,190,100,20);}
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
