package com.en.aspect;

import com.en.constant.CookieConstant;
import com.en.exception.SellerAuthorizeException;
import com.en.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by En on 2018/4/22.
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.en.controller.Seller*.*(..))" +
            "&& !execution(public * com.en.controller.SellerUserController.*(..))")
    public void verify(){

    }

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        String tokenValue = redisTemplate.opsForValue().get(cookie.getValue());
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
    }

}
