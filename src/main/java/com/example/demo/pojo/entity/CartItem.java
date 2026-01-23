package com.example.demo.pojo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "购物车条目")
public class CartItem {
    @Schema(description = "购物车条目ID")
    private Long cartItemId;
    @Schema(description = "用户ID")
    private Long userId;
    @Schema(description = "商品ID")
    private Long productId;
    @Schema(description = "数量")
    private Integer quantity;
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
