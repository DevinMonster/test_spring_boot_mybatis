package com.devin.exception;

import com.devin.enums.ApiEnum;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException{

    private ApiEnum apiEnum;

    private String message;

    public GlobalException(ApiEnum apiEnum) {
        super(apiEnum.getMessage());
        this.apiEnum = apiEnum;
        this.message = apiEnum.getMessage();
    }


}
