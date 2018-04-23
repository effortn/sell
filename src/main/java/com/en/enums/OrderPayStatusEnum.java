package com.en.enums;

import lombok.Getter;

/**
 * 订单支付状态枚举类
 * Created by En on 2018/4/6.
 */
@Getter
public enum  OrderPayStatusEnum implements CodeEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    OrderPayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
