package com.plant.smartwater.box.common.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TableNameTool {
    public static SimpleDateFormat MONTHSDF = new SimpleDateFormat("yyyyMM");
    public static SimpleDateFormat YEARSDF = new SimpleDateFormat("yyyy");

    public static String getTableName(String table){
        return getTableName(table,new Date());
    }

    public static String getTableName(String table,Date date){
        if(table == null){
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append(table);
        switch (table){
            case "m_login_log":
                sBuffer.append(YEARSDF.format(date));
            case "m_operate_log":
                sBuffer.append(YEARSDF.format(date));
            case "m_run_record":
                sBuffer.append(MONTHSDF.format(date));
                break;
            default:
                ;
        }
        return sBuffer.toString();
    }
}
