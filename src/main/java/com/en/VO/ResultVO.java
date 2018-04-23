package com.en.VO;

import com.en.dataobject.ProductInfo;
import lombok.Data;

import java.util.List;

/**
 * http请求返回的最外层对象
 * Created by En on 2018/4/6.
 */
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 错误提示信息. */
    private String msg;

    /** 返回结果. */
    private T data;

}
