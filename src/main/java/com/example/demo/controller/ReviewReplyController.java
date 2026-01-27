package com.example.demo.controller;

import com.example.demo.context.BaseContext;
import com.example.demo.pojo.entity.ReviewReply;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ReviewReplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@Tag(name = "评价回复", description = "评价回复相关接口")
public class ReviewReplyController {

    @Autowired
    private ReviewReplyService reviewReplyService;

    @GetMapping("/{reviewId}/replies")
    @Operation(summary = "回复列表", description = "获取指定评价的回复列表")
    public Result<List<ReviewReply>> list(@PathVariable Long reviewId) {
        return Result.success(reviewReplyService.listByReview(reviewId));
    }

    @PostMapping("/{reviewId}/replies")
    @Operation(summary = "添加回复", description = "对评价进行回复")
    public Result<Void> add(@PathVariable Long reviewId, @RequestBody Map<String, String> body) {
        Long userId = BaseContext.getCurrentId();
        if (userId == null) {
            throw new IllegalArgumentException("未登录");
        }
        String content = body == null ? null : body.get("content");
        reviewReplyService.addReply(reviewId, userId, content);
        return Result.success();
    }
}
