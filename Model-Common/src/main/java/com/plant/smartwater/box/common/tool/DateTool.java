package com.plant.smartwater.box.common.tool;



import com.plant.smartwater.box.common.base.BaseQuery;

import java.text.ParseException;
import java.util.Date;

public class DateTool {
    
    public static Date[] getDateCondition(BaseQuery query){
        Date start = null;
        Date end = null;
        if (query.isNotEmpty("start")) {
            try {
                start = Dates.parseDate(query.getString("start"), "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                try {
                    start = Dates.parseDate(query.getString("start") + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (query.isNotEmpty("end")) {
            try {
                end = Dates.parseDate(query.getString("end"), "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                try {
                    end = Dates.parseDate(query.getString("end") + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        }
        Date[] ds = new Date[2];
        ds[0] = start;
        ds[1] = end;
        return ds;
    }

}
