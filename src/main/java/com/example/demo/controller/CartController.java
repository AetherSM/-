package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "购物车管理", description = "购物车的添加、更新、删除与查询接口")
public class CartController {

    @Autowired
    private CartService cartService;

    private Long requireLogin() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) throw new IllegalArgumentException("未登录");
        return userId;
    }

    @GetMapping
    @Operation(summary = "查询购物车", description = "返回购物车条目及商品信息")
    public Result<List<Map<String, Object>>> list() {
        Long userId = requireLogin();
        return Result.success(cartService.listWithProduct(userId));
    }

    @PostMapping("/add")
    @Operation(summary = "加入购物车", description = "将商品加入购物车（存在则数量累加）")
    public Result<Void> add(@Parameter(description = "商品ID", required = true) @RequestParam Long productId,
                            @Parameter(description = "数量", required = true) @RequestParam Integer quantity) {
        Long userId = requireLogin();
        cartService.addItem(userId, productId, quantity);
        return Result.success();
    }

    @PutMapping("/{productId}")
    @Operation(summary = "设置商品数量", description = "设置购物车某商品的数量")
    public Result<Void> setQty(@PathVariable Long productId,
                               @Parameter(description = "数量", required = true) @RequestParam Integer quantity) {
        Long userId = requireLogin();
        cartService.setQuantity(userId, productId, quantity);
        return Result.success();
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "移除商品", description = "从购物车中移除指定商品")
    public Result<Void> remove(@PathVariable Long productId) {
        Long userId = requireLogin();
        cartService.removeItem(userId, productId);
        return Result.success();
    }

    @PostMapping("/clear")
    @Operation(summary = "清空购物车", description = "清空当前用户购物车")
    public Result<Void> clear() {
        Long userId = requireLogin();
        cartService.clear(userId);
        return Result.success();
    }
}
