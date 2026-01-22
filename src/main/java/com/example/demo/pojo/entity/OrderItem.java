package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "订单明细项实体")
public class OrderItem {
    @Schema(description = "明细ID")
    private Long itemId;
    @Schema(description = "订单ID")
    private Long orderId;
    @Schema(description = "商品ID")
    private Long productId;
    @Schema(description = "商品名称")
    private String productName;
    @Schema(description = "商品图片")
    private String productImage;
    @Schema(description = "购买价格")
    private BigDecimal price;
    @Schema(description = "购买数量")
    private Integer quantity;
    @Schema(description = "小计金额")
    private BigDecimal subtotal;
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

