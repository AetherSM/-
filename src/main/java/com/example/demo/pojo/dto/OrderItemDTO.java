package com.example.demo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "订单商品项传输对象")
public class OrderItemDTO {
    @Schema(description = "商品ID")
    private Long productId;
    @Schema(description = "购买数量")
    private Integer quantity;
}

