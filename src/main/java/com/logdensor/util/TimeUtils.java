package com.logdensor.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parse(String input) {
        return LocalDateTime.parse(input, formatter);
    }

    public static String format(LocalDateTime dt) {
        return dt.format(formatter);
    }
}
