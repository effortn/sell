package com.en.utils;

import com.en.enums.CodeEnum;

import java.lang.reflect.Field;

/**
 * Created by En on 2018/4/16.
 */
public class EnumUtil {

    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

}
