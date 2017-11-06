package com.uci.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by junm5 on 2/22/17.
 */
public class JsonUtils {
    private final static Gson gson = new Gson();

    public static String toJson(Object obj) {
        if (obj == null) {
            return "";
        }
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> clas) {
        return gson.fromJson(json, clas);
    }

    public static <T> T fromJson(String json, TypeToken<T> type) {
        return gson.fromJson(json, type.getType());
    }
}