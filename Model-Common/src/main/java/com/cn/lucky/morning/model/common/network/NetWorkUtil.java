package com.cn.lucky.morning.model.common.network;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetWorkUtil {
    private static final int CONNECT_TIMEOUT = 1;
    private static final TimeUnit timeUnit = TimeUnit.SECONDS;
    public static OkHttpClient client;


    /**
     * 异步调用get方法
     * @param url 链接地址
     * @param headers 请求头
     * @param isUseStaticClient 是否使用静态变量对象访问
     * @param callback 回调函数
     * @return
     */
    public static Call get(String url,Headers headers, boolean isUseStaticClient, Callback callback){
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if (headers!=null){
            requestBuilder.headers(headers);
        }
        Request request = requestBuilder.build();
        if(isUseStaticClient){
            if(client == null){
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
                client = builder.build();
            }
            Call call=client.newCall(request);
            call.enqueue(callback);
            return call;
        }else{

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
            OkHttpClient okHttpClient = builder.build();;
            Call call=okHttpClient.newCall(request);
            call.enqueue(callback);
            return call;
        }
    }

    /**
     * 异步调用post方法
     * @param url 链接地址
     * @param params 请求参数
     * @param headers 请求头
     * @param isUseStaticClient 是否使用静态变量对象访问
     * @param callback 回调函数
     * @return
     */
    public static Call post(String url, Map<String,String> params, Headers headers, boolean isUseStaticClient, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();

        if(params!=null && !params.isEmpty()){
            for (Map.Entry<String,String> entry:params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }

        RequestBody post = builder.build();

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if (headers!=null){
            requestBuilder.headers(headers);
        }
        requestBuilder.post(post);
        Request request = requestBuilder.build();

        if(isUseStaticClient){
            if(client == null){
                OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
                clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
                client = clientbuilder.build();
            }
            Call call=client.newCall(request);
            call.enqueue(callback);
            return call;
        }else{
            OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
            clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
            OkHttpClient okHttpClient = clientbuilder.build();
            Call call=okHttpClient.newCall(request);
            call.enqueue(callback);
            return call;
        }
    }

    /**
     * 同步调用get方法
     * @param url 链接地址
     * @param headers 请求头
     * @param isUseStaticClient 是否使用静态变量对象访问
     * @return
     * @throws IOException
     */
    public static Response get(String url,Headers headers, boolean isUseStaticClient) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        if (headers!=null){
            requestBuilder.headers(headers);
        }
        requestBuilder.url(url);
        Request request = requestBuilder.build();
        if(isUseStaticClient){
            if(client == null){
                OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
                clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
                client = clientbuilder.build();
            }
            Call call=client.newCall(request);
            Response response = call.execute();
            return response;
        }else{

            OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
            clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
            OkHttpClient okHttpClient = clientbuilder.build();
            Call call=okHttpClient.newCall(request);
            Response response = call.execute();
            return response;
        }
    }

    /**
     * 同步调用post方法
     * @param url 链接地址
     * @param params 请求参数
     * @param headers 请求头
     * @param isUseStaticClient 是否使用静态变量对象访问
     * @return
     * @throws IOException
     */
    public static Response post(String url, Map<String,String> params, Headers headers, boolean isUseStaticClient) throws IOException {
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

        if(isUseStaticClient){
            if(client == null){
                OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
                clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
                client = clientbuilder.build();
            }
            Call call=client.newCall(request);
            Response response = call.execute();
            return response;
        }else{
            OkHttpClient.Builder clientbuilder = new OkHttpClient.Builder();
            clientbuilder.connectTimeout(CONNECT_TIMEOUT, timeUnit);
            OkHttpClient okHttpClient  = clientbuilder.build();
            Call call=okHttpClient.newCall(request);
            Response response = call.execute();
            return response;
        }
    }
}
