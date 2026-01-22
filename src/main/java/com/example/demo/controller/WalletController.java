package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.entity.WalletTransaction;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wallet")
@Tag(name = "钱包管理", description = "用户钱包及交易记录相关接口")
public class WalletController {

    @Autowired
    private WalletService walletService;

    /**
     * 获取钱包交易记录
     * @return 交易记录列表
     */
    @Operation(summary = "获取交易记录", description = "查询当前登录用户的钱包交易明细")
    @GetMapping("/transactions")
    public Result<List<WalletTransaction>> list() {
        Long userId = requireLogin();
        return Result.success(walletService.list(userId));
    }

    /**
     * 钱包充值
     * @param body 包含充值金额 amount
     * @return 无
     */
    @Operation(summary = "钱包充值", description = "用户进行钱包充值")
    @PostMapping("/recharge")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "充值信息，包含amount字段", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(example = "{\"amount\": 100.00}")))
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

