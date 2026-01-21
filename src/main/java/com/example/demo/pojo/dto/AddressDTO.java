package com.example.demo.pojo.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long addressId;
    private String contactName;
    private String contactPhone;
    private String address;
    private String building;
    private String room;
    private Integer isDefault;
}

