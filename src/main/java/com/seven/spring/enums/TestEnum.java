package com.seven.spring.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum TestEnum {
    Yes(1, "是"),
    NO(2, "否");
    @EnumValue
    private int code;
    @JsonValue
    private String value;
    TestEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

}
