package com.devin.dto;

import com.devin.enums.ApiEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 全局返回类，接口的返回
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Api("全局返回值")
public class APIResult<T> {
    // 状态码
    @ApiModelProperty("状态码")
    private Integer code;
    // 消息：解释状态码
    @ApiModelProperty("消息：解释状态码")
    private String message;
    // 返回的数据
    @ApiModelProperty("返回的数据")
    private T data;

    public APIResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 枚举类构造函数
     *
     * @param apiEnum api状态枚举
     */
    public APIResult(ApiEnum apiEnum) {
        this.code = apiEnum.getCode();
        this.message = apiEnum.getMessage();
    }
}


