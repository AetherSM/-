package com.example.demo.service;

import com.example.demo.pojo.dto.OrderCreateDTO;
import com.example.demo.pojo.entity.OrderItem;
import com.example.demo.pojo.entity.ProductOrder;

import java.util.List;

public interface OrderService {
    ProductOrder createOrder(Long userId, OrderCreateDTO dto);

    void pay(String orderNo, Long userId);

    void cancel(String orderNo, Long userId, String reason);

    void ship(String orderNo, Long sellerId);

    void confirm(String orderNo, Long userId);

    ProductOrder findByOrderNo(String orderNo);

    List<ProductOrder> listUserOrders(Long userId, Integer status);

    List<ProductOrder> listSellerOrders(Long sellerId, Integer status);

    List<OrderItem> listItems(Long orderId);
}

