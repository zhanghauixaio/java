package com.seven.spring.bo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.spring.bo.CategoryBo;
import com.seven.spring.mapper.CategoryExtMapper;
import com.seven.spring.model.CategoryExt;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryBoImpl extends ServiceImpl<CategoryExtMapper, CategoryExt> implements CategoryBo {

    @Override
    public CategoryExt getOne() {
        LambdaQueryWrapper<CategoryExt> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryExt::getId, "01");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public void insertInto() {
        CategoryExt categoryExt = new CategoryExt();
        categoryExt.setId(UUID.randomUUID().toString());
        categoryExt.setSn(true);
        categoryExt.setName("hhoa");
        baseMapper.insert(categoryExt);
    }
}
