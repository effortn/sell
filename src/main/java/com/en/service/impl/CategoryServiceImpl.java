package com.en.service.impl;

import com.en.dataobject.ProductCategory;
import com.en.repository.ProductCategoryRepository;
import com.en.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类目
 * Created by En on 2018/4/5.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer id) {
        return productCategoryRepository.findOne(id);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory saveOrUpdate(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

}
