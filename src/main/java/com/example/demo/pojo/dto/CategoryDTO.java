package com.example.demo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "商品分类传输对象")
public class CategoryDTO {
    @Schema(description = "分类名称")
    private String categoryName;
    @Schema(description = "父分类ID")
    private Integer parentId;
    @Schema(description = "排序值")
    private Integer sortOrder;
    @Schema(description = "图标URL")
    private String icon;
    @Schema(description = "状态: 0-禁用, 1-启用")
    private Integer status;
}

