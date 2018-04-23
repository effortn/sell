package com.en.dto;

import lombok.Data;

/**
 * 订单商品详情
 * Created by En on 2018/4/7.
 */
@Data
public class CartDTO {

    /** 商品ID. */
    private String productId;

    /** 商品数量. */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
