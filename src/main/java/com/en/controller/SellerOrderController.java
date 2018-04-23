package com.en.controller;

import com.en.config.ProjectUrlConfig;
import com.en.dto.OrderDTO;
import com.en.enums.ResultEnum;
import com.en.exception.SellException;
import com.en.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


/**
 * Created by En on 2018/4/16.
 */
@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 管理端查询订单列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageable);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        return new ModelAndView("order/list", map);
    }

    @RequestMapping("/cancel")
    public ModelAndView cancle(@RequestParam("orderId") String orderId,
                                Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【订单取消】发生异常{}", e);
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
        map.put("url", "/sell/seller/order/list");
        map.put("msg", ResultEnum.SUCCESS);
        return new ModelAndView(projectUrlConfig.getSuccess(), map);
    }

    @RequestMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            map.put("orderDTO", orderDTO);
        } catch (SellException e) {
            log.error("【订单详情】查看订单详情报错，orderId={}，e={}", orderId, e);
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
        return new ModelAndView("order/detail", map);
    }

    @RequestMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【订单完成】发生异常{}", e);
            map.put("url", "/sell/seller/order/list");
            map.put("msg", e.getMessage());
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
        map.put("url", "/sell/seller/order/list");
        map.put("msg", ResultEnum.SUCCESS);
        return new ModelAndView(projectUrlConfig.getSuccess(), map);
    }

}
