package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.entity.WalletTransaction;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/transactions")
    public Result<List<WalletTransaction>> list() {
        Long userId = requireLogin();
        return Result.success(walletService.list(userId));
    }

    @PostMapping("/recharge")
    public Result<Void> recharge(@RequestBody Map<String, Object> body) {
        Long userId = requireLogin();
        Object amt = body.get("amount");
        BigDecimal amount = amt == null ? null : new BigDecimal(amt.toString());
        walletService.recharge(userId, amount);
        return Result.success();
    }

    private Long requireLogin() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        return userId;
    }
}

