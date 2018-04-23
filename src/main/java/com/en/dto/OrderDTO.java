package com.en.dto;

import com.en.dataobject.OrderDetail;
import com.en.enums.OrderPayStatusEnum;
import com.en.enums.OrderStatusEnum;
import com.en.utils.EnumUtil;
import com.en.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by En on 2018/4/7.
 */
@Data
public class OrderDTO {

    /** 订单ID */
    private String orderId;

    /** 买家名字 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openID */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;

    /** 订单状态,默认0新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态，默认0未支付 */
    private Integer payStatus = OrderPayStatusEnum.WAIT.getCode();

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 购买商品明细 */
    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public OrderPayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, OrderPayStatusEnum.class);
    }

}
