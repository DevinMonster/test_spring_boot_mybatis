package com.devin.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsDeletedEnum implements IEnum<Integer> {
    YES(1, "禁用"),
    NO(0, "激活");

    private final Integer code;

    @JsonValue
    private final String message;

    @Override
    public Integer getValue() {
        return code;
    }
}
