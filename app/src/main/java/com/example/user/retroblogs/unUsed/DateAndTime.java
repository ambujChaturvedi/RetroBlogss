package com.example.user.retroblogs.unUsed;


import java.text.DateFormat;
import java.util.Date;

import static java.text.DateFormat.getDateTimeInstance;

/**
 * Created by user on 19-02-2018.
 */

public class DateAndTime {

    public static String getTimeDate(long timeStamp){
        try{
            DateFormat dateFormat = getDateTimeInstance();
            Date netDate = (new Date(timeStamp));
            return dateFormat.format(netDate);
        } catch(Exception e) {
            return "date";
        }
    }
}
