package com.slidingtest.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;

import com.slidingtest.MainActivity;

public class Utils {
    
    public static String[] getCurrentDateAndTime() {
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = dateformat.format(date);
        return dateTime.split(" ");
    }
    
    public static void changeTitleText(Activity activity){
        MainActivity fca = (MainActivity) activity;
        fca.changeTitle("我的任务列表");
    }

}
