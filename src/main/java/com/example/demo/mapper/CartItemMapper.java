package com.example.demo.mapper;

import com.example.demo.pojo.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartItemMapper {
    void insert(CartItem item);
    void updateQuantity(@Param("userId") Long userId, @Param("productId") Long productId, @Param("quantity") Integer quantity);
    CartItem findByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);
    List<CartItem> listByUser(@Param("userId") Long userId);
    void deleteByUserAndProduct(@Param("userId") Long userId, @Param("productId") Long productId);
    void clearByUser(@Param("userId") Long userId);
}
