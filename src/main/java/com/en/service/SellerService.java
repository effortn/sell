package com.en.service;

import com.en.dataobject.SellerInfo;

/**
 * Created by En on 2018/4/22.
 */
public interface SellerService {

    //根据openid获取用户信息
    SellerInfo findByOpenid(String openid);

}
