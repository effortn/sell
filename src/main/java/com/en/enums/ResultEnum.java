package com.en.enums;

import lombok.Getter;

/**
 * 异常枚举
 * Created by En on 2018/4/7.
 */
@Getter
public enum ResultEnum implements CodeEnum {

    SUCCESS(0, "成功"),

    UNKNOW_ERROR(1, "未知错误"),

    PARAM_ERROR(2, "参数错误"),

    PRODUCT_NOT_EXIT(10, "商品不存在"),

    CART_IS_NULL(11, "购物列表为空"),

    PRODUCT_STOCK_ERROR(12, "商品库存不足"),

    BUYER_INFO_ERROR(13, "买家信息错误"),

    ORDER_NOT_EXIST(14, "订单不存在"),

    ORDERDETAIL_NOT_EXIT(15, "订单详情不存在"),

    ORDER_STATUS_ERROR(16, "订单状态错误"),

    ORDER_UPDATE_ERROR(17, "订单更新错误"),

    PAY_STATUS_ERROR(18, "支付状态错误"),

    ORDER_OWNER_ERROR(19, "该订单不属于当前用户"),

    WECHAT_MP_ERROR(20, "微信公众号错误"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付金额校验错误"),

    PRODUCT_STATUS_ERROR(22, "商品状态错误"),

    LOGIN_FAIL(23, "登录失败"),

    LOGOUT_SUCCESS(24, "登出成功")
    ;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
