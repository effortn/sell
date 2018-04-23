package com.en.utils;

import com.en.VO.ResultVO;

/**
 * ResultVO工具类
 * Created by En on 2018/4/6.
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO result = new ResultVO();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO result = new ResultVO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
