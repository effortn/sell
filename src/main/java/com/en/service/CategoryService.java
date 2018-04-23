package com.en.service;

import com.en.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by En on 2018/4/5.
 */
public interface CategoryService {

    ProductCategory findOne(Integer id);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory saveOrUpdate(ProductCategory productCategory);

}
