package com.en.exception;

import com.en.enums.ResultEnum;
import lombok.Data;

/**
 * 项目异常类
 * Created by En on 2018/4/7.
 */
@Data
public class SellException extends RuntimeException {

    /** 错误码. */
    private Integer code;

    public SellException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SellException(ResultEnum sellExceptionEnum) {
        super(sellExceptionEnum.getMsg());
        this.code = sellExceptionEnum.getCode();
    }

}
