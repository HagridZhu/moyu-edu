package com.moyulab.cn.exam.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date addMinute(Date date, int minute){
        if (date == null) {
            return null;
        }
        Calendar cld = Calendar.getInstance();
        cld.setTime(date);
        cld.add(Calendar.MINUTE, minute);
        return cld.getTime();
    }

}
