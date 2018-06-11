package com.siziksu.bluetooth.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Class with some util methods for dates.
 */
public final class DatesUtils {

    private DatesUtils() {}

    /**
     * Formats current time in the format 24h 00:00:00.000.
     *
     * @return string formatted
     */
    public static String getTimeString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        return formatter.format(calendar.getTime());
    }
}
