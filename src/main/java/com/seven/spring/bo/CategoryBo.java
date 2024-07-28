package com.seven.spring.bo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seven.spring.model.CategoryExt;

import java.util.List;

public interface CategoryBo extends IService<CategoryExt> {
    CategoryExt getOne();
    void insertInto();
    List<CategoryExt> listAll(List<String> ids);
}
