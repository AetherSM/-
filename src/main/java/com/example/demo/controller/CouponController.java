package com.example.demo.controller;

import com.example.demo.pojo.entity.Coupon;
import com.example.demo.pojo.entity.UserCoupon;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券管理控制器
 */
@Tag(name = "优惠券管理", description = "优惠券的创建、领取和查询接口")
@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    /**
     * 创建优惠券
     * @param coupon 优惠券信息
     * @return 操作结果
     */
    @Operation(summary = "创建优惠券", description = "商家或管理员创建新的优惠券")
    @PostMapping("/add")
    public Result<String> addCoupon(@RequestBody Coupon coupon) {
        couponService.createCoupon(coupon);
        return Result.success("优惠券创建成功");
    }

    /**
     * 查询可领取的优惠券列表
     * @return 优惠券列表
     */
    @Operation(summary = "查询可领取优惠券", description = "获取当前所有有效且可领取的优惠券")
    @GetMapping("/list")
    public Result<List<Coupon>> listAvailable() {
        return Result.success(couponService.listAvailable());
    }

    /**
     * 用户领取优惠券
     * @param userId 用户ID
     * @param couponId 优惠券ID
     * @return 操作结果
     */
    @Operation(summary = "领取优惠券", description = "用户领取指定的优惠券")
    @PostMapping("/receive")
    public Result<String> receiveCoupon(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "优惠券ID", required = true) @RequestParam Long couponId) {
        couponService.receiveCoupon(userId, couponId);
        return Result.success("领取成功");
    }

    /**
     * 查询我的优惠券
     * @param userId 用户ID
     * @param status 状态（可选）
     * @return 用户优惠券列表
     */
    @Operation(summary = "我的优惠券", description = "查询用户已领取的优惠券，可按状态筛选")
    @GetMapping("/my")
    public Result<List<UserCoupon>> listMyCoupons(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId,
            @Parameter(description = "状态: 1-未使用, 2-已使用, 3-已过期") @RequestParam(required = false) Integer status) {
        return Result.success(couponService.listMyCoupons(userId, status));
    }
}
