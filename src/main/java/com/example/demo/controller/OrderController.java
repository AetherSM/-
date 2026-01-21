package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.dto.OrderCreateDTO;
import com.example.demo.pojo.entity.OrderItem;
import com.example.demo.pojo.entity.ProductOrder;
import com.example.demo.pojo.entity.UserEntity;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * 创建订单
     * @param dto 订单创建信息
     * @return 创建的订单
     */
    @PostMapping
    public Result<ProductOrder> create(@RequestBody OrderCreateDTO dto) {
        Long userId = requireLogin();
        return Result.success(orderService.createOrder(userId, dto));
    }

    /**
     * 支付订单
     * @param orderNo 订单号
     * @return 无
     */
    @PostMapping("/{orderNo}/pay")
    public Result<Void> pay(@PathVariable String orderNo) {
        Long userId = requireLogin();
        orderService.pay(orderNo, userId);
        return Result.success();
    }

    /**
     * 取消订单
     * @param orderNo 订单号
     * @param body 包含取消原因
     * @return 无
     */
    @PostMapping("/{orderNo}/cancel")
    public Result<Void> cancel(@PathVariable String orderNo, @RequestBody(required = false) Map<String, String> body) {
        Long userId = requireLogin();
        String reason = body == null ? null : body.get("cancelReason");
        orderService.cancel(orderNo, userId, reason);
        return Result.success();
    }

    /**
     * 发货（仅商家）
     * @param orderNo 订单号
     * @return 无
     */
    @PostMapping("/{orderNo}/ship")
    public Result<Void> ship(@PathVariable String orderNo) {
        Long sellerId = requireSeller();
        orderService.ship(orderNo, sellerId);
        return Result.success();
    }

    /**
     * 确认收货
     * @param orderNo 订单号
     * @return 无
     */
    @PostMapping("/{orderNo}/confirm")
    public Result<Void> confirm(@PathVariable String orderNo) {
        Long userId = requireLogin();
        orderService.confirm(orderNo, userId);
        return Result.success();
    }

    /**
     * 获取我的订单列表
     * @param status 订单状态过滤（可选）
     * @return 订单列表
     */
    @GetMapping
    public Result<List<ProductOrder>> listMy(@RequestParam(value = "status", required = false) Integer status) {
        Long userId = requireLogin();
        return Result.success(orderService.listUserOrders(userId, status));
    }

    /**
     * 获取商家订单列表（仅商家）
     * @param status 订单状态过滤（可选）
     * @return 订单列表
     */
    @GetMapping("/seller")
    public Result<List<ProductOrder>> listSeller(@RequestParam(value = "status", required = false) Integer status) {
        Long sellerId = requireSeller();
        return Result.success(orderService.listSellerOrders(sellerId, status));
    }

    /**
     * 获取订单详情
     * @param orderNo 订单号
     * @return 订单详情（包含订单信息和订单项）
     */
    @GetMapping("/{orderNo}")
    public Result<Map<String, Object>> detail(@PathVariable String orderNo) {
        ProductOrder order = orderService.findByOrderNo(orderNo);
        if (order == null) {
            return Result.error("订单不存在");
        }
        List<OrderItem> items = orderService.listItems(order.getOrderId());
        Map<String, Object> res = new HashMap<>();
        res.put("order", order);
        res.put("items", items);
        return Result.success(res);
    }

    private Long requireLogin() {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        return userId;
    }

    private Long requireSeller() {
        Long userId = requireLogin();
        UserEntity user = userService.findById(userId);
        if (user == null || user.getUserType() == null || user.getUserType() != 3) {
            throw new IllegalArgumentException("仅商家可操作");
        }
        return userId;
    }
}

