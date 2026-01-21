package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.dto.ReviewCreateDTO;
import com.example.demo.pojo.entity.Review;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/orders/{orderNo}/reviews")
    public Result<Void> addReview(@PathVariable String orderNo, @RequestBody ReviewCreateDTO dto) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        reviewService.addReview(orderNo, userId, dto);
        return Result.success();
    }

    @GetMapping("/products/{productId}/reviews")
    public Result<List<Review>> listReviews(@PathVariable Long productId) {
        return Result.success(reviewService.listByProduct(productId));
    }
}

