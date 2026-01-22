package com.example.demo.controller;

import com.example.demo.pojo.entity.Complaint;
import com.example.demo.pojo.result.Result;
import com.example.demo.service.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 投诉管理控制器
 */
@Tag(name = "投诉管理", description = "用户投诉的提交与处理")
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    /**
     * 提交投诉
     * @param complaint 投诉信息
     * @return 操作结果
     */
    @Operation(summary = "提交投诉", description = "用户提交新的投诉信息")
    @PostMapping("/submit")
    public Result<String> submitComplaint(@RequestBody Complaint complaint) {
        complaintService.submitComplaint(complaint);
        return Result.success("投诉提交成功");
    }

    /**
     * 处理投诉
     * @param complaintId 投诉ID
     * @param result 处理结果
     * @return 操作结果
     */
    @Operation(summary = "处理投诉", description = "管理员或客服处理用户投诉")
    @PostMapping("/resolve")
    public Result<String> resolveComplaint(
            @Parameter(description = "投诉ID", required = true) @RequestParam Long complaintId,
            @Parameter(description = "处理结果/回复内容", required = true) @RequestParam String result) {
        complaintService.resolveComplaint(complaintId, result);
        return Result.success("处理完成");
    }

    /**
     * 查询我的投诉
     * @param userId 用户ID
     * @return 投诉列表
     */
    @Operation(summary = "我的投诉", description = "查询当前用户提交的所有投诉记录")
    @GetMapping("/my")
    public Result<List<Complaint>> listMyComplaints(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId) {
        return Result.success(complaintService.listMyComplaints(userId));
    }
}
