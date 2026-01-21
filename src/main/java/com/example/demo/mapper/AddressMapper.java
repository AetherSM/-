package com.example.demo.mapper;

import com.example.demo.pojo.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<Address> listByUser(@Param("userId") Long userId);

    Address findById(@Param("addressId") Long addressId);

    void insert(Address address);

    void update(Address address);

    void delete(@Param("addressId") Long addressId);

    void clearDefault(@Param("userId") Long userId);

    void setDefault(@Param("addressId") Long addressId);
}

