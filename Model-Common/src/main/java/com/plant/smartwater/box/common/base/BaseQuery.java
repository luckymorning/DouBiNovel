package com.plant.smartwater.box.common.base;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

public class BaseQuery {

    /**
     * 当前页
     */
    private int current = 1;

    /**
     * 每页数量
     */
    private int size = 20;

    /**
     * 参数
     */
    private Map<String, Object> param;

    public int getCurrent() {
        return current - 1;
    }

    public void setCurrent(int current) {
        if(current < 1){
            current = 1;
        }
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
    
    public BaseQuery set(String key,Object obj){
        if (MapUtils.isEmpty(param)) {
            param = Maps.newHashMap();
        }
        param.put(key, obj);
        return this;
    }
    
    public Object get(String key) {
        if (MapUtils.isEmpty(param)) {
            return null;
        }
        return param.get(key);
    }
    
    public String getString(String key){
        return get(key) == null ? null : get(key).toString();
    }
    
    public Integer getInt(String key){
        if (MapUtils.isEmpty(param)) {
            return null;
        }
        String val = get(key) == null ? "0" : get(key).toString();
        if(StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)){
            return Integer.valueOf(val);
        }
        return null;
    }
    
    public Long getLong(String key){
        if (MapUtils.isEmpty(param)) {
            return null;
        }
        String val = get(key) == null ? "0" : get(key).toString();
        /*if(StringUtils.isNotBlank(val) && StringUtils.isNumeric(val)){
            return Long.valueOf(val);
        }*/
        try{
        	return Long.valueOf(val);
        } catch(Exception e){
        	return null;
        }
    }
    
    public boolean isNull(String key){
        return get(key) == null;
    }
    
    public boolean isNotNull(String key){
        return !isNull(key);
    }
    
    public boolean isEmpty(String key){
        return StringUtils.isEmpty(getString(key));
    }
    
    public boolean isNotEmpty(String key){
        return !isEmpty(key);
    }

}
