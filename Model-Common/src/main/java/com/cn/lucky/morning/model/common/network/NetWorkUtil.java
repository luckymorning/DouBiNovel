package com.cn.lucky.morning.model.common.network;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.cn.lucky.morning.model.common.log.Logs;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;

public class NetWorkUtil {
    private static final Logger logger = Logs.get();

    private static final int CONNECT_TIMEOUT = 1;
    private static final TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 同步调用get方法
     * @param url 链接地址
     * @return
     */
    public static Response get(String url) {
        return get(url,null);
    }

    /**
     * 同步调用get方法
     * @param url 链接地址
     * @param headers 请求头
     * @return
     */
    public static Response get(String url,Headers headers) {
        try {
            Request.Builder requestBuilder = new Request.Builder();
            if (headers!=null){
                requestBuilder.headers(headers);
            }
            requestBuilder.url(url);
            Request request = requestBuilder.build();
            OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
            clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
            OkHttpClient okHttpClient = clientbuilder.build();
            Call call=okHttpClient.newCall(request);
            Response response = call.execute();
            return response;
        }catch (Exception e){
            logger.error("GET请求【{}】出错",url,e);
            return null;
        }
    }

    /**
     * 同步调用post方法
     * @param url 链接地址
     * @param params 请求参数
     * @return
     */
    public static Response post(String url, Map<String,String> params) {
        return post(url, params,null);
    }

    /**
     * 同步调用post方法
     * @param url 链接地址
     * @param params 请求参数
     * @param headers 请求头
     * @return
     */
    public static Response post(String url, Map<String,String> params, Headers headers) {
        try {

            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String,String> entry:params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }

            RequestBody post = builder.build();
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(url);
            if (headers!=null){
                requestBuilder.headers(headers);
            }
            requestBuilder.post(post);
            Request request = requestBuilder.build();

            OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
            clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
            OkHttpClient okHttpClient  = clientbuilder.build();
            Call call=okHttpClient.newCall(request);
            Response response = call.execute();
            return response;
        }catch (Exception e){
            logger.error("POST请求【{}】出错，参数【{}】",url,params,e);
            return null;
        }
    }
}
