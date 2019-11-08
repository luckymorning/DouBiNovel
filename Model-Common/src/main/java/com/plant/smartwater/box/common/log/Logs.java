package com.plant.smartwater.box.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logs {

    /**
     * 获取Logger
     * 
     * @return
     */
    public static final Logger get() {
        String clsName = Thread.currentThread().getStackTrace()[2].getClassName();
        return LoggerFactory.getLogger(clsName);
    }

    public static final Logger get(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }

    /**
     * 编写Logger 参数
     * 
     * @param obj
     * @return
     */
    public static final Object[] arg(Object... obj) {
        return obj;
    }

}
