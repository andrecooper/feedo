package com.home.feedo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

//    public static void main(String[] args) {
//        Date startDate = new Date();
//        Date endDate = new Date();
//        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd:MM:yyyy");
//        try {
//            startDate = dateFormat1.parse("05:01:2016");
//            endDate = dateFormat1.parse("31:01:2016");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("START: " + startDate);
//        System.out.println("END: " + endDate);
//        final List<Date> period = getPeriod(startDate, endDate);
//        for (Date date : period) {
//            System.out.println(date);
//        }
//
//    }
}
