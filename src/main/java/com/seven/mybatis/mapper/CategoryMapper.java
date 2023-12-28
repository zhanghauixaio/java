package com.seven.mybatis.mapper;

import com.seven.excel.Category;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CategoryMapper {
    Category selectCategoryById(@Param("i") int i);
    // @MapKey("id")
    // Map<String, Object> selectMap();
}
