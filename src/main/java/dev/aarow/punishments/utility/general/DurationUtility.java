package dev.aarow.punishments.utility.general;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DurationUtility {

    public static long getDurationFromString(String input) {
        final long MILLISECONDS_IN_SECOND = 1000;
        final long MILLISECONDS_IN_MINUTE = 60 * MILLISECONDS_IN_SECOND;
        final long MILLISECONDS_IN_HOUR = 60 * MILLISECONDS_IN_MINUTE;
        final long MILLISECONDS_IN_DAY = 24 * MILLISECONDS_IN_HOUR;
        final long MILLISECONDS_IN_WEEK = 7 * MILLISECONDS_IN_DAY;
        final long MILLISECONDS_IN_MONTH = 30 * MILLISECONDS_IN_DAY; // Approximate month duration
        final long MILLISECONDS_IN_YEAR = 365 * MILLISECONDS_IN_DAY; // Approximate year duration

        Pattern pattern = Pattern.compile("(\\d+y)?(\\d+mm)?(\\d+w)?(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?");
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            return -1;
        }

        long totalMilliseconds = 0;

        if (matcher.group(1) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(1).replace("y", "")) * MILLISECONDS_IN_YEAR;
        }
        if (matcher.group(2) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(2).replace("mm", "")) * MILLISECONDS_IN_MONTH;
        }
        if (matcher.group(3) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(3).replace("w", "")) * MILLISECONDS_IN_WEEK;
        }
        if (matcher.group(4) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(4).replace("d", "")) * MILLISECONDS_IN_DAY;
        }
        if (matcher.group(5) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(5).replace("h", "")) * MILLISECONDS_IN_HOUR;
        }
        if (matcher.group(6) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(6).replace("m", "")) * MILLISECONDS_IN_MINUTE;
        }
        if (matcher.group(7) != null) {
            totalMilliseconds += Integer.parseInt(matcher.group(7).replace("s", "")) * MILLISECONDS_IN_SECOND;
        }

        return totalMilliseconds;
    }

    public static String getDurationFromLong(long durationMillis) {
        final long MILLISECONDS_IN_SECOND = 1000;
        final long MILLISECONDS_IN_MINUTE = 60 * MILLISECONDS_IN_SECOND;
        final long MILLISECONDS_IN_HOUR = 60 * MILLISECONDS_IN_MINUTE;
        final long MILLISECONDS_IN_DAY = 24 * MILLISECONDS_IN_HOUR;
        final long MILLISECONDS_IN_WEEK = 7 * MILLISECONDS_IN_DAY;
        final long MILLISECONDS_IN_MONTH = 30 * MILLISECONDS_IN_DAY; // Approximate month duration
        final long MILLISECONDS_IN_YEAR = 365 * MILLISECONDS_IN_DAY; // Approximate year duration

        StringBuilder result = new StringBuilder();

        long years = durationMillis / MILLISECONDS_IN_YEAR;
        if (years > 0) {
            result.append(years).append("y");
            durationMillis %= MILLISECONDS_IN_YEAR;
        }

        long months = durationMillis / MILLISECONDS_IN_MONTH;
        if (months > 0) {
            result.append(months).append("mm");
            durationMillis %= MILLISECONDS_IN_MONTH;
        }

        long weeks = durationMillis / MILLISECONDS_IN_WEEK;
        if (weeks > 0) {
            result.append(weeks).append("w");
            durationMillis %= MILLISECONDS_IN_WEEK;
        }

        long days = durationMillis / MILLISECONDS_IN_DAY;
        if (days > 0) {
            result.append(days).append("d");
            durationMillis %= MILLISECONDS_IN_DAY;
        }

        long hours = durationMillis / MILLISECONDS_IN_HOUR;
        if (hours > 0) {
            result.append(hours).append("h");
            durationMillis %= MILLISECONDS_IN_HOUR;
        }

        long minutes = durationMillis / MILLISECONDS_IN_MINUTE;
        if (minutes > 0) {
            result.append(minutes).append("m");
            durationMillis %= MILLISECONDS_IN_MINUTE;
        }

        long seconds = durationMillis / MILLISECONDS_IN_SECOND;
        if (seconds > 0) {
            result.append(seconds).append("s");
        }

        if (result.length() == 0) {
            return "0s";
        }

        return result.toString();
    }

    public static String getExpireDate(long happenedAt, long duration){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        return simpleDateFormat.format(new Date((happenedAt + duration)));
    }

    public static String getExpiresIn(long happenedAt, long duration){
        return getDurationFromLong((happenedAt + duration) - System.currentTimeMillis());
    }
}
