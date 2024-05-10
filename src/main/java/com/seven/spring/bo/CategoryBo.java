package com.seven.spring.bo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.spring.model.Category;
import com.seven.spring.model.CategoryExt;

public interface CategoryBo extends IService<CategoryExt> {
    CategoryExt getOne();
    void insertInto();
}
