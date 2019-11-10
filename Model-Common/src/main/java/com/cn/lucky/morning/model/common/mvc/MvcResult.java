package com.cn.lucky.morning.model.common.mvc;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import java.util.Map;

public class MvcResult {

    public static final String MODEL_KEY = "MvcResult";

    private static final String DATA_KEY = "DATA_MODEL_KEY";

    private MvcResult(boolean success, int code, String message) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public static MvcResult create() {
        return new MvcResult(true, 200, null);
    }

    public static MvcResult create(boolean success) {
        return create().setSuccess(success);
    }

    public static MvcResult create(boolean success, String message) {
        return create().setSuccess(success).setMessage(message);
    }

    public static MvcResult createFail() {
        return create(false);
    }

    public static MvcResult createFail(int code) {
        return create(false).setCode(code);
    }

    public static MvcResult createFail(String message) {
        return create(false).setMessage(message);
    }

    public static MvcResult createFail(int code, String message) {
        return create(false).setCode(code).setMessage(message);
    }

    private boolean success;

    private int code;

    private String message;

    private Map<String, Object> values;

    public MvcResult addVal(String key, Object val) {
        if (MapUtils.isEmpty(values)) {
            initMap();
        }
        values.put(key, val);
        return this;
    }

    public MvcResult addAllVal(Map<String, Object> data) {
        if (MapUtils.isEmpty(values)) {
            initMap();
        }
        values.putAll(data);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getVal(String key) {
        if (MapUtils.isEmpty(values)) {
            initMap();
        }
        return (T) values.get(key);
    }

    private void initMap() {
        values = Maps.newHashMap();
    }

    public MvcResult setData(Object obj) {
        addVal(DATA_KEY, obj);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData() {
        return (T) getVal(DATA_KEY);
    }

    public boolean isSuccess() {
        return success;
    }

    public MvcResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MvcResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public MvcResult setCode(int code) {
        this.code = code;
        return this;
    }

    public Map<String, Object> getValues() {
        return values;
    }

}
