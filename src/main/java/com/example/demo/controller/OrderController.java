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

    @PostMapping
    public Result<ProductOrder> create(@RequestBody OrderCreateDTO dto) {
        Long userId = requireLogin();
        return Result.success(orderService.createOrder(userId, dto));
    }

    @PostMapping("/{orderNo}/pay")
    public Result<Void> pay(@PathVariable String orderNo) {
        Long userId = requireLogin();
        orderService.pay(orderNo, userId);
        return Result.success();
    }

    @PostMapping("/{orderNo}/cancel")
    public Result<Void> cancel(@PathVariable String orderNo, @RequestBody(required = false) Map<String, String> body) {
        Long userId = requireLogin();
        String reason = body == null ? null : body.get("cancelReason");
        orderService.cancel(orderNo, userId, reason);
        return Result.success();
    }

    @PostMapping("/{orderNo}/ship")
    public Result<Void> ship(@PathVariable String orderNo) {
        Long sellerId = requireSeller();
        orderService.ship(orderNo, sellerId);
        return Result.success();
    }

    @PostMapping("/{orderNo}/confirm")
    public Result<Void> confirm(@PathVariable String orderNo) {
        Long userId = requireLogin();
        orderService.confirm(orderNo, userId);
        return Result.success();
    }

    @GetMapping
    public Result<List<ProductOrder>> listMy(@RequestParam(value = "status", required = false) Integer status) {
        Long userId = requireLogin();
        return Result.success(orderService.listUserOrders(userId, status));
    }

    @GetMapping("/seller")
    public Result<List<ProductOrder>> listSeller(@RequestParam(value = "status", required = false) Integer status) {
        Long sellerId = requireSeller();
        return Result.success(orderService.listSellerOrders(sellerId, status));
    }

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

