package com.en.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情
 * Created by En on 2018/4/6.
 */
@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;

    /** 商品名称 */
    @JsonProperty("name")
    private String productName;

    /** 商品单价 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 商品描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 商品小图 */
    @JsonProperty("icon")
    private String productIcon;

}
