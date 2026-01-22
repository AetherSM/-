package com.example.demo.service.impl;

import com.example.demo.mapper.WantedPostMapper;
import com.example.demo.pojo.entity.WantedPost;
import com.example.demo.service.WantedPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WantedPostServiceImpl implements WantedPostService {

    @Autowired
    private WantedPostMapper wantedPostMapper;

    @Override
    public void createPost(WantedPost post) {
        post.setStatus(1);
        wantedPostMapper.insert(post);
    }

    @Override
    public void updateStatus(Long postId, Integer status) {
        WantedPost post = wantedPostMapper.findById(postId);
        if (post != null) {
            post.setStatus(status);
            wantedPostMapper.update(post);
        }
    }

    @Override
    public List<WantedPost> listPosts(Integer status) {
        return wantedPostMapper.list(status);
    }
}
