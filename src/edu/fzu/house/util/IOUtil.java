package edu.fzu.house.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
    public static void Close(Closeable... con) {
        for (Closeable closeable : con) {
            try {
                if (closeable != null)
                    closeable.close();
                ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //根据字符串 以&为分割 利用IO流读取所有路径的图片信息并返回 如果没用则返回默认字符串
    public static List<byte[]> readStrImage(String src) {
        ByteArrayOutputStream bos = null;
        BufferedInputStream bis = null;
        List<byte[]> list = null;
        if (src == null)
            return readStrImage("src/image/bkimage/default.jpg&");
        int length = src.indexOf("&");
        if (length == -1)
            return null;
        String image_src = src.substring(0, length);
        while (image_src != null && !src.equals("")) {
            //读
            try {
                bos = new ByteArrayOutputStream();
                bis = new BufferedInputStream(new FileInputStream(new File(image_src)));
                int len = -1;
                byte date[] = new byte[1024 * 1024];
                //开始读数据
                while ((len = bis.read(date)) != -1) {
                    bos.write(date, 0, len);
                }
                bos.flush();
                if (list == null)
                    list = new ArrayList<>();
                list.add(bos.toByteArray());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtil.Close(bos, bis);
            }
            //字符串赋值
            if (length == src.length() - 1)
                break;
            src = src.substring(length + 1, src.length());
            length = src.indexOf("&");
            image_src = src.substring(0, length);
        }
        return list;
    }

    public static void main(String[] args) {
      /*  String src="src/image/Icon/login/close.png&src/image/Icon/login/login.png&";
        List<byte[]> bytes=readStrImage(src);
        for (byte[] aByte : bytes) {
            System.out.println(aByte.length);
        }*/
    }
}
