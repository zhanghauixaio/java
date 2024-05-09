package com.seven.mybatis.vo;

import com.seven.mybatis.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Category {
    private String id;
    private Boolean sn;
    private TypeEnum name;
}
