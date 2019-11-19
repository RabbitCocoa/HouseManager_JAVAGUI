package edu.fzu.house.core.confirmInterface;

import javax.swing.*;

public class SexConfirm implements Confirm {
    @Override
    public boolean confirm(Object msg) {
        String sex = (String) msg;
        if (!sex.equals("男") && (!sex.equals("女"))) {
            JOptionPane.showMessageDialog(null, "性别有误");
            return false;
        }
        return true;
    }
}
