package com.example.demo.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "购物记录")
public class ShoppingRecord {
    @Schema(description = "记录ID")
    private Long recordId;
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "订单号")
    private String orderNo;
    @Schema(description = "商品ID")
    private Long productId;
    @Schema(description = "商品名称")
    private String productName;
    @Schema(description = "商品图片")
    private String productImage;
    @Schema(description = "单价")
    private BigDecimal price;
    @Schema(description = "数量")
    private Integer quantity;
    @Schema(description = "小计")
    private BigDecimal subtotal;
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}
