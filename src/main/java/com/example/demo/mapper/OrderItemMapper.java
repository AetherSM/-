package com.example.demo.mapper;

import com.example.demo.pojo.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void batchInsert(@Param("items") List<OrderItem> items);

    List<OrderItem> listByOrderId(@Param("orderId") Long orderId);
}

