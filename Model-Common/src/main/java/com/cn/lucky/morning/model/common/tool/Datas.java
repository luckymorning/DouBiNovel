package com.cn.lucky.morning.model.common.tool;

import java.text.DecimalFormat;
import java.util.UUID;

public class Datas {

    private static final DecimalFormat df = new DecimalFormat("###.##");
    public static String floatFormat(float value){
        return df.format(value);
    }

    public static <T> T getDef(T v, T d) {
        return v == null ? d : v;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
