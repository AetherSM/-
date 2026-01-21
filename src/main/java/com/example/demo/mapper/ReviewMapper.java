package com.example.demo.mapper;

import com.example.demo.pojo.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insert(Review review);

    List<Review> listByProduct(@Param("productId") Long productId);
}

