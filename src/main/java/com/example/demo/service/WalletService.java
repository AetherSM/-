package com.example.demo.service;

import com.example.demo.pojo.entity.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * 钱包业务接口
 */
public interface WalletService {
    
    /**
     * 充值
     * @param userId 用户ID
     * @param amount 充值金额
     */
    void recharge(Long userId, BigDecimal amount);

    /**
     * 查询交易记录
     * @param userId 用户ID
     * @return 交易记录列表
     */
    List<WalletTransaction> list(Long userId);
}
