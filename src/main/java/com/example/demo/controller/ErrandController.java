package com.example.demo.controller;

import com.example.demo.pojo.entity.ErrandOrder;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ErrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 跑腿订单控制器
 */
@Tag(name = "跑腿订单管理", description = "跑腿订单的发布、接单与查询")
@RestController
@RequestMapping("/api/errands")
public class ErrandController {

    @Autowired
    private ErrandService errandService;

    /**
     * 创建跑腿订单
     * @param order 订单信息
     * @return 操作结果
     */
    @Operation(summary = "发布跑腿订单", description = "用户发布新的跑腿订单")
    @PostMapping("/create")
    public Result<String> createOrder(@RequestBody ErrandOrder order) {
        errandService.createOrder(order);
        return Result.success("订单创建成功");
    }

    /**
     * 跑腿员接单
     * @param orderNo 订单号
     * @param runnerId 跑腿员ID
     * @return 操作结果
     */
    @Operation(summary = "接单", description = "跑腿员接取订单")
    @PostMapping("/take")
    public Result<String> takeOrder(
            @Parameter(description = "订单号", required = true) @RequestParam String orderNo,
            @Parameter(description = "跑腿员ID", required = true) @RequestParam Long runnerId) {
        errandService.takeOrder(orderNo, runnerId);
        return Result.success("接单成功");
    }

    /**
     * 完成订单
     * @param orderNo 订单号
     * @return 操作结果
     */
    @Operation(summary = "完成订单", description = "跑腿员或用户确认订单完成")
    @PostMapping("/complete")
    public Result<String> completeOrder(
            @Parameter(description = "订单号", required = true) @RequestParam String orderNo) {
        errandService.completeOrder(orderNo);
        return Result.success("订单完成");
    }

    /**
     * 查询待接单订单
     * @return 待接单订单列表
     */
    @Operation(summary = "查询待接单订单", description = "获取所有状态为'待接单'的跑腿订单")
    @GetMapping("/open")
    public Result<List<ErrandOrder>> listOpenOrders() {
        return Result.success(errandService.listOpenOrders());
    }

    /**
     * 查询我的订单
     * @param userId 用户ID
     * @return 订单列表
     */
    @Operation(summary = "我的订单", description = "查询用户发布或接取的跑腿订单")
    @GetMapping("/my")
    public Result<List<ErrandOrder>> listMyOrders(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId) {
        return Result.success(errandService.listMyOrders(userId));
    }
}
