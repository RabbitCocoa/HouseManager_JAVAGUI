package edu.fzu.house.gui.Manager.table;

import com.sorm.bean.ColumnInfo;
import com.sorm.bean.TableInfo;
import com.sorm.core.TableContext;
import com.sorm.po.House;
import edu.fzu.house.core.ActionInterfaces.ActionInterface;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.columnInterface.ColumnInterface;
import edu.fzu.house.core.columnInterface.HouseColumImpl;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.login.panel.TextButton;
import edu.fzu.house.util.ImageUtil;
import edu.fzu.house.util.MathUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataTablePanel {

    private String sql;
    private Class  clz;
    public static int num=301; //用来配合覆盖翻页
    public static int num2=201;
    private Object[] params;
    private String[] titles;
    private int column;
    private ColumnInterface co;
    private int width;
    private int height;
    private Color color1;//单行文本颜色
    private Color color2;//双行文本颜色
    private Color pagecolor; //换页栏颜色
    private List<Integer> imageID;
    private boolean desc=true;
    private int page;   //页数 默认为1
    private int size; //每页大小 默认为12
    private int pageHeight=40; //换页高度
    private int totalPage; //总页数 默认为1 自动计算
    private Rectangle positionsize; //绝对定位换页覆盖自己
    private JLayeredPane comment; //父窗口 换页要用

    private HashMap<String,ActionInterface> accdent;


    public   void addMap(String text, ActionInterface action){
        //先增加宽度 再重置宽度
        this.width+=width/(column+accdent.size());
        accdent.put(text,action);

        this.width=MathUtil.getDetail(this.width,(column+accdent.size()));
        this.positionsize.width=width;
    }

    public void setPositionsize(Rectangle positionsize) {
        this.positionsize = positionsize;
    }

    public void setComment(JLayeredPane comment) {
        this.comment = comment;
    }

    /* 注：sql的select顺序请与title保持一致*/
    public DataTablePanel(String sql, Class clz, Object[] params,
                          String[] title, int column, ColumnInterface co,Rectangle rc,JLayeredPane comment) {
        this.sql = sql;
        this.clz = clz;
        this.params = params;
        this.titles = title;
        this.column = column;
        this.co = co;
        this.positionsize=rc;
        accdent=new HashMap<>();

        this.size=12;
        this.page=1;
        //宽高修正  宽应该是column+字符事件数量 的倍数 高应该是size+1的倍数
        this.positionsize.height= MathUtil.getDetail( this.positionsize.height,size+1)+pageHeight;
        this.positionsize.width=MathUtil.getDetail(this.positionsize.width,column);

        this.comment=comment;
        //默认的初始值
        this.width=rc.width;
        this.height=rc.height-pageHeight;

        imageID=new ArrayList<>();


        color1= Color.pink;
        color2= Color.yellow;

    }

    public int getTotalPage()
    {
        StringBuilder countSql=new StringBuilder();
        TableInfo ti=TableContext.poClassTableMap.get(clz);
        List<ColumnInfo> prikeyss=ti.getPriKeys();


        countSql.append("select count(").append(clz.getSimpleName()).append(".").append(prikeyss.get(0).getName()).append(") ");

        countSql.append(sql.substring(sql.lastIndexOf("from"),sql.length()));



       return MysqlQuery.query.queryNumber(countSql.toString(),params).intValue();

    }

    public Object[] getParams() {
        return params;
    }

    public String getSql() {
        return sql;
    }

    public JPanel generateMsg() {
        JPanel panel = new JPanel();
        int wid = (int)(width/ (column+accdent.size()));
        int hei = (int)((height) /(size+1));
        panel.setSize((int)width, (int)height);
        panel.setLayout(null);

        HashMap<Integer, List<Object>> map = ManagerFunc.getMap(sql,clz,params,page,size,co);
        /*画第一列*/
        for (int i = 0; i < column; i++) {
            JTextField label = new JTextField(titles[i]);
            label.setBounds(i * wid, 0, wid , hei);
            label.setBackground(color1);
            label.setEditable(false);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(null);
            int times=i;
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                     label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setCursor(Cursor.getDefaultCursor());
                }

                @Override
                public void mousePressed(MouseEvent e) {

                    if(map.get(0).get(times) instanceof  byte[])
                        return ;
                    StringBuilder msg=new StringBuilder();
                    String ssql=sql;
                    if(sql.indexOf("order by")==-1)
                        msg.append(ssql);
                    else
                        msg.append(ssql.substring(0,ssql.lastIndexOf("order by")+8));
                    String fieldname;
                    if(column==1)
                    {
                        if(ssql.indexOf("distinct")==-1)
                            fieldname=sql.substring(ssql.indexOf("select")+7,ssql.indexOf("from"));
                        else{
                            fieldname=sql.substring(ssql.indexOf("distinct")+9,ssql.indexOf("from"));
                        }
                    }

                    else{
                        if(times==0)
                        {
                            if(ssql.indexOf("distinct")==-1)
                                fieldname=ssql.substring(ssql.indexOf("select")+6,ssql.indexOf(","));
                            else
                                fieldname=sql.substring(ssql.indexOf("distinct")+9,ssql.indexOf(","));
                        }
                        else if(times>0&&times<column-1)
                        {
                            for(int i=0;i<times;i++)
                            {
                                ssql=ssql.substring(ssql.indexOf(",")+1,ssql.length()) ;
                            }
                            fieldname=ssql.substring(0,ssql.indexOf(","));
                        }
                        else{
                            for(int i=0;i<=times;i++)
                            {
                                ssql=ssql.substring(ssql.indexOf(",")+1,ssql.length()) ;
                            }
                            fieldname= ssql.substring(0,ssql.indexOf("from"));
                        }
                    }
                    msg.append("  ").append(fieldname);


                    if(desc)
                    {
                        msg.append(" asc");
                        desc=!desc;
                    }
                    else
                    {
                        msg.append(" desc");
                        desc=!desc;
                    }

                    sql=msg.toString();
                    generateTable();
                }
            });
            panel.add(label);
        }
        //加空列
        for(int i=column;i<column+accdent.size();i++)
        {
            JTextField label = new JTextField("");
            label.setBounds(i * wid, 0, wid , hei);
            label.setBackground(color1);
            label.setEditable(false);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setBorder(null);
            panel.add(label);
        }

        int totalsize=getTotalPage();
        totalPage=totalsize%size==0?(totalsize/size):(totalsize/size+1);



        if(map==null||map.size()<=0)
        { JPanel bkcolor=new JPanel();
        bkcolor.setBounds( 0,   hei, width , hei);
        JLabel label = new JLabel("暂无记录");
            label.setBounds(wid/5, 0, wid*2 , hei);
            bkcolor.setBackground(color2);
            bkcolor.add(label);
            panel.add(bkcolor);
            return panel;
        }




        //为每一行赋值
        int highs = 1;
        for (Object hid : map.keySet()) {
            List<Object> list = map.get(hid);
            for (int i = 0; i < list.size(); i++) {
                JPanel bkcolor=new JPanel();
                bkcolor.setBounds(i * wid, highs * hei, wid , hei);
                JLabel label = null;

                    if(list.get(i) instanceof  Integer)
                        label=new JLabel(String.valueOf(list.get(i)));
                    if(list.get(i) instanceof  String)
                         label = new JLabel((String) list.get(i));
                    if(list.get(i) instanceof Timestamp)
                        label = new JLabel(((Timestamp)(list.get(i))).toString());
                    if(list.get(i) instanceof  byte[])
                    {
                        ImageIcon icon = new ImageIcon((byte[]) list.get(i));
                        icon = ImageUtil.StretchPngImage(icon, Math.min(wid, hei)-10,Math.min(wid, hei)-10);
                        label = new JLabel(icon);
                        label.setBounds(0, 0, wid, hei-5);
                    }
                    label.setBounds(wid/5, 0, wid*2 , hei);

                //单数 奇数行设不同颜色
                if(highs%2==0)
                    bkcolor.setBackground(color1);
                else
                    bkcolor.setBackground(color2);
                //label.setHorizontalAlignment(SwingConstants.CENTER);

                // bkcolor.setBorder(BorderFactory.createLineBorder(Color.gray));
                bkcolor.add(label);
                panel.add(bkcolor);
            }

            int i=0;
            for (String text:accdent.keySet()) {
                JPanel bkcolor=new JPanel();
                bkcolor.setBounds((i+list.size()) * wid, highs * hei, wid , hei);
                /*将按钮加上去*/
                TextButton but=new TextButton(text,new Rectangle(0,0,wid,hei));
                but.addActionListener(e->{
                   ActionInterface ac= accdent.get(text);
                   ac.action(new Object[]{list});
                });
                if(highs%2==0)
                    bkcolor.setBackground(color1);
                else
                    bkcolor.setBackground(color2);

                bkcolor.add(but);
                panel.add(bkcolor);
                i++;
            }
            highs++;
        }

        return panel;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setTitle(String[] title) {
        this.titles = title;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setCo(ColumnInterface co) {
        this.co = co;
    }

    public void setWidth(int width) {
        this.width = width;
        this.width=MathUtil.getDetail(width,column);
        this.positionsize.width=width;
    }

    public void setHeight(int height) {
        this.height = height;
        this.height=MathUtil.getDetail(height,size);
        this.positionsize.height=height+pageHeight;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public void setImageID(int id) {
        this.imageID.add(id);
    }
    public void removeId(int id) {
        this.imageID.remove(id);
    }
    public void setPage(int page) {
        this.page = page;

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int size) {
        this.size = size;
        this.height=MathUtil.getDetail(height,size);
        this.positionsize.height=height+pageHeight;
    }


    /*封装页码*/
    public TextButton getNumButton(int i, int w)
    {
        TextButton text = new TextButton(i + "", new Rectangle(20 * w, 0, 40, 40));
        text.setFont(new Font(Font.SERIF, Font.BOLD, 12));
        text.setForeground(Color.gray);
        if(page==i)
            text.setForeground(Color.blue);

        text.addActionListener(e->{
            page=Integer.parseInt(text.getText());
            /*
            List<Integer>  imageID=new ArrayList<>();
            imageID.add(new Integer(3));
            JPanel msgPanel=generateMsg();
            msgPanel.setBounds(positionsize);

            comment.add(msgPanel,new Integer(num++));

            //转页
            JPanel page_panel= generatePage();
            page_panel.setBounds(10,710,600,40);
            page_panel.setBackground(pagecolor);
            comment.add(page_panel,new Integer(num2++));*/
            generateTable();
        });
        return text;
    }

    //生成页码
    public JPanel generatePage() {
        JPanel panel = new JPanel();
        panel.setSize((int)width,50);
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

    public void generateTable()
    {
        JPanel panel=generateMsg();

        Rectangle rs=new Rectangle(positionsize.x,positionsize.y,width,height);

        panel.setBounds(rs);
        comment.add(panel,new Integer(num2));

        JPanel pagePanel=generatePage();
        pagePanel.setBackground(pagecolor);
        Rectangle pageRs=new Rectangle(rs.x,rs.y+rs.height,rs.width,pageHeight);
        pagePanel.setBounds(pageRs);
        comment.add(pagePanel,new Integer(num2++));
    }

    public void setPagecolor(Color pagecolor) {
        this.pagecolor = pagecolor;
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame();
        frame.setSize(1000,800);
        frame.setLayout(null);
        JLayeredPane panel=new JLayeredPane();
        panel.setBounds(0,0,1000,1000);

        String sql="select hid,hname,htype,photo,addressid,hprice,uname,hstate from house where hstate=? order by hprice desc";

        DataTablePanel date=new DataTablePanel(sql,House.class,new Object[]{0},new String[]{"序列号","房名","房型","照片",
                "地址","售价","房主"},7,new HouseColumImpl(),new Rectangle(80,50,600,600),panel);
        date.setImageID(3);


        date.setSize(12);
        date.addMap("修改", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                List<Object> keys=( List<Object>)Keys[0];

                System.out.println(keys.get(0));
            }
        });

 /*       date.addMap("批准", new ActionInterface() {
            @Override
            public void action(Object[] Keys) {
                List<Object> keys=( List<Object>)Keys[0];

                System.out.println(keys.get(2) );
            }
        });*/
        date.generateTable();

        frame.add(panel);
        frame.setVisible(true);

    }
}
