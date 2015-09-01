package com.andaily.infrastructure;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Shengzhao Li
 */
public abstract class DateUtils {

    /**
     * Default time format :  yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Time format :  yyyy-MM-dd HH:mm
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT = "HH:mm";

    /**
     * Default date format
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     * Default month format
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    /**
     * Default day format
     */
    public static final String DAY_FORMAT = "dd";

    public static final long MILLISECOND_UNIT = 1000L;

    public static final long DAY_MILLISECONDS = 86400000L;

    //Date pattern,  demo:  2013-09-11
    public static final String DATE_PATTERN = "^[0-9]{4}\\-[0-9]{2}\\-[0-9]{2}$";

    protected DateUtils() {
    }

    public static boolean isDate(String dateAsText) {
        return StringUtils.isNotEmpty(dateAsText) && dateAsText.matches(DATE_PATTERN);
    }

    public static Date now() {
        return new Date();
    }

    public static String toDateText(Date date) {
        return toDateText(date, DATE_FORMAT);
    }

    public static String toDateText(Date date, String pattern) {
        if (date == null || pattern == null) {
            return null;
        }
        DateFormat dateFormat = createDateFormat(pattern);
        return dateFormat.format(date);
    }

    private static DateFormat createDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.SIMPLIFIED_CHINESE);
    }

    public static Date getDate(String dateText) {
        return getDate(dateText, DATE_FORMAT);
    }


    public static Date getDate(String dateText, String pattern) {
        DateFormat dateFormat = createDateFormat(pattern);
        try {
            return dateFormat.parse(dateText);
        } catch (ParseException e) {
            throw new IllegalStateException("Parse date from [" + dateText + "," + pattern + "] failed", e);
        }
    }

    public static String toDateTime(Date date) {
        return toDateText(date, DATE_TIME_FORMAT);
    }


    public static boolean isToday(Date date) {
        if (date == null) {
            return false;
        }
        String dateAsText = toDateText(date);
        String todayAsText = toDateText(now());
        return dateAsText.equals(todayAsText);
    }

    /**
     * Get tow dates period as days,
     * return -1 if the start or end is null
     *
     * @param start Start date
     * @param end   End date
     * @return Period as days or -1
     */
    public static long periodAsDays(final Date start, final Date end) {
        if (start == null || end == null) {
            return -1;
        }
        Date truncatedStart = org.apache.commons.lang.time.DateUtils.truncate(start, Calendar.DAY_OF_MONTH);
        Date truncatedEnd = org.apache.commons.lang.time.DateUtils.truncate(end, Calendar.DAY_OF_MONTH);

        long periodAsMilliSeconds = truncatedEnd.getTime() - truncatedStart.getTime();
        return periodAsMilliSeconds / DAY_MILLISECONDS;
    }

    /**
     * Get tow dates period as seconds,
     * return -1 if the start or end is null
     *
     * @param start Start date
     * @param end   End date
     * @return Period as days or -1
     */
    public static long periodAsSeconds(final Date start, final Date end) {
        if (start == null || end == null) {
            return -1;
        }
        Date truncatedStart = org.apache.commons.lang.time.DateUtils.truncate(start, Calendar.SECOND);
        Date truncatedEnd = org.apache.commons.lang.time.DateUtils.truncate(end, Calendar.SECOND);

        long periodAsMilliSeconds = truncatedEnd.getTime() - truncatedStart.getTime();
        return periodAsMilliSeconds / MILLISECOND_UNIT;
    }

    /*
    * Convert seconds to text: 1y 2M 1d 12h 23m 23s
    * If seconds < 0, will return "-1"
    * */
    public static String secondsToText(long seconds) {
        SecondsToTextHelper secondsToTextHelper = new SecondsToTextHelper(seconds);
        return secondsToTextHelper.toText();
    }


    /*
   * Help convert seconds to text
   * */
    private static class SecondsToTextHelper {

        // 365 days
        private static final long PER_YEAR_SECONDS = 31536000L;
        //30days
        private static final long PER_MONTH_SECONDS = 2592000L;

        private static final long PER_DAY_SECONDS = 86400L;

        private static final long PER_HOUR_SECONDS = 3600L;

        private static final long PER_MINUTE_SECONDS = 60L;

        private long seconds;

        public SecondsToTextHelper(long seconds) {
            this.seconds = seconds;
        }

        public String toText() {
            if (seconds < 0) {
                return "-1";
            }
            StringBuilder sb = new StringBuilder();
            toYear(sb);
            toMonth(sb);
            toDay(sb);

            toHour(sb);
            toMinute(sb);
            toSecond(sb);
            return sb.toString();
        }

        private void toSecond(StringBuilder sb) {
            final long tempSeconds = this.seconds % PER_MINUTE_SECONDS;
            sb.append(tempSeconds).append("s");
        }

        private void toMinute(StringBuilder sb) {
            if (seconds >= PER_MINUTE_SECONDS) {
                final long minuteSeconds = seconds % PER_HOUR_SECONDS;
                sb.append(minuteSeconds / PER_MINUTE_SECONDS).append("m ");
            }
        }

        private void toHour(StringBuilder sb) {
            if (seconds >= PER_HOUR_SECONDS) {
                final long hourSeconds = seconds % PER_DAY_SECONDS;
                sb.append(hourSeconds / PER_HOUR_SECONDS).append("h ");
            }
        }

        private void toDay(StringBuilder sb) {
            if (seconds >= PER_DAY_SECONDS) {
                final long daySeconds = seconds % PER_MONTH_SECONDS;
                sb.append(daySeconds / PER_DAY_SECONDS).append("d ");
            }
        }

        private void toMonth(StringBuilder sb) {
            if (seconds > PER_MONTH_SECONDS) {
                final long monthSeconds = seconds % PER_YEAR_SECONDS;
                sb.append(monthSeconds / PER_MONTH_SECONDS).append("M ");
            }
        }

        private void toYear(StringBuilder sb) {
            if (seconds >= PER_YEAR_SECONDS) {
                sb.append(seconds / PER_YEAR_SECONDS).append("y ");
            }
        }
    }
}