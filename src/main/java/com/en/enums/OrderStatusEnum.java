package com.en.enums;

import lombok.Getter;

/**
 * 订单状态枚举类
 * Created by En on 2018/4/6.
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {

    NEW(0, "新订单"),
    FINSH(1, "完结"),
    CANCLE(2, "取消"),
    ;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
