package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.constant.Messages;
import com.example.demo.pojo.dto.ReviewCreateDTO;
import com.example.demo.pojo.entity.Review;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Tag(name = "评价管理", description = "订单评价相关接口")
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
    @Operation(summary = "添加评价", description = "对已完成的订单进行评价")
    public Result<Void> addReview(@PathVariable String orderNo, @RequestBody ReviewCreateDTO dto) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException(Messages.UNAUTHORIZED);
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
    @Operation(summary = "商品评价", description = "获取指定商品的评价列表")
    public Result<List<Review>> listReviews(@PathVariable Long productId) {
        return Result.success(reviewService.listByProduct(productId));
    }
}
