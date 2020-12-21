package com.devin.entity;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotation.*;
import com.devin.entity.request.UserRequest;
import com.devin.enums.GenderEnum;
import com.devin.enums.IsDeletedEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.beans.BeanUtils;


/**
 * <p>
 * 
 * </p>
 *
 * @author devin
 * @since 2020-12-15
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    // 雪花算法，保证id唯一
    @TableId(type = IdType.ASSIGN_ID)
    // 字符串序列化
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Version
    @ApiModelProperty("版本")
    private Integer version;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("性别")
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @EnumValue
    private GenderEnum gender;

    @ApiModelProperty(value = "0 没删除 1 已删除	")
    @TableLogic
    @JSONField(serialzeFeatures = SerializerFeature.WriteEnumUsingToString)
    @EnumValue
    private IsDeletedEnum isDeleted;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    public User(UserRequest request) {
        BeanUtils.copyProperties(request, this);
        Date date = new Date();
        this.createTime = date;
        this.updateTime = date;
    }

}
