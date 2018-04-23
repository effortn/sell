package com.en.form;

import com.en.enums.ProductStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by En on 2018/4/21.
 */
@Data
public class ProductForm {

    private String productId;

    /** 商品名称 */
    @NotNull(message = "商品名不能为空")
    private String productName;

    /** 商品单价 */
    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    /** 商品库存 */
    private Integer productStock;

    /** 商品描述 */
    private String productDescription;

    /** 商品小图 */
    @NotNull(message = "商品图片不能为空")
    private String productIcon;

    /** 类目编号 */
    @NotNull(message = "商品类目不能为空")
    private Integer categoryType;

    /** 商品状态,0正常1下架 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

}
