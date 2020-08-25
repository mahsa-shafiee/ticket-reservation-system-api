package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date formatDate(Date date, int daysToAddOrSubtract) {
        String dateStr = addOrSubtractDays(date, daysToAddOrSubtract);
        LocalDate localDate = LocalDate.parse(dateStr);
        return java.sql.Date.valueOf(localDate);
    }

    private static String addOrSubtractDays(Date date, int numberOfDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, numberOfDays);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date convertStrToDate(String dateStr) throws ParseException {
        return simpleDateFormat.parse(dateStr);
    }
}
