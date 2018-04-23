package com.en.form;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

/**
 * Created by En on 2018/4/14.
 */
@Data
public class OrderForm {

    /**   买家姓名    */
    @NotNull(message = "买家姓名必填")
    private String name;

    /**  买家电话   */
    @NotNull(message = "买家电话必填")
    private String phone;

    /**  买家地址   */
    @NotNull(message = "买家地址必填")
    private String address;

    /**  买家微信openid   */
    @NotNull(message = "买家微信openid必填")
    private String openid;

    /**  购物车   */
    @NotNull(message = "购物车不能为空")
    private String items;

}
