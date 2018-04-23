package com.en.utils;

import org.springframework.util.DigestUtils;

/**
 * Created by En on 2018/4/21.
 */
public class MD5Util {

    private static final String SLAT = "LQWEROIUSDF!@sdf;l";

    private static String getMD5(long seckillId){
        String base = seckillId + "/" + SLAT;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

}
