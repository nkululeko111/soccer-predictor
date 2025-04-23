package org.soccer.smartbet.service.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final DateTimeFormatter UI_DATE_FORMAT = 
        DateTimeFormatter.ofPattern("dd MMM yyyy");
    public static final DateTimeFormatter UI_DATETIME_FORMAT = 
        DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");

    public static String formatForUI(LocalDate date) {
        return date.format(UI_DATE_FORMAT);
    }

    public static String formatForUI(LocalDateTime dateTime) {
        return dateTime.format(UI_DATETIME_FORMAT);
    }

    public static boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }

    public static boolean isUpcoming(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }
}