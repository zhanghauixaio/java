package com.seven.mybatis.vo;

import com.seven.mybatis.enums.TypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Category {
    private String id;
    private List<String> sn;
    // private String sn;
    private TypeEnum name;
}
