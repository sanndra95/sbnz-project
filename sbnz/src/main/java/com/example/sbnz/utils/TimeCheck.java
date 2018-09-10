package com.example.sbnz.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeCheck {

    public Date last60Days() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -60);
        Date retVal = cal.getTime();
        return retVal;
    }

    public Date last6Months() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.MONTH, -6);
        Date retVal = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String s = sdf.format(retVal);
        System.err.println(s);
        return retVal;
    }

    public Date last14Days() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -14);
        Date retVal = cal.getTime();
        return retVal;
    }

    public Date last21Day() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -21);
        Date retVal = cal.getTime();
        return retVal;
    }

    public Date last2Years() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.YEAR, -2);
        Date retVal = cal.getTime();
        return retVal;
    }

    public Date last12Months() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.MONTH, -12);
        Date retVal = cal.getTime();
        return retVal;
    }
}
