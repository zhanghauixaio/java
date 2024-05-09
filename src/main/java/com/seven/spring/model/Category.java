package com.seven.spring.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("CATEGORY")
public class Category {
    @TableId("id")
    private String id;

    @TableField("sn")
    private String sn;
}
