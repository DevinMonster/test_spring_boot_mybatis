package com.devin.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("分页请求类")
public class PageRequest implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页内容数")
    private int size;
}
