package com.en.repository;

import com.en.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by En on 2018/4/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(UUID.randomUUID().toString().replace("-", ""));
        productInfo.setProductName("IPhone X");
        productInfo.setProductPrice(new BigDecimal(7999.23));
        productInfo.setProductStock(9403);
        productInfo.setProductDescription("最新款IPhone");
        productInfo.setProductIcon("http://www.asdf.com");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        productInfoRepository.save(productInfo);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(1);
        for (ProductInfo productInfo : productInfoList) {
            System.out.println(productInfo);
        }
    }

}