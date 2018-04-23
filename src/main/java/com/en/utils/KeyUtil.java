package com.en.utils;

import java.util.Random;

/**
 * Created by En on 2018/4/7.
 */
public class KeyUtil {

    public static synchronized String getKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        String key = System.currentTimeMillis() + String.valueOf(number);
        return key;
    }

}
