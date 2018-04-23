package com.en.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by En on 2018/4/21.
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    @NotNull(message = "类目名字不能为空")
    private String categoryName;

    /** 类目编号. */
    @NotNull(message = "类目编号不能为空")
    private Integer categoryType;

}
