package com.en.controller;

import com.en.config.ProjectUrlConfig;
import com.en.constant.CookieConstant;
import com.en.constant.RedisConstant;
import com.en.dataobject.SellerInfo;
import com.en.enums.ResultEnum;
import com.en.service.SellerService;
import com.en.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by En on 2018/4/22.
 */
@Slf4j
@Controller
@RequestMapping("/seller/user")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        if (StringUtils.isEmpty(openid)) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        SellerInfo sellerInfo = sellerService.findByOpenid(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),
                openid, RedisConstant.TIMEOUT, TimeUnit.SECONDS);
        CookieUtil.saveCookie(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response,
                        HttpServletRequest request,
                        Map<String, Object> map) {
        Cookie cookie = CookieUtil.getCookie(request, CookieConstant.TOKEN);
        if (cookie != null) {
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            CookieUtil.saveCookie(response, CookieConstant.TOKEN, null, 0);
        }
        map.put("url", "/seller/order/list");
        map.put("msg",  ResultEnum.LOGOUT_SUCCESS.getMsg());
        return new ModelAndView("common/success", map);
    }

}
