package edu.fzu.house.core.confirmInterface;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneConfirm implements Confirm {
    @Override
    public boolean confirm(Object msg) {
        String phonenum=(String)msg;
        Pattern p=Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
        Matcher m=p.matcher(phonenum);
        if(m.matches())
            return true;
        else{
            JOptionPane.showMessageDialog(null,"手机号有误");
            return false;
        }
    }
}
