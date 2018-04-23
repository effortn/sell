package com.en.service.impl;

import com.en.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by En on 2018/4/6.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("c5b62be07df0483681ff0a16759e65a5");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertEquals(1, upAll.size());
    }

    @Test
    public void findAll() throws Exception {
        //Sort sort = new JpaSort(Sort.Direction.ASC, new JpaSort.Path());
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        Sort sort = new Sort(order);
        Pageable pageable = new PageRequest(0, 5, sort);
        Page<ProductInfo> all = productService.findAll(pageable);
        Assert.assertNotNull(all);
    }

    @Test
    public void saveOrUpdate() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString().replace("-", ""));
        productInfo.setProductName("MacBook Pro");
        productInfo.setProductPrice(new BigDecimal(18888.83));
        productInfo.setProductStock(390);
        productInfo.setProductIcon("http://l;eriup.jpg");
        productInfo.setCategoryType(6);
        productInfo.setProductStatus(0);
        ProductInfo result = productService.saveOrUpdate(productInfo);
        Assert.assertNotNull(result);
    }

}