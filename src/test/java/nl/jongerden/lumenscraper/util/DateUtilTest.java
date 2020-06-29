package nl.jongerden.lumenscraper.util;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * Created by jan on 11 February 2016.
 */
public class DateUtilTest {
    private int currentYear = LocalDate.now().getYear();

    @Test
    public void testConvert() {
        LocalDate today = LocalDate.now();
        assertEquals("31-12-" + currentYear, DateUtil.convertLumenDate("vr 31 dec", today));
    }

    @Test
    public void testConvertNextYear() {
        // when today's month is greater than the date to be converted,
        // it will be in the next year
        LocalDate today = LocalDate.of(1999, 12, 31);
        assertEquals("01-01-2000", DateUtil.convertLumenDate("ma 1 jan", today));
    }
}
