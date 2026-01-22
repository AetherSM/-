package com.example.demo.mapper;

import com.example.demo.pojo.entity.WantedPost;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface WantedPostMapper {
    void insert(WantedPost post);
    void update(WantedPost post);
    WantedPost findById(Long postId);
    List<WantedPost> list(Integer status);
}
