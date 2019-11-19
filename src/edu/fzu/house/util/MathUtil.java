package edu.fzu.house.util;

import sun.applet.Main;

public class MathUtil {
    public static int getDetail(int num,int time)
    {
        //将num转化为num的倍数中最接近的
        int times=num/time;

        int max=times*(time+1);
        int min=time*times;
        if(Math.abs(max-num)>Math.abs(min-num))
        {
            return min;
        }
        else{
            return max;
        }

    }

    public static void main(String[] args) {
        System.out.println(getDetail(117,3));
    }
}
