package com.example.demo.controller;

import com.example.demo.pojo.entity.WantedPost;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.WantedPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 二手求购控制器
 */
@Tag(name = "二手求购管理", description = "二手求购信息的发布与查询")
@RestController
@RequestMapping("/api/wanted")
public class WantedPostController {

    @Autowired
    private WantedPostService wantedPostService;

    /**
     * 发布求购信息
     * @param post 求购信息实体
     * @return 操作结果
     */
    @Operation(summary = "发布求购", description = "发布新的二手求购信息")
    @PostMapping("/create")
    public Result<String> createPost(@RequestBody WantedPost post) {
        wantedPostService.createPost(post);
        return Result.success("发布成功");
    }

    /**
     * 更新求购状态
     * @param postId 求购信息ID
     * @param status 新状态
     * @return 操作结果
     */
    @Operation(summary = "更新状态", description = "更新求购信息的状态 (如：1-求购中, 2-已买到)")
    @PostMapping("/status")
    public Result<String> updateStatus(
            @Parameter(description = "求购信息ID", required = true) @RequestParam Long postId,
            @Parameter(description = "新状态: 1-求购中, 2-已买到, 3-已取消") @RequestParam Integer status) {
        wantedPostService.updateStatus(postId, status);
        return Result.success("状态更新成功");
    }

    /**
     * 获取求购信息列表
     * @param status 状态筛选（可选）
     * @return 求购信息列表
     */
    @Operation(summary = "获取求购列表", description = "查询求购信息列表，可按状态筛选")
    @GetMapping("/list")
    public Result<List<WantedPost>> listPosts(
            @Parameter(description = "状态筛选: 1-求购中, 2-已买到, 3-已取消") @RequestParam(required = false) Integer status) {
        return Result.success(wantedPostService.listPosts(status));
    }
}
