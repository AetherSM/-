package com.example.demo.service;

import com.example.demo.pojo.entity.WalletTransaction;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    void recharge(Long userId, BigDecimal amount);

    List<WalletTransaction> list(Long userId);
}

