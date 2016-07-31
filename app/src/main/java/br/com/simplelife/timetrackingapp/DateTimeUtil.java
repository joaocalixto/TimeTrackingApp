package br.com.simplelife.timetrackingapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jjcc on 30/07/16.
 */
public class DateTimeUtil {

    public static long calculateDateDiff(String maior, String menor) {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        long diffTemp = -1;

        try {
            d1 = sdf.parse(maior);
            d2 = sdf.parse(menor);

            diffTemp = Math.abs(d1.getTime() - d2.getTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffTemp;
    }

    public static String longToStringDate(long tempo){

       long diffSeconds = tempo / 1000 % 60;
       long diffMinutes = tempo / (60 * 1000) % 60;
       long diffHours = tempo / (60 * 60 * 1000) % 24;

        return diffHours + ":" + diffMinutes + ":" + diffSeconds;

    }

}
