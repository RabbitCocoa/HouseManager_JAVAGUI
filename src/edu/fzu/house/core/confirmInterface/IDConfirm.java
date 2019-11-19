package edu.fzu.house.core.confirmInterface;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDConfirm implements Confirm {
    @Override
    public boolean confirm(Object msg) {
        String id = (String) msg;
        //十八位身份证正则表达式
        Pattern p = Pattern.compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
        //十五位身份证
        Pattern p2 = Pattern.compile("^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$");
        Matcher m = p.matcher(id);
        Matcher m2 = p2.matcher(id);
        if (!m.matches()) {
            if (!m2.matches()) {
                JOptionPane.showMessageDialog(null, "身份证号有误");
                return false;
            }

        }

        return true;
    }
}
