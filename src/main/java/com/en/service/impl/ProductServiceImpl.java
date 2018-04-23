package com.en.service.impl;

import com.en.dataobject.ProductInfo;
import com.en.dto.CartDTO;
import com.en.enums.ProductStatusEnum;
import com.en.enums.ResultEnum;
import com.en.exception.SellException;
import com.en.repository.ProductInfoRepository;
import com.en.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by En on 2018/4/6.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findOne(String id) {
        return productInfoRepository.findOne(id);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo saveOrUpdate(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cart : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cart.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            int nextStock = productInfo.getProductStock() + cart.getProductQuantity();
            productInfo.setProductStock(nextStock);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public synchronized void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cart : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cart.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            int nextStock = productInfo.getProductStock() - cart.getProductQuantity();
            if(nextStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(nextStock);
            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        ProductInfo result = productInfoRepository.save(productInfo);
        return result;
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInfoRepository.findOne(productId);
        if (productInfo == null) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIT);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        ProductInfo result = productInfoRepository.save(productInfo);
        return result;
    }

}
