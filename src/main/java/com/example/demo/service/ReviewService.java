package com.example.demo.service;

import com.example.demo.pojo.dto.ReviewCreateDTO;
import com.example.demo.pojo.entity.Review;

import java.util.List;

/**
 * 评价业务接口
 */
public interface ReviewService {
    
    /**
     * 添加评价
     * @param orderNo 订单号
     * @param userId 用户ID
     * @param dto 评价信息
     */
    void addReview(String orderNo, Long userId, ReviewCreateDTO dto);

    /**
     * 查询商品评价列表
     * @param productId 商品ID
     * @return 评价列表
     */
    List<Review> listByProduct(Long productId);
}
