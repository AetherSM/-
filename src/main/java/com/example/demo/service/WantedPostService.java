package com.example.demo.service;

import com.example.demo.pojo.entity.WantedPost;
import java.util.List;

/**
 * 二手求购业务接口
 */
public interface WantedPostService {
    
    /**
     * 发布求购信息
     * @param post 求购信息
     */
    void createPost(WantedPost post);
    
    /**
     * 更新求购状态
     * @param postId 求购ID
     * @param status 新状态
     */
    void updateStatus(Long postId, Integer status);
    
    /**
     * 查询求购信息列表
     * @param status 状态筛选 (可选)
     * @return 求购信息列表
     */
    List<WantedPost> listPosts(Integer status);
}
