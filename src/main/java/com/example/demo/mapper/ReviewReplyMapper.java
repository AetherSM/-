package com.example.demo.mapper;

import com.example.demo.pojo.entity.ReviewReply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewReplyMapper {
    void insert(ReviewReply reply);
    List<ReviewReply> listByReview(@Param("reviewId") Long reviewId);
}
