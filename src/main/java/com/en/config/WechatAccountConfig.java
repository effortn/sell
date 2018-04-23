package com.en.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by En on 2018/4/14.
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatAccountConfig {

    /**
     * 公众平台id
     */
    private String mpAppId;

    /**
     * 公众平台密钥
     */
    private String mpAppSecret;

    /**
     * 开放平台id
     */
    private String openAppId;

    /**
     * 开放平台密钥
     */
    private String oepenAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户证书
     */
    private String mchKey;

    /**
     *  证书地址
     */
    private String keyPath;

    /**
     *  支付结果异步通知地址
     */
    private String notifyUrl;

}
