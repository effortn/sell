package com.en.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by En on 2018/4/14.
 */
@Data
@Component
@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {

    /**
     * 微信公众平台授权url
     */
    private String wechatMpAuthorize;

    /**
     * 微信开放平台授权url
     */
    private String wechatOpenAuthorize;

    /**
     * 点餐系统
     */
    private String sell;

    /**
     * 错误页面
     */
    private String error;

    /**
     * 成功页面
     */
    private String success;
}
