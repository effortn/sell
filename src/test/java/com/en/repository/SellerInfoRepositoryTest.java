package com.en.repository;

import com.en.dataobject.SellerInfo;
import com.en.utils.KeyUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.provider.MD5;

import static org.junit.Assert.*;

/**
 * Created by En on 2018/4/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    private final String openid = "fasfqwrqwfasdfasdf";

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() throws Exception {
        SellerInfo sellerInfo = new SellerInfo();

        sellerInfo.setId(KeyUtil.getKey());
        sellerInfo.setOpenid(openid);
        sellerInfo.setPassword("123456");
        sellerInfo.setUsername("admin");
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid(openid);
        Assert.assertEquals(sellerInfo.getUsername(), "admin");
    }

}