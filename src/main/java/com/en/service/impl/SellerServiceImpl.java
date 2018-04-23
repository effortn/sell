package com.en.service.impl;

import com.en.dataobject.SellerInfo;
import com.en.repository.SellerInfoRepository;
import com.en.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by En on 2018/4/22.
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }

}
