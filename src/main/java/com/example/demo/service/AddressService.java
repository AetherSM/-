package com.example.demo.service;

import com.example.demo.pojo.dto.AddressDTO;
import com.example.demo.pojo.entity.Address;

import java.util.List;

/**
 * 收货地址业务接口
 */
public interface AddressService {
    
    /**
     * 查询用户所有地址
     * @param userId 用户ID
     * @return 地址列表
     */
    List<Address> list(Long userId);

    /**
     * 创建新地址
     * @param userId 用户ID
     * @param dto 地址信息
     * @return 创建后的地址
     */
    Address create(Long userId, AddressDTO dto);

    /**
     * 更新地址
     * @param userId 用户ID
     * @param addressId 地址ID
     * @param dto 更新信息
     * @return 更新后的地址
     */
    Address update(Long userId, Long addressId, AddressDTO dto);

    /**
     * 删除地址
     * @param userId 用户ID
     * @param addressId 地址ID
     */
    void delete(Long userId, Long addressId);

    /**
     * 设置默认地址
     * @param userId 用户ID
     * @param addressId 地址ID
     */
    void setDefault(Long userId, Long addressId);
}
