package com.example.demo.mapper;

import com.example.demo.pojo.entity.UserCoupon;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface UserCouponMapper {
    void insert(UserCoupon userCoupon);
    void updateStatus(Long id, Integer status, Long orderId);
    List<UserCoupon> listByUser(Long userId, Integer status);
    int countByUserAndCoupon(Long userId, Long couponId);
}
