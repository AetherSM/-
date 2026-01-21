package com.example.demo.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreateDTO {
    private Long addressId;
    private String deliveryAddress;
    private String contactName;
    private String contactPhone;
    private String remark;
    private List<OrderItemDTO> items;
}

