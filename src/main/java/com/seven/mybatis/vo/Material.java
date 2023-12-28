package com.seven.mybatis.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Getter
@Setter
public class Material {
    private String materialName;
    private List<Map<String, String>> category;
}
