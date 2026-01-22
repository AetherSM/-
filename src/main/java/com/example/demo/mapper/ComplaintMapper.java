package com.example.demo.mapper;

import com.example.demo.pojo.entity.Complaint;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ComplaintMapper {
    void insert(Complaint complaint);
    void update(Complaint complaint);
    Complaint findById(Long complaintId);
    List<Complaint> listByUser(Long userId);
}
