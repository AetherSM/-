package com.example.demo.service;

import com.example.demo.pojo.entity.ReviewReply;

import java.util.List;

public interface ReviewReplyService {
    void addReply(Long reviewId, Long userId, String content);
    List<ReviewReply> listByReview(Long reviewId);
}
