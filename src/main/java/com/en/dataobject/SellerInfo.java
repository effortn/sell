package com.en.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by En on 2018/4/21.
 */
@Data
@Entity
@DynamicUpdate
public class SellerInfo {

    @Id
    private String id;

    /**  用户名    */
    private String username;

    /**  密码   */
    private String password;

    /**  openid  */
    private String openid;

    /**  创建时间  */
    private Date createTime;

    /**  更新时间   */
    private Date updateTime;

}
