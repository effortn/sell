package com.en.repository;

import com.en.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by En on 2018/4/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void testFindOne() {
        ProductCategory category = productCategoryRepository.findOne(1);
        System.out.println(category);
    }

    @Test
    //@Transactional
    public void testSave() {
        ProductCategory category = new ProductCategory("男生最爱", 4);
        ProductCategory result = productCategoryRepository.save(category);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void testFindByCategoryTypeIn() {
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        List<ProductCategory> categoryList = productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
        for (ProductCategory category : categoryList) {
            System.out.println(category);
        }
    }

}