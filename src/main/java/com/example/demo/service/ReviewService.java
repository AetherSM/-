package com.example.demo.service;

import com.example.demo.pojo.dto.ReviewCreateDTO;
import com.example.demo.pojo.entity.Review;

import java.util.List;

public interface ReviewService {
    void addReview(String orderNo, Long userId, ReviewCreateDTO dto);

    List<Review> listByProduct(Long productId);
}

