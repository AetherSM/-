package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "用户优惠券关联实体")
public class UserCoupon {
    @Schema(description = "主键ID")
    private Long id;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "优惠券ID")
    private Long couponId;
    
    @Schema(description = "状态: 0-未使用, 1-已使用, 2-已过期")
    private Integer status; 
    
    @Schema(description = "使用的订单ID")
    private Long usedOrderId;
    
    @Schema(description = "领取时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @Schema(description = "使用时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime usedTime;
}
