package com.example.demo.service.impl;

import com.example.demo.mapper.ErrandOrderMapper;
import com.example.demo.pojo.entity.ErrandOrder;
import com.example.demo.service.ErrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Service
public class ErrandServiceImpl implements ErrandService {

    @Autowired
    private ErrandOrderMapper errandOrderMapper;
    
    private static final DateTimeFormatter ORDER_NO_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final Random RANDOM = new Random();

    @Override
    public void createOrder(ErrandOrder order) {
        order.setOrderNo("EO" + LocalDateTime.now().format(ORDER_NO_FMT) + (1000 + RANDOM.nextInt(9000)));
        order.setOrderStatus(1); // 待接单
        errandOrderMapper.insert(order);
    }

    @Override
    public void takeOrder(String orderNo, Long runnerId) {
        // 简化逻辑，实际需要校验状态和runnerId
        ErrandOrder order = errandOrderMapper.findByOrderNo(orderNo);
        if (order != null && order.getOrderStatus() == 1) {
            // 需要更新runner_id，这里简化处理，只更新状态，实际mapper需要支持更新runner_id
             errandOrderMapper.updateStatus(orderNo, 2); 
        }
    }

    @Override
    public void completeOrder(String orderNo) {
        errandOrderMapper.updateStatus(orderNo, 4);
    }

    @Override
    public List<ErrandOrder> listOpenOrders() {
        return errandOrderMapper.listByStatus(1);
    }

    @Override
    public List<ErrandOrder> listMyOrders(Long userId) {
        return errandOrderMapper.listByUser(userId);
    }
}
