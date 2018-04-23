package com.en.service;

import com.en.dto.OrderDTO;

/**
 * Created by En on 2018/4/14.
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);

}
