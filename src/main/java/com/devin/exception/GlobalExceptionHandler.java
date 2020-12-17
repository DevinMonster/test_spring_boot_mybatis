package com.devin.exception;

import com.devin.dto.APIResult;
import com.devin.dto.ResultGenerator;
import com.devin.enums.ApiEnum;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 统一异常处理
     * @param e 异常
     * @return 错误500
     */
    @ExceptionHandler(value = Exception.class)
    public APIResult exceptionHandler(Exception e) {
        // 打印调用栈信息
        e.printStackTrace();
        if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException)e;
            if (globalException.getApiEnum() == null)
                return ResultGenerator.genFailed();
            return ResultGenerator.genFailed(globalException.getApiEnum());
        }
        // 还可以接其他异常
        return ResultGenerator.genFailed();
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public APIResult arithmeticExceptionHandler(ArithmeticException e) {
        e.printStackTrace();
        return ResultGenerator.genFailed(ApiEnum.DIVIDE_BY_ZERO);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public APIResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        // 这里在构造错误信息
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        boolean first = true;
        for (ObjectError error : allErrors) {
            if (first) first = false;
            else sb.append(",");
            sb.append(error.getDefaultMessage());
        }
        // 返回错误信息
        return ResultGenerator.genFailed(sb.toString());
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public APIResult maxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return ResultGenerator.genFailed(ApiEnum.FILE_SIZE_EXCEEDED);
    }

}
