package com.en.controller;

import com.en.config.ProjectUrlConfig;
import com.en.dataobject.ProductCategory;
import com.en.dataobject.ProductInfo;
import com.en.exception.SellException;
import com.en.form.ProductForm;
import com.en.service.CategoryService;
import com.en.service.ProductService;
import com.en.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by En on 2018/4/21.
 */
@RequestMapping("/seller/product")
@Controller
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        Pageable pageable = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageable);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        return new ModelAndView("/product/list", map);
    }

    @GetMapping("/onSale/{productId}")
    public ModelAndView onSale(@PathVariable("productId")String productId,
                                Map<String, Object> map) {
        try {
            productService.onSale(productId);
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView(projectUrlConfig.getSuccess());
        } catch (SellException e) {
            log.error("【商品上架】失败，productId={}, e={}", productId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
    }

    @GetMapping("/offSale/{productId}")
    public ModelAndView offSale(@PathVariable("productId")String productId,
                                 Map<String, Object> map) {
        try {
            productService.offSale(productId);
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView(projectUrlConfig.getSuccess());
        } catch (SellException e) {
            log.error("【商品下架】失败，productId={}, e={}", productId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                               Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        if (StringUtils.isEmpty(productId)) {
            return new ModelAndView("product/index", map);
        }
        try {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
            return new ModelAndView("product/index", map);
        } catch (SellException e) {
            log.error("【商品修改】报错，productId={}, e={}", productId, e);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
    }

    @PostMapping("/increase")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                              Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            log.error("【新建商品】报错，productForm={}, e={}", productForm, bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return new ModelAndView(projectUrlConfig.getError(), map);
        }
        String productId = productForm.getProductId();
        if (StringUtils.isEmpty(productId)) {
            productId = KeyUtil.getKey();
        }
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productForm, productInfo);
        productInfo.setProductId(productId);
        try {
            productService.saveOrUpdate(productInfo);
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView(projectUrlConfig.getSuccess(), map);
        } catch (SellException e) {
            log.error("【新建商品】报错，productForm={}, e={}", productForm, e);
            map.put("url", "/sell/seller/product/index");
            map.put("msg", e.getMessage());
            return new ModelAndView(projectUrlConfig.getError(), map);
        }

    }

}
