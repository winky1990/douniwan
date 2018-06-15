package com.winky.expand.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件读写工具类
 *
 * @author bear
 */
public class FileUtils {

    private static final Singleton<FileUtils> SINGLETON = new Singleton<FileUtils>() {
        @Override
        protected FileUtils create() {
            return new FileUtils();
        }
    };

    public static FileUtils getInstance() {
        return SINGLETON.get();
    }

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * 得到手机的缓存目录
     *
     * @return
     */
    public String getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir.getAbsolutePath();
            }
        }
        File cacheDir = context.getCacheDir();
        return cacheDir.getAbsolutePath();
    }

    /**
     * 复制assets/skin目录下的皮肤文件到指定地址，注意需要带文件名
     *
     * @param context   the context
     * @param assetPath 皮肤地址
     * @param toPath    指定地址
     * @return
     */
    public String copyAssetsToDir(Context context, String assetPath, String toPath) {
        try {
            InputStream is = context.getAssets().open(assetPath);
            File fileDir = new File(toPath);
            if (!fileDir.exists()) {
                fileDir.createNewFile();
            }
            OutputStream os = new FileOutputStream(toPath);
            int byteCount;
            byte[] bytes = new byte[1024];

            while ((byteCount = is.read(bytes)) != -1) {
                os.write(bytes, 0, byteCount);
            }
            os.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return toPath;
    }

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path 文件路径
     * @return
     */
    public String ifFileNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                Logger.getInstance().e(e.getMessage());
            }
        }
        return path;
    }

    /**
     * 如果文件不存在，就创建文件
     *
     * @param path 文件路径
     * @return
     */
    public String ifDirNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                Logger.getInstance().e(e.getMessage());
            }
        }
        return path;
    }

    /**
     * 向文件中写入数据
     *
     * @param filePath 目标文件全路径
     * @param data     要写入的数据
     * @param append   是否追加原文件
     * @return true表示写入成功 false表示写入失败
     */
    public boolean writeBytes(String filePath, byte[] data, boolean append) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath, append);
            fos.write(data);
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * 从文件中读取数据
     *
     * @param file
     * @return
     */
    public byte[] readBytes(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] buffer = new byte[len];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    /**
     * 向文件中写入字符串String类型的内容
     *
     * @param file    文件路径
     * @param content 文件内容
     * @param append  是否追加
     */
    public void writeString(String file, String content, boolean append) {
        try {
            byte[] data = content.getBytes("UTF-8");
            writeBytes(file, data, append);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * 从文件中读取数据，返回类型是字符串String类型
     *
     * @param file    文件路径
     * @param charset 读取文件时使用的字符集，如utf-8、GBK等
     * @return
     */
    public String readString(String file, String charset) {
        byte[] data = readBytes(file);
        String ret = null;

        try {
            ret = new String(data, charset);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

}
