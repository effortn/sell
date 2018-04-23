package com.en.service.impl;

import com.en.converter.OrderMasterToOrderDTOConverter;
import com.en.dataobject.OrderDetail;
import com.en.dataobject.OrderMaster;
import com.en.dataobject.ProductInfo;
import com.en.dto.CartDTO;
import com.en.dto.OrderDTO;
import com.en.enums.OrderPayStatusEnum;
import com.en.enums.OrderStatusEnum;
import com.en.enums.ResultEnum;
import com.en.exception.SellException;
import com.en.repository.OrderDetailRepository;
import com.en.repository.OrderMasterRepository;
import com.en.service.OrderService;
import com.en.service.PayService;
import com.en.service.ProductService;
import com.en.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单Service
 * Created by En on 2018/4/7.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO orderCreate(OrderDTO orderDTO) {
        //1. 获取商品详细信息，拼装订单数据
        String orderId = KeyUtil.getKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        for (OrderDetail orderDetail :  orderDTO.getOrderDetailList()) {
            if (orderDetail == null || StringUtils.isEmpty(orderDetail.getProductId())) {
                throw new SellException(ResultEnum.CART_IS_NULL);
            }
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getKey());
            orderDetailRepository.save(orderDetail);
            //1-2. 计算总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
        }
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(OrderPayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        //2. 保存订单信息(主表，详情表)
        orderMasterRepository.save(orderMaster);
        //3. 减库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> result = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return result;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        String orderId = orderDTO.getOrderId();
        //1.判断订单状态是否为新订单
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (ObjectUtils.isEmpty(orderMaster)) {
            log.error("【取消订单】订单不存在，orderID={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.更改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
        OrderMaster orderUpdate = orderMasterRepository.save(orderMaster);
        if (ObjectUtils.isEmpty(orderUpdate)) {
            log.error("【取消订单】更新订单状态失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());

        //3.加库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //4.退款
        if (orderMaster.getPayStatus() == 1) {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        String orderId = orderDTO.getOrderId();
        //1.判断订单状态是否为新订单
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (ObjectUtils.isEmpty(orderMaster)) {
            log.error("【完结订单】订单不存在，orderID={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if (!orderMaster.getPayStatus().equals(OrderPayStatusEnum.SUCCESS.getCode())) {
            log.error("【完结订单】支付状态不正确, orderId={}, payStatus={}", orderDTO.getOrderId(), orderMaster.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //3.更改订单
        orderDTO.setOrderStatus(OrderStatusEnum.FINSH.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.FINSH.getCode());
        OrderMaster orderUpdate = orderMasterRepository.save(orderMaster);
        if (orderUpdate == null) {
            log.error("【完结订单】更新订单状态失败，orderMaster={}", orderMaster);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        String orderId = orderDTO.getOrderId();
        //1.判断订单状态
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (ObjectUtils.isEmpty(orderMaster)) {
            log.error("【订单支付】订单不存在，orderID={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        if (orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if (!orderMaster.getPayStatus().equals(OrderPayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付】订单支付状态不正确，orderId={}, payStatus={}", orderId, orderMaster.getPayStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //3.更改支付状态
        orderDTO.setPayStatus(OrderPayStatusEnum.SUCCESS.getCode());
        orderMaster.setOrderStatus(OrderPayStatusEnum.SUCCESS.getCode());
        OrderMaster orderUpdate = orderMasterRepository.save(orderMaster);
        if (orderUpdate == null) {
            log.error("【订单支付】订单支付状态更新失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> result = new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return result;
    }

}
