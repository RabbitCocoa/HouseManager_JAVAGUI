package edu.fzu.house.gui.Manager.table;

import com.sorm.po.Comment;
import edu.fzu.house.core.Manager.ManagerFunc;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.gui.Manager.ManagerFrame;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.RoundButtonText;
import edu.fzu.house.gui.login.panel.RoundInputButton;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.List;

public class ChalkPanel extends JPanel {
    public JLayeredPane panel;
    public String self;
    public String ohter_people;
    public String title;
    public Rectangle r;
    public RoundInputButton rb;
    private static int i=400;
    public void generateTaliking() {


        JPanel talkingPanel = new JPanel();
        talkingPanel.setBounds(0, 0, r.width, r.height - 70);
        talkingPanel.setLayout(null);
        //获取两人的所有消息记录
        String sql = "select * from comment where (cid=? and bcid=? ) or (cid=? and bcid=?) " +
                "order by ccreatetime  ";

        List<Comment> commons = (List<Comment>) MysqlQuery.query.queryRows
                (sql, Comment.class, new Object[]{self, ohter_people, ohter_people, self});
        if(commons==null||commons.size()<=0)
        {
            panel.add(this, new Integer(i++));
            return ;
        }
        int i = 0;

        for (Comment common : commons) {
            int head_x, text_x;
            if (common.getCid().equals(self)) {
                head_x = r.width - 120;
                //取得文本最大宽度
                int width = RoundButtonText.getMaxSize(RoundButtonText.handleText(common.getCtext(), 24)) * 12 + 20;
                text_x = r.width - 120 - width;
            } else {
                head_x = 0;
                text_x = 100;
            }

            //查询头像
            String sql2 = "select photo from hsuser where uname=?";
            ImageIcon icon = new ImageIcon((byte[]) MysqlQuery.query.queryValue(sql2, new Object[]{common.getCid()}));
            icon = ImageUtil.CutCircleImage(icon, 80);

            //设置头像
            JLabel head = new JLabel(icon);
            head.setBounds(head_x, i * 100, 100, 100);
            talkingPanel.add(head);

            //设置消息框
            int len = common.getCtext().length();
            int width, height;
            if (len <= 30) {
                width = 14 * len;
                height = 30;
            } else {
                width = 30 * 14;
                height = (len / 30 + 1) * 30;
            }
            RoundButtonText buton = new RoundButtonText(text_x, i * 100 + 20,
                    common.getCtext(), new Color(0x47CCFF), Color.white, 24, 12);

            talkingPanel.add(buton);
            i++;
        }
        talkingPanel.setPreferredSize(new Dimension(r.width,i*100));
        JScrollPane scrollPane = new JScrollPane(talkingPanel);
        scrollPane.setBounds(0,0, r.width, r.height-70);
        // talkingPanel.setBackground(Color.white);
        this.add(scrollPane);
        panel.add(this, new Integer(1000+i++));
    }

    public ChalkPanel(JLayeredPane panel, String self, String ohter_people, String title, Rectangle r) {

        String sql = "select sname from hsuser where uname=?";

        if (title == null)
            this.title = (String) MysqlQuery.query.queryValue(sql, new Object[]{self});
        else this.title = title;

        this.panel = panel;
        this.self = self;
        this.ohter_people = ohter_people;
        this.r = r;

        this.setLayout(null);
        this.setBounds(r);
        //发送区域 只初始化一次
        JPanel send_area = new JPanel();
        send_area.setLayout(null);
        send_area.setBounds(0, r.height - 70, r.width, 70);

        //发送输入框
        ImageIcon icon = new ImageIcon("src/image/Icon/Manager/search.png");
        icon = ImageUtil.StretchPngImage(icon, 20, 20);
        rb = new RoundInputButton(icon, new Rectangle(0, 0, r.width, 70), ""
                , new Color(255,255,255), Color.gray, Color.LIGHT_GRAY, new Font(Font.SERIF, Font.PLAIN, 12), true);

        rb.setBackground(Color.white);
        SendAction Send = new SendAction(this, icon);
        rb.button.setAction(Send);
        InputMap imap = send_area.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        imap.put(KeyStroke.getKeyStroke("ENTER"), "send"); //第二个参数与ActionMap第一个参数对应

        ActionMap amap = send_area.getActionMap();
        amap.put("send", Send);

        send_area.add(rb);
        this.add(send_area);

        //消息框
        generateTaliking();

    }
}

class SendAction extends AbstractAction {

    public ChalkPanel cp;
    public SendAction(ChalkPanel cp, ImageIcon icon) {
        this.cp=cp;
        this.putValue(Action.SMALL_ICON, icon);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //generateTaliking();
        if(cp.rb.file.getText().equals(""))
            return;
        Comment comment = new Comment();
        comment.setCtext(cp.rb.file.getText());
        comment.setCname(ManagerFunc.generateSerialNum(20));
        comment.setCcreatetime(new Timestamp(System.currentTimeMillis()));

        comment.setCtitle(cp.title);
        comment.setCid(cp.self);
        comment.setBcid(cp.ohter_people);
        comment.setCdegree(0);
        MysqlQuery.query.insert(comment);

        ChalkPanel ck=new ChalkPanel(cp.panel,cp.self,cp.ohter_people,cp.title,cp.r);
    }
}