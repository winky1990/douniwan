package com.winky.expand.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogIntercepter implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        StringBuffer buffer = new StringBuffer();
        buffer.append("request:");
        buffer.append(request.url());
        buffer.append("\n");
        buffer.append("response:");
        buffer.append(response.toString());

//        Log.i(TAG, buffer.toString());
        return response;
    }
}
