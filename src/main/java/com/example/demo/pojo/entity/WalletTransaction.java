package com.example.demo.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "钱包交易记录实体")
public class WalletTransaction {
    @Schema(description = "交易ID")
    private Long transactionId;
    
    @Schema(description = "用户ID")
    private Long userId;
    
    @Schema(description = "交易类型: 1-充值, 2-提现, 3-支付, 4-收入, 5-退款")
    private Integer transactionType; // 1-充值,2-提现,3-支付,4-收入,5-退款
    
    @Schema(description = "交易金额")
    private BigDecimal amount;
    
    @Schema(description = "交易前余额")
    private BigDecimal balanceBefore;
    
    @Schema(description = "交易后余额")
    private BigDecimal balanceAfter;
    
    @Schema(description = "关联订单号")
    private String relatedOrderNo;
    
    @Schema(description = "交易描述")
    private String description;
    
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

