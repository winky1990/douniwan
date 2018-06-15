package com.winky.expand.http;

import android.content.Context;


import com.winky.expand.utils.Singleton;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * 类描述：封装一个OkHttp3的获取类
 * Created by YSL on 2016/8/3 0003.
 */
public class HttpUtils {

    private static final Singleton<HttpUtils> SINGLETON = new Singleton<HttpUtils>() {
        @Override
        protected HttpUtils create() {
            return new HttpUtils();
        }
    };

    public static HttpUtils getInstance() {
        return SINGLETON.get();
    }

    //    /*    text/html ： HTML格式
//    text/plain ：纯文本格式
//    text/xml ：  XML格式
//    image/gif ：gif图片格式
//    image/jpeg ：jpg图片格式
//    image/png：png图片格式
//    以application开头的媒体格式类型：
//   application/xhtml+xml ：XHTML格式
//   application/xml     ： XML数据格式
//   application/atom+xml  ：Atom XML聚合格式
//   application/json    ： JSON数据格式
//   application/pdf       ：pdf格式
//   application/msword  ： Word文档格式
//   application/octet-stream ： 二进制流数据（如常见的文件下载）
//   application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
//   另外一种常见的媒体格式是上传文件之时使用的：
//
//    multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式*/

    private OkHttpClient okHttpClient;
    private CookieManager cookiesManager;


    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public void init(Context context) {
        try {
            File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "cache");
            Cache cache = new Cache(cacheDirectory, 10 * 1024 * 1024);
            cookiesManager = new CookieManager(context);

            SSLContext sslContext = SSLContext.getInstance("SSL");

            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());

            okHttpClient = new OkHttpClient.Builder()
                    //设置一个自动管理cookies的管理器
                    .cookieJar(cookiesManager)
                    //设置套接字工厂
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    //设置计算机验证
                    .hostnameVerifier(hostnameVerifier)
                    //添加日志拦截器
                    .addInterceptor(new LogIntercepter())
                    //添加网络连接器
//                    .addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(cache)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    private X509TrustManager trustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    };

}
