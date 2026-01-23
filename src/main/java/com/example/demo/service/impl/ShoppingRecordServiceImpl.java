package com.example.demo.service.impl;

import com.example.demo.mapper.ShoppingRecordMapper;
import com.example.demo.pojo.entity.ShoppingRecord;
import com.example.demo.service.ShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingRecordServiceImpl implements ShoppingRecordService {

    @Autowired
    private ShoppingRecordMapper shoppingRecordMapper;

    @Override
    public void batchInsert(List<ShoppingRecord> records) {
        if (records == null || records.isEmpty()) return;
        shoppingRecordMapper.batchInsert(records);
    }

    @Override
    public List<ShoppingRecord> listByUser(Long userId) {
        return shoppingRecordMapper.listByUser(userId);
    }

    @Override
    public void delete(Long userId, Long recordId) {
        shoppingRecordMapper.deleteByIdAndUser(recordId, userId);
    }
}
