package edu.fzu.house.gui.Manager.table;

import com.sorm.po.Comment;
import com.sorm.po.Hsuser;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class NoticePanel extends JPanel {

    public NoticePanel(JLayeredPane layer, String rec, Rectangle r, Color bkcolor1, Color bkcolor2, Hsuser user)
    {
        //面板设置
        this.setLayout(null);
        this.setBounds(r);
        this.setBackground(bkcolor1);
        //
        String sql="select distinct cid,ctitle,ctext,ccreatetime from " +
                "comment  " +
                "where bcid=?   and cdegree=0  and bcid!=cid " +
                "  group by cid order by ccreatetime   ";
        //查询到消息列表
        List<Comment> commons=(List<Comment>) MysqlQuery.query.queryRows(sql,Comment.class,new Object[]{rec});

        if(commons==null)
        {
            JLabel text=new JLabel("暂无消息");
            text.setHorizontalAlignment(SwingConstants.CENTER);
            text.setBounds(r.width/3,r.height/3,100,100);
            text.setFont(new Font(Font.SERIF,Font.BOLD,16));
            this.add(text);
            layer.add(this);
            return;
        }

        //遍历消息列表 生成面板
        int i=0;
        for (Comment common : commons) {
            JPanel panel=new JPanel();
            panel.setLayout(null);
            panel.setBounds(0,100*i,r.width,100);
            panel.setBackground(bkcolor2);
            //查询头像
            String sql2="select photo from hsuser where uname=?";
            ImageIcon icon=new ImageIcon((byte[])MysqlQuery.query.queryValue(sql2,new Object[]{common.getCid()}));
            icon= ImageUtil.CutCircleImage(icon,100);

        //设置头像
            JLabel head=new JLabel(icon);
            head.setBounds(0,0,100,100);
            panel.add(head);

            //设置名字

            JLabel title=new JLabel(common.getCtitle());
            title.setBounds(120,0,200,40);
            title.setFont(new Font(Font.SERIF,Font.BOLD,14));
            title.setForeground(Color.black);
            panel.add(title);

            //设置正文
            JLabel text=new JLabel(common.getCtext());
            text.setForeground(Color.gray);
            text.setBounds(120,40,600,40);
            text.setFont(new Font(Font.SERIF,Font.BOLD,12));
            panel.add(text);

            //设置时间
            Timestamp timestamp=common.getCcreatetime();
            String time=new SimpleDateFormat("h:mm a").format(timestamp);
            JLabel timelabel=new JLabel(time);
            timelabel.setForeground(Color.gray);
            timelabel.setBounds(740,20,200,40);
            timelabel.setFont(new Font(Font.SERIF,Font.BOLD,12));
            panel.add(timelabel);

            //鼠标事件
            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    ChalkPanel chalk=new ChalkPanel(layer,user.getUname(),common.getCid(),null,
                            new Rectangle(r));

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    panel.setBackground(bkcolor2.darker());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                    panel.setBackground(bkcolor2);
                }
            });

            this.add(panel);
            i++;
        }
        layer.add(this);
    }

    public static void main(String[] args) {

    }
}
