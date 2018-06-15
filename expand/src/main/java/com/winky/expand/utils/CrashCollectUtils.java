package com.winky.expand.utils;

import android.content.pm.PackageInfo;
import android.os.Build;

import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashCollectUtils implements Thread.UncaughtExceptionHandler {

    private static final Singleton<CrashCollectUtils> SINGLETON = new Singleton<CrashCollectUtils>() {
        @Override
        protected CrashCollectUtils create() {
            return new CrashCollectUtils();
        }
    };

    public static CrashCollectUtils getInstance() {
        return SINGLETON.get();
    }

    private CrashCollectUtils() {
        fileUtils = FileUtils.getInstance();
        filePath = fileUtils.ifDirNotExist(fileUtils.getCacheDir() + File.separator + "crash" + File.separator);
        //获取系统默认的UncaughtException处理器
        defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;
    private String filePath;
    //用于格式化日期,作为日志文件名的一部分
    private SimpleDateFormat format = new SimpleDateFormat("yy_MM_dd");

    private FileUtils fileUtils;
    private static final Object object = new Object();

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        synchronized (object) {
            //判断是否在主线程
//            if (thread.getId() == Looper.getMainLooper().getThread().getId()) {
//
//            }
            //收集手机应用版本等信息
            PackageInfo packageInfo = SysUtils.getInstance().getPackageInfo();
            JSONObject jsonObject = new JSONObject();
            if (packageInfo != null) {
                jsonObject.put("versionName", packageInfo.versionName == null ? "null" : packageInfo.versionName);
                jsonObject.put("versionCode", String.valueOf(packageInfo.versionCode));
            }
            jsonObject.put("systemVersion", Build.VERSION.RELEASE);
            jsonObject.put("model", Build.MANUFACTURER + " " + Build.MODEL);
            //收集错误日志
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            printWriter.close();
            jsonObject.put("content", writer.toString());
            //写日志
            String fileName = format.format(new Date());
            fileUtils.writeString(fileUtils.ifFileNotExist(filePath + fileName + ".txt"), jsonObject.toJSONString() + "\r\n\n", true);
            if (defaultExceptionHandler != null) {
                //如果用户没有处理则让系统默认的异常处理器来处理
                defaultExceptionHandler.uncaughtException(thread, throwable);
            } else {
                //退出程序
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        }
    }
}
