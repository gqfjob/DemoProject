package com.andaily.infrastructure;

import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

/**
 * @author Shengzhao Li
 */
public class DateUtilsTest {

    @Test
    public void isToday() throws Exception {
        boolean today = DateUtils.isToday(DateUtils.now());
        assertTrue(today);

        today = DateUtils.isToday(DateUtils.getDate("2013-03-09"));
        assertFalse(today);
    }

    @Test
    public void isDate() throws Exception {
        boolean isDate = DateUtils.isDate(null);
        assertFalse(isDate);

        isDate = DateUtils.isDate("2013-09-11");
        assertTrue(isDate);

        isDate = DateUtils.isDate("20130911");
        assertFalse(isDate);
    }

    /**
     * OOps!  what happened, waiting................
     */
    @Test(enabled = false)
    public void periodAsDays() {
        Date start = DateUtils.now();
        Date end = DateUtils.now();

        long days = DateUtils.periodAsDays(start, end);
        System.out.println("Period: " + days);
        assertEquals(days, 0);

        start = DateUtils.getDate("2013-03-01 12:23:23", DateUtils.DEFAULT_DATE_TIME_FORMAT);
        end = DateUtils.getDate("2013-03-11 16:00:11", DateUtils.DEFAULT_DATE_TIME_FORMAT);
        System.out.println("Start [" + DateUtils.toDateTime(start) + "], End [" + DateUtils.toDateTime(end) + "]");

        days = DateUtils.periodAsDays(start, end);
        System.out.println("Period: " + days);
        assertEquals(days, 10);
    }

    @Test(enabled = true)
    public void periodAsSeconds() {
        Date start = DateUtils.now();
        Date end = DateUtils.now();

        long days = DateUtils.periodAsSeconds(start, end);
        assertEquals(days, 0);

        //case 2
        start = DateUtils.getDate("2013-03-01 12:23:23", DateUtils.DEFAULT_DATE_TIME_FORMAT);
        end = DateUtils.getDate("2013-03-01 12:24:23", DateUtils.DEFAULT_DATE_TIME_FORMAT);

        days = DateUtils.periodAsSeconds(start, end);
        assertEquals(days, 60);

        //case 3
        start = DateUtils.getDate("2013-03-01 12:23:23", DateUtils.DEFAULT_DATE_TIME_FORMAT);
        end = DateUtils.getDate("2013-03-01 13:24:20", DateUtils.DEFAULT_DATE_TIME_FORMAT);

        days = DateUtils.periodAsSeconds(start, end);
        assertEquals(days, 3600 + 57);
    }


    @Test(enabled = true)
    public void secondsToText() {

        long seconds = -34;
        String text = DateUtils.secondsToText(seconds);
        assertEquals(text, "-1");

        seconds = 34;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "34s");

        seconds = 89;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "1m 29s");

        seconds = 134;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "2m 14s");

        seconds = 3609;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "1h 0m 9s");

        seconds = 3699;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "1h 1m 39s");

        seconds = 86400L;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "1d 0h 0m 0s");

        seconds = 86477L;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "1d 0h 1m 17s");

        seconds = 2592008L;
        text = DateUtils.secondsToText(seconds);
        assertEquals(text, "1M 0d 0h 0m 8s");

    }
}