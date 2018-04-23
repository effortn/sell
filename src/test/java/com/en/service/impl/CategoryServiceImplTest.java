package com.en.service.impl;

import com.en.dataobject.ProductCategory;
import com.en.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by En on 2018/4/5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals("畅销物品", category.getCategoryName());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> categoryList = categoryService.findAll();
        Assert.assertEquals(3, categoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        Assert.assertEquals(2, categoryList.size());
    }

    @Test
    public void saveOrUpdate() throws Exception {
        ProductCategory category = new ProductCategory("电子产品", 2);
        ProductCategory result = categoryService.saveOrUpdate(category);
        Assert.assertNotEquals(null, result);
    }

}