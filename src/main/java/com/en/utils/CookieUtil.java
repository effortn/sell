package com.en.utils;

import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by En on 2018/4/22.
 */
public class CookieUtil {

    public static Cookie getCookie(HttpServletRequest request,
                                    String token) {
        Map<String, Cookie> cookieMap = getCookieMap(request);
        return cookieMap.get(token);
    }

    public static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
        Map<String, Cookie> result = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                result.put(cookie.getName(), cookie);
            }
        }
        return result;
    }

    public static void saveCookie(HttpServletResponse response,
                                  String name,
                                  String value,
                                  int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}
