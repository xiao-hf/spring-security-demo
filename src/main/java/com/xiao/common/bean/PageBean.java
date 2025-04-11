package com.xiao.common.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页父类
 */
@Data
public class PageBean {

    @Schema(description = "页码")
    Integer pageNo;

    @Schema(description = "每页数量")
    Integer pageSize;

}
