package edu.fzu.house.core.confirmInterface;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmialConfirm implements Confirm {
    @Override
    public boolean confirm(Object msg) {
        Pattern p= Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        String email=(String)msg;
        Matcher m=p.matcher(email);
        if(m.matches())
            return true;
        else
        {
            JOptionPane.showMessageDialog(null,"邮箱有误");
            return false;
        }

    }
}
