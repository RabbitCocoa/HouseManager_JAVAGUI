package edu.fzu.house.gui.Seller;

import com.sorm.po.Haddress;
import com.sorm.po.Hsuser;
import edu.fzu.house.core.login.LoginFunc;
import edu.fzu.house.core.login.MysqlQuery;
import edu.fzu.house.core.setter.SetterFunc;
import edu.fzu.house.gui.login.LoginFrame;
import edu.fzu.house.gui.login.panel.RectInputPanel;
import edu.fzu.house.gui.login.panel.RoundButtonPanel;
import edu.fzu.house.util.FrameUtil;
import edu.fzu.house.util.IOUtil;
import edu.fzu.house.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RegisterHouse extends JFrame {
    public JLayeredPane layer_panel = new JLayeredPane();

    public RectInputPanel hname;
    public RectInputPanel hprice;
    public RectInputPanel harea;
    public List<byte[]> imgs = new ArrayList<>();
    public List<String> paths = new ArrayList<>();
    public int deg = 300;
    public JComboBox<String> provinces= new JComboBox<>();;
    public JComboBox<String> towns= new JComboBox<>();
    public JComboBox<String> contras=new JComboBox<>();;
    public JComboBox<String> areas=new JComboBox<>();;
    public JComboBox<String> type=new JComboBox<>();;
public  JTextArea are;
    public  JCheckBox box1=new JCheckBox("小区");
    public JCheckBox box2=new JCheckBox("高楼");
    public JCheckBox box3=new JCheckBox("阳台");
    public JCheckBox box4=new JCheckBox("近地铁");
    public JCheckBox box5=new JCheckBox("朝南");
    void showImage_Button() {
        //图片区+按钮区
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(LoginFrame.bkColor);
        panel.setBounds(65, 360, 200, 60);

        int i = 0;
        for (; i < imgs.size(); i++) {
            ImageIcon img = new ImageIcon(imgs.get(i));
            img = ImageUtil.StretchPngImage(img, 60, 60);
            JButton label = new JButton(img);
            label.setBorderPainted(false);
            label.setFocusPainted(false);
            label.setContentAreaFilled(false);
            label.setBounds(i * 62, 0, 60, 60);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    ImageIcon error = new ImageIcon("src/image/Icon/Manager/error.png");
                    error = ImageUtil.StretchPngImage(error, 60, 60);
                    Cursor coursor = Toolkit.getDefaultToolkit().createCustomCursor
                            (error.getImage(), new Point(0, 0), "error");
                    setCursor(coursor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            int j = i;
            label.addActionListener(e -> {
                imgs.remove(j);
                paths.remove(j);
                showImage_Button();

            });

            panel.add(label);
        }
        ImageIcon image_icon = new ImageIcon("src/image/Icon/seller/image.png");
        image_icon = ImageUtil.StretchPngImage(image_icon, 20, 20);

        if (imgs.size() <= 3) {
            JButton button = new JButton(image_icon);
            button.setBounds(i * 65, 0, 30, 30);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getDefaultCursor());
                }
            });
            button.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg/png/jpeg", "jpg", "png", "jpeg");
                fileChooser.setFileFilter(filter);
                fileChooser.showOpenDialog(null);

                File file = fileChooser.getSelectedFile();
                BufferedInputStream bis = null;
                ByteArrayOutputStream bos = null;
                if (file == null)
                    return;
                try {
                    bis = new BufferedInputStream(new FileInputStream(file));
                    int len = -1;
                    bos = new ByteArrayOutputStream();
                    byte[] dates = new byte[1024 * 1024];
                    while ((len = bis.read(dates)) != -1) {
                        bos.write(dates, 0, len);
                    }
                    bos.flush();
                    imgs.add(bos.toByteArray());
                    paths.add(file.getAbsolutePath() + "&");
                    showImage_Button();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    IOUtil.Close(bos, bis);
                }

            });
            panel.add(button);
        }
        layer_panel.add(panel, new Integer(deg++));
    }

    void addType()
    {
        type.addItem("未选择");
        type.addItem("一室");
        type.addItem("二室");
        type.addItem("三室");
        type.addItem("四室");
        type.addItem("五室");
        type.addItem("五室以上");
        layer_panel.add(type);
    }

    void addAreas() {
        List<Haddress> area = MysqlQuery.query.queryRows("select area from haddress where province = ? and " +
                        "town=? and county=?",
                Haddress.class, new Object[]{provinces.getItemAt(provinces.getSelectedIndex()),
                        towns.getItemAt(towns.getSelectedIndex()),
                        contras.getItemAt(contras.getSelectedIndex())});

        areas.removeAllItems();
        areas.addItem("未选择");
        if(area==null)
            return ;
        if (area.size() > 0) {
            for (int i = 0; i < area.size(); i++) {
                areas.addItem(area.get(i).getArea());
            }
        }

    }

    void addCountry() {
        List<Haddress> areas = MysqlQuery.query.queryRows("select distinct county from haddress where province = ? and " +
                        "town=?",
                Haddress.class, new Object[]{provinces.getItemAt(provinces.getSelectedIndex()),
                        towns.getItemAt(towns.getSelectedIndex())});

        contras.removeAllItems();
        contras.addItem("未选择");
        if(areas==null)
            return ;
        for (int i = 0; i < areas.size(); i++) {
            contras.addItem(areas.get(i).getCounty());
        }
        contras.addActionListener(e -> {
            addAreas();
        });

    }

    void addTowns() {
        List<Haddress> areas = MysqlQuery.query.queryRows("select distinct town from haddress where province = ?",
                Haddress.class, new Object[]{provinces.getItemAt(provinces.getSelectedIndex())});

        towns.removeAllItems();
        towns.addItem("未选择");
        if(areas==null)
            return ;
        for (int i = 0; i < areas.size(); i++) {
            towns.addItem(areas.get(i).getTown());
        }
        towns.addActionListener(e -> {
            addCountry();
        });

    }

    void addProcess() {
        List<Haddress> areas = MysqlQuery.query.queryRows("select distinct province  from haddress", Haddress.class, null);

        provinces.removeAllItems();
        provinces.addItem("未选择");
        for (int i = 0; i < areas.size(); i++) {
            provinces.addItem(areas.get(i).getProvince());
        }
        provinces.addActionListener(e -> {
            addTowns();
        });
    }

    public RegisterHouse(Hsuser user) {

        setSize(new Dimension(320, 800));//窗口大小
        FrameUtil.Setting(this, layer_panel, 120, LoginFrame.bkColor);

        layer_panel.setPreferredSize(new Dimension(320, 800));

        /* *//*背景设置*//*
        ImageIcon bk_image=new ImageIcon("src/image/bkimage/bk.png");
        bk_image= ImageUtil.StretchPngImage(bk_image,112,178);
        JLabel bk_label=new JLabel(bk_image);
        bk_label.setBounds(140,240,112,178);
        layer_panel.add(bk_label,new Integer(200));*/

        //用户输入框
        hname = RectInputPanel.addInput(new ImageIcon("src/image/Icon/seller/house.png"), "房名",
                false, hname, new Rectangle(40, 60, 240, 40));
        layer_panel.add(hname, new Integer(300));

        //价格输入框
        hprice = RectInputPanel.addInput(new ImageIcon("src/image/Icon/seller/money.png"), "价钱",
                false, hprice, new Rectangle(40, 120, 250, 40));
        layer_panel.add(hprice, new Integer(300));

        //区域输入框
        harea = RectInputPanel.addInput(new ImageIcon("src/image/Icon/seller/area.png"), "面积",
                false, harea, new Rectangle(40, 180, 250, 40));
        layer_panel.add(harea, new Integer(300));

        //描述框
        are = new JTextArea("房源描述");
        are.setOpaque(false);
        are.setBounds(65, 240, 200, 100);
        are.setBorder(BorderFactory.createLineBorder(Color.gray));
        are.setLineWrap(true);
        layer_panel.add(are, new Integer(300));

        //图片区
        showImage_Button();

        //下拉框 地址

        provinces.setBounds(30, 450, 60, 30);
        layer_panel.add(provinces);

        areas.setBounds(230, 450, 60, 30);
        layer_panel.add(areas);

        towns.setBounds(100, 450, 60, 30);
        layer_panel.add(towns);

        contras.setBounds(170, 450, 60, 30);
        layer_panel.add(contras);
        addProcess();
        addType();

        JPanel type_boder=new JPanel();
        type_boder.setOpaque(false);
        type_boder.setBounds(90,500,140,60);
        type_boder.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray),"房型"));
        layer_panel.add(type_boder);
        type.setBounds(105,520,100,30);

        //边框
        JPanel border=new JPanel();
        border.setOpaque(false);
        border.setBounds(10,430,300,60);
        border.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray),"地址"));
        layer_panel.add(border);


        //额外信息
        JPanel check_border=new JPanel();
        check_border.setOpaque(false);
        check_border.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray),"额外信息"));
        check_border.setBounds(10,600,280,80);

        box1.setOpaque(false);
        box2.setOpaque(false);
        box3.setOpaque(false);
        box4.setOpaque(false);
        box5.setOpaque(false);
        check_border.add(box1);
        check_border.add(box2);
        check_border.add(box3);
        check_border.add(box4);
        check_border.add(box5);
        layer_panel.add(check_border);

        //注册
        /*注册按钮*/
        ImageIcon login_icon=new ImageIcon("src/image/Icon/login/register.png");
        login_icon= ImageUtil.StretchPngImage(login_icon,25,25);

        RoundButtonPanel button=new RoundButtonPanel(login_icon,180,45,"提交~",new Color(102,126,175),
                new Color(219, 237, 255),12);
        button.setBackground(LoginFrame.bkColor);
        button.setBounds(80,700,180,45);
        button.button.addActionListener(e->{
            //检查是否为空
            if(hname.getText().equals("")||hprice.getText().equals("")||harea.getText().equals("")
                ||paths.size()<1||provinces.getSelectedIndex()<1||towns.getSelectedIndex()<1
                    ||contras.getSelectedIndex()<1||areas.getSelectedIndex()<1)
            {
                JOptionPane.showMessageDialog(null,"请完善信息");
                return;
            }
           try{
            if(Integer.parseInt(hprice.getText())<=0 || Integer.parseInt(harea.getText())<=0)
                throw new Exception();
           }
           catch (Exception ew)
           {
               JOptionPane.showMessageDialog(null,"请输入正确的数字");
               return;
           }
           //获得价钱
           int  m_price=Integer.parseInt(hprice.getText());
           //获得面积
           int  m_area=Integer.parseInt(harea.getText());
           //获得地址
           String sql="select addressid from haddress where province=? and town=? and county=? and area=?";
           int addressId=MysqlQuery.query.queryNumber(sql,new Object[]{provinces.getItemAt(provinces.getSelectedIndex()),
           towns.getItemAt(towns.getSelectedIndex()),contras.getItemAt(contras.getSelectedIndex()),areas.getItemAt(
                   areas.getSelectedIndex())
           }).intValue();
           //获得图片路径
            StringBuilder builder=new StringBuilder();
            for (String path : paths) {
                builder.append(path);
            }
            //获得额外信息
            StringBuilder builder2=new StringBuilder();
            if(box1.isSelected())
                builder2.append("1");
            else
                builder2.append(0);
            if(box2.isSelected())
                builder2.append("1");
            else
                builder2.append(0);
            if(box3.isSelected())
                builder2.append("1");
            else
                builder2.append(0);
            if(box4.isSelected())
                builder2.append("1");
            else
                builder2.append(0);
            if(box5.isSelected())
                builder2.append("1");
            else
                builder2.append(0);


            int count= SetterFunc.Register(user.getUname(),hname.getText(),type.getSelectedIndex(),builder.toString(),
                    m_area,are.getText(),m_price,builder2.toString(),addressId);


            if(count==1) {
                JOptionPane.showMessageDialog(null, "注册成功");
                this.setVisible(false);
            }
            else {
                System.out.println("注册失败");
            }


        });
        layer_panel.add(button,new Integer(300));


        add(layer_panel);
        setVisible(true);



    }

    public static void main(String[] args) {
        new RegisterHouse(null);

    }
}
