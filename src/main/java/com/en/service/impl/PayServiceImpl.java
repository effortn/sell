package com.en.service.impl;

import com.en.dto.OrderDTO;
import com.en.enums.ResultEnum;
import com.en.exception.SellException;
import com.en.service.OrderService;
import com.en.service.PayService;
import com.en.utils.JsonUtil;
import com.en.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by En on 2018/4/15.
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信订餐订单";

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private OrderService orderService;

    /**
     * 微信支付统一下单
     * @param orderDTO
     * @return
     */
    @Override
    public PayResponse creat(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        PayResponse response = bestPayService.pay(payRequest);
        log.info("【微信支付】统一下单，reponse={}", response);
        return response;
    }

    /**
     * 异步接收微信支付状态
     * @param notifyData
     * @return
     */
    @Override
    public PayResponse notify(String notifyData) {
        //1.校验签名
        //2.校验是否成功
        //3.校验金额
        //4.支付人（支付人==下单人）
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知, payResponse={}", JsonUtil.toJson(payResponse));

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if (orderDTO == null) {
            log.error("【微信支付】异步通知, 订单不存在, orderId={}", payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //判断金额是否一致(0.10   0.1)
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())) {
            log.error("【微信支付】异步通知, 订单金额不一致, orderId={}, 微信通知金额={}, 系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改订单的支付状态
        orderService.paid(orderDTO);

        return payResponse;
    }

    /**
     * 微信支付退款
     * @param orderDTO
     * @return
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));
        RefundResponse response = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JsonUtil.toJson(response));
        return response;
    }

}
