package edu.fzu.house.util;

import com.sun.org.apache.bcel.internal.generic.IADD;
import sun.awt.image.BufferedImageGraphicsConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/*
    图形工具类
*/
public class ImageUtil extends JFrame {
    //裁剪png文件 按长 宽等分
    /*
       源路径
       宽 高 注意这里是
     */
    public static boolean cut(String srcpath, int width, int height, String despath) {
        //取得文件名
        String name = srcpath.substring(srcpath.lastIndexOf("\\") + 1, srcpath.lastIndexOf("."));
        //取得后缀名
        String dname = srcpath.substring(srcpath.lastIndexOf(".") + 1, srcpath.length());
        ImageIcon icon = new ImageIcon(srcpath);
        int wid = icon.getIconWidth();
        int hei = icon.getIconHeight();

        //裁剪后一张图片大小
        int awid = wid / width;
        int ahei = hei / height;

        String path = despath;
        BufferedOutputStream bos = null;
        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    BufferedImage ret = new BufferedImage(awid, ahei, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g;
                    g = ret.createGraphics();
                    if (dname.equals("png")) {
                        /*图形背景透明处理*/
                        ret = g.getDeviceConfiguration().createCompatibleImage(awid, ahei, Transparency.TRANSLUCENT);
                        g.dispose();
                        g = ret.createGraphics();
                    }

                    File file = new File(path + "\\" + name + i + j + "." + dname);
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    g.drawImage(icon.getImage(), -i * awid, -j * ahei, null);
                    ImageIO.write(ret, dname, bos);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtil.Close(bos);
        }

    }

    //平滑拉伸图片
    public static ImageIcon StretchImage(ImageIcon icon, int width, int height) {
        int wid = icon.getIconWidth();
        int hei = icon.getIconHeight();

        BufferedImage bi = new BufferedImage(wid, hei, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(icon.getImage(), 0, 0, null); //将图像复制到缓冲区处理 避免损坏
        Image scaledImage = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon result = new ImageIcon(scaledImage);
        return result;
    }

    //拉伸png图片
    public static ImageIcon StretchPngImage(ImageIcon icon, int width, int height) {
        int wid = icon.getIconWidth();
        int hei = icon.getIconHeight();

        BufferedImage bi = new BufferedImage(wid, hei, BufferedImage.TYPE_INT_RGB);

        Graphics2D g;
        g = bi.createGraphics();
        bi = g.getDeviceConfiguration().createCompatibleImage(wid, hei, Transparency.TRANSLUCENT);
        g.dispose();
        g = bi.createGraphics();
        g.drawImage(icon.getImage(), 0, 0, null);

        Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    //裁剪图片
    public static ImageIcon CutImage(ImageIcon icon, int x, int y, int width, int height) {
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g;
        g = ret.createGraphics();
        g.drawImage(icon.getImage(), -x, -y, null);

        Image image = ret;
        return new ImageIcon(image);
    }

    //裁剪png图片
    public static ImageIcon CutPngImage(ImageIcon icon, int x, int y, int width, int height) {
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g;
        g = ret.createGraphics();
        ret = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        g = ret.createGraphics();
        g.drawImage(icon.getImage(), -x, -y, null);
        Image image = ret;
        return new ImageIcon(image);
    }

    public static ImageIcon CutCircleImage(ImageIcon icon, int radius) {
        icon = StretchPngImage(icon, radius, radius);
        BufferedImage result = new BufferedImage(radius, radius, BufferedImage.TYPE_INT_RGB);

        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, radius, radius);
        Graphics2D g2 = result.createGraphics();
        result = g2.getDeviceConfiguration().createCompatibleImage(radius, radius, Transparency.TRANSLUCENT);
        g2.dispose();
        g2 = result.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //设置抗锯齿
        g2.setClip(shape);

        g2.drawImage(icon.getImage(), 0, 0, null);

        return new ImageIcon(result);
    }

    public String srcpathmsg;
    public JTextField width;
    public JTextField height;
    public String despathmsg;

    public ImageUtil() {
        // FrameUtil.Setting(this);
        JPanel panel = new JPanel();
        JButton srcpath = new JButton("源路径");
        srcpath.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.jpg/png/jpeg/bmp/tga", "jpg", "png", "bmp", "jpeg", "tga");
            fileChooser.setFileFilter(filter);
            fileChooser.showOpenDialog(null);

            File file = fileChooser.getSelectedFile();
            if (file == null)
                return;
            srcpathmsg = file.getAbsolutePath();
        });
        panel.add(srcpath);

        width = new JTextField();
        width.setColumns(5);
        height = new JTextField();
        height.setColumns(5);
        panel.add(new JLabel("行:"));
        panel.add(width);
        panel.add(new JLabel("列:"));
        panel.add(height);

        JButton despath = new JButton("目标路径");
        despath.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(null);
            File file = fileChooser.getSelectedFile();
            if (file == null)
                return;
            despathmsg = file.getAbsolutePath();
        });
        panel.add(despath);

        JButton button = new JButton("切割");
        button.addActionListener(e -> {
            if (despathmsg != null && srcpathmsg != null) {
                if (cut(srcpathmsg, Integer.parseInt(width.getText()), Integer.parseInt(height.getText()), despathmsg))
                    JOptionPane.showMessageDialog(null, "切割已完成");
            } else {
                JOptionPane.showMessageDialog(null, "请选择路径");
                return;
            }
        });
        panel.add(button);
        add(panel, BorderLayout.CENTER);
        pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        //   cut("D:/图标图片库/Icon.png",5,3,"src/image/Icon/");
        // ImageUtil imageUtil=new ImageUtil();
        cut("D:\\图标图片库\\Icon4.png", 7, 5, "D:\\图标图片库\\icon4\\");
      /*  ImageIcon icon=  new ImageIcon("src/image/bkimage/default.jpg");
        icon=CutCircleImage(icon,100);
        JLabel label=new JLabel(icon);
        JFrame frame=new JFrame("1");
        frame.setSize(1000,800);
        frame.add(label,BorderLayout.CENTER);
        frame.setVisible(true);*/

    }
}
