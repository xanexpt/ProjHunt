package com.bold.projhunt.utils;

import android.util.Log;

import com.bold.projhunt.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by bruno.marques on 20/07/2015.
 */
public class Utils {

    public static String getDayDiference(String selectedDate){
        int days = -1;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_FORMAT_TYPE);
        final String currentDate = df.format(c.getTime());

        SimpleDateFormat myFormat = new SimpleDateFormat(Constants.DATE_FORMAT_TYPE);

        try {
            Date date1 = myFormat.parse(selectedDate);
            Date date2 = myFormat.parse(currentDate);
            long diff = date2.getTime() - date1.getTime();
            days = (int)TimeUnit.MILLISECONDS.toDays(diff);

        } catch (ParseException e) {
            e.printStackTrace();

        }

        return String.valueOf(days);
    }

}
