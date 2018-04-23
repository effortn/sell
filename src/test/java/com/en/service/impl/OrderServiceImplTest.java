package com.en.service.impl;

import com.en.dataobject.OrderDetail;
import com.en.dto.OrderDTO;
import com.en.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * Created by En on 2018/4/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    private final String orderId = "1523070349395750767";
    private final String openid = "110110";

    @Test
    public void orderCreate() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("沙比");
        orderDTO.setBuyerAddress("家里");
        orderDTO.setBuyerOpenid(openid);
        orderDTO.setBuyerPhone("1230438943");
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("f7a344e342fa4410b046b77d07a4ddb4");
        orderDetail.setProductQuantity(2);
        orderDTO.setOrderDetailList(Arrays.asList(orderDetail));
        OrderDTO result = orderService.orderCreate(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        log.info("订单详情={}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        Pageable pageable = new PageRequest(0, 10);
        Page<OrderDTO> dtos = orderService.findList(openid, pageable);
        Assert.assertNotNull(dtos.getContent());
    }

}