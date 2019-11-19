package edu.fzu.house.gui.login;

import edu.fzu.house.core.login.LoginFunc;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.gui.login.panel.RectInputPanel;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private LoginFrame parent;
    public RectInputPanel uname;
    public RectInputPanel pwd;
    public RectInputPanel spwd;
    public ButtonGroup group;
    public  JLayeredPane layer_panel= new JLayeredPane();
    public int type;
    private Point mouse_position = new Point(0, 0);
    //鼠标拖住移动窗口
    public void MoveWindow ( int x, int y){
        this.setLocation(this.getX() + x, this.getY() + y);
    }
    //添加单选框
    public JRadioButton add(String name,int type)
    {
        JRadioButton radio=new JRadioButton(name);
        group.add(radio);
        radio.addActionListener(e -> {
            this.type=type;
        });
        return radio;
    }
    public RegisterFrame(LoginFrame frame)
    {
        parent=frame;
        setSize(new Dimension(250, 400));//窗口大小
        FrameUtil.Setting(this,layer_panel,70,LoginFrame.bkColor);


        layer_panel.setPreferredSize(new Dimension(250,400));

        group=new ButtonGroup();

        /*背景设置*/
        ImageIcon bk_image=new ImageIcon("src/image/bkimage/bk.png");
        bk_image=ImageUtil.StretchPngImage(bk_image,112,178);
        JLabel bk_label=new JLabel(bk_image);
        bk_label.setBounds(150,300,112,178);
        layer_panel.add(bk_label,new Integer(200));
        //用户输入框
        uname=RectInputPanel.addInput(new ImageIcon("src/image/Icon/login/uname.png"),
                "用户名",false,uname,new Rectangle(0,60,250,40));
        layer_panel.add(uname,new Integer(300));

        //密码输入框

        pwd=RectInputPanel.addInput(new ImageIcon("src/image/Icon/login/pwd.png"),"密码",true,uname,new Rectangle(0,120,250,40));
        pwd.setXY(125,-5);
        layer_panel.add(pwd);


        //确认密码
        spwd=RectInputPanel.addInput(new ImageIcon("src/image/Icon/login/pwd.png"),"确认密码",true,uname,new Rectangle(0,180,250,40));
        spwd.setXY(125,-5);
        layer_panel.add(spwd);


        //单选框
        JRadioButton radio=add("管理员",2);
        radio.setFont(new Font(Font.SERIF,Font.PLAIN,12));
        radio.setOpaque(false);
        radio.setBounds(30,220,70,40);
        layer_panel.add(radio,new Integer(100));

        radio=add("商家",1);
        radio.setFont(new Font(Font.SERIF,Font.PLAIN,12));
        radio.setOpaque(false);
        radio.setBounds(100,220,60,40);
        layer_panel.add(radio,new Integer(100));

        radio=add("用户",0);
        radio.setOpaque(false);
        radio.setFont(new Font(Font.SERIF,Font.PLAIN,12));
        radio.setBounds(150,220,60,40);
        layer_panel.add(radio,new Integer(100));

        /*注册按钮*/
        ImageIcon login_icon=new ImageIcon("src/image/Icon/login/register.png");
        login_icon= ImageUtil.StretchPngImage(login_icon,25,25);

        RoundButtonPanel button=new RoundButtonPanel(login_icon,180,45,"注册~",new Color(102,126,175),
                new Color(219, 237, 255),12);
        button.setBackground(LoginFrame.bkColor);
        button.setBounds(32,260,180,45);
        button.button.addActionListener(e->{

            int count= LoginFunc.Register(uname.getText(),pwd.getText(),spwd.getText(),type);
            if(count==1) {
                JOptionPane.showMessageDialog(null, "注册成功");
            }
            else if(count==0){
                System.out.println("注册失败");
            }
            else if(count==-1)
            {
                JOptionPane.showMessageDialog(null,"用户名重复");
            }

        });
        layer_panel.add(button,new Integer(300));

        /*返回按钮*/

        login_icon=new ImageIcon("src/image/Icon/login/return.png");
        login_icon= ImageUtil.StretchPngImage(login_icon,25,25);

         button=new RoundButtonPanel(login_icon,180,45,"返回~",new Color(102,126,175),
                new Color(219, 237, 255),12);
        button.setBackground(LoginFrame.bkColor);
        button.setBounds(32,310,180,45);
        button.button.addActionListener(e->{
            frame.setVisible(true);
            this.setVisible(false);
        });
        layer_panel.add(button,new Integer(100));
        this.add(layer_panel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        LoginFrame frame=new LoginFrame();
        frame.setVisible(false);
        new RegisterFrame(frame);
    }
}
