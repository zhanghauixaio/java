package com.seven.mybatis.mapper;

import com.seven.mybatis.vo.Category;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    Category selectCategoryById(@Param("i") String i);

    List<Category> pagination(@Param("offset") Integer offset, @Param("limit") Integer limit);
    void insertCategory(Category category);
    // @MapKey("id")
    // Map<String, Object> selectMap();
}
