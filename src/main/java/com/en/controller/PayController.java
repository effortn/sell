package com.en.controller;

import com.en.dto.OrderDTO;
import com.en.enums.ResultEnum;
import com.en.exception.SellException;
import com.en.service.OrderService;
import com.en.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by En on 2018/4/15.
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("returnUrl") String returnUrl,
                               @RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【微信支付】订单为空，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        PayResponse response = payService.creat(orderDTO);
        map.put("payResponse", response);
        map.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", map);
    }

    @RequestMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData) {
        PayResponse payResponse = payService.notify(notifyData);
        return new ModelAndView("/pay/success");
    }

}
