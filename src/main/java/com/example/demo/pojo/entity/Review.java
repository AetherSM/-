package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "评价实体")
public class Review {
    @Schema(description = "评价ID")
    private Long reviewId;
    @Schema(description = "订单ID")
    private Long orderId;
    @Schema(description = "订单类型: 1-商品订单, 2-跑腿订单")
    private Integer orderType; // 1-商品订单,2-跑腿订单
    @Schema(description = "评价人ID")
    private Long reviewerId;
    @Schema(description = "被评价人ID")
    private Long revieweeId;
    @Schema(description = "评分: 1-5")
    private Integer rating;
    @Schema(description = "评价内容")
    private String content;
    @Schema(description = "评价图片JSON")
    private String images; // JSON
    @Schema(description = "是否匿名: 0-否, 1-是")
    private Integer isAnonymous;
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

