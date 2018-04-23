package com.en.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by En on 2018/4/16.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

}
