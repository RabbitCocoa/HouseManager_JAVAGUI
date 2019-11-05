package edu.fzu.house.gui.login;

import com.sorm.core.TableContext;
import com.sun.awt.AWTUtilities;
import edu.fzu.house.core.login.LoginFunc;
import edu.fzu.house.gui.login.panel.InputButtonPanel;
import edu.fzu.house.gui.login.panel.RectInputPanel;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginFrame extends JFrame {
    public JLayeredPane layer_panel;//分层面板
    private RectInputPanel name;
    private RectInputPanel pwd;
    private Point mouse_position = new Point(0, 0);
    public static Color bkColor = new Color(143, 163, 204);
    private RegisterFrame frame;
    private JLabel label_photo;
    //增加一个自定义输入框 包括图标 文本

    //读取文件
    static {
        LoginFunc.ReadPassword();
    }

    //初始化布局
    public void InitBackground() {

        //初始化分层面板 设置大小
        layer_panel = new JLayeredPane();
        layer_panel.setPreferredSize(new Dimension(FrameUtil.SCREEN_WIDTH, FrameUtil.SCREEN_HEIGHT));

        /*关闭按钮*/
        ImageIcon close_image=new ImageIcon("src/image/Icon/close.png");
        close_image=ImageUtil.StretchPngImage(close_image,40,40);
        JButton close_button=new JButton(close_image);
        close_button.setBorderPainted(false); //去除边框
        close_button.setBackground(bkColor); //透明
        close_button.setFocusPainted(false); //去焦点
        //设置颜色事件
        close_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               close_button.setBackground(bkColor.darker());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                close_button.setBackground(bkColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                close_button.setBackground(bkColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                close_button.setBackground(bkColor);
            }
        });
        //关闭事件
        close_button.addActionListener(e->{
            System.exit(0);
        });
        //按钮大小
        close_button.setBounds(70,5,40,40);

        layer_panel.add(close_button);
        /*背景设置*/
        ImageIcon bk_image = new ImageIcon("src/image/bkimage/bk.png");
        bk_image = ImageUtil.StretchPngImage(bk_image, 112, 178);
        JLabel bk_label = new JLabel(bk_image);
        bk_label.setBounds(80, -100, 250, 400);
        layer_panel.add(bk_label, new Integer(200));


        /*用户框*/
        ImageIcon icon = new ImageIcon("src/image/Icon/uname.png");
        Rectangle r = new Rectangle(0, 180, 250, 40);
        name = RectInputPanel.addInput(icon, "请输入用户名", false, name, r);
        if (LoginFunc.uname != null && !LoginFunc.uname.equals("")) {
            name.getInput().setText(LoginFunc.uname);
            name.getInput().setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
            name.getInput().setForeground(new Color(219, 237, 255));
            name.getInput().setHorizontalAlignment(JTextField.LEFT);//居中对齐
        }
        name.getInput().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                byte[] image= LoginFunc.readImage(name.getText());
                if(image==null)
                {
                    label_photo.setIcon(new ImageIcon("src/image/bkimage/default.jpg"));
                    return;
                }

                ImageIcon icon=new ImageIcon(image);
                icon=ImageUtil.CutCircleImage(icon,80);
                label_photo.setIcon(icon);
            }
        });
        layer_panel.add(name, new Integer(300));
        /*密码框*/
        icon = new ImageIcon("src/image/Icon/pwd.png");
        pwd = RectInputPanel.addInput(icon, "请输入密码", true, pwd, new Rectangle(0, 240, 250, 40));
        if (LoginFunc.pwd != null && !LoginFunc.pwd.equals("")) {
            pwd.getPassword().setText(LoginFunc.pwd);
            pwd.lab.setText("");
            pwd.getPassword().setFont(new Font("微软雅黑", Font.TYPE1_FONT, 12));
            pwd.getPassword().setForeground(new Color(219, 237, 255));
            pwd.getPassword().setHorizontalAlignment(JTextField.LEFT);//居中对齐
        }
        layer_panel.add(pwd, new Integer(300));

        //记住密码
        JCheckBox checkBox = new JCheckBox("记住密码");
        checkBox.setOpaque(false);
        //如果上次登陆时勾选了 则下次登陆时默认勾选
        checkBox.setBounds(80, 270, 100, 50);
        checkBox.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        checkBox.setForeground(new Color(219, 237, 255));
        checkBox.setBorder(null);
        if (pwd.getText() != null && !pwd.getText().equals(""))
            checkBox.setSelected(true);
        layer_panel.add(checkBox, new Integer(300));


        /*注册按钮***************/
        JButton label = new JButton("Register");
        label.setBorderPainted(false); //去除边框
        label.setContentAreaFilled(false); //透明
        label.setFocusPainted(false); //去焦点
        label.setBounds(140, 270, 100, 50);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        label.setForeground(new Color(219, 237, 255));
        //颜色变化事件
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                label.setForeground(new Color(110, 161, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                label.setForeground(new Color(219, 237, 255));
            }
        });
        //注册事件
        label.addActionListener(e -> {
            this.setVisible(false);
            frame = new RegisterFrame(this);
            frame.setVisible(true);
        });
        layer_panel.add(label, new Integer(300));

        //登录按钮

        ImageIcon login_icon = new ImageIcon("src/image/Icon/login.png");
        login_icon = ImageUtil.StretchPngImage(login_icon, 25, 25);

        InputButtonPanel button = new InputButtonPanel(login_icon, 200, 45, "GO~~", new Color(102, 126, 175),
                new Color(219, 237, 255), 12);
        button.setBackground(bkColor);
        button.setBounds(30, 320, 200, 45);
        button.button.addActionListener(e -> {
            switch (LoginFunc.Login(name.getText(), pwd.getText())) {
                case 0: {
                    if (checkBox.isSelected())
                        LoginFunc.RemberPassword(name.getText(), pwd.getText(), true);
                    else
                        LoginFunc.RemberPassword(name.getText(), pwd.getText(), false);
                }
                break;
                case 1: {
                    if (checkBox.isSelected())
                        LoginFunc.RemberPassword(name.getText(), pwd.getText(), true);
                    else
                        LoginFunc.RemberPassword(name.getText(), pwd.getText(), false);
                    System.out.println();
                }
                break;
                case 2: {
                    if (checkBox.isSelected())
                        LoginFunc.RemberPassword(name.getText(), pwd.getText(), true);
                    else
                        LoginFunc.RemberPassword(name.getText(), pwd.getText(), false);
                    System.out.println();
                    System.out.println();
                }
                break;
                default:
                    JOptionPane.showMessageDialog(null, "用户名或密码错误");

            }
            layer_panel.add(button, new Integer(300));
        });
        layer_panel.add(button,new Integer(300));



        /*头像*/
        ImageIcon photo=new ImageIcon("src/image/bkimage/default.jpg");
        photo=ImageUtil.CutCircleImage(photo,80);
        label_photo=new JLabel(photo);
        label_photo.setBounds(50,80,80,80);
        layer_panel.add(label_photo);


    }
    //鼠标拖住移动窗口
    public void MoveWindow ( int x, int y){
            this.setLocation(this.getX() + x, this.getY() + y);
        }



    public LoginFrame() {
            FrameUtil.Setting(this); //一些通用设置
            this.setUndecorated(true);//无标题栏
            setSize(new Dimension(250, 400));//窗口大小
            AWTUtilities.setWindowShape(this, new RoundRectangle2D.Double(0.0d, 0.0d, this.getWidth(), this.getHeight(),
                    30, 30));//圆角窗口
            this.setFocusable(true);

            //监控鼠标 可以拖动
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouse_position = e.getPoint();
                }

            });
            this.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point now_position = e.getPoint();
                    int x = now_position.x - mouse_position.x;
                    int y = now_position.y - mouse_position.y;
                    MoveWindow(x, y);
                }
            });
            //监控键盘 按下ESC退出
            this.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        System.exit(0);
                    }
                }
            });

            InitBackground();//初始化布局

            this.getContentPane().setBackground(new Color(143, 163, 204));
            this.add(layer_panel, BorderLayout.CENTER);

            this.setVisible(true);
        }

        public static void main (String[]args){
            LoginFrame login = new LoginFrame();

        }
    }
