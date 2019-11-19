package edu.fzu.house.gui.login.panel;



import javax.swing.*;
import java.awt.*;

import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RoundButtonText extends JPanel {
    public Color bk;
    public Color font;
    public BufferedImage bi;
    public int maxWidth;
    public  static List<String>   handleText(String text, int column)
    {
         List<String> results=new ArrayList<>();
         while(text.length()>column)
         {
             int len1=text.indexOf("\n");
             String sql;
             if(len1!=-1&&len1<column)
             {
                 sql=text.substring(0,len1+1);
                 text=text.substring(len1+1,text.length());
             }
            else{
                sql=text.substring(0,column);
                sql+="\n";
                text=text.substring(column,text.length());
             }
             results.add(sql);
         }

          if(text.length()>0)
              results.add(text);


        return results;
    }

    public static int getMaxSize(List<String> lists)
    {
        int n=0;
        for (String list : lists) {
            n=list.length()>n?list.length():n;
        }
        return n;
    }


    public RoundButtonText(int x,int y, String text, Color bk, Color font,int column,int size)
    {
        if(text==null||text.equals(""))
            return;
        this.setLayout(null); //空布局
     //   this.setBounds(r);
        this.bk=bk;
        this.font=font;

        int width,height;
        List<String> result=handleText(text,column);
        if(result.size()==1)
        {
            width=result.get(0).length()*12+20;
            height=30;
        }
        else{
          width=getMaxSize(result)*12+20;
          height=result.size()*10+20;
        }
        maxWidth=width;
        this.setBounds(x,y,width+20,height+20);

        //画圆角矩形 作为外边框
        bi=new BufferedImage(width+20,height+20,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2=bi.createGraphics();
        //透明处理
        bi = g2.getDeviceConfiguration().createCompatibleImage(width+20, height+20, Transparency.TRANSLUCENT);
        g2.dispose();
        g2=bi.createGraphics();
        //平滑
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bk);
        //填充圆角矩形
        g2.fill(new RoundRectangle2D.Float(0,2,width+15,height+15,45,45));
        JLabel  label=new JLabel(new ImageIcon(bi));
        label.setBounds(0,0,width+20,height+20);
        int i=0;
        for (String s : result) {
            JTextField col=new JTextField(s);
            col.setFont(new Font(Font.SERIF,Font.PLAIN,size));
            col.setForeground(font);
            col.setBounds(width/20+10,10+height/10+(size+1)*i++,width,size+4);
            col.setFocusable(false);
            col.setEditable(false);
            col.setOpaque(false);
            col.setBorder(null);
            this.add(col);
        }

        this.add(label);


        //this.setMargin(new Insets(0,0,0,0));

    }

    public static void main(String[] args) {
        JFrame frame =new JFrame("2");
        frame.setLayout(null);
        frame.setSize(250,400);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.pink);



        RoundButtonText button=new RoundButtonText(
               30,30,"你好啊啊啊a 啊啊啊啊啊啊啊啊挖达带娃", Color.pink, Color.black,22,10);



        frame.add(button);


        frame.setVisible(true);

    }
}
