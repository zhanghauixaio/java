package com.seven.spring.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.seven.mybatis.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("CATEGORY")
public class CategoryExt extends Category{
    @TableField("name")
    private TypeEnum name;
}
