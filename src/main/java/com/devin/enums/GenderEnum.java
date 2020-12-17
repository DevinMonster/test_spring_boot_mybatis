package com.devin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import lombok.Getter;

@Getter
public enum GenderEnum implements IEnum<Integer> {
    MAN(1, "男"), GIRL(0, "女");

    private Integer code;

    @EnumValue
    @JsonValue
    private String message;

    @Override
    public Integer getValue() {
        return this.code;
    }

    GenderEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
