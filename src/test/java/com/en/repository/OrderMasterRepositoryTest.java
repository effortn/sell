package com.en.repository;

import com.en.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static javax.print.attribute.standard.MediaSizeName.A;
import static org.junit.Assert.*;

/**
 * Created by En on 2018/4/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void saveTest() {
        String id = UUID.randomUUID().toString().replace("-", "");
        OrderMaster master = new OrderMaster();
        master.setOrderId(id);
        master.setBuyerName("张三");
        master.setBuyerPhone("1*******1");
        master.setBuyerAddress("慕课网");
        master.setBuyerOpenid("110110");
        master.setOrderAmount(new BigDecimal(9.21));
        OrderMaster result = repository.save(master);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        Pageable pageable = new PageRequest(0, 5);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("110110", pageable);
        long total = orderMasterPage.getTotalElements();
        Assert.assertEquals(2, total);
    }

}