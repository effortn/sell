package com.en.service;

import com.en.dataobject.ProductInfo;
import com.en.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品
 * Created by En on 2018/4/6.
 */
public interface ProductService {

    ProductInfo findOne(String id);

    /** 查询已上架的所有商品 */
    List<ProductInfo> findUpAll();

    /** 查询所有商品 */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo saveOrUpdate(ProductInfo productInfo);

    //增加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减少库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //商品下架
    ProductInfo offSale(String productId);

    //商品上架
    ProductInfo onSale(String productId);
}
