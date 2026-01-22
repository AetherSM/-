package com.example.demo.service;

import com.example.demo.pojo.entity.Complaint;
import java.util.List;

/**
 * 投诉业务接口
 */
public interface ComplaintService {
    
    /**
     * 提交投诉
     * @param complaint 投诉信息
     */
    void submitComplaint(Complaint complaint);
    
    /**
     * 处理投诉
     * @param complaintId 投诉ID
     * @param result 处理结果
     */
    void resolveComplaint(Long complaintId, String result);
    
    /**
     * 查询我的投诉
     * @param userId 用户ID
     * @return 投诉列表
     */
    List<Complaint> listMyComplaints(Long userId);
}
