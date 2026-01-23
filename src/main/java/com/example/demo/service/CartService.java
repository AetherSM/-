package com.example.demo.service;

import com.example.demo.pojo.entity.CartItem;

import java.util.List;
import java.util.Map;

public interface CartService {
    void addItem(Long userId, Long productId, Integer quantity);
    void setQuantity(Long userId, Long productId, Integer quantity);
    void removeItem(Long userId, Long productId);
    void clear(Long userId);
    List<CartItem> list(Long userId);
    List<Map<String, Object>> listWithProduct(Long userId);
}
