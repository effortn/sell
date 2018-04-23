package com.en.repository;

import com.en.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Created by En on 2018/4/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest() {
        OrderDetail detail = new OrderDetail();
        detail.setDetailId(UUID.randomUUID().toString().replace("-", ""));
        detail.setOrderId("15089b7b7074485aa34e15bb30026185");
        detail.setProductId("f7a344e342fa4410b046b77d07a4ddb4");
        detail.setProductName("芒果冰");
        detail.setProductIcon("http://asdf.jpg");
        detail.setProductPrice(new BigDecimal(16.66));
        detail.setProductQuantity(3);
        OrderDetail result = repository.save(detail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() throws Exception {
        List<OrderDetail> detailList = repository.findByOrderId("");
        Assert.assertNotNull(detailList);
    }

}