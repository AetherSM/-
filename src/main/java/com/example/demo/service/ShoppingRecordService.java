package com.example.demo.service;

import com.example.demo.pojo.entity.ShoppingRecord;

import java.util.List;

public interface ShoppingRecordService {
    void batchInsert(List<ShoppingRecord> records);
    List<ShoppingRecord> listByUser(Long userId);
    void delete(Long userId, Long recordId);
}
