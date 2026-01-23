package com.example.demo.mapper;

import com.example.demo.pojo.entity.ShoppingRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingRecordMapper {
    void batchInsert(@Param("records") List<ShoppingRecord> records);
    List<ShoppingRecord> listByUser(@Param("userId") Long userId);
    void deleteByIdAndUser(@Param("recordId") Long recordId, @Param("userId") Long userId);
}
