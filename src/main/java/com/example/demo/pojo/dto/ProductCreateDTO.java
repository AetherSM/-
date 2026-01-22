package com.example.demo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "商品创建/更新传输对象")
public class ProductCreateDTO {
    @Schema(description = "分类ID")
    private Integer categoryId;
    @Schema(description = "商品名称")
    private String productName;
    @Schema(description = "商品描述")
    private String description;
    @Schema(description = "价格")
    private BigDecimal price;
    @Schema(description = "原价")
    private BigDecimal originalPrice;
    @Schema(description = "库存")
    private Integer stock;
    @Schema(description = "主图URL")
    private String mainImage;
    @Schema(description = "详情图列表")
    private List<String> images;
    @Schema(description = "状态: 0-下架, 1-上架")
    private Integer status; // 0/1
}

