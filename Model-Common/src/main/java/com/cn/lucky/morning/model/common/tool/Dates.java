package com.cn.lucky.morning.model.common.tool;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dates {

    public static Timestamp curr() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String format(Date date, String fmt) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    @SuppressWarnings("deprecation")
    public static Date yearPlus(Date date, int count) {
        date.setYear(date.getYear() + count);
        return date;
    }

    public static Date parseDate(String source, String fmt) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.parse(source);
    }
}
