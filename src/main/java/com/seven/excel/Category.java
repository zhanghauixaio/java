package com.seven.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    @ExcelProperty("序号")

    private String id;
    @ExcelProperty("分类编码")
    private String sn;
    @ExcelProperty("分类名称")
    private String name;
}
