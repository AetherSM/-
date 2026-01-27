package com.example.demo.service.impl;

import com.example.demo.mapper.ReviewReplyMapper;
import com.example.demo.pojo.entity.ReviewReply;
import com.example.demo.service.ReviewReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewReplyServiceImpl implements ReviewReplyService {

    @Autowired
    private ReviewReplyMapper reviewReplyMapper;

    @Override
    public void addReply(Long reviewId, Long userId, String content) {
        if (reviewId == null || userId == null) {
            throw new IllegalArgumentException("参数错误");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("回复内容不能为空");
        }
        ReviewReply rr = new ReviewReply();
        rr.setReviewId(reviewId);
        rr.setReplierId(userId);
        rr.setContent(content.trim());
        rr.setCreateTime(LocalDateTime.now());
        reviewReplyMapper.insert(rr);
    }

    @Override
    public List<ReviewReply> listByReview(Long reviewId) {
        return reviewReplyMapper.listByReview(reviewId);
    }
}
