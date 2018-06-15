/*
    ShengDao Android Client, JsonMananger
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.winky.expand.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.TypeUtils;

import java.util.List;

/**
 * [JSON解析管理类]
 **/
public class JsonUtils {

    private static final Singleton<JsonUtils> SINGLETON = new Singleton<JsonUtils>() {
        @Override
        protected JsonUtils create() {
            return new JsonUtils();
        }
    };

    public static JsonUtils getInstance() {
        return SINGLETON.get();
    }

    private JsonUtils() {
        TypeUtils.compatibleWithJavaBean = true;
    }

    /**
     * 将json字符串转换成java对象
     *
     * @param json
     * @param cls
     * @return
     */
    public <T> T toBean(String json, Class<T> cls) {
        try {
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将json字符串转换成java List对象
     *
     * @param json
     * @param cls
     * @return
     */
    public <T> List<T> toList(String json, Class<T> cls) {
        try {
            return JSON.parseArray(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将bean对象转化成json字符串
     *
     * @param obj
     * @return
     */
    public String toJson(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
