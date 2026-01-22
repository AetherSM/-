package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "商品分类实体")
public class Category {
    @Schema(description = "分类ID")
    private Integer categoryId;
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
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

