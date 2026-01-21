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

    /**
     * 添加评价
     * @param orderNo 订单号
     * @param dto 评价信息
     * @return 无
     */
    @PostMapping("/orders/{orderNo}/reviews")
    public Result<Void> addReview(@PathVariable String orderNo, @RequestBody ReviewCreateDTO dto) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        reviewService.addReview(orderNo, userId, dto);
        return Result.success();
    }

    /**
     * 获取商品评价列表
     * @param productId 商品ID
     * @return 评价列表
     */
    @GetMapping("/products/{productId}/reviews")
    public Result<List<Review>> listReviews(@PathVariable Long productId) {
        return Result.success(reviewService.listByProduct(productId));
    }
}

