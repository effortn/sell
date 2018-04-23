package com.en.controller;

import com.en.VO.ResultVO;
import com.en.converter.OrderForm2OrderDTOConverter;
import com.en.dto.OrderDTO;
import com.en.enums.ResultEnum;
import com.en.exception.SellException;
import com.en.form.OrderForm;
import com.en.service.BuyerService;
import com.en.service.OrderService;
import com.en.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by En on 2018/4/14.
 */
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //新建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        OrderDTO orderCreate = orderService.orderCreate(orderDTO);
        Map<String, String> rs = new HashMap<>();
        rs.put("orderId", orderCreate.getOrderId());
        return ResultVOUtil.success(rs);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<Page<OrderDTO>> list(@RequestParam("openid")String openId,
                                         @RequestParam(value = "page", defaultValue = "0")Integer page,
                                         @RequestParam(value = "size", defaultValue = "10")Integer size) {
        if (StringUtils.isEmpty(openId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openId, pageable);
        return ResultVOUtil.success(orderDTOPage);
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String openId,
                                     @RequestParam("orderId")String orderId) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderDTO orderDTO = buyerService.findOrderOne(openId, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancle(@RequestParam("openid")String openId,
                                     @RequestParam("orderId")String orderId) {
        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(orderId)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        buyerService.cancelOrder(openId, orderId);
        return ResultVOUtil.success();
    }
}
