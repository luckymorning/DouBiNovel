package com.cn.lucky.morning.model.service.impl;

import com.cn.lucky.morning.model.common.network.NetWorkUtil;
import com.cn.lucky.morning.model.common.tool.ByteUtils;
import com.cn.lucky.morning.model.service.NetWorkService;
import okhttp3.Headers;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lucky_morning
 */
@Service
public class NetWorkServiceImpl implements NetWorkService {
    @Override
    public String get(String url) {
        return get(url, null);
    }

    @Override
    public String post(String url, Map<String, String> params, String key) {
        return post(url, params, null, key);
    }

    @Override
    public String get(String url, Headers headers) {
        Response response = NetWorkUtil.get(url, headers);
        if (response != null) {
            try {
                byte[] bytes = NetWorkUtil.get(url, headers).body().bytes();
                String charset = ByteUtils.getEncoding(bytes);
                String responseStr = new String(bytes, charset);
                return responseStr;
            } catch (Exception e) {
                return null;
            }finally {
                if (response!=null){
                    response.close();
                }
            }
        }
        return null;
    }

    @Override
    public String post(String url, Map<String, String> params, Headers headers, String key) {
        Response response = NetWorkUtil.post(url, params, headers);
        if (response != null) {
            try {
                byte[] bytes = NetWorkUtil.get(url, headers).body().bytes();
                String charset = ByteUtils.getEncoding(bytes);
                String responseStr = new String(bytes, charset);
                return responseStr;
            } catch (Exception e) {
                return null;
            }finally {
                if (response!=null){
                    response.close();
                }
            }
        }
        return null;
    }
}
