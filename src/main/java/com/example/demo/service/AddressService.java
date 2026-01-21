package com.example.demo.service;

import com.example.demo.pojo.dto.AddressDTO;
import com.example.demo.pojo.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> list(Long userId);

    Address create(Long userId, AddressDTO dto);

    Address update(Long userId, Long addressId, AddressDTO dto);

    void delete(Long userId, Long addressId);

    void setDefault(Long userId, Long addressId);
}

