package com.example.demo.service;

import com.example.demo.pojo.entity.ErrandOrder;
import java.util.List;

/**
 * 跑腿业务接口
 */
public interface ErrandService {
    
    /**
     * 创建跑腿订单
     * @param order 订单信息
     */
    void createOrder(ErrandOrder order);
    
    /**
     * 跑腿员接单
     * @param orderNo 订单号
     * @param runnerId 跑腿员ID
     */
    void takeOrder(String orderNo, Long runnerId);
    
    /**
     * 完成订单
     * @param orderNo 订单号
     */
    void completeOrder(String orderNo);
    
    /**
     * 查询所有待接单订单
     * @return 订单列表
     */
    List<ErrandOrder> listOpenOrders();
    
    /**
     * 查询我的跑腿订单（我发布的或我接单的）
     * @param userId 用户ID
     * @param status 订单状态（可选）
     * @return 订单列表
     */
    List<ErrandOrder> listMyOrders(Long userId, Integer status);
}
