package nl.jongerden.lumenscraper.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by jan on 11 February 2016.
 */
public class DateUtil {

    /**
     * Convert "vr 19 feb" to "19-02-2016"
     */
    public static String convertLumenDate(String lumenDate, LocalDate today) {

        int day = Integer.valueOf(lumenDate.substring(3, 5).trim());
        int month = getMonth(lumenDate.substring(5));

        int year = today.getYear();

        if (month < today.getMonthValue()) {
            // the date appears to be in the next year
            year++;
        }

        LocalDate date = LocalDate.of(year, month, day);
        return date.format(DateTimeFormatter.ofPattern("dd-MM-uuuu"));
    }

    private static int getMonth(String month) {
        // TODO: these values are mostly speculative, may fail when
        // these months start occurring in the data
        month = month.trim().toLowerCase();
        if ("jan".equals(month)) {
            return 1;
        } else if ("feb".equals(month)) {
            return 2;
        } else if ("mrt".equals(month)) {
            return 3;
        } else if ("apr".equals(month)) {
            return 4;
        } else if ("mei".equals(month)) {
            return 5;
        } else if ("jun".equals(month)) {
            return 6;
        } else if ("jul".equals(month)) {
            return 7;
        } else if ("aug".equals(month)) {
            return 8;
        } else if ("sep".equals(month)) {
            return 9;
        } else if ("okt".equals(month)) {
            return 10;
        } else if ("nov".equals(month)) {
            return 11;
        } else if ("dec".equals(month)) {
            return 12;
        }
        return 0;
    }


}
