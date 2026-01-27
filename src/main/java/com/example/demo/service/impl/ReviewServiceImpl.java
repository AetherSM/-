package com.example.demo.service.impl;

import com.example.demo.mapper.ErrandOrderMapper;
import com.example.demo.mapper.ProductOrderMapper;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.pojo.dto.ReviewCreateDTO;
import com.example.demo.pojo.entity.ErrandOrder;
import com.example.demo.pojo.entity.ProductOrder;
import com.example.demo.pojo.entity.Review;
import com.example.demo.service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private ProductOrderMapper productOrderMapper;

    @Autowired
    private ErrandOrderMapper errandOrderMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addReview(String orderNo, Long userId, ReviewCreateDTO dto) {
        Integer orderType = dto.getOrderType() == null ? 1 : dto.getOrderType();
        if (orderType == 1) {
            ProductOrder order = productOrderMapper.findByOrderNo(orderNo);
            if (order == null || !order.getUserId().equals(userId)) {
                throw new IllegalArgumentException("订单不存在或无权限");
            }
            if (order.getOrderStatus() != 4) {
                throw new IllegalArgumentException("仅已完成订单可评价");
            }
            Review review = new Review();
            review.setOrderId(order.getOrderId());
            review.setOrderType(1);
            review.setReviewerId(userId);
            review.setRevieweeId(order.getSellerId());
            review.setRating(dto.getRating());
            review.setContent(dto.getContent());
            review.setImages(writeImages(dto.getImages()));
            review.setIsAnonymous(dto.getIsAnonymous() == null ? 0 : dto.getIsAnonymous());
            review.setCreateTime(LocalDateTime.now());
            reviewMapper.insert(review);
        } else if (orderType == 2) {
            ErrandOrder order = errandOrderMapper.findByOrderNo(orderNo);
            if (order == null || !order.getUserId().equals(userId)) {
                throw new IllegalArgumentException("订单不存在或无权限");
            }
            if (order.getOrderStatus() != 4) {
                throw new IllegalArgumentException("仅已完成订单可评价");
            }
            if (order.getRunnerId() == null) {
                throw new IllegalArgumentException("订单未关联跑腿员，无法评价");
            }
            Review review = new Review();
            review.setOrderId(order.getOrderId());
            review.setOrderType(2);
            review.setReviewerId(userId);
            review.setRevieweeId(order.getRunnerId());
            review.setRating(dto.getRating());
            review.setContent(dto.getContent());
            review.setImages(writeImages(dto.getImages()));
            review.setIsAnonymous(dto.getIsAnonymous() == null ? 0 : dto.getIsAnonymous());
            review.setCreateTime(LocalDateTime.now());
            reviewMapper.insert(review);
        } else {
            throw new IllegalArgumentException("不支持的订单类型");
        }
    }

    @Override
    public List<Review> listByProduct(Long productId) {
        return reviewMapper.listByProduct(productId);
    }

    private String writeImages(List<String> images) {
        if (images == null) return null;
        try {
            return objectMapper.writeValueAsString(images);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("图片列表格式错误");
        }
    }
}
