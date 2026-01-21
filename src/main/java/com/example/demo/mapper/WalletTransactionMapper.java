package com.example.demo.mapper;

import com.example.demo.pojo.entity.WalletTransaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WalletTransactionMapper {
    void insert(WalletTransaction tx);

    List<WalletTransaction> listByUser(@Param("userId") Long userId);
}

