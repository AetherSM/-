package com.example.demo.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "评价创建传输对象")
public class ReviewCreateDTO {
    @Schema(description = "评分: 1-5")
    private Integer rating; // 1-5
    @Schema(description = "评价内容")
    private String content;
    @Schema(description = "评价图片列表")
    private List<String> images;
    @Schema(description = "是否匿名: 0-否, 1-是")
    private Integer isAnonymous;
    @Schema(description = "订单类型: 1-商品订单, 2-跑腿订单")
    private Integer orderType;
}
