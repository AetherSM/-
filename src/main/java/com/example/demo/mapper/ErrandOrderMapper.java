package com.example.demo.mapper;

import com.example.demo.pojo.entity.ErrandOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ErrandOrderMapper {
    void insert(ErrandOrder order);
    void updateStatus(@Param("orderNo") String orderNo, @Param("status") Integer status, @Param("runnerId") Long runnerId);
    
    ErrandOrder findByOrderNo(String orderNo);
    List<ErrandOrder> listByStatus(Integer status);
    List<ErrandOrder> listByUser(@Param("userId") Long userId, @Param("status") Integer status);
    List<ErrandOrder> listByRunner(Long runnerId);
}
