package com.home.feedo.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DateUtils {

    public static Date addDay(Date date, int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static List<Date> getPeriod(Date startDate, Date endDate){
        List<Date> datesPeriod = new LinkedList<>();
        Date nextDate = startDate;
        while (nextDate.before(endDate)){
            datesPeriod.add(nextDate);
            nextDate = addDay(nextDate, 1);
        }
        datesPeriod.add(endDate);
        return datesPeriod;
    }

    public static Date roundDate (Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
}
