package com.devin.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author devin
 */
@Data
@ToString
public class UserRequest extends PageRequest implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("确认密码")
    @NotNull(message = "确认密码不能为空")
    @NotBlank(message = "确认密码不能为空")
    private String checkPassword;

    @ApiModelProperty("服务端验证码")
    private String key;

    @ApiModelProperty("图片")
    private String picName;

    @ApiModelProperty("验证码")
    private String verifyCode;

    @ApiModelProperty("乐观锁")
    private Integer version;
}
