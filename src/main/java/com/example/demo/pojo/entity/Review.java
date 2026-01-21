package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Review {
    private Long reviewId;
    private Long orderId;
    private Integer orderType; // 1-商品订单,2-跑腿订单
    private Long reviewerId;
    private Long revieweeId;
    private Integer rating;
    private String content;
    private String images; // JSON
    private Integer isAnonymous;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}

